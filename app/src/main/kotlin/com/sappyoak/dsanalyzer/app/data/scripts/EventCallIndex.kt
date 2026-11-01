package com.sappyoak.dsanalyzer.app.data.scripts

import com.sappyoak.dsanalyzer.formats.emevd.ArgData
import com.sappyoak.dsanalyzer.formats.emevd.EventCall
import com.sappyoak.dsanalyzer.game.world.scripts.ScriptId

/** An event, named by the script that defines it */
public data class EventKey(public val script: ScriptId, public val eventId: Long)

/** One 'InitializeEvent' and the event it was found in */
public data class FoundCall(
    public val script: ScriptId,
    public val callerEventId: Long,
    public val call: EventCall
)

/** Somewhere an event is started, and what it is handed */
public data class CallSite(
    public val script: ScriptId,
    public val callerEventId: Long,
    public val slot: Int,
    /** The caller's whole argument block; [BlockBase] says where the callee's reads begin */
    public val args: ArgData
)

/** Every place every event is started */
public class EventCallIndex private constructor(
    private val byEvent: Map<Long, List<CallSite>>,
    public val scripts: Int,
    public val base: BlockBase
) {
    public val built: Boolean get() = scripts > 0
    public val sites: Int get() = byEvent.values.sumOf { it.size }

    /** Every place an event is started, nearest script first */
    public fun sitesFor(event: EventKey): List<CallSite> = byEvent[event.eventId]
        .orEmpty()
        .sortedWith(compareBy({ it.script.nearness(event.script) }, { it.callerEventId }))

    public companion object {
        public val Empty: EventCallIndex =
            EventCallIndex(emptyMap(), scripts = 0, base = BlockBase.Ambiguous)

        public fun of(
            found: List<FoundCall>,
            needs: Map<EventKey, Int>,
            scripts: Int
        ): EventCallIndex = EventCallIndex(
            byEvent = found.groupBy({ it.call.eventId }, { it.site() }),
            scripts = scripts,
            base = deriveBlockBase(found.mapNotNull { it.fit(needs) })
        )
    }
}

private fun FoundCall.site() = CallSite(
    script = script,
    callerEventId = callerEventId,
    slot = call.slot,
    args = call.args
)

private fun FoundCall.fit(needs: Map<EventKey, Int>): BlockFit? {
    val needed = needs[EventKey(script, call.eventId)] ?: return null
    return BlockFit(argsLength = call.args.size, needed = needed)
}

/** How near a script is to the one defining the event being looked up */
private fun ScriptId.nearness(to: ScriptId): Int = when {
    this == to -> 0
    this == ScriptId.Common -> 1
    else -> 2
}
