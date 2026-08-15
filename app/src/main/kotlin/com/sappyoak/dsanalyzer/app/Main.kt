package com.sappyoak.dsanalyzer.app

import androidx.compose.runtime.*
import androidx.compose.ui.unit.DpSize
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.*

import com.sappyoak.dsanalyzer.app.effects.*
import com.sappyoak.dsanalyzer.app.state.*
import com.sappyoak.dsanalyzer.app.ui.AppShell

fun main() {
    val services = AppServices.create()
    val environment = services.environment

    application {
        val scope = rememberCoroutineScope()
        val store = remember {
            Store(
                initialState = AppState.from(environment),
                scope = scope,
                effects = AppEffects(
                    services = services,
                    setup = SetupEffects(services)
                )
            )
        }

        val state by store.state.collectAsState()
        val actions = remember { AppActions(store::dispatch) }

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
        }
    }
}
