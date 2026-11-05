package com.sappyoak.dsanalyzer.app.shell

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier

import com.sappyoak.dsanalyzer.app.analysis.AnalysisMessage
import com.sappyoak.dsanalyzer.app.data.esd.EsdsMessage
import com.sappyoak.dsanalyzer.app.data.maps.MapsMessage
import com.sappyoak.dsanalyzer.app.data.params.ParamsMessage
import com.sappyoak.dsanalyzer.app.data.scripts.ScriptsMessage
import com.sappyoak.dsanalyzer.app.nav.Navigator
import com.sappyoak.dsanalyzer.app.nav.ResolveContext
import com.sappyoak.dsanalyzer.app.nav.View
import com.sappyoak.dsanalyzer.app.nav.WorkspaceResolveContext
import com.sappyoak.dsanalyzer.app.nav.resolve
import com.sappyoak.dsanalyzer.app.runtime.OverlaySettings
import com.sappyoak.dsanalyzer.app.runtime.RuntimeMessage
import com.sappyoak.dsanalyzer.app.runtime.RuntimeSettings
import com.sappyoak.dsanalyzer.app.settings.SettingsEdit
import com.sappyoak.dsanalyzer.app.workspace.Workspace
import com.sappyoak.dsanalyzer.app.workspace.WorkspaceStores
import com.sappyoak.dsanalyzer.game.Installation
import com.sappyoak.dsanalyzer.game.ref.Address

@Composable
public fun WorkspaceShell(
    workspace: Workspace,
    installation: Installation?,
    stores: WorkspaceStores,
    overlay: OverlaySettings,
    runtime: RuntimeSettings,
    onSettingsEdit: (SettingsEdit) -> Unit
) {
    val navigator = remember { Navigator() }
    var settingsOpen by remember { mutableStateOf(false) }

    LaunchedEffect(installation?.id) {
        installation?.let {
            stores.analysis.dispatch(AnalysisMessage.Opened(it))
            stores.params.dispatch(ParamsMessage.Opened(it))
            stores.esds.dispatch(EsdsMessage.Opened(it))
            stores.maps.dispatch(MapsMessage.Opened(it))
            stores.scripts.dispatch(ScriptsMessage.Opened(it))
            stores.runtime.dispatch(RuntimeMessage.Opened(it))
        }
    }

    val context: ResolveContext by remember(stores) { stores.resolveContext() }
        .collectAsState(WorkspaceResolveContext.empty())
    val current = context
    val resolve = remember(current) { { address: Address -> current.resolve(address) } }

    val flagsBuilt by remember(stores) { stores.flagIndexBuilt() }
        .collectAsState(stores.scripts.state.value.flagIndex.built)

    LaunchedEffect(navigator.current, flagsBuilt) {
        stores.sync(navigator.current)
    }

    Box(modifier = Modifier.fillMaxSize()) {
        Shell(
            workspace = workspace.name,
            installation = installation?.let { "${it.build.edition.name} - ${it.root}" },
            navigator = navigator,
            runtimeState = stores.runtime.state,
            onSettings = { settingsOpen = true },
            navigatorPane = { view -> NavigatorPane(view, stores, navigator::go) },
            detailPane = { view -> DetailPane(view, stores, runtime, resolve, navigator::go) }
        )

        if (settingsOpen) {
            SettingsSheet(
                overlay = overlay,
                runtime = runtime,
                onEdit = onSettingsEdit,
                onDismiss = { settingsOpen = false }
            )
        }
    }
}

private fun WorkspaceStores.sync(view: View) {
    when (view) {
        is View.Data.Map -> maps.dispatch(MapsMessage.MapSelected(view.map))
        is View.Data.MapEntry -> maps.dispatch(MapsMessage.MapSelected(view.map))
        is View.Data.Script -> scripts.dispatch(ScriptsMessage.ScriptSelected(view.script))
        is View.Data.ScriptEvent -> scripts.dispatch(ScriptsMessage.ScriptSelected(view.script))
        is View.Data.EventFlag ->
            scripts.dispatch(ScriptsMessage.FlagRequested(view.flagId, view.seenIn))
        is View.Data.Esd -> esds.dispatch(EsdsMessage.SourceSelected(view.source))
        is View.Data.EsdState -> esds.dispatch(EsdsMessage.SourceSelected(view.source))
        is View.Data.Overview, is View.Data.ParamSearch, is View.Data.Param, is View.Data.ParamRow,
        is View.Analysis, is View.Live -> Unit
    }
}
