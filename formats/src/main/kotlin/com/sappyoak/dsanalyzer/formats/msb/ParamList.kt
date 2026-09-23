package com.sappyoak.dsanalyzer.formats.msb

import com.sappyoak.dsanalyzer.shared.binary.BinaryFormatException
import com.sappyoak.dsanalyzer.shared.binary.BinaryReader
import com.sappyoak.dsanalyzer.shared.binary.assertValue

/**
 * Reads the param list at the current position and leaves the reader at the next one
 *
 * A list is a zero, the offset of its name, a count, and then that many offsets (one per entry
 * and a final one to the next list, which is zero for the last list in the file). [readEntry] runs
 * with the reader at the start of each entry, and every offset an entry counts from there
 */
internal fun <T> BinaryReader.readParamList(
    name: String,
    isLast: Boolean,
    readEntry: BinaryReader.() -> T
): List<T> {
    assertValue(0) { readInt() }
    val nameOffset = readRequiredOffset()
    val offsets = List(readInt()) { readInt().toLong() }

    assertValue(name) { at(nameOffset) { readAscii() } }

    val entries = offsets.dropLast(1).map { at(it) { readEntry() } }
    val next = offsets.last()

    when {
        isLast -> if (next != 0L) throw BinaryFormatException("$name is the last list but points to another", next)
        next == 0L -> throw BinaryFormatException("$name ends the file early", position)
        else -> position = next
    }

    return entries
}
