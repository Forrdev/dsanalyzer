package com.sappyoak.dsanalyzer.app.data.ai.ui

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.text.selection.SelectionContainer
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.VerticalDivider
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.text.LinkAnnotation
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.TextLinkStyles
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp

import com.sappyoak.dsanalyzer.app.data.ai.AiState
import com.sappyoak.dsanalyzer.app.data.ai.SourceOutline
import com.sappyoak.dsanalyzer.app.data.ai.charactersRunning
import com.sappyoak.dsanalyzer.app.data.params.ParamsState
import com.sappyoak.dsanalyzer.app.nav.View
import com.sappyoak.dsanalyzer.app.ui.theme.AnalyzerTheme
import com.sappyoak.dsanalyzer.game.world.ai.LuaScriptId
import com.sappyoak.dsanalyzer.game.world.ai.SourceLinks
import com.sappyoak.dsanalyzer.game.world.ai.SourceTarget
import com.sappyoak.dsanalyzer.game.world.animations.AnimationSetId

private val OUTLINE_WIDTH = 260.dp
private val LINE_NUMBER_WIDTH = 56.dp

/**
 * A decompiled script beside its function outline, scrolled to the function the view names. Goal and logic
 * constants and global functions in it link to their views once the AI index is built, and EzState ids passed
 * to goals to the animation in the chosen character that runs the script
 */
@Composable
internal fun LuaSourcePane(ai: AiState, params: ParamsState, view: View.Data.LuaScript, onFollow: (View) -> Unit) {
    val problem = ai.problem
    val decompiled = ai.source?.takeIf { ai.shown == view.script }
    if (problem != null) return AiNote(problem)
    if (decompiled == null) return AiNote("Decompiling ${view.script}…")
    val outline = remember(decompiled) { SourceOutline.of(decompiled.source) }
    val target = view.function?.let(outline::lineOf)
    val links = remember(ai.index) { ai.index?.let(::SourceLinks) }
    val characters = remember(ai.index, ai.thinkCharacters, params, view.script) {
        ai.charactersRunning(params, view.script)
    }
    var character by remember(characters) { mutableStateOf(characters.firstOrNull()) }

    Column(modifier = Modifier.fillMaxSize()) {
        Text(
            text = view.script.toString(),
            style = MaterialTheme.typography.titleSmall,
            modifier = Modifier.padding(horizontal = 16.dp, vertical = 8.dp)
        )
        if (decompiled.failures.isNotEmpty()) {
            val reasons = decompiled.failures.distinct().joinToString()
            Text(
                text = "${decompiled.failures.size} function(s) kept as disassembly: $reasons",
                style = AnalyzerTheme.text.label,
                color = AnalyzerTheme.colors.failed,
                modifier = Modifier.padding(horizontal = 16.dp, vertical = 4.dp)
            )
        }
        CharacterPicker(characters, character) { character = it }
        HorizontalDivider(color = AnalyzerTheme.colors.border)
        Row(modifier = Modifier.fillMaxSize()) {
            Outline(outline, view, onFollow)
            VerticalDivider(color = AnalyzerTheme.colors.border)
            Source(outline, target) { line -> linked(line, view.script, links, character, onFollow) }
        }
    }
}

@Composable
private fun Outline(outline: SourceOutline, view: View.Data.LuaScript, onFollow: (View) -> Unit) {
    LazyColumn(modifier = Modifier.width(OUTLINE_WIDTH).fillMaxHeight()) {
        items(outline.functions) { entry ->
            val selected = entry.name == view.function
            Text(
                text = entry.name,
                style = AnalyzerTheme.text.monoSmall,
                color = if (selected) AnalyzerTheme.colors.textHi else AnalyzerTheme.colors.link,
                maxLines = 1,
                overflow = TextOverflow.Ellipsis,
                modifier = Modifier.fillMaxWidth()
                    .let { if (selected) it.background(AnalyzerTheme.colors.raised) else it }
                    .clickable { onFollow(view.copy(function = entry.name)) }
                    .padding(horizontal = 12.dp, vertical = 3.dp)
            )
        }
    }
}

/** The source, numbered, with each line as [text] renders it */
@Composable
private fun Source(outline: SourceOutline, target: Int?, text: @Composable (String) -> AnnotatedString) {
    val listState = rememberLazyListState()
    LaunchedEffect(outline, target) { target?.let { listState.scrollToItem(it) } }

    SelectionContainer {
        LazyColumn(state = listState, modifier = Modifier.fillMaxSize()) {
            itemsIndexed(outline.lines) { index, line ->
                val highlight = if (index == target) Modifier.background(AnalyzerTheme.colors.raised) else Modifier
                Row(modifier = highlight) {
                    Text(
                        text = (index + 1).toString(),
                        style = AnalyzerTheme.text.monoSmall,
                        color = AnalyzerTheme.colors.textLo,
                        modifier = Modifier.width(LINE_NUMBER_WIDTH).padding(start = 8.dp)
                    )
                    Text(text(line), style = AnalyzerTheme.text.monoSmall)
                }
            }
        }
    }
}

/**
 * [line] with the names [links] knows made links; plain until the AI index is built. Animation ids link only
 * when a [character] runs the script
 */
@Composable
private fun linked(
    line: String,
    script: LuaScriptId,
    links: SourceLinks?,
    character: AnimationSetId?,
    onFollow: (View) -> Unit
): AnnotatedString {
    val style = TextLinkStyles(SpanStyle(color = AnalyzerTheme.colors.link))
    return remember(line, script, links, character, style) {
        buildAnnotatedString {
            append(line)
            links?.linksIn(line, script)?.forEach { link ->
                val view = link.target.view(character) ?: return@forEach
                val clickable = LinkAnnotation.Clickable(link.target.toString(), style) { onFollow(view) }
                addLink(clickable, link.start, link.end)
            }
        }
    }
}

private fun SourceTarget.view(character: AnimationSetId?): View.Data? = when (this) {
    is SourceTarget.Goal -> View.Data.AiGoal(id)
    is SourceTarget.Logic -> View.Data.AiLogic(id)
    is SourceTarget.Function -> View.Data.LuaScript(script, name)
    is SourceTarget.Animation -> character?.let { View.Data.Animation(it, id) }
}
