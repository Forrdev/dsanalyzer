package com.sappyoak.dsanalyzer.app.settings.ui

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.selection.selectableGroup
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.RadioButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp


import com.sappyoak.dsanalyzer.app.runtime.AISettings
import com.sappyoak.dsanalyzer.app.runtime.AITracking
import com.sappyoak.dsanalyzer.app.runtime.EngineDrawSettings
import com.sappyoak.dsanalyzer.app.runtime.OverlayEnabled
import com.sappyoak.dsanalyzer.app.runtime.TrackAI
import com.sappyoak.dsanalyzer.app.settings.CavesOnLaunch
import com.sappyoak.dsanalyzer.app.settings.Settings
import com.sappyoak.dsanalyzer.app.settings.SettingsEdit
import com.sappyoak.dsanalyzer.app.settings.ToolSettings
import com.sappyoak.dsanalyzer.app.settings.UseTranslatedNames

@Composable
public fun SettingsScreen(settings: Settings, onEdit: (SettingsEdit) -> Unit) {
    Column(modifier = Modifier.fillMaxSize().verticalScroll(rememberScrollState())) {
        ToolSection(settings.tool, onEdit)
        SettingsSection("Overlay") {
            ToggleRow(
                label = "Draw the overlay",
                checked = settings.overlay.enabled,
                explain = "Off means no overlay window at all, rather than an empty one"
            ) { onEdit(OverlayEnabled(it)) }
        }
        HorizontalDivider()

        EngineDrawSection(settings.engineDraw, onEdit)
        HorizontalDivider()

        AISettingsSection(settings.runtime.ai, onEdit)
    }
}

@Composable
private fun ToolSection(settings: ToolSettings, onEdit: (SettingsEdit) -> Unit) {
    SettingsSection("Tool") {
        ToggleRow(
            label = "Create code caves on launch",
            checked = settings.cavesOnLaunch,
            explain = "Installs the tool's code caves as soon as it attaches. Off installs them when a " +
                    "feature first needs them. Every patch shows in the write strip and goes back with revert-all"
        ) { onEdit(CavesOnLaunch(it)) }
        ToggleRow(
            label = "Use translated names",
            checked = settings.translatedNames,
            explain = "Shows English for param row names the game only carries in Japanese. They are " +
                    "machine translations; a name marked ? is one the translations disagreed on"
        ) { onEdit(UseTranslatedNames(it)) }
    }
}

@Composable
internal fun AISettingsSection(ai: AISettings, onEdit: (SettingsEdit) -> Unit) {
    SettingsSection("Enemy AI") {
        Column(modifier = Modifier.selectableGroup()) {
            AITracking.entries.forEach { tracking ->
                Row(verticalAlignment = Alignment.CenterVertically) {
                    RadioButton(selected = ai.tracking == tracking, onClick = { onEdit(TrackAI(tracking)) })
                    Text(text = tracking.describe(), style = MaterialTheme.typography.bodySmall)
                }
            }
        }

        Text(
            text = "Read every tick, so each enemy tracked costs a few reads per tick. An enemy stays tracked " +
                    "for three seconds after it last targeted you, so it does not drop out between attacks",
            style = MaterialTheme.typography.bodySmall,
            color = MaterialTheme.colorScheme.onSurfaceVariant
        )
    }
}

private fun AITracking.describe(): String = when (this) {
    AITracking.Off -> "Off"
    AITracking.TargetingPlayer -> "Enemies targeting you"
    AITracking.All -> "Every enemy"
}
