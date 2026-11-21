package com.sappyoak.dsanalyzer.app.logs.ui

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.text.selection.SelectionContainer
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

import com.sappyoak.dsanalyzer.app.logs.LogEntry
import com.sappyoak.dsanalyzer.app.logs.LogError
import com.sappyoak.dsanalyzer.app.ui.theme.AnalyzerTheme

private val KEY_WIDTH = 140.dp

/** Everything one line carries: its message, fields, the context it was logged in, and its error */
@Composable
internal fun LogEntryDetail(entry: LogEntry, modifier: Modifier = Modifier) {
    SelectionContainer {
        Column(modifier = modifier.padding(16.dp), verticalArrangement = Arrangement.spacedBy(10.dp)) {
            Text(entry.message, style = AnalyzerTheme.text.mono, color = AnalyzerTheme.colors.textHi)

            Section("Line", listOf(
                "Time" to entry.time.dated(),
                "Level" to entry.level.tag,
                "Logger" to entry.logger,
                "Thread" to entry.thread
            ))
            if (entry.fields.isNotEmpty()) Section("Fields", entry.fields.toList())
            if (entry.context.isNotEmpty()) Section("Context", entry.context.map { (key, value) -> key.label to value })
            entry.error?.let { ErrorTrace(it) }
        }
    }
}

@Composable
private fun Section(title: String, rows: List<Pair<String, String>>) {
    Column(verticalArrangement = Arrangement.spacedBy(2.dp)) {
        Text(title.uppercase(), style = AnalyzerTheme.text.label, color = AnalyzerTheme.colors.textLo)
        rows.forEach { (key, value) ->
            Row(modifier = Modifier.fillMaxWidth()) {
                Text(
                    text = key,
                    style = AnalyzerTheme.text.label,
                    color = AnalyzerTheme.colors.textLo,
                    modifier = Modifier.width(KEY_WIDTH)
                )
                Text(value, style = AnalyzerTheme.text.monoSmall, color = AnalyzerTheme.colors.textMid)
            }
        }
    }
}

/** Our own frames stand out; the library frames between them are dimmed */
@Composable
private fun ErrorTrace(error: LogError, caused: Boolean = false) {
    Column(verticalArrangement = Arrangement.spacedBy(1.dp)) {
        Text(
            text = (if (caused) "CAUSED BY " else "ERROR ") + error.type + (error.message?.let { ": $it" }.orEmpty()),
            style = AnalyzerTheme.text.mono,
            color = AnalyzerTheme.colors.failed
        )
        error.frames.forEach { frame ->
            Text(
                text = "  at $frame",
                style = AnalyzerTheme.text.monoSmall,
                color = if (frame.ours) AnalyzerTheme.colors.textHi else AnalyzerTheme.colors.textLo
            )
        }
        error.cause?.let { ErrorTrace(it, caused = true) }
    }
}
