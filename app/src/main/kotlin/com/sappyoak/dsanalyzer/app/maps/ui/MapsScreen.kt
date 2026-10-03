package com.sappyoak.dsanalyzer.app.maps.ui

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
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.VerticalDivider
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

import com.sappyoak.dsanalyzer.app.maps.MapsMessage
import com.sappyoak.dsanalyzer.app.maps.MapsState
import com.sappyoak.dsanalyzer.app.maps.MapsStore
import com.sappyoak.dsanalyzer.game.world.WorldRef
import com.sappyoak.dsanalyzer.game.world.maps.MapId
import com.sappyoak.dsanalyzer.game.world.maps.label

private val MAP_LIST_WIDTH = 200.dp
private val ENTRY_LIST_WIDTH = 320.dp

@Composable
public fun MapsScreen(state: MapsState, store: MapsStore) {
    Row(modifier = Modifier.fillMaxSize()) {
        MapList(
            maps = state.maps,
            selected = state.selected,
            onSelect = { store.dispatch(MapsMessage.Navigated(WorldRef.Map(it))) }
        )
        VerticalDivider()

        Column(modifier = Modifier.width(ENTRY_LIST_WIDTH)) {
            EntryFilters(state, store)
            HorizontalDivider()
            EntryList(
                entries = state.visible,
                focused = state.focused,
                loading = state.loading,
                onSelect = { store.dispatch(MapsMessage.Navigated(it)) }
            )
        }
        VerticalDivider()

        InspectorPane(state, store)
    }
}
@Composable
private fun MapList(
    maps: List<MapId>,
    selected: MapId?,
    onSelect: (MapId) -> Unit
) {
    Column(modifier = Modifier.width(MAP_LIST_WIDTH)) {
        Text(
            text = "Maps",
            style = MaterialTheme.typography.titleSmall,
            modifier = Modifier.fillMaxWidth().padding(horizontal = 12.dp, vertical = 8.dp)
        )
        HorizontalDivider()

        LazyColumn(modifier = Modifier.fillMaxSize(), verticalArrangement = Arrangement.spacedBy(2.dp)) {
            items(maps) { map ->
                TextButton(onClick = { onSelect(map) }, modifier = Modifier.fillMaxWidth()) {
                    Column(
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        Text(
                            text = map.label,
                            style = MaterialTheme.typography.bodyMedium,
                            color = if (map == selected) {
                                MaterialTheme.colorScheme.primary
                            } else {
                                MaterialTheme.colorScheme.onSurface
                            }
                        )

                        Text(
                            text = map.name,
                            style = MaterialTheme.typography.bodySmall,
                            color = MaterialTheme.colorScheme.onSurfaceVariant
                        )
                    }
                }
            }
        }
    }
}