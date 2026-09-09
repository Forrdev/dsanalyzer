package com.sappyoak.dsanalyzer.app.workspace.ui

import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import com.sappyoak.dsanalyzer.game.Installation
import com.sappyoak.dsanalyzer.game.InstallationId

@Composable
public fun NewWorkspaceDialog(
    installations: List<Installation>,
    onCreate: (String, InstallationId) -> Unit,
    onAddInstallation: () -> Unit,
    onDismiss: () -> Unit
) {
    val form = rememberWorkspaceFormState(installations)

    AlertDialog(
        onDismissRequest = onDismiss,
        title = { Text("New Workspace") },
        text = {
            WorkspaceFormFields(
                state = form,
                installations = installations,
                onAddInstallation = onAddInstallation
            )
        },
        confirmButton = {
            TextButton(
                onClick = { form.submit(onCreate) },
                enabled = form.canSubmit
            ) {
                Text("Create")
            }
        },
        dismissButton = {
            TextButton(onClick = onDismiss) {
                Text("Cancel")
            }
        }
    )
}