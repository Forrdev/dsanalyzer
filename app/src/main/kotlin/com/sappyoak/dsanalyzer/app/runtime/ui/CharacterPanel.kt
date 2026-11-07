package com.sappyoak.dsanalyzer.app.runtime.ui

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyListScope
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
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
import com.sappyoak.dsanalyzer.runtime.session.effects.SpecialEffectSnapshot

private const val CHARACTER_ROW = "character"
private val DETAIL_INDENT = Modifier.padding(start = 32.dp, end = 16.dp)

internal class CharacterList(
    characters: List<CharacterSnapshot>,
    read: List<CharacterSnapshot>?,
    hideDead: Boolean
) {
    val shown: List<CharacterSnapshot> = characters.listed(hideDead)
    val effectsRead: Boolean = read != null
    private val effects: Map<Int, SpecialEffectSnapshot> =
        read.orEmpty().mapNotNull { character -> character.effects?.let { character.handle to it } }.toMap()

    fun effectsOf(character: CharacterSnapshot): SpecialEffectSnapshot? = character.effects ?: effects[character.handle]
}


internal fun LazyListScope.characterItems(
    list: CharacterList,
    timings: TimingLibrary,
    placed: List<BlockRoster>?,
    onReadPlaced: () -> Unit,
    onReadEffects: () -> Unit
) {
    val others = list.shown
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


    item(key = "character-effects") {
        Row(modifier = ROW_PADDING, verticalAlignment = Alignment.CenterVertically) {
            Text(
                text = if (list.effectsRead) "Effects as of the last read" else "Effects not read",
                style = MaterialTheme.typography.bodySmall,
                color = MaterialTheme.colorScheme.onSurfaceVariant
            )
            TextButton(onClick = onReadEffects) { Text(if (list.effectsRead) "Re-read" else "Read") }
        }
    }

    items(
        others,
        key = { "character-${it.handle}" },
        contentType = { CHARACTER_ROW }
    ) { character ->
        CharacterRow(character, list.effectsOf(character), timings)
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

@Composable
private fun CharacterRow(character: CharacterSnapshot, effects: SpecialEffectSnapshot?, timings: TimingLibrary) {
    var open by remember(character.handle) { mutableStateOf(false) }
    val windows = timings[character.modelId]
    val countdown = timings.countdownOf(character)?.let { " - ${it.describe()}" }.orEmpty()
    val effectCount = effects?.let { " - ${it.effects.size} effects" }.orEmpty()

    Column {
        Row(modifier = ROW_PADDING, verticalAlignment = Alignment.CenterVertically) {
            Field(character.modelName, character.describe() + countdown + effectCount, Modifier.weight(1f))
            TextButton(onClick = { open = !open }) { Text(if (open) "Less" else "More") }
        }
        character.ai?.goals?.let { goals ->
            Field("  plan", goals.planLine { windows?.windowsOf(it).orEmpty() }, ROW_PADDING)
        }
        if (open) {
            character.action?.let { ActionFields(it, DETAIL_INDENT) }
            effects?.let { EffectList(it, DETAIL_INDENT) }
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
