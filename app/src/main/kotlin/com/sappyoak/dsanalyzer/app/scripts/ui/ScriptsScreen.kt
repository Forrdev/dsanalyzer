package com.sappyoak.dsanalyzer.app.scripts.ui

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.VerticalDivider
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

import com.sappyoak.dsanalyzer.app.scripts.EventSummary
import com.sappyoak.dsanalyzer.app.scripts.ScriptsMessage
import com.sappyoak.dsanalyzer.app.scripts.ScriptsState
import com.sappyoak.dsanalyzer.app.scripts.ScriptsStore
import com.sappyoak.dsanalyzer.game.world.WorldRef
import com.sappyoak.dsanalyzer.game.world.scripts.ScriptId
import com.sappyoak.dsanalyzer.game.world.scripts.title

private val SCRIPT_LIST_WIDTH = 200.dp
private val EVENT_LIST_WIDTH = 280.dp

@Composable
public fun ScriptsScreen(
    state: ScriptsState,
    store: ScriptsStore,
    onFollow: (WorldRef) -> Unit
) {
    Row(modifier = Modifier.fillMaxSize()) {
        ScriptList(
            scripts = state.scripts,
            selected = state.selected,
            onSelect = { store.dispatch(ScriptsMessage.ScriptSelected(it)) }
        )
        VerticalDivider()

        Column(modifier = Modifier.width(EVENT_LIST_WIDTH)) {
            OutlinedTextField(
                value = state.query,
                onValueChange = { store.dispatch(ScriptsMessage.QueryChanged(it)) },
                label = { Text("Event id or name") },
                singleLine = true,
                modifier = Modifier.fillMaxWidth().padding(8.dp)
            )
            HorizontalDivider()
            state.findings?.let { findings ->
                FlagReferences(findings) { reference ->
                    store.dispatch(
                        ScriptsMessage.Navigated(WorldRef.ScriptEvent(reference.script, reference.eventId))
                    )
                }
                HorizontalDivider()
            }
            EventList(
                events = state.visible,
                focused = state.focused,
                loading = state.loading,
                onSelect = { store.dispatch(ScriptsMessage.Navigated(it)) }
            )
        }
        VerticalDivider()

        InstructionPane(state, onFollow)
    }
}

@Composable
private fun ScriptList(scripts: List<ScriptId>, selected: ScriptId?, onSelect: (ScriptId) -> Unit) {
    Column(modifier = Modifier.width(SCRIPT_LIST_WIDTH)) {
        Text(
            text = "Scripts",
            style = MaterialTheme.typography.titleSmall,
            modifier = Modifier.fillMaxWidth().padding(horizontal = 12.dp, vertical = 8.dp)
        )
        HorizontalDivider()

        LazyColumn(modifier = Modifier.fillMaxSize(), verticalArrangement = Arrangement.spacedBy(2.dp)) {
            items(scripts) { script ->
                TextButton(onClick = { onSelect(script) }, modifier = Modifier.fillMaxWidth()) {
                    Text(
                        text = script.title,
                        style = MaterialTheme.typography.bodyMedium,
                        color = if (script == selected) {
                            MaterialTheme.colorScheme.primary
                        } else {
                            MaterialTheme.colorScheme.onSurface
                        }
                    )
                }
            }
        }
    }
}

@Composable
private fun EventList(
    events: List<EventSummary>,
    focused: Long?,
    loading: Boolean,
    onSelect: (WorldRef.ScriptEvent) -> Unit
) {
    if (loading) {
        Text("Loading", style = MaterialTheme.typography.bodySmall, modifier = Modifier.padding(12.dp))
        return
    }

    LazyColumn(modifier = Modifier.fillMaxSize()) {
        items(events, key = { it.id }) { event ->
            Surface(
                onClick = { onSelect(event.ref) },
                color = if (event.id == focused) {
                    MaterialTheme.colorScheme.surfaceVariant
                } else {
                    MaterialTheme.colorScheme.surface
                },
                modifier = Modifier.fillMaxWidth()
            ) {
                Column(modifier = Modifier.padding(horizontal = 12.dp, vertical = 6.dp)) {
                    Text(event.name ?: "Event ${event.id}", style = MaterialTheme.typography.bodyMedium)
                    Text(
                        text = "${event.id} - ${event.instructionCount} instructions",
                        style = MaterialTheme.typography.bodySmall,
                        color = MaterialTheme.colorScheme.onSurfaceVariant
                    )
                }
            }
        }
    }
}