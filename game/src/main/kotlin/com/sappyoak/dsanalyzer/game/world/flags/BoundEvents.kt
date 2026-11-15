package com.sappyoak.dsanalyzer.game.world.flags

import com.sappyoak.dsanalyzer.formats.emevd.emedf.ArgReference
import com.sappyoak.dsanalyzer.formats.emevd.emedf.ArgValue
import com.sappyoak.dsanalyzer.formats.emevd.emedf.DecodedInstruction
import com.sappyoak.dsanalyzer.formats.emevd.emedf.Opcode
import com.sappyoak.dsanalyzer.formats.emevd.emedf.literalAt
import com.sappyoak.dsanalyzer.formats.emevd.emedf.opcode
import com.sappyoak.dsanalyzer.game.data.DecodedScript
import com.sappyoak.dsanalyzer.game.world.scripts.ScriptId
import com.sappyoak.dsanalyzer.game.world.scripts.index.CallSite
import com.sappyoak.dsanalyzer.game.world.scripts.index.CallerArguments
import com.sappyoak.dsanalyzer.game.world.scripts.index.EventCallIndex
import com.sappyoak.dsanalyzer.game.world.scripts.index.EventKey
import com.sappyoak.dsanalyzer.game.world.scripts.index.argumentsFor

private const val BY_EVENT = 1L
private const val BY_EVENT_AND_SLOT = 2L

/** One way an event runs: its instructions with every argument a number, where it can be read */
internal class BoundEvent(
    val script: ScriptId,
    val eventId: Long,
    val instructions: List<BoundInstruction>
)

/** An instruction's arguments in definition order; null where the caller's value cannot be read */
internal class BoundInstruction(val opcode: Opcode, val values: List<Long?>) {
    fun int(index: Int): Int? = values.getOrNull(index)?.toInt()
}

/**
 * Every way each event in [script] runs. An event reading its caller's arguments runs once per
 * distinct call, so a common event started three hundred times names three hundred sets of flags;
 * one with no readable caller runs once with those arguments unknown
 */
internal fun DecodedScript.bound(calls: EventCallIndex): List<BoundEvent> = events.flatMap { (eventId, instructions) ->
    val takesArguments = instructions.any { decoded -> decoded.args.any { it.value is ArgValue.FromCaller } }
    val callers = if (takesArguments) calls.argumentsFor(EventKey(script, eventId)) else CallerArguments.NoCaller
    val sites = (callers as? CallerArguments.Sites)?.sites?.distinctBy { it.slot to it.args }

    if (sites == null) listOf(BoundEvent(script, eventId, instructions.mapNotNull { it.bind(eventId, null, 0) }))
    else sites.map { site -> BoundEvent(script, eventId, instructions.mapNotNull { it.bind(eventId, site, callers.offset) }) }
}

private fun DecodedInstruction.bind(eventId: Long, site: CallSite?, offset: Int): BoundInstruction? {
    if (definition == null) return null
    val values = args.map { arg ->
        when (val value = arg.value) {
            is ArgValue.Literal -> value.raw
            is ArgValue.FromCaller -> site?.args?.literalAt(offset + value.sourceStartByte, arg.definition.type)?.raw
            ArgValue.Omitted -> arg.definition.default.toLong()
        }
    }
    val addressing = args.indexOfFirst { it.definition.reference == ArgReference.EventFlagType }
        .takeIf { it >= 0 }
        ?.let { values[it] }
    val flags = values.mapIndexed { index, value ->
        if (value == null || args[index].definition.reference != ArgReference.EventFlag) value
        else when (addressing) {
            BY_EVENT -> value + eventId
            BY_EVENT_AND_SLOT -> site?.let { value + eventId + it.slot }
            else -> value
        }
    }
    return BoundInstruction(instruction.opcode, flags)
}
