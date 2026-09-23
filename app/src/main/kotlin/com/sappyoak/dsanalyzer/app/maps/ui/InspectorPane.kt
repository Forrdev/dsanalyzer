package com.sappyoak.dsanalyzer.app.maps.ui

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

import com.sappyoak.dsanalyzer.app.maps.MapsMessage
import com.sappyoak.dsanalyzer.app.maps.MapsState
import com.sappyoak.dsanalyzer.app.maps.MapsStore
import com.sappyoak.dsanalyzer.app.maps.inspect.InspectorRow
import com.sappyoak.dsanalyzer.app.maps.inspect.InspectorSection
import com.sappyoak.dsanalyzer.app.maps.inspect.inspect
import com.sappyoak.dsanalyzer.game.world.WorldRef

private val LABEL_WIDTH = 160.dp

@Composable
internal fun InspectorPane(state: MapsState, store: MapsStore) {
    val contents = state.contents
    val focused = state.focused

    Column(modifier = Modifier.fillMaxSize()) {
        HistoryBar(state, store)
        HorizontalDivider()

        state.problem?.let { problem ->
            Text(
                text = problem,
                style = MaterialTheme.typography.bodySmall,
                color = MaterialTheme.colorScheme.error,
                modifier = Modifier.padding(12.dp)
            )
        }

        if (contents == null || focused == null) {
            Text(
                text = "Select an entry",
                style = MaterialTheme.typography.bodyMedium,
                modifier = Modifier.padding(16.dp)
            )
            return@Column
        }

        Column(
            modifier = Modifier.fillMaxSize().verticalScroll(rememberScrollState()).padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            contents.inspect(focused).forEach { section ->
                Section(section) { store.dispatch(MapsMessage.Navigated(it)) }
            }
        }
    }
}

@Composable
private fun HistoryBar(state: MapsState, store: MapsStore) {
    Row(
        modifier = Modifier.fillMaxWidth().padding(horizontal = 8.dp, vertical = 4.dp),
        horizontalArrangement = Arrangement.spacedBy(4.dp)
    ) {
        TextButton(onClick = { store.dispatch(MapsMessage.BackRequested) }, enabled = state.canGoBack) {
            Text("Back")
        }
        TextButton(onClick = { store.dispatch(MapsMessage.ForwardRequested) }, enabled = state.canGoForward) {
            Text("Forward")
        }
        state.focusedEntry?.let { entry ->
            Text(
                text = "${state.selected?.name.orEmpty()} / ${entry.name}",
                style = MaterialTheme.typography.bodySmall,
                modifier = Modifier.padding(top = 12.dp)
            )
        }
    }
}

@Composable
private fun Section(section: InspectorSection, onFollow: (WorldRef) -> Unit) {
    Column(verticalArrangement = Arrangement.spacedBy(2.dp)) {
        Text(section.title, style = MaterialTheme.typography.titleSmall)
        section.rows.forEach { row -> Field(row, onFollow) }
    }
}

@Composable
private fun Field(row: InspectorRow, onFollow: (WorldRef) -> Unit) {
    Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        Text(
            text = row.label,
            style = MaterialTheme.typography.bodySmall,
            color = MaterialTheme.colorScheme.onSurfaceVariant,
            modifier = Modifier.width(LABEL_WIDTH)
        )

        val link = row.link
        if (link == null) {
            Text(row.value, style = MaterialTheme.typography.bodySmall)
        } else {
            TextButton(onClick = { onFollow(link) }, contentPadding = PaddingValues(0.dp)) {
                Text(row.value, style = MaterialTheme.typography.bodySmall)
            }
        }
    }
}