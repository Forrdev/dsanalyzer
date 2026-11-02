package com.sappyoak.dsanalyzer.app.nav

import com.sappyoak.dsanalyzer.game.ref.DataAddress
import com.sappyoak.dsanalyzer.game.ref.DataSpace
import com.sappyoak.dsanalyzer.game.world.esd.EsdSource
import com.sappyoak.dsanalyzer.game.world.maps.EntryKind
import com.sappyoak.dsanalyzer.game.world.maps.MapId
import com.sappyoak.dsanalyzer.game.world.scripts.ScriptId

public fun View.Data.toDataAddress(): DataAddress? = when (this) {
    is View.Data.Overview -> null

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

    is View.Data.Esd ->
        DataAddress(DataSpace.Esd.token, source.segments)
    is View.Data.EsdState ->
        DataAddress(DataSpace.Esd.token, source.segments + listOf(group.toString(), state.toString()))
}

internal const val EVENT: String = "event"

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
