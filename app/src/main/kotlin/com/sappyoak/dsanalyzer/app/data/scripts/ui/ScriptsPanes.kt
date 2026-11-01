package com.sappyoak.dsanalyzer.app.data.scripts.ui

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
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
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

import com.sappyoak.dsanalyzer.app.data.shownScript
import com.sappyoak.dsanalyzer.app.data.scripts.EventSummary
import com.sappyoak.dsanalyzer.app.data.scripts.ScriptsMessage
import com.sappyoak.dsanalyzer.app.data.scripts.ScriptsState
import com.sappyoak.dsanalyzer.app.data.scripts.ScriptsStore
import com.sappyoak.dsanalyzer.app.nav.Resolution
import com.sappyoak.dsanalyzer.app.nav.View
import com.sappyoak.dsanalyzer.app.ui.theme.AnalyzerTheme
import com.sappyoak.dsanalyzer.game.ref.Address

private val EVENT_LIST_WIDTH = 300.dp

@Composable
public fun ScriptsDetail(
    state: ScriptsState,
    store: ScriptsStore,
    view: View.Data,
    resolve: (Address) -> Resolution,
    onFollow: (View) -> Unit
) {
    if (view is View.Data.EventFlag) {
        FlagPane(state, view.flagId, onFollow)
        return
    }

    Row(modifier = Modifier.fillMaxSize()) {
        if (view.shownScript != null) {
            Column(
                modifier = Modifier
                    .width(EVENT_LIST_WIDTH)
                    .fillMaxHeight()
                    .background(AnalyzerTheme.colors.panel)
            ) {
                OutlinedTextField(
                    value = state.query,
                    onValueChange = { store.dispatch(ScriptsMessage.QueryChanged(it)) },
                    label = { Text("Event id or name") },
                    singleLine = true,
                    modifier = Modifier.fillMaxWidth().padding(8.dp)
                )
                HorizontalDivider(color = AnalyzerTheme.colors.border)
                EventList(
                    events = state.visible,
                    focused = (view as? View.Data.ScriptEvent)?.eventId,
                    loading = state.loading,
                    onSelect = onFollow
                )
            }

            Box(
                modifier = Modifier
                    .width(1.dp)
                    .fillMaxHeight()
                    .background(AnalyzerTheme.colors.border)
            )
        }

        when (view) {
            is View.Data.ScriptEvent -> InstructionPane(state, view.eventId, resolve, onFollow)
            else -> Placeholder(
                when {
                    state.loading -> "Reading the script"
                    view.shownScript != null -> "Select an event"
                    else -> "Select a script from the tree"
                }
            )
        }
    }
}

@Composable
private fun EventList(
    events: List<EventSummary>,
    focused: Long?,
    loading: Boolean,
    onSelect: (View.Data.ScriptEvent) -> Unit
) {
    if (loading) {
        Placeholder("Loading")
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
                    Text(
                        text = event.name ?: "Event ${event.id}",
                        style = MaterialTheme.typography.bodyMedium
                    )
                    Text(
                        text = "${event.id} - ${event.instructionCount} instructions",
                        style = MaterialTheme.typography.bodySmall,
                        color = AnalyzerTheme.colors.textLo
                    )
                }
            }
        }
    }
}

@Composable
private fun Placeholder(what: String) {
    Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
        Text(what, style = AnalyzerTheme.text.label, color = AnalyzerTheme.colors.textLo)
    }
}
