package com.sappyoak.dsanalyzer.app.shell

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

import com.sappyoak.dsanalyzer.app.nav.Navigator
import com.sappyoak.dsanalyzer.app.nav.Section
import com.sappyoak.dsanalyzer.app.ui.theme.AnalyzerTheme
import com.sappyoak.dsanalyzer.app.ui.theme.MonoFamily

private val BAR_HEIGHT = 40.dp
private val WORDMARK_SIZE = 13.sp
private val WORDMARK_TRACKING = 0.5.sp
private val PILL_RADIUS = 6.dp
private val KEY_RADIUS = 3.dp

@Composable
internal fun SectionBar(
    workspace: String,
    installation: String?,
    navigator: Navigator,
    onSettings: () -> Unit
) {
    val colors = AnalyzerTheme.colors

    Row(
        modifier = Modifier
            .fillMaxWidth()
            .height(BAR_HEIGHT)
            .background(colors.panel)
            .padding(horizontal = 16.dp),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        Text(
            text = workspace,
            style = AnalyzerTheme.text.label.copy(
                fontFamily = MonoFamily,
                fontSize = WORDMARK_SIZE,
                fontWeight = FontWeight.Bold,
                letterSpacing = WORDMARK_TRACKING
            ),
            color = colors.accent,
            maxLines = 1
        )

        Row(horizontalArrangement = Arrangement.spacedBy(2.dp)) {
            Section.entries.forEach { section ->
                SectionPill(
                    section = section,
                    selected = section == navigator.section,
                    onClick = { navigator.open(section) }
                )
            }
        }

        Spacer(modifier = Modifier.weight(1f))

        // Not wired yet: the palette is plan 18's Tools, and an entry point that does nothing on
        // click would be worse than one that is visibly still to come
        Chip(enabled = false) {
            Text("Search or run a tool", style = AnalyzerTheme.text.label, color = colors.textLo)
            Text(
                text = "Ctrl K",
                style = AnalyzerTheme.text.monoSmall,
                color = colors.textLo,
                modifier = Modifier
                    .clip(RoundedCornerShape(KEY_RADIUS))
                    .background(colors.window)
                    .border(1.dp, colors.border, RoundedCornerShape(KEY_RADIUS))
                    .padding(horizontal = 5.dp, vertical = 1.dp)
            )
        }

        Chip(onClick = onSettings) {
            Text("Settings", style = AnalyzerTheme.text.label, color = colors.textMid)
        }

        installation?.let {
            Text(it, style = AnalyzerTheme.text.label, color = colors.textLo, maxLines = 1)
        }
    }
    HorizontalDivider(color = colors.border)
}

@Composable
private fun SectionPill(section: Section, selected: Boolean, onClick: () -> Unit) {
    val colors = AnalyzerTheme.colors

    Box(
        modifier = Modifier
            .clip(RoundedCornerShape(PILL_RADIUS))
            .background(if (selected) colors.accent else Color.Transparent)
            .clickable(onClick = onClick)
            .padding(horizontal = 14.dp, vertical = 7.dp)
    ) {
        Text(
            text = section.label,
            style = AnalyzerTheme.text.label.copy(
                fontWeight = if (selected) FontWeight.SemiBold else FontWeight.Medium
            ),
            color = if (selected) colors.window else colors.textMid
        )
    }
}

@Composable
private fun Chip(
    enabled: Boolean = true,
    onClick: () -> Unit = {},
    content: @Composable () -> Unit
) {
    Row(
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.spacedBy(8.dp),
        modifier = Modifier
            .clip(RoundedCornerShape(PILL_RADIUS))
            .background(AnalyzerTheme.colors.raised)
            .border(1.dp, AnalyzerTheme.colors.border, RoundedCornerShape(PILL_RADIUS))
            .then(if (enabled) Modifier.clickable(onClick = onClick) else Modifier)
            .padding(horizontal = 10.dp, vertical = 6.dp)
    ) {
        content()
    }
}
