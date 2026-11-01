package com.sappyoak.dsanalyzer.app.shell

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp

import com.sappyoak.dsanalyzer.app.runtime.OverlaySettings
import com.sappyoak.dsanalyzer.app.runtime.RuntimeSettings
import com.sappyoak.dsanalyzer.app.settings.SettingsEdit
import com.sappyoak.dsanalyzer.app.settings.ui.SettingsScreen
import com.sappyoak.dsanalyzer.app.ui.theme.AnalyzerTheme

private const val SHEET_FRACTION = 0.42f
private val SCRIM = Color.Black.copy(alpha = 0.55f)

@Composable
internal fun SettingsSheet(
    overlay: OverlaySettings,
    runtime: RuntimeSettings,
    onEdit: (SettingsEdit) -> Unit,
    onDismiss: () -> Unit
) {
    val colors = AnalyzerTheme.colors

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(SCRIM)
            .clickable(onClick = onDismiss),
        contentAlignment = Alignment.CenterEnd
    ) {
        Column(
            modifier = Modifier
                .fillMaxHeight()
                .fillMaxWidth(SHEET_FRACTION)
                .background(colors.panel)
                .border(width = 1.dp, color = colors.border)
                .clickable(enabled = false, onClick = {})
        ) {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(start = 16.dp, end = 8.dp),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Text(
                    text = "Settings",
                    style = AnalyzerTheme.text.label,
                    color = colors.textHi
                )
                TextButton(onClick = onDismiss) {
                    Text(
                        text = "Close",
                        style = AnalyzerTheme.text.label,
                        color = colors.textMid
                    )
                }
            }

            HorizontalDivider(color = colors.border)
            SettingsScreen(overlay, runtime, onEdit)
        }
    }
}
