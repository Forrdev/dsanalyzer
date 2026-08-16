package com.sappyoak.dsanalyzer.game.assets.fs

import java.nio.file.Path
import kotlin.io.path.exists

import com.sappyoak.dsanalyzer.game.assets.GameAssetPaths

/**
 * Resolves game files from whichever source has them, preferring the cheapest.
 *
 * 1. ** game directory loose file ** - Present only if someone ran UDSFM, or another unpacker
 *    which they did in order to *change* something. Reading anything else would silently
 *    analyze content the game is not running
 * 2. **Extracted cache** - Ours, written from the archives, so identical to them by construction.
 * 3. **Mapped archives** - Always correct, always available, marginally slower
 */
class GameFileSource(
    private val gamePath: Path,
    extractedPath: Path? = null
) : AutoCloseable {
    private val gameLooseSource = UnpackedFileSource(gamePath)
    private val gameHasLoose = GameAssetPaths.LOOSE_FILE_MARKERS.any { gamePath.resolve(it).exists() }

    private val cache = extractedPath?.let { UnpackedFileSource(it) }
    private val cacheRoot = extractedPath

    private val archives: MappedArchiveSource? = runCatching {
        MappedArchiveSource(gamePath, GameAssetPaths.ARCHIVE_NAMES).takeIf { it.isUsable }
    }.getOrNull()

    val canExtract: Boolean get() = !gameHasLoose && archives != null
    val archiveCount: Int get() = archives?.archiveCount ?: 0
    val totalArchivedFiles: Int get() = archives?.totalFiles ?: 0

    var looseHits = 0
        private set
    var cacheHits = 0
        private set
    var archiveHits = 0
        private set
    var misses = 0
        private set

    fun read(path: String): ByteArray? {
        if (gameHasLoose) {
            gameLooseSource.read(path)?.let {
                looseHits++
                return it
            }
        }

        cache?.read(path)?.let {
            cacheHits++
            return it
        }

        archives?.read(path)?.let {
            archiveHits++
            return it
        }

        misses++
        return null
    }

    fun exists(path: String): Boolean =
        (gameHasLoose && gameLooseSource.exists(path)) ||
                cache?.exists(path) == true ||
                archives?.contains(path) == true

    fun sourceOf(path: String): Tier? = when {
        gameHasLoose && gameLooseSource.exists(path) -> Tier.GameLoose
        cache?.exists(path) == true -> Tier.ExtractedCache
        archives?.contains(path) == true -> Tier.Archive
        else -> null
    }

    fun stats(): Stats = Stats(
        looseHits = looseHits,
        cacheHits = cacheHits,
        archiveHits = archiveHits,
        misses = misses,
        cacheRoot = cacheRoot
    )

    override fun close() {
        archives?.close()
    }

    enum class Tier(val label: String) {
        GameLoose("game directory"),
        ExtractedCache("extracted"),
        Archive("archive");
    }

    data class Stats(
        val looseHits: Int,
        val cacheHits: Int,
        val archiveHits: Int,
        val misses: Int,
        val cacheRoot: Path?
    ) {
        val total: Int get() = looseHits + cacheHits + archiveHits
        val cacheHitRate: Double
            get() = if (total == 0) 0.0 else (looseHits + cacheHits).toDouble() / total

        fun summary(): String = buildString {
            if (looseHits > 0) append("$looseHits from game directory, ")
            if (cacheHits > 0) append("$cacheHits from cache, ")
            if (archiveHits > 0) append("$archiveHits from archives, ")
            if (misses > 0) append(" -- $misses not found")
        }
    }
}