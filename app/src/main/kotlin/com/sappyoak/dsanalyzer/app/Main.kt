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

import com.sappyoak.dsanalyzer.app.connection.ui.ConnectionStatusBar
import com.sappyoak.dsanalyzer.app.logging.coroutineErrorLogging
import com.sappyoak.dsanalyzer.app.logging.setupLogging
import com.sappyoak.dsanalyzer.app.paths.ToolPaths
import com.sappyoak.dsanalyzer.app.settings.SettingsMessage
import com.sappyoak.dsanalyzer.app.settings.ui.SettingsProblemBanner
import com.sappyoak.dsanalyzer.app.startup.StartupMessage
import com.sappyoak.dsanalyzer.app.startup.StartupPhase
import com.sappyoak.dsanalyzer.app.startup.describe
import com.sappyoak.dsanalyzer.app.startup.ui.StartupScreen
import com.sappyoak.dsanalyzer.app.ui.NoticeBanner
import com.sappyoak.dsanalyzer.app.ui.chooseInstallationDirectory
import com.sappyoak.dsanalyzer.app.verification.VerificationMessage
import com.sappyoak.dsanalyzer.app.verification.ui.VerificationDialog
import com.sappyoak.dsanalyzer.app.workspace.ui.NewWorkspaceDialog
import com.sappyoak.dsanalyzer.app.workspace.ui.WorkspaceShell

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
                startup = model.startup,
                verification = model.verification,
                connection = model.connection,
                startupState = startupState,
                connectionState = connectionState,
                autoConnect = settingsState.settings.connection.autoConnect,
                onQuit = ::exitApplication
            )
            MaterialTheme {
                Surface {
                    Column(modifier = Modifier.fillMaxSize()) {
                        settingsState.problem?.let { problem ->
                            SettingsProblemBanner(problem) { model.settings.dispatch(SettingsMessage.ProblemDismissed) }
                        }

                        if (startupState.notices.isNotEmpty()) {
                            NoticeBanner(startupState.notices.map { it.describe() }) {
                                model.startup.dispatch(StartupMessage.NoticesDismissed)
                            }
                        }

                        Box(modifier = Modifier.weight(1f)) {
                            when (val phase = startupState.phase) {
                                is StartupPhase.Ready -> WorkspaceShell(
                                    workspace = phase.workspace,
                                    installation = startupState.installations
                                        .firstOrNull { it.id == phase.workspace.installationId },
                                    stores = model.workspace
                                )
                                else -> StartupScreen(startupState, model.startup)
                            }
                        }

                        ConnectionStatusBar(connectionState.status)
                    }

                    if (startupState.creatingWorkspace) {
                        NewWorkspaceDialog(
                            installations = startupState.installations,
                            onCreate = { name, id ->
                                model.startup.dispatch(StartupMessage.WorkspaceCreationRequested(name, id))
                            },
                            onAddInstallation = {
                                chooseInstallationDirectory()?.let {
                                    model.startup.dispatch(StartupMessage.FolderChosen(it))
                                }
                            },
                            onDismiss = { model.startup.dispatch(StartupMessage.NewWorkspaceDismissed) }
                        )
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
