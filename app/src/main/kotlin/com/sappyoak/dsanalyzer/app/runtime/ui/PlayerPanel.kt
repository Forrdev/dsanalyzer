package com.sappyoak.dsanalyzer.app.runtime.ui

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

import com.sappyoak.dsanalyzer.runtime.session.PlayerSnapshot
import com.sappyoak.dsanalyzer.runtime.session.RuntimeSnapshot

@Composable
internal fun PlayerPanel(snapshot: RuntimeSnapshot) {
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

        Section("Position")
        Field("Position", player.position.describe())
        Field("Angle", "%.3f".format(player.angle))
        snapshot.world?.let { world ->
            Field("Stable Position", world.stablePosition.describe())
            snapshot.divergence?.let { away ->
                Field("Away from it", "%.2f".format(away) + if (snapshot.offStableGround) " (off stable ground)" else "")
            }
            Field("Last bonfire", world.lastBonfire.toString())
            if (world.deathCam) Field("Death Cam", "running")
        }

        player.animationSpeed?.let { Field("Animation speed", it.toString()) }
        Field("Play region", player.playRegion.toString())
        snapshot.menu?.let { menu ->
            Field("Default Quantity", menu.defaultQuantity.toString())
        }

        Section("Vitals")
        Vitals(player)

        player.attributes?.let { CharacterAttributes(it) }
        player.character.action?.let {
            Section("Action state")
            ActionFields(it, Modifier)
        }

        player.character.effects?.let {
            Section("Effects (${it.effects.size})")
            EffectList(it, Modifier)
        }
    }
}
