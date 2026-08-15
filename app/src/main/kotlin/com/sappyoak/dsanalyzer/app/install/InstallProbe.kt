package com.sappyoak.dsanalyzer.app.install

import java.nio.file.Path

import com.sappyoak.dsanalyzer.domain.*
import com.sappyoak.dsanalyzer.shared.readBytes
import kotlin.io.path.exists

object InstallProbe {
    fun inspect(path: Path): InstallInspection {
        if (!path.exists()) {
            return InstallInspection(
                path = path,
                kind = InstallKind.Unknown,
                problemStr = "Directory does not exist"
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

        val detection = GameDetector.detect(path)
        val version = detection.version

        return InstallInspection(
            path = path,
            kind = kind,
            version = version,
            identity = version?.let { identifyFor(path, it, archives.map { it.first }) },
            detection = detection,
            archiveCount = archives.size,
            looseMarkersFound = looseFileCount,
            problemStr = if (kind != InstallKind.Unpacked && archives.size < GameAssetPaths.ARCHIVE_NAMES.size) {
                "Only ${archives.size} of ${GameAssetPaths.ARCHIVE_NAMES.size} archives found. Some game files " +
                 "will be missing, and the extraction manifest will show them as misses"
            } else null
        )
    }

    private fun identifyFor(
        path: Path,
        version: GameVersion,
        archives: List<String>
    ): GameIdentity {
        val samples = buildList {
            for (name in archives) {
                readSample(path.resolve(name))?.let { add(it) }
            }
            if (isEmpty()) {
                for (marker in GameAssetPaths.LOOSE_FILE_MARKERS) {
                    readSample(path.resolve(marker))?.let { add(it) }
                }
            }
        }

        return if (samples.isEmpty()) {
            GameIdentity.unknown(version)
        } else GameIdentity.from(version, samples)
    }
    private fun readSample(path: Path, limit: Int = 1 shl 20): ByteArray? =
        path.readBytes(limit).getOrNull()
}

