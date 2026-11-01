package com.sappyoak.dsanalyzer.app.data.scripts.ui

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

import com.sappyoak.dsanalyzer.app.data.scripts.ScriptsState
import com.sappyoak.dsanalyzer.app.nav.Resolution
import com.sappyoak.dsanalyzer.app.nav.View
import com.sappyoak.dsanalyzer.app.ui.theme.AnalyzerTheme
import com.sappyoak.dsanalyzer.game.ref.Address

@Composable
internal fun InstructionPane(
    state: ScriptsState,
    eventId: Long,
    resolve: (Address) -> Resolution,
    onFollow: (View) -> Unit
) {
    val contents = state.contents
    val event = state.event(eventId)

    Column(modifier = Modifier.fillMaxSize()) {
        state.problem?.let { problem ->
            Text(
                text = problem,
                style = MaterialTheme.typography.bodySmall,
                color = AnalyzerTheme.colors.failed,
                modifier = Modifier.padding(12.dp)
            )
        }

        if (contents == null || event == null) {
            Text(
                text = if (state.loading) "Reading the script" else "Event $eventId is not here",
                style = MaterialTheme.typography.bodyMedium,
                color = AnalyzerTheme.colors.textLo,
                modifier = Modifier.padding(16.dp)
            )
            return@Column
        }

        Column(modifier = Modifier.fillMaxWidth().padding(horizontal = 18.dp, vertical = 12.dp)) {
            Text(
                text = event.name ?: "Event ${event.id}",
                style = MaterialTheme.typography.titleMedium,
                color = AnalyzerTheme.colors.textHi
            )
            Text(
                text = "${contents.script.label} · event ${event.id}",
                style = AnalyzerTheme.text.label,
                color = AnalyzerTheme.colors.textLo
            )
        }
        HorizontalDivider(color = AnalyzerTheme.colors.border)

        LazyColumn(
            modifier = Modifier.fillMaxSize().padding(horizontal = 18.dp),
            verticalArrangement = Arrangement.spacedBy(10.dp),
            contentPadding = PaddingValues(vertical = 14.dp)
        ) {
            itemsIndexed(contents.instructions(event.id)) { index, instruction ->
                InstructionCard(index, instruction, contents.definitions, resolve, onFollow)
            }
        }
    }
}
