package com.sappyoak.dsanalyzer.game.world.scripts.index

import com.sappyoak.dsanalyzer.formats.emevd.emedf.FlagAddressing
import com.sappyoak.dsanalyzer.formats.emevd.emedf.FlagTarget
import com.sappyoak.dsanalyzer.formats.emevd.emedf.literalAt

/** A flag read off one caller's arguments, with the event that passed it */
internal data class CallerFlag(val flagId: Int, val caller: EventKey)

/**
 * The flags [found] names once each of its event's callers is followed, the way the game fills them in
 * when the event starts: a caller-passed flag read from that call's arguments, a slot-relative one counted
 * from the call's slot. Empty when the event has no caller this index can read
 */
internal fun EventCallIndex.flagsOf(found: FoundFlagUse): List<CallerFlag> {
    val callers = argumentsFor(EventKey(found.script, found.eventId)) as? CallerArguments.Sites ?: return emptyList()
    val target = found.use.target

    return callers.sites.distinctBy { it.slot to it.args }.mapNotNull { site ->
        val flag = when (target) {
            is FlagTarget.FromCaller -> site.args.literalAt(callers.offset + target.start, target.type)?.raw?.let {
                when (target.addressing) {
                    FlagAddressing.Absolute -> it
                    FlagAddressing.ByEvent -> it + found.eventId
                    FlagAddressing.BySlot -> it + found.eventId + site.slot
                }
            }
            is FlagTarget.SlotRelative -> target.offset + found.eventId + site.slot
            else -> null
        }
        flag?.let { CallerFlag(it.toInt(), EventKey(site.script, site.callerEventId)) }
    }.distinct()
}
