package com.sappyoak.dsanalyzer.runtime.session.collision

import kotlin.math.abs

import com.sappyoak.dsanalyzer.shared.math.Aabb
import com.sappyoak.dsanalyzer.shared.math.Vec3

public sealed interface CollisionGeometry {
    public val triangleCount: Int

    public data class Mesh(public val mesh: CollisionMesh) : CollisionGeometry {
        override val triangleCount: Int
            get() = mesh.triangleCount
    }

    public data class Box(
        public val center: Vec3,
        public val halfX: Vec3,
        public val halfY: Vec3,
        public val halfZ: Vec3,
        public val radius: Float
    ) : CollisionGeometry {
        override val triangleCount: Int = 0

        public val bounds: Aabb get() = Aabb(
            center = center,
            halfExtents = Vec3(
                abs(halfX.x) + abs(halfY.x) + abs(halfZ.x) + radius,
                abs(halfX.y) + abs(halfY.y) + abs(halfZ.y) + radius,
                abs(halfX.z) + abs(halfY.z) + abs(halfZ.z) + radius
            )
        )
    }

    public data class Cylinder(
        public val a: Vec3,
        public val b: Vec3,
        public val cylRadius: Float,
        public val radius: Float
    ) : CollisionGeometry {
        override val triangleCount: Int get() = 0

        public val bounds: Aabb get() = Aabb.between(a, b).expandedBy(cylRadius + radius)
    }

    public data class Hull(
        public val mesh: CollisionMesh,
        public val radius: Float
    ) : CollisionGeometry {
        override val triangleCount: Int get() = mesh.triangleCount

        public val bounds: Aabb get() = mesh.fitted().expandedBy(radius)
    }

    public data object Unread : CollisionGeometry {
        override val triangleCount: Int get() = 0
    }
}

private fun CollisionMesh.fitted(): Aabb {
    if (vertexCount == 0) return Aabb.Empty

    var min = vertex(0)
    var max = min

    for (i in 1 until vertexCount) {
        val point = vertex(i)
        min = Vec3(minOf(min.x, point.x), minOf(min.y, point.y), minOf(min.z, point.z))
        max = Vec3(maxOf(max.x, point.x), maxOf(max.y, point.y), maxOf(max.z, point.z))
    }

    return Aabb.between(min, max)
}