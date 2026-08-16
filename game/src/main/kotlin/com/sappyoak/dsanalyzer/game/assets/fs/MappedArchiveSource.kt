package com.sappyoak.dsanalyzer.game.assets.fs

import java.nio.file.Path

import com.sappyoak.dsanalyzer.game.assets.*
import com.sappyoak.dsanalyzer.game.assets.containers.BHD5Header
import com.sappyoak.dsanalyzer.game.assets.containers.BHD5HeaderReader
import com.sappyoak.dsanalyzer.game.assets.containers.DCXProcessor
import com.sappyoak.dsanalyzer.shared.readBytes

/**
 * Reads entries straight from mapped archives, without extracting first
 */
class MappedArchiveSource(
    private val path: Path,
    private val archiveNames: List<String>
) : AutoCloseable {
    private val assets = GameAssetResolver(path)

    private val archives: List<Pair<BHD5Header, MappedArchive>> = archiveNames.mapNotNull { name ->
        val (headerPath, dataPath) = GameAssetPaths.archiveFilePair(name).run { assets.resolve(first) to assets.resolve(second) }
        if (headerPath == null || dataPath == null) return@mapNotNull null

        val headerBytes = headerPath.readBytes().getOrNull() ?: return@mapNotNull null
        val header = BHD5HeaderReader.read(headerBytes, name) ?: return@mapNotNull null
        val data = runCatching { MappedArchive(dataPath) }.getOrNull() ?: return@mapNotNull null

        header to data
    }

    val isUsable: Boolean get() = archives.isNotEmpty()
    val archiveCount: Int get() = archives.size
    val totalFiles: Int get() = archives.sumOf { it.first.fileCount }

    fun read(path: String): ByteArray? {
        for ((header, data) in archives) {
            val record = header[path] ?: continue
            val raw = data.read(record.offset, record.paddedSize) ?: continue
            return DCXProcessor.process(raw).bytesOrNull ?: raw
        }
        return null
    }

    operator fun contains(path: String): Boolean = archives.any { path in it.first }

    override fun close() {
        archives.forEach { it.second.close() }
    }
}