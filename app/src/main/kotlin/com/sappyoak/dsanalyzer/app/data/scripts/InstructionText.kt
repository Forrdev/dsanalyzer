package com.sappyoak.dsanalyzer.app.data.scripts

import com.sappyoak.dsanalyzer.formats.emevd.emedf.ArgReference
import com.sappyoak.dsanalyzer.formats.emevd.emedf.ArgType
import com.sappyoak.dsanalyzer.formats.emevd.emedf.ArgValue
import com.sappyoak.dsanalyzer.formats.emevd.emedf.DecodedArg
import com.sappyoak.dsanalyzer.formats.emevd.emedf.DecodedInstruction
import com.sappyoak.dsanalyzer.formats.emevd.emedf.Emedf
import com.sappyoak.dsanalyzer.game.ref.Address
import com.sappyoak.dsanalyzer.game.ref.DataAddress
import com.sappyoak.dsanalyzer.game.ref.DataSpace

public data class ArgumentLine(
    public val label: String,
    public val value: ArgumentValue,
    public val link: Address?
)

public sealed interface ArgumentValue {
    public data class Literal(
        public val shown: String,
        public val meaning: String?
    ) : ArgumentValue

    public data class Default(public val shown: String) : ArgumentValue

    public data class FromCaller(public val byte: Int) : ArgumentValue
    public data class Raw(public val bytes: String) : ArgumentValue
}

public val DecodedInstruction.opcode: String get() = instruction.toString()
public val DecodedInstruction.displayName: String get() = definition?.name ?: "no definition"

public fun DecodedInstruction.summary(): String? = definition?.summary
public fun DecodedInstruction.argumentLines(emedf: Emedf): List<ArgumentLine> = when (definition) {
    null -> listOf(
        ArgumentLine("bytes", ArgumentValue.Raw(instruction.args.toString()), null)
    )
    else -> args.map { it.line(emedf) }
}

private fun DecodedArg.line(emedf: Emedf): ArgumentLine = ArgumentLine(
    label = definition.name,
    value = resolveValue(emedf),
    link = (value as? ArgValue.Literal)?.let { definition.reference?.linkTo(it.raw) }
)

private fun DecodedArg.resolveValue(emedf: Emedf): ArgumentValue = when (val argValue = value) {
    is ArgValue.FromCaller -> ArgumentValue.FromCaller(argValue.sourceStartByte)

    ArgValue.Omitted -> ArgumentValue.Default(definition.default.asArgument(definition.type))

    is ArgValue.Literal -> ArgumentValue.Literal(
        shown = argValue.asFloat?.toString() ?: argValue.raw.toString(),
        meaning = emedf.enumValue(definition.enumName, argValue.raw)
    )
}

private fun ArgReference.linkTo(raw: Long): Address? {
    val id = raw.toInt()
    if (id <= 0) return null

    return when {
        placed -> DataAddress(DataSpace.Entity.token, listOf(id.toString()))
        this == ArgReference.EventFlag -> DataAddress(DataSpace.Flag.token, listOf(id.toString()))
        else -> null
    }
}

private fun Double.asArgument(type: ArgType): String =
    if (type == ArgType.Float) toString() else toLong().toString()
