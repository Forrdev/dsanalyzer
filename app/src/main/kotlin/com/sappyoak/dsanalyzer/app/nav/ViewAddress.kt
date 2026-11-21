package com.sappyoak.dsanalyzer.app.nav

import com.sappyoak.dsanalyzer.game.ref.DataAddress
import com.sappyoak.dsanalyzer.game.ref.DataSpace
import com.sappyoak.dsanalyzer.game.world.esd.EsdSource
import com.sappyoak.dsanalyzer.game.world.maps.EntryKind
import com.sappyoak.dsanalyzer.game.world.maps.MapId
import com.sappyoak.dsanalyzer.game.world.scripts.ScriptId

public fun View.Data.toDataAddress(): DataAddress? = when (this) {
    // A navigator grouping, not a thing in the game's data
    is View.Data.Overview, is View.Data.FlagList, is View.Data.AiList -> null

    is View.Data.ParamSearch -> DataAddress(DataSpace.Param.token)
    is View.Data.Param ->
        DataAddress(DataSpace.Param.token, listOf(file))
    is View.Data.ParamRow ->
        DataAddress(DataSpace.Param.token, listOf(file, rowId.toString()))

    is View.Data.Map ->
        DataAddress(DataSpace.Map.token, listOf(map.toString()))
    is View.Data.MapEntry ->
        DataAddress(DataSpace.Map.token, listOf(map.toString(), kind.token, index.toString()))

    is View.Data.Script ->
        DataAddress(DataSpace.Script.token, listOf(script.token))
    is View.Data.ScriptEvent ->
        DataAddress(DataSpace.Script.token, listOf(script.token, EVENT, eventId.toString()))
    is View.Data.EventFlag ->
        DataAddress(DataSpace.Flag.token, listOf("$flagId" + (seenIn?.let { "$SEEN_IN$it" } ?: "")))

    is View.Data.AnimationSet ->
        DataAddress(DataSpace.Animation.token, listOf(set.label))
    is View.Data.Animation ->
        DataAddress(DataSpace.Animation.token, listOf(set.label, animationId.toString()))

    is View.Data.AiGoal ->
        DataAddress(DataSpace.Goal.token, listOf(goalId.toString()))
    is View.Data.AiLogic ->
        DataAddress(DataSpace.Goal.token, listOf(LOGIC, logicId.toString()))
    is View.Data.LuaFiles ->
        DataAddress(DataSpace.Lua.token, listOf(binder.label))
    is View.Data.LuaScript ->
        DataAddress(DataSpace.Lua.token, listOf(script.binder.label, script.name), function)

    is View.Data.Esd ->
        DataAddress(DataSpace.Esd.token, source.segments)
    is View.Data.EsdState ->
        DataAddress(DataSpace.Esd.token, source.segments + listOf(group.toString(), state.toString()))
}

internal const val EVENT: String = "event"

/** Logic shares the goal space, under its own segment */
internal const val LOGIC: String = "logic"

/** Separates a flag's id from the map a citation saw it in */
internal const val SEEN_IN: Char = '@'

internal val EntryKind.token: String get() = name.lowercase()

internal fun entryKindOf(token: String): EntryKind? = when (token) {
    "model" -> EntryKind.Model
    "event" -> EntryKind.Event
    "region" -> EntryKind.Region
    "part" -> EntryKind.Part
    else -> null
}

internal val ScriptId.token: String
    get() = when (this) {
        ScriptId.Common -> COMMON
        is ScriptId.Of -> map.toString()
    }

internal fun scriptIdOf(token: String): ScriptId? =
    if (token == COMMON) ScriptId.Common else MapId.parse(token)?.let(ScriptId::Of)

private const val COMMON = "common"

/** The player's ESD, by the character it belongs to */
internal const val PLAYER_ESD = "c0000"

internal val EsdSource.segments: List<String>
    get() = when (this) {
        EsdSource.Player -> listOf(PLAYER_ESD)
        is EsdSource.Talk -> listOf(map.toString(), talkId.toString())
    }
