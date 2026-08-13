package com.sappyoak.dsanalyzer.app

import androidx.compose.ui.unit.DpSize
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.*

import com.sappyoak.dsanalyzer.app.config.AppEnvironment


fun main() {
    val environment = AppEnvironment.load()

    application {

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
