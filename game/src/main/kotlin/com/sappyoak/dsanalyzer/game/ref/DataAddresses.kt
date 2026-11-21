package com.sappyoak.dsanalyzer.game.ref

import com.sappyoak.dsanalyzer.game.world.maps.EntryKind
import com.sappyoak.dsanalyzer.game.world.maps.MapId
import com.sappyoak.dsanalyzer.game.world.scripts.ScriptId

/** The segment between a script and an event id in a script address */
public const val EVENT_SEGMENT: String = "event"

/** Separates a flag's id from the map it was seen in */
public const val SEEN_IN: Char = '@'

private const val COMMON = "common"

/*
 * The addresses of the things the game data holds, built here so every producer spells them the same:
 * `ds:/map/m10_01_00_00`, `ds:/map/m10_01_00_00/part/12`, `ds:/entity/1010700`, `ds:/flag/11010100@m10_01_00_00`,
 * `ds:/script/common/event/50`, `ds:/param/EquipParamWeapon/100000`
 */

public fun DataAddress.Companion.map(map: MapId): DataAddress =
    DataAddress(DataSpace.Map.token, listOf(map.toString()))

public fun DataAddress.Companion.mapEntry(map: MapId, kind: EntryKind, index: Int): DataAddress =
    DataAddress(DataSpace.Map.token, listOf(map.toString(), kind.token, index.toString()))

public fun DataAddress.Companion.entity(entityId: Int): DataAddress =
    DataAddress(DataSpace.Entity.token, listOf(entityId.toString()))

public fun DataAddress.Companion.flag(flagId: Int, seenIn: MapId? = null): DataAddress =
    DataAddress(DataSpace.Flag.token, listOf("$flagId" + (seenIn?.let { "$SEEN_IN$it" } ?: "")))

public fun DataAddress.Companion.scriptEvent(script: ScriptId, eventId: Long): DataAddress =
    DataAddress(DataSpace.Script.token, listOf(script.token, EVENT_SEGMENT, eventId.toString()))

public fun DataAddress.Companion.paramRow(file: String, rowId: Int): DataAddress =
    DataAddress(DataSpace.Param.token, listOf(file, rowId.toString()))

public val EntryKind.token: String get() = name.lowercase()

public fun entryKindOf(token: String): EntryKind? = EntryKind.entries.firstOrNull { it.token == token }

public val ScriptId.token: String
    get() = when (this) {
        ScriptId.Common -> COMMON
        is ScriptId.Of -> map.toString()
    }

public fun scriptIdOf(token: String): ScriptId? =
    if (token == COMMON) ScriptId.Common else MapId.parse(token)?.let(ScriptId::Of)
