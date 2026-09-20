package com.sappyoak.dsanalyzer.game.files

import com.sappyoak.dsanalyzer.formats.archive.ArchiveEntry
import com.sappyoak.dsanalyzer.formats.archive.archivePathHash
import com.sappyoak.dsanalyzer.formats.archive.readArchive
import com.sappyoak.dsanalyzer.formats.compression.decompress
import com.sappyoak.dsanalyzer.game.Installation
import com.sappyoak.dsanalyzer.game.archiveStems
import com.sappyoak.dsanalyzer.shared.binary.MappedFile

/**
 * Access to a games files that are packed in archive files
 */
internal class ArchiveGameFiles(
    private val data: List<MappedFile>,
    private val entries: Map<UInt, Located>
) : GameFiles {
    override fun hashes(): Set<UInt> = entries.keys

    override fun exists(path: String): Boolean = archivePathHash(path) in entries

    override fun read(path: String): ByteArray? {

        val located = entries[archivePathHash(path)] ?: return null
        val raw = data[located.archive].reader().at(located.entry.offset) {
            readBytes(located.entry.paddedSize)
        }


        return raw.decompress()
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
                    opened.add(MappedFile.open(installation.root.resolve("$stem.bdt")))

                    val header = MappedFile.open(installation.root.resolve("$stem.bhd5"))
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