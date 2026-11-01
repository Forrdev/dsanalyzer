package com.sappyoak.dsanalyzer.app.data.scripts

import com.sappyoak.dsanalyzer.formats.emevd.Emevd
import com.sappyoak.dsanalyzer.formats.emevd.asEventCall
import com.sappyoak.dsanalyzer.formats.emevd.callerBytesNeeded
import com.sappyoak.dsanalyzer.formats.emevd.emedf.Emedf
import com.sappyoak.dsanalyzer.formats.emevd.emedf.decode
import com.sappyoak.dsanalyzer.formats.emevd.emedf.flagUses
import com.sappyoak.dsanalyzer.game.world.scripts.ScriptId

public data class ScriptIndexing(
    public val flagUses: List<FoundFlagUse>,
    public val calls: List<FoundCall>,
    /** Events that read their caller's arguments, and how many bytes each reads */
    public val needs: Map<EventKey, Int>
)

public fun Emevd.indexed(script: ScriptId, definitions: Emedf): ScriptIndexing {
    val flagUses = mutableListOf<FoundFlagUse>()
    val calls = mutableListOf<FoundCall>()
    val needs = mutableMapOf<EventKey, Int>()

    events.forEach { event ->
        event.callerBytesNeeded()?.let { needed -> needs[EventKey(script, event.id)] = needed }

        // Calls come off the raw instructions: the decoder models an argument per definition, and
        // 'InitializeEvent' takes a vararg tail that no definition describes
        event.instructions.forEach { instruction ->
            instruction.asEventCall()?.let { call -> calls += FoundCall(script, event.id, call) }
        }

        event.decode(definitions).forEach { decoded ->
            decoded.flagUses(event.id).forEach { use ->
                flagUses += FoundFlagUse(script, event.id, use)
            }
        }
    }

    return ScriptIndexing(flagUses, calls, needs)
}
