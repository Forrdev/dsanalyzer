package com.sappyoak.dsanalyzer.app.runtime.ui

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.lazy.LazyListScope
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.ui.Alignment
import com.sappyoak.dsanalyzer.app.runtime.listed
import com.sappyoak.dsanalyzer.app.runtime.planLine
import com.sappyoak.dsanalyzer.app.runtime.timing.TimingLibrary
import com.sappyoak.dsanalyzer.app.runtime.timing.countdownOf
import com.sappyoak.dsanalyzer.app.runtime.timing.describe

import com.sappyoak.dsanalyzer.runtime.session.BlockRoster
import com.sappyoak.dsanalyzer.runtime.session.CharacterSnapshot
import com.sappyoak.dsanalyzer.runtime.session.PlacedEnemy
import com.sappyoak.dsanalyzer.runtime.session.ai.AISnapshot
import com.sappyoak.dsanalyzer.runtime.session.ai.AITarget

private const val CHARACTER_ROW = "character"

internal fun LazyListScope.characterItems(
    characters: List<CharacterSnapshot>,
    timings: TimingLibrary,
    placed: List<BlockRoster>?,
    hideDead: Boolean,
    onReadPlaced: () -> Unit
) {
    val others = characters.listed(hideDead)
    val dead = others.count { !it.alive }

    item(key = "characters") {
        Text(
            text = "Instantiated characters (${others.size}${if (dead > 0) ", $dead dead" else ""})",
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
    ) { character ->
        Column {
            val windows = timings[character.modelId]
            val countdown = timings.countdownOf(character)?.let { " - ${it.describe()}" }.orEmpty()
            Field(character.modelName, character.describe() + countdown, ROW_PADDING)
            character.ai?.goals?.let { goals ->
                Field("  plan", goals.planLine { windows?.windowsOf(it).orEmpty()}, ROW_PADDING)
            }
        }
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
    val target = when (val aiTarget = ai?.target) {
        is AITarget.Character -> " - fighting %08X".format(aiTarget.handle)
        AITarget.Sound -> " - turned to a sound"
        AITarget.None -> ""
        null -> targetHandle?.let { " - fighting %08X".format(it) }.orEmpty()
    }
    val dead = if (alive) "" else " - dead"
    val thinking = ai?.describe().orEmpty()

    return "$health/$healthMax hp, npc $npcParamId, ${position.describe()}$dead$target$thinking"
}

private fun AISnapshot.describe(): String {
    val playing = playingAction?.let { " (playing $it)" }.orEmpty()
    return " - think $thinkId, action ${requestedAction ?: "none"}$playing"
}

private fun PlacedEnemy.describe(): String =
    "entity $entityId, npc $npcParamId, think $thinkParamId, ${position.describe()}"
