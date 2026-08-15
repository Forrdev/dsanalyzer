package com.sappyoak.dsanalyzer.game.install

import java.nio.file.Path
import kotlin.io.path.exists

import com.sappyoak.dsanalyzer.shared.readBytes
import com.sappyoak.dsanalyzer.game.assets.GameAssetPaths
import com.sappyoak.dsanalyzer.game.identity.*

class InstallationInspector(
    private val identityProbe: GameIdentityProbe,
    private val versionDetector: GameVersionDetector
) {
    fun inspect(path: Path): InstallInspectionResult {
        if (!path.exists()) {
            return InstallInspectionResult(
                path = path,
                kind = InstallKind.Unknown,
                problem = "Directory does not exist"
            )
        }

        val archives = GameAssetPaths.getArchivePairs().filter { (header, data) ->
            path.resolve(header).exists() && path.resolve(data).exists()
        }

        val looseFileCount = GameAssetPaths.LOOSE_FILE_MARKERS.count { marker ->
            path.resolve(marker).exists()
        }

        val kind = when {
            archives.isNotEmpty() && looseFileCount > 0 -> InstallKind.Mixed
            archives.isNotEmpty() -> InstallKind.Packed
            looseFileCount > 0 -> InstallKind.Unpacked
            else -> InstallKind.Unknown
        }

        val detected = versionDetector.detect(path)
        val version = detected.version
        val identity = version?.let {
            getGameIdentity(path, it, archives.map { it.first })
        }

        return InstallInspectionResult(
            path = path,
            kind = kind,
            version = version,
            detection = detected,
            archiveCount = archives.size,
            looseFilesFound = looseFileCount,
            problem = if (kind != InstallKind.Unpacked && archives.size < GameAssetPaths.ARCHIVE_NAMES.size) {
                "Only ${archives.size} of ${GameAssetPaths.ARCHIVE_NAMES.size} archives found. Some game files " +
                        "will be missing, and the extraction manifest will show them as misses"
            } else null
        )
    }

    private fun getGameIdentity(
        path: Path,
        version: GameVersion,
        archives: List<String>
    ): GameIdentity {
        val samples = buildList {
            for (name in archives) {
                sampleGameFile(path.resolve(name))?.let { add(it) }
            }

            if (isEmpty()) {
                for (marker in GameAssetPaths.LOOSE_FILE_MARKERS) {
                    sampleGameFile(path.resolve(marker))?.let { add(it) }
                }
            }
        }

        return if (samples.isEmpty()) {
            GameIdentity.unknown(version)
        } else identityProbe.identify(version, samples)
    }

    private fun sampleGameFile(path: Path, limit: Int = 1 shl 20): ByteArray? =
        path.readBytes(limit).getOrNull()
}
