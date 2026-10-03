package com.sappyoak.dsanalyzer.app.runtime.ui

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
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
import com.sappyoak.dsanalyzer.runtime.session.PlayerSnapshot
import com.sappyoak.dsanalyzer.runtime.session.RuntimeSnapshot
import com.sappyoak.dsanalyzer.shared.math.Vec3

private val LABEL_WIDTH = 160.dp

@Composable
public fun RuntimeScreen(state: RuntimeState, onFollow: (WorldRef) -> Unit) {
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

        Player(snapshot)
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

@Composable
private fun Player(snapshot: RuntimeSnapshot) {
    val player = snapshot.player

    Column(
        modifier = Modifier.fillMaxWidth().padding(horizontal = 16.dp, vertical = 8.dp),
        verticalArrangement = Arrangement.spacedBy(2.dp)
    ) {
        if (player == null) {
            Text("No character is loaded", style = MaterialTheme.typography.bodyMedium)
            return@Column
        }

        if (player.cheats.isNotEmpty()) {
            Text(
                text = "Running with ${player.cheats.joinToString()} - this is not the game as shipped",
                style = MaterialTheme.typography.bodySmall,
                color = MaterialTheme.colorScheme.error
            )
        }

        Field("Position", player.position.describe())
        snapshot.world?.let { world ->
            Field("Last stable ground", world.stablePosition.describe())
            snapshot.divergence?.let { away ->
                Field("Distance from it", "%.2f".format(away))
                if (world.deathCam) Field("Death cam", "running")
            }
        }
        Field("Health", player.describeHealth())
        Field("Stamina", player.stamina.toString())
        player.animationSpeed?.let { Field("Animation speed", it.toString()) }
        Field("Play region", player.playRegion.toString())
        player.attributes?.let { Field("Soul level", "${it.soulLevel} (${it.souls} souls)")}
    }
}

@Composable
private fun Field(label: String, value: String) {
    Row(modifier = Modifier.fillMaxWidth()) {
        Text(
            text = label,
            style = MaterialTheme.typography.bodySmall,
            color = MaterialTheme.colorScheme.onSurfaceVariant,
            modifier = Modifier.width(LABEL_WIDTH)
        )
        Text(text = value, style = MaterialTheme.typography.bodySmall)
    }
}

private fun Vec3.describe(): String = "%.2f, %.2f, %.2f".format(x, y, z)

private fun PlayerSnapshot.describeHealth(): String =
    attributes?.let { "$health / ${it.healthMax}" } ?: health.toString()