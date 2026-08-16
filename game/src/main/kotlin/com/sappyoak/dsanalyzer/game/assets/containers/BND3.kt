package com.sappyoak.dsanalyzer.game.assets.containers

import com.sappyoak.dsanalyzer.shared.toReader

fun ByteArray.isBND3Archive(): Boolean =
    size >= 4 && decodeToString(0, 4) == BND3ArchiveReader.MAGIC_STR

/**
 * BND3 is the archive format DS1 packs its per-character and per-map files into. This
 * plus the [DCXProcessor] cracks this archives and turns the installation into bytes the
 * rest of the tool can read
 *
 * Two potential footguns to mention:
 *
 * - The format byte is bit-reversed. Which way depends on a flag stored *later* in the same header
 *   so the byte has to be read, and then conditionally reversed once 'bitBigEndian' is known.
 *
 * - Per-file header size is variable. IDs, names, and compressed sizes are each optional and
 *   governed by those flags, so entries cannot be indexed by a fixed stride. Assuming one works on
 *   archives that happen to have every field and fails on those that do not
 */
object BND3ArchiveReader {
    const val MAGIC_STR = "BND3"

    /**
     * Reads an archive's entries. Handles a DCX wrapper transparently, since archives are
     * shipped both ways.
     */
    fun read(bytes: ByteArray): Outcome {
        val raw = when (val res = DCXProcessor.process(bytes)) {
            is DCXProcessor.Outcome.Failure -> return Outcome.Failure("DCX Failure: ${res.reason}", res.error)
            else -> res.bytesOrNull!!
        }

        if (!raw.isBND3Archive()) return Outcome.Failure("Not a BND3 archive")

        val info = BinderHeader.read(raw) ?: return Outcome.Failure(
            "Header did not parse. An implausible file count here usually means the format " +
                    "was read without the bit reversal"
        )

        val reader = bytes.toReader(!info.bigEndian).seek(info.entriesOffset)
        val entries = ArrayList<BND3Entry>(info.fileCount)

        for (i in 0 until info.fileCount) {
            val header = info.readEntry(reader) ?: break
            if (header.dataOffset < 0 || header.dataOffset > raw.size) continue

            val data = reader.slice(header.dataOffset, header.size.coerceAtLeast(0))
            if (data.isEmpty()) continue

            entries.add(BND3Entry(
                id = header.id,
                name = header.name,
                flags = header.flags,
                data = data,
                declaredUncompressedSize = header.uncompressedSize
            ))
        }

        return Outcome.Success(BND3Archive(info.version, info.format, entries))
    }

    sealed class Outcome {
        val archiveOrNull: BND3Archive? get() = (this as? Success)?.archive

        data class Success(val archive: BND3Archive) : Outcome()
        data class Failure(val reason: String, val error: Throwable? = null) : Outcome()
    }
}

class BND3Archive(
    val version: String,
    val format: Int,
    val entries: List<BND3Entry>
) {
    operator fun get(id: Int): BND3Entry? = entries.firstOrNull { it.id == id }
    operator fun get(fileName: String): BND3Entry? = entries.firstOrNull {
        it.fileName?.equals(fileName, ignoreCase = true) == true
    }

    fun withExtension(extension: String): List<BND3Entry> =
        entries.filter { it.fileName?.endsWith(extension, ignoreCase = true) == true }

    /** Entry data keyed by filename, ready to hand to a reader */
    fun asMap(): Map<String, ByteArray> =
        entries.mapNotNull { entry -> entry.fileName?.let { it to entry.data } }.toMap()

    override fun toString() = "BND3 $version (${entries.size} entries)"
}
class BND3Entry(
    val id: Int,
    val name: String?,
    val flags: Int,
    val data: ByteArray,
    val declaredUncompressedSize: Int
) {
    /** Trailing path segment. Internal paths carry build machine roots nobody wants to match on */
    val fileName: String?
        get() = name?.substringAfterLast('\\')?.substringAfterLast('/')

    override fun toString() = "${fileName ?: id} (${data.size} bytes)"
}