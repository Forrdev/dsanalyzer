package com.sappyoak.dsanalyzer.game.world.flags

import com.sappyoak.dsanalyzer.formats.esd.CommandCall
import com.sappyoak.dsanalyzer.formats.esd.Esd
import com.sappyoak.dsanalyzer.formats.msb.part.Part
import com.sappyoak.dsanalyzer.game.ref.WorldRef
import com.sappyoak.dsanalyzer.game.world.maps.EntryKind
import com.sappyoak.dsanalyzer.game.world.maps.MapId
import com.sappyoak.dsanalyzer.game.world.params.GameParam
import com.sappyoak.dsanalyzer.game.world.text.TextCategory

private const val TALK_TO_PLAYER = 1
private const val SET_FLAG = 11
private const val ON = 1

/** One map's talk script, by the talk id its NPC parts state */
public data class TalkScript(public val map: MapId, public val talkId: Int, public val esd: Esd)

/**
 * Flags an NPC's talk script turns on, named by the NPC and by what leads to them: a line spoken or an
 * option offered in the same state, else the menu choice that leads there, else a line spoken just
 * before, else the state itself
 */
internal fun NamingContext.talkFlags(scripts: List<TalkScript>): List<Pair<Int, FlagEvidence>> = scripts.flatMap { talk ->
    val (npc, source) = speaker(talk)
    talk.esd.groups.values.flatMap { group ->
        val graph = TalkGraph(group)
        group.states.values.flatMap { state ->
            val calls = state.calls()
            val flags = calls.filter { it.isTalk(SET_FLAG) && it.int(1) == ON }.mapNotNull { it.int(0) }
            if (flags.isEmpty()) return@flatMap emptyList()

            val said = saying(calls)
                ?: graph.choiceInto(state.id)?.let { text(TextCategory.EventText, it) }?.let { "chose \"$it\"" }
                ?: graph.before(state.id).firstNotNullOfOrNull { group.states[it]?.let { before -> saying(before.calls()) } }?.let { "after $it" }
                ?: "talk state ${group.id}/${state.id}"
            flags.map { flag -> flag namedBy FlagEvidence(FlagKind.NpcState, "$npc: $said", source, direct = true) }
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
        ?.let { "offers \"$it\"" }
}

