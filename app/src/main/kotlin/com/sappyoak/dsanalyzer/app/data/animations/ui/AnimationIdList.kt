package com.sappyoak.dsanalyzer.app.data.animations.ui

import androidx.compose.foundation.background
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
import androidx.compose.ui.unit.dp

import com.sappyoak.dsanalyzer.app.data.animations.SetAnimations
import com.sappyoak.dsanalyzer.app.data.animations.animationLabel
import com.sappyoak.dsanalyzer.app.nav.View
import com.sappyoak.dsanalyzer.app.ui.theme.AnalyzerTheme

private val LABEL_WIDTH = 100.dp

/** A set's animation ids, filtered by either spelling of the id */
@Composable
internal fun AnimationIdList(
    title: String,
    animations: SetAnimations,
    selected: Long?,
    onFollow: (View) -> Unit,
    modifier: Modifier
) {
    var filter by remember(animations.set) { mutableStateOf("") }
    val shown = remember(animations, filter) {
        val text = filter.trim()
        animations.ids.filter { text.isEmpty() || it.toString().startsWith(text) || animationLabel(it).contains(text) }
    }

    Column(modifier = modifier) {
        Column(modifier = Modifier.fillMaxWidth().padding(horizontal = 12.dp, vertical = 8.dp)) {
            Text(title, style = MaterialTheme.typography.titleSmall)
            OutlinedTextField(
                value = filter,
                onValueChange = { filter = it },
                label = { Text("Animation id") },
                singleLine = true,
                modifier = Modifier.fillMaxWidth().padding(top = 6.dp)
            )
            Text(
                text = "${shown.size} of ${animations.ids.size}",
                style = AnalyzerTheme.text.label,
                color = AnalyzerTheme.colors.textLo,
                modifier = Modifier.padding(top = 6.dp)
            )
        }
        HorizontalDivider(color = AnalyzerTheme.colors.border)

        LazyColumn(modifier = Modifier.fillMaxSize()) {
            items(shown, key = { it }) { id ->
                val rowModifier = Modifier.fillMaxWidth()
                    .let { if (id == selected) it.background(AnalyzerTheme.colors.raised) else it }
                    .clickable { onFollow(View.Data.Animation(animations.set, id)) }
                    .padding(horizontal = 12.dp, vertical = 4.dp)
                Row(modifier = rowModifier) {
                    Text(animationLabel(id), style = AnalyzerTheme.text.mono, modifier = Modifier.width(LABEL_WIDTH))
                    Text(id.toString(), style = AnalyzerTheme.text.label, color = AnalyzerTheme.colors.textLo)
                }
            }
        }
    }
}
