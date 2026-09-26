package com.sappyoak.dsanalyzer.formats.emevd

import java.nio.ByteOrder

import com.sappyoak.dsanalyzer.shared.binary.BinaryFormatException
import com.sappyoak.dsanalyzer.shared.binary.BinaryReader
import com.sappyoak.dsanalyzer.shared.binary.assertValue
import com.sappyoak.dsanalyzer.shared.binary.at
import com.sappyoak.dsanalyzer.shared.binary.assertZeros
import com.sappyoak.dsanalyzer.shared.binary.readCoded
import com.sappyoak.dsanalyzer.shared.binary.readUInt

private const val MAGIC = "EVD\u0000"
private const val DS1_VERSION = 0xCC
private const val NO_LAYER = -1

public fun readEmevd(reader: BinaryReader): Emevd {
    reader.order = ByteOrder.LITTLE_ENDIAN
    reader.position = 0
    reader.assertValue(MAGIC) { readAscii(MAGIC.length) }
    reader.readFormat()
    reader.assertValue(DS1_VERSION) { readInt() }
    reader.skip(4) // file size

    val eventCount = reader.readInt()
    val eventsOffset = reader.readInt()
    reader.skip(4) // instruction count
    val instructionsOffset = reader.readInt()
    reader.assertZeros(1) // a table no DS1 file uses
    reader.skip(4)
    reader.skip(4) // layer count
    val layerOffset = reader.readInt()
    reader.skip(4) // param count
    val parametersOffset = reader.readInt()
    val linkedFileCount = reader.readInt()
    val linkedFilesOffset = reader.readInt()
    reader.skip(4) // argument block length
    val argumentsOffset = reader.readInt()
    val stringsLength = reader.readInt()
    val stringsOffset = reader.readInt()
    reader.assertZeros(1)

    val tables = Tables(
        events = eventsOffset,
        instructions = instructionsOffset,
        layers = layerOffset,
        parameters = parametersOffset,
        linkedFiles = linkedFilesOffset,
        arguments = argumentsOffset,
        strings = stringsOffset
    )

    reader.position = tables.events.toLong()
    val events = List(eventCount) { reader.readScriptEvent(tables) }

    return Emevd(
        events = events,
        linkedFileOffsets = reader.at(tables.linkedFiles) { List(linkedFileCount) { readInt() } },
        strings = StringTable(reader.at(tables.strings) { readBytes(stringsLength) })
    )
}

/** Absolute offsets for where each table starts */
private class Tables(
    val events: Int,
    val instructions: Int,
    val layers: Int,
    val parameters: Int,
    val linkedFiles: Int,
    val arguments: Int,
    val strings: Int
)

private fun BinaryReader.readFormat() {
    val at = position
    val bigEndian = readBoolean()
    val is64Bit = readByte().toInt() == -1
    val unicode = readBoolean()
    skip(1)

    if (bigEndian || is64Bit || unicode) {
        throw BinaryFormatException("Not a DS1 PC event script: bigEndian=$bigEndian, 64bit=$is64Bit, unicode=$unicode", at)
    }
}

private fun BinaryReader.readScriptEvent(tables: Tables): ScriptEvent {
    val id = readInt().toLong()
    val instructionCount = readInt()
    val instructionsOffset = readInt()
    val parameterCount = readInt()
    val parametersOffset = readInt()
    val restBehavior = readCoded<RestBehavior>()
    assertZeros(1)

    return ScriptEvent(
        id = id,
        restBehavior = restBehavior,
        instructions = at(tables.instructions + instructionsOffset) {
            List(instructionCount) { readInstruction(tables) }
        },
        parameters = at(tables.parameters + parametersOffset) {
            List(parameterCount) { readParameter() }
        }
    )
}

private fun BinaryReader.readInstruction(tables: Tables): Instruction {
    val bank = readInt()
    val id = readInt()
    val argsLength = readInt()
    val argsOffset = readInt()
    val layerOffset = readInt()
    assertZeros(1)

    return Instruction(
        bank = bank,
        id = id,
        args = if (argsLength == 0) {
            ArgData.Empty
        } else {
            ArgData(at(tables.arguments + argsOffset) { readBytes(argsLength) })
        },
        layerMask = if (layerOffset == NO_LAYER) null else at(tables.layers + layerOffset) { readLayer() }
    )
}

private fun BinaryReader.readLayer(): UInt {
    assertValue(2) { readInt() }
    val mask = readUInt()
    assertValue(0) { readInt() }
    assertValue(-1) { readInt() }
    assertValue(1) { readInt() }
    return mask
}

private fun BinaryReader.readParameter(): Parameter = Parameter(
    instructionIndex = readInt(),
    targetStartByte = readInt(),
    sourceStartByte = readInt(),
    byteCount = readInt(),
    unkId = readInt()
)