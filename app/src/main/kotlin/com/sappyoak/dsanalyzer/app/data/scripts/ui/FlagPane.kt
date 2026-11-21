package com.sappyoak.dsanalyzer.app.data.scripts.ui

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

import com.sappyoak.dsanalyzer.app.data.flags.FlagLive
import com.sappyoak.dsanalyzer.app.data.scripts.ScriptsState
import com.sappyoak.dsanalyzer.app.nav.View
import com.sappyoak.dsanalyzer.app.nav.view
import com.sappyoak.dsanalyzer.app.ui.theme.AnalyzerTheme
import com.sappyoak.dsanalyzer.formats.emevd.emedf.FlagAccess
import com.sappyoak.dsanalyzer.game.world.flags.FlagName
import com.sappyoak.dsanalyzer.game.world.flags.FlagSite
import com.sappyoak.dsanalyzer.game.world.scripts.index.FlagReference
import com.sappyoak.dsanalyzer.game.world.scripts.title

/**
 * One flag: its live value, what the game data says it means, then every place that sets or reads it, the
 * event scripts first and then everywhere else
 */
@Composable
public fun FlagPane(state: ScriptsState, flagId: Int, live: FlagLive, onFollow: (View) -> Unit) {
    val references = state.findings?.takeIf { it.flagId == flagId }?.references
    val sites = state.flagSites[flagId]

    Column(modifier = Modifier.fillMaxSize()) {
        Column(modifier = Modifier.fillMaxWidth().padding(horizontal = 16.dp, vertical = 8.dp)) {
            Text("Flag $flagId", style = MaterialTheme.typography.titleSmall)
            Text(
                text = live.label,
                style = AnalyzerTheme.text.label,
                color = if (live == FlagLive.Set) AnalyzerTheme.colors.textHi else AnalyzerTheme.colors.textLo
            )
            state.flagNames[flagId]?.let { Meaning(it, onFollow) }
            state.namingProblem?.let { Text(it, style = AnalyzerTheme.text.label, color = AnalyzerTheme.colors.failed) }
            Text(
                text = summarize(references, sites) ?: state.describeWait(),
                style = AnalyzerTheme.text.label,
                color = AnalyzerTheme.colors.textLo
            )
        }
        HorizontalDivider()

        state.problem?.let { problem ->
            Text(
                text = problem,
                style = MaterialTheme.typography.bodySmall,
                color = AnalyzerTheme.colors.failed,
                modifier = Modifier.padding(12.dp)
            )
        }

        LazyColumn(modifier = Modifier.fillMaxSize()) {
            if (!references.isNullOrEmpty()) {
                item(key = "heading:scripts") { Heading("Event scripts") }
                items(references) { reference ->
                    AccessRow(reference.access, reference.describe()) {
                        onFollow(View.Data.ScriptEvent(reference.script, reference.eventId))
                    }
                }
            }
            flagSites(sites, onFollow)
        }
    }
}

/** The best reading, then every other, each with its kind, whether it names this flag itself, and a link to it */
@Composable
private fun Meaning(name: FlagName, onFollow: (View) -> Unit) {
    name.evidence.forEachIndexed { index, evidence ->
        val view = evidence.source.view()
        Text(
            text = "${evidence.kind.label}: ${evidence.label}${if (evidence.direct) "" else " (shares an event)"}",
            style = if (index == 0) MaterialTheme.typography.bodyMedium else AnalyzerTheme.text.label,
            color = when {
                view != null -> AnalyzerTheme.colors.link
                index == 0 -> AnalyzerTheme.colors.textHi
                else -> AnalyzerTheme.colors.textLo
            },
            modifier = view?.let { Modifier.clickable { onFollow(it) } } ?: Modifier
        )
    }
}

private fun ScriptsState.describeWait(): String = when {
    indexing -> "Reading every script"
    flagIndex.built -> "Used nowhere the game data shows"
    else -> "Scripts have not been searched"
}

/** How many places set and read the flag, counting the event scripts and everything else; null before any is known */
private fun summarize(references: List<FlagReference>?, sites: List<FlagSite>): String? {
    if (references == null && sites.isEmpty()) return null
    val accesses = references.orEmpty().map { it.access } + sites.map { it.access }
    if (accesses.isEmpty()) return "Used nowhere the game data shows"

    val writes = accesses.count { it == FlagAccess.Writes }
    val reads = accesses.size - writes
    return listOfNotNull(
        writes.takeIf { it > 0 }?.let { "set in $it place${plural(it)}" },
        reads.takeIf { it > 0 }?.let { "read in $it place${plural(it)}" }
    ).joinToString(", ")
}

private fun plural(count: Int): String = if (count == 1) "" else "s"

private fun FlagReference.describe(): String = buildString {
    append(script.title)
    append(" · ")
    append(label)
    append(" · event ")
    append(eventId)
    if (viaSpan) append(" (range)")
}
