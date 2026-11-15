package com.sappyoak.dsanalyzer.app.data.scripts

import com.sappyoak.dsanalyzer.formats.emevd.emedf.ArgDefinition
import com.sappyoak.dsanalyzer.formats.emevd.emedf.ArgValue
import com.sappyoak.dsanalyzer.formats.emevd.emedf.Emedf
import com.sappyoak.dsanalyzer.formats.emevd.emedf.literalAt
import com.sappyoak.dsanalyzer.game.world.scripts.index.CallerArguments

internal fun CallerArguments.fill(
    from: ArgValue.FromCaller,
    definition: ArgDefinition,
    emedf: Emedf
): ArgumentValue = when (this) {
    CallerArguments.Unknown -> ArgumentValue.FromCaller(from.sourceStartByte, Unresolved.Unknown)
    CallerArguments.NoCaller -> ArgumentValue.FromCaller(from.sourceStartByte, Unresolved.NoCaller)
    is CallerArguments.Sites -> read(from, definition, emedf)
}

private fun CallerArguments.Sites.read(
    from: ArgValue.FromCaller,
    definition: ArgDefinition,
    emedf: Emedf
): ArgumentValue {
    val at = offset + from.sourceStartByte
    val distinct = sites.map { it.args.literalAt(at, definition.type) }.distinct()

    if (distinct.size > 1) {
        return ArgumentValue.FromCaller(from.sourceStartByte, Unresolved.Differs(sites.size))
    }

    val only = distinct.firstOrNull()
        ?: return ArgumentValue.FromCaller(from.sourceStartByte, Unresolved.NotPassed(sites.size))

    return ArgumentValue.Passed(
        shown = only.asFloat?.toString() ?: only.raw.toString(),
        meaning = emedf.enumValue(definition.enumName, only.raw),
        origin = origin(),
        raw = only.raw
    )
}

private fun CallerArguments.Sites.origin(): PassedOrigin = sites.singleOrNull()
    ?.let { PassedOrigin.One(it.script, it.callerEventId) }
    ?: PassedOrigin.Agreeing(sites.size)
