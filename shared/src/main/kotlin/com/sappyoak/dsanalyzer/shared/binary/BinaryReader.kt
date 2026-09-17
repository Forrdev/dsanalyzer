package com.sappyoak.dsanalyzer.shared.binary

import java.nio.ByteOrder

/**
 * A wrapper around some backing collection of bytes that provides methods for accessing its data.
 */
public interface BinaryReader {
    var order: ByteOrder
    var position: Long

    val size: Long
    val pointerSize: PointerSize

    val remaining: Long get() = size - position

    fun readByte(): Byte
    fun readShort(): Short
    fun readInt(): Int
    fun readLong(): Long
    fun readFloat(): Float
    fun readBoolean(): Boolean = readByte().toInt() != 0
    fun readBytes(count: Int): ByteArray

    fun readString(): String
    fun readString(length: Int): String

    /**
     * Reads a 16-string up to and including the null-terminator using the current [order]
     */
    fun readWideString(): String

    /**
     * Reads a null-terminated Shift-JIS
     */
    fun readJisString(): String

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
    fun <T> at(offset: Long, block: BinaryReader.() -> T): T
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
public fun <T> BinaryReader.assertValue(value: T, block: BinaryReader.() -> T) {
    val start = position
    val actual = block()
    if (value != actual) {
        throw BinaryFormatException("Expected value to be $value but was $actual", start)
    }
}
