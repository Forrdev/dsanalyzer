package com.sappyoak.dsanalyzer.app.workspace.ui

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.sappyoak.dsanalyzer.game.Installation
import com.sappyoak.dsanalyzer.game.InstallationId

public class WorkspaceFormState(initialInstallation: InstallationId?) {
    public var name: String by mutableStateOf("")
    public var installationId: InstallationId? by mutableStateOf(initialInstallation)

    public val canSubmit: Boolean get() = name.isNotBlank() && installationId != null

    public fun submit(onCreate: (String, InstallationId) -> Unit) {
        installationId?.takeIf { name.isNotBlank() }?.let { onCreate(name.trim(), it) }
    }
}

@Composable
public fun rememberWorkspaceFormState(installations: List<Installation>): WorkspaceFormState =
    remember(installations) { WorkspaceFormState(installations.firstOrNull()?.id) }

@Composable
public fun WorkspaceFormFields(
    state: WorkspaceFormState,
    installations: List<Installation>,
    onAddInstallation: () -> Unit,
    modifier: Modifier = Modifier
) {
    Column(modifier = modifier, verticalArrangement = Arrangement.spacedBy(12.dp)) {
        OutlinedTextField(
            value = state.name,
            onValueChange = { state.name = it },
            label = { Text("Name") },
            singleLine = true,
            modifier = Modifier.fillMaxWidth()
        )

        InstallationChoice(
            installations = installations,
            selected = state.installationId,
            onSelect = { state.installationId = it }
        )

        OutlinedButton(onClick = onAddInstallation, modifier = Modifier.fillMaxWidth()) {
            Text("Add an installation")
        }
    }
}

@Composable
private fun InstallationChoice(
    installations: List<Installation>,
    selected: InstallationId?,
    onSelect: (InstallationId) -> Unit
) {
    if (installations.isEmpty()) {
        Text("No installations are setup yet")
        return
    }

    Text("Installation")
    installations.forEach { installation ->
        val marker = if (installation.id == selected) "\u25CF" else "\u25CB"
        OutlinedButton(
            onClick = { onSelect(installation.id) },
            modifier = Modifier.fillMaxWidth()
        ) {
            Text("$marker  ${installation.build.edition.name} - ${installation.root}")
        }
    }
}