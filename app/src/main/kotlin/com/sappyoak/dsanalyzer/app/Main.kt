package com.sappyoak.dsanalyzer.app

import androidx.compose.runtime.*
import androidx.compose.ui.unit.DpSize
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.*
import kotlinx.coroutines.*
import com.sappyoak.dsanalyzer.app.effects.*
import com.sappyoak.dsanalyzer.app.state.*
import com.sappyoak.dsanalyzer.app.state.global.FirstRunSequence
import com.sappyoak.dsanalyzer.app.state.workspace.WorkspaceAction
import com.sappyoak.dsanalyzer.app.ui.AppShell

fun main() {
    application {
        val services = remember { AppServices.create() }
        val scope = rememberCoroutineScope()

        val store = remember {
            Store(
                scope = scope,
                effects = AppEffects(
                    services = services,
                    setup = SetupEffects(services)
                )
            )
        }

        val state by store.state.collectAsState()

        LaunchedEffect(Unit) {
            val settings = services.environment.settings

            if (settings.workspaces.isEmpty()) {
                store.dispatchLifecycle(WorkspaceAction.FirstRunStarted(
                    FirstRunSequence(dataPath = settings.dataPath)
                ))
            } else {
                val restored = settings.workspaces.mapNotNull { persisted ->
                    services.environment.restoreWorkspace(persisted)
                }

                store.dispatchLifecycle(WorkspaceAction.Restored(restored, settings.activeWorkspace))
            }
        }

        val firstRun = state.global.firstRun
        if (firstRun != null) {
            Window(
                onCloseRequest = ::exitApplication,
                title = "DSAnalyzer -- Setup",
                state = rememberWindowState(size = DpSize(760.dp, 620.dp))
            ) {
                // First time window
            }
            return@application
        }

        state.workspaces.values.forEach { workspace ->
            val scoped = remember(workspace.id) { store.scopedTo(workspace.id) }
            val actions = remember(workspace.id) { AppActions(services.gameInstallInspector, scoped) }

            Window(
                onCloseRequest = {
                    if (state.workspaces.size == 1) {
                        services.environment.persistWorkspaces(
                            state.workspaces.values,
                            state.activeWorkspace
                        )
                        services.close()
                        exitApplication()
                    } else {
                        store.dispatchLifecycle(WorkspaceAction.Close(workspace.id))
                    }
                },
                title = workspace.title,
                // need to parse previous state settings for each window now, temp until then
                state = rememberWindowState(size = DpSize(1400.dp, 900.dp))
            ) {
                "Meow"
            }
        }
       // val actions = remember { AppActions(services.gameInstallInspector, store::dispatch) }

        /*
        val windowSettings = environment.settings.window
        val (width, height) = windowSettings.targetDimensions

        Window(
            onCloseRequest = {
                exitApplication()
            },
            title = "DSAnalyzer",
            state = rememberWindowState(
                placement = windowSettings.getWindowPlacement(),
                position = windowSettings.getWindowPosition(),
                size = DpSize(width.dp, height.dp)
            ),
            onKeyEvent=actions::handleKeyEvent
        ) {
            AppShell(state, actions)
        } */
    }
}
