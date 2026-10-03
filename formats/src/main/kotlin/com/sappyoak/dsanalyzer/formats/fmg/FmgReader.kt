package com.sappyoak.dsanalyzer.formats.fmg

import java.nio.ByteOrder

import com.sappyoak.dsanalyzer.shared.binary.BinaryFormatException
import com.sappyoak.dsanalyzer.shared.binary.BinaryReader
import com.sappyoak.dsanalyzer.shared.binary.assertValue
import com.sappyoak.dsanalyzer.shared.binary.assertZeroByte
import com.sappyoak.dsanalyzer.shared.binary.assertZeroBytes
import com.sappyoak.dsanalyzer.shared.binary.at

/** Dark Souls 1 and 2. 0 is Demon's Souls, 2 is Dark Souls 3 and Bloodborne */
private const val VERSION = 1

/** Little-endian, the flag being a byte rather than a magic value */
private const val LITTLE_ENDIAN = 0

/** Dark Souls text is UTF-16; the flag exists because Demon's Souls text is Shift-JIS */
private const val UNICODE = 1

/** Demon's Souls writes 0xFF in the byte after the encoding flag */
private const val NOT_DEMONS_SOULS = 0

/** Every offset in a Dark Souls FMG is 32 bits. Dark Souls 3 widens them to 64 */
private const val OFFSET_SIZE = 4

/** Reads a string container */
public fun readFmg(reader: BinaryReader): Fmg {
    reader.order = ByteOrder.LITTLE_ENDIAN

    // a non-zero first byte is a 16-byte hash of the rest of the file. Refused rather than skipped past.
    // nothing in this game produces one, so meeting one means the bytes are not the file they were taken for
    if (reader.readByte().toInt() != 0) {
        throw BinaryFormatException("FMG opens with a hash, so these are not Dark Souls strings", 0L)
    }

    reader.assertValue(LITTLE_ENDIAN) { readByte().toInt() }
    reader.assertValue(VERSION) { readByte().toInt() }
    reader.assertZeroByte()

    val declaredSize = reader.readInt()
    if (declaredSize > reader.size) {
        throw BinaryFormatException("FMG claims $declaredSize bytes of ${reader.size}", reader.position)
    }

    reader.assertValue(UNICODE) { readByte().toInt() }
    reader.assertValue(NOT_DEMONS_SOULS) { readByte().toInt() }
    reader.assertZeroBytes(2)

    val groupCount = reader.readInt()
    val stringCount = reader.readInt()
    val offsetsAt = reader.readInt().toLong()
    reader.assertValue(0) { readInt() }

    val groups = List(groupCount) { reader.readGroup() }

    val covered = groups.sumOf { it.count }
    if (covered != stringCount) {
        throw BinaryFormatException("FMG groups cover $covered strings but declare $stringCount", reader.position)
    }

    return Fmg(groups.readStrings(reader, offsetsAt))
}

/**
 * A run of consecutive ids and where in the offsets table the first of them is
 */
private class Group(val offsetIndex: Int, val firstId: Int, val lastId: Int) {
    val count: Int get() = lastId - firstId + 1
}

private fun BinaryReader.readGroup(): Group {
    val at = position
    val group = Group(
        offsetIndex = readInt(),
        firstId = readInt(),
        lastId = readInt()
    )

    if (group.lastId < group.firstId) {
        throw BinaryFormatException("FMG group runs from ${group.firstId} to ${group.lastId}", at)
    }

    return group
}

private fun List<Group>.readStrings(
    reader: BinaryReader,
    offsetsAt: Long
): Map<Int, String> = flatMap { group ->
    val offsets = reader.at(offsetsAt + group.offsetIndex.toLong() * OFFSET_SIZE) {
        List(group.count) { readInt() }
    }

    offsets.mapIndexedNotNull { index, stringAt ->
        if (stringAt <= 0) null else group.firstId + index to reader.at(stringAt.toLong()) { readUTF16() }
    }
}.toMap()