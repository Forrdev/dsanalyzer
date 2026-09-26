package com.sappyoak.dsanalyzer.formats.emevd

import com.sappyoak.dsanalyzer.formats.FixtureWriter

internal class InstructionSpec(
    val bank: Int,
    val id: Int,
    val args: ByteArray = ByteArray(0),
    val layerMask: Int? = null
)

internal class EventSpec(
    val id: Int,
    val restBehavior: Int = 0,
    val instructions: List<InstructionSpec> = emptyList(),
    val parameters: List<Parameter> = emptyList()
)

private const val HEADER_SIZE = 0x54
private const val EVENT_SIZE = 28
private const val INSTRUCTION_SIZE = 24
private const val LAYER_SIZE = 20
private const val PARAMETER_SIZE = 20

internal fun emevdFile(
    events: List<EventSpec>,
    linkedFileOffsets: List<Int> = emptyList(),
    strings: String = "",
    bigEndian: Boolean = false,
    version: Int = 0xCC
): ByteArray {
    val instructions = events.flatMap { it.instructions }
    val layerMasks = instructions.mapNotNull { it.layerMask }.distinct()

    val eventsAt = HEADER_SIZE
    val instructionsAt = eventsAt + events.size * EVENT_SIZE
    val layersAt = instructionsAt + instructions.size * INSTRUCTION_SIZE
    val parametersAt = layersAt + layerMasks.size * LAYER_SIZE
    val linkedAt = parametersAt + events.sumOf { it.parameters.size } * PARAMETER_SIZE
    val argumentsAt = linkedAt + linkedFileOffsets.size * 4
    val argumentBytes = instructions.sumOf { it.args.size }
    val stringsAt = argumentsAt + argumentBytes
    val stringBytes = strings.toByteArray()

    return FixtureWriter().apply {
        bytes("EVD\u0000".toByteArray())
        byte(if (bigEndian) 1 else 0).byte(0).byte(0).byte(0)
        int(version).int(stringsAt + stringBytes.size)
        int(events.size).int(eventsAt)
        int(instructions.size).int(instructionsAt)
        int(0).int(0)
        int(layerMasks.size).int(layersAt)
        int(events.sumOf { it.parameters.size }).int(parametersAt)
        int(linkedFileOffsets.size).int(linkedAt)
        int(argumentBytes).int(argumentsAt)
        int(stringBytes.size).int(stringsAt)
        int(0)

        var instructionIndex = 0
        var parameterIndex = 0
        events.forEach { event ->
            int(event.id).int(event.instructions.size).int(instructionIndex * INSTRUCTION_SIZE)
            int(event.parameters.size).int(parameterIndex * PARAMETER_SIZE)
            int(event.restBehavior).int(0)
            instructionIndex += event.instructions.size
            parameterIndex += event.parameters.size
        }

        var argumentOffset = 0
        instructions.forEach { instruction ->
            int(instruction.bank).int(instruction.id)
            int(instruction.args.size).int(argumentOffset)
            int(instruction.layerMask?.let { layerMasks.indexOf(it) * LAYER_SIZE } ?: -1).int(0)
            argumentOffset += instruction.args.size
        }

        layerMasks.forEach { mask -> int(2).int(mask).int(0).int(-1).int(1) }

        events.flatMap { it.parameters }.forEach { parameter ->
            int(parameter.instructionIndex).int(parameter.targetStartByte).int(parameter.sourceStartByte)
            int(parameter.byteCount).int(parameter.unkId)
        }

        linkedFileOffsets.forEach(::int)
        instructions.forEach { bytes(it.args) }
        bytes(stringBytes)
    }.toByteArray()
}