package com.sappyoak.dsanalyzer.game.assets.fs

import kotlinx.serialization.Serializable
import java.nio.file.Path

import com.sappyoak.dsanalyzer.game.assets.*
import kotlin.io.path.exists

/**
 * A warmer than can fill the extraction cache, designed to be
 * run in the background during other work, but does not in-force this constraint
 * itself, relying on the caller to ensure it is running on the appropriate dispatcher/pool
 */
class ExtractionCacheWarmer(
    private val gamePath: Path,
    private val extractedPath: Path,
    /**
     * Which paths to extract, supplied rather than looked up
     *
     * A hashed archive cannot be listed, so extraction needs a list of names,
     * but where that comes from is not this class's concern.
     */
    private val paths: Map<String, List<String>>
) {
    private val cache = GameAssetResolver(extractedPath)
    private val sink = ExtractionFileSink(extractedPath)

    fun warm(
        onProgress: (done: Int, total: Int, current: String) -> Unit = {  _, _, _ -> },
        shouldContinue: () -> Boolean = { true }
    ): Outcome {
        val total = paths.values.sumOf { it.size }

        var written = 0
        var skipped = 0
        var processed = 0

        MappedArchiveSource(gamePath, GameAssetPaths.ARCHIVE_NAMES).use { archives ->
            if (!archives.isUsable) {
                return Outcome(0, 0, "No archives found at $gamePath")
            }

            for ((_, archivePaths) in paths) {
                for (path in archivePaths) {
                    if (!shouldContinue()) {
                        return Outcome(written, skipped, "Cancelled")
                    }

                    processed++
                    val asset = GameAsset(path)

                    // Skip what is already cached, so an interrupted warm resumes rather than
                    // starting over, and so a second run costs a directory walk rather than a
                    // full re-extraction
                    if (cache.target(asset).exists()) {
                        skipped++
                        continue
                    }

                    val bytes = archives.read(path) ?: continue
                    sink.write(asset.name, bytes).onSuccess { written++ }
                    onProgress(processed, total, path)
                }
            }

            return Outcome(written, skipped, null)
        }
    }

    @Serializable
    data class Outcome(
        val written: Int,
        val skipped: Int,
        val error: String?
    ) {
        val wasResumed: Boolean get() = skipped > 0
    }
}