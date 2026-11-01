package com.sappyoak.dsanalyzer.app.data.ui

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp

import com.sappyoak.dsanalyzer.app.data.FieldRow
import com.sappyoak.dsanalyzer.app.nav.Resolution
import com.sappyoak.dsanalyzer.app.nav.View
import com.sappyoak.dsanalyzer.app.ui.theme.AnalyzerTheme
import com.sappyoak.dsanalyzer.game.ref.Address

private val NAME_WIDTH = 260.dp
private val VALUE_WIDTH = 150.dp


@Composable
public fun FieldTable(
    rows: List<FieldRow>,
    resolve: (Address) -> Resolution,
    onFollow: (View) -> Unit,
    modifier: Modifier = Modifier
) {
    Column(modifier = modifier.fillMaxWidth()) {
        rows.forEachIndexed { index, row ->
            if (index > 0) HorizontalDivider(color = AnalyzerTheme.colors.border)
            FieldRowView(row, resolve, onFollow)
        }
    }
}

@Composable
private fun FieldRowView(
    row: FieldRow,
    resolve: (Address) -> Resolution,
    onFollow: (View) -> Unit
) {
    val colors = AnalyzerTheme.colors
    val resolution = row.link?.let(resolve)
    val target = (resolution as? Resolution.Resolved)?.view

    Row(
        modifier = Modifier
            .fillMaxWidth()
            .then(if (target != null) Modifier.clickable { onFollow(target) } else Modifier)
            .padding(horizontal = 14.dp, vertical = 7.dp),
        verticalAlignment = Alignment.Top,
        horizontalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        Text(
            text = row.name,
            style = AnalyzerTheme.text.label,
            color = colors.textMid,
            modifier = Modifier.width(NAME_WIDTH)
        )

        Text(
            text = row.value,
            style = AnalyzerTheme.text.mono,
            color = when {
                target != null -> colors.link
                !row.stated -> colors.textLo
                else -> colors.textHi
            },
            textAlign = TextAlign.End,
            modifier = Modifier.width(VALUE_WIDTH)
        )

        row.noteWith(resolution)?.let { note ->
            Text(
                text = note,
                style = AnalyzerTheme.text.label,
                color = if (resolution is Resolution.Absent) colors.absent else colors.textLo,
                modifier = Modifier.weight(1f)
            )
        }
    }
}

private fun FieldRow.noteWith(resolution: Resolution?): String? = when (resolution) {
    is Resolution.Absent -> "not in this installation"
    is Resolution.NoView -> "no view for this yet"
    is Resolution.Malformed -> "unreadable reference"
    else -> note
}
