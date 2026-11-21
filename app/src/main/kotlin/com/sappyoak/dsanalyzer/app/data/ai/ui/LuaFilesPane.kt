package com.sappyoak.dsanalyzer.app.data.ai.ui

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

import com.sappyoak.dsanalyzer.app.data.ai.AiState
import com.sappyoak.dsanalyzer.app.data.ui.FilterHeader
import com.sappyoak.dsanalyzer.app.nav.View
import com.sappyoak.dsanalyzer.app.ui.theme.AnalyzerTheme
import com.sappyoak.dsanalyzer.game.world.ai.LuaBinder
import com.sappyoak.dsanalyzer.game.world.ai.LuaScriptId

/** The scripts in one binder, filtered by name */
@Composable
internal fun LuaFilesPane(ai: AiState, binder: LuaBinder, onFollow: (View) -> Unit) {
    val names = ai.binders[binder].orEmpty()
    var filter by remember(binder) { mutableStateOf("") }
    val shown = remember(names, filter) { names.filter { it.contains(filter.trim(), ignoreCase = true) } }

    Column(modifier = Modifier.fillMaxSize()) {
        FilterHeader(binder.title, filter, { filter = it }, "Script name", shown.size, names.size)
        HorizontalDivider(color = AnalyzerTheme.colors.border)
        LazyColumn(modifier = Modifier.fillMaxSize()) {
            items(shown, key = { it }) { name ->
                Text(
                    text = name,
                    style = AnalyzerTheme.text.mono,
                    modifier = Modifier.fillMaxWidth()
                        .clickable { onFollow(View.Data.LuaScript(LuaScriptId(binder, name))) }
                        .padding(horizontal = 16.dp, vertical = 5.dp)
                )
            }
        }
    }
}
