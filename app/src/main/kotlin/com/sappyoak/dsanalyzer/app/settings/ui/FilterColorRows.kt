package com.sappyoak.dsanalyzer.app.settings.ui

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Checkbox
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
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp

import com.sappyoak.dsanalyzer.app.runtime.DrawColor
import com.sappyoak.dsanalyzer.app.runtime.GameCollisionColors
import com.sappyoak.dsanalyzer.app.runtime.GameCollisionFilter
import com.sappyoak.dsanalyzer.app.runtime.GameCollisionSettings
import com.sappyoak.dsanalyzer.app.runtime.MAX_CHANNEL
import com.sappyoak.dsanalyzer.app.settings.SettingsEdit
import com.sappyoak.dsanalyzer.formats.msb.part.CollisionHitFilter

private val CHANNEL = 0f..MAX_CHANNEL
private val ALPHA = 0f..1f
private val SWATCH = 14.dp

@Composable
internal fun FilterColorRows(settings: GameCollisionSettings, enabled: Boolean, onEdit: (SettingsEdit) -> Unit) {
    var open by remember { mutableStateOf<CollisionHitFilter?>(null) }

    CollisionHitFilter.entries.forEach { filter ->
        val colors = settings.colorsOf(filter)

        Row(verticalAlignment = Alignment.CenterVertically) {
            Checkbox(
                checked = filter in settings.filters,
                onCheckedChange = { onEdit(GameCollisionFilter(filter, it)) },
                enabled = enabled
            )
            Text(text = "${filter.id}  ${filter.name}", style = MaterialTheme.typography.bodySmall)
            Spacer(modifier = Modifier.weight(1f))
            Swatch(colors.wire)
            Swatch(colors.fill)
            TextButton(onClick = { open = filter.takeIf { open != it } }, enabled = enabled) {
                Text(if (open == filter) "Close" else "Edit")
            }
        }

        if (open == filter && enabled) {
            Column(modifier = Modifier.padding(start = 24.dp)) {
                ColorSliders("Wire", colors.wire) { onEdit(GameCollisionColors(filter, colors.copy(wire = it))) }
                ColorSliders("Fill", colors.fill) { onEdit(GameCollisionColors(filter, colors.copy(fill = it))) }
            }
        }
    }
}

@Composable
private fun ColorSliders(label: String, color: DrawColor, onChange: (DrawColor) -> Unit) {
    Text(text = label, style = MaterialTheme.typography.bodyMedium)
    Channel("Red", color.r, CHANNEL) { onChange(color.copy(r = it)) }
    Channel("Green", color.g, CHANNEL) { onChange(color.copy(g = it)) }
    Channel("Blue", color.b, CHANNEL) { onChange(color.copy(b = it)) }
    Channel("Alpha", color.a, ALPHA) { onChange(color.copy(a = it)) }
}

@Composable
private fun Channel(label: String, value: Float, range: ClosedFloatingPointRange<Float>, onChange: (Float) -> Unit) {
    SliderRow(
        label = label,
        value = value,
        range = range,
        steps = sliderSteps(range, by = range.endInclusive / 50f),
        format = { "${(it / range.endInclusive * 100).toInt()}%" },
        onChange = onChange
    )
}

/** Shown at twice the value, since the game's colors top out near half brightness */
@Composable
private fun Swatch(color: DrawColor) {
    Box(
        modifier = Modifier
            .padding(horizontal = 2.dp)
            .size(SWATCH)
            .border(1.dp, MaterialTheme.colorScheme.outline)
            .background(
                Color(
                    (color.r / MAX_CHANNEL).coerceIn(0f, 1f),
                    (color.g / MAX_CHANNEL).coerceIn(0f, 1f),
                    (color.b / MAX_CHANNEL).coerceIn(0f, 1f),
                    color.a.coerceIn(0f, 1f)
                )
            )
    )
}
