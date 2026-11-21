package com.sappyoak.dsanalyzer.formats.tae

import java.nio.ByteOrder

import com.sappyoak.dsanalyzer.shared.binary.BinaryFormatException
import com.sappyoak.dsanalyzer.shared.binary.BinaryReader
import com.sappyoak.dsanalyzer.shared.binary.assertValue

private const val MAGIC = "TAE "

/** Dark Souls, 32-bit. 0x1000A and 0x10000 are Demon's Souls, 0x1000C and up are later games */
private const val VERSION = 0x1000B

private const val ID_AT = 0x50L
private const val ANIMATION_COUNT_AT = 0x54L
private const val ANIMATIONS_OFFSET_AT = 0x58L
private const val SKELETON_NAME_AT = 0x98L
private const val SIB_NAME_AT = 0x9CL

/**
 * The file states where its own sub-blocks are, and for Dark Souls those are fixed. Checking three
 * of them is a cheap fingerprint for the layout the offsets above assume, and far shorter than
 * replaying every assertion a writer would need
 */
private val LAYOUT_MARKERS = mapOf(0x10L to 0x40, 0x60L to 0x90, 0x94L to 0x98)

public fun readTae(reader: BinaryReader): Tae {
    reader.order = ByteOrder.LITTLE_ENDIAN
    reader.seek(0)

    val magic = reader.readAscii(MAGIC.length)
    if (magic != MAGIC) throw BinaryFormatException("Not a TAE file, found '$magic'", 0L)

    val bigEndian = reader.readBoolean()
    reader.order = if (bigEndian) ByteOrder.BIG_ENDIAN else ByteOrder.LITTLE_ENDIAN
    reader.skip(2)

    val wide = reader.readBoolean()
    val versionAt = reader.position
    val version = reader.readInt()
    if (wide || version != VERSION) {
        throw BinaryFormatException("TAE version 0x${version.toString(16)} is not Dark Souls", versionAt)
    }

    LAYOUT_MARKERS.forEach { (at, expected) -> reader.at(at) {
        assertValue(expected) { readInt() }
    } }

    val id = reader.at(ID_AT) { readInt() }
    val count = reader.at(ANIMATION_COUNT_AT) { readInt() }
    val animationsOffset = reader.at(ANIMATIONS_OFFSET_AT) { readInt() }

    return Tae(
        id = id,
        bigEndian = bigEndian,
        skeletonName = reader.nameAt(SKELETON_NAME_AT),
        sibName = reader.nameAt(SIB_NAME_AT),
        animations = readAnimations(reader, animationsOffset, count)
    )
}

private fun BinaryReader.nameAt(at: Long): String? = at(at) {
    val offset = readInt()
    if (offset <= 0) null else at(offset.toLong()) { readUTF16() }
}
