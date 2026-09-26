package com.sappyoak.dsanalyzer.app.scripts

import com.sappyoak.dsanalyzer.formats.emevd.Emevd
import com.sappyoak.dsanalyzer.formats.emevd.EventNames
import com.sappyoak.dsanalyzer.formats.emevd.ScriptEvent
import com.sappyoak.dsanalyzer.formats.emevd.emedf.DecodedInstruction
import com.sappyoak.dsanalyzer.formats.emevd.emedf.Emedf
import com.sappyoak.dsanalyzer.formats.emevd.emedf.decode
import com.sappyoak.dsanalyzer.game.world.WorldRef
import com.sappyoak.dsanalyzer.game.world.scripts.ScriptId

/** One row in a script's event list */
public data class EventSummary(
    public val ref: WorldRef.ScriptEvent,
    public val id: Long,
    /** From the script's name file, which only names some events */
    public val name: String?,
    public val instructionCount: Int
) {
    public fun matches(query: String): Boolean =
        query.isBlank() ||
            id.toString().startsWith(query) ||
            name?.contains(query, ignoreCase = true) == true
}

/**
 * A parsed script with its instructions already decoded
 */
public class ScriptContents(
    public val script: ScriptId,
    public val events: List<EventSummary>,
    public val linkedFiles: List<String>,
    /** The definitions the instructions were decoded against */
    public val definitions: Emedf,
    private val decoded: Map<Long, List<DecodedInstruction>>
) {
    /** Instructions the definitions do not cover */
    public val undefinedCount: Int get() = decoded.values.sumOf { event -> event.count { it.definition == null } }

    public fun instructions(eventId: Long): List<DecodedInstruction> = decoded[eventId].orEmpty()
}

public fun Emevd.summarize(
    script: ScriptId,
    names: EventNames,
    emedf: Emedf
): ScriptContents = ScriptContents(
    script = script,
    events = events.map { it.summarize(script, names) },
    linkedFiles = linkedFiles,
    definitions = emedf,
    decoded = events.associate { it.id to it.decode(emedf) }
)

public fun ScriptEvent.summarize(script: ScriptId, names: EventNames): EventSummary = EventSummary(
    ref = WorldRef.ScriptEvent(script, id),
    id = id,
    name = names[id],
    instructionCount = instructions.size
)