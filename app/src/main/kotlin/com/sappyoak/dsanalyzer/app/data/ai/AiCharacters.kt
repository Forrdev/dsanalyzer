package com.sappyoak.dsanalyzer.app.data.ai

import com.sappyoak.dsanalyzer.app.data.params.ParamsState
import com.sappyoak.dsanalyzer.app.nav.View
import com.sappyoak.dsanalyzer.game.world.ai.AiIndex
import com.sappyoak.dsanalyzer.game.world.ai.LuaScriptId
import com.sappyoak.dsanalyzer.game.world.animations.AnimationSetId

/**
 * Human NPCs share the player's model, and their EzState turns the ids their AI sends into commands rather
 * than playing the animation of that id, so no id in their scripts names an animation
 */
private val PLAYER_MODEL = AnimationSetId.Character(0)

/** The characters placed with an NpcThinkParam row that runs [view]'s goal or logic */
public fun AiState.charactersOf(params: ParamsState, view: View.Data): List<AnimationSetId> =
    charactersOfRows(params.thinkRowsUsing(view).map { it.rowId }, thinkCharacters)

/** The characters whose AI runs [script]: through the goals and logic it defines functions for */
public fun AiState.charactersRunning(params: ParamsState, script: LuaScriptId): List<AnimationSetId> {
    val index = index ?: return emptyList()
    val rows = index.entriesDefinedIn(script).flatMap { params.thinkRowsUsing(it) }.map { it.rowId }
    return charactersOfRows(rows, thinkCharacters)
}

/** The characters placed with any of [rows], by think row in [byThink], leaving out human NPCs */
internal fun charactersOfRows(rows: List<Int>, byThink: Map<Int, Set<AnimationSetId>>): List<AnimationSetId> =
    rows.flatMap { byThink[it].orEmpty() }.filter { it != PLAYER_MODEL }.distinct().sortedBy { it.label }

/** The goals and logic whose functions [script] defines */
internal fun AiIndex.entriesDefinedIn(script: LuaScriptId): List<View.Data> {
    val defines = { functions: List<String> -> functions.any { script in definitionsOf(it) } }
    return goals.values.filter { defines(it.functions) }.map { View.Data.AiGoal(it.id) } +
            logics.values.filter { defines(it.functions) }.map { View.Data.AiLogic(it.id) }
}
