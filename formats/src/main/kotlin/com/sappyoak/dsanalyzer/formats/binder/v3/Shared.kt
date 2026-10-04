package com.sappyoak.dsanalyzer.formats.binder.v3

import com.sappyoak.dsanalyzer.formats.binder.BinderEntry
import com.sappyoak.dsanalyzer.formats.binder.BinderEntryFlags
import com.sappyoak.dsanalyzer.formats.binder.BinderFormatFlags
import com.sappyoak.dsanalyzer.shared.binary.BinaryReader
import com.sappyoak.dsanalyzer.shared.binary.assertValue
import com.sappyoak.dsanalyzer.shared.binary.readUInt
import java.nio.ByteOrder

internal class Version3SharedFields(
    val version: String,
    val flags: BinderFormatFlags,
    val bigEndian: Boolean,
    val bitBigEndian: Boolean,
    val entryCount: Int
)

internal fun BinaryReader.readVersion3SharedFields(magic: String): Version3SharedFields {
    assertValue(magic) { readAscii(magic.length) }

    val version = readFixedString(8)
    val bitBigEndian = at(0xE) { readBoolean() }
    val flags = BinderFormatFlags.read(this, bitBigEndian)
    val bigEndian = readBoolean() || flags.isBigEndian
    assertValue(bitBigEndian) { readBoolean() }
    assertValue(0) { readByte().toInt() }

    order = if (bigEndian) ByteOrder.BIG_ENDIAN else ByteOrder.LITTLE_ENDIAN

    val fileCount = readInt()
    skip(12)

    return Version3SharedFields(
        version = version,
        flags = flags,
        bigEndian = bigEndian,
        bitBigEndian = bitBigEndian,
        entryCount = fileCount
    )
}

internal fun BinaryReader.readVersion3Entry(flags: BinderFormatFlags, bitBigEndian: Boolean): BinderEntry {
    val entryFlags = BinderEntryFlags.read(this, bitBigEndian)
    assertValue(0) { readByte().toInt() }
    assertValue(0) { readByte().toInt() }
    assertValue(0) { readByte().toInt() }

    val compressedSize = readUInt().toLong()
    val dataOffset = if (flags.hasLongOffsets) readLong() else readUInt().toLong()
    val id = if (flags.hasIds) readInt() else -1
    val name = if (flags.hasNames) {
        val nameOffset = readInt().toLong()
        at(nameOffset) { readShiftJIS() }
    } else null

    val uncompressedSize = if (flags.hasCompression) readUInt().toLong() else -1

    return BinderEntry(
        id = id,
        name = name,
        flags = entryFlags,
        dataOffset = dataOffset,
        compressedSize = compressedSize,
        uncompressedSize = uncompressedSize
    )
}