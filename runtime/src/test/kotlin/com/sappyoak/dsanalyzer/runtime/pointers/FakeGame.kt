package com.sappyoak.dsanalyzer.runtime.pointers

import java.lang.foreign.MemorySegment
import java.lang.foreign.ValueLayout.JAVA_BYTE
import java.nio.ByteBuffer
import java.nio.ByteOrder

import com.sappyoak.dsanalyzer.native.memory.Address
import com.sappyoak.dsanalyzer.native.memory.AddressRange
import com.sappyoak.dsanalyzer.native.memory.MemoryRegion
import com.sappyoak.dsanalyzer.native.process.ProcessMemory
import com.sappyoak.dsanalyzer.shared.platform.PointerSize

/**
 * A 32 bit process laid out by hand, so a table of signatures and offsets can be exercised
 * without a game running. Offsets are from the module base, which is how the tables read
 */
internal class FakeGame(
    size: Int = 0x40000,
    private val base: Address = Address(0x400000)
) : ProcessMemory {
    private val bytes = ByteArray(size)

    override val pointerSize: PointerSize = PointerSize.IntPointer

    val module: AddressRange = AddressRange(base, size.toLong())

    var reads: Int = 0
        private set

    fun address(offset: Int): Address = base + offset.toLong()

    fun write(offset: Int, vararg values: Int) {
        values.forEachIndexed { index, value -> bytes[offset + index] = value.toByte() }
    }

    /** A pointer stored at [offset] naming the module offset [target] */
    fun pointer(offset: Int, target: Int) = int(offset, address(target).value.toInt())

    /** A pointer stored at [offset] naming nothing, which is what a freed structure leaves */
    fun nullPointer(offset: Int) = int(offset, 0)

    fun int(offset: Int, value: Int) {
        ByteBuffer.wrap(bytes, offset, Int.SIZE_BYTES).order(ByteOrder.LITTLE_ENDIAN).putInt(value)
    }

    fun float(offset: Int, value: Float) {
        ByteBuffer.wrap(bytes, offset, Float.SIZE_BYTES).order(ByteOrder.LITTLE_ENDIAN).putFloat(value)
    }

    override fun read(address: Address, length: Int): ByteArray? {
        reads++
        val at = index(address) ?: return null
        if (at + length > bytes.size) return null
        return bytes.copyOfRange(at, at + length)
    }

    override fun read(address: Address, into: MemorySegment): Boolean {
        reads++
        val at = index(address) ?: return false
        val length = into.byteSize().toInt()
        if (at + length > bytes.size) return false
        MemorySegment.copy(bytes, at, into, JAVA_BYTE, 0, length)
        return true
    }

    override fun write(address: Address, bytes: ByteArray): Boolean {
        val at = index(address) ?: return false
        if (at + bytes.size > this.bytes.size) return false
        bytes.copyInto(this.bytes, at)
        return true
    }

    override fun write(address: Address, from: MemorySegment): Boolean =
        write(address, from.toArray(JAVA_BYTE))

    /** One region covering everything, which the game's own module is close enough to */
    override fun regions(range: AddressRange): List<MemoryRegion> {
        val start = maxOf(module.start.value, range.start.value)
        val end = minOf(module.end.value, range.end.value)
        if (start >= end) return emptyList()
        return listOf(MemoryRegion(AddressRange(Address(start), end - start), readable = true, executable = true))
    }

    private fun index(address: Address): Int? {
        val at = (address - base).value
        return if (at in 0 until bytes.size.toLong()) at.toInt() else null
    }
}