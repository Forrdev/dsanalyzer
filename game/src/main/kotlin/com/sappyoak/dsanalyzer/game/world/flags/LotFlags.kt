package com.sappyoak.dsanalyzer.game.world.flags

import com.sappyoak.dsanalyzer.formats.msb.event.Event
import com.sappyoak.dsanalyzer.formats.msb.eventsOf
import com.sappyoak.dsanalyzer.formats.msb.part.Part
import com.sappyoak.dsanalyzer.formats.param.ParamRow
import com.sappyoak.dsanalyzer.formats.param.defs.ItemLotParamRow
import com.sappyoak.dsanalyzer.formats.param.defs.NpcParamRow
import com.sappyoak.dsanalyzer.formats.param.refs.item
import com.sappyoak.dsanalyzer.formats.param.refs.items
import com.sappyoak.dsanalyzer.game.ref.WorldRef
import com.sappyoak.dsanalyzer.game.world.maps.label
import com.sappyoak.dsanalyzer.game.world.params.GameParam
import com.sappyoak.dsanalyzer.game.world.text.TextCategory

/** Item lots and limited shop stock: each row carries the flag the game sets when it is taken */
internal val LotOrigin: FlagOrigin = FlagOrigin { it.lotFlags().withSites() }

private fun NamingContext.lotFlags(): List<Pair<Int, FlagEvidence>> {
    val places = lotPlaces()
    val lots = params[GameParam.ItemLotParam].rows.flatMap { lot ->
        val where = places[lot.id]?.let { " ($it)" }.orEmpty()
        lot.flags().map { (flagId, slot) ->
            val items = if (slot == null) lot.items.filterNotNull() else listOfNotNull(lot.items[slot])
            val label = "Picked up ${items.joinToString { item(it) }.ifEmpty { "lot ${lot.id}" }}$where"
            flagId namedBy FlagEvidence(FlagKind.ItemPickup, label, lot.source(GameParam.ItemLotParam), direct = true)
        }
    }
    val shops = params[GameParam.ShopLineupParam].rows.filter { it.eventFlag > 0 }.map { row ->
        val what = row.item?.let(::item) ?: "row ${row.id}"
        row.eventFlag namedBy FlagEvidence(FlagKind.ShopStock, "Bought $what", row.source(GameParam.ShopLineupParam), direct = true)
    }
    return lots + shops
}

/** The lot's own flag (slot null), then any per-slot flags */
private fun ItemLotParamRow.flags(): List<Pair<Int, Int?>> {
    val slots = listOf(
        getItemFlagId01, getItemFlagId02, getItemFlagId03, getItemFlagId04,
        getItemFlagId05, getItemFlagId06, getItemFlagId07, getItemFlagId08
    )
    val own = listOfNotNull(getItemFlagId.takeIf { it > 0 }?.let { it to null })
    return own + slots.withIndex().filter { (_, flag) -> flag > 0 }.map { (slot, flag) -> flag to slot }
}

/** Where each lot is found: a treasure placed in a map, or the enemy that drops it */
private fun NamingContext.lotPlaces(): Map<Int, String> {
    val treasures = maps.flatMap { (map, msb) ->
        msb.eventsOf<Event.Treasure>().flatMap { treasure ->
            val on = treasure.part?.let { msb[it].header.name }
            treasure.itemLots.filter { it > 0 }.map { lot -> lot to listOfNotNull(map.label, on).joinToString(" ") }
        }
    }
    val drops = params[GameParam.NpcParam].rows.flatMap { npc ->
        npc.lots().map { lot -> lot to "dropped by ${dropperOf(npc)}" }
    }
    return (drops + treasures).toMap()
}

private fun NpcParamRow.lots(): List<Int> =
    listOf(itemLotId_1, itemLotId_2, itemLotId_3, itemLotId_4, itemLotId_5, itemLotId_6).filter { it > 0 }

/** The row's NPC name, else the model of the first character placed with it, else the row id */
private fun NamingContext.dropperOf(npc: NpcParamRow): String {
    text(TextCategory.NpcNames, npc.nameId)?.let { return it }
    val model = maps.values.firstNotNullOfOrNull { msb ->
        msb.parts.firstOrNull { it is Part.Enemy && it.data.npcParamId == npc.id }?.header?.model?.let { msb[it].name }
    }
    return model?.let { models.describe(it) ?: it } ?: "NpcParam ${npc.id}"
}

internal fun ParamRow.source(param: GameParam<*>): WorldRef = WorldRef.ParamRow(param.fileName, id)
