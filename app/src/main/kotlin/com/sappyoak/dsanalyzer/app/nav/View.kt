package com.sappyoak.dsanalyzer.app.nav

import com.sappyoak.dsanalyzer.analysis.FindingId
import com.sappyoak.dsanalyzer.analysis.InvestigationId
import com.sappyoak.dsanalyzer.game.world.maps.EntryKind
import com.sappyoak.dsanalyzer.game.world.maps.MapId
import com.sappyoak.dsanalyzer.game.world.scripts.ScriptId

public sealed interface View {
    public val section: Section

    public sealed interface Analysis : View {
        override val section: Section get() = Section.Analysis

        public data object Investigations : Analysis

        public data class Investigation(public val id: InvestigationId) : Analysis
        public data class Finding(public val id: FindingId) : Analysis
    }

    public sealed interface Data : View {
        override val section: Section get() = Section.Data

        public data object Overview : Data

        public data class Map(public val map: MapId) : Data
        public data class MapEntry(
            public val map: MapId,
            public val kind: EntryKind,
            public val index: Int
        ) : Data

        public data class Script(public val script: ScriptId) : Data
        public data class ScriptEvent(
            public val script: ScriptId,
            public val eventId: Long
        ) : Data

        public data class EventFlag(
            public val flagId: Int,
            public val seenIn: MapId? = null
        ) : Data
    }

    public data class Live(public val panel: RuntimePanel) : View {
        override val section: Section = Section.Runtime
    }
}
