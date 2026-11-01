package com.sappyoak.dsanalyzer.app.shell

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier

import com.sappyoak.dsanalyzer.app.data.maps.MapsMessage
import com.sappyoak.dsanalyzer.app.data.maps.ui.MapsDetail
import com.sappyoak.dsanalyzer.app.data.params.ParamsMessage
import com.sappyoak.dsanalyzer.app.data.params.ui.ParamsDetail
import com.sappyoak.dsanalyzer.app.data.scripts.ScriptsMessage
import com.sappyoak.dsanalyzer.app.data.scripts.ui.ScriptsDetail
import com.sappyoak.dsanalyzer.app.data.ui.DataNavigator
import com.sappyoak.dsanalyzer.app.nav.Navigator
import com.sappyoak.dsanalyzer.app.nav.Resolution
import com.sappyoak.dsanalyzer.app.nav.ResolveContext
import com.sappyoak.dsanalyzer.app.nav.View
import com.sappyoak.dsanalyzer.app.nav.WorkspaceResolveContext
import com.sappyoak.dsanalyzer.app.nav.resolve
import com.sappyoak.dsanalyzer.app.runtime.OverlaySettings
import com.sappyoak.dsanalyzer.app.runtime.RuntimeMessage
import com.sappyoak.dsanalyzer.app.runtime.RuntimeSettings
import com.sappyoak.dsanalyzer.app.runtime.ui.RuntimeDetail
import com.sappyoak.dsanalyzer.app.runtime.ui.RuntimeNavigator
import com.sappyoak.dsanalyzer.app.settings.SettingsEdit
import com.sappyoak.dsanalyzer.app.ui.theme.AnalyzerTheme
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

    val paramsState by stores.params.state.collectAsState()
    val mapsState by stores.maps.state.collectAsState()
    val scriptsState by stores.scripts.state.collectAsState()
    val runtimeState by stores.runtime.state.collectAsState()

    LaunchedEffect(installation?.id) {
        installation?.let {
            stores.params.dispatch(ParamsMessage.Opened(it))
            stores.maps.dispatch(MapsMessage.Opened(it))
            stores.scripts.dispatch(ScriptsMessage.Opened(it))
            stores.runtime.dispatch(RuntimeMessage.Opened(it))
        }
    }

    val context: ResolveContext = remember(mapsState.maps, mapsState.entities, scriptsState.scripts, paramsState.entries) {
        WorkspaceResolveContext(
            maps = mapsState.maps.toSet(),
            scripts = scriptsState.scripts.toSet(),
            params = paramsState.entries.keys,
            entryForEntity = { id -> mapsState.entities[id].firstOrNull() }
        )
    }

    val resolve: (Address) -> Resolution = { address -> context.resolve(address) }

    LaunchedEffect(navigator.current, scriptsState.flagIndex.built) {
        stores.sync(navigator.current)
    }


    Box(modifier = Modifier.fillMaxSize()) {
        Shell(
            workspace = workspace.name,
            installation = installation?.let { "${it.build.edition.name} - ${it.root}"},
            navigator = navigator,
            runtimeState = runtimeState,
            attached = runtimeState.live,
            onSettings = { settingsOpen = true },
            navigatorPane = { view ->
                when (view) {
                    is View.Data -> DataNavigator(
                        params = paramsState.catalog,
                        maps = mapsState.maps,
                        scripts = scriptsState.scripts,
                        view = view,
                        onFollow = navigator::go
                    )
                    is View.Live -> RuntimeNavigator(runtimeState, view, navigator::go)
                    is View.Analysis -> Unbuilt("Analysis not yet built")
                }
            },
            detailPane = { view ->
                when (view) {
                    is View.Data.Script,
                    is View.Data.ScriptEvent,
                    is View.Data.EventFlag -> ScriptsDetail(scriptsState, stores.scripts, view, resolve, navigator::go)

                    is View.Data.Param,
                    is View.Data.ParamRow -> ParamsDetail(paramsState, stores.params, view, resolve, navigator::go)

                    is View.Data -> MapsDetail(mapsState, stores.maps,  view, resolve, navigator::go)
                    is View.Live -> RuntimeDetail(
                        state = runtimeState,
                        settings = runtime,
                        panel = view.panel,
                        onFollow = navigator::go,
                        onReadPlacedEnemies = {
                            stores.runtime.dispatch(RuntimeMessage.PlacedEnemiesRequested)
                        },
                        onReadCollision = {
                            stores.runtime.dispatch(RuntimeMessage.CollisionRequested)
                        }
                    )

                    is View.Analysis -> Unbuilt("Analysis not yet built")
                }
            }
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
        is View.Data.Overview, is View.Data.Param, is View.Data.ParamRow, is View.Analysis, is View.Live -> Unit
    }
}

@Composable
private fun Unbuilt(what: String) {
    Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
        Text(what, style = AnalyzerTheme.text.label, color = AnalyzerTheme.colors.textLo)
    }
}
