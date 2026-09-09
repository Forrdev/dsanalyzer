package com.sappyoak.dsanalyzer.app

import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.runtime.*
import androidx.compose.ui.unit.*
import androidx.compose.ui.window.*
import io.github.oshai.kotlinlogging.KotlinLogging
import kotlinx.coroutines.CoroutineName
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.SupervisorJob

import com.sappyoak.dsanalyzer.app.logging.coroutineErrorLogging
import com.sappyoak.dsanalyzer.app.logging.setupLogging
import com.sappyoak.dsanalyzer.app.paths.ToolPaths
import com.sappyoak.dsanalyzer.app.startup.StartupMessage
import com.sappyoak.dsanalyzer.app.startup.ui.StartupScreen

fun main() {
    val paths = ToolPaths()
    setupLogging(paths.logs)

    val logger = KotlinLogging.logger("com.sappyoak.dsanalyzer.app.Main")
    logger.info { "dsanalyzer start. Data root: ${paths.root}" }

    val scope = CoroutineScope(
        SupervisorJob() +
        Dispatchers.Default +
        CoroutineName("RootAppCoroutine") +
        coroutineErrorLogging()
    )

    val model = createAppModel(scope, paths)
    model.startup.dispatch(StartupMessage.Start)

    application {
        Window(
            onCloseRequest = ::exitApplication,
            title = "dsanalyzer",
            state = rememberWindowState(size = DpSize(1280.dp, 800.dp))
        ) {
            val state by model.startup.state.collectAsState()

            AppMenuBar(state, model.startup, onQuit = ::exitApplication)
            MaterialTheme {
                Surface {
                    StartupScreen(state, model.startup)
                }
            }
        }
    }
}
