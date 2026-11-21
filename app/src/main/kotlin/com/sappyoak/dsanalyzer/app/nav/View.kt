package com.sappyoak.dsanalyzer.app.nav

import com.sappyoak.dsanalyzer.analysis.FindingId
import com.sappyoak.dsanalyzer.analysis.InvestigationId
import com.sappyoak.dsanalyzer.app.data.ai.AiListKind
import com.sappyoak.dsanalyzer.app.data.flags.FlagGroup
import com.sappyoak.dsanalyzer.game.world.ai.LuaBinder
import com.sappyoak.dsanalyzer.game.world.ai.LuaScriptId
import com.sappyoak.dsanalyzer.game.world.animations.AnimationSetId
import com.sappyoak.dsanalyzer.game.world.esd.EsdSource
import com.sappyoak.dsanalyzer.game.world.maps.EntryKind
import com.sappyoak.dsanalyzer.game.world.maps.MapId
import com.sappyoak.dsanalyzer.game.world.scripts.ScriptId

public sealed interface View {
    public val section: Section

    public sealed interface Analysis : View {
        override val section: Section get() = Section.Analysis

        public data object Leads : Analysis
        public data object Investigations : Analysis

        public data class Investigation(public val id: InvestigationId) : Analysis
        public data class Finding(public val id: FindingId) : Analysis
    }

    public sealed interface Data : View {
        override val section: Section get() = Section.Data

        public data object Overview : Data

        public data object ParamSearch : Data

        public data class Param(public val file: String) : Data
        public data class ParamRow(public val file: String, public val rowId: Int) : Data

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

        /** The flags of one navigator group */
        public data class FlagList(public val group: FlagGroup) : Data

        public data class EventFlag(
            public val flagId: Int,
            public val seenIn: MapId? = null
        ) : Data

        /** The animation ids of one character or object */
        public data class AnimationSet(public val set: AnimationSetId) : Data
        public data class Animation(public val set: AnimationSetId, public val animationId: Long) : Data

        public data class AiList(public val kind: AiListKind) : Data
        public data class AiGoal(public val goalId: Int) : Data
        public data class AiLogic(public val logicId: Int) : Data

        public data class LuaFiles(public val binder: LuaBinder) : Data

        public data class LuaScript(public val script: LuaScriptId, public val function: String? = null) : Data

        public data class Esd(public val source: EsdSource) : Data
        public data class EsdState(
            public val source: EsdSource,
            public val group: Long,
            public val state: Long
        ) : Data
    }

    public data class Live(public val panel: RuntimePanel) : View {
        override val section: Section = Section.Runtime
    }

    public data class Logs(public val tab: LogTab) : View {
        override val section: Section = Section.Logs
    }
}
