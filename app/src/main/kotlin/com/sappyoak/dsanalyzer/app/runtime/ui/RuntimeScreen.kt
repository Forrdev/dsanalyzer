package com.sappyoak.dsanalyzer.app.runtime.ui

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
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
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

import com.sappyoak.dsanalyzer.app.runtime.RuntimeState
import com.sappyoak.dsanalyzer.game.world.WorldRef


@Composable
public fun RuntimeScreen(
    state: RuntimeState,
    onFollow: (WorldRef) -> Unit,
    onReadPlacedEnemies: () -> Unit
) {
    Column(modifier = Modifier.fillMaxSize()) {
        Attachment(state, onFollow)
        HorizontalDivider()

        val snapshot = state.snapshot
        if (snapshot == null) {
            Text(
                text = state.problem ?: "Connect to a running game",
                style = MaterialTheme.typography.bodyMedium,
                modifier = Modifier.padding(16.dp)
            )
            return@Column
        }

        PlayerPanel(snapshot)
        HorizontalDivider()
        CameraPanel(snapshot.camera)
        HorizontalDivider()
        LoadQueuePanel(snapshot.loadQueue)
        HorizontalDivider()
        CharacterPanel(snapshot.characters, state.placedEnemies, onReadPlacedEnemies)
        HorizontalDivider()
        FlagLog(state.flagLog, onFollow)
    }
}

@Composable
private fun Attachment(state: RuntimeState, onFollow: (WorldRef) -> Unit) {
    val attached = state.attached

    Column(
        modifier = Modifier.fillMaxWidth().padding(horizontal = 16.dp, vertical = 12.dp),
        verticalArrangement = Arrangement.spacedBy(2.dp)
    ) {
        Text(
            text = attached?.let { "${it.executableName} (${it.pid})" } ?: "Not attached",
            style = MaterialTheme.typography.titleMedium
        )

        attached?.let {
            Text(
                text = it.build?.name ?: "unrecognized build, so the offsets might not apply",
                style = MaterialTheme.typography.bodySmall
            )
        }

        state.problem?.let {
            Text(
                text = it,
                style = MaterialTheme.typography.bodySmall,
                color = MaterialTheme.colorScheme.error
            )
        }

        attached?.unresolved?.takeIf { it.isNotEmpty() }?.let { missing ->
            Text(
                text = "Not found in this build: ${missing.joinToString()}",
                style = MaterialTheme.typography.bodySmall,
                color = MaterialTheme.colorScheme.error
            )
        }

        state.snapshot?.let { snapshot ->
            Row(verticalAlignment = Alignment.CenterVertically) {
                Text(
                    text = "frame ${snapshot.frame} - ${snapshot.cost.present}/${snapshot.cost.expected} read in ${snapshot.cost.elapsed}",
                    style = MaterialTheme.typography.bodySmall
                )
                val map = snapshot.map
                if (map != null) {
                    TextButton(onClick = { onFollow(WorldRef.Map(map)) }) {
                        Text(map.name)
                    }
                } else {
                    Text(
                        text = snapshot.place.label,
                        style = MaterialTheme.typography.bodySmall,
                        modifier = Modifier.padding(start = 8.dp)
                    )
                }
            }
        }
    }
}
