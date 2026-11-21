package com.sappyoak.dsanalyzer.app.shell

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.ColumnScope
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier

import com.sappyoak.dsanalyzer.app.analysis.ui.AnalysisNavigator
import com.sappyoak.dsanalyzer.app.analysis.ui.LeadsPane
import com.sappyoak.dsanalyzer.app.data.ai.ui.AiDetail
import com.sappyoak.dsanalyzer.app.data.animations.ui.AnimationsPane
import com.sappyoak.dsanalyzer.app.data.esd.ui.EsdDetail
import com.sappyoak.dsanalyzer.app.data.flags.flagLive
import com.sappyoak.dsanalyzer.app.data.flags.ui.FlagListPane
import com.sappyoak.dsanalyzer.app.data.maps.ui.MapsDetail
import com.sappyoak.dsanalyzer.app.data.params.ui.ParamSearchPane
import com.sappyoak.dsanalyzer.app.data.params.ui.ParamsDetail
import com.sappyoak.dsanalyzer.app.data.scripts.ui.FlagPane
import com.sappyoak.dsanalyzer.app.data.scripts.ui.ScriptsDetail
import com.sappyoak.dsanalyzer.app.data.ui.DataNavigator
import com.sappyoak.dsanalyzer.app.data.ui.NavigatorContents
import com.sappyoak.dsanalyzer.app.nav.Resolution
import com.sappyoak.dsanalyzer.app.nav.View
import com.sappyoak.dsanalyzer.app.runtime.RuntimeMessage
import com.sappyoak.dsanalyzer.app.runtime.ui.RuntimeDetail
import com.sappyoak.dsanalyzer.app.runtime.ui.RuntimeNavigator
import com.sappyoak.dsanalyzer.app.ui.theme.AnalyzerTheme
import com.sappyoak.dsanalyzer.app.workspace.WorkspaceStores
import com.sappyoak.dsanalyzer.game.ref.Address

@Composable
internal fun ColumnScope.NavigatorPane(view: View, stores: WorkspaceStores, onFollow: (View) -> Unit) {
    when (view) {
        is View.Data -> {
            val params by stores.params.state.collectAsState()
            val esds by stores.esds.state.collectAsState()
            val maps by stores.maps.state.collectAsState()
            val scripts by stores.scripts.state.collectAsState()
            val animations by stores.animations.state.collectAsState()
            val ai by stores.ai.state.collectAsState()
            val contents = NavigatorContents(
                params.catalog, esds.sources, maps.maps, scripts.scripts, scripts.flagCatalog, animations, ai
            )
            DataNavigator(contents, view, onFollow)
        }
        is View.Live -> {
            val runtime by stores.runtime.state.collectAsState()
            RuntimeNavigator(runtime, view, onFollow)
        }
        is View.Analysis.Leads -> {
            val analysis by stores.analysis.state.collectAsState()
            AnalysisNavigator(analysis, stores.analysis)
        }
        is View.Analysis -> Unbuilt("Investigations not yet built")
    }
}

@Composable
internal fun DetailPane(
    view: View,
    stores: WorkspaceStores,
    resolve: (Address) -> Resolution,
    onFollow: (View) -> Unit
) {
    when (view) {
        is View.Data.FlagList -> {
            val state by stores.scripts.state.collectAsState()
            FlagListPane(state.flagCatalog, state.flagNames, view.group, onFollow)
        }

        is View.Data.EventFlag -> {
            val state by stores.scripts.state.collectAsState()
            val runtime by stores.runtime.state.collectAsState()
            FlagPane(state, view.flagId, runtime.flagLive(view.flagId), onFollow)
        }

        is View.Data.Script,
        is View.Data.ScriptEvent -> {
            val state by stores.scripts.state.collectAsState()
            ScriptsDetail(state, stores.scripts, view, resolve, onFollow)
        }

        is View.Data.AnimationSet,
        is View.Data.Animation -> {
            val state by stores.animations.state.collectAsState()
            AnimationsPane(state, view, onFollow)
        }

        is View.Data.AiList,
        is View.Data.AiGoal,
        is View.Data.AiLogic,
        is View.Data.LuaFiles,
        is View.Data.LuaScript -> {
            val state by stores.ai.state.collectAsState()
            val params by stores.params.state.collectAsState()
            AiDetail(state, params, view, onFollow)
        }

        is View.Data.Esd,
        is View.Data.EsdState -> {
            val state by stores.esds.state.collectAsState()
            EsdDetail(state, view, resolve, onFollow)
        }

        is View.Data.ParamSearch -> {
            val state by stores.params.state.collectAsState()
            ParamSearchPane(state, stores.params, onFollow)
        }
        is View.Data.Param,
        is View.Data.ParamRow -> {
            val state by stores.params.state.collectAsState()
            ParamsDetail(state, stores.params, view, resolve, onFollow)
        }

        is View.Data -> {
            val state by stores.maps.state.collectAsState()
            MapsDetail(state, stores.maps, view, resolve, onFollow)
        }

        is View.Live -> {
            val state by stores.runtime.state.collectAsState()
            val scripts by stores.scripts.state.collectAsState()
            RuntimeDetail(
                state = state,
                panel = view.panel,
                flagNames = scripts.flagNames,
                onFollow = onFollow,
                onReadPlacedEnemies = { stores.runtime.dispatch(RuntimeMessage.PlacedEnemiesRequested) },
                onInstallDraw = { stores.runtime.dispatch(RuntimeMessage.EngineDrawInstallRequested(it)) },
                onReadCharacterEffects = { stores.runtime.dispatch(RuntimeMessage.CharacterEffectsRequested) },
                onAnimate = { stores.runtime.dispatch(RuntimeMessage.AnimationTargetChosen(it)) }
            )
        }

        is View.Analysis.Leads -> {
            val state by stores.analysis.state.collectAsState()
            LeadsPane(state, resolve, onFollow)
        }
        is View.Analysis -> Unbuilt("Investigations not yet built")
    }
}

@Composable
private fun Unbuilt(what: String) {
    Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
        Text(
            text = what,
            style = AnalyzerTheme.text.label,
            color = AnalyzerTheme.colors.textLo
        )
    }
}
