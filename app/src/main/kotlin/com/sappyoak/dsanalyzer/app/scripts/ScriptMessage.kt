package com.sappyoak.dsanalyzer.app.scripts

import com.sappyoak.dsanalyzer.game.Installation
import com.sappyoak.dsanalyzer.game.world.WorldRef
import com.sappyoak.dsanalyzer.game.world.maps.MapId
import com.sappyoak.dsanalyzer.game.world.scripts.ScriptId

public sealed interface ScriptsMessage {
    /** The workspace opened over this installation */
    public data class Opened(public val installation: Installation) : ScriptsMessage

    public data class CatalogLoaded(public val scripts: List<ScriptId>) : ScriptsMessage

    public data class ScriptLoaded(public val contents: ScriptContents) : ScriptsMessage

    public data class FlagsIndexed(
        public val index: FlagIndex,
        public val unreadable: List<ScriptId>
    ) : ScriptsMessage

    public data class ScriptSelected(public val script: ScriptId) : ScriptsMessage

    /** Following a link, a list selection, or a finding */
    public data class Navigated(public val ref: WorldRef.ScriptEvent) : ScriptsMessage

    public data class FlagRequested(
        public val flagId: Int,
        public val seenIn: MapId? = null
    ) : ScriptsMessage
    public data class QueryChanged(public val query: String) : ScriptsMessage

    public data class Failed(public val reason: String) : ScriptsMessage
}