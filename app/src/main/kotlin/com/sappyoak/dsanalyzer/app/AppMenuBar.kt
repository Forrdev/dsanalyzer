package com.sappyoak.dsanalyzer.app

import androidx.compose.runtime.Composable
import androidx.compose.ui.window.FrameWindowScope
import androidx.compose.ui.window.MenuBar
import com.sappyoak.dsanalyzer.app.startup.StartupMessage
import com.sappyoak.dsanalyzer.app.startup.StartupPhase
import com.sappyoak.dsanalyzer.app.startup.StartupState
import com.sappyoak.dsanalyzer.app.startup.StartupStore
import com.sappyoak.dsanalyzer.app.ui.chooseInstallationDirectory
import com.sappyoak.dsanalyzer.app.verification.VerificationMessage
import com.sappyoak.dsanalyzer.app.verification.VerificationStore

@Composable
public fun FrameWindowScope.AppMenuBar(
    state: StartupState,
    startupStore: StartupStore,
    verificationStore: VerificationStore,
    onQuit: () -> Unit
) {
    val workspaceOpen = state.phase is StartupPhase.Ready

    MenuBar {
        Menu("File", mnemonic = 'F') {
            Item("New workspace") {
                startupStore.dispatch(StartupMessage.NewWorkspaceRequested)
            }

            Menu("Open workspace", enabled = state.workspaces.isNotEmpty()) {
                state.workspaces.forEach { workspace ->
                    Item(workspace.name) {
                        startupStore.dispatch(StartupMessage.WorkspaceChosen(workspace.id))
                    }
                }
            }

            Item("Close workspace", enabled = workspaceOpen) {
                startupStore.dispatch(StartupMessage.WorkspacePickerRequested)
            }

            Separator()

            Item("Quit", mnemonic = 'Q', onClick = onQuit)
        }

        Menu("Installation", mnemonic = 'I') {
            Item("Add Installation") {
                chooseInstallationDirectory()?.let { startupStore.dispatch(StartupMessage.FolderChosen(it)) }
            }

            Menu("Verify", enabled = state.installations.isNotEmpty()) {
                state.installations.forEach { installation ->
                    Item("${installation.build.edition.name} - ${installation.root}") {
                        verificationStore.dispatch(VerificationMessage.VerificationRequested(installation))
                    }
                }
            }
        }
    }
}