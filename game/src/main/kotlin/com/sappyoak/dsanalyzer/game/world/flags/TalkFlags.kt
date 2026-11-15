package com.sappyoak.dsanalyzer.game.world.flags

import com.sappyoak.dsanalyzer.formats.esd.CommandCall
import com.sappyoak.dsanalyzer.formats.esd.Esd
import com.sappyoak.dsanalyzer.formats.esd.EsdCondition
import com.sappyoak.dsanalyzer.formats.esd.EsdState
import com.sappyoak.dsanalyzer.formats.esd.expr.Decoded
import com.sappyoak.dsanalyzer.formats.esd.expr.EsdExpression
import com.sappyoak.dsanalyzer.formats.esd.expr.decode
import com.sappyoak.dsanalyzer.formats.msb.part.Part
import com.sappyoak.dsanalyzer.game.ref.WorldRef
import com.sappyoak.dsanalyzer.game.world.maps.EntryKind
import com.sappyoak.dsanalyzer.game.world.maps.MapId
import com.sappyoak.dsanalyzer.game.world.params.GameParam
import com.sappyoak.dsanalyzer.game.world.text.TextCategory

private const val TALK_BANK = 1
private const val TALK_TO_PLAYER = 1
private const val SET_FLAG = 11
private const val ADD_TALK_LIST = 19
private const val ON = 1

/** One map's talk script, by the talk id its NPC parts state */
public data class TalkScript(public val map: MapId, public val talkId: Int, public val esd: Esd)

/**
 * Flags an NPC's talk script turns on, named by the NPC and by what it says or offers in the same
 * state: a line it speaks, else a menu option, else the state itself
 */
internal fun NamingContext.talkFlags(scripts: List<TalkScript>): List<Pair<Int, FlagEvidence>> = scripts.flatMap { talk ->
    val (npc, source) = speaker(talk)
    talk.esd.groups.values.flatMap { group ->
        group.states.values.flatMap { state ->
            val calls = state.calls()
            val said = saying(calls) ?: "talk state ${group.id}/${state.id}"
            calls.filter { it.isTalk(SET_FLAG) && it.int(1) == ON }.mapNotNull { it.int(0) }.map { flag ->
                flag namedBy FlagEvidence(FlagKind.NpcState, "$npc: $said", source, direct = true)
            }
        }
    }
}

/** The NPC placed with this talk id, and the part to cite; the map itself where none is */
private fun NamingContext.speaker(talk: TalkScript): Pair<String, WorldRef> {
    val msb = maps[talk.map] ?: return "talk ${talk.talkId}" to WorldRef.Map(talk.map)
    val index = msb.parts.indexOfFirst { it is Part.Enemy && it.data.talkId == talk.talkId }
    if (index < 0) return "talk ${talk.talkId}" to WorldRef.Map(talk.map)

    val part = msb.parts[index] as Part.Enemy
    val name = npcName(part.data.npcParamId)
        ?: part.header.entityId?.let(entities::label)
        ?: part.header.name
    return name to WorldRef.Entry(talk.map, EntryKind.Part, index)
}

/** The first line spoken in the state, else the first menu option it offers */
private fun NamingContext.saying(calls: List<CommandCall>): String? {
    val line = calls.firstOrNull { it.isTalk(TALK_TO_PLAYER) }?.int(0)
        ?.let { params[GameParam.TalkParam][it] }
        ?.let { text(TextCategory.Conversations, it.msgId) }
    if (line != null) return "\"$line\""

    return calls.firstOrNull { it.isTalk(ADD_TALK_LIST) }?.int(1)
        ?.let { text(TextCategory.EventText, it) }
        ?.let { "chose \"$it\"" }
}

private fun EsdState.calls(): List<CommandCall> =
    entryCommands + whileCommands + exitCommands + conditions.flatMap { it.calls() }

private fun EsdCondition.calls(): List<CommandCall> = passCommands + subconditions.flatMap { it.calls() }

private fun CommandCall.isTalk(command: Int): Boolean = bank == TALK_BANK && id == command

private fun CommandCall.int(index: Int): Int? =
    ((args.getOrNull(index)?.decode() as? Decoded.Expression)?.expression as? EsdExpression.IntValue)?.value
