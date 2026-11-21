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
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp

import com.sappyoak.dsanalyzer.app.logs.LogEntry
import com.sappyoak.dsanalyzer.app.logs.LogFilter
import com.sappyoak.dsanalyzer.app.logs.LogsState
import com.sappyoak.dsanalyzer.app.ui.theme.AnalyzerTheme

private val TIME_WIDTH = 96.dp
private val LEVEL_WIDTH = 52.dp
private val AREA_WIDTH = 72.dp

/**
 * The lines the filter shows, newest last, following the end of the file while the list is scrolled to it.
 * The chosen line opens below
 */
@Composable
public fun LogListPane(
    state: LogsState,
    onFilter: (LogFilter) -> Unit,
    onSelect: (Long?) -> Unit
) {
    val shown = state.visible
    val list = rememberLazyListState()
    val atEnd = !list.canScrollForward

    LaunchedEffect(shown.size) {
        if (atEnd && shown.isNotEmpty()) list.scrollToItem(shown.lastIndex)
    }

    Column(modifier = Modifier.fillMaxSize()) {
        OutlinedTextField(
            value = state.filter.text,
            onValueChange = { onFilter(state.filter.copy(text = it)) },
            label = { Text("Search messages, loggers and fields") },
            singleLine = true,
            modifier = Modifier.fillMaxWidth().padding(horizontal = 16.dp, vertical = 8.dp)
        )
        state.failure?.let {
            Text(
                text = "The log file cannot be read: $it",
                color = AnalyzerTheme.colors.failed,
                modifier = Modifier.padding(16.dp)
            )
        }
        HorizontalDivider(color = AnalyzerTheme.colors.border)

        LazyColumn(state = list, modifier = Modifier.weight(1f).fillMaxWidth()) {
            items(shown, key = { it.sequence }) { entry ->
                if (entry.launch) LaunchDivider(entry)
                LogRow(entry, selected = entry.sequence == state.selected) {
                    onSelect(entry.sequence.takeUnless { it == state.selected })
                }
            }
        }

        state.shownEntry?.let { entry ->
            HorizontalDivider(color = AnalyzerTheme.colors.border)
            LogEntryDetail(entry, modifier = Modifier.weight(1f).verticalScroll(rememberScrollState()))
        }
    }
}

/** Where a launch of the tool starts, since the file holds every launch of the day */
@Composable
private fun LaunchDivider(entry: LogEntry) {
    Column(modifier = Modifier.fillMaxWidth().padding(top = 8.dp)) {
        HorizontalDivider(color = AnalyzerTheme.colors.accent)
        Text(
            text = "LAUNCH  ${entry.time.dated()}",
            style = AnalyzerTheme.text.label,
            color = AnalyzerTheme.colors.accent,
            modifier = Modifier.padding(horizontal = 16.dp, vertical = 4.dp)
        )
    }
}

@Composable
private fun LogRow(entry: LogEntry, selected: Boolean, onClick: () -> Unit) {
    val colors = AnalyzerTheme.colors

    Row(
        modifier = Modifier
            .fillMaxWidth()
            .background(if (selected) colors.raised else Color.Transparent)
            .clickable(onClick = onClick)
            .padding(horizontal = 16.dp, vertical = 3.dp),
        horizontalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        Cell(entry.time.clock(), colors.textLo, TIME_WIDTH)
        Cell(entry.level.tag, entry.level.color(), LEVEL_WIDTH)
        Cell(entry.area, colors.textLo, AREA_WIDTH)
        Text(
            text = entry.message + if (entry.error != null) "  ·  ${entry.error.type.substringAfterLast('.')}" else "",
            style = AnalyzerTheme.text.monoSmall,
            color = colors.textHi,
            maxLines = 1,
            overflow = TextOverflow.Ellipsis
        )
    }
}

@Composable
private fun Cell(text: String, color: Color, width: Dp) {
    Text(text, style = AnalyzerTheme.text.monoSmall, color = color, modifier = Modifier.width(width))
}
