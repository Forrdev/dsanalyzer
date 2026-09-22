package com.sappyoak.dsanalyzer.app.maps

import com.sappyoak.dsanalyzer.formats.msb.MSB
import com.sappyoak.dsanalyzer.formats.msb.event.Event
import com.sappyoak.dsanalyzer.formats.msb.part.Part
import com.sappyoak.dsanalyzer.formats.msb.region.Region
import com.sappyoak.dsanalyzer.formats.msb.region.Shape
import com.sappyoak.dsanalyzer.game.world.EntryKind
import com.sappyoak.dsanalyzer.game.world.WorldRef
import com.sappyoak.dsanalyzer.game.world.maps.MapId

/** One row in a map's entry list */
public data class EntrySummary(
    public val ref: WorldRef.Entry,
    public val name: String,
    public val subtype: String,
    public val entityId: Int?
) {
    public val kind: EntryKind get() = ref.kind
}

/**
 * A parsed map with its entries flattened into rows
 *
 * Some references count through one subtype rather than a whole param, so where each collision
 * event sits in the full list is worked out once here rather than searched for every time one is followed
 */
public class MapContents(
    public val map: MapId,
    public val msb: MSB,
    public val entries: List<EntrySummary>,
    private val collisionIndices: List<Int>
) {
    public fun collisionPart(index: Int): Int? = collisionIndices.getOrNull(index)
}


public fun MSB.summarize(map: MapId): MapContents {
    val entries = models.mapIndexed { index, model ->
        summary(map, EntryKind.Model, index, model.name, model.type.name, null)
    } + events.mapIndexed { index, event ->
        summary(map, EntryKind.Event, index, event.header.name, event.subtype(), event.header.entityId)
    } + regions.mapIndexed { index, region ->
        summary(map, EntryKind.Region, index, region.name, region.subtype(), region.entityId)
    } + parts.mapIndexed { index, part ->
        summary(map, EntryKind.Part, index, part.header.name, part.subtype(), part.header.entityId)
    }

    return MapContents(
        map = map,
        msb = this,
        entries = entries,
        collisionIndices = parts.indicesOf<Part.Collision>()
    )
}

private fun summary(
    map: MapId,
    kind: EntryKind,
    index: Int,
    name: String,
    subtype: String,
    entityId: Int?
): EntrySummary = EntrySummary(WorldRef.Entry(map, kind, index), name, subtype, entityId)

internal fun Part.subtype(): String = this::class.simpleName.orEmpty()
internal fun Event.subtype(): String = this::class.simpleName.orEmpty()

internal fun Region.subtype(): String = when (shape) {
    is Shape.Point -> "Point"
    is Shape.Circle -> "Circle"
    is Shape.Sphere -> "Sphere"
    is Shape.Cylinder -> "Cylinder"
    is Shape.Rectangle -> "Rectangle"
    is Shape.Box -> "Box"
}

private inline fun <reified T> List<*>.indicesOf(): List<Int> =
    mapIndexedNotNull { index, entry -> index.takeIf { entry is T  } }