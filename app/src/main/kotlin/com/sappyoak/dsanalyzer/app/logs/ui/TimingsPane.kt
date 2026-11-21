package com.sappyoak.dsanalyzer.app.logs.ui

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp

import com.sappyoak.dsanalyzer.app.logs.TimingStat
import com.sappyoak.dsanalyzer.app.ui.theme.AnalyzerTheme

private val NUMBER_WIDTH = 84.dp
private val HEADINGS = listOf("Count", "Average", "Longest", "Last")

/** How long each timed piece of work has taken across the current log, slowest on average first */
@Composable
public fun TimingsPane(timings: List<TimingStat>) {
    Column(modifier = Modifier.fillMaxSize()) {
        TimingRow("Work", HEADINGS, AnalyzerTheme.colors.textLo)
        HorizontalDivider(color = AnalyzerTheme.colors.border)

        if (timings.isEmpty()) {
            Text(
                text = "Nothing has been timed in the current log yet",
                style = AnalyzerTheme.text.label,
                color = AnalyzerTheme.colors.textLo,
                modifier = Modifier.padding(16.dp)
            )
        }

        LazyColumn(modifier = Modifier.fillMaxSize()) {
            items(timings, key = { it.name }) { stat ->
                TimingRow(
                    name = stat.name,
                    numbers = listOf(
                        stat.count.toString(),
                        stat.averageMillis.shown(),
                        stat.longestMillis.shown(),
                        stat.lastMillis.shown()
                    ),
                    color = AnalyzerTheme.colors.textMid
                )
            }
        }
    }
}

@Composable
private fun TimingRow(name: String, numbers: List<String>, color: Color) {
    Row(
        modifier = Modifier.fillMaxWidth().padding(horizontal = 16.dp, vertical = 4.dp),
        horizontalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        Text(name, style = AnalyzerTheme.text.monoSmall, color = color, modifier = Modifier.weight(1f))
        numbers.forEach {
            Text(
                text = it,
                style = AnalyzerTheme.text.monoSmall,
                color = color,
                modifier = Modifier.width(NUMBER_WIDTH),
                textAlign = TextAlign.End
            )
        }
    }
}

/** Fractions only where they matter: under 10 ms they are most of the number */
private fun Double.shown(): String = if (this < 10) "%.2f ms".format(this) else "%.0f ms".format(this)
