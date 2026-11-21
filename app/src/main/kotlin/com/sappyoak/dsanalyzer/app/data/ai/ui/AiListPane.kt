package com.sappyoak.dsanalyzer.app.data.ai.ui

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp

import com.sappyoak.dsanalyzer.app.data.ai.AiListKind
import com.sappyoak.dsanalyzer.app.data.ui.FilterHeader
import com.sappyoak.dsanalyzer.app.nav.View
import com.sappyoak.dsanalyzer.app.ui.theme.AnalyzerTheme
import com.sappyoak.dsanalyzer.game.world.ai.AiIndex

private val ID_WIDTH = 80.dp

/** One row of the list: an id, its constant, and how many scripts register it */
private class ListEntry(val id: Int, val constant: String, val copies: Int, val view: View.Data)

/** Every goal or logic the scripts register, filtered by id or constant */
@Composable
internal fun AiListPane(index: AiIndex, kind: AiListKind, onFollow: (View) -> Unit) {
    var filter by remember(kind) { mutableStateOf("") }
    val entries = remember(index, kind) { entriesOf(index, kind) }
    val shown = remember(entries, filter) {
        val text = filter.trim()
        entries.filter {
            text.isEmpty() || it.id.toString().startsWith(text) || it.constant.contains(text, ignoreCase = true)
        }
    }

    Column(modifier = Modifier.fillMaxSize()) {
        FilterHeader(kind.label, filter, { filter = it }, "Id or constant", shown.size, entries.size)
        HorizontalDivider(color = AnalyzerTheme.colors.border)
        LazyColumn(modifier = Modifier.fillMaxSize()) {
            items(shown, key = { it.id }) { entry -> EntryRow(entry) { onFollow(entry.view) } }
        }
    }
}

@Composable
private fun EntryRow(entry: ListEntry, onClick: () -> Unit) {
    Row(modifier = Modifier.fillMaxWidth().clickable(onClick = onClick).padding(horizontal = 16.dp, vertical = 5.dp)) {
        Text(entry.id.toString(), style = AnalyzerTheme.text.mono, modifier = Modifier.width(ID_WIDTH))
        Text(
            text = entry.constant,
            style = MaterialTheme.typography.bodyMedium,
            maxLines = 1,
            overflow = TextOverflow.Ellipsis,
            modifier = Modifier.weight(1f)
        )
        if (entry.copies > 1) {
            Text("${entry.copies} copies", style = AnalyzerTheme.text.label, color = AnalyzerTheme.colors.textLo)
        }
    }
}

private fun entriesOf(index: AiIndex, kind: AiListKind): List<ListEntry> = when (kind) {
    AiListKind.Goals -> index.goals.values.map {
        ListEntry(it.id, it.constant ?: it.name, it.registeredIn.size, View.Data.AiGoal(it.id))
    }
    AiListKind.Logic -> index.logics.values.map {
        ListEntry(it.id, it.constant.orEmpty(), it.registeredIn.size, View.Data.AiLogic(it.id))
    }
}
