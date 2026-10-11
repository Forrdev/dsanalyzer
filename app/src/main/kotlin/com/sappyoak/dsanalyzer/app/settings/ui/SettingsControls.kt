package com.sappyoak.dsanalyzer.app.settings.ui

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Slider
import androidx.compose.material3.Switch
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

/** Wide enough for "Triangle budget", so every row's control starts at the same place */
private val LABEL_WIDTH = 170.dp

/** Wide enough for "20000", so a changing value does not shift the slider beside it */
private val VALUE_WIDTH = 56.dp

@Composable
internal fun ToggleRow(
    label: String,
    checked: Boolean,
    enabled: Boolean = true,
    explain: String? = null,
    onChange: (Boolean) -> Unit
) {
    Column(modifier = Modifier.fillMaxWidth().padding(vertical = 2.dp)) {
        Row(verticalAlignment = Alignment.CenterVertically) {
            Text(
                text = label,
                style = MaterialTheme.typography.bodyMedium,
                modifier = Modifier.width(LABEL_WIDTH)
            )
            Switch(checked = checked, onCheckedChange = onChange, enabled = enabled)
        }
        explain?.let {
            Text(
                text = it,
                style = MaterialTheme.typography.bodySmall,
                color = MaterialTheme.colorScheme.onSurfaceVariant,
                modifier = Modifier.padding(start = LABEL_WIDTH, bottom = 4.dp)
            )
        }
    }
}

@Composable
internal fun SliderRow(
    label: String,
    value: Float,
    range: ClosedFloatingPointRange<Float>,
    steps: Int,
    enabled: Boolean = true,
    format: (Float) -> String = { it.toInt().toString() },
    onChange: (Float) -> Unit
) {
    Row(
        modifier = Modifier.fillMaxWidth().padding(vertical = 2.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text(
            text = label,
            style = MaterialTheme.typography.bodyMedium,
            modifier = Modifier.width(LABEL_WIDTH)
        )
        Text(
            text = format(value),
            style = MaterialTheme.typography.bodyMedium,
            color = if (enabled) {
                MaterialTheme.colorScheme.onSurface
            } else {
                MaterialTheme.colorScheme.onSurfaceVariant
            },
            modifier = Modifier.width(VALUE_WIDTH)
        )
        Slider(
            value = value.coerceIn(range),
            onValueChange = onChange,
            valueRange = range,
            steps = steps,
            enabled = enabled,
            modifier = Modifier.width(240.dp)
        )
    }
}

@Composable
internal fun SettingsSection(title: String, content: @Composable () -> Unit) {
    Column(
        modifier = Modifier.fillMaxWidth().padding(horizontal = 16.dp, vertical = 10.dp),
        verticalArrangement = Arrangement.spacedBy(2.dp)
    ) {
        Text(text = title, style = MaterialTheme.typography.titleSmall)
        Spacer(modifier = Modifier.width(4.dp))
        content()
    }
}

internal fun sliderSteps(range: ClosedFloatingPointRange<Float>, by: Float): Int =
    (((range.endInclusive - range.start) / by).toInt() - 1).coerceAtLeast(0)