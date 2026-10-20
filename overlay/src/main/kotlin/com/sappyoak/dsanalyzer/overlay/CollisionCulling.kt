package com.sappyoak.dsanalyzer.overlay

import com.sappyoak.dsanalyzer.runtime.session.collision.CollisionBody
import com.sappyoak.dsanalyzer.runtime.session.collision.CollisionGeometry
import com.sappyoak.dsanalyzer.runtime.session.collision.ShapeKind
import com.sappyoak.dsanalyzer.shared.math.Vec3

internal fun List<CollisionBody>.nearest(
    eye: Vec3,
    radius: Float,
    kinds: Set<ShapeKind>,
    filters: Set<Int> = emptySet()
): List<CollisionBody> {
    if (radius <= 0f) return emptyList()
    val limit = radius * radius

    return asSequence()
        .filter { it.kind in kinds && (filters.isEmpty() || it.filterInfo in filters) }
        .filter { it.geometry !is CollisionGeometry.Unread && !it.bounds.isEmpty }
        .map { body -> body to body.bounds.distanceSquaredTo(eye) }
        .filter { (_, distance) -> distance <= limit }
        .sortedBy { (_, distance) -> distance }
        .map { (body, _) -> body }
        .toList()
}