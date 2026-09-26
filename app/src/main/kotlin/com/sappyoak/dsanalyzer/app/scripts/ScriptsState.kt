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
    public val problem: String? = null
) {
    public val visible: List<EventSummary>
        get() = contents?.events.orEmpty().filter { it.matches(query) }

    public val focusedEvent: EventSummary?
        get() = focused?.let { id -> contents?.events?.firstOrNull { it.id == id } }
}