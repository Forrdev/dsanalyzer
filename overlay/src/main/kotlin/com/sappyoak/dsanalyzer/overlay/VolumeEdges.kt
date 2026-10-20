package com.sappyoak.dsanalyzer.overlay

import kotlin.math.roundToLong
import kotlin.math.sqrt

import com.sappyoak.dsanalyzer.runtime.session.collision.CollisionBody
import com.sappyoak.dsanalyzer.runtime.session.collision.CollisionGeometry
import com.sappyoak.dsanalyzer.runtime.session.collision.CollisionMesh

/** Floats per edge: two `x, y, z` endpoints */
internal const val EDGE_COMPONENTS: Int = 6

/** Faces whose normals agree this closely are one flat face, and the line between them a seam */
private const val COPLANAR: Float = 0.9998f

/** Corners closer than this are one corner, in world units: shared corners are often stored twice */
private const val WELD: Float = 1e-3f

/**
 * The outline of a volume: its feature edges, cached per geometry like [CollisionTessellation].
 *
 * A volume is drawn as lines, not surfaces, so only the edges that carry shape are worth drawing.
 * The seam splitting a flat face into two triangles is not one, and drawing it is what makes a box
 * read as a crumpled sheet
 */
internal class VolumeEdges {
    private val cache = HashMap<CollisionGeometry, FloatArray>()
    private var bodies: List<CollisionBody>? = null
    private var segments: Int = 0

    /** Edges for [geometry], whose triangles are [mesh]. [owner] is compared by identity, as there */
    fun edgesOf(
        geometry: CollisionGeometry,
        mesh: CollisionMesh,
        owner: List<CollisionBody>,
        segments: Int
    ): FloatArray {
        if (owner !== bodies || segments != this.segments) {
            cache.clear()
            bodies = owner
            this.segments = segments
        }
        return cache.getOrPut(geometry) { mesh.featureEdges() }
    }
}

/**
 * Every edge that is a boundary, a crease, or shared by more than two faces, as [EDGE_COMPONENTS]
 * floats each.
 *
 * Corners are welded by position before edges are matched: a mesh that stores a corner once per
 * triangle would otherwise have no shared edges at all, and every seam would survive as a boundary.
 */
internal fun CollisionMesh.featureEdges(): FloatArray {
    val canonical = weldedCorners()
    val faces = HashMap<Long, Edge>()

    val indices = indices
    var triangle = 0
    while (triangle + CollisionMesh.CORNERS <= indices.size) {
        val a = indices[triangle]
        val b = indices[triangle + 1]
        val c = indices[triangle + 2]
        triangle += CollisionMesh.CORNERS
        if (!inRange(a) || !inRange(b) || !inRange(c)) continue

        val normal = normalOf(a, b, c) ?: continue
        faces.note(canonical, a, b, normal)
        faces.note(canonical, b, c, normal)
        faces.note(canonical, c, a, normal)
    }

    val kept = faces.values.filter { it.isFeature }
    val out = FloatArray(kept.size * EDGE_COMPONENTS)
    kept.forEachIndexed { i, edge ->
        val at = i * EDGE_COMPONENTS
        copyVertex(edge.from, out, at)
        copyVertex(edge.to, out, at + 3)
    }
    return out
}

private class Edge(val from: Int, val to: Int, val normal: FloatArray) {
    var faces: Int = 1
    var creased: Boolean = false

    /** Not the seam inside one flat face: a boundary, a crease, or a non-manifold edge */
    val isFeature: Boolean get() = faces != 2 || creased
}

private fun HashMap<Long, Edge>.note(canonical: IntArray, from: Int, to: Int, normal: FloatArray) {
    val p = canonical[from]
    val q = canonical[to]
    if (p == q) return
    val key = (minOf(p, q).toLong() shl 32) or maxOf(p, q).toLong()

    val seen = this[key]
    if (seen == null) {
        this[key] = Edge(from, to, normal)
        return
    }
    seen.faces++
    val agreement =
        seen.normal[0] * normal[0] + seen.normal[1] * normal[1] + seen.normal[2] * normal[2]
    if (agreement < COPLANAR) seen.creased = true
}

private fun CollisionMesh.weldedCorners(): IntArray {
    val seen = HashMap<Triple<Long, Long, Long>, Int>()
    return IntArray(vertexCount) { i ->
        val at = i * CollisionMesh.COMPONENTS
        val key = Triple(
            (vertices[at] / WELD).roundToLong(),
            (vertices[at + 1] / WELD).roundToLong(),
            (vertices[at + 2] / WELD).roundToLong()
        )
        seen.getOrPut(key) { seen.size }
    }
}

/** The unit normal of a triangle, or null when it is degenerate and faces nowhere */
private fun CollisionMesh.normalOf(a: Int, b: Int, c: Int): FloatArray? {
    val pa = a * CollisionMesh.COMPONENTS
    val pb = b * CollisionMesh.COMPONENTS
    val pc = c * CollisionMesh.COMPONENTS
    val ux = vertices[pb] - vertices[pa]
    val uy = vertices[pb + 1] - vertices[pa + 1]
    val uz = vertices[pb + 2] - vertices[pa + 2]
    val vx = vertices[pc] - vertices[pa]
    val vy = vertices[pc + 1] - vertices[pa + 1]
    val vz = vertices[pc + 2] - vertices[pa + 2]

    val nx = uy * vz - uz * vy
    val ny = uz * vx - ux * vz
    val nz = ux * vy - uy * vx
    val length = sqrt(nx * nx + ny * ny + nz * nz)
    if (length <= 0f || length.isNaN()) return null
    return floatArrayOf(nx / length, ny / length, nz / length)
}

private fun CollisionMesh.inRange(index: Int): Boolean = index in 0 until vertexCount

private fun CollisionMesh.copyVertex(index: Int, out: FloatArray, at: Int) {
    val from = index * CollisionMesh.COMPONENTS
    out[at] = vertices[from]
    out[at + 1] = vertices[from + 1]
    out[at + 2] = vertices[from + 2]
}
