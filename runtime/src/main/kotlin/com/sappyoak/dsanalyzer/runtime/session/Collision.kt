package com.sappyoak.dsanalyzer.runtime.session

import com.sappyoak.dsanalyzer.runtime.ptde.ShapeKind
import com.sappyoak.dsanalyzer.shared.math.Aabb
import com.sappyoak.dsanalyzer.shared.math.Vec3

public class CollisionMesh(
    public val vertices: FloatArray,
    public val indices: IntArray
) {
    public val vertexCount: Int get() = vertices.size / COMPONENTS
    public val triangleCount: Int get() = indices.size / CORNERS

    public fun vertex(index: Int): Vec3 {
        val at = index * COMPONENTS
        return Vec3(vertices[at], vertices[at + 1], vertices[at + 2])
    }

    public companion object {
        public const val COMPONENTS: Int = 3
        public const val CORNERS: Int = 3

        public val Empty: CollisionMesh = CollisionMesh(FloatArray(0), IntArray(0))
    }
}

public data class CollisionBody(
    public val name: String,
    public val kind: ShapeKind?,
    public val bounds: Aabb,
    public val mesh: CollisionMesh?
)

public data class CollisionSnapshot(
    public val bodies: List<CollisionBody>,
    public val skipped: Int
) {
    public val triangleCount: Int get() = bodies.sumOf { it.mesh?.triangleCount ?: 0 }

    public companion object {
        public val Empty: CollisionSnapshot = CollisionSnapshot(emptyList(), 0)
    }
}