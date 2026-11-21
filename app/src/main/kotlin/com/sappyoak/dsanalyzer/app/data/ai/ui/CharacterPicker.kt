package com.sappyoak.dsanalyzer.app.data.ai.ui

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.FlowRow
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

import com.sappyoak.dsanalyzer.app.ui.theme.AnalyzerTheme
import com.sappyoak.dsanalyzer.game.world.animations.AnimationSetId
import com.sappyoak.dsanalyzer.game.world.maps.loadCharacterModels

/** A character's model label with its name, when the model is known */
internal fun AnimationSetId.title(): String =
    loadCharacterModels().describe(label)?.let { "$label  $it" } ?: label

/**
 * Which character's animations the ids in a script open: a choice when several characters run the script,
 * a note when one does, and nothing when none does
 */
@Composable
internal fun CharacterPicker(
    characters: List<AnimationSetId>,
    selected: AnimationSetId?,
    onSelect: (AnimationSetId) -> Unit
) {
    if (characters.isEmpty()) return
    FlowRow(
        horizontalArrangement = Arrangement.spacedBy(8.dp),
        modifier = Modifier.padding(horizontal = 16.dp, vertical = 4.dp)
    ) {
        Text("Animations from", style = AnalyzerTheme.text.label, color = AnalyzerTheme.colors.textLo)
        characters.forEach { character ->
            val chosen = character == selected
            Text(
                text = character.title(),
                style = AnalyzerTheme.text.label,
                color = if (chosen) AnalyzerTheme.colors.textHi else AnalyzerTheme.colors.link,
                modifier = Modifier
                    .let { if (chosen) it.background(AnalyzerTheme.colors.raised) else it }
                    .clickable(enabled = characters.size > 1) { onSelect(character) }
                    .padding(horizontal = 6.dp, vertical = 2.dp)
            )
        }
    }
}
