package com.sappyoak.dsanalyzer.formats.msb

import com.sappyoak.dsanalyzer.formats.msb.event.Event
import com.sappyoak.dsanalyzer.formats.msb.model.Model
import com.sappyoak.dsanalyzer.formats.msb.part.Part
import com.sappyoak.dsanalyzer.formats.msb.region.Region

/**
 * A map's layout, the models it uses, its regions, and the parts placed in it.
 *
 * Entries refer to each other by index rather than by name because names repeat within a map.
 */
public class MSB(
    public val models: List<Model>,
    public val events: List<Event>,
    public val regions: List<Region>,
    public val parts: List<Part>
) {
    public val collisions: List<Part.Collision> get() = parts.filterIsInstance<Part.Collision>()

    public operator fun get(index: ModelIndex): Model = models[index.value]
    public operator fun get(index: PartIndex): Part = parts[index.value]
    public operator fun get(index: RegionIndex): Region = regions[index.value]
    public operator fun get(index: CollisionIndex): Part.Collision = collisions[index.value]

}

public inline fun <reified T : Event> MSB.eventsOf(): List<T> = events.filterIsInstance<T>()
public inline fun <reified T : Part> MSB.partsOf(): List<T> = parts.filterIsInstance<T>()

