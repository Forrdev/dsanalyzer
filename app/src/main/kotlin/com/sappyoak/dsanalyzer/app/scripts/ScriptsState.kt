package com.sappyoak.dsanalyzer.app.scripts

import com.sappyoak.dsanalyzer.game.Installation
import com.sappyoak.dsanalyzer.game.world.maps.MapId
import com.sappyoak.dsanalyzer.game.world.scripts.ScriptId

public data class ScriptsState(
    public val installation: Installation? = null,
    public val scripts: List<ScriptId> = emptyList(),
    public val selected: ScriptId? = null,
    public val contents: ScriptContents? = null,
    public val loading: Boolean = false,
    public val query: String = "",
    public val focused: Long? = null,
    public val pending: Long? = null,
    /** Every script's flag references */
    public val flagIndex: FlagIndex = FlagIndex.Empty,
    public val indexing: Boolean = false,
    /** Scripts the index could not read */
    public val unreadable: List<ScriptId> = emptyList(),
    /** A flag follow waiting on the index */
    public val pendingFlag: FlagRequest? = null,
    public val findings: FlagFindings? = null,
    public val problem: String? = null
) {
    public val visible: List<EventSummary> by lazy {
        contents?.events.orEmpty().filter { it.matches(query) }
    }

    public val focusedEvent: EventSummary? by lazy {
        focused?.let { id -> contents?.event(id) }
    }
}

/**
 * What searching a flag resulted in. Kept whole rather than reduced to the one jumped to,
 * because the others are usually the interesting part.
 */
public data class FlagFindings(
    public val flagId: Int,
    public val references: List<FlagReference>,
    public val chosen: FlagReference?
) {
    public val others: List<FlagReference> get() = references.filterNot { it == chosen }
}

/** A flag follow that arrived before the index was finished */
public data class FlagRequest(public val flagId: Int, public val seenIn: MapId?)