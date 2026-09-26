package com.sappyoak.dsanalyzer.formats.emevd

import com.sappyoak.dsanalyzer.shared.binary.BinaryReader

/**
 * One call in an event, named by the bank it lives in and its id within the bank.
 *
 * [layerMask] is a bitmask of the map layers the instruction runs on, absent when it runs on all
 * of them
 */
public data class Instruction(
    public val bank: Int,
    public val id: Int,
    public val args: ArgData,
    public val layerMask: UInt?
) {
    override fun toString(): String = "$bank[$id]"
}

/**
 * An instruction's argument bytes, undecoded
 */
public class ArgData(private val bytes: ByteArray) {
    public val size: Int get() = bytes.size

    public fun toByteArray(): ByteArray = bytes.copyOf()
    public fun reader(): BinaryReader = BinaryReader.of(bytes)

    override fun equals(other: Any?): Boolean = other is ArgData && bytes.contentEquals(other.bytes)
    override fun hashCode(): Int = bytes.contentHashCode()
    override fun toString(): String = bytes.joinToString(" ") { "%02x".format(it) }

    public companion object {
        public val Empty: ArgData = ArgData(ByteArray(0))
    }
}

/**
 * One substitution into an instruction's arguments
 *
 * [sourceStartByte] counts into the arguments the event was called with, and [targetStartByte]
 * into the instruction's own argument types
 */
public data class Parameter(
    public val instructionIndex: Int,
    public val targetStartByte: Int,
    public val sourceStartByte: Int,
    public val byteCount: Int,
    public val unkId: Int
)