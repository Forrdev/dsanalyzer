package com.sappyoak.dsanalyzer.app.runtime.ui

import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.lazy.LazyListScope
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.ui.Alignment

import com.sappyoak.dsanalyzer.runtime.session.BlockRoster
import com.sappyoak.dsanalyzer.runtime.session.CharacterSnapshot
import com.sappyoak.dsanalyzer.runtime.session.PlacedEnemy

private const val CHARACTER_ROW = "character"

internal fun LazyListScope.characterItems(
    characters: List<CharacterSnapshot>,
    placed: List<BlockRoster>?,
    onReadPlaced: () -> Unit
) {
    val others = characters.filterNot { it.isPlayer }
    item(key = "characters") {
        Text(
            text = "Instantiated characters (${others.size})",
            style = MaterialTheme.typography.titleSmall,
            modifier = HEADER_PADDING
        )
    }

    if (others.isEmpty()) {
        item(key = "characters-none") {
            Text(
                text = "Nothing but the player",
                style = MaterialTheme.typography.bodySmall,
                modifier = ROW_PADDING
            )
        }
    }

    items(
        others,
        contentType = {CHARACTER_ROW }
    ) {
        Field(it.modelName, it.describe(), ROW_PADDING)
    }

    item(key = "placed") {
        Row(
            modifier = ROW_PADDING,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text("Placed enemies", style = MaterialTheme.typography.titleSmall)
            TextButton(onClick = onReadPlaced) {
                Text(if (placed == null) "Read" else "Re-read")
            }
        }
    }

    when {
        placed == null -> item(key = "placed-unread") {
            Text(
                text = "Not read",
                style = MaterialTheme.typography.bodySmall,
                color = MaterialTheme.colorScheme.onSurfaceVariant,
                modifier = ROW_PADDING
            )
        }
        placed.isEmpty() -> item(key = "placed-none") {
            Text(
                text = "No block is loaded",
                style = MaterialTheme.typography.bodySmall,
                modifier = ROW_PADDING
            )
        }
        else -> placed.forEach { roster ->
            item {
                Text(
                    text = "Block ${roster.blockIndex} - ${roster.enemies.size} enemies of ${roster.partCount} parts",
                    style = MaterialTheme.typography.bodySmall,
                    color = MaterialTheme.colorScheme.onSurfaceVariant,
                    modifier = ROW_PADDING
                )
            }
            items(roster.enemies, contentType = { CHARACTER_ROW }) {
                Field(it.name, it.describe(), ROW_PADDING)
            }
        }
    }
}

private fun CharacterSnapshot.describe(): String {
    val target = targetHandle?.let { "- fighting %08X".format(it) }.orEmpty()
    val dead = if (alive) "" else " - dead"
    return "$health/$healthMax hp, npc $npcParamId, ${position.describe()}$dead$target"
}

private fun PlacedEnemy.describe(): String =
    "entity $entityId, npc $npcParamId, think $thinkParamId, ${position.describe()}"
