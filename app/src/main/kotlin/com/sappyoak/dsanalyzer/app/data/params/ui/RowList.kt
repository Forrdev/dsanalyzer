package com.sappyoak.dsanalyzer.app.data.params.ui

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

import com.sappyoak.dsanalyzer.app.data.params.RowSummary
import com.sappyoak.dsanalyzer.app.ui.theme.AnalyzerTheme

@Composable
internal fun RowFilter(query: String, onQuery: (String) -> Unit) {
    OutlinedTextField(
        value = query,
        onValueChange = onQuery,
        label = { Text("Row id or name") },
        singleLine = true,
        modifier = Modifier.fillMaxWidth().padding(8.dp)
    )
}

@Composable
internal fun RowList(
    rows: List<RowSummary>,
    focused: Int?,
    onSelect: (RowSummary) -> Unit
) {
    LazyColumn(modifier = Modifier.fillMaxSize()) {
        items(rows, key = { it.index }) { row ->
            RowEntry(row, selected = row.index == focused, onSelect = { onSelect(row) })
        }
    }
}

@Composable
private fun RowEntry(row: RowSummary, selected: Boolean, onSelect: () -> Unit) {
    Surface(
        onClick = onSelect,
        color = if (selected) MaterialTheme.colorScheme.surfaceVariant else MaterialTheme.colorScheme.surface,
        modifier = Modifier.fillMaxWidth()
    ) {
        Row(
            modifier = Modifier.padding(horizontal = 12.dp, vertical = 6.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            Text(
                text = row.id.toString(),
                style = AnalyzerTheme.text.mono,
                color = AnalyzerTheme.colors.textHi
            )
            Text(
                text = row.name,
                style = MaterialTheme.typography.bodySmall,
                color = MaterialTheme.colorScheme.onSurfaceVariant
            )
        }
    }
}
