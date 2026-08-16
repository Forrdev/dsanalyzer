package com.sappyoak.dsanalyzer.shared.io

import com.sappyoak.dsanalyzer.shared.geometry.Vec2
import com.sappyoak.dsanalyzer.shared.geometry.Vec3
import java.nio.ByteBuffer
import java.nio.ByteOrder

fun ByteArray.toReader(littleEndian: Boolean = true, position: Int = 0): BinaryReader =
    BinaryReader(this, littleEndian, position)

class BinaryReader(
    val bytes: ByteArray,
    littleEndian: Boolean = true,
    var position: Int = 0
) {
    @PublishedApi
    internal val buffer: ByteBuffer = ByteBuffer.wrap(bytes).also {
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
            error("Requested $count bytes but only $remaining bytes available")
        }
    }

    fun seek(offset: Int): BinaryReader {
        position = offset
        return this
    }

    fun skip(count: Int): BinaryReader {
        position += count
        return this
    }

    fun <T> operate(block: BinaryReader.() -> T): T {
        val storedPosition = position
        val result = block()
        position = storedPosition
        return result
    }

    fun at(offset: Int): BinaryReader = BinaryReader(bytes, littleEndian, offset)

    fun u8(): Int {
        val value = if (requestBytes(1)) buffer.get(position).unsigned else 0
        position++
        return value
    }

    fun u16(): Int {
        val value = if (requestBytes(2)) buffer.getShort(position).unsigned else 0
        position += 2
        return value
    }

    fun u32(): Long = i32().unsigned

    fun i8(): Int {
        val value = if (requestBytes(1)) buffer.get(position).toInt() else 0
        position++
        return value
    }

    fun i16(): Int {
        val value = if (requestBytes(2)) buffer.getShort(position).toInt() else 0
        position += 2
        return value
    }

    fun i32(): Int {
        val value = if (requestBytes(4)) buffer.getInt(position) else 0
        position += 4
        return value
    }

    fun i64(): Long {
        val value = if (requestBytes(8)) buffer.getLong(position) else 0L
        position += 8
        return value
    }

    fun f32(): Float {
        val value = if (requestBytes(4)) buffer.getFloat(position) else 0f
        position += 4
        return value
    }

    fun bool(): Boolean = u8() != 0

    fun varInt(wide: Boolean): Int = if (wide) i64().toInt() else i32()

    fun ascii(count: Int): String {
        if (!requestBytes(count)) return ""
        val value = bytes.decodeToString(position, position + count)
        position += count
        return value
    }

    fun fixedString(count: Int): String = ascii(count).substringBefore('\u0000')

    fun cString(maxLength: Int = 512): String {
        val str = bytes.readCString(position, maxLength)
        position += str.length
        return str
    }

    fun wideString(maxLength: Int = 512): String {
        val str = bytes.readWideString(position, littleEndian, maxLength)
        position += str.length * 2
        return str
    }

    fun vec2(): Vec2 = Vec2(f32(), f32())
    fun vec3(): Vec3 = Vec3(f32(), f32(), f32())

    fun slice(offset: Int, length: Int): ByteArray {
        if (offset < 0 || length <= 0) return ByteArray(0)
        val end = (offset + length).coerceAtMost(size)
        if (end <= offset) return ByteArray(0)
        return bytes.copyOfRange(offset, end)
    }
}

fun BinaryReader.u8At(offset: Int): Int =
    if (offset < 0 || offset >= size) 0 else buffer.get(offset).unsigned

fun BinaryReader.u16At(offset: Int): Int =
    if (offset < 0 || offset + 2 >= size) 0 else buffer.getShort(offset).unsigned

fun BinaryReader.u32At(offset: Int): Long = i32At(offset).unsigned

fun BinaryReader.i8At(offset: Int): Int =
    if (offset < 0 || offset >= size) 0 else buffer.get(offset).toInt()

fun BinaryReader.i16At(offset: Int): Int =
    if (offset < 0 || offset + 2 >= size) 0 else buffer.getShort(offset).toInt()

fun BinaryReader.i32At(offset: Int): Int =
    if (offset < 0 || offset + 4 >= size) 0 else buffer.getInt(offset)

fun BinaryReader.i64At(offset: Int): Long =
    if (offset < 0 || offset + 8 >= size) 0L else buffer.getLong(offset)

fun BinaryReader.f32At(offset: Int): Float =
    if (offset < 0 || offset + 4 >= size) 0f else buffer.getFloat(offset)

fun BinaryReader.varIntAt(offset: Int, wide: Boolean): Int =
    if (wide) i64At(offset).toInt() else i32At(offset)

fun BinaryReader.boolAt(offset: Int): Boolean = u8At(offset) != 0

fun BinaryReader.asciiAt(offset: Int, count: Int): String =
    if (offset < 0 || offset + count >= size) "" else bytes.readAscii(offset, count)

fun BinaryReader.fixedStringAt(offset: Int, count: Int): String =
    asciiAt(offset, count).substringBefore('\u0000')

fun BinaryReader.cStringAt(offset: Int, maxLength: Int = 512): String? {
    if (offset <= 0 || offset >= bytes.size) return null
    return bytes.readCString(offset, maxLength).ifEmpty { null }
}

fun BinaryReader.wideStringAt(offset: Int, maxLength: Int = 512): String? {
    if (offset <= 0 || offset >= bytes.size) return null
    return bytes.readWideString(offset, littleEndian, maxLength).ifEmpty { null }
}

fun BinaryReader.vec2At(offset: Int): Vec2 = Vec2(f32At(offset), f32At(offset + 4))
fun BinaryReader.vec3At(offset: Int): Vec3 = Vec3(f32At(offset), f32At(offset + 4), f32At(offset + 8))

