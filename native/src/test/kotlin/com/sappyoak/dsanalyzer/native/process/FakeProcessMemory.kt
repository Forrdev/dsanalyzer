package com.sappyoak.dsanalyzer.native.process

import java.lang.foreign.MemorySegment
import java.lang.foreign.ValueLayout
import java.nio.ByteBuffer
import java.nio.ByteOrder

import com.sappyoak.dsanalyzer.native.memory.Address
import com.sappyoak.dsanalyzer.native.memory.AddressRange
import com.sappyoak.dsanalyzer.native.memory.MemoryRegion
import com.sappyoak.dsanalyzer.shared.platform.PointerSize

/**
 * Process memory backed by a byte array mapped at [base]. This is entirely
 * for testing
 */
internal class FakeProcessMemory(
    private val base: Address,
    size: Int,
    override val pointerSize: PointerSize = PointerSize.Companion.LongPointer,
    private val unreadable: List<AddressRange> = emptyList(),
    code: List<AddressRange>? = null
) : ProcessMemory {
    private val bytes = ByteArray(size)
    private val mapped = AddressRange(base, size.toLong())
    private val executable = code ?: listOf(mapped)

    public var reads: Int = 0
        private set

    operator fun set(address: Address, data: ByteArray) {
        data.copyInto(bytes, index(address))
    }

    operator fun set(address: Address, value: Address) {
        val buffer = ByteBuffer.allocate(pointerSize.value).order(ByteOrder.LITTLE_ENDIAN)
        if (pointerSize.intPointer) buffer.putInt(value.value.toInt()) else buffer.putLong(value.value)
        this[address] = buffer.array()
    }

    override fun read(address: Address, length: Int): ByteArray? {
        reads++
        val span = AddressRange(address, length.toLong())
        if (address !in mapped || span.end.value > mapped.end.value) return null
        if (unreadable.any { it.overlaps(span) }) return null
        return bytes.copyOfRange(index(address), index(address) + length)
    }

    override fun read(address: Address, into: MemorySegment): Boolean {
        reads++
        if (!accessible(address, into.byteSize())) return false
        MemorySegment.copy(bytes, index(address), into, ValueLayout.JAVA_BYTE, 0, into.byteSize().toInt())
        return true
    }

    override fun write(address: Address, bytes: ByteArray): Boolean {
        val readValue = read(address, bytes.size)
        reads--
        if (readValue == null) return false
        this[address] = bytes
        return true
    }

    override fun write(address: Address, from: MemorySegment): Boolean {
        if (!accessible(address, from.byteSize())) return false
        this[address] = from.toArray(ValueLayout.JAVA_BYTE)
        return true
    }

    override fun regions(range: AddressRange): List<MemoryRegion> {
        val edges = listOf(mapped) + unreadable + executable
        val boundaries = edges.flatMap { listOf(it.start, it.end) }
            .map { it.value.coerceIn(range.start.value, range.end.value) }
            .distinct()
            .sorted()

        return boundaries.zipWithNext { start, end ->
            val span = AddressRange(Address(start), end - start)
            MemoryRegion(
                range = span,
                readable = unreadable.none { it.overlaps(span) },
                executable = executable.any { it.overlaps(span) }
            )
        }.filter { it.range.size > 0 }
    }

    private fun accessible(address: Address, length: Long): Boolean {
        val span = AddressRange(address, length)
        return address in mapped && span.end.value <= mapped.end.value && unreadable.none { it.overlaps(span) }
    }

    private fun index(address: Address): Int = (address - base).value.toInt()

    private fun AddressRange.overlaps(other: AddressRange): Boolean =
        start.value < other.end.value && other.start.value < end.value
}