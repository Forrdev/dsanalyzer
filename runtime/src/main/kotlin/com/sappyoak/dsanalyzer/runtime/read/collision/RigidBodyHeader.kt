package com.sappyoak.dsanalyzer.runtime.read.collision

import com.sappyoak.dsanalyzer.runtime.session.collision.CollisionBody
import com.sappyoak.dsanalyzer.runtime.session.collision.CollisionGeometry
import com.sappyoak.dsanalyzer.runtime.session.collision.ShapeKind
import com.sappyoak.dsanalyzer.shared.math.Aabb
import com.sappyoak.dsanalyzer.shared.math.Mat4

internal data class RigidBodyHeader(
    val label: String,
    val filterInfo: Int,
    val allowedPenetration: Float,
    val transform: Mat4
) {
    fun describing(kind: ShapeKind?, geometry: CollisionGeometry): CollisionBody =
        CollisionBody(
            name = label,
            kind = kind,
            filterInfo = filterInfo,
            allowedPenetration = allowedPenetration,
            bounds = when (geometry) {
                is CollisionGeometry.Box -> geometry.bounds
                is CollisionGeometry.Cylinder -> geometry.bounds
                is CollisionGeometry.Hull -> geometry.bounds
                is CollisionGeometry.Mesh, CollisionGeometry.Unread -> Aabb.Companion.Empty
            },
            geometry = geometry
        )
}