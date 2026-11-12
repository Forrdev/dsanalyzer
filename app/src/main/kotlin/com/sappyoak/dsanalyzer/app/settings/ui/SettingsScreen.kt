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
import com.sappyoak.dsanalyzer.app.runtime.CharacterDrawDistance
import com.sappyoak.dsanalyzer.app.runtime.CharacterDrawing
import com.sappyoak.dsanalyzer.app.runtime.DrawCharacters
import com.sappyoak.dsanalyzer.app.runtime.DrawHitWindows
import com.sappyoak.dsanalyzer.app.runtime.EngineDrawSettings
import com.sappyoak.dsanalyzer.app.runtime.HideDeadCharacters
import com.sappyoak.dsanalyzer.app.runtime.OverlaySettings
import com.sappyoak.dsanalyzer.app.runtime.RuntimeSettings
import com.sappyoak.dsanalyzer.app.runtime.OverlayEnabled
import com.sappyoak.dsanalyzer.app.runtime.TrackAI
import com.sappyoak.dsanalyzer.app.settings.CavesOnLaunch
import com.sappyoak.dsanalyzer.app.settings.Settings
import com.sappyoak.dsanalyzer.app.settings.SettingsEdit
import com.sappyoak.dsanalyzer.app.settings.ToolSettings

private val DRAW_DISTANCE = 10f..400f

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

        CharacterSettings(settings.overlay, settings.runtime, onEdit)
        HorizontalDivider()

        EngineDrawSection(settings.engineDraw, onEdit)
        HorizontalDivider()

        AISettingsSection(settings.runtime.ai, settings.overlay, onEdit)
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
    }
}

@Composable
private fun CharacterSettings(overlay: OverlaySettings, runtime: RuntimeSettings, onEdit: (SettingsEdit) -> Unit) {
    val settings = overlay.characters
    val on = overlay.enabled

    SettingsSection("Characters") {
        Column(modifier = Modifier.selectableGroup()) {
            CharacterDrawing.entries.forEach { mode ->
                Row(verticalAlignment = Alignment.CenterVertically) {
                    RadioButton(
                        selected = settings.draw == mode,
                        onClick = { onEdit(DrawCharacters(mode)) },
                        enabled = on
                    )
                    Text(text = mode.describe(), style = MaterialTheme.typography.bodySmall)
                }
            }
        }

        SliderRow(
            label = "Draw distance",
            value = settings.drawDistance,
            range = DRAW_DISTANCE,
            steps = sliderSteps(DRAW_DISTANCE, by = 10f),
            enabled = on && settings.draw != CharacterDrawing.None,
            format = { "${it.toInt()}m" }
        ) { onEdit(CharacterDrawDistance(it)) }

        Text(
            text = "Markers fade to their faintest at this distance, so it sets the depth cue as " +
                    "well as the cull",
            style = MaterialTheme.typography.bodySmall,
            color = MaterialTheme.colorScheme.onSurfaceVariant
        )

        ToggleRow(
            label = "Hide the dead",
            checked = runtime.hideDeadCharacters,
            explain = "Leaves dead characters out of the Runtime tab's list. off keeps them at the foot of the list. " +
            "The overlay never draws them"
        ) { onEdit(HideDeadCharacters(it)) }
    }
}

@Composable
internal fun AISettingsSection(
    ai: AISettings,
    overlay: OverlaySettings,
    onEdit: (SettingsEdit) -> Unit
) {
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

        ToggleRow(
            label = "Attack countdown",
            checked = overlay.characters.hitWindows,
            enabled = overlay.enabled && ai.tracking != AITracking.Off,
            explain = "A ring on each tracked enemy's marker that fills up to its next attack. Only enemies tracked above"
        ) { onEdit(DrawHitWindows(it)) }
    }
}

private fun CharacterDrawing.describe(): String = when (this) {
    CharacterDrawing.None -> "None"
    CharacterDrawing.Instantiated -> "Only the ones the game has built"
    CharacterDrawing.All -> "Those, and the placed enemies it has not built"
}

private fun AITracking.describe(): String = when (this) {
    AITracking.Off -> "Off"
    AITracking.TargetingPlayer -> "Enemies targeting you"
    AITracking.All -> "Every enemy"
}
