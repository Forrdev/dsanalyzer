package com.sappyoak.dsanalyzer.app.startup.view

import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.runtime.*
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
    var name by remember { mutableStateOf("") }
    var selected by remember(installations) { mutableStateOf(installations.firstOrNull()?.id) }

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

        OutlinedTextField(
            value = name,
            onValueChange = { name = it },
            label = { Text("name") },
            singleLine = true,
            modifier = Modifier.fillMaxWidth()
        )

        InstallationChoice(
            installations = installations,
            selected = selected,
            onSelect = { selected = it }
        )

        Button(
            onClick = { selected?.let { onCreate(name.trim(), it) } },
            enabled = name.isNotBlank() && selected != null
        ) {
            Text("Created")
        }

        HorizontalDivider()

        OutlinedButton(onClick = onAddInstallation) {
            Text("Add Another Installation")
        }
    }
}

@Composable
private fun InstallationChoice(
    installations: List<Installation>,
    selected: InstallationId?,
    onSelect: (InstallationId) -> Unit
) {
    if (installations.size <= 1) {
        installations.firstOrNull()?.let { Text("Installation: ${it.root}") }
        return
    }

    Text("Installation")
    installations.forEach { installation ->
        val marker = if (installation.id == selected) "\u25CF" else "\u25CB"
        OutlinedButton(
            onClick = { onSelect(installation.id) },
            modifier = Modifier.fillMaxWidth()
        ) {
            Text("$marker ${installation.root}")
        }
    }
}