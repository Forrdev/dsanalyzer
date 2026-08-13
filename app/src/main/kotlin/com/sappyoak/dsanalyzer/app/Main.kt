package com.sappyoak.dsanalyzer.app

import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.unit.DpSize
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.*

import com.sappyoak.dsanalyzer.app.config.AppEnvironment
import com.sappyoak.dsanalyzer.app.state.*

fun main() {
    val environment = AppEnvironment.load()

    application {
        val scope = rememberCoroutineScope()
        val store = remember {
            Store(
                initialState = AppState.from(environment),
                scope = scope,
                effects = AppEffects(
                    environment = environment
                )
            )
        }

        val state = store.state.collectAsState()
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
            onKeyEvent = { event ->
                false
            }
        ) {}
    }
}
