package com.sappyoak.dsanalyzer.app.data.esd.ui

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.unit.dp

import com.sappyoak.dsanalyzer.app.data.shownEsd
import com.sappyoak.dsanalyzer.app.data.FieldRow
import com.sappyoak.dsanalyzer.app.data.esd.EsdsState
import com.sappyoak.dsanalyzer.app.data.esd.StateEntry
import com.sappyoak.dsanalyzer.app.data.esd.stateEntries
import com.sappyoak.dsanalyzer.app.data.esd.title
import com.sappyoak.dsanalyzer.app.data.esd.toRows
import com.sappyoak.dsanalyzer.app.data.ui.FieldTable
import com.sappyoak.dsanalyzer.app.nav.Resolution
import com.sappyoak.dsanalyzer.app.nav.View
import com.sappyoak.dsanalyzer.app.ui.theme.AnalyzerTheme
import com.sappyoak.dsanalyzer.game.ref.Address

private val STATE_LIST_WIDTH = 260.dp
private val CARD = RoundedCornerShape(7.dp)

@Composable
public fun EsdDetail(state: EsdsState, view: View.Data, resolve: (Address) -> Resolution, onFollow: (View) -> Unit) {
    val source = view.shownEsd ?: return
    val esd = state.esd.takeIf { state.shown == source }
    val focused = view as? View.Data.EsdState

    Row(modifier = Modifier.fillMaxSize()) {
        if (esd != null) {
            val entries = remember(esd) { esd.stateEntries() }
            LazyColumn(
                modifier = Modifier.width(STATE_LIST_WIDTH).fillMaxHeight().background(AnalyzerTheme.colors.panel)
            ) {
                items(entries, key = { "${it.group}:${it.state}" }) { entry ->
                    val selected = focused != null && focused.group == entry.group && focused.state == entry.state
                    EntryRow(entry, selected) {
                        onFollow(View.Data.EsdState(source, entry.group, entry.state))
                    }
                }
            }
            Box(modifier = Modifier.width(1.dp).fillMaxHeight().background(AnalyzerTheme.colors.border))
        }

        Column(modifier = Modifier.fillMaxSize()) {
            Text(
                text = listOfNotNull(source.title, focused?.let { "group ${it.group} / state ${it.state}" })
                    .joinToString(" / "),
                style = AnalyzerTheme.text.label,
                color = AnalyzerTheme.colors.textMid,
                modifier = Modifier.padding(horizontal = 12.dp, vertical = 8.dp)
            )
            HorizontalDivider(color = AnalyzerTheme.colors.border)
            state.problem?.let { Text(it, color = AnalyzerTheme.colors.failed, modifier = Modifier.padding(12.dp)) }

            val shown = focused?.let { esd?.groups?.get(it.group)?.states?.get(it.state) }
            if (shown == null || focused == null) {
                Text(
                    text = when {
                        state.loading -> "Reading ${source.title}"
                        esd == null -> "Nothing read"
                        focused == null -> "${esd.groups.size} machines — select a state"
                        else -> "No state ${focused.state} in group ${focused.group}"
                    },
                    style = MaterialTheme.typography.bodyMedium,
                    color = AnalyzerTheme.colors.textLo,
                    modifier = Modifier.padding(16.dp)
                )
                return@Column
            }

            Column(
                modifier = Modifier.fillMaxSize().verticalScroll(rememberScrollState()).padding(18.dp),
                verticalArrangement = Arrangement.spacedBy(14.dp)
            ) {
                // Decoding every program is the cost; done once per state shown, not per recomposition
                val names = state.definitions
                val sections = remember(shown, source, focused.group, names) {
                    listOf(
                        "Exits" to shown.conditions.toRows(source, focused.group, names),
                        "On entry" to shown.entryCommands.toRows(names),
                        "Every frame" to shown.whileCommands.toRows(names),
                        "On exit" to shown.exitCommands.toRows(names)
                    )
                }
                sections.forEach { (title, rows) -> Section(title, rows, resolve, onFollow) }
            }
        }
    }
}

@Composable
private fun EntryRow(entry: StateEntry, selected: Boolean, onClick: () -> Unit) {
    Surface(
        onClick = onClick,
        color = if (selected) MaterialTheme.colorScheme.surfaceVariant else MaterialTheme.colorScheme.surface,
        modifier = Modifier.fillMaxWidth()
    ) {
        Text(
            text = "${entry.group} / ${entry.state}  ·  ${entry.exits} exits",
            style = AnalyzerTheme.text.mono,
            modifier = Modifier.padding(horizontal = 12.dp, vertical = 6.dp)
        )
    }
}

@Composable
private fun Section(title: String, rows: List<FieldRow>, resolve: (Address) -> Resolution, onFollow: (View) -> Unit) {
    if (rows.isEmpty()) return
    Column(modifier = Modifier.fillMaxWidth().clip(CARD).border(1.dp, AnalyzerTheme.colors.border, CARD)) {
        Text(
            text = title,
            style = MaterialTheme.typography.titleSmall,
            color = AnalyzerTheme.colors.textHi,
            modifier = Modifier.padding(horizontal = 14.dp, vertical = 8.dp)
        )
        HorizontalDivider(color = AnalyzerTheme.colors.border)
        FieldTable(rows = rows, resolve = resolve, onFollow = onFollow)
    }
}
