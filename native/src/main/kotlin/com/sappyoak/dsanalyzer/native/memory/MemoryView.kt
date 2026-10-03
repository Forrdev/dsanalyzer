package com.sappyoak.dsanalyzer.native.memory

import java.lang.foreign.Arena
import java.lang.foreign.MemorySegment
import java.lang.foreign.ValueLayout.*

import com.sappyoak.dsanalyzer.native.process.ProcessMemory
import com.sappyoak.dsanalyzer.shared.platform.PointerSize

/**
 * A reusable local copy of a span of another process's memory read by offset.
 * This is a view that is intended to be refreshed at some rate by called [refresh].
 */
public class MemoryView(public val capacity: Int) {
    /** Freed by the collector once the view is unreachable, so there is nothing to close */
    private val buffer: MemorySegment = Arena.ofAuto().allocate(capacity.toLong())

    public var length: Int = 0
        private set

    private var pointerSize: PointerSize = PointerSize.LongPointer

    /** Copies [size] bytes from [address]. On failure the view reads as empty */
    public fun refresh(memory: ProcessMemory, address: Address, size: Int = capacity): Boolean {
        require(size in 0..capacity) { "Cannot read $size bytes into a view of $capacity" }
        val result = memory.read(address, buffer.asSlice(0, size.toLong()))

        length = if (result) size else 0
        pointerSize = memory.pointerSize
        return result
    }

    /** Forgets the last refresh, so nothing reads out of it until the next one */
    public fun clear() {
        length = 0
    }

    public fun byte(offset: Int): Byte = buffer.get(JAVA_BYTE, checked(offset, 1))
    public fun short(offset: Int): Short = buffer.get(JAVA_SHORT_UNALIGNED, checked(offset, 2))
    public fun int(offset: Int): Int = buffer.get(JAVA_INT_UNALIGNED, checked(offset, 4))
    public fun long(offset: Int): Long = buffer.get(JAVA_LONG_UNALIGNED, checked(offset, 8))
    public fun float(offset: Int): Float = buffer.get(JAVA_FLOAT_UNALIGNED, checked(offset, 4))

    public fun pointer(offset: Int): Address = Address(
        if (pointerSize == PointerSize.IntPointer) int(offset).toUInt().toLong() else long(offset)
    )

    private fun checked(offset: Int, size: Int): Long {
        check(offset >= 0 && offset + size <= length) {
            "Read of $size bytes at $offset is outside the $length bytes of this current views refresh"
        }
        return offset.toLong()
    }
}