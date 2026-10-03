package com.sappyoak.dsanalyzer.app

import androidx.compose.runtime.Composable
import androidx.compose.ui.window.FrameWindowScope
import androidx.compose.ui.window.MenuBar

import com.sappyoak.dsanalyzer.app.connection.ConnectionState
import com.sappyoak.dsanalyzer.app.connection.ConnectionStore
import com.sappyoak.dsanalyzer.app.connection.ui.GameConnectionMenu
import com.sappyoak.dsanalyzer.app.startup.StartupMessage
import com.sappyoak.dsanalyzer.app.startup.StartupPhase
import com.sappyoak.dsanalyzer.app.startup.StartupState
import com.sappyoak.dsanalyzer.app.startup.StartupStore
import com.sappyoak.dsanalyzer.app.ui.chooseInstallationDirectory
import com.sappyoak.dsanalyzer.app.verification.VerificationMessage
import com.sappyoak.dsanalyzer.app.verification.VerificationStore

@Composable
public fun FrameWindowScope.AppMenuBar(
    startup: StartupStore,
    verification: VerificationStore,
    connection: ConnectionStore,
    startupState: StartupState,
    connectionState: ConnectionState,
    autoConnect: Boolean,
    onQuit: () -> Unit
) {
    val workspaceOpen = startupState.phase is StartupPhase.Ready

    MenuBar {
        Menu("File", mnemonic = 'F') {
            Item("New workspace") {
                startup.dispatch(StartupMessage.NewWorkspaceRequested)
            }

            Menu("Open workspace", enabled = startupState.workspaces.isNotEmpty()) {
                startupState.workspaces.forEach { workspace ->
                    Item(workspace.name) {
                        startup.dispatch(StartupMessage.WorkspaceChosen(workspace.id))
                    }
                }
            }

            Item("Close workspace", enabled = workspaceOpen) {
                startup.dispatch(StartupMessage.WorkspacePickerRequested)
            }

            Separator()

            Item("Quit", mnemonic = 'Q', onClick = onQuit)
        }

        Menu("Installation", mnemonic = 'I') {
            Item("Add Installation") {
                chooseInstallationDirectory()?.let { startup.dispatch(StartupMessage.FolderChosen(it)) }
            }

            Menu("Verify", enabled = startupState.installations.isNotEmpty()) {
                startupState.installations.forEach { installation ->
                    Item("${installation.build.edition.name} - ${installation.root}") {
                        verification.dispatch(VerificationMessage.VerificationRequested(installation))
                    }
                }
            }
        }

        GameConnectionMenu(connectionState, autoConnect, connection)
    }
}