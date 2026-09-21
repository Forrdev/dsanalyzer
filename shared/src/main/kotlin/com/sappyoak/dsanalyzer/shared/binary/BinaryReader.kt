package com.sappyoak.dsanalyzer.shared.binary

import java.lang.foreign.MemorySegment
import java.nio.ByteOrder
import java.nio.charset.Charset

import com.sappyoak.dsanalyzer.shared.platform.PointerSize
/**
 * A wrapper around some backing collection of bytes that provides methods for accessing its data.
 */
public interface BinaryReader {
    var order: ByteOrder
    var position: Long

    val size: Long
    var pointerSize: PointerSize

    val remaining: Long get() = size - position

    fun readByte(): Byte
    fun readShort(): Short
    fun readInt(): Int
    fun readLong(): Long
    fun readFloat(): Float
    fun readBoolean(): Boolean = readByte().toInt() != 0
    fun readBytes(count: Int): ByteArray

    fun readString(charset: Charset = Charsets.US_ASCII, length: Int): String {
        val bytes = readBytes(length)
        return String(bytes, charset)
    }

    fun readStringTerminated(charset: Charset = Charsets.US_ASCII): String {
        val start = position
        var end = start

        while (end < size && readBoolean()) {
            end++
        }

        position = start

        if (end == size) {
            throw BinaryFormatException("Unterminated string", start)
        }

        val text = String(readBytes((end - start).toInt()), charset)
        position = end + 1
        return text
    }

    fun readAscii(): String = readStringTerminated()
    fun readAscii(length: Int): String = readString(length = length)

    fun readShiftJIS(): String = readStringTerminated(SHIFT_JIS)
    fun readShiftJIS(length: Int): String = readString(SHIFT_JIS, length)

    /**
     * Reads a utf16 string up to and including the null-terminator using the current [order]
     */
    fun readUTF16(): String {
        val text = StringBuilder()
        while (true) {
            val char = readShort().toInt().toChar()
            if (char == NULL_CHAR) {
                break
            }
            text.append(char)
        }

        val encoding = if (order == ByteOrder.BIG_ENDIAN) Charsets.UTF_16BE else Charsets.UTF_16LE
        return String(text.toString().toByteArray(encoding), encoding)
    }

    fun readFixedString(length: Int): String =
        readAscii(length).substringBefore(NULL_CHAR)

    fun readFixedShiftJis(length: Int): String = readShiftJIS(length).substringBefore(NULL_CHAR)

    fun readFixedUTF16(length: Int): String {
        val text = StringBuilder()
        while (text.length <= length) {
            val char = readShort().toInt().toChar()
            if (char == NULL_CHAR) {
                break
            }
            text.append(char)
        }

        val encoding = if (order == ByteOrder.BIG_ENDIAN) Charsets.UTF_16BE else Charsets.UTF_16LE
        return String(text.toString().toByteArray(encoding), encoding)
    }

    /**
     * Attempts to read a pointer at [position], using [pointerSize] as an indication of how many
     * bytes to read
     */
    fun readPointer(): Long = when (pointerSize) {
        PointerSize.IntPointer -> readUInt().toLong()
        PointerSize.LongPointer -> readLong()
        else -> throw BinaryFormatException("Unsupported pointer size $pointerSize", position)
    }


    /**
     * Moves [count] bytes forward and returns this reader
     */
    fun skip(count: Long): BinaryReader

    /**
     * Moves to [offset] position and returns this reader
     */
    fun seek(offset: Long): BinaryReader

    /**
     * Creates a new [BinaryReader] of [length] bytes starting from [offset]
     */
    fun slice(offset: Long, length: Long): BinaryReader

    /**
     * Stores the current [position] and moves to [offset] to perform some work [block],
     * returning to the original stored [position] when the block exits
     */
    fun <T> at(offset: Long, block: BinaryReader.() -> T): T {
        val start = position
        position = offset
        try {
            return block()
        } finally {
            position = start
        }
    }

    public companion object {
        internal const val NULL_CHAR = '\u0000'
        internal const val ZERO_BYTE = 0.toByte()

        private val SHIFT_JIS = Charset.forName("Shift_JIS")

        public fun of(
            bytes: ByteArray,
            position: Int = 0,
            order: ByteOrder = ByteOrder.LITTLE_ENDIAN,
            pointerSize: PointerSize = PointerSize.IntPointer
        ): BinaryReader = ByteBufferBinaryReader(bytes, position, order, pointerSize)

        public fun of(
            segment: MemorySegment,
            position: Int = 0,
            order: ByteOrder = ByteOrder.LITTLE_ENDIAN,
            pointerSize: PointerSize = PointerSize.IntPointer
        ): BinaryReader = MemorySegmentBinaryReader(segment, position, order, pointerSize)
    }
}

public fun BinaryReader.readUByte(): UByte = readByte().toUByte()
public fun BinaryReader.readUShort(): UShort = readShort().toUShort()
public fun BinaryReader.readUInt(): UInt = readInt().toUInt()
public fun BinaryReader.readULong(): ULong = readLong().toULong()
public fun BinaryReader.readVarInt(wide: Boolean = pointerSize.intPointer): Int =
    if (wide) readLong().toInt() else readInt()

public fun BinaryReader.skip(count: Int): BinaryReader = skip(count.toLong())
public fun BinaryReader.seek(offset: Int): BinaryReader = seek(offset.toLong())
public fun BinaryReader.slice(offset: Int, length: Int): BinaryReader = slice(offset.toLong(), length.toLong())
public fun <T> BinaryReader.at(offset: Int, block: BinaryReader.() -> T): T = at(offset.toLong(), block)

/**
 * Read a value [T] from the reader and check its equality against a provided [value]
 */
public fun <T> BinaryReader.expectValue(value: T, block: BinaryReader.() -> T): Boolean =
    block() == value

/**
 * Read a value [T] from the reader and enforce its equality to [value]
 */
public fun <T> BinaryReader.assertValue(value: T, block: BinaryReader.() -> T): T {
    val start = position
    val actual = block()
    if (value != actual) {
        throw BinaryFormatException("Expected value to be $value but was $actual", start)
    }
    return actual
}

/**
 * Reads a value [T] from the reader and enforces its equality to one of [value]
 */
public fun <T> BinaryReader.assertValues(vararg value: T, block: BinaryReader.() -> T): T {
    val start = position
    val actual = block()
    for (v in value) {
        if (v == actual) return v
    }

    throw BinaryFormatException("Expected value to be on of ${value.joinToString(", ")} but was $actual", start)
}
