package com.sappyoak.dsanalyzer.app.data.ui

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp

import com.sappyoak.dsanalyzer.app.data.DataKind
import com.sappyoak.dsanalyzer.app.ui.theme.AnalyzerTheme

internal val KIND_INDENT = 14.dp
internal val CHILD_INDENT = 32.dp

@Composable
internal fun KindRow(kind: DataKind, count: Int?, open: Boolean, onClick: () -> Unit) {
    val colors = AnalyzerTheme.colors

    Row(
        modifier = Modifier
            .fillMaxWidth()
            .clickable(onClick = onClick)
            .padding(horizontal = KIND_INDENT, vertical = 6.dp),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.spacedBy(9.dp)
    ) {
        Text(
            text = if (open) "▾" else "▸",
            style = AnalyzerTheme.text.monoSmall,
            color = colors.textLo
        )
        Text(
            text = kind.label,
            style = AnalyzerTheme.text.label.copy(fontWeight = FontWeight.Medium),
            color = if (open) colors.textHi else colors.textMid
        )
        Text(
            text = count?.toString() ?: "—",
            style = AnalyzerTheme.text.monoSmall,
            color = colors.textLo,
            modifier = Modifier.weight(1f),
            textAlign = TextAlign.End
        )
    }
}

@Composable
internal fun ChildRow(label: String, detail: String?, selected: Boolean, onClick: () -> Unit) {
    val colors = AnalyzerTheme.colors

    Row(
        modifier = Modifier
            .fillMaxWidth()
            .background(if (selected) colors.raised else Color.Transparent)
            .clickable(onClick = onClick)
            .padding(start = CHILD_INDENT, end = KIND_INDENT, top = 5.dp, bottom = 5.dp),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        Text(
            text = label,
            style = AnalyzerTheme.text.label.copy(
                fontWeight = if (selected) FontWeight.SemiBold else FontWeight.Normal
            ),
            color = if (selected) colors.textHi else colors.textMid
        )
        detail?.let {
            Text(
                text = it,
                style = AnalyzerTheme.text.monoSmall,
                color = colors.textLo,
                modifier = Modifier.weight(1f),
                textAlign = TextAlign.End
            )
        }
    }
}
