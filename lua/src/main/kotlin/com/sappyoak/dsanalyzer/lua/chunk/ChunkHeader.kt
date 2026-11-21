package com.sappyoak.dsanalyzer.lua.chunk

import java.nio.ByteOrder

import com.sappyoak.dsanalyzer.shared.binary.BinaryFormatException
import com.sappyoak.dsanalyzer.shared.binary.BinaryReader

private val SIGNATURE = byteArrayOf(0x1B, 'L'.code.toByte(), 'u'.code.toByte(), 'a'.code.toByte())
private const val VERSION_50 = 0x50

private const val INT_SIZE = 4
private const val INSTRUCTION_SIZE = 4
private const val NUMBER_SIZE = 8
private val SIZE_T_SIZES = setOf(4, 8)

/** What 5.0 writes after the sizes, so a reader can tell its number format is the same */
private const val TEST_NUMBER = 3.14159265358979323846E7

/**
 * The machine a 5.0 chunk was dumped on. PTDE wrote 4-byte size_t, DSR 8-byte;
 * everything else the reader needs to be as DS1's luac had it.
 */
public data class ChunkHeader(
    public val order: ByteOrder,
    public val sizeTSize: Int
)

internal fun BinaryReader.readChunkHeader(): ChunkHeader {
    if (!readBytes(SIGNATURE.size).contentEquals(SIGNATURE)) fail("Not a Lua chunk")
    val version = readByte().toInt()
    if (version != VERSION_50) fail("Lua version 0x%02X, only 5.0 is read".format(version))

    val order = if (readByte().toInt() != 0) ByteOrder.LITTLE_ENDIAN else ByteOrder.BIG_ENDIAN
    expectSize("int", INT_SIZE)
    val sizeT = readByte().toInt()
    if (sizeT !in SIZE_T_SIZES) fail("size_t of $sizeT bytes")
    expectSize("Instruction", INSTRUCTION_SIZE)
    expectSize("opcode field", SIZE_OP)
    expectSize("A field", SIZE_A)
    expectSize("B field", SIZE_B)
    expectSize("C field", SIZE_C)
    expectSize("lua_Number", NUMBER_SIZE)

    this.order = order
    val test = Double.fromBits(readLong())
    if (test != TEST_NUMBER) fail("Number format check read $test")
    return ChunkHeader(order, sizeT)
}

private fun BinaryReader.expectSize(what: String, expected: Int) {
    val actual = readByte().toInt()
    if (actual != expected) fail("$what is $actual, expected $expected")
}

private fun BinaryReader.fail(message: String): Nothing = throw BinaryFormatException(message, position)
