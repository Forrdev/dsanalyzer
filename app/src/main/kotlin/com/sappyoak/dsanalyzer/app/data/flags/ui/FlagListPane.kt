package com.sappyoak.dsanalyzer.app.data.flags.ui

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
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp

import com.sappyoak.dsanalyzer.app.data.flags.FlagCatalog
import com.sappyoak.dsanalyzer.app.data.flags.FlagGroup
import com.sappyoak.dsanalyzer.app.nav.View
import com.sappyoak.dsanalyzer.app.ui.theme.AnalyzerTheme
import com.sappyoak.dsanalyzer.game.world.flags.FlagName
import com.sappyoak.dsanalyzer.game.world.flags.FlagNames

private val ID_WIDTH = 96.dp
private val KIND_WIDTH = 120.dp

@Composable
public fun FlagListPane(catalog: FlagCatalog, names: FlagNames, group: FlagGroup, onFollow: (View) -> Unit) {
    var filter by remember(group) { mutableStateOf("") }
    val shown = remember(catalog, names, group, filter) {
        catalog[group].filter { id -> filter.isBlank() || matches(id, names[id], filter.trim()) }
    }

    Column(modifier = Modifier.fillMaxSize()) {
        Column(modifier = Modifier.fillMaxWidth().padding(horizontal = 16.dp, vertical = 8.dp)) {
            Text(group.label, style = MaterialTheme.typography.titleSmall)
            OutlinedTextField(
                value = filter,
                onValueChange = { filter = it },
                label = { Text("Flag id or name") },
                singleLine = true,
                modifier = Modifier.fillMaxWidth().padding(top = 6.dp)
            )
            Text(
                text = "${shown.size} of ${catalog[group].size}",
                style = AnalyzerTheme.text.label,
                color = AnalyzerTheme.colors.textLo,
                modifier = Modifier.padding(top = 6.dp)
            )
        }
        HorizontalDivider(color = AnalyzerTheme.colors.border)

        LazyColumn(modifier = Modifier.fillMaxSize()) {
            items(shown, key = { it }) { id -> FlagRow(id, names[id]) { onFollow(View.Data.EventFlag(id)) } }
        }
    }
}

@Composable
private fun FlagRow(flagId: Int, name: FlagName?, onClick: () -> Unit) {
    Row(modifier = Modifier.fillMaxWidth().clickable(onClick = onClick).padding(horizontal = 16.dp, vertical = 5.dp)) {
        Text(flagId.toString(), style = AnalyzerTheme.text.mono, modifier = Modifier.width(ID_WIDTH))
        Text(
            text = name?.kind?.label.orEmpty(),
            style = AnalyzerTheme.text.label,
            color = AnalyzerTheme.colors.textLo,
            modifier = Modifier.width(KIND_WIDTH)
        )
        Text(
            text = name?.label ?: "Unnamed",
            style = MaterialTheme.typography.bodyMedium,
            color = if (name == null) AnalyzerTheme.colors.textLo else AnalyzerTheme.colors.textHi,
            maxLines = 1,
            overflow = TextOverflow.Ellipsis
        )
    }
}

private fun matches(flagId: Int, name: FlagName?, filter: String): Boolean =
    if (filter.all(Char::isDigit)) flagId.toString().startsWith(filter)
    else name?.label?.contains(filter, ignoreCase = true) == true
