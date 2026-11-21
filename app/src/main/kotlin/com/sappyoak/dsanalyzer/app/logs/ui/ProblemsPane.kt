package com.sappyoak.dsanalyzer.app.logs.ui

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp

import com.sappyoak.dsanalyzer.app.logs.LogsState
import com.sappyoak.dsanalyzer.app.logs.Problem
import com.sappyoak.dsanalyzer.app.ui.theme.AnalyzerTheme

private val COUNT_WIDTH = 56.dp

/** Warnings and errors gathered by problem, worst and most frequent first; the chosen one opens below */
@Composable
public fun ProblemsPane(state: LogsState, onSelect: (String?) -> Unit) {
    val chosen = state.selectedProblem?.let(state.problems::get)

    Column(modifier = Modifier.fillMaxSize()) {
        if (state.ranked.isEmpty()) {
            Text(
                text = "No warnings or errors in the current log",
                style = AnalyzerTheme.text.label,
                color = AnalyzerTheme.colors.textLo,
                modifier = Modifier.padding(16.dp)
            )
        }

        LazyColumn(modifier = Modifier.weight(1f).fillMaxWidth()) {
            items(state.ranked, key = { it.key }) { problem ->
                ProblemRow(problem, selected = problem.key == chosen?.key) {
                    onSelect(problem.key.takeUnless { it == chosen?.key })
                }
            }
        }

        chosen?.let { problem ->
            HorizontalDivider(color = AnalyzerTheme.colors.border)
            Column(modifier = Modifier.weight(1f).verticalScroll(rememberScrollState())) {
                Text(
                    text = problem.summary(),
                    style = AnalyzerTheme.text.label,
                    color = AnalyzerTheme.colors.textMid,
                    modifier = Modifier.padding(start = 16.dp, top = 12.dp)
                )
                LogEntryDetail(problem.latest)
            }
        }
    }
}

@Composable
private fun ProblemRow(problem: Problem, selected: Boolean, onClick: () -> Unit) {
    val colors = AnalyzerTheme.colors
    val latest = problem.latest

    Row(
        modifier = Modifier
            .fillMaxWidth()
            .background(if (selected) colors.raised else Color.Transparent)
            .clickable(onClick = onClick)
            .padding(horizontal = 16.dp, vertical = 5.dp),
        horizontalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        Text(
            text = "×${problem.count}",
            style = AnalyzerTheme.text.mono,
            color = problem.level.color(),
            modifier = Modifier.width(COUNT_WIDTH)
        )
        Column {
            Text(
                text = latest.message,
                style = AnalyzerTheme.text.label,
                color = colors.textHi,
                maxLines = 1,
                overflow = TextOverflow.Ellipsis
            )
            Text(
                text = problem.where(),
                style = AnalyzerTheme.text.monoSmall,
                color = colors.textLo,
                maxLines = 1,
                overflow = TextOverflow.Ellipsis
            )
        }
    }
}

private fun Problem.summary(): String = "$count times, first ${first.dated()}, last ${latest.time.dated()}"

/** The exception and where in our code it surfaced, or the logger when there is no exception */
private fun Problem.where(): String =
    listOfNotNull(latest.error?.type, latest.error?.origin?.toString() ?: logger).joinToString("  ·  ")
