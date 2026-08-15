package com.sappyoak.dsanalyzer.app.ui.screens

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.sappyoak.dsanalyzer.app.state.AppActions
import com.sappyoak.dsanalyzer.app.state.SetupState

import com.sappyoak.dsanalyzer.app.state.readiness.*
import com.sappyoak.dsanalyzer.app.ui.Theme
import java.nio.file.Path

@Composable
fun ReadinessScreen(
    readiness: Readiness.Blocked,
    setup: SetupState,
    actions: AppActions
) {
    val blocker = readiness.primary

    Column(
        Modifier.fillMaxSize().padding(24.dp).verticalScroll(rememberScrollState()),
        verticalArrangement = Arrangement.spacedBy(12.dp)
    ) {
        Text("DSAnalyzer", style = MaterialTheme.typography.headlineSmall)
        Card(
            Modifier.fillMaxWidth(),
            colors = CardDefaults.cardColors(
                containerColor = if (blocker.kind == Blocker.Kind.NoInstallation) {
                    MaterialTheme.colorScheme.surfaceVariant
                } else {
                    Theme.colors.warning
                }
            )
        ) {
            Column(Modifier.padding(16.dp)) {
                Text(
                    text = blocker.summary,
                    style = MaterialTheme.typography.titleMedium,
                    color = if (blocker.kind == Blocker.Kind.NoInstallation) {
                        MaterialTheme.colorScheme.onSurface
                    } else {
                        Color.White
                    }
                )
                Spacer(Modifier.padding(2.dp))
                Text(
                    text = blocker.consequences,
                    style = MaterialTheme.typography.bodyMedium,
                    color = if (blocker.kind == Blocker.Kind.NoInstallation) {
                        Theme.colors.secondaryFocus
                    } else {
                        Color.White
                    }
                )
            }
        }

        Resolutions(blocker, actions)

        if (blocker.kind == Blocker.Kind.NoInstallation && setup.suggestedPaths.isNotEmpty()) {
            Card(Modifier.fillMaxWidth()) {
                Column(Modifier.padding(12.dp)) {
                    Text("Found installations", style = MaterialTheme.typography.labelLarge)
                    setup.suggestedPaths.forEach { path ->
                        Text(
                            text = path,
                            style = MaterialTheme.typography.bodySmall,
                            modifier = Modifier
                                .padding(vertical = 3.dp)
                                .clickable { actions.useGamePath(Path.of(path)) }
                        )
                    }
                }
            }
        }

        if (readiness.blockers.size > 1) {
            Text(
                text = "${readiness.blockers.size - 1} other issue(s) to resolve after this",
                style = MaterialTheme.typography.labelSmall,
                color = Theme.colors.secondaryFocus
            )
        }

        if (blocker.kind != Blocker.Kind.DataDirectoryUnwritable) {
            Spacer(Modifier.padding(4.dp))
            Text(
                text = "Tool data: ${setup.dataPath ?: "not set"}",
                style = MaterialTheme.typography.labelSmall,
                color = Theme.colors.secondaryFocus
            )
            TextButton(onClick = actions::chooseDataPath) {
                Text("Change", style = MaterialTheme.typography.labelSmall)
            }
        }
    }
}

@Composable
private fun Resolutions(blocker: Blocker, actions: AppActions) {
    val primary = blocker.resolutions.filter { it.isPrimary }
    val secondary = blocker.resolutions.filterNot { it.isPrimary }

    Row(horizontalArrangement = Arrangement.spacedBy(Theme.spacing.gap)) {
        primary.forEach { resolution ->
            Button(onClick = { actions.dispatch(resolution.action) }) {
                Text(resolution.label)
            }
        }
    }

    if (secondary.isNotEmpty()) {
        Row(horizontalArrangement = Arrangement.spacedBy(Theme.spacing.gap)) {
            secondary.forEach { resolution ->
                TextButton(onClick = { actions.dispatch(resolution.action) }) {
                    Text(resolution.label)
                }
            }
        }
    }
}