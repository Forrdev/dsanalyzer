package com.sappyoak.dsanalyzer.app.data.ui

import com.sappyoak.dsanalyzer.app.data.DataKind
import com.sappyoak.dsanalyzer.app.data.ai.AiState
import com.sappyoak.dsanalyzer.app.data.animations.AnimationsState
import com.sappyoak.dsanalyzer.app.data.flags.FlagCatalog
import com.sappyoak.dsanalyzer.app.data.params.ParamSummary
import com.sappyoak.dsanalyzer.game.world.esd.EsdSource
import com.sappyoak.dsanalyzer.game.world.maps.MapId
import com.sappyoak.dsanalyzer.game.world.scripts.ScriptId

/** What the data navigator lists under each kind */
public data class NavigatorContents(
    val params: List<ParamSummary>,
    val esds: List<EsdSource>,
    val maps: List<MapId>,
    val scripts: List<ScriptId>,
    val flags: FlagCatalog,
    val animations: AnimationsState,
    val ai: AiState
) {
    /** The count beside a kind, or none while it is still being read */
    fun countOf(kind: DataKind): Int? = when (kind) {
        DataKind.Params -> params.size
        DataKind.Maps -> maps.size
        DataKind.Scripts -> scripts.size
        DataKind.Esd -> esds.size
        DataKind.EventFlags -> flags.size.takeIf { it > 0 }
        DataKind.Animations -> animations.sets.size.takeIf { it > 0 }
        DataKind.AiScripts -> ai.binders.values.sumOf { it.size }.takeIf { it > 0 }
    }
}
