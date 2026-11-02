package com.sappyoak.dsanalyzer.app.analysis.ui

import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.unit.dp

import com.sappyoak.dsanalyzer.analysis.engine.Lead
import com.sappyoak.dsanalyzer.app.analysis.AnalysisState
import com.sappyoak.dsanalyzer.app.analysis.summary
import com.sappyoak.dsanalyzer.app.data.FieldRow
import com.sappyoak.dsanalyzer.app.data.ui.FieldTable
import com.sappyoak.dsanalyzer.app.nav.Resolution
import com.sappyoak.dsanalyzer.app.nav.View
import com.sappyoak.dsanalyzer.app.ui.theme.AnalyzerTheme
import com.sappyoak.dsanalyzer.game.ref.Address
import com.sappyoak.dsanalyzer.game.ref.parseAddress

private val CARD = RoundedCornerShape(7.dp)

/** What each analyzer concluded, then every lead, each citing what it is about */
@Composable
public fun LeadsPane(state: AnalysisState, resolve: (Address) -> Resolution, onFollow: (View) -> Unit) {
    LazyColumn(
        modifier = Modifier.fillMaxSize().padding(horizontal = 18.dp),
        verticalArrangement = Arrangement.spacedBy(10.dp)
    ) {
        item(key = "status") {
            Text(
                text = statusOf(state),
                style = AnalyzerTheme.text.label,
                color = AnalyzerTheme.colors.textMid,
                modifier = Modifier.padding(top = 14.dp)
            )
            state.problem?.let { problem ->
                Text(problem, style = MaterialTheme.typography.bodySmall, color = AnalyzerTheme.colors.failed)
            }
        }

        items(state.catalogue.filter { it.id in state.results }, key = { "outcome:${it.id}" }) { descriptor ->
            Text(
                text = "${descriptor.displayName}: ${state.results.getValue(descriptor.id).summary()}",
                style = AnalyzerTheme.text.monoSmall,
                color = AnalyzerTheme.colors.textLo
            )
        }

        items(state.leads, key = { it.key.value }) { lead -> LeadCard(lead, resolve, onFollow) }
    }
}

@Composable
private fun LeadCard(lead: Lead, resolve: (Address) -> Resolution, onFollow: (View) -> Unit) {
    val colors = AnalyzerTheme.colors

    Column(
        modifier = Modifier.fillMaxWidth().clip(CARD).border(1.dp, colors.border, CARD).padding(vertical = 8.dp),
        verticalArrangement = Arrangement.spacedBy(4.dp)
    ) {
        Text(lead.title, style = MaterialTheme.typography.titleSmall, color = colors.textHi, modifier = INSET)
        Text(
            text = listOfNotNull(lead.kind.label, "${lead.confidence} confidence", lead.precedent?.let { "like $it" })
                .joinToString(" · "),
            style = AnalyzerTheme.text.monoSmall,
            color = colors.textLo,
            modifier = INSET
        )
        Text(lead.detail, style = MaterialTheme.typography.bodySmall, color = colors.textMid, modifier = INSET)
        lead.reproduction.forEachIndexed { step, text ->
            Text(
                text = "${step + 1}. $text",
                style = MaterialTheme.typography.bodySmall,
                color = colors.textLo,
                modifier = INSET
            )
        }
        FieldTable(
            rows = lead.citations.map { cited ->
                FieldRow(name = cited.label ?: cited.text, value = "open", link = parseAddress(cited.text))
            },
            resolve = resolve,
            onFollow = onFollow
        )
    }
}

private val INSET = Modifier.padding(horizontal = 14.dp)

private fun statusOf(state: AnalysisState): String = when {
    state.installation == null -> "Open an installation to run analysis"
    state.running -> "Running over ${state.units} units: ${state.concluded} results so far"
    state.results.isEmpty() -> "Choose analyzers and run them over this installation"
    else -> "${state.leads.size} leads from ${state.results.size} analyzers"
}
