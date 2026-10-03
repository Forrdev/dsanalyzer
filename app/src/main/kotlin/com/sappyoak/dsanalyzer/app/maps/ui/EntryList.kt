package com.sappyoak.dsanalyzer.app.maps.ui

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.FlowRow
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.FilterChip
import androidx.compose.material3.LinearProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

import com.sappyoak.dsanalyzer.app.maps.EntrySummary
import com.sappyoak.dsanalyzer.app.maps.MapsMessage
import com.sappyoak.dsanalyzer.app.maps.MapsState
import com.sappyoak.dsanalyzer.app.maps.MapsStore
import com.sappyoak.dsanalyzer.game.world.EntryKind
import com.sappyoak.dsanalyzer.game.world.WorldRef

@Composable
internal fun EntryFilters(state: MapsState, store: MapsStore) {
    Column(
        modifier = Modifier.fillMaxWidth().padding(8.dp),
        verticalArrangement = Arrangement.spacedBy(6.dp)
    ) {
        OutlinedTextField(
            value = state.query,
            onValueChange = { store.dispatch(MapsMessage.QueryChanged(it)) },
            label = { Text("Name or entity id") },
            singleLine = true,
            modifier = Modifier.fillMaxWidth()
        )

        FlowRow(horizontalArrangement = Arrangement.spacedBy(6.dp)) {
            EntryKind.entries.forEach { kind ->
                FilterChip(
                    selected = kind in state.kinds,
                    onClick = { store.dispatch(MapsMessage.KindToggled(kind)) },
                    label = { Text(kind.name) }
                )
            }
        }
    }
}

@Composable
internal fun EntryList(
    entries: List<EntrySummary>,
    focused: WorldRef.Entry?,
    loading: Boolean,
    onSelect: (WorldRef.Entry) -> Unit
) {
    if (loading) {
        LinearProgressIndicator(modifier = Modifier.fillMaxWidth())
    }

    LazyColumn(modifier = Modifier.fillMaxSize()) {
        items(entries, key = { it.ref }) { entry ->
            EntryRow(entry, selected = entry.ref == focused, onSelect = { onSelect(entry.ref) })
        }
    }
}

@Composable
private fun EntryRow(
    entry: EntrySummary,
    selected: Boolean,
    onSelect: () -> Unit
) {
    Surface(
        onClick = onSelect,
        color = if (selected) MaterialTheme.colorScheme.surfaceVariant else MaterialTheme.colorScheme.surface,
        modifier = Modifier.fillMaxWidth()
    ) {
        Column(modifier = Modifier.padding(horizontal = 12.dp, vertical = 6.dp)) {
            Text(entry.name, style = MaterialTheme.typography.bodyMedium)
            Text(
                text = entry.entityId?.let { "${entry.subtype} - entity $it" } ?: entry.subtype,
                style = MaterialTheme.typography.bodySmall,
                color = MaterialTheme.colorScheme.onSurfaceVariant
            )
        }
    }
}