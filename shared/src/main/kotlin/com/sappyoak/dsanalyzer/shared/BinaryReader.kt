package com.sappyoak.dsanalyzer.shared

import java.nio.ByteBuffer
import java.nio.ByteOrder

fun ByteArray.toReader(
    littleEndian: Boolean = true,
    position: Int = 0,
    lenient: Boolean = true
): BinaryReader =
    if (lenient) LenientBinaryReader(this, littleEndian, position)
    else StrictBinaryReader(this, littleEndian, position)

/**
 * Random-access, endian-aware reader over a byte array backed by [ByteBuffer]
 *
 * [lenient] controls whether reads past the end return placeholder data and advances the
 * position rather than throwing. Most of the parsers themselves should validate structurally,
 * and an exception mid-parse would lose the partial state those checks work from
 */
abstract class BinaryReader(
    protected val bytes: ByteArray,
    littleEndian: Boolean = true,
    var position: Int = 0
) {
    protected val buffer: ByteBuffer = ByteBuffer.wrap(bytes).also {
        it.order(if (littleEndian) ByteOrder.LITTLE_ENDIAN else ByteOrder.BIG_ENDIAN)
    }

    var littleEndian: Boolean = littleEndian
        set(value) {
            field = value
            buffer.order(if (value) ByteOrder.LITTLE_ENDIAN else ByteOrder.BIG_ENDIAN)
        }

    val size: Int get() = bytes.size
    val remaining: Int get() = bytes.size - position

    fun requestBytes(count: Int): Boolean = position >= 0 && position + count <= size
    fun requireBytes(count: Int) {
        if (!requestBytes(count)) {
            error("Reqeusted $count bytes but only $remaining bytes available")
        }
    }

    protected abstract fun <T> readCheck(size: Int, default: T): T?

    abstract fun seek(offset: Int): BinaryReader
    abstract fun skip(count: Int): BinaryReader
    abstract fun at(offset: Int): BinaryReader

    fun u8(): Int {
        readCheck(1, 0)?.let { return it }
        return buffer.get(position++).toInt() and 0xFF
    }

    fun u16(): Int {
        readCheck(2, 0)?.let { return it }
        val value = buffer.getShort(position).toInt() and 0xFFFF
        position += 2
        return value
    }

    fun u32(): Long = i32().toLong() and 0xFFFFFFFFL

    fun i8(): Int {
        readCheck(1, 0)?.let { return it }
        return buffer.get(position++).toInt()
    }

    fun i16(): Int {
        readCheck(2, 0)?.let { return it }
        val value = buffer.getShort(position).toInt()
        position += 2
        return value
    }

    fun i32(): Int {
        readCheck(4, 0)?.let { return it }
        val value = buffer.getInt(position)
        position += 4
        return value
    }

    fun i64(): Long {
        readCheck(8, 0L)?.let { return it }
        val value = buffer.getLong(position)
        position += 8
        return value
    }

    fun f32(): Float {
        readCheck(4, 0f)?.let { return it }
        val value = buffer.getFloat(position)
        position += 4
        return value
    }

    fun varint(wide: Boolean): Int = if (wide) i64().toInt() else i32()
    fun ascii(count: Int): String {
        readCheck(count, "")?.let { return it }
        val value = bytes.decodeToString(position, position + count)
        position += count
        return value
    }

    fun fixedString(count: Int): String = ascii(count).substringBefore('\u0000')

    fun cStringAt(offset: Int, maxLength: Int = 512): String? {
        if (offset <= 0 || offset >= bytes.size) return null
        val builder = StringBuilder()
        var index = offset
        while (index < bytes.size && builder.length < maxLength) {
            val byte = bytes[index].toInt() and 0xFF
            if (byte == 0 || byte > 0x80) break
            builder.append(byte.toChar())
            index++
        }

        return builder.toString().ifEmpty { null }
    }

    fun wideStringAt(offset: Int, maxLength: Int = 512): String? {
        if (offset <= 0 || offset >= bytes.size) return null
        var end = offset
        while (end + 1 < bytes.size && end - offset < maxLength * 2) {
            if (bytes[end].toInt() == 0 && bytes[end + 1].toInt() == 0) break
            end += 2
        }
        if (end <= offset) return null

        return String(bytes, offset, end - offset, if (littleEndian) Charsets.UTF_16LE else Charsets.UTF_16BE).ifEmpty { null }
    }

    fun slice(offset: Int, count: Int): ByteArray {
        if (offset < 0 || count <= 0) return ByteArray(0)
        val end = (offset + count).coerceAtMost(size)
        if (end <= offset) return ByteArray(0)
        return bytes.copyOfRange(offset, end)
    }

    fun lenient(at: Int = position): BinaryReader =
        LenientBinaryReader(bytes, littleEndian, at)

    fun strict(at: Int = position): BinaryReader =
        StrictBinaryReader(bytes, littleEndian, at)

    companion object {
        operator fun invoke(
            bytes: ByteArray,
            littleEndian: Boolean = true,
            position: Int = 0
        ): BinaryReader = LenientBinaryReader(bytes, littleEndian, position)
    }
}

class LenientBinaryReader(
    bytes: ByteArray,
    littleEndian: Boolean = true,
    position: Int = 0
) : BinaryReader(bytes, littleEndian, position) {
    override fun <T> readCheck(size: Int, default: T): T? {
        if (!requestBytes(size)) {
            position += size
            return default
        }

        return null
    }

    override fun seek(offset: Int): BinaryReader {
        position = offset
        return this
    }

    override fun skip(count: Int): BinaryReader {
        position += count
        return this
    }

    override fun at(offset: Int): BinaryReader = LenientBinaryReader(bytes, littleEndian, offset)
}

class StrictBinaryReader(
    bytes: ByteArray,
    littleEndian: Boolean = true,
    position: Int = 0
): BinaryReader(bytes, littleEndian, position) {
    override fun <T> readCheck(size: Int, default: T): T? {
        requireBytes(size)
        return null
    }

    override fun seek(offset: Int): BinaryReader {
        if (offset > size) error("Cannot seek past the end of the reader")
        position = offset
        return this
    }

    override fun skip(count: Int): BinaryReader {
        if (position + count > size) error("Cannot skip past the end of the reader")
        position += count
        return this
    }

    override fun at(offset: Int): BinaryReader {
        if (offset > size) error("Cannot slice a new reader past the end of the current one")
        return StrictBinaryReader(bytes, littleEndian, offset)
    }
}