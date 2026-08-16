package com.sappyoak.dsanalyzer.game.assets.containers

import com.sappyoak.dsanalyzer.shared.toReader

fun ByteArray.isBND4Archive(): Boolean =
    size >= 4 && decodeToString(0, 4) == BND4ArchiveReader.MAGIC_STR

/**
 * BND4 is the 64-bit binder which DSR uses in place of BND3
 *
 * Structurally the same idea: a header, per-file records, and data. What differs is width.
 * Every offset and size is 64 bit, and that the header carries its own sizes rather than implying
 * them, which makes it more tolerant of variants than BND3 was.
 *
 * A quirk is the bit-order flag is **inverted** relative to BND3. The byte at 0x06 means
 * 'bit little endian' here where the equivalent meant "bit big endian".
 */
object BND4ArchiveReader {
    private const val HEADER_SIZE = 0x40L
    const val MAGIC_STR = "BND4"

    fun read(bytes: ByteArray): Outcome {
        val raw = when (val res = DCXProcessor.process(bytes)) {
            is DCXProcessor.Outcome.Failure -> return Outcome.Failure("DCX Failure: ${res.reason}", res.error)
            else -> res.bytesOrNull!!
        }

        if (!raw.isBND4Archive()) return Outcome.Failure("Not a BND4 Archive")
        if (raw.size < HEADER_SIZE) return Outcome.Failure("BND4 shorter than its header")

        val bigEndian = raw[0x09].toInt() != 0
        // inverted relative to BND3
        val bitLittleEndian = raw[0x0A].toInt() != 0

        val reader = raw.toReader(!bigEndian).seek(0x0C)
        val fileCount = reader.i32()

        if (reader.i64() != HEADER_SIZE) {
            return Outcome.Failure("Unexpected header size, this may not be a BND4")
        }

        val version = reader.fixedString(8)
        val fileHeaderSize = reader.i64()
        reader.i64() // headers end, including the hash table

        val unicode = reader.u8() != 0
        val rawFormat = reader.u8()
        val format = BinderHeader.decodeFormat(rawFormat, bitBigEndian = !bitLittleEndian)

        if (fileCount !in 0..BinderHeader.MAX_FILES) {
            return Outcome.Failure(
                "Implausible file count $fileCount. The format byte was probably read with the " +
                "wrong bit order"
            )
        }

        val hasIds = (format and BinderHeader.Flags.FormatIds) != 0
        val hasNames = (format and (BinderHeader.Flags.FormatNames1 or BinderHeader.Flags.FormatNames2)) != 0
        val hasCompression = (format and BinderHeader.Flags.FormatCompression) != 0

        val entries = ArrayList<BND4Entry>(fileCount)
        var offset = HEADER_SIZE

        for (i in 0 until fileCount) {
            val entryReader = reader.at(offset.toInt())
            if (entryReader.requestBytes(0x18)) break

            entryReader.i32() // flags, as a padded byte
            entryReader.i32() // padding
            val size = entryReader.i64()
            val uncompressedSize = if (hasCompression) entryReader.i64() else -1L
            val dataOffset = entryReader.i32()
            val id = if (hasIds) entryReader.i32() else -1

            val name = if (hasNames) {
                val nameOffset = entryReader.i32()
                // names are utf-16 when the header says unicode, which is the usual case here
                // reading them as ASCII yields every other character rather than failing
                if (unicode) reader.wideStringAt(nameOffset) else reader.cStringAt(nameOffset)
            } else null

            offset += fileHeaderSize

            if (dataOffset < 0 || size <= 0 || dataOffset + size > raw.size) continue

            entries.add(BND4Entry(
                id = id,
                name = name,
                data = raw.copyOfRange(dataOffset, (dataOffset + size).toInt()),
                declaredUncompressedSize = uncompressedSize
            ))
        }

        return Outcome.Success(BND4Archive(version, format, entries))
    }

    sealed class Outcome {
        val archiveOrNull: BND4Archive? get() = (this as? Success)?.archive

        data class Success(val archive: BND4Archive) : Outcome()
        data class Failure(val reason: String, val error: Throwable? = null) : Outcome()
    }
}

class BND4Archive(
    val version: String,
    val format: Int,
    val entries: List<BND4Entry>
) {
    operator fun get(id: Int): BND4Entry? = entries.firstOrNull { it.id == id }
    operator fun get(fileName: String): BND4Entry? = entries.firstOrNull {
        it.fileName?.equals(fileName, ignoreCase = true) == true
    }

    fun withExtension(extension: String): List<BND4Entry> = entries.filter {
        it.fileName?.endsWith(extension, ignoreCase = true) == true
    }

    fun asMap(): Map<String, ByteArray> =
        entries.mapNotNull { entry -> entry.fileName?.let { it to entry.data } }.toMap()

    override fun toString() = "BND4 $version (${entries.size} entries)"
}

class BND4Entry(
    val id: Int,
    val name: String?,
    val data: ByteArray,
    val declaredUncompressedSize: Long
) {
    val fileName: String?
        get() = name?.substringAfterLast('\\')?.substringAfterLast("/")

    override fun toString() = "${fileName ?: id} (${data.size} bytes)"
}