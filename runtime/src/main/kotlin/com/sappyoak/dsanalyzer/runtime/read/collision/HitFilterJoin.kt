package com.sappyoak.dsanalyzer.runtime.read.collision

import com.sappyoak.dsanalyzer.formats.msb.part.CollisionHitFilter
import com.sappyoak.dsanalyzer.runtime.session.collision.CollisionBody
import com.sappyoak.dsanalyzer.runtime.session.collision.HitFilter

/** `_0000`, the suffix that distinguishes sibling parts and that a rigid body's name drops */
private val SIBLING_SUFFIX = Regex("_\\d+$")

private val DEATH_PAIR = setOf(CollisionHitFilter.KillPlane, CollisionHitFilter.DeathCam)

internal fun hitFilterKeyOf(name: String): String =
    name.removePrefix("h").replace(SIBLING_SUFFIX, "")

internal fun hitFilterOf(ids: Set<Int>): HitFilter {
    val named = ids.mapNotNull { id -> BY_ID[id] }

    return when {
        ids.size > 1 -> HitFilter.Ambiguous(named.toSet())
        named.size == 1 -> HitFilter.Known(named.single())
        else -> HitFilter.Unnamed(ids.single())
    }
}

internal fun List<CollisionBody>.withHitFilters(filters: Map<String, HitFilter>): List<CollisionBody> =
    map { body -> body.copy(hitFilter = filters[body.name] ?: HitFilter.Absent) }

internal fun List<CollisionBody>.resolvingDeathVolumes(): List<CollisionBody> {
    val groups = withIndex()
        .filter { (_, body) -> (body.hitFilter as? HitFilter.Ambiguous)?.candidates == DEATH_PAIR }
        .groupBy { (_, body) -> body.name }
        .filterValues { it.size == 2 }

    if (groups.isEmpty()) return this

    val resolved = toMutableList()
    for (group in groups.values) {
        val ordered = group.sortedBy { (_, body) -> body.bounds.center.y }
        resolved[ordered.first().index] = ordered.first().value
            .copy(hitFilter = HitFilter.Known(CollisionHitFilter.KillPlane))
        resolved[ordered.last().index] = ordered.last().value
            .copy(hitFilter = HitFilter.Known(CollisionHitFilter.DeathCam))
    }
    return resolved
}

private val BY_ID: Map<Int, CollisionHitFilter> = CollisionHitFilter.entries.associateBy { it.id }
