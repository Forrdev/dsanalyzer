package com.sappyoak.dsanalyzer.native.process

import com.sappyoak.dsanalyzer.native.memory.Address

public class CodePatch(public val address: Address, public val bytes: ByteArray) {
    init {
        require(bytes.isNotEmpty()) { "A patch writes at least one byte" }
    }

    public val end: Address get() = address + bytes.size.toLong()

    public operator fun contains(pointer: Long): Boolean = pointer >= address.value && pointer < end.value
}

public interface CodeMemory {
    public fun allocate(size: Int): Address?

    /**
     * Memory a 'rel32' can reach from anywhere within [NEAR_SPAN] of [near]. A 32-bit target
     * reaches everything, which is all the default covers
     */
    public fun allocate(size: Int, near: Address): Address? = allocate(size)

    public fun free(address: Address): Boolean

    /** Lets the game write [size] bytes at [address] itself, such as constants a cave rewrites */
    public fun makeWritable(address: Address, size: Long): Boolean = false

    /** Starts a thread in the game at [entry], passing [parameter]. Returns false when shared threads cannot be started */
    public fun startThread(entry: Address, parameter: Address): Boolean = false

    public fun patch(patches: List<CodePatch>): Boolean
}

/** How far from the address asked for a near block may land, leaving a module's length of slack under 2 GB */
public const val NEAR_SPAN: Long = 0x7000_0000L

/** Windows hands out address space in steps of this */
public const val ALLOCATION_STEP: Long = 0x1_0000L

/**
 * Where a block of [size] goes in the free-range [base]..[end] so that it stays inside
 * [low]..[high], or null if it can't, [fromTop] takes the highest such place, which is the
 * nearest one when the range lies below the address being reached
 */
public fun placeIn(
    base: Long,
    end: Long,
    size: Long,
    low: Long,
    high: Long,
    fromTop: Boolean
): Long? {
    val first = alignUp(maxOf(base, low))
    val last = alignDown(minOf(end, high) - size)
    return when {
        first > last -> null
        fromTop -> last
        else -> first
    }
}

private fun alignDown(value: Long): Long = value and (ALLOCATION_STEP - 1).inv()
private fun alignUp(value: Long): Long = alignDown(value + ALLOCATION_STEP - 1)

public fun CodeMemory.patch(
    address: Address,
    bytes: ByteArray
): Boolean = patch(listOf(CodePatch(address, bytes)))
