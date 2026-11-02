package com.sappyoak.dsanalyzer.app.data.params.ui

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Button
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.LinearProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

import com.sappyoak.dsanalyzer.app.data.params.ParamMatch
import com.sappyoak.dsanalyzer.app.data.params.ParamsMessage
import com.sappyoak.dsanalyzer.app.data.params.ParamsState
import com.sappyoak.dsanalyzer.app.data.params.ParamsStore
import com.sappyoak.dsanalyzer.app.data.params.SEARCH_LIMIT
import com.sappyoak.dsanalyzer.app.nav.View
import com.sappyoak.dsanalyzer.app.ui.theme.AnalyzerTheme

private val FIELD_WIDTH = 260.dp

@Composable
public fun ParamSearchPane(state: ParamsState, store: ParamsStore, onFollow: (View) -> Unit) {
    val search = state.search

    Column(modifier = Modifier.fillMaxSize()) {
        Row(
            modifier = Modifier.fillMaxWidth().padding(12.dp),
            horizontalArrangement = Arrangement.spacedBy(10.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            OutlinedTextField(
                value = search.field,
                onValueChange = { store.dispatch(ParamsMessage.SearchEdited(search.copy(field = it))) },
                label = { Text("Field name contains (optional)") },
                singleLine = true,
                modifier = Modifier.width(FIELD_WIDTH)
            )
            OutlinedTextField(
                value = search.value,
                onValueChange = { store.dispatch(ParamsMessage.SearchEdited(search.copy(value = it))) },
                label = { Text("Value, as shown") },
                singleLine = true,
                modifier = Modifier.width(FIELD_WIDTH)
            )
            Button(
                onClick = { store.dispatch(ParamsMessage.SearchRequested) },
                enabled = search.runnable && state.params != null
            ) { Text("Search") }
        }

        if (state.searching) LinearProgressIndicator(modifier = Modifier.fillMaxWidth())
        HorizontalDivider(color = AnalyzerTheme.colors.border)

        Text(
            text = statusOf(state),
            style = AnalyzerTheme.text.label,
            color = AnalyzerTheme.colors.textMid,
            modifier = Modifier.padding(horizontal = 12.dp, vertical = 8.dp)
        )

        LazyColumn(modifier = Modifier.fillMaxSize()) {
            items(state.searchOutcome?.matches.orEmpty()) { match ->
                MatchRow(match) { onFollow(View.Data.ParamRow(match.file, match.rowId)) }
            }
        }
    }
}

@Composable
private fun MatchRow(match: ParamMatch, onClick: () -> Unit) {
    Surface(onClick = onClick, color = MaterialTheme.colorScheme.surface, modifier = Modifier.fillMaxWidth()) {
        Row(
            modifier = Modifier.padding(horizontal = 12.dp, vertical = 6.dp),
            horizontalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            Text(match.file, style = AnalyzerTheme.text.label, color = AnalyzerTheme.colors.textMid)
            Text(match.rowId.toString(), style = AnalyzerTheme.text.mono, color = AnalyzerTheme.colors.link)
            Text(match.rowName, style = MaterialTheme.typography.bodySmall, color = AnalyzerTheme.colors.textLo)
            Text(
                text = "${match.field} = ${match.value}",
                style = AnalyzerTheme.text.mono,
                color = AnalyzerTheme.colors.textHi
            )
        }
    }
}

private fun statusOf(state: ParamsState): String {
    val outcome = state.searchOutcome ?: return when {
        state.loading -> "Reading the params"
        state.params == null -> "No params loaded"
        else -> "Search every row of every param for a value"
    }
    val count = outcome.matches.size
    val where = outcome.query.field.takeIf { it.isNotBlank() }?.let { " in fields named like '$it'" }.orEmpty()
    return when {
        outcome.truncated ->
            "First $SEARCH_LIMIT matches for ${outcome.query.value}$where; narrow by field to see the rest"
        count == 0 -> "No field holds ${outcome.query.value}$where"
        else -> "$count matches for ${outcome.query.value}$where"
    }
}
