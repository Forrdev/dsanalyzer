package com.sappyoak.dsanalyzer.formats.nvm

import com.sappyoak.dsanalyzer.shared.math.Vec3

/** No neighbor across an edge, the walkable surface ends there */
public const val OPEN_EDGE: Int = -1

/**
 * One '.nvm' navmesh, the surface the AI walks on, as triangles that know their neighbors
 */
public class Navmesh(
    public val vertices: FloatArray,
    public val triangles: List<NavTriangle>,
    public val boxes: NavBox
) {
    public val vertexCount: Int get() = vertices.size / 3

    public val openEdges: List<NavEdge>
        get() = triangles.flatMapIndexed { index, triangle ->
            (0 until 3).filter { triangle.neighbors[it] == OPEN_EDGE }.map { NavEdge(index, it) }
        }

    public fun vertex(index: Int): Vec3 = Vec3(
        vertices[index * 3],
        vertices[index * 3 + 1],
        vertices[index * 3 + 2]
    )
}

public class NavTriangle(
    public val corners: IntArray,
    public val neighbors: IntArray,
    public val flags: Int
)

public data class NavEdge(public val triangle: Int, public val slot: Int)

public class NavBox(
    public val min: Vec3,
    public val max: Vec3,
    public val children: List<NavBox>,
    public val triangles: IntArray
)
