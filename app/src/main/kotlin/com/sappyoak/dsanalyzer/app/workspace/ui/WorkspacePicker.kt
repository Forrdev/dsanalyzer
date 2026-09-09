package com.sappyoak.dsanalyzer.app.workspace.ui

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.sappyoak.dsanalyzer.app.workspace.Workspace
import com.sappyoak.dsanalyzer.app.workspace.WorkspaceId
import com.sappyoak.dsanalyzer.game.Installation
import com.sappyoak.dsanalyzer.game.InstallationId

@Composable
public fun WorkspacePicker(
    workspaces: List<Workspace>,
    installations: List<Installation>,
    onOpen: (WorkspaceId) -> Unit,
    onCreate: (String, InstallationId) -> Unit,
    onAddInstallation: () -> Unit
) {
    val form = rememberWorkspaceFormState(installations)

    Column(
        modifier = Modifier.fillMaxSize().padding(24.dp),
        verticalArrangement = Arrangement.spacedBy(12.dp)
    ) {
        if (workspaces.isNotEmpty()) {
            Text("Open a workspace")
            workspaces.forEach { workspace ->
                OutlinedButton(
                    onClick = { onOpen(workspace.id) },
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Text(workspace.name)
                }
            }
            HorizontalDivider()
        }

        Text("Create a workspace")

        WorkspaceFormFields(
            state = form,
            installations = installations,
            onAddInstallation = onAddInstallation
        )

        Button(
            onClick = { form.submit(onCreate) },
            enabled = form.canSubmit
        ) {
            Text("Create")
        }
    }
}