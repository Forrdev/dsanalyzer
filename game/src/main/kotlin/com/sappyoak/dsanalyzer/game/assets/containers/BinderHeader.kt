package com.sappyoak.dsanalyzer.game.assets.containers

import com.sappyoak.dsanalyzer.shared.BinaryReader
import com.sappyoak.dsanalyzer.shared.toReader

/**
 * Header parsing shared by BND3 and BXF3.
 *
 * The two formats differ only in where entry data lives. BND3 keeps it in the same file,
 * BXF3 is a companion '.bdt.'
 */
internal object BinderHeader {
    const val MAX_FILES = 65536

    fun read(bytes: ByteArray): BinderInfo? {
        if (bytes.size < 32) return null

        val bitBigEndian = bytes[0x0E].toInt() != 0
        val rawFormat = bytes[0x0C].toInt() and 0xFF
        val format = decodeFormat(rawFormat, bitBigEndian)
        val bigEndian = bytes[0x0D].toInt() != 0 || (format and Flags.FormatBigEndian) != 0

        val reader = bytes.toReader(!bigEndian)
        val version = reader.seek(4).fixedString(8)

        reader.seek(0x10)
        val fileCount = reader.i32()

        if (fileCount < 0 || fileCount > MAX_FILES) return null

        return BinderInfo(
            version = version,
            format = format,
            bigEndian = bigEndian,
            fileCount = fileCount,
            entriesOffset = 0x20
        )
    }

    fun decodeFormat(rawFormat: Int, bitBigEndian: Boolean): Int {
        val alreadyOrdered = bitBigEndian || ((rawFormat and 1) != 0 && (rawFormat and 0x80) == 0)
        return if (alreadyOrdered) rawFormat else rawFormat.reverseBits()
    }

    private fun Int.reverseBits(): Int {
        var value = this and 0xFF
        var result = 0
        repeat(8) {
            result = (result shl 1) or (value and 1)
            value = value shr 1
        }
        return result
    }
}



private enum class Flags(val flag: Byte) {
    FormatBigEndian(0b0000_0001),
    FormatIds(0b0000_0010),
    FormatNames1(0b0000_0100),
    FormatNames2(0b0000_1000),
    FormatLongOffsets(0b0001_0000),
    FormatCompression(0b0010_0000);

    infix fun or(other: Flags): Int = flag.toInt() or other.flag.toInt()
}

private infix fun Int.and(flag: Flags): Int = this and flag.flag.toInt()

internal data class BinderInfo(
    val version: String,
    val format: Int,
    val bigEndian: Boolean,
    val fileCount: Int,
    val entriesOffset: Int
) {
    val hasIds: Boolean get(): Boolean = (format and Flags.FormatIds) != 0
    val hasNames: Boolean get() = (format and (Flags.FormatNames1 or Flags.FormatNames2)) != 0
    val hasCompression: Boolean get() = (format and Flags.FormatCompression) != 0
    val hasLongOffsets: Boolean get() = (format and Flags.FormatLongOffsets) != 0

    /**
     * An implausible file count is the first symptom of the bit order being wrong, and the
     * cheapest one to check
     */
    val looksPlausible: Boolean get() = fileCount in 0..BinderHeader.MAX_FILES

    fun readEntry(reader: BinaryReader): BinderEntryHeader? {
        if (!reader.requestBytes(8)) return null

        val flags = reader.u8()
        reader.skip(3)
        val size = reader.i32()
        val dataOffset = if (hasLongOffsets) reader.i64().toInt() else reader.i32()
        val id = if (hasIds) reader.i32() else -1
        val name = if (hasNames) reader.cStringAt(reader.i32()) else null
        val uncompressedSize = if (hasCompression) reader.i32() else -1

        return BinderEntryHeader(
            flags,
            size,
            dataOffset,
            id,
            name,
            uncompressedSize
        )
    }
}

internal data class BinderEntryHeader(
    val flags: Int,
    val size: Int,
    val dataOffset: Int,
    val id: Int,
    val name: String?,
    val uncompressedSize: Int
)