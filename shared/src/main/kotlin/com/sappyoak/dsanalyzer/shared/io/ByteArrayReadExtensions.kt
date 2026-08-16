package com.sappyoak.dsanalyzer.shared.io

import com.sappyoak.dsanalyzer.shared.geometry.Vec2
import com.sappyoak.dsanalyzer.shared.geometry.Vec3

/**************************
 * Regular primitive types
 **************************/

fun ByteArray.readByte(offset: Int): Byte = this[offset]
fun ByteArray.readUByte(offset: Int): UByte = this[offset].toUByte()

fun ByteArray.readShort(offset: Int, littleEndian: Boolean): Short =
    if (littleEndian) readShortLE(offset) else readShortBE(offset)
fun ByteArray.readUShort(offset: Int, littleEndian: Boolean): UShort = readShort(offset, littleEndian).toUShort()

fun ByteArray.readInt(offset: Int, littleEndian: Boolean): Int =
    if (littleEndian) readIntLE(offset) else readIntBE(offset)
fun ByteArray.readUInt(offset: Int, littleEndian: Boolean): UInt = readInt(offset, littleEndian).toUInt()

fun ByteArray.readLong(offset: Int, littleEndian: Boolean): Long =
    if (littleEndian) readLongLE(offset) else readLongBE(offset)
fun ByteArray.readULong(offset: Int, littleEndian: Boolean): ULong = readLong(offset, littleEndian).toULong()

fun ByteArray.readFloat(offset: Int, littleEndian: Boolean): Float =
    if (littleEndian) readFloatLE(offset) else readFloatBE(offset)

fun ByteArray.readDouble(offset: Int, littleEndian: Boolean): Double =
    if (littleEndian) readDoubleLE(offset) else readDoubleBE(offset)

fun ByteArray.readVarInt(offset: Int, wide: Boolean, littleEndian: Boolean): Long =
    if (wide) readLong(offset, littleEndian) else readInt(offset, littleEndian).unsigned

fun ByteArray.readBoolean(offset: Int): Boolean = readByte(offset).unsigned != 0

fun ByteArray.readAscii(offset: Int, count: Int): String =
    decodeToString(offset, offset + count)

fun ByteArray.readFixedString(offset: Int, count: Int): String =
    readAscii(offset, count).substringBefore('\u0000')

/**
 * Null-terminated string. Stops at any byte at or above 0x80 rather than attempting Shift-J15.
 */
fun ByteArray.readCString(offset: Int, maxLength: Int = 512): String {
    val builder = StringBuilder()
    var index = offset
    while (index < size && builder.length < maxLength) {
        val byte = this[index].unsigned
        if (byte == 0 || byte >= 0x80) break
        builder.append(byte.toChar())
        index++
    }

    return builder.toString()
}

fun ByteArray.readWideString(offset: Int, littleEndian: Boolean, maxLength: Int = 512): String {
    var index = offset
    while (index + 1 < size && index - offset < maxLength * 2) {
        if (this[index].unsigned == 0 && this[index + 1].unsigned == 0) break
        index += 2
    }
    if (index <= offset) return ""
    return String(
        this,
        offset,
        index - offset,
        if (littleEndian) Charsets.UTF_16LE else Charsets.UTF_16BE
    )
}

/***************
 * Array Types
 ***************/
fun ByteArray.readBooleanArray(offset: Int, size: Int): BooleanArray = getTypedArray(offset, size, 1, BooleanArray(size)) {
    arr, n, pos -> arr[n] = readBoolean(pos)
}

fun ByteArray.readShortArray(offset: Int, size: Int, littleEndian: Boolean): ShortArray =
    if (littleEndian) readShortArrayLE(offset, size) else readShortArrayBE(offset, size)
fun ByteArray.readUShortArray(offset: Int, size: Int, littleEndian: Boolean): UShortArray =
    readShortArray(offset, size, littleEndian).map { it.toUShort() }.toUShortArray()

fun ByteArray.readIntArray(offset: Int, size: Int, littleEndian: Boolean): IntArray =
    if (littleEndian) readIntArrayBE(offset, size) else readIntArrayLE(offset, size)
fun ByteArray.readUIntArray(offset: Int, size: Int, littleEndian: Boolean): UIntArray =
    readIntArray(offset, size, littleEndian).map { it.toUInt() }.toUIntArray()

fun ByteArray.readLongArray(offset: Int, size: Int, littleEndian: Boolean): LongArray =
    if (littleEndian) readLongArrayLE(offset, size) else readLongArrayBE(offset, size)
fun ByteArray.readULongArray(offset: Int, size: Int, littleEndian: Boolean): ULongArray =
    readLongArray(offset, size, littleEndian).map { it.toULong() }.toULongArray()

fun ByteArray.readFloatArray(offset: Int, size: Int, littleEndian: Boolean): FloatArray =
    if (littleEndian) readFloatArrayLE(offset, size) else readFloatArrayBE(offset, size)

fun ByteArray.readDoubleArray(offset: Int, size: Int, littleEndian: Boolean): DoubleArray =
    if (littleEndian) readDoubleArrayLE(offset, size) else readDoubleArrayBE(offset, size)


/****************
 * Special Types
 ****************/
fun ByteArray.readVec2(offset: Int, littleEndian: Boolean): Vec2 = Vec2(
    x = readFloat(offset, littleEndian),
    y = readFloat(offset + 4, littleEndian)
)

fun ByteArray.readVec3(offset: Int, littleEndian: Boolean): Vec3 = Vec3(
    x = readFloat(offset, littleEndian),
    y = readFloat(offset + 4, littleEndian),
    z = readFloat(offset + 8, littleEndian)
)

/***************
 * Format types
 ***************/
