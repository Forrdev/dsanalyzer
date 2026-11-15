package com.sappyoak.dsanalyzer.game.world.scripts.index

import com.sappyoak.dsanalyzer.formats.emevd.asEventCall
import com.sappyoak.dsanalyzer.formats.emevd.callerBytesNeeded
import com.sappyoak.dsanalyzer.formats.emevd.emedf.flagUses
import com.sappyoak.dsanalyzer.game.data.DecodedScript

public data class ScriptIndexing(
    public val flagUses: List<FoundFlagUse>,
    public val calls: List<FoundCall>,
    /** Events that read their caller's arguments, and how many bytes each reads */
    public val needs: Map<EventKey, Int>
)

/** Indexes a script the game data already decoded, so nothing is decoded twice */
public fun DecodedScript.indexed(): ScriptIndexing {
    val calls = mutableListOf<FoundCall>()
    val needs = mutableMapOf<EventKey, Int>()

    emevd.events.forEach { event ->
        event.callerBytesNeeded()?.let { needed -> needs[EventKey(script, event.id)] = needed }

        // Calls come off the raw instructions: the decoder models an argument per definition, and
        // 'InitializeEvent' takes a vararg tail that no definition describes
        event.instructions.forEach { instruction ->
            instruction.asEventCall()?.let { call -> calls += FoundCall(script, event.id, call) }
        }
    }

    val flagUses = events.flatMap { (eventId, instructions) ->
        instructions.flatMap { instruction ->
            instruction.flagUses(eventId).map { use -> FoundFlagUse(script, eventId, use) }
        }
    }
    return ScriptIndexing(flagUses, calls, needs)
}
