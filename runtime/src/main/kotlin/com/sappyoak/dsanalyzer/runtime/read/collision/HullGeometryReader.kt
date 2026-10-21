package com.sappyoak.dsanalyzer.runtime.read.collision

import com.sappyoak.dsanalyzer.native.memory.Address
import com.sappyoak.dsanalyzer.native.memory.MemoryView
import com.sappyoak.dsanalyzer.native.process.ProcessMemory
import com.sappyoak.dsanalyzer.runtime.pointers.StructView
import com.sappyoak.dsanalyzer.runtime.ptde.CONNECTIVITY_SIZE
import com.sappyoak.dsanalyzer.runtime.ptde.ConvexShape
import com.sappyoak.dsanalyzer.runtime.ptde.FOUR_VECTORS_SIZE
import com.sappyoak.dsanalyzer.runtime.ptde.HULL_SIZE
import com.sappyoak.dsanalyzer.runtime.ptde.HullConnectivity
import com.sappyoak.dsanalyzer.runtime.ptde.HullShape
import com.sappyoak.dsanalyzer.runtime.ptde.POINTS_PER_BLOCK
import com.sappyoak.dsanalyzer.runtime.session.collision.CollisionGeometry
import com.sappyoak.dsanalyzer.runtime.session.collision.CollisionMesh
import com.sappyoak.dsanalyzer.runtime.session.collision.place
import com.sappyoak.dsanalyzer.shared.math.Mat4

/** An `hkArray`'s length sits one word past its data pointer */
private const val ARRAY_LENGTH: Int = 4

private const val VERTEX_LIMIT: Int = 4096

/** Bytes of transposed vertex blocks, face indices or face sizes this will read in one go */
private const val BUFFER_CAPACITY: Int = 1 shl 18

internal class HullGeometryReader {
    private val hull = StructView(HULL_SIZE)
    private val connectivity = StructView(CONNECTIVITY_SIZE)
    private val points = MemoryView(BUFFER_CAPACITY)
    private val faces = MemoryView(BUFFER_CAPACITY)
    private val sizes = MemoryView(BUFFER_CAPACITY)

    fun read(memory: ProcessMemory, shapeAt: Address, transform: Mat4): CollisionGeometry {
        if (!hull.refresh(memory, shapeAt)) return CollisionGeometry.Unread

        val vertices = verticesOf(memory, transform) ?: return CollisionGeometry.Unread
        val indices = indicesOf(memory, vertices.size / CollisionMesh.COMPONENTS)
            ?: return CollisionGeometry.Unread

        return CollisionGeometry.Hull(
            mesh = CollisionMesh(vertices, indices),
            radius = hull.float(ConvexShape.Radius)
        )
    }

    private fun verticesOf(memory: ProcessMemory, transform: Mat4): FloatArray? {
        val count = hull.int(HullShape.VertexCount)
        if (count <= 0 || count > VERTEX_LIMIT) return null

        val at = hull.pointer(HullShape.RotatedVertices)
        // The array's own length counts blocks, not points, so it is the bound on the read while
        // `numVertices` is the bound on the decode. A block short of what the count claims would
        // otherwise be read past the end of the buffer
        val blocks = hull.int(HullShape.RotatedVertices + ARRAY_LENGTH)
        if (at.isNull || blocks <= 0) return null

        // Long because a block count read out of the wrong memory overflows an Int and
        // comes back negative, which passes every bound below and then fails the read
        if (blocks.toLong() * POINTS_PER_BLOCK < count) return null

        val bytes = blocks.toLong() * FOUR_VECTORS_SIZE
        if (bytes > BUFFER_CAPACITY || !points.refresh(memory, at, bytes.toInt())) return null

        val vertices = FloatArray(count * CollisionMesh.COMPONENTS)
        for (i in 0 until count) {
            val lane = (i % POINTS_PER_BLOCK) * COMPONENT_BYTES
            val block = (i / POINTS_PER_BLOCK) * FOUR_VECTORS_SIZE
            val placed = transform.place(
                points.float(block + lane),
                points.float(block + LANE_STRIDE + lane),
                points.float(block + LANE_STRIDE * 2 + lane)
            )
            val to = i * CollisionMesh.COMPONENTS
            vertices[to] = placed.x
            vertices[to + 1] = placed.y
            vertices[to + 2] = placed.z
        }
        return vertices
    }

    private fun indicesOf(memory: ProcessMemory, vertexCount: Int): IntArray? {
        val at = hull.pointer(HullShape.Connectivity)
        if (at.isNull || !connectivity.refresh(memory, at)) return null

        val indicesAt = connectivity.pointer(HullConnectivity.VertexIndices)
        val indexCount = connectivity.int(HullConnectivity.VertexIndices + ARRAY_LENGTH)
        val sizesAt = connectivity.pointer(HullConnectivity.FaceSizes)
        val faceCount = connectivity.int(HullConnectivity.FaceSizes + ARRAY_LENGTH)

        if (indicesAt.isNull || sizesAt.isNull) return null
        if (indexCount <= 0 || faceCount <= 0) return null
        if (indexCount.toLong() * INDEX_BYTES > BUFFER_CAPACITY || faceCount > BUFFER_CAPACITY) return null
        if (!faces.refresh(memory, indicesAt, indexCount * INDEX_BYTES)) return null
        if (!sizes.refresh(memory, sizesAt, faceCount)) return null

        val triangles = ArrayList<Int>(indexCount * CollisionMesh.CORNERS)
        var cursor = 0

        for (face in 0 until faceCount) {
            val corners = sizes.byte(face).toInt() and 0xFF
            // A face that runs past the index array means the two arrays disagree, which is a sign
            // the layout is wrong rather than that this one face is odd. Stop rather than salvage
            if (corners < CollisionMesh.CORNERS || cursor + corners > indexCount) break

            for (corner in 1 until corners - 1) {
                triangles += cornerAt(cursor, 0, vertexCount) ?: return null
                triangles += cornerAt(cursor, corner, vertexCount) ?: return null
                triangles += cornerAt(cursor, corner + 1, vertexCount) ?: return null
            }
            cursor += corners
        }

        return if (triangles.isEmpty()) null else triangles.toIntArray()
    }

    private fun cornerAt(cursor: Int, corner: Int, vertexCount: Int): Int? {
        val index = faces.short((cursor + corner) * INDEX_BYTES).toInt() and 0xFFFF
        return if (index < vertexCount) index else null
    }

    private companion object {
        const val COMPONENT_BYTES = 4
        const val LANE_STRIDE = COMPONENT_BYTES * POINTS_PER_BLOCK

        const val INDEX_BYTES = 2
    }
}
