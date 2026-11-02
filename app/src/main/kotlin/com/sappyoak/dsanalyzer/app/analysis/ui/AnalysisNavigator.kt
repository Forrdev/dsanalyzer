package com.sappyoak.dsanalyzer.app.analysis.ui

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ColumnScope
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.Checkbox
import androidx.compose.material3.LinearProgressIndicator
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

import com.sappyoak.dsanalyzer.analysis.engine.AnalyzerDescriptor
import com.sappyoak.dsanalyzer.app.analysis.AnalysisMessage
import com.sappyoak.dsanalyzer.app.analysis.AnalysisState
import com.sappyoak.dsanalyzer.app.analysis.AnalysisStore
import com.sappyoak.dsanalyzer.app.ui.theme.AnalyzerTheme

/** The catalogue: which analyzers will run, and the button that runs them */
@Composable
public fun ColumnScope.AnalysisNavigator(state: AnalysisState, store: AnalysisStore) {
    Text(
        text = "ANALYZERS",
        style = AnalyzerTheme.text.label,
        color = AnalyzerTheme.colors.textLo,
        modifier = Modifier.padding(start = 14.dp, top = 14.dp, bottom = 10.dp)
    )

    state.catalogue.forEach { descriptor ->
        AnalyzerRow(descriptor, enabled = descriptor.id in state.enabled) {
            store.dispatch(AnalysisMessage.Toggled(descriptor.id))
        }
    }

    Column(modifier = Modifier.fillMaxWidth().padding(14.dp), verticalArrangement = Arrangement.spacedBy(8.dp)) {
        Button(
            onClick = { store.dispatch(AnalysisMessage.RunRequested) },
            enabled = !state.running && state.installation != null && state.enabled.isNotEmpty()
        ) { Text(if (state.running) "Running" else "Run") }

        if (state.running) {
            LinearProgressIndicator(modifier = Modifier.fillMaxWidth())
            Text(
                text = "${state.concluded} results",
                style = AnalyzerTheme.text.monoSmall,
                color = AnalyzerTheme.colors.textLo
            )
        }
    }
}

@Composable
private fun AnalyzerRow(descriptor: AnalyzerDescriptor, enabled: Boolean, onToggle: () -> Unit) {
    Row(
        modifier = Modifier.fillMaxWidth().padding(horizontal = 6.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Checkbox(checked = enabled, onCheckedChange = { onToggle() })
        Column {
            Text(descriptor.displayName, style = AnalyzerTheme.text.label, color = AnalyzerTheme.colors.textHi)
            Text(descriptor.summary, style = AnalyzerTheme.text.monoSmall, color = AnalyzerTheme.colors.textLo)
        }
    }
}
