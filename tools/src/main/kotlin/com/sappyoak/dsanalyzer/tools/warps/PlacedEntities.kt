package com.sappyoak.dsanalyzer.tools.warps

import com.sappyoak.dsanalyzer.formats.msb.MSB
import com.sappyoak.dsanalyzer.formats.msb.RegionIndex
import com.sappyoak.dsanalyzer.formats.msb.event.Event
import com.sappyoak.dsanalyzer.formats.msb.part.Part
import com.sappyoak.dsanalyzer.game.world.maps.MapId
import com.sappyoak.dsanalyzer.shared.math.Vec3

/**
 * Everything in the map that carries an entity id: parts, regions **and events**.
 *
 * Events were missing from the first three runs of this tool, and that omission is the whole
 * reason two known warp targets looked undeclared — `1,600,980` from the jump table and
 * `1,000,980` captured live, both of which the engine demonstrably uses. `Event.SpawnPoint` is an
 * MSB event type and `EventHeader` carries an `entityId`, so an arrival point declared that way
 * was invisible to a sweep that only read parts and regions.
 *
 * A list rather than a map, so a map declaring the same id twice stays visible instead of being
 * collapsed by whichever entry was read last.
 */
internal fun MSB.placed(map: MapId): List<PlacedEntity> {
    val fromParts = parts.mapNotNull { part ->
        val id = part.header.entityId.declared() ?: return@mapNotNull null
        placedAt(map, id, part::class.simpleName ?: "Part", part.header.name, part.header.position)
    }

    val fromRegions = regions.mapNotNull { region ->
        val id = region.entityId.declared() ?: return@mapNotNull null
        placedAt(map, id, "Region", region.name, region.position)
    }

    val fromEvents = events.mapNotNull { event ->
        val id = event.header.entityId.declared() ?: return@mapNotNull null
        val kind = "Event." + (event::class.simpleName ?: "Event")
        placedAt(map, id, kind, event.header.name, anchorOf(event))
    }

    return fromParts + fromRegions + fromEvents
}

/**
 * An entity id only when it names something.
 *
 * Zero is not an entity, and the MSB does not use it consistently: m10_00 alone has 42
 * `Event.Light` and 10 `Event.Sfx` entries carrying `0` where other maps leave the field null.
 * Treating those as real ids put fifty-odd entries into one duplicate bucket and drowned the
 * three duplicates actually worth looking at.
 */
private fun Int?.declared(): Int? = this?.takeIf { it != 0 }

private fun placedAt(map: MapId, id: Int, kind: String, name: String, at: Vec3?) = PlacedEntity(
    map = map.name,
    entityId = id,
    entity = id % 10000,
    kind = kind,
    name = name,
    x = at?.x,
    y = at?.y,
    z = at?.z
)

/**
 * Where an event effectively is.
 *
 * Events hold no coordinates; they point at a region or a part. `SpawnPoint` and `Navmesh` carry a
 * region of their own in addition to the one on the header, and the subtype's is the more specific,
 * so it wins. An event referencing neither has no position, which is why [PlacedEntity] allows it.
 */
private fun MSB.anchorOf(event: Event): Vec3? {
    val region = when (event) {
        is Event.SpawnPoint -> event.region ?: event.header.region
        is Event.Navmesh -> event.region ?: event.header.region
        else -> event.header.region
    }

    return region?.let { positionOf(it) } ?: event.header.part?.let { this[it].header.position }
}

private fun MSB.positionOf(region: RegionIndex): Vec3 = this[region].position

/**
 * Ids this map claims more than once, named by the kinds that claim them.
 *
 * The kinds are the useful part: a `DummyEnemy`/`Enemy` pair is the game's own authoring habit and
 * carries no information, while `Player` beside `Region` — which m11_00 does at 1102980 — is worth
 * a second look. An earlier version printed bare ids and so buried the three interesting ones in
 * the twenty-eight dull ones.
 */
internal fun List<PlacedEntity>.duplicatesIn(map: MapId): List<DuplicateEntity> =
    groupBy { it.entityId }
        .filterValues { it.size > 1 }
        .map { (id, claims) -> DuplicateEntity(map.name, id, claims.map { it.kind }.sorted()) }

internal fun MSB.playerParts(map: MapId): List<PlayerPart> =
    parts.filterIsInstance<Part.Player>().map { part ->
        PlayerPart(
            map = map.name,
            entityId = part.header.entityId,
            name = part.header.name,
            x = part.header.position.x,
            y = part.header.position.y,
            z = part.header.position.z
        )
    }
