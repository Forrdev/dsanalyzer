package com.sappyoak.dsanalyzer.app.data.scripts.ui

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
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
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

import com.sappyoak.dsanalyzer.app.nav.View
import com.sappyoak.dsanalyzer.app.data.scripts.FlagFindings
import com.sappyoak.dsanalyzer.app.data.scripts.FlagReference
import com.sappyoak.dsanalyzer.app.data.scripts.ScriptsState
import com.sappyoak.dsanalyzer.app.ui.theme.AnalyzerTheme
import com.sappyoak.dsanalyzer.formats.emevd.emedf.FlagAccess
import com.sappyoak.dsanalyzer.game.world.scripts.title

private val ACCESS_WIDTH = 64.dp

@Composable
internal fun FlagPane(state: ScriptsState, flagId: Int, onFollow: (View) -> Unit) {
    val findings = state.findings?.takeIf { it.flagId == flagId }

    Column(modifier = Modifier.fillMaxSize()) {
        Column(modifier = Modifier.fillMaxWidth().padding(horizontal = 16.dp, vertical = 8.dp)) {
            Text("Flag $flagId", style = MaterialTheme.typography.titleSmall)
            Text(
                text = findings?.summarize() ?: state.describeWait(),
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

        if (findings == null) return@Column

        LazyColumn(modifier = Modifier.fillMaxSize()) {
            items(findings.references) { reference ->
                Reference(reference) {
                    onFollow(View.Data.ScriptEvent(reference.script, reference.eventId))
                }
            }
        }
    }
}

@Composable
private fun Reference(reference: FlagReference, onFollow: () -> Unit) {
    Row(
        modifier = Modifier.fillMaxWidth().padding(horizontal = 12.dp),
        horizontalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        Text(
            text = if (reference.access == FlagAccess.Writes) "sets" else "reads",
            style = AnalyzerTheme.text.label,
            color = if (reference.access == FlagAccess.Writes) {
                AnalyzerTheme.colors.pending
            } else {
                AnalyzerTheme.colors.textLo
            },
            modifier = Modifier.width(ACCESS_WIDTH)
        )

        TextButton(onClick = onFollow, contentPadding = PaddingValues(0.dp)) {
            Text(
                text = reference.describe(),
                style = MaterialTheme.typography.bodySmall,
                color = AnalyzerTheme.colors.link
            )
        }
    }
}

private fun ScriptsState.describeWait(): String = when {
    indexing -> "Reading every script"
    flagIndex.built -> "Nothing found"
    else -> "Scripts have not been searched"
}

private fun FlagFindings.summarize(): String {
    if (references.isEmpty()) return "Named by no script"

    val writes = references.count { it.access == FlagAccess.Writes }
    val reads = references.size - writes

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
