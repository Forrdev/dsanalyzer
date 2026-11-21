package com.sappyoak.dsanalyzer.game.world.flags

import com.sappyoak.dsanalyzer.formats.emevd.emedf.FlagAccess
import com.sappyoak.dsanalyzer.formats.esd.expr.EsdExpression
import com.sappyoak.dsanalyzer.game.world.esd.EsdSource

/** 'CombineMenuFlagAndEventFlag(menuFlagId, eventFlagId)': a menu option follows the event flag */
private const val MENU_FOLLOWS_FLAG = 49

/** 'GetEventFlag(eventFlagId)', in a condition or a command's argument */
private const val GET_EVENT_FLAG = 15

/** Every flag an NPC's talk script sets, reads, or ties a menu option to, by the state that does it */
internal fun talkSites(scripts: List<Pair<TalkScript, Speaker>>): List<Pair<Int, FlagSite>> =
    scripts.flatMap { (talk, speaker) -> talkSites(talk, speaker.name) }

private fun talkSites(talk: TalkScript, npc: String): List<Pair<Int, FlagSite>> =
    talk.esd.groups.values.flatMap { group ->
        group.states.values.flatMap { state ->
            val source = FlagSource.Talk(EsdSource.Talk(talk.map, talk.talkId), group.id, state.id)
            val calls = state.calls()
            val sets = calls.filter { it.isTalk(SET_TALK_FLAG) }.mapNotNull { call ->
                val change = when (call.int(1)) {
                    TALK_FLAG_ON -> "turns it on"
                    null -> "changes it"
                    else -> "turns it off"
                }
                call.int(0)?.let { it to FlagSite(FlagAccess.Writes, "$npc: $change", source) }
            }
            val menus = calls.filter { it.isTalk(MENU_FOLLOWS_FLAG) }.mapNotNull { call ->
                call.int(1)?.let { it to FlagSite(FlagAccess.Reads, "$npc: shows a menu option by it", source) }
            }
            val reads = state.expressions().flatMap { it.flagsRead() }.distinct()
                .map { it to FlagSite(FlagAccess.Reads, "$npc: checks it", source) }
            sets + menus + reads
        }
    }

/** The flags `GetEventFlag` reads anywhere in this expression */
private fun EsdExpression.flagsRead(): List<Int> {
    val own = (this as? EsdExpression.Call)?.takeIf { it.function == GET_EVENT_FLAG }
        ?.let { (it.args.firstOrNull() as? EsdExpression.IntValue)?.value }
    return listOfNotNull(own) + children().flatMap { it.flagsRead() }
}
