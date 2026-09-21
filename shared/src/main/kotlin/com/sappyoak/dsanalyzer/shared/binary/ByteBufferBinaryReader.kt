package com.sappyoak.dsanalyzer.shared.binary

import java.nio.ByteBuffer
import java.nio.ByteOrder

import com.sappyoak.dsanalyzer.shared.platform.PointerSize

class ByteBufferBinaryReader(
    private val bytes: ByteArray,
    position: Int = 0,
    order: ByteOrder = ByteOrder.LITTLE_ENDIAN,
    override var pointerSize: PointerSize = PointerSize.IntPointer
) : BinaryReader {
    private val buffer = ByteBuffer.wrap(bytes).also {
        it.order(order)
    }

    override var order: ByteOrder = order
        set(value) {
            field = value
            buffer.order(value)
        }

    private var intPosition: Int = position
    override var position: Long = position.toLong()
        set(value) {
            field = value
            intPosition = value.toInt()
        }

    override val size: Long get() = bytes.size.toLong()
    override val remaining: Long get() = size - position

    override fun readByte(): Byte = buffer.get(advance(1))
    override fun readShort(): Short = buffer.getShort(advance(2))
    override fun readInt(): Int = buffer.getInt(advance(4))
    override fun readLong(): Long = buffer.getLong(advance(8))
    override fun readFloat(): Float = buffer.getFloat(advance(4))

    override fun readBytes(count: Int): ByteArray {
        val end = (intPosition + count).coerceAtMost(bytes.size)
        return bytes.copyOfRange(intPosition, end)
    }

    override fun skip(count: Long): BinaryReader {
        advance(count)
        return this
    }

    override fun seek(offset: Long): BinaryReader {
        position = offset
        return this
    }

    override fun slice(offset: Long, length: Long): BinaryReader {
        if (offset < 0 || length <= 0 || offset + length > size) {
            throw BinaryFormatException("Slice of $length bytes runs past end of the buffer", offset)
        }

        val end = (offset + length).toInt()
        return ByteBufferBinaryReader(
            bytes = bytes.copyOfRange(offset.toInt(), end),
            position = 0,
            order = order,
            pointerSize = pointerSize
        )
    }

    private fun advance(count: Long): Int {
        val start = intPosition
        if (count < 0 || count + position > size) {
            throw BinaryFormatException("Read of $count bytes runs past the end of the buffer", position)
        }
        position += count
        return start
    }
}