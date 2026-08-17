package com.sappyoak.dsanalyzer.game.assets.containers

import com.sappyoak.dsanalyzer.shared.io.toReader

fun ByteArray.isBXF3Header(): Boolean =
    size >= 4 && decodeToString(0, 4) == BXF3ArchiveReader.MAGIC_HEADER_STR

fun ByteArray.isBXF3Data(): Boolean =
    size >= 4 && decodeToString(0, 4) == BXF3ArchiveReader.MAGIC_DATA_STR

object BXF3ArchiveReader {
    const val MAGIC_HEADER_STR = "BHF3"
    const val MAGIC_DATA_STR =  "BDF3"

    /**
     * Reads a split archive from its two halves
     *
     * The data file is only checked for its magic, every offset and length lives in
     * the header, so a mismatched pair reads plausible looking garbage rather than failing.
     * The bounds check on each entry is what catches that
     */
    fun read(headerBytes: ByteArray, dataBytes: ByteArray): Outcome {
        if (!headerBytes.isBXF3Header()) return Outcome.Failure("Not a BHF3 header file")
        if (!dataBytes.isBXF3Data()) return Outcome.Failure("Not a BDF3 data file")

        val info = BinderHeader.read(headerBytes) ?: return Outcome.Failure(
            "Header did not parse. An implausible file count here usually means the format " +
            "byte was read without the bit being reversed"
        )

        val headerReader = headerBytes.toReader(!info.bigEndian).seek(info.entriesOffset)
        val dataReader = dataBytes.toReader(!info.bigEndian)

        val entries = ArrayList<BXF3Entry>(info.fileCount)
        var outOfBounds = 0

        for (i in 0 until info.fileCount) {
            val header = info.readEntry(headerReader) ?: break

            // The only real differences from BND3 are that offsets point into the companion file,
            // so a mismatched pair reads plausible garbage rather than failing. This catches that
            if (header.dataOffset < 0 || header.size <= 0 || header.dataOffset + header.size > dataBytes.size) {
                outOfBounds++
                continue
            }

            entries.add(BXF3Entry(
                id = header.id,
                name = header.name,
                data = dataReader.slice(header.dataOffset, header.size)
            ))
        }

        if (outOfBounds > info.fileCount / 2) {
            return Outcome.Failure(
                "$outOfBounds of ${info.fileCount} entries point outside the data file " +
                "the header and data files are probably not a matching pair"
            )
        }

        return Outcome.Success(BXF3Archive(info.version, entries))
    }

    sealed class Outcome {
        val archiveOrNull: BXF3Archive? get() = (this as? Success)?.archive

        data class Success(val archive: BXF3Archive) : Outcome()
        data class Failure(val reason: String, val error: Throwable? = null) : Outcome()
    }
}

class BXF3Archive(val version: String, val entries: List<BXF3Entry>) {
    operator fun get(fileName: String): BXF3Entry? = entries.firstOrNull {
        it.fileName?.equals(fileName, ignoreCase = true) == true
    }

    fun lowResCollision(): List<BXF3Entry> =
        entries.filter { it.fileName?.startsWith("l", ignoreCase = true) == true }

    fun highResCollision(): List<BXF3Entry> =
        entries.filter { it.fileName?.startsWith("h", ignoreCase = true) == true }


    fun asMap(): Map<String, ByteArray> = entries.mapNotNull { entry ->
        entry.fileName?.let { it to (entry.decompressedOrNull() ?: entry.data) }
    }.toMap()

    override fun toString() = "BXF3 $version (${entries.size} entries)"
}

class BXF3Entry(
    val id: Int,
    val name: String?,
    val data: ByteArray
) {
    val fileName: String?
        get() = name?.substringAfterLast('\\')?.substringAfterLast("/")

    /** Individual entries are usually DCX-wrapped even inside the archive */
    fun decompressedOrNull(): ByteArray? = DCXProcessor.process(data).bytesOrNull

    override fun toString() = "${fileName ?: id} (${data.size} bytes)"
}