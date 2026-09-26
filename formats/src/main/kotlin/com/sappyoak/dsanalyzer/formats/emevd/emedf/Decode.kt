package com.sappyoak.dsanalyzer.formats.emevd.emedf

import java.nio.ByteOrder

import com.sappyoak.dsanalyzer.formats.emevd.Instruction
import com.sappyoak.dsanalyzer.formats.emevd.Parameter
import com.sappyoak.dsanalyzer.formats.emevd.ScriptEvent
import com.sappyoak.dsanalyzer.shared.binary.BinaryReader
import com.sappyoak.dsanalyzer.shared.binary.at
import com.sappyoak.dsanalyzer.shared.binary.readUByte
import com.sappyoak.dsanalyzer.shared.binary.readUInt
import com.sappyoak.dsanalyzer.shared.binary.readUShort

public sealed interface ArgValue {
    /** A value written into the instruction itself */
    public data class Literal(public val raw: Long, public val asFloat: Float? = null) : ArgValue{
        override fun toString(): String = asFloat?.toString() ?: raw.toString()
    }

    /** A value the caller passes in, copied from its arguments when the event is initialized */
    public data class FromCaller(public val sourceStartByte: Int, public val byteCount: Int) : ArgValue {
        override fun toString(): String = "caller@$sourceStartByte"
    }

    /** Left off the end of the instruction, so the game uses the definition's default */
    public data object Omitted : ArgValue {
        override fun toString(): String = "default"
    }
}

public data class DecodedArg(public val definition: ArgDefinition, public val value: ArgValue)


/**
 * An instruction with its arguments split up, or with [definition] null when the definitions do
 * not cover it, in which case the bytes stay in the instruction itself
 */
public data class DecodedInstruction(
    public val instruction: Instruction,
    public val definition: InstructionDefinition?,
    public val args: List<DecodedArg>
) {
    public val sizeMismatch: Boolean
        get() = definition != null && instruction.args.size > definition.packedSize
}

/** Decoded every instruction in the event, applying the arguments its callers pass in */
public fun ScriptEvent.decode(emedf: Emedf): List<DecodedInstruction> =
    instructions.mapIndexed { index, instruction ->
        instruction.decode(emedf[instruction], parameters.filter { it.instructionIndex == index })
    }

/**
 * Splits an instruction's bytes into arguments
 */
public fun Instruction.decode(
    definition: InstructionDefinition?,
    parameters: List<Parameter> = emptyList()
): DecodedInstruction {
    if (definition == null) return DecodedInstruction(this, null, emptyList())

    val reader = args.reader().also { it.order = ByteOrder.LITTLE_ENDIAN }
    var at = 0

    val decoded = definition.args.map { arg ->
        at = arg.type.align(at)
        val start = at
        at += arg.type.size

        val substitution = parameters.firstOrNull { start >= it.targetStartByte && start < it.targetStartByte + it.byteCount }
        val value = when {
            substitution != null -> ArgValue.FromCaller(
                sourceStartByte = substitution.sourceStartByte + (start - substitution.targetStartByte),
                byteCount = arg.type.size
            )
            start + arg.type.size <= args.size -> reader.at(start) { readLiteral(arg.type) }
            else -> ArgValue.Omitted
        }
        DecodedArg(arg, value)
    }

    return DecodedInstruction(this, definition, decoded)
}

private fun BinaryReader.readLiteral(type: ArgType): ArgValue.Literal = when (type) {
    ArgType.UByte -> ArgValue.Literal(readUByte().toLong())
    ArgType.Byte -> ArgValue.Literal(readByte().toLong())
    ArgType.UShort -> ArgValue.Literal(readUShort().toLong())
    ArgType.Short -> ArgValue.Literal(readShort().toLong())
    ArgType.UInt -> ArgValue.Literal(readUInt().toLong())
    ArgType.Int -> ArgValue.Literal(readInt().toLong())
    ArgType.Float -> readFloat().let { ArgValue.Literal(it.toRawBits().toLong(), it) }
}