package com.sappyoak.dsanalyzer.formats.msb

import kotlinx.serialization.Serializable

import com.sappyoak.dsanalyzer.shared.binary.BinaryReader
import com.sappyoak.dsanalyzer.shared.binary.readUInt

private const val WORD_BITS = 32
private const val WORD_COUNT = 4

/**
 * A set of up to 128 groups, stored as four 32 bit words with group 0 in the lowest bit of the first word.
 * Parts use these for drawing, display, and navmesh membership where two parts interact through a mask
 * when their groups overlap
 */
@Serializable
public data class GroupMask(private val words: List<UInt>) {
    init {
        require(words.size == WORD_COUNT) { "A group mask has $WORD_COUNT words, not ${words.size}" }
    }

    public operator fun contains(group: Int): Boolean =
        group in 0 until WORD_BITS * WORD_COUNT && (words[group / WORD_BITS] shr (group % WORD_BITS)) and 1u != 0u

    public fun overlaps(other: GroupMask): Boolean = words.zip(other.words).any { (a, b) -> a and b != 0u }
}

public fun BinaryReader.readGroupMask(): GroupMask = GroupMask(List(WORD_COUNT) { readUInt() })