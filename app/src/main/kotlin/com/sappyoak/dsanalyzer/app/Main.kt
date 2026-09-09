package com.sappyoak.dsanalyzer.app

import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
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

    application {
        Window(
            onCloseRequest = ::exitApplication,
            title = "dsanalyzer",
            state = rememberWindowState(size = DpSize(1280.dp, 800.dp))
        ) {
            AppRoot()
        }
    }
}

@Composable
private fun AppRoot() {
    MaterialTheme {
        Surface {
            Text("dsanalyzer")
        }
    }
}