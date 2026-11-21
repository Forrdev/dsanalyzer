package com.sappyoak.dsanalyzer.app.data.ui

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

import com.sappyoak.dsanalyzer.app.ui.theme.AnalyzerTheme

/** A list's title, its filter field, and how many entries the filter leaves */
@Composable
internal fun FilterHeader(
    title: String,
    filter: String,
    onFilter: (String) -> Unit,
    hint: String,
    shown: Int,
    total: Int
) {
    Column(modifier = Modifier.fillMaxWidth().padding(horizontal = 16.dp, vertical = 8.dp)) {
        Text(title, style = MaterialTheme.typography.titleSmall)
        OutlinedTextField(
            value = filter,
            onValueChange = onFilter,
            label = { Text(hint) },
            singleLine = true,
            modifier = Modifier.fillMaxWidth().padding(top = 6.dp)
        )
        Text(
            text = "$shown of $total",
            style = AnalyzerTheme.text.label,
            color = AnalyzerTheme.colors.textLo,
            modifier = Modifier.padding(top = 6.dp)
        )
    }
}
