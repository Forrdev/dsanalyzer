package com.sappyoak.dsanalyzer.formats.tae

import com.sappyoak.dsanalyzer.shared.binary.BinaryFormatException
import com.sappyoak.dsanalyzer.shared.binary.BinaryReader

/** An event's data block opens with its type, then a pointer to its parameters */
private const val EVENT_TYPE_SIZE = 4

/** The size of one entry in the animation table: an id and an offset */
private const val ANIMATION_ENTRY = 8

private const val SOURCE_OWN = 0
private const val SOURCE_IMPORTED = 1

/**
 * Parameter lengths are not stored: an event's parameters run from its data block to whatever comes
 * next. Every offset in the file that could be "next" is collected, across every animation, and the
 * nearest one wins. Taking them from one animation alone let its last event run on through the blocks
 * of the animations after it
 */
internal fun readAnimations(reader: BinaryReader, tableOffset: Int, count: Int): List<TaeAnimation> {
    val layouts = List(count) { index ->
        reader.at(tableOffset.toLong() + index * ANIMATION_ENTRY) {
            val animationId = readInt().toLong()
            readLayout(animationId, readInt())
        }
    }
    val boundaries = buildList {
        add(tableOffset)
        add(reader.size.toInt())
        layouts.forEach { addAll(it.offsets) }
    }.filter { it > 0 }.distinct().sorted()

    return layouts.map { reader.readAnimation(it, boundaries) }
}


/** Where an animation's blocks are, read before any event so the boundaries can span the file */
private class AnimationLayout(
    val id: Long,
    val offset: Int,
    val headersOffset: Int,
    val headers: List<EventHeader>,
    val groupsOffset: Int,
    val timesOffset: Int,
    val sourceOffset: Int
) {
    val offsets: List<Int>
        get() = headers.map { it.dataOffset } + listOf(offset, headersOffset, groupsOffset, timesOffset, sourceOffset)
}

private fun BinaryReader.readLayout(id: Long, offset: Int): AnimationLayout = at(offset.toLong()) {
    val eventCount = readInt()
    val headersOffset = readInt()
    skip(4) // event group count
    val groupsOffset = readInt()
    skip(4) // times count
    val timesOffset = readInt()
    AnimationLayout(
        id = id,
        offset = offset,
        headersOffset = headersOffset,
        headers = at(headersOffset.toLong()) { List(eventCount) { readEventHeader() } },
        groupsOffset = groupsOffset,
        timesOffset = timesOffset,
        sourceOffset = readInt()
    )
}

private fun BinaryReader.readAnimation(layout: AnimationLayout, boundaries: List<Int>): TaeAnimation {
    val (source, fileName) = readSource(layout.sourceOffset)
    return TaeAnimation(
        id = layout.id,
        source = source,
        fileName = fileName,
        events = layout.headers.map { readEvent(it, boundaries) }
    )
}

private class EventHeader(val startTimeOffset: Int, val endTimeOffset: Int, val dataOffset: Int)


private fun BinaryReader.readEventHeader() = EventHeader(
    startTimeOffset = readInt(),
    endTimeOffset = readInt(),
    dataOffset = readInt()
)

/** A null pointer to the parameters means the event has none, as a Blend at the end of a file can */
private fun BinaryReader.readEvent(header: EventHeader, boundaries: List<Int>): TaeEvent {
    val start = at(header.dataOffset.toLong() + EVENT_TYPE_SIZE) { readInt() }
    val end = boundaries.firstAbove(start) ?: start

    return TaeEvent(
        type = at(header.dataOffset.toLong()) { readInt() },
        startTime = at(header.startTimeOffset.toLong()) { readFloat() },
        endTime = at(header.endTimeOffset.toLong()) { readFloat() },
        params = if (start == 0 || end <= start) EventParams.Empty else {
            EventParams(at(start.toLong()) { readBytes(end - start) })
        }
    )
}

private fun BinaryReader.readSource(offset: Int): Pair<AnimationSource, String?> {
    if (offset <= 0) return AnimationSource.Own(false, false, false, 0) to null

    return at(offset.toLong()) {
        val kind = readInt()
        val dataOffset = readInt()

        when {
            dataOffset <= 0 && kind == SOURCE_IMPORTED -> AnimationSource.Imported(0, 0) to null
            dataOffset <= 0 -> AnimationSource.Own(false, false, false, 0) to null
            else -> at(dataOffset.toLong()) { readSourceData(kind, offset) }
        }
    }
}

/**
 * The data block opens with the offset of the '.hkx' name. An import still writes one, but it points
 * just past its own block, at the next animation's source or the end of the file, so it is not read
 */
private fun BinaryReader.readSourceData(kind: Int, sourceOffset: Int): Pair<AnimationSource, String?> {
    val fileNameOffset = readInt()

    return when (kind) {
        SOURCE_IMPORTED -> AnimationSource.Imported(fromAnimationId = readInt(), unknown = readInt()) to null
        SOURCE_OWN -> AnimationSource.Own(
            loopsByDefault = readBoolean(),
            importsHkx = readBoolean(),
            allowsDelayLoad = readBoolean(),
            hkxSourceAnimationId = skip(1).readInt()
        ) to if (fileNameOffset > 0) at(fileNameOffset.toLong()) { readUTF16() } else null
        else -> throw BinaryFormatException("Unknown animation source $kind", sourceOffset.toLong())
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
