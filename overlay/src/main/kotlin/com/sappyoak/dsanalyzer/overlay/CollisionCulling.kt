package com.sappyoak.dsanalyzer.overlay

import com.sappyoak.dsanalyzer.runtime.session.CollisionBody
import com.sappyoak.dsanalyzer.shared.math.Vec3

internal fun List<CollisionBody>.nearest(eye: Vec3, radius: Float): List<CollisionBody> {
    if (radius <= 0f) return emptyList()
    val limit = radius * radius

    return asSequence()
        .filter { it.mesh != null && !it.bounds.isEmpty }
        .map { body -> body to body.bounds.distanceSquaredTo(eye) }
        .filter { (_, distance) -> distance <= limit }
        .sortedBy { (_, distance) -> distance }
        .map { (body, _) -> body }
        .toList()
}