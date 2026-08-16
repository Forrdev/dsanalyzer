package com.sappyoak.dsanalyzer.game.data.map

import com.sappyoak.dsanalyzer.game.data.MapId
import kotlinx.serialization.Serializable

@Serializable
data class MSBMap(
    val mapId: MapId,
    val parts: List<MSBPart>,
    val regions: List<MSBRegion>,
    val events: List<MSBEvent>
) {
    fun partByName(name: String): MSBPart? = parts.firstOrNull { it.name == name }
    fun partsByEntityId(id: Int): List<MSBPart> = parts.filter { it.entityId == id }
    fun regionsByEntityId(id: Int): List<MSBRegion> = regions.filter { it.entityId == id }
    fun eventsByName(name: String): List<MSBEvent> = events.filter { it.name == name }
    fun eventsByEntityId(id: Int): List<MSBEvent> = events.filter { it.entityId == id }

    inline fun <reified T : MSBPart> partsOfType(): List<T> = parts.filterIsInstance<T>()
    inline fun <reified T : MSBEvent> eventsOfType(): List<T> = events.filterIsInstance<T>()
}