package com.sappyoak.dsanalyzer.app.data.ai.ui

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

import com.sappyoak.dsanalyzer.app.data.ai.AiState
import com.sappyoak.dsanalyzer.app.data.ai.charactersOf
import com.sappyoak.dsanalyzer.app.data.ai.thinkRowsUsing
import com.sappyoak.dsanalyzer.app.data.params.ParamsState
import com.sappyoak.dsanalyzer.app.nav.View
import com.sappyoak.dsanalyzer.app.ui.theme.AnalyzerTheme
import com.sappyoak.dsanalyzer.game.world.ai.AiIndex
import com.sappyoak.dsanalyzer.game.world.ai.GoalParameter
import com.sappyoak.dsanalyzer.game.world.ai.LuaBinder
import com.sappyoak.dsanalyzer.game.world.params.GameParam

/** What a goal or logic view shows, whichever it is */
private class EntryPage(
    val title: String,
    val subtitle: String?,
    val functions: List<String>,
    val registeredIn: List<LuaBinder>,
    val parameters: List<GoalParameter> = emptyList(),
    val noUpdate: Boolean = false
)

/** The engine looks this suffix up for a goal's update, and skips it for a goal registered no-update */
private const val UPDATE = "_Update"

/**
 * A goal or logic: its constant and name, each of its functions in every script that defines it, a goal's
 * parameters, the binders that register it, the NpcThinkParam rows that run it and the characters placed with
 * those rows
 */
@Composable
internal fun AiEntryPane(
    ai: AiState,
    index: AiIndex,
    params: ParamsState,
    view: View.Data,
    onFollow: (View) -> Unit
) {
    val entry = entryOf(index, view) ?: return AiNote("No AI binder registers ${titleOf(view)}")
    val users = remember(params, view) { params.thinkRowsUsing(view) }
    val characters = remember(ai.thinkCharacters, params, view) { ai.charactersOf(params, view) }

    Column(modifier = Modifier.fillMaxSize().verticalScroll(rememberScrollState()).padding(16.dp)) {
        Text(entry.title, style = MaterialTheme.typography.titleMedium)
        entry.subtitle?.let { Text(it, style = AnalyzerTheme.text.mono, color = AnalyzerTheme.colors.textLo) }

        Section("Functions")
        if (entry.functions.isEmpty()) Plain("None in the scripts; the engine runs it itself")
        entry.functions.forEach { function ->
            val skipped = entry.noUpdate && function.endsWith(UPDATE)
            Plain(if (skipped) "$function  (never called: registered no-update)" else function)
            index.definitionsOf(function).forEach { script ->
                Link("    $script") { onFollow(View.Data.LuaScript(script, function)) }
            }
        }

        if (entry.parameters.isNotEmpty()) {
            Section("Parameters")
            entry.parameters.forEach {
                Plain("${it.index}  ${it.name}" + if (it.isEzState) "  (EzState: an animation id)" else "")
            }
        }

        Section("Registered in")
        entry.registeredIn.forEach { binder -> Link(binder.label) { onFollow(View.Data.LuaFiles(binder)) } }

        Section("Run by NpcThinkParam")
        if (users.isEmpty()) Plain("No row names it")
        users.forEach { use ->
            Link("${use.rowId}  ${use.label}  (${use.field})") {
                onFollow(View.Data.ParamRow(GameParam.NpcThinkParam.fileName, use.rowId))
            }
        }

        Section("Characters")
        if (characters.isEmpty()) Plain("No enemy placed in a map runs it")
        characters.forEach { Link(it.title()) { onFollow(View.Data.AnimationSet(it)) } }
    }
}

@Composable
private fun Section(title: String) {
    Text(title, style = MaterialTheme.typography.titleSmall, modifier = Modifier.padding(top = 16.dp, bottom = 4.dp))
}

@Composable
private fun Plain(text: String) {
    Text(text, style = AnalyzerTheme.text.mono, color = AnalyzerTheme.colors.textMid)
}

@Composable
private fun Link(text: String, onClick: () -> Unit) {
    Text(
        text = text,
        style = AnalyzerTheme.text.mono,
        color = AnalyzerTheme.colors.link,
        modifier = Modifier.clickable(onClick = onClick).padding(vertical = 2.dp)
    )
}

private fun entryOf(index: AiIndex, view: View.Data): EntryPage? = when (view) {
    is View.Data.AiGoal -> index.goals[view.goalId]?.let { goal ->
        val subtitle = listOfNotNull(goal.constant, goal.name).joinToString("  ·  ")
        EntryPage(titleOf(view), subtitle, goal.functions, goal.registeredIn, goal.parameters, goal.noUpdate)
    }
    is View.Data.AiLogic -> index.logics[view.logicId]?.let { logic ->
        EntryPage(titleOf(view), logic.constant, logic.functions, logic.registeredIn)
    }
    else -> null
}

private fun titleOf(view: View.Data): String = when (view) {
    is View.Data.AiGoal -> "Goal ${view.goalId}"
    is View.Data.AiLogic -> "Logic ${view.logicId}"
    else -> ""
}
