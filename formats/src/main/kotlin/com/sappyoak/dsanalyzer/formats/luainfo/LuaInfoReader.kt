package com.sappyoak.dsanalyzer.formats.luainfo

import java.nio.ByteOrder

import com.sappyoak.dsanalyzer.shared.binary.BinaryFormatException
import com.sappyoak.dsanalyzer.shared.binary.BinaryReader
import com.sappyoak.dsanalyzer.shared.binary.assertValue
import com.sappyoak.dsanalyzer.shared.binary.assertValues
import com.sappyoak.dsanalyzer.shared.binary.assertZeroBytes
import com.sappyoak.dsanalyzer.shared.binary.assertZeros
import com.sappyoak.dsanalyzer.shared.binary.at

private const val MAGIC = "LUAI"

/** The only version Dark Souls writes */
private const val VERSION = 1

/** Flags are single bytes holding 0 or 1 */
private const val FALSE = 0
private const val TRUE = 1

/**
 * Reads a `.luainfo`: the header, then one 16-byte record per goal (id, name offset, interrupt name
 * offset or 0, and the noUpdate and logic flag bytes), then the Shift-JIS names the offsets point at
 */
public fun readLuaInfo(reader: BinaryReader): LuaInfo {
    reader.order = ByteOrder.LITTLE_ENDIAN
    reader.assertValue(MAGIC) { readFixedString(MAGIC.length) }
    reader.assertValue(VERSION) { readInt() }
    val count = reader.readInt()
    if (count < 0) throw BinaryFormatException("LUAINFO declares $count goals", reader.position)
    reader.assertZeros(1)
    return LuaInfo(List(count) { reader.readGoal() })
}

private fun BinaryReader.readGoal(): LuaInfoGoal {
    val id = readInt()
    val nameOffset = readInt()
    val interruptOffset = readInt()
    val noUpdate = readFlag()
    val logicInterrupt = readFlag()
    assertZeroBytes(2)
    return LuaInfoGoal(
        id = id,
        name = at(nameOffset) { readShiftJIS() },
        noUpdate = noUpdate,
        logicInterrupt = logicInterrupt,
        interruptName = interruptOffset.takeIf { it != 0 }?.let { at(it) { readShiftJIS() } }
    )
}

private fun BinaryReader.readFlag(): Boolean = assertValues(FALSE, TRUE) { readByte().toInt() } == TRUE
