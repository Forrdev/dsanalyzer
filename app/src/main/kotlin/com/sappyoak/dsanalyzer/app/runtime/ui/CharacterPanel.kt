package com.sappyoak.dsanalyzer.app.runtime.ui

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

import com.sappyoak.dsanalyzer.runtime.session.BlockRoster
import com.sappyoak.dsanalyzer.runtime.session.CharacterSnapshot
import com.sappyoak.dsanalyzer.runtime.session.PlacedEnemy

@Composable
internal fun CharacterPanel(
    characters: List<CharacterSnapshot>,
    placed: List<BlockRoster>?,
    onReadPlaced: () -> Unit
) {
    Column(
        modifier = Modifier.fillMaxWidth().padding(horizontal = 16.dp, vertical = 8.dp),
        verticalArrangement = Arrangement.spacedBy(2.dp)
    ) {
        val others = characters.filterNot { it.isPlayer }

        Text("Instantiated characters (${others.size})", style = MaterialTheme.typography.titleSmall)
        if (others.isEmpty()) {
            Text("Nothing but the player", style = MaterialTheme.typography.bodySmall)
        }
        others.forEach { Field(it.modelName, it.describe()) }

        Row(verticalAlignment = Alignment.CenterVertically) {
            Text("Placed enemies", style = MaterialTheme.typography.titleSmall)
            TextButton(onClick = onReadPlaced) {
                Text(if (placed == null) "Read" else "Re-read")
            }
        }

        when {
            placed == null -> Text(
                text = "Not read. This walks every part of every loaded block, so it is on request",
                style = MaterialTheme.typography.bodySmall,
                color = MaterialTheme.colorScheme.onSurfaceVariant
            )
            placed.isEmpty() -> Text("No block is loaded", style = MaterialTheme.typography.bodySmall)
            else -> placed.forEach { roster ->
                Text(
                    text = "Block ${roster.blockIndex} - ${roster.enemies.size} enemies " +
                    "of ${roster.partCount} parts",
                    style = MaterialTheme.typography.bodySmall,
                    color = MaterialTheme.colorScheme.onSurfaceVariant
                )
                roster.enemies.forEach { Field(it.name, it.describe()) }
            }
        }
    }
}

private fun CharacterSnapshot.describe(): String {
    val target = if (alive) targetHandle?.let { " - targeting %08X".format(it) }.orEmpty() else ""
    val dead = if (alive) "" else " - dead"
    return "$health/$healthMax hp, npc $npcParamId, ${position.describe()}$dead$target"
}

private fun PlacedEnemy.describe(): String =
    "entity $entityId, npc $npcParamId, think $thinkParamId, ${position.describe()}"