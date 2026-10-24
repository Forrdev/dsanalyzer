package com.sappyoak.dsanalyzer.formats.havok.tagfile

import com.sappyoak.dsanalyzer.shared.binary.BinaryFormatException
import com.sappyoak.dsanalyzer.shared.binary.BinaryReader

/** A section header's big-endian word: two flag bits, then the section's size including the header */
private const val SIZE_MASK = 0x3FFF_FFFF
private const val HEADER_SIZE = 8

/** One section's body: from just after its header to its end */
internal class TagSection(val tag: String, val start: Int, val end: Int)

/** The sections laid end to end between [start] and [end], by tag */
internal fun BinaryReader.sectionsIn(start: Int, end: Int): Map<String, TagSection> {
    val found = LinkedHashMap<String, TagSection>()
    var at = start
    while (at < end) {
        val word = at(at.toLong()) { readBytes(4) }.fold(0) { word, byte -> (word shl 8) or (byte.toInt() and 0xFF) }
        val size = word and SIZE_MASK
        val tag = at((at + 4).toLong()) { readFixedString(4) }
        if (size < HEADER_SIZE || at + size > end) throw BinaryFormatException("Tagfile section $tag runs past its parent", at)
        found[tag] = TagSection(tag, at + HEADER_SIZE, at + size)
        at += size
    }
    return found
}

internal fun Map<String, TagSection>.require(tag: String): TagSection =
    this[tag] ?: throw BinaryFormatException("Tagfile has no $tag section", 0)

/**
 * The tagfile's packed integers: the leading bits of the first byte say how many bytes follow,
 * and the value is big endian across what is left
 */
internal class PackedInts(private val bytes: ByteArray, var at: Int) {
    fun next(): Long {
        val first = bytes[at++].toInt() and 0xFF
        return when {
            first and 0x80 == 0 -> first.toLong()
            first and 0xC0 == 0x80 -> following(first and 0x3F, 1)
            first and 0xE0 == 0xC0 -> following(first and 0x1F, 2)
            first and 0xF8 == 0xE0 -> following(first and 0x07, 3)
            first and 0xF8 == 0xE8 -> following(first and 0x07, 4)
            first and 0xF8 == 0xF0 -> following(first and 0x07, 7)
            else -> following(0, 8)
        }
    }

    fun nextInt(): Int = next().toInt()

    private fun following(high: Int, count: Int): Long {
        var value = high.toLong()
        repeat(count) { value = (value shl 8) or (bytes[at++].toLong() and 0xFF) }
        return value
    }
}
