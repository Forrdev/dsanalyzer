package com.sappyoak.dsanalyzer.app.scripts.ui

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

import com.sappyoak.dsanalyzer.app.scripts.ArgumentLine
import com.sappyoak.dsanalyzer.app.scripts.ScriptsState
import com.sappyoak.dsanalyzer.app.scripts.argumentLines
import com.sappyoak.dsanalyzer.app.scripts.title
import com.sappyoak.dsanalyzer.formats.emevd.emedf.DecodedInstruction
import com.sappyoak.dsanalyzer.formats.emevd.emedf.Emedf
import com.sappyoak.dsanalyzer.game.world.WorldRef

private val ARG_LABEL_WIDTH = 220.dp

@Composable
internal fun InstructionPane(state: ScriptsState, onFollow: (WorldRef) -> Unit) {
    val contents = state.contents
    val event = state.focusedEvent

    Column(modifier = Modifier.fillMaxSize()) {
        state.problem?.let { problem ->
            Text(
                text = problem,
                style = MaterialTheme.typography.bodySmall,
                color = MaterialTheme.colorScheme.error,
                modifier = Modifier.padding(12.dp)
            )
        }

        if (contents == null || event == null) {
            Text(
                text = "Select an event",
                style = MaterialTheme.typography.bodyMedium,
                modifier = Modifier.padding(16.dp)
            )
            return@Column
        }

        Column(modifier = Modifier.fillMaxWidth().padding(horizontal = 16.dp, vertical = 8.dp)) {
            Text(event.name ?: "Event ${event.id}", style = MaterialTheme.typography.titleSmall)
            Text(
                text = "${contents.script.label} - event ${event.id}",
                style = MaterialTheme.typography.bodySmall,
                color = MaterialTheme.colorScheme.onSurfaceVariant
            )
        }

        HorizontalDivider()

        LazyColumn(modifier = Modifier.fillMaxSize().padding(horizontal = 16.dp)) {
            itemsIndexed(contents.instructions(event.id)) { index, instruction ->
                Instruction(index, instruction, contents.definitions, onFollow)
            }
        }
    }
}

@Composable
private fun Instruction(
    index: Int,
    instruction: DecodedInstruction,
    definitions: Emedf,
    onFollow: (WorldRef) -> Unit
) {
    Column(modifier = Modifier.fillMaxWidth().padding(vertical = 6.dp)) {
        Text(
            text = "$index ${instruction.title()}",
            style = MaterialTheme.typography.bodyMedium,
            color = if (instruction.definition == null) {
                MaterialTheme.colorScheme.onSurfaceVariant
            } else {
                MaterialTheme.colorScheme.onSurface
            }
        )
        instruction.argumentLines(definitions).forEach { Argument(it, onFollow) }
    }
}

@Composable
private fun Argument(line: ArgumentLine, onFollow: (WorldRef) -> Unit) {
    Row(
        modifier = Modifier.fillMaxWidth().padding(start = 24.dp),
        horizontalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        Text(
            text = line.label,
            style = MaterialTheme.typography.bodySmall,
            color = MaterialTheme.colorScheme.onSurfaceVariant,
            modifier = Modifier.width(ARG_LABEL_WIDTH)
        )

        val link = line.link
        val lineText = Text(line.value, style = MaterialTheme.typography.bodySmall)

        if (link == null) {
            lineText
        } else {
            TextButton(onClick = { onFollow(link) }, contentPadding = PaddingValues(0.dp)) {
                lineText
            }
        }
    }
}