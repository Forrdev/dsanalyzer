package com.sappyoak.dsanalyzer.app.ui.screens.firstrun

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.widthIn
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.unit.dp

import com.sappyoak.dsanalyzer.app.state.global.FirstRunSequence
import com.sappyoak.dsanalyzer.app.ui.Theme

@Composable
fun WorkspaceNaming(
    sequence: FirstRunSequence,
    onCreate: (String) -> Unit
) {
    var title by remember {
        mutableStateOf(sequence.identity?.displayName ?: "My Workspace")
    }

    Column(verticalArrangement = Arrangement.spacedBy(Theme.spacing.gap)) {
        Card(Modifier.widthIn(max = 420.dp)) {
            BasicTextField(
                value = title,
                onValueChange = { title = it },
                singleLine = true,
                textStyle = TextStyle(
                    color = Theme.colors.surface,
                    fontSize = MaterialTheme.typography.bodyLarge.fontSize
                ),
                modifier = Modifier.padding(Theme.spacing.cardPadding).fillMaxWidth()
            )
        }

        sequence.identity?.let {
            Row(verticalAlignment = Alignment.CenterVertically) {
                Text(
                    text = "Points at",
                    style = MaterialTheme.typography.labelSmall,
                    color = Theme.colors.secondaryFocus,
                    modifier = Modifier.width(70.dp)
                )

                Text(
                    text = "${it.displayName} - ${it.buildId}",
                    style = MaterialTheme.typography.bodySmall,
                    color = Theme.colors.normalText
                )
            }
        }

        Button(onClick = { onCreate(title.ifBlank { "My Workspace" }) }) {
            Text("Create and scan")
        }
    }
}