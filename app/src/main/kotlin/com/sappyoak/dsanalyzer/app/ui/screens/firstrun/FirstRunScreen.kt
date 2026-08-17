package com.sappyoak.dsanalyzer.app.ui.screens.firstrun

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.widthIn
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

import com.sappyoak.dsanalyzer.app.state.global.*
import com.sappyoak.dsanalyzer.app.ui.Theme

@Composable
fun FirstRunScreen(
    sequence: FirstRunSequence,
    globalState: GlobalState,
    onChooseDataPath: () -> Unit,
    onChooseInstallation: () -> Unit,
    onExtract: () -> Unit,
    onSkipExtraction: () -> Unit,
    onCreateWorkspace: (String) -> Unit
) {
    val step = sequence.current ?: return

    Column(
        Modifier.fillMaxSize().padding(Theme.spacing.screenPadding).verticalScroll(rememberScrollState()),
        verticalArrangement = Arrangement.spacedBy(Theme.spacing.gapWide)
    ) {
        Column(Modifier.widthIn(max = 620.dp)) {
            Text(
                text = "Step ${sequence.position} of ${sequence.total}",
                style = MaterialTheme.typography.labelSmall,
                color = Theme.colors.secondaryFocus
            )
            Text(
                text = step.title,
                style = MaterialTheme.typography.titleLarge,
                color = Theme.colors.surface
            )
            Text(
                text = step.purpose,
                style = MaterialTheme.typography.bodyMedium,
                color = Theme.colors.normalText
            )
        }

        Spacer(Modifier.padding(Theme.spacing.gapTight))

        when (step) {
            FirstRunStep.DataPath -> Button(onClick = onChooseDataPath) {
                Text("Choose a location")
            }

            FirstRunStep.Installation -> Column(
                verticalArrangement = Arrangement.spacedBy(Theme.spacing.gap)
            ) {
                Button(onClick = onChooseInstallation) {
                    Text("Browse for the game")
                }

                if (globalState.installations.isNotEmpty()) {
                    Text(
                        text = "Found on this machine",
                        style = MaterialTheme.typography.labelLarge,
                        color = Theme.colors.normalText
                    )
                    // should make these clickable?
                    globalState.installations.forEach { entry ->
                        Text(
                            text = entry.path.toString(),
                            style = MaterialTheme.typography.bodySmall,
                            color = Theme.colors.accent
                        )
                    }
                }
            }

            FirstRunStep.Extraction -> Column(
                verticalArrangement = Arrangement.spacedBy(Theme.spacing.gap)
            ) {
                Card(Modifier.fillMaxWidth()) {
                    Column(Modifier.padding(Theme.spacing.cardPadding)) {
                        Text(
                            text = "This installation is packed",
                            style = MaterialTheme.typography.bodyMedium,
                            color = Theme.colors.surface
                        )
                    }

                    Row(horizontalArrangement = Arrangement.spacedBy(Theme.spacing.gap)) {
                        Button(onClick = onExtract) { Text("Extract now") }
                        TextButton(onClick = onSkipExtraction) { Text("Skip for now") }
                    }
                }
            }

            FirstRunStep.Workspace -> WorkspaceNaming(sequence, onCreateWorkspace)
        }
    }
}