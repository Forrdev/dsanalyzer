package com.sappyoak.dsanalyzer.app.data.scripts

import com.sappyoak.dsanalyzer.game.Installation
import com.sappyoak.dsanalyzer.game.world.flags.FlagNames
import com.sappyoak.dsanalyzer.game.world.scripts.ScriptId
import com.sappyoak.dsanalyzer.game.world.scripts.index.EventCallIndex
import com.sappyoak.dsanalyzer.game.world.scripts.index.FlagIndex
import com.sappyoak.dsanalyzer.game.world.scripts.index.FlagReference

public data class ScriptsState(
    public val installation: Installation? = null,
    public val scripts: List<ScriptId> = emptyList(),
    public val contents: ScriptContents? = null,
    public val loadingScript: ScriptId? = null,
    public val query: String = "",
    public val flagIndex: FlagIndex = FlagIndex.Empty,
    /** Every place every event is started, and with what */
    public val callIndex: EventCallIndex = EventCallIndex.Empty,
    /** What the game data says each flag means; filled in after the index */
    public val flagNames: FlagNames = FlagNames.Empty,
    /** Why [flagNames] stayed empty, kept apart from [problem] so it does not hide a script's own */
    public val namingProblem: String? = null,
    public val indexing: Boolean = false,
    /** Scripts the index could not read */
    public val unreadable: List<ScriptId> = emptyList(),
    /** A flag follow waiting on the index */
    public val findings: FlagFindings? = null,
    public val problem: String? = null
) {
    public val visible: List<EventSummary> by lazy {
        contents?.events.orEmpty().filter { it.matches(query) }
    }

    public val loading: Boolean get() = loadingScript != null
    public val shown: ScriptId? get() = contents?.script ?: loadingScript

    public fun event(eventId: Long): EventSummary? = contents?.event(eventId)
}

/**
 * What searching a flag resulted in. Kept whole rather than reduced to the one jumped to,
 * because the others are usually the interesting part.
 */
public data class FlagFindings(
    public val flagId: Int,
    public val references: List<FlagReference>
)
