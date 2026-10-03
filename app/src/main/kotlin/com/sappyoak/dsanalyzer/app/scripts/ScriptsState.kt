package com.sappyoak.dsanalyzer.app.scripts

import com.sappyoak.dsanalyzer.game.Installation
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
    public val pendingFlag: FlagSearch? = null,
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
 * A flag being searched for and where there is left to look
 *
 * Carried across loads because looking in a script means loading it first, and a flag
 * seen in a map is as likely to have been set by the common script as by the map itself
 */
public data class FlagSearch(
    public val flagId: Int,
    public val places: List<ScriptId>,
    public val looked: Int = 0
) {
    public val next: ScriptId? get() = places.getOrNull(looked)
    public val advanced: FlagSearch get() = copy(looked = looked + 1)
}