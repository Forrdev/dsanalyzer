package com.sappyoak.dsanalyzer.app.settings.ui

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.selection.selectableGroup
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.RadioButton
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

import com.sappyoak.dsanalyzer.app.runtime.DrawGameCollision
import com.sappyoak.dsanalyzer.app.runtime.DrawGameHitboxes
import com.sappyoak.dsanalyzer.app.runtime.EngineDrawSettings
import com.sappyoak.dsanalyzer.app.runtime.GameCollisionColorsReset
import com.sappyoak.dsanalyzer.app.runtime.GameCollisionDistance
import com.sappyoak.dsanalyzer.app.runtime.GameCollisionFill
import com.sappyoak.dsanalyzer.app.runtime.GameCollisionFilters
import com.sappyoak.dsanalyzer.app.runtime.GameCollisionMode
import com.sappyoak.dsanalyzer.app.runtime.KILL_PLANES
import com.sappyoak.dsanalyzer.app.settings.SettingsEdit
import com.sappyoak.dsanalyzer.formats.msb.part.CollisionHitFilter
import com.sappyoak.dsanalyzer.runtime.session.DrawMode

/** The top of the range stands for no limit */
private val DISTANCE = 10f..500f

@Composable
internal fun EngineDrawSection(settings: EngineDrawSettings, onEdit: (SettingsEdit) -> Unit) {
    val collision = settings.collision
    val on = collision.enabled

    SettingsSection("In-game draw") {
        Text(
            text = "Drawn by the game itself, inside the scene. Turning one on installs the code caves if they are not in yet",
            style = MaterialTheme.typography.bodySmall,
            color = MaterialTheme.colorScheme.onSurfaceVariant
        )

        ToggleRow(
            label = "Attack hitboxes",
            checked = settings.hitboxes,
            explain = "The game's own hitbox draw, in its own colors. Needs no collision draw"
        ) { onEdit(DrawGameHitboxes(it)) }

        ToggleRow(label = "Collision", checked = on) { onEdit(DrawGameCollision(it)) }

        Column(modifier = Modifier.selectableGroup()) {
            DrawMode.entries.forEach { mode ->
                Row(verticalAlignment = Alignment.CenterVertically) {
                    RadioButton(
                        selected = collision.mode == mode,
                        onClick = { onEdit(GameCollisionMode(mode)) },
                        enabled = on
                    )
                    Text(text = mode.describe(), style = MaterialTheme.typography.bodySmall)
                }
            }
        }

        ToggleRow(
            label = "Fill",
            checked = collision.fill,
            enabled = on,
            explain = "Off draws the wireframe only"
        ) { onEdit(GameCollisionFill(it)) }

        SliderRow(
            label = "Draw distance",
            value = collision.distance ?: DISTANCE.endInclusive,
            range = DISTANCE,
            steps = sliderSteps(DISTANCE, by = 10f),
            enabled = on,
            format = { if (it >= DISTANCE.endInclusive) "no limit" else "${it.toInt()}m" }
        ) { onEdit(GameCollisionDistance(it.takeIf { units -> units < DISTANCE.endInclusive })) }

        Text(
            text = "Measured from the character, so a zoomed-out death cam still shows what is near it",
            style = MaterialTheme.typography.bodySmall,
            color = MaterialTheme.colorScheme.onSurfaceVariant
        )

        Row(verticalAlignment = Alignment.CenterVertically, modifier = Modifier.padding(top = 6.dp)) {
            Text(text = "Hit filters", style = MaterialTheme.typography.bodyMedium)
            TextButton(onClick = { onEdit(GameCollisionFilters(CollisionHitFilter.entries.toSet())) }, enabled = on) {
                Text("All")
            }
            TextButton(onClick = { onEdit(GameCollisionFilters(KILL_PLANES)) }, enabled = on) {
                Text("Kill planes")
            }
            TextButton(onClick = { onEdit(GameCollisionColorsReset) }, enabled = on) {
                Text("Game colors")
            }
        }

        FilterColorRows(collision, enabled = on, onEdit = onEdit)
    }
}

private fun DrawMode.describe(): String = when (this) {
    DrawMode.Lo -> "Low detail. The game data has gaps in places"
    DrawMode.Hi -> "High detail. The game builds it only near the player"
    DrawMode.Both -> "Both"
}
