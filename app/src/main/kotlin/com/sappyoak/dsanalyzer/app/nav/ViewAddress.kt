package com.sappyoak.dsanalyzer.app.nav

import com.sappyoak.dsanalyzer.game.ref.DataAddress
import com.sappyoak.dsanalyzer.game.ref.DataSpace
import com.sappyoak.dsanalyzer.game.world.maps.EntryKind
import com.sappyoak.dsanalyzer.game.world.maps.MapId
import com.sappyoak.dsanalyzer.game.world.scripts.ScriptId

public fun View.Data.toDataAddress(): DataAddress? = when (this) {
    is View.Data.Overview -> null
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
