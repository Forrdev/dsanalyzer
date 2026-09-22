package com.sappyoak.dsanalyzer.app

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
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
import com.sappyoak.dsanalyzer.app.settings.SettingsMessage
import com.sappyoak.dsanalyzer.app.settings.ui.SettingsProblemBanner
import com.sappyoak.dsanalyzer.app.startup.ui.StartupScreen
import com.sappyoak.dsanalyzer.app.verification.VerificationMessage
import com.sappyoak.dsanalyzer.app.verification.ui.VerificationDialog

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
    model.start()

    application {
        Window(
            onCloseRequest = ::exitApplication,
            title = "dsanalyzer",
            state = rememberWindowState(size = DpSize(1280.dp, 800.dp))
        ) {
            val settingsState by model.settings.state.collectAsState()
            val startupState by model.startup.state.collectAsState()
            val verificationState by model.verification.state.collectAsState()
            val connectionState by model.connection.state.collectAsState()

            AppMenuBar(
                model = model,
                state = startupState,
                connection = connectionState,
                autoConnect = settingsState.settings.connection.autoConnect,
                onQuit = ::exitApplication
            )
            MaterialTheme {
                Surface {
                    Column(modifier = Modifier.fillMaxSize()) {
                        settingsState.problem?.let { problem ->
                            SettingsProblemBanner(problem) { model.settings.dispatch(SettingsMessage.ProblemDismissed) }
                        }

                        Box(modifier = Modifier.weight(1f)) {
                            StartupScreen(startupState, model.startup)
                        }
                    }

                    verificationState.viewing
                        ?.let { id -> startupState.installations.firstOrNull { it.id == id } }
                        ?.let { installation -> VerificationDialog(
                            installation = installation,
                            status = verificationState.statuses[installation.id],
                            onDismiss = { model.verification.dispatch(VerificationMessage.Dismissed) }
                        ) }
                }
            }
        }
    }
}
