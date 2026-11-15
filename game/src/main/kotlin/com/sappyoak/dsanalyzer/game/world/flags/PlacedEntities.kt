package com.sappyoak.dsanalyzer.game.world.flags

import com.sappyoak.dsanalyzer.formats.msb.MSB
import com.sappyoak.dsanalyzer.formats.msb.part.Part
import com.sappyoak.dsanalyzer.game.ref.WorldRef
import com.sappyoak.dsanalyzer.game.world.maps.CharacterModels
import com.sappyoak.dsanalyzer.game.world.maps.EntryKind
import com.sappyoak.dsanalyzer.game.world.maps.MapId

/** Something a map places under an entity id, as a script names it */
public data class Placed(
    public val ref: WorldRef.Entry,
    /** The part or region's own name, such as `c2410_0001` */
    public val name: String,
    /** The character's name: its NPC name where it has one, else its model's */
    public val character: String?
) {
    public val label: String get() = character?.let { "$it ($name, ${ref.map})" } ?: "$name (${ref.map})"
}

/** Every entity id the maps place, across all of them */
public class PlacedEntities(private val byId: Map<Int, Placed>) {
    public operator fun get(entityId: Int): Placed? = byId[entityId]

    /** The entity's name, or its bare id where no map places it */
    public fun label(entityId: Int): String = byId[entityId]?.label ?: "entity $entityId"

    public companion object {
        /** [npcName] names a character by its NpcParam row, where the game's text gives it one */
        public fun of(maps: Map<MapId, MSB>, models: CharacterModels, npcName: (Int) -> String?): PlacedEntities =
            PlacedEntities(maps.flatMap { (map, msb) -> msb.placed(map, models, npcName) }.toMap())
    }
}

private fun MSB.placed(map: MapId, models: CharacterModels, npcName: (Int) -> String?): List<Pair<Int, Placed>> {
    val parts = parts.withIndex().mapNotNull { (index, part) ->
        val id = part.header.entityId ?: return@mapNotNull null
        val character = characterName(part, models, npcName)
        id to Placed(WorldRef.Entry(map, EntryKind.Part, index), part.header.name, character)
    }
    val regions = regions.withIndex().mapNotNull { (index, region) ->
        region.entityId?.let { it to Placed(WorldRef.Entry(map, EntryKind.Region, index), region.name, null) }
    }
    return parts + regions
}

private fun MSB.characterName(part: Part, models: CharacterModels, npcName: (Int) -> String?): String? {
    val data = when (part) {
        is Part.Enemy -> part.data
        is Part.DummyEnemy -> part.data
        else -> return null
    }
    return npcName(data.npcParamId) ?: part.header.model?.let { models.describe(this[it].name) }
}
