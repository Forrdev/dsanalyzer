package com.sappyoak.dsanalyzer.overlay

import kotlin.math.abs
import kotlin.math.cos
import kotlin.math.sin

import com.sappyoak.dsanalyzer.runtime.session.collision.CollisionBody
import com.sappyoak.dsanalyzer.runtime.session.collision.CollisionGeometry
import com.sappyoak.dsanalyzer.runtime.session.collision.CollisionMesh
import com.sappyoak.dsanalyzer.shared.math.Vec3

private const val BOX_CORNERS = 8
private const val TAU: Float = (2.0 * Math.PI).toFloat()

/** Below three a ring is not a ring, so the shape would be a claim rather than an approximation */
internal const val MIN_SEGMENTS: Int = 3

/** Triangles per segment: two for the wall, one for each cap */
private const val PER_SEGMENT = 4

internal class CollisionTessellation {
    private val cache = HashMap<CollisionGeometry, CollisionMesh>()
    private var bodies: List<CollisionBody>? = null
    private var segments: Int = 0

    /**
     * The triangles for [geometry], or null when it has none to give.
     *
     * [owner] is the list the geometry came from, and is compared by identity: a new read of the
     * map produces a new list, and that is exactly when every cached tessellation stops being about
     * anything on screen.
     */
    fun meshOf(
        geometry: CollisionGeometry,
        owner: List<CollisionBody>,
        segments: Int
    ): CollisionMesh? {
        if (owner !== bodies || segments != this.segments) {
            cache.clear()
            bodies = owner
            this.segments = segments
        }

        return when (geometry) {
            is CollisionGeometry.Mesh -> geometry.mesh
            is CollisionGeometry.Hull -> geometry.mesh
            is CollisionGeometry.Box -> cache.getOrPut(geometry) { geometry.tessellated() }
            is CollisionGeometry.Cylinder ->
                cache.getOrPut(geometry) { geometry.tessellated(segments) }
            CollisionGeometry.Unread -> null
        }
    }
}

private fun CollisionGeometry.Box.tessellated(): CollisionMesh {
    val vertices = FloatArray(BOX_CORNERS * CollisionMesh.COMPONENTS)

    for (corner in 0 until BOX_CORNERS) {
        val point = Vec3(
            center.x + halfX.x.signed(corner, 1) + halfY.x.signed(corner, 2) + halfZ.x.signed(corner, 4),
            center.y + halfX.y.signed(corner, 1) + halfY.y.signed(corner, 2) + halfZ.y.signed(corner, 4),
            center.z + halfX.z.signed(corner, 1) + halfY.z.signed(corner, 2) + halfZ.z.signed(corner, 4)
        )
        val at = corner * CollisionMesh.COMPONENTS
        vertices[at] = point.x
        vertices[at + 1] = point.y
        vertices[at + 2] = point.z
    }

    return CollisionMesh(vertices, BOX_TRIANGLES)
}

internal fun CollisionGeometry.Cylinder.tessellated(segments: Int): CollisionMesh {
    val length = a.distanceTo(b)
    if (length <= 0f || segments < MIN_SEGMENTS) return CollisionMesh.Empty

    val along = Vec3((b.x - a.x) / length, (b.y - a.y) / length, (b.z - a.z) / length)
    val u = along.anyPerpendicular()
    val v = along.cross(u)

    // Two rings, then one centre per cap
    val vertices = FloatArray((segments * 2 + 2) * CollisionMesh.COMPONENTS)
    for (step in 0 until segments) {
        val angle = TAU * step / segments
        val around = Vec3(
            (u.x * cos(angle) + v.x * sin(angle)) * cylRadius,
            (u.y * cos(angle) + v.y * sin(angle)) * cylRadius,
            (u.z * cos(angle) + v.z * sin(angle)) * cylRadius
        )
        vertices.put(step, a.x + around.x, a.y + around.y, a.z + around.z)
        vertices.put(segments + step, b.x + around.x, b.y + around.y, b.z + around.z)
    }
    vertices.put(segments * 2, a.x, a.y, a.z)
    vertices.put(segments * 2 + 1, b.x, b.y, b.z)

    return CollisionMesh(vertices, cylinderIndices(segments))
}

private fun cylinderIndices(segments: Int): IntArray {
    val indices = IntArray(segments * PER_SEGMENT * CollisionMesh.CORNERS)
    val capA = segments * 2
    val capB = capA + 1
    var at = 0

    for (step in 0 until segments) {
        val next = (step + 1) % segments
        at = indices.triangle(at, step, segments + step, segments + next)
        at = indices.triangle(at, step, segments + next, next)
        at = indices.triangle(at, capA, next, step)
        at = indices.triangle(at, capB, segments + step, segments + next)
    }
    return indices
}

/**
 * Corner indices, bit 0 being the `halfX` sign, bit 1 `halfY` and bit 2 `halfZ`. Six faces, each
 * split into two triangles, every one wound counter-clockwise seen from outside.
 */
private val BOX_TRIANGLES = intArrayOf(
    0, 2, 3, 0, 3, 1, // -z
    4, 5, 7, 4, 7, 6, // +z
    0, 1, 5, 0, 5, 4, // -y
    2, 6, 7, 2, 7, 3, // +y
    0, 4, 6, 0, 6, 2, // -x
    1, 3, 7, 1, 7, 5 // +x
)

private fun IntArray.triangle(at: Int, i: Int, j: Int, k: Int): Int {
    this[at] = i
    this[at + 1] = j
    this[at + 2] = k
    return at + CollisionMesh.CORNERS
}

private fun FloatArray.put(index: Int, x: Float, y: Float, z: Float) {
    val at = index * CollisionMesh.COMPONENTS
    this[at] = x
    this[at + 1] = y
    this[at + 2] = z
}

private fun Float.signed(corner: Int, bit: Int): Float = if (corner and bit != 0) this else -this

private fun Vec3.anyPerpendicular(): Vec3 {
    val away = when {
        abs(x) < abs(y) && abs(x) < abs(z) -> Vec3(1f, 0f, 0f)
        abs(y) < abs(z) -> Vec3(0f, 1f, 0f)
        else -> Vec3(0f, 0f, 1f)
    }

    val perpendicular = cross(away)
    val length = perpendicular.distanceTo(Vec3.Zero)
    return Vec3(perpendicular.x / length, perpendicular.y / length, perpendicular.z / length)
}