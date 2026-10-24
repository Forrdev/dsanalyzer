package com.sappyoak.dsanalyzer.overlay

import com.sappyoak.dsanalyzer.runtime.session.collision.CollisionBody
import com.sappyoak.dsanalyzer.runtime.session.collision.CollisionGeometry
import com.sappyoak.dsanalyzer.runtime.session.collision.ShapeKind
import com.sappyoak.dsanalyzer.shared.math.Vec3

internal fun List<CollisionBody>.nearest(
    eye: Vec3,
    radius: Float,
    kinds: Set<ShapeKind>?,
    filters: Set<Int> = emptySet()
): List<CollisionBody> = ranked(eye, radius, kinds, filters).bodies

internal fun List<CollisionBody>.ranked(
    eye: Vec3,
    radius: Float,
    kinds: Set<ShapeKind>?,
    filters: Set<Int> = emptySet()
): RankedBodies {
    if (radius <= 0f) return RankedBodies.Empty
    val limit = radius * radius

    val near = asSequence()
        .filter { it.selectedBy(kinds, filters) }
        .filter { it.geometry !is CollisionGeometry.Unread && !it.bounds.isEmpty }
        .map { body -> body to body.bounds.distanceSquaredTo(eye) }
        .filter { (_, distance) -> distance <= limit }
        .sortedBy { (_, distance) -> distance }
        .toList()

    return RankedBodies(
        bodies = near.map { (body, _) -> body },
        distancesSquared = FloatArray(near.size) { near[it].second }
    )
}

/** Bodies, nearest first with their squared distances from the eye they were ranked against */
internal class RankedBodies(
    val bodies: List<CollisionBody>,
    private val distancesSquared: FloatArray
) {
    fun within(radius: Float, kinds: Set<ShapeKind>?, filters: Set<Int> = emptySet()): List<CollisionBody> {
        if (radius <= 0f) return emptyList()
        val limit = radius * radius

        val selected = ArrayList<CollisionBody>()
        for (i in bodies.indices) {
            if (distancesSquared[i] > limit) break
            if (bodies[i].selectedBy(kinds, filters)) selected.add(bodies[i])
        }
        return selected
    }

    companion object {
        val Empty = RankedBodies(emptyList(), FloatArray(0))
    }
}

private fun CollisionBody.selectedBy(kinds: Set<ShapeKind>?, filters: Set<Int>): Boolean =
    (kinds == null || kind in kinds) && (filters.isEmpty() || filterInfo in filters)
