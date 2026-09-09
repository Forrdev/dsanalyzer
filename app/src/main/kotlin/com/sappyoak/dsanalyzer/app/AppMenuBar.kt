package com.sappyoak.dsanalyzer.app

import androidx.compose.runtime.Composable
import androidx.compose.ui.window.FrameWindowScope
import androidx.compose.ui.window.MenuBar
import com.sappyoak.dsanalyzer.app.startup.StartupMessage
import com.sappyoak.dsanalyzer.app.startup.StartupPhase
import com.sappyoak.dsanalyzer.app.startup.StartupState
import com.sappyoak.dsanalyzer.app.startup.StartupStore

@Composable
public fun FrameWindowScope.AppMenuBar(
    state: StartupState,
    store: StartupStore,
    onQuit: () -> Unit
) {
    val workspaceOpen = state.phase is StartupPhase.Ready

    MenuBar {
        Menu("File", mnemonic = 'F') {
            Item("New workspace") {
                store.dispatch(StartupMessage.NewWorkspaceRequested)
            }

            Menu("Open workspace", enabled = state.workspaces.isNotEmpty()) {
                state.workspaces.forEach { workspace ->
                    Item(workspace.name) {
                        store.dispatch(StartupMessage.WorkspaceChosen(workspace.id))
                    }
                }
            }

            Item("Close workspace", enabled = workspaceOpen) {
                store.dispatch(StartupMessage.WorkspacePickerRequested)
            }

            Separator()

            Item("Quit", mnemonic = 'Q', onClick = onQuit)
        }
    }
}