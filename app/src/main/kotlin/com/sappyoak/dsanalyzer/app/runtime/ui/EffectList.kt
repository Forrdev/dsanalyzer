package com.sappyoak.dsanalyzer.app.runtime.ui

import androidx.compose.foundation.layout.Column
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier

import com.sappyoak.dsanalyzer.app.ui.theme.AnalyzerTheme
import com.sappyoak.dsanalyzer.runtime.session.effects.ActiveEffect
import com.sappyoak.dsanalyzer.runtime.session.effects.SpecialEffectSnapshot

/** A character's active effects, named where the names file knows them; the same list for the player and anyone else */
@Composable
internal fun EffectList(effects: SpecialEffectSnapshot, modifier: Modifier = ROW_PADDING) {
    Column(modifier = modifier) {
        if (effects.effects.isEmpty()) {
            Text("No active effects", style = MaterialTheme.typography.bodySmall, color = AnalyzerTheme.colors.textLo)
        }
        effects.effects.forEach { Field(it.title(), it.describe()) }

        if (effects.desynced) {
            Warning(
                "State counters disagree with the list: " +
                        effects.disagreements.joinToString { "bucket ${it.bucket} counts ${it.counted}, list has ${it.walked}" }
            )
        }
        if (effects.truncated) Warning("List cut short at the walk limit")
    }
}

@Composable
private fun Warning(text: String) {
    Text(text, style = MaterialTheme.typography.bodySmall, color = AnalyzerTheme.colors.failed)
}

private fun ActiveEffect.title(): String = "SpEffect $effectId"

private fun ActiveEffect.describe(): String = buildList {
    add("id $effectId")
    durationRemaining?.let { left ->
        add(durationTotal?.let { "%.1f / %.1f s".format(left, it) } ?: "%.1f s".format(left))
    }
    stateInfo?.let { add("state $it") }
    if (excluded) add("hidden but counted")
    if (liveOnlyMismatch) add("not live")
}.joinToString("  ·  ")
