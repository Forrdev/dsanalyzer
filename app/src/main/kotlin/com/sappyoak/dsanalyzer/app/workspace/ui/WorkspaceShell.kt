package com.sappyoak.dsanalyzer.app.workspace.ui

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.sappyoak.dsanalyzer.app.workspace.Workspace
import com.sappyoak.dsanalyzer.game.Installation

@Composable
public fun WorkspaceShell(
    workspace: Workspace,
    installation: Installation?
) {
    Column(modifier = Modifier.fillMaxSize()) {
        Header(workspace, installation)
        HorizontalDivider()

        Box(
            modifier = Modifier.fillMaxSize(),
            contentAlignment = Alignment.Center
        ) {
            Text("Nothing to show yet", style = MaterialTheme.typography.bodyMedium)
        }
    }
}

@Composable
private fun Header(workspace: Workspace, installation: Installation?) {
    Column(
        modifier = Modifier.fillMaxWidth().padding(horizontal = 16.dp, vertical = 12.dp),
        verticalArrangement = Arrangement.spacedBy(2.dp)
    ) {
        Text(workspace.name, style = MaterialTheme.typography.titleMedium)
        Text(
            text = installation?.let { "${it.build.edition.name} - ${it.root}" } ?: "Installation Unavailable",
            style = MaterialTheme.typography.bodySmall
        )
    }
}