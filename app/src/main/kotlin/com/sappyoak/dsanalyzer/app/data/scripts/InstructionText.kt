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
import com.sappyoak.dsanalyzer.game.world.scripts.ScriptId

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

    public data class Passed(
        public val shown: String,
        public val meaning: String?,
        public val origin: PassedOrigin,
        public val raw: Long
    ) : ArgumentValue

    public data class FromCaller(public val byte: Int, public val why: Unresolved) : ArgumentValue
    public data class Raw(public val bytes: String) : ArgumentValue
}

/** Which call site a filled-in argument came from */
public sealed interface PassedOrigin {
    public data class One(
        public val script: ScriptId,
        public val eventId: Long
    ) : PassedOrigin

    /** Several call sites, all passing the same thing */
    public data class Agreeing(public val sites: Int) : PassedOrigin
}

/** Why a caller's arguments could not be filled in */
public sealed interface Unresolved {
    public data object Unknown : Unresolved
    public data object NoCaller : Unresolved

    public data class Differs(public val sites: Int) : Unresolved
    public data class NotPassed(public val sites: Int) : Unresolved
}

public val DecodedInstruction.opcode: String get() = instruction.toString()
public val DecodedInstruction.displayName: String get() = definition?.name ?: "no definition"

public fun DecodedInstruction.summary(): String? = definition?.summary
public fun DecodedInstruction.argumentLines(
    emedf: Emedf,
    callers: CallerArguments = CallerArguments.Unknown
): List<ArgumentLine> = when (definition) {
    null -> listOf(
        ArgumentLine("bytes", ArgumentValue.Raw(instruction.args.toString()), null)
    )
    else -> args.map { it.line(emedf, callers) }
}

private fun DecodedArg.line(
    emedf: Emedf,
    callers: CallerArguments = CallerArguments.Unknown
): ArgumentLine {
    val resolved = resolveValue(emedf, callers)
    val raw = (value as? ArgValue.Literal)?.raw ?: (resolved as? ArgumentValue.Passed)?.raw

    return ArgumentLine(
        label = definition.name,
        value = resolved,
        link = raw?.let { definition.reference?.linkTo(it) }
    )
}

private fun DecodedArg.resolveValue(
    emedf: Emedf,
    callers: CallerArguments
): ArgumentValue = when (val argValue = value) {
    is ArgValue.FromCaller -> callers.fill(argValue, definition, emedf)

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
