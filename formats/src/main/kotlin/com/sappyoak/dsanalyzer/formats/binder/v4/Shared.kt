package com.sappyoak.dsanalyzer.formats.binder.v4

import com.sappyoak.dsanalyzer.formats.binder.BinderEntry
import com.sappyoak.dsanalyzer.formats.binder.BinderEntryFlags
import com.sappyoak.dsanalyzer.formats.binder.BinderFormatFlags
import com.sappyoak.dsanalyzer.shared.binary.BinaryReader
import com.sappyoak.dsanalyzer.shared.binary.assertValue
import com.sappyoak.dsanalyzer.shared.binary.readUInt
import java.nio.ByteOrder

internal class Version4SharedFields(
    val version: String,
    val flags: BinderFormatFlags,
    val bitBigEndian: Boolean,
    val unicodeNames: Boolean,
    val entrySize: Long,
    val entryCount: Int
)

internal fun BinaryReader.readVersion4SharedFields(magic: String): Version4SharedFields {
    assertValue(magic) { readAscii(magic.length) }

    skip(2)
    assertValue(0) { readByte().toInt() }
    assertValue(0) { readByte().toInt() }
    assertValue(0) { readByte().toInt() }

    val bigEndian = readBoolean()
    val bitBigEndian = !readBoolean()
    assertValue(0) { readByte().toInt() }

    order = if (bigEndian) ByteOrder.BIG_ENDIAN else ByteOrder.LITTLE_ENDIAN
    val entryCount = readInt()
    val version = readFixedString(8)
    val entrySize = readLong()
    skip(8)

    val unicodeNames = readBoolean()
    val flags = BinderFormatFlags.read(this, bitBigEndian)

    skip(1)
    assertValue(0) { readByte().toInt() }
    assertValue(0) { readInt() }
    skip(8)

    return Version4SharedFields(
        version = version,
        flags = flags,
        bitBigEndian = bitBigEndian,
        unicodeNames = unicodeNames,
        entrySize = entrySize,
        entryCount = entryCount
    )
}

internal fun BinaryReader.readVersion4Entry(shared: Version4SharedFields): BinderEntry {
    val entryFlags = BinderEntryFlags.read(this, shared.bitBigEndian)
    assertValue(0) { readByte().toInt() }
    assertValue(0) { readByte().toInt() }
    assertValue(0) { readByte().toInt() }
    assertValue(-1) { readInt() }

    val compressedSize = readLong()
    val uncompressedSize = if (shared.flags.hasCompression) readLong() else -1
    val dataOffset = if (shared.flags.hasLongOffsets) readLong() else readUInt().toLong()
    val id = if (shared.flags.hasIds) readInt() else -1
    val name = if (shared.flags.hasNames) {
        val nameOffset = readUInt().toLong()
        at(nameOffset) {
            if (shared.unicodeNames) readUTF16() else readShiftJIS()
        }
    } else null

    if (shared.flags.bits == 0x20) {
        skip(4)
        assertValue(0) { readInt() }
    }

    return BinderEntry(
        id = id,
        name = name,
        flags = entryFlags,
        dataOffset = dataOffset,
        compressedSize = compressedSize,
        uncompressedSize = uncompressedSize
    )
}