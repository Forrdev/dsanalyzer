package com.sappyoak.dsanalyzer.app.data.params.ui

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

import com.sappyoak.dsanalyzer.app.data.shownParam
import com.sappyoak.dsanalyzer.app.data.params.ParamEntry
import com.sappyoak.dsanalyzer.app.data.params.ParamsMessage
import com.sappyoak.dsanalyzer.app.data.params.ParamsState
import com.sappyoak.dsanalyzer.app.data.params.ParamsStore
import com.sappyoak.dsanalyzer.app.data.params.indexOf
import com.sappyoak.dsanalyzer.app.data.params.linksOf
import com.sappyoak.dsanalyzer.app.data.params.nameOf
import com.sappyoak.dsanalyzer.app.data.params.summaries
import com.sappyoak.dsanalyzer.app.data.params.toFieldRows
import com.sappyoak.dsanalyzer.app.data.ui.FieldTable
import com.sappyoak.dsanalyzer.app.nav.Resolution
import com.sappyoak.dsanalyzer.app.nav.View
import com.sappyoak.dsanalyzer.app.ui.theme.AnalyzerTheme
import com.sappyoak.dsanalyzer.game.ref.Address
import com.sappyoak.dsanalyzer.game.world.params.ParamNames

private val ROW_LIST_WIDTH = 320.dp

@Composable
public fun ParamsDetail(
    state: ParamsState,
    store: ParamsStore,
    view: View.Data,
    resolve: (Address) -> Resolution,
    onFollow: (View) -> Unit
) {
    val file = view.shownParam
    val entry = file?.let { state.entries[it] }
    val rowId = (view as? View.Data.ParamRow)?.rowId
    val focused = remember(entry, rowId) { rowId?.let { entry?.table?.indexOf(it) } }

    Row(modifier = Modifier.fillMaxSize()) {
        if (file != null && entry != null) {
            val rows = remember(entry, state.names, state.query) {
                entry.summaries(state.names).filter { it.matches(state.query) }
            }

            Column(
                modifier = Modifier
                    .width(ROW_LIST_WIDTH)
                    .fillMaxHeight()
                    .background(AnalyzerTheme.colors.panel)
            ) {
                RowFilter(state.query) { store.dispatch(ParamsMessage.QueryChanged(it)) }
                HorizontalDivider(color = AnalyzerTheme.colors.border)
                RowList(rows, focused) { onFollow(View.Data.ParamRow(file, it.id)) }
            }

            Box(
                modifier = Modifier
                    .width(1.dp)
                    .fillMaxHeight()
                    .background(AnalyzerTheme.colors.border)
            )
        }

        Column(modifier = Modifier.fillMaxSize()) {
            Header(file, entry, state.names, rowId, focused)
            HorizontalDivider(color = AnalyzerTheme.colors.border)

            state.problem?.let { problem ->
                Text(
                    text = problem,
                    style = MaterialTheme.typography.bodySmall,
                    color = AnalyzerTheme.colors.failed,
                    modifier = Modifier.padding(12.dp)
                )
            }

            if (entry == null || focused == null) {
                Placeholder(placeholderFor(state, file, entry, rowId))
            } else {
                Fields(entry, focused, resolve, onFollow)
            }
        }
    }
}

@Composable
private fun Fields(
    entry: ParamEntry,
    index: Int,
    resolve: (Address) -> Resolution,
    onFollow: (View) -> Unit
) {
    val rows = remember(entry, index) {
        entry.table.fieldsAt(index).toFieldRows(entry.linksOf(entry.table.rows[index]))
    }

    Column(modifier = Modifier.fillMaxSize().verticalScroll(rememberScrollState()).padding(vertical = 6.dp)) {
        FieldTable(rows = rows, resolve = resolve, onFollow = onFollow)
    }
}

/** The param and row being shown, and how many rows share the id when the file repeats it */
@Composable
private fun Header(file: String?, entry: ParamEntry?, names: ParamNames, rowId: Int?, focused: Int?) {
    val row = focused?.let { entry?.table?.rows?.get(it) }
    val name = row?.let { entry?.nameOf(it, names) }.orEmpty()
    val repeats = rowId?.let { entry?.table?.all(it)?.size } ?: 0

    val text = listOfNotNull(
        file,
        row?.let { "${it.id} $name".trim() },
        "id repeated ${repeats}×".takeIf { repeats > 1 }
    ).joinToString(" / ").ifEmpty { "No param" }

    Text(
        text = text,
        style = AnalyzerTheme.text.label,
        color = AnalyzerTheme.colors.textMid,
        modifier = Modifier.padding(horizontal = 12.dp, vertical = 8.dp)
    )
}

@Composable
private fun Placeholder(text: String) {
    Text(
        text = text,
        style = MaterialTheme.typography.bodyMedium,
        color = AnalyzerTheme.colors.textLo,
        modifier = Modifier.padding(16.dp)
    )
}

private fun placeholderFor(state: ParamsState, file: String?, entry: ParamEntry?, rowId: Int?): String = when {
    state.loading -> "Reading the params"
    file == null -> "Select a param from the tree"
    entry == null -> "$file is not in this installation"
    rowId == null -> "Select a row"
    else -> "$file has no row $rowId"
}
