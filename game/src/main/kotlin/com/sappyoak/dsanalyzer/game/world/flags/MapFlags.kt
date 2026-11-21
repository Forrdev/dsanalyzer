package com.sappyoak.dsanalyzer.game.world.flags

import com.sappyoak.dsanalyzer.formats.msb.event.Event
import com.sappyoak.dsanalyzer.game.ref.WorldRef
import com.sappyoak.dsanalyzer.game.world.maps.EntryKind
import com.sappyoak.dsanalyzer.game.world.maps.label

/** ObjActs placed in a map: each names the flag the game sets once the object is used */
internal val MapOrigin: FlagOrigin = FlagOrigin { it.mapFlags().withSites() }

private fun NamingContext.mapFlags(): List<Pair<Int, FlagEvidence>> = maps.flatMap { (map, msb) ->
    msb.events.withIndex().mapNotNull { (index, event) ->
        val act = event as? Event.ObjAct ?: return@mapNotNull null
        if (act.eventFlagId <= 0) return@mapNotNull null

        val target = act.part?.let { msb[it].header.name } ?: "an object"
        val verb = objActVerb(act.paramId) ?: "Used"
        act.eventFlagId namedBy FlagEvidence(
            FlagKind.ObjectState,
            "$verb: $target (${map.label})",
            WorldRef.Entry(map, EntryKind.Event, index),
            direct = true
        )
    }
}
