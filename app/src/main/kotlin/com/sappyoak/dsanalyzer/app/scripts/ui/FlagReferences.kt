package com.sappyoak.dsanalyzer.app.scripts.ui

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

import com.sappyoak.dsanalyzer.app.scripts.FlagFindings
import com.sappyoak.dsanalyzer.app.scripts.FlagReference
import com.sappyoak.dsanalyzer.formats.emevd.emedf.FlagAccess
import com.sappyoak.dsanalyzer.game.world.scripts.title

private const val SHOWN = 6

@Composable
internal fun FlagReferences(findings: FlagFindings, onSelect: (FlagReference) -> Unit) {
    Column(modifier = Modifier.fillMaxWidth().padding(horizontal = 8.dp, vertical = 4.dp)) {
        Text(text = findings.summarize(), style = MaterialTheme.typography.labelLarge)

        findings.others.take(SHOWN).forEach { reference ->
            TextButton(
                onClick = { onSelect(reference) },
                contentPadding = PaddingValues(horizontal = 4.dp, vertical = 2.dp)
            ) {
                Text(text = reference.describe(), style = MaterialTheme.typography.bodySmall)
            }
        }

        val hidden = findings.others.size - SHOWN
        if (hidden > 0) {
            Text(
                text = "and $hidden more",
                style = MaterialTheme.typography.bodySmall,
                color = MaterialTheme.colorScheme.onSurfaceVariant,
                modifier = Modifier.padding(horizontal = 4.dp)
            )
        }
    }
}

private fun FlagFindings.summarize():  String {
    val best = chosen ?: return "Flag $flagId is named by no script"
    val verb = if (best.access == FlagAccess.Writes) "set by" else "only read by"
    val rest = others.size

    return buildString {
        append("Flag $flagId $verb ${best.script.title}")
        if (rest == 1) append(", 1 other reference")
        if (rest > 1) append(", $rest other references")
    }
}

private fun FlagReference.describe(): String = buildString {
    append(script.title)
    append(" · ")
    append(label)
    append(" · event")
    append(eventId)
    if (viaSpan) append(" (range)")
}