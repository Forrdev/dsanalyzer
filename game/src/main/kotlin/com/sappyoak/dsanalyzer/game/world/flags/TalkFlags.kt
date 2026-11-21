package com.sappyoak.dsanalyzer.game.world.flags

import com.sappyoak.dsanalyzer.formats.esd.CommandCall
import com.sappyoak.dsanalyzer.formats.esd.Esd
import com.sappyoak.dsanalyzer.formats.msb.part.Part
import com.sappyoak.dsanalyzer.game.world.maps.EntryKind
import com.sappyoak.dsanalyzer.game.world.maps.MapId
import com.sappyoak.dsanalyzer.game.world.text.TextCategory
import com.sappyoak.dsanalyzer.game.ref.DataAddress
import com.sappyoak.dsanalyzer.game.ref.map
import com.sappyoak.dsanalyzer.game.ref.mapEntry

private const val TALK_TO_PLAYER = 1

/** One map's talk script, by the talk id its NPC parts state */
public data class TalkScript(public val map: MapId, public val talkId: Int, public val esd: Esd)

/** Who speaks a talk script, and the placed part to cite; the map itself where no part is placed */
internal data class Speaker(val name: String, val address: DataAddress)

/** Talk scripts both name the flags they turn on and are places that set and read flags */
internal class TalkOrigin(private val scripts: List<TalkScript>) : FlagOrigin {
    override fun read(context: NamingContext): OriginReading {
        val spoken = scripts.map { it to context.speaker(it) }
        return OriginReading(names = context.talkFlags(spoken), sites = talkSites(spoken))
    }
}

/**
 * Flags an NPC's talk script turns on, named by the NPC and by what leads to them: a line spoken or an
 * option offered in the same state, else the menu choice that leads there, else a line spoken just
 * before, else the state itself
 */
private fun NamingContext.talkFlags(scripts: List<Pair<TalkScript, Speaker>>): List<Pair<Int, FlagEvidence>> =
    scripts.flatMap { (talk, speaker) -> talkFlags(talk, speaker) }

private fun NamingContext.talkFlags(talk: TalkScript, speaker: Speaker): List<Pair<Int, FlagEvidence>> =
    talk.esd.groups.values.flatMap { group ->
        val graph = TalkGraph(group)
        group.states.values.flatMap { state ->
            val calls = state.calls()
            val flags = calls.filter { it.isTalk(SET_TALK_FLAG) && it.int(1) == TALK_FLAG_ON }.mapNotNull { it.int(0) }
            if (flags.isEmpty()) return@flatMap emptyList()

            val said = saying(calls)
                ?: graph.choiceInto(state.id)?.let { text(TextCategory.EventText, it) }?.let { "chose \"$it\"" }
                ?: graph.before(state.id).firstNotNullOfOrNull { group.states[it]?.let { before -> saying(before.calls()) } }?.let { "after $it" }
                ?: "talk state ${group.id}/${state.id}"
            flags.map { flag ->
                flag namedBy FlagEvidence(FlagKind.NpcState, "${speaker.name}: $said", speaker.address, direct = true)
            }
        }
    }

/** The NPC placed with this talk id */
private fun NamingContext.speaker(talk: TalkScript): Speaker {
    val unplaced = Speaker("talk ${talk.talkId}", DataAddress.map(talk.map))
    val msb = maps[talk.map] ?: return unplaced
    val index = msb.parts.indexOfFirst { it is Part.Enemy && it.data.talkId == talk.talkId }
    if (index < 0) return unplaced

    val part = msb.parts[index] as Part.Enemy
    val name = npcName(part.data.npcParamId)
        ?: part.header.entityId?.let(entities::label)
        ?: part.header.name
    return Speaker(name, DataAddress.mapEntry(talk.map, EntryKind.Part, index))
}

/** The first line spoken in the state, else the first menu option it offers */
private fun NamingContext.saying(calls: List<CommandCall>): String? {
    val line = calls.firstOrNull { it.isTalk(TALK_TO_PLAYER) }?.int(0)?.let(::spokenLine)
    if (line != null) return "\"$line\""

    return calls.firstOrNull { it.isTalk(ADD_TALK_LIST) }?.int(1)
        ?.let { text(TextCategory.EventText, it) }
        ?.let { "offers \"$it\"" }
}

