package com.sappyoak.dsanalyzer.app.data.ai.ui

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

import com.sappyoak.dsanalyzer.app.data.ai.AiState
import com.sappyoak.dsanalyzer.app.data.params.ParamsState
import com.sappyoak.dsanalyzer.app.nav.View
import com.sappyoak.dsanalyzer.app.ui.theme.AnalyzerTheme

/** The detail pane for every AI Scripts view */
@Composable
public fun AiDetail(ai: AiState, params: ParamsState, view: View.Data, onFollow: (View) -> Unit) {
    val index = ai.index
    when (view) {
        is View.Data.LuaFiles -> LuaFilesPane(ai, view.binder, onFollow)
        is View.Data.LuaScript -> LuaSourcePane(ai, params, view, onFollow)
        is View.Data.AiList, is View.Data.AiGoal, is View.Data.AiLogic ->
            if (index == null) AiNote("Decompiling the AI scripts to index their goals and logic…")
            else when (view) {
                is View.Data.AiList -> AiListPane(index, view.kind, onFollow)
                else -> AiEntryPane(ai, index, params, view, onFollow)
            }
        else -> Unit
    }
}

@Composable
internal fun AiNote(text: String) {
    Box(modifier = Modifier.padding(16.dp)) {
        Text(text, style = MaterialTheme.typography.bodyMedium, color = AnalyzerTheme.colors.textLo)
    }
}