fun ByteArray.u8(offset: Int): Int = readByte(offset).unsigned
fun ByteArray.u16(offset: Int, littleEndian: Boolean): Int = readShort(offset, littleEndian).unsigned
fun ByteArray.u32(offset: Int, littleEndian: Boolean): Long = readInt(offset, littleEndian).unsigned
fun ByteArray.u64(offset: Int, littleEndian: Boolean): Double = readULong(offset, littleEndian).toLong().interpretAsDouble()

fun ByteArray.i8(offset: Int): Int = readByte(offset).toInt()
fun ByteArray.i16(offset: Int, littleEndian: Boolean): Int = readShort(offset, littleEndian).toInt()
fun ByteArray.i32(offset: Int, littleEndian: Boolean): Int = readInt(offset, littleEndian)
fun ByteArray.i64(offset: Int, littleEndian: Boolean): Long = readLong(offset, littleEndian)
fun ByteArray.f32(offset: Int, littleEndian: Boolean): Float = readFloat(offset, littleEndian)

fun ByteArray.readShortBE(offset: Int): Short =
    (this[offset].unsigned shl 8 or (this[offset + 1].unsigned)).toShort()

fun ByteArray.readShortLE(offset: Int): Short =
    (this[offset].unsigned or (this[offset + 1].unsigned shl 8)).toShort()

fun ByteArray.readIntBE(offset: Int): Int = (
    this[offset].unsigned shl 24
        or (this[offset + 1].unsigned shl 16)
        or (this[offset + 2].unsigned shl 8)
        or (this[offset + 3].unsigned)
)

fun ByteArray.readIntLE(offset: Int): Int = (
    this[offset].unsigned
        or (this[offset + 1].unsigned shl 8)
        or (this[offset + 2].unsigned shl 16)
        or (this[offset + 3].unsigned shl 24)
)

fun ByteArray.readLongBE(offset: Int): Long = (
    this[offset].unsigned shl 56
        or (this[offset + 1].unsigned shl 48)
        or (this[offset + 2].unsigned shl 40)
        or (this[offset + 3].unsigned shl 32)
        or (this[offset + 4].unsigned shl 24)
        or (this[offset + 5].unsigned shl 16)
        or (this[offset + 6].unsigned shl 8)
        or (this[offset + 7].unsigned)
).toLong()

fun ByteArray.readLongLE(offset: Int): Long = (
    this[offset].unsigned
        or (this[offset + 1].unsigned shl 8)
        or (this[offset + 2].unsigned shl 16)
        or (this[offset + 3].unsigned shl 24)
        or (this[offset + 4].unsigned shl 32)
        or (this[offset + 5].unsigned shl 40)
        or (this[offset + 6].unsigned shl 48)
        or (this[offset + 7].unsigned shl 56)
).toLong()

fun ByteArray.readFloatBE(offset: Int): Float = readIntBE(offset).interpretAsFloat()
fun ByteArray.readFloatLE(offset: Int): Float = readIntLE(offset).interpretAsFloat()

fun ByteArray.readDoubleBE(offset: Int): Double = readLongBE(offset).interpretAsDouble()
fun ByteArray.readDoubleLE(offset: Int): Double = readLongLE(offset).interpretAsDouble()

@PublishedApi
internal inline fun <T> ByteArray.getTypedArray(
    offset: Int,
    size: Int,
    elementSize: Int,
    arr: T,
    crossinline getter: ByteArray.(array: T, n: Int, pos: Int) -> Unit
): T = arr.also {
    for (n in 0 until size) this.getter(arr, n, offset + n * elementSize)
}

fun ByteArray.readShortArrayBE(offset: Int, size: Int): ShortArray = getTypedArray(offset, size, 2, ShortArray(size)) {
    arr, n, pos -> arr[n] = readShortBE(pos)
}

fun ByteArray.readShortArrayLE(offset: Int, size: Int): ShortArray = getTypedArray(offset, size, 2, ShortArray(size)) {
    arr, n, pos -> arr[n] = readShortLE(pos)
}

fun ByteArray.readIntArrayBE(offset: Int, size: Int): IntArray = getTypedArray(offset, size, 4, IntArray(size)) {
    arr, n, pos -> arr[n] = readIntBE(pos)
}

fun ByteArray.readIntArrayLE(offset: Int, size: Int): IntArray = getTypedArray(offset, size, 4, IntArray(size)) {
    arr, n, pos -> arr[n] = readIntLE(pos)
}

fun ByteArray.readLongArrayBE(offset: Int, size: Int): LongArray = getTypedArray(offset, size, 8, LongArray(size)) {
    arr, n, pos -> arr[n] = readLongBE(pos)
}

fun ByteArray.readLongArrayLE(offset: Int, size: Int): LongArray = getTypedArray(offset, size, 8, LongArray(size)) {
    arr, n, pos -> arr[n] = readLongLE(pos)
}

fun ByteArray.readFloatArrayBE(offset: Int, size: Int): FloatArray = getTypedArray(offset, size, 4, FloatArray(size)) {
    arr, n, pos -> arr[n] = readFloatBE(pos)
}

fun ByteArray.readFloatArrayLE(offset: Int, size: Int): FloatArray = getTypedArray(offset, size, 4, FloatArray(size)) {
    arr, n, pos -> arr[n] = readFloatLE(pos)
}

fun ByteArray.readDoubleArrayBE(offset: Int, size: Int): DoubleArray = getTypedArray(offset, size, 8, DoubleArray(size)) {
    arr, n, pos -> arr[n] = readDoubleBE(pos)
}

fun ByteArray.readDoubleArrayLE(offset: Int, size: Int): DoubleArray = getTypedArray(offset, size, 8, DoubleArray(size)) {
    arr, n, pos -> arr[n] = readDoubleLE(pos)
}