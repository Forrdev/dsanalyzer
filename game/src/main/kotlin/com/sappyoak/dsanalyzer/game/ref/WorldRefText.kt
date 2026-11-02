package com.sappyoak.dsanalyzer.game.ref

import com.sappyoak.dsanalyzer.game.world.maps.EntryKind
import com.sappyoak.dsanalyzer.game.world.maps.MapId
import com.sappyoak.dsanalyzer.game.world.scripts.ScriptId

private const val EVENT = "event"
private const val SEEN_IN = '@'

public fun WorldRef.toDataAddress(): DataAddress = when (this) {
    is WorldRef.Map ->
        DataAddress(DataSpace.Map.token, listOf(map.toString()))
    is WorldRef.Entry ->
        DataAddress(DataSpace.Map.token, listOf(map.toString(), kind.token, index.toString()))
    is WorldRef.Entity ->
        DataAddress(DataSpace.Entity.token, listOf(entityId.toString()))
    is WorldRef.EventFlag ->
        DataAddress(DataSpace.Flag.token, listOf("$flagId" + (seenIn?.let { "$SEEN_IN$it" } ?: "")))
    is WorldRef.ScriptEvent ->
        DataAddress(DataSpace.Script.token, listOf(script.token, EVENT, eventId.toString()))
    is WorldRef.ParamRow ->
        DataAddress(DataSpace.Param.token, listOf(file, rowId.toString()))
}

public fun WorldRef.toAddress(): String = toDataAddress().toAddress()

public fun DataAddress.toWorldRef(): WorldRef? = when (knownSpace) {
    DataSpace.Map -> mapRef()
    DataSpace.Entity -> onlySegment()?.toIdOrNull()?.let { WorldRef.Entity(it) }
    DataSpace.Flag -> onlySegment()?.let(::flagRef)
    DataSpace.Script -> scriptEventRef()
    DataSpace.Param -> paramRowRef()
    else -> null
}

private fun DataAddress.mapRef(): WorldRef? {
    val map = MapId.parse(segments.firstOrNull() ?: return null) ?: return null

    return when (segments.size) {
        1 -> WorldRef.Map(map)
        3 -> {
            val kind = entryKindOf(segments[1]) ?: return null
            val index = segments[2].toIdOrNull() ?: return null
            WorldRef.Entry(map, kind, index)
        }
        else -> null
    }
}

private fun DataAddress.scriptEventRef(): WorldRef? {
    if (segments.size != 3 || segments[1] != EVENT) return null
    val script = scriptIdOf(segments[0]) ?: return null
    val eventId = segments[2].toLongOrNull()?.takeIf { it >= 0 } ?: return null
    return WorldRef.ScriptEvent(script, eventId)
}

private fun DataAddress.paramRowRef(): WorldRef? {
    val (file, row) = segments.takeIf { it.size == 2 } ?: return null
    return row.toIntOrNull()?.let { WorldRef.ParamRow(file, it) }
}

private fun DataAddress.onlySegment(): String? = segments.singleOrNull()

private fun flagRef(token: String): WorldRef? {
    val at = token.indexOf(SEEN_IN)
    if (at < 0) return token.toIdOrNull()?.let { WorldRef.EventFlag(it) }

    val flagId = token.substring(0, at).toIdOrNull() ?: return null
    val seenIn = MapId.parse(token.substring(at + 1)) ?: return null
    return WorldRef.EventFlag(flagId, seenIn)
}


private val EntryKind.token: String get() = name.lowercase()

private fun entryKindOf(token: String): EntryKind? = when (token) {
    "model" -> EntryKind.Model
    "event" -> EntryKind.Event
    "region" -> EntryKind.Region
    "part" -> EntryKind.Part
    else -> null
}

private val ScriptId.token: String
    get() = when (this) {
        ScriptId.Common -> "common"
        is ScriptId.Of -> map.toString()
    }

private fun scriptIdOf(token: String): ScriptId? =
    if (token == "common") ScriptId.Common else MapId.parse(token)?.let(ScriptId::Of)

private fun String.toIdOrNull(): Int? = toIntOrNull()?.takeIf { it >= 0 }
