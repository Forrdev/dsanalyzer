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

@Composable
internal fun FilterColorPickerRows(
    settings: GameCollisionSettings,
    enabled: Boolean,
    onEdit: (SettingsEdit) -> Unit
) {
    var open by remember { mutableStateOf<CollisionHitFilter?>(null) }

    CollisionHitFilter.All.forEach { filter ->
        val colors = settings.colorsOf(filter)

        Row(verticalAlignment = Alignment.CenterVertically) {
            Checkbox(
                checked = filter in settings.filters,
                onCheckedChange = { onEdit(GameCollisionFilter(filter, it)) },
                enabled = enabled
            )
            Text(text = "${filter.id} ${filter.name}", style = MaterialTheme.typography.bodySmall)
            Spacer(modifier = Modifier.weight(1f))
            TextButton(onClick = { open = filter.takeIf { open != it } }, enabled = enabled) {
                Text(if (open == filter) "Close" else "Edit")
            }
        }
    }
}


