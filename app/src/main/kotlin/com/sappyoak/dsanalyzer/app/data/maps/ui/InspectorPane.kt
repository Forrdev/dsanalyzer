package com.sappyoak.dsanalyzer.app.data.maps.ui

import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.unit.dp

import com.sappyoak.dsanalyzer.app.data.FieldRow
import com.sappyoak.dsanalyzer.app.data.maps.MapsState
import com.sappyoak.dsanalyzer.app.data.maps.inspect.InspectorSection
import com.sappyoak.dsanalyzer.app.data.maps.inspect.inspect
import com.sappyoak.dsanalyzer.app.data.ui.FieldTable
import com.sappyoak.dsanalyzer.app.nav.Resolution
import com.sappyoak.dsanalyzer.app.nav.View
import com.sappyoak.dsanalyzer.app.ui.theme.AnalyzerTheme
import com.sappyoak.dsanalyzer.game.ref.Address

private val CARD_RADIUS = 7.dp

@Composable
internal fun InspectorPane(
    state: MapsState,
    focused: View.Data.MapEntry?,
    resolve: (Address) -> Resolution,
    onFollow: (View) -> Unit
) {
    val contents = state.contents
    val colors = AnalyzerTheme.colors

    Column(modifier = Modifier.fillMaxSize()) {
        Breadcrumb(state, focused)
        HorizontalDivider(color = colors.border)

        state.problem?.let { problem ->
            Text(
                text = problem,
                style = MaterialTheme.typography.bodySmall,
                color = colors.failed,
                modifier = Modifier.padding(12.dp)
            )
        }

        if (contents == null || focused == null) {
            Text(
                text = when {
                    state.loading -> "Reading the map"
                    state.shown == null -> "Select a map from the tree"
                    else -> "Select an entry"
                },
                style = MaterialTheme.typography.bodyMedium,
                color = colors.textLo,
                modifier = Modifier.padding(16.dp)
            )
            return@Column
        }

        Column(
            modifier = Modifier.fillMaxSize().verticalScroll(rememberScrollState()).padding(18.dp),
            verticalArrangement = Arrangement.spacedBy(14.dp)
        ) {
            remember(contents, focused) { contents.inspect(focused) }.forEach { section ->
                SectionCard(section, resolve, onFollow)
            }
        }
    }
}


@Composable
private fun Breadcrumb(state: MapsState, focused: View.Data.MapEntry?) {
    val entry = focused?.let { state.contents?.entryAt(it) }

    Row(modifier = Modifier.fillMaxWidth().padding(horizontal = 12.dp, vertical = 8.dp)) {
        Text(
            text = listOfNotNull(state.shown?.name, entry?.name).joinToString(" / ")
                .ifEmpty { "No map" },
            style = AnalyzerTheme.text.label,
            color = AnalyzerTheme.colors.textMid
        )
    }
}

@Composable
private fun SectionCard(
    section: InspectorSection,
    resolve: (Address) -> Resolution,
    onFollow: (View) -> Unit
) {
    val colors = AnalyzerTheme.colors

    Column(
        modifier = Modifier
            .fillMaxWidth()
            .clip(RoundedCornerShape(CARD_RADIUS))
            .border(1.dp, colors.border, RoundedCornerShape(CARD_RADIUS))
    ) {
        Text(
            text = section.title,
            style = MaterialTheme.typography.titleSmall,
            color = colors.textHi,
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 14.dp, vertical = 8.dp)
        )
        HorizontalDivider(color = colors.border)

        FieldTable(
            rows = section.rows.map { FieldRow(it.label, it.value, link = it.link) },
            resolve = resolve,
            onFollow = onFollow
        )
    }
}
