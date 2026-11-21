package com.sappyoak.dsanalyzer.app.logs.ui

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.ColumnScope
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyListScope
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp

import com.sappyoak.dsanalyzer.app.logs.LogFilter
import com.sappyoak.dsanalyzer.app.logs.LogLevel
import com.sappyoak.dsanalyzer.app.logs.LogsState
import com.sappyoak.dsanalyzer.app.nav.LogTab
import com.sappyoak.dsanalyzer.app.nav.View
import com.sappyoak.dsanalyzer.app.ui.theme.AnalyzerTheme

/** The three pages with their counts, then the level and areas the log list shows */
@Composable
public fun ColumnScope.LogsNavigator(
    state: LogsState,
    view: View.Logs,
    onFollow: (View) -> Unit,
    onFilter: (LogFilter) -> Unit
) {
    val filter = state.filter

    LazyColumn(modifier = Modifier.fillMaxSize()) {
        heading("LOGS")
        items(LogTab.entries, key = { "tab:${it.name}" }) { tab ->
            NavRow(tab.label, state.countOf(tab).toString(), selected = view.tab == tab) { onFollow(View.Logs(tab)) }
        }

        heading("SCOPE")
        item(key = "scope:launch") {
            NavRow("This launch", null, selected = filter.thisLaunch) { onFilter(filter.copy(thisLaunch = true)) }
        }
        item(key = "scope:file") {
            NavRow("Whole file", null, selected = !filter.thisLaunch) { onFilter(filter.copy(thisLaunch = false)) }
        }

        heading("LEVEL")
        items(LogLevel.entries.reversed(), key = { "level:${it.name}" }) { level ->
            NavRow("${level.name} and above", null, selected = filter.level == level) {
                onFilter(filter.copy(level = level))
            }
        }

        heading("AREAS")
        items(state.areas, key = { "area:$it" }) { area ->
            NavRow(area, if (area in filter.hiddenAreas) "hidden" else null, selected = area !in filter.hiddenAreas) {
                onFilter(filter.toggled(area))
            }
        }
    }
}

private fun LogsState.countOf(tab: LogTab): Int = when (tab) {
    LogTab.Log -> visible.size
    LogTab.Problems -> problems.size
    LogTab.Timings -> timings.size
}

private fun LazyListScope.heading(text: String) {
    item(key = "heading:$text") {
        Text(
            text = text,
            style = AnalyzerTheme.text.label,
            color = AnalyzerTheme.colors.textLo,
            modifier = Modifier.fillMaxWidth().padding(start = 14.dp, top = 14.dp, bottom = 6.dp)
        )
    }
}

@Composable
private fun NavRow(label: String, detail: String?, selected: Boolean, onClick: () -> Unit) {
    val colors = AnalyzerTheme.colors

    Row(
        modifier = Modifier
            .fillMaxWidth()
            .background(if (selected) colors.raised else Color.Transparent)
            .clickable(onClick = onClick)
            .padding(horizontal = 14.dp, vertical = 5.dp),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        Text(label, style = AnalyzerTheme.text.label, color = if (selected) colors.textHi else colors.textLo)
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
