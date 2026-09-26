package com.sappyoak.dsanalyzer.app.scripts

import com.sappyoak.dsanalyzer.formats.emevd.emedf.ArgReference
import com.sappyoak.dsanalyzer.formats.emevd.emedf.ArgType
import com.sappyoak.dsanalyzer.formats.emevd.emedf.ArgValue
import com.sappyoak.dsanalyzer.formats.emevd.emedf.DecodedArg
import com.sappyoak.dsanalyzer.formats.emevd.emedf.DecodedInstruction
import com.sappyoak.dsanalyzer.formats.emevd.emedf.Emedf
import com.sappyoak.dsanalyzer.game.world.WorldRef

public data class ArgumentLine(
    public val label: String,
    public val value: String,
    public val link: WorldRef?
)

public fun DecodedInstruction.title(): String = when (val named = definition) {
    null -> "$instruction (no definition)"
    else -> "$instruction ${named.name}" + if (sizeMismatch) " [extra bytes]" else ""
}

public fun DecodedInstruction.argumentLines(emedf: Emedf): List<ArgumentLine> = when (definition) {
    null -> listOf(ArgumentLine("bytes", instruction.args.toString(), null))
    else -> args.map { it.line(emedf) }
}

private fun DecodedArg.line(emedf: Emedf): ArgumentLine = ArgumentLine(
    label = definition.name,
    value = describe(emedf),
    link = (value as? ArgValue.Literal)
        ?.takeIf { definition.reference == ArgReference.Entity }
        ?.let { WorldRef.Entity(it.raw.toInt()) }
)

private fun DecodedArg.describe(emedf: Emedf): String = when (val argValue = value) {
    is ArgValue.FromCaller -> "passed in by the caller, byte ${argValue.sourceStartByte}"
    ArgValue.Omitted -> "${definition.default.asArgument(definition.type)} (default)"
    is ArgValue.Literal -> {
        val shown = argValue.asFloat?.toString() ?: argValue.raw.toString()
        emedf.enumValue(definition.enumName, argValue.raw)?.let { "$shown ($it)" } ?: shown
    }
}

/** Definition defaults are stored as numbers, so whole ones read better without the decimal */
private fun Double.asArgument(type: ArgType): String = if (type == ArgType.Float) toString() else toLong().toString()