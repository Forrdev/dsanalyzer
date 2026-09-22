package com.sappyoak.dsanalyzer.game.files

import com.sappyoak.dsanalyzer.formats.archive.ArchiveEntry
import com.sappyoak.dsanalyzer.formats.archive.archivePathHash
import com.sappyoak.dsanalyzer.formats.archive.readArchive
import com.sappyoak.dsanalyzer.formats.compression.decompress
import com.sappyoak.dsanalyzer.game.Installation
import com.sappyoak.dsanalyzer.game.archiveStems
import com.sappyoak.dsanalyzer.game.ARCHIVE_DATA_EXTENSION
import com.sappyoak.dsanalyzer.game.ARCHIVE_HEADER_EXTENSION
import com.sappyoak.dsanalyzer.shared.binary.BinaryReader
import com.sappyoak.dsanalyzer.shared.binary.MappedFile

/**
 * Access to a games files that are packed in archive files
 */
internal class ArchiveGameFiles(
    private val data: List<MappedFile>,
    private val entries: Map<UInt, Located>
) : GameFiles {
    override fun listing(): FileListing = FileListing.Hashed(entries.keys)

    override fun exists(path: GamePath): Boolean = path.hash in entries

    override fun open(path: GamePath): BinaryReader? {
        val located = entries[path.hash] ?: return null
        return data[located.archive].reader()
            .slice(located.entry.offset, located.entry.paddedSize.toLong())
            .decompress()
    }

    override fun close() {
        data.forEach(MappedFile::close)
    }

    internal class Located(val archive: Int, val entry: ArchiveEntry)

    public companion object {
        public fun open(installation: Installation): GameFiles {
            val opened = mutableListOf<MappedFile>()
            val entries = hashMapOf<UInt, Located>()

            try {
                archiveStems(installation.build.edition).forEach { stem ->
                    val index = opened.size
                    opened.add(MappedFile.open(installation.root.resolve("$stem.$ARCHIVE_DATA_EXTENSION")))

                    val header = MappedFile.open(installation.root.resolve("$stem.$ARCHIVE_HEADER_EXTENSION"))
                        .use { readArchive(it.reader()) }

                    header.entries.forEach { entry ->
                        entries.putIfAbsent(entry.hash, Located(index, entry))
                    }
                }
            } catch (err: Throwable) {
                opened.forEach(MappedFile::close)
                throw err
            }

            return ArchiveGameFiles(opened, entries)
        }
    }
}