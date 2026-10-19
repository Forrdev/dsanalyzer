package com.sappyoak.dsanalyzer.app.settings.ui

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.selection.selectableGroup
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Checkbox
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.RadioButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

import com.sappyoak.dsanalyzer.app.runtime.CharacterDrawDistance
import com.sappyoak.dsanalyzer.app.runtime.CharacterDrawing
import com.sappyoak.dsanalyzer.app.runtime.CollisionCull
import com.sappyoak.dsanalyzer.app.runtime.CollisionDepthCue
import com.sappyoak.dsanalyzer.app.runtime.CollisionOpacity
import com.sappyoak.dsanalyzer.app.runtime.CollisionRadius
import com.sappyoak.dsanalyzer.app.runtime.CollisionTriangleBudget
import com.sappyoak.dsanalyzer.app.runtime.DrawCharacters
import com.sappyoak.dsanalyzer.app.runtime.DrawCollision
import com.sappyoak.dsanalyzer.app.runtime.DrawPlayer
import com.sappyoak.dsanalyzer.app.runtime.OccludeMarkers
import com.sappyoak.dsanalyzer.app.runtime.OverlayEnabled
import com.sappyoak.dsanalyzer.app.runtime.OverlaySettings
import com.sappyoak.dsanalyzer.app.settings.SettingsEdit
import com.sappyoak.dsanalyzer.overlay.FaceCull

private val DRAW_DISTANCE = 10f..400f
private val COLLISION_RADIUS = 5f..200f
private val TRIANGLE_BUDGET = 500f..20_000f
private val OPACITY = 0.1f..1f

@Composable
public fun SettingsScreen(overlay: OverlaySettings, onEdit: (SettingsEdit) -> Unit) {
    Column(modifier = Modifier.fillMaxSize().verticalScroll(rememberScrollState())) {
        SettingsSection("Overlay") {
            ToggleRow(
                label = "Draw the overlay",
                checked = overlay.enabled,
                explain = "Off means no overlay window at all, rather than an empty one"
            ) { onEdit(OverlayEnabled(it)) }
        }
        HorizontalDivider()

        CharacterSettings(overlay, onEdit)
        HorizontalDivider()

        CollisionSettings(overlay, onEdit)
    }
}

@Composable
private fun CharacterSettings(overlay: OverlaySettings, onEdit: (SettingsEdit) -> Unit) {
    val settings = overlay.characters
    val on = overlay.enabled

    SettingsSection("Characters") {
        ToggleRow(
            label = "Your own marker",
            checked = settings.drawPlayer,
            enabled = on
        ) { onEdit(DrawPlayer(it)) }

        Text(
            text = "Everyone else",
            style = MaterialTheme.typography.bodyMedium,
            modifier = Modifier.padding(top = 6.dp, bottom = 2.dp)
        )

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
            label = "Hide behind collision",
            checked = settings.occludeByCollision,
            enabled = on,
            explain = "Drops a marker only when collision covers it completely. Reads collision even if the wireframe is off"
        ) { onEdit(OccludeMarkers(it)) }
    }
}

@Composable
private fun CollisionSettings(overlay: OverlaySettings, onEdit: (SettingsEdit) -> Unit) {
    val settings = overlay.collision
    val on = overlay.enabled && settings.enabled

    SettingsSection("Collision") {
        ToggleRow(
            label = "Draw collision",
            checked = settings.enabled,
            enabled = overlay.enabled,
            explain = "Needs a read first, from the Collision panel in the Runtime tab"
        ) { onEdit(DrawCollision(it)) }

        SliderRow(
            label = "Radius",
            value = settings.radius,
            range = COLLISION_RADIUS,
            steps = sliderSteps(COLLISION_RADIUS, by = 5f),
            enabled = on,
            format = { "${it.toInt()}m" }
        ) { onEdit(CollisionRadius(it)) }

        SliderRow(
            label = "Triangle budget",
            value = settings.triangleBudget.toFloat(),
            range = TRIANGLE_BUDGET,
            steps = sliderSteps(TRIANGLE_BUDGET, by = 500f),
            enabled = on
        ) { onEdit(CollisionTriangleBudget(it.toInt())) }

        Text(
            text = "Nearest geometry is drawn first, so the budget drops what is furthest away",
            style = MaterialTheme.typography.bodySmall,
            color = MaterialTheme.colorScheme.onSurfaceVariant
        )

        SliderRow(
            label = "Opacity",
            value = settings.opacity,
            range = OPACITY,
            steps = sliderSteps(OPACITY, by = 0.05f),
            enabled = on,
            format = { "${(it * 100).toInt()}%" }
        ) { onEdit(CollisionOpacity(it)) }

        ToggleRow(
            label = "Fade with distance",
            checked = settings.depthCue,
            enabled = on
        ) { onEdit(CollisionDepthCue(it)) }

        Text(
            text = "Hidden surfaces",
            style = MaterialTheme.typography.bodyMedium,
            modifier = Modifier.padding(top = 6.dp, bottom = 2.dp)
        )

        Column(modifier = Modifier.selectableGroup()) {
            FaceCull.entries.forEach { mode ->
                Row(verticalAlignment = Alignment.CenterVertically) {
                    RadioButton(
                        selected = settings.cull == mode,
                        onClick = { onEdit(CollisionCull(mode)) },
                        enabled = on
                    )
                    Text(text = mode.describe(), style = MaterialTheme.typography.bodySmall)
                }
            }
        }
    }
}

private fun FaceCull.describe(): String = when (this) {
    FaceCull.Off -> "Draw every face, including the ones pointing away"
    FaceCull.Clockwise -> "Drop clockwise faces"
    FaceCull.CounterClockwise -> "Drop counter-clockwise faces"
}

private fun CharacterDrawing.describe(): String = when (this) {
    CharacterDrawing.None -> "None"
    CharacterDrawing.Instantiated -> "Only the ones the game has built"
    CharacterDrawing.All -> "Those, and the placed enemies it has not built"
}