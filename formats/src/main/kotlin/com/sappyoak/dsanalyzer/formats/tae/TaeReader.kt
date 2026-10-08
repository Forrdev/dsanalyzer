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

/** An event's data block opens with its type and a pointer to its parameters */
private const val EVENT_DATA_HEADER = 8

/** The size of one entry in the animation table: an id and an offset */
private const val ANIMATION_ENTRY = 8

private const val SOURCE_OWN = 0
private const val SOURCE_IMPORTED = 1

public fun readTae(reader: BinaryReader): Tae {
    reader.order = ByteOrder.LITTLE_ENDIAN
    reader.seek(0)

    val magic = reader.readAscii(MAGIC.length)
    if (magic != MAGIC) throw BinaryFormatException("Not a TAE file, found '$magic'", 0L)

    val bigEndian = reader.readBoolean()
    reader.order = if (bigEndian) ByteOrder.BIG_ENDIAN else ByteOrder.LITTLE_ENDIAN
    reader.skip(2)

    val wide = reader.readBoolean()
    val version = reader.readInt()
    if (wide || version != VERSION) {
        throw BinaryFormatException("TAE version 0x${version.toString(16)} is not Dark Souls", 0L)
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
        animations = List(count) { index ->
            reader.at(animationsOffset.toLong() + index * ANIMATION_ENTRY) {
                val animationId = readInt().toLong()
                readAnimation(animationId, readInt())
            }
        }
    )
}

private fun BinaryReader.nameAt(at: Long): String? = at(at) {
    val offset = readInt()
    if (offset <= 0) null else at(offset.toLong()) { readUTF16() }
}

private fun BinaryReader.readAnimation(id: Long, offset: Int): TaeAnimation = at(offset.toLong()) {
    val eventCount= readInt()
    val eventHeadersOffset= readInt()
    skip(4) // event group count
    val eventGroupsOffset = readInt()
    skip(4) // times count
    val timesOffset = readInt()
    val sourceOffset = readInt()

    val headers = at(eventHeadersOffset.toLong()) { List(eventCount) { readEventHeader() } }

    // Parameter lengths are not stored: an event's parameters run from its data block to whatever
    // comes next, so every offset that could be "next" is collected and the nearest one wins.
    // Deriving it from the following event instead would assume the blocks are laid out in the
    // same order the headers list them, which nothing in the file promises
    val fileEnd = size.toInt()
    val boundaries = buildList {
        headers.forEach { add(it.dataOffset) }
        listOf(eventGroupsOffset, timesOffset, sourceOffset).forEach { if (it > 0) add(it) }
        add(fileEnd)
    }.distinct().sorted()

    val (source, fileName) = readSource(sourceOffset)

    TaeAnimation(
        id = id,
        source = source,
        fileName = fileName,
        events = headers.map { header ->
            readEvent(header, boundaries)
        }
    )
}

private class EventHeader(val startTimeOffset: Int, val endTimeOffset: Int, val dataOffset: Int)


private fun BinaryReader.readEventHeader() = EventHeader(
    startTimeOffset = readInt(),
    endTimeOffset = readInt(),
    dataOffset = readInt()
)

private fun BinaryReader.readEvent(header: EventHeader, boundaries: List<Int>): TaeEvent {
    val start = header.dataOffset + EVENT_DATA_HEADER
    val end = boundaries.firstAbove(start) ?: start

    return TaeEvent(
        type = at(header.dataOffset.toLong()) { readInt() },
        startTime = at(header.startTimeOffset.toLong()) { readFloat() },
        endTime = at(header.endTimeOffset.toLong()) { readFloat() },
        params = if (end <= start) EventParams.Empty else {
            EventParams(at(start.toLong()) { readBytes(end - start) })
        }
    )
}

private fun BinaryReader.readSource(offset: Int): Pair<AnimationSource, String?> {
    if (offset <= 0) return AnimationSource.Own(false, false, false, 0) to null

    return at(offset.toLong()) {
        val kind = readInt()
        val written = readInt() != 0

        val fileNameOffset = if (written) readInt() else 0
        val fileName = if (fileNameOffset > 0) at(fileNameOffset.toLong()) { readUTF16() } else null

        val source = when {
            !written && kind == SOURCE_IMPORTED -> AnimationSource.Imported(0, 0)
            !written -> AnimationSource.Own(false, false, false, 0)
            kind == SOURCE_IMPORTED -> AnimationSource.Imported(
                fromAnimationId = readInt(),
                unknown = readInt()
            )
            kind == SOURCE_OWN -> AnimationSource.Own(
                loopsByDefault = readBoolean(),
                importsHkx = readBoolean(),
                allowsDelayLoad = readBoolean(),
                hkxSourceAnimationId = skip(1).readInt()
            )
            else -> throw BinaryFormatException("Unknown animation source $kind", offset.toLong())
        }

        source to fileName
    }
}

/** The smallest entry greater than [value] over a sorted list */
private fun List<Int>.firstAbove(value: Int): Int? {
    var low = 0
    var high = size

    while (low < high) {
        val middle= (low + high) / 2
        if (this[middle] > value) high = middle else low = middle + 1
    }

    return getOrNull(low)
}