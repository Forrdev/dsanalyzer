package com.sappyoak.dsanalyzer.app.startup.ui

import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.*
import androidx.compose.ui.unit.dp
import javax.swing.JFileChooser
import java.nio.file.Path

import com.sappyoak.dsanalyzer.app.startup.*
import com.sappyoak.dsanalyzer.app.ui.chooseInstallationDirectory
import com.sappyoak.dsanalyzer.app.workspace.ui.NewWorkspaceDialog
import com.sappyoak.dsanalyzer.app.workspace.ui.WorkspacePicker
import com.sappyoak.dsanalyzer.game.RejectionReason

@Composable
public fun StartupScreen(state: StartupState, store: StartupStore) {
    Column(modifier = Modifier.fillMaxSize()) {
        if (state.notices.isNotEmpty()) {
            NoticeBanner(state.notices) { store.dispatch(StartupMessage.NoticesDismissed) }
        }

        Box(modifier = Modifier.weight(1f)) {
            PhaseContent(state, store)
        }
    }

    if (state.creatingWorkspace) {
        NewWorkspaceDialog(
            installations = state.installations,
            onCreate = { name, id ->
                store.dispatch(StartupMessage.WorkspaceCreationRequested(name, id) )
            },
            onAddInstallation = {
                chooseInstallationDirectory()?.let { store.dispatch(
                    StartupMessage.FolderChosen(it)
                )}
            },
            onDismiss = {
                store.dispatch(StartupMessage.NewWorkspaceDismissed)
            }
        )
    }
}

@Composable
private fun PhaseContent(state: StartupState, store: StartupStore) {
    when (val phase = state.phase) {
        StartupPhase.Loading -> Centered { Busy("Loading") }

        is StartupPhase.Validating -> Centered {
            Busy("Checking ${phase.folder}")
        }

        StartupPhase.NeedsInstallation -> Centered {
            Text("No Dark Souls installation is setup yet")
            ChooseFolderButton(store)
        }

        is StartupPhase.Rejected -> Centered {
            Text("That folder can't be used: ${phase.reason.describe()}")
            ChooseFolderButton(store)
            OutlinedButton(onClick = { store.dispatch(StartupMessage.RejectionDismissed) }) {
                Text("Cancel")
            }
        }

        StartupPhase.NeedsWorkspace -> WorkspacePicker(
            workspaces = state.workspaces,
            installations = state.installations,
            onOpen = { store.dispatch(StartupMessage.WorkspaceChosen(it)) },
            onCreate = { name, id -> store.dispatch(StartupMessage.WorkspaceCreationRequested(name, id)) },
            onAddInstallation = { chooseInstallationDirectory()?.let { store.dispatch(StartupMessage.FolderChosen(it)) } }
        )

        is StartupPhase.Ready -> Centered {
            Text("Workspace: ${phase.workspace.name}")
            Text("The work space shell goes here")
        }

        else -> {}
    }
}



// TODO: Make this reusable probably
@Composable
private fun Centered(content: @Composable () -> Unit) {
    Column(
        modifier = Modifier.fillMaxSize().padding(24.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.spacedBy(12.dp, Alignment.CenterVertically)
    ) {
        content()
    }
}


@Composable
private fun Busy(message: String) {
    CircularProgressIndicator()
    Text(message)
}

@Composable
private fun ChooseFolderButton(store: StartupStore) {
    Button(onClick = {
        chooseInstallationDirectory()?.let { store.dispatch(StartupMessage.FolderChosen(it)) }
    }) {
        Text("Choose installation folder")
    }
}

private fun RejectionReason.describe(): String = when (this) {
    RejectionReason.NoExecutable -> "No Dark Souls executable was found"
    is RejectionReason.UnrecognizedExecutable -> "$name is not an executable this tool knows"
    is RejectionReason.MissingArchives -> "these files are missing: ${names.joinToString()}"
    is RejectionReason.EditionNotSupportedYet -> "${edition.name} is not supported yet"
}