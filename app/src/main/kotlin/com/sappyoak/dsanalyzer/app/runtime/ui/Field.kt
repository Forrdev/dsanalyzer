package com.sappyoak.dsanalyzer.app.runtime.ui

import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.width
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

import com.sappyoak.dsanalyzer.shared.math.Vec3

internal val LABEL_WIDTH = 160.dp

@Composable
internal fun Field(label: String, value: String) {
    Row(modifier = Modifier.fillMaxWidth()) {
        Text(
            text = label,
            style = MaterialTheme.typography.bodySmall,
            color = MaterialTheme.colorScheme.onSurfaceVariant,
            modifier = Modifier.width(LABEL_WIDTH)
        )
        Text(text = value, style = MaterialTheme.typography.bodySmall)
    }
}

internal fun Vec3.describe(): String = "%.2f, %.2f, %.2f".format(x, y, z)