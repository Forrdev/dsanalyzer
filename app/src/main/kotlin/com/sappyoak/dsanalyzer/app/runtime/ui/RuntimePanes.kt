package com.sappyoak.dsanalyzer.app.runtime.ui

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.ColumnScope
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyListScope
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

import com.sappyoak.dsanalyzer.app.nav.RuntimePanel
import com.sappyoak.dsanalyzer.app.nav.View
import com.sappyoak.dsanalyzer.app.runtime.RuntimeSettings
import com.sappyoak.dsanalyzer.app.runtime.RuntimeState
import com.sappyoak.dsanalyzer.app.ui.theme.AnalyzerTheme
import com.sappyoak.dsanalyzer.app.ui.theme.Indicator
import com.sappyoak.dsanalyzer.app.ui.theme.of

@Composable
public fun ColumnScope.RuntimeNavigator(
    state: RuntimeState,
    view: View,
    onFollow: (View) -> Unit
) {
    Text(
        text = "Panels",
        style = MaterialTheme.typography.titleSmall,
        modifier = Modifier.fillMaxWidth().padding(horizontal = 12.dp, vertical = 8.dp)
    )
    HorizontalDivider(color = AnalyzerTheme.colors.border)

    LazyColumn(modifier = Modifier.fillMaxSize()) {
        items(RuntimePanel.entries, key = { it.name }) { panel ->
            PanelRow(
                panel = panel,
                indicator = state.indicatorFor(panel),
                selected = (view as? View.Live)?.panel == panel,
                onClick = { onFollow(View.Live(panel)) }
            )
        }
    }
}

internal fun RuntimeState.indicatorFor(panel: RuntimePanel): Indicator {
    val feature = panel.feature

    return when {
        !live -> Indicator.Absent
        feature != null && feature !in supports -> Indicator.Absent
        snapshot == null && panel != RuntimePanel.Attachment -> Indicator.Pending
        else -> Indicator.Resident
    }
}

@Composable
private fun PanelRow(
    panel: RuntimePanel,
    indicator: Indicator,
    selected: Boolean,
    onClick: () -> Unit
) {
    Surface(
        onClick = onClick,
        color = if (selected) {
            MaterialTheme.colorScheme.surfaceVariant
        } else {
            MaterialTheme.colorScheme.surface
        },
        modifier = Modifier.fillMaxWidth()
    ) {
        Row(
            modifier = Modifier.padding(horizontal = 12.dp, vertical = 8.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            Text(
                text = indicator.glyph,
                style = AnalyzerTheme.text.monoSmall,
                color = AnalyzerTheme.colors.of(indicator)
            )
            Text(
                text = panel.label,
                style = MaterialTheme.typography.bodyMedium,
                color = if (indicator == Indicator.Absent) {
                    AnalyzerTheme.colors.textLo
                } else {
                    AnalyzerTheme.colors.textHi
                }
            )
        }
    }
}

@Composable
public fun RuntimeDetail(
    state: RuntimeState,
    settings: RuntimeSettings,
    panel: RuntimePanel,
    onFollow: (View) -> Unit,
    onReadPlacedEnemies: () -> Unit,
    onReadCollision: () -> Unit
) {
    val unsupported = panel.feature?.takeIf { state.live && it !in state.supports }
    val edition = state.attached?.edition?.name

    LazyColumn(modifier = Modifier.fillMaxSize()) {
        when {
            unsupported != null -> note("Not read for ${edition ?: "this edition"} yet")

            panel == RuntimePanel.Attachment -> item(key = "attachment") {
                AttachmentPanel(state, onFollow)
            }

            !state.live -> note(state.problem ?: "Connect to a running game")

            else -> state.snapshot?.let { snapshot ->
                when (panel) {
                    RuntimePanel.Attachment -> Unit
                    RuntimePanel.Player -> item(key = "player") { PlayerPanel(snapshot) }
                    RuntimePanel.Camera -> item(key = "camera") { CameraPanel(snapshot.camera) }
                    RuntimePanel.LoadQueue ->
                        item(key = "load-queue") { LoadQueuePanel(snapshot.loadQueue) }
                    RuntimePanel.Characters -> characterItems(
                        snapshot.characters,
                        state.timings,
                        state.placedEnemies,
                        settings.hideDeadCharacters,
                        onReadPlacedEnemies
                    )
                    RuntimePanel.Collision ->
                        item(key = "collision") { CollisionPanel(state.collision, onReadCollision) }
                    RuntimePanel.Flags -> flagItems(state.flagLog, onFollow)
                }
            } ?: note(state.problem ?: "Waiting for the first sample")
        }
    }
}

private fun LazyListScope.note(what: String) = item(key = "note") {
    Box(
        modifier = Modifier.fillMaxWidth().padding(24.dp),
        contentAlignment = Alignment.Center
    ) {
        Text(what, style = MaterialTheme.typography.bodyMedium, color = AnalyzerTheme.colors.textLo)
    }
}
