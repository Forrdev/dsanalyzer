package com.sappyoak.dsanalyzer.shared.binary

import java.lang.foreign.MemorySegment
import java.lang.foreign.ValueLayout.*
import java.nio.ByteBuffer
import java.nio.ByteOrder
import java.nio.charset.Charset

/**
 * Mapped by a [MemorySegment] for cases when a [ByteBuffer] is not large enough to hold
 * the bytes due to its 2 gigabyte limit
 */
public class MemorySegmentBinaryReader(
    private val segment: MemorySegment,
    position: Int = 0,
    order: ByteOrder = ByteOrder.LITTLE_ENDIAN,
    public override var pointerSize: PointerSize = PointerSize.IntPointer
) : BinaryReader {
    override var order: ByteOrder = order
        set(value) {
            field = value
            layouts = Layouts(value)
        }

    private var layouts: Layouts = Layouts(order)
    private var cursor: Long = 0
    override var position: Long
        get() = cursor
        set(value) {
            if (value < 0 || value > size) {
                throw BinaryFormatException("Cannot seek outside of buffer", value)
            }
            cursor = value
        }

    override val size = segment.byteSize()
    override val remaining: Long get() = size - cursor

    override fun readByte(): Byte = segment.get(JAVA_BYTE, advance(1))
    override fun readShort(): Short = segment.get(layouts.short, advance(2))
    override fun readInt(): Int = segment.get(layouts.int, advance(4))
    override fun readLong(): Long = segment.get(layouts.long, advance(8))
    override fun readFloat(): Float = segment.get(layouts.float, advance(4))

    override fun readBytes(count: Int): ByteArray =
        segment.asSlice(advance(count.toLong()), count.toLong()).toArray(JAVA_BYTE)

    override fun skip(count: Long): BinaryReader {
        advance(count)
        return this
    }

    override fun seek(offset: Long): BinaryReader {
        position = offset
        return this
    }

    override fun slice(offset: Long, length: Long): BinaryReader {
        if (offset < 0 || length < 0 || offset + length > size) {
            throw BinaryFormatException("Slice of $length bytes runs past the end of the segment", offset)
        }
        return MemorySegmentBinaryReader(
            segment = segment.asSlice(offset, length),
            position = 0,
            order = order,
            pointerSize = pointerSize
        )
    }

    private fun advance(count: Long): Long {
        val start = position
        if (count < 0 || position + count > size) {
            throw BinaryFormatException("Read of $count byte runs past the end of the segment", position)
        }
        position += count
        return start
    }
}

/**
 * Layouts must be the unaligned variants for file reading. Offsets in a file are
 * wherever the format puts them, and the aligned layouts throw on a four byte read at
 * an odd address
 */
private class Layouts(order: ByteOrder) {
    val short: OfShort = JAVA_SHORT_UNALIGNED.withOrder(order)
    val int: OfInt = JAVA_INT_UNALIGNED.withOrder(order)
    val long: OfLong = JAVA_LONG_UNALIGNED.withOrder(order)
    val float: OfFloat = JAVA_FLOAT_UNALIGNED.withOrder(order)
}