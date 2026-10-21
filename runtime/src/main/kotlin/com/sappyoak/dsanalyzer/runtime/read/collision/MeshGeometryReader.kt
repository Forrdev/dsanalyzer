package com.sappyoak.dsanalyzer.runtime.read.collision

import com.sappyoak.dsanalyzer.native.memory.Address
import com.sappyoak.dsanalyzer.native.memory.MemoryView
import com.sappyoak.dsanalyzer.native.process.ProcessMemory
import com.sappyoak.dsanalyzer.runtime.pointers.StructView
import com.sappyoak.dsanalyzer.runtime.pointers.TableView
import com.sappyoak.dsanalyzer.runtime.ptde.MeshShape
import com.sappyoak.dsanalyzer.runtime.ptde.SUBPART_SIZE
import com.sappyoak.dsanalyzer.runtime.ptde.Subpart
import com.sappyoak.dsanalyzer.runtime.session.collision.CollisionMesh
import com.sappyoak.dsanalyzer.runtime.session.collision.place
import com.sappyoak.dsanalyzer.shared.math.Mat4

/** The most bytes one subpart's vertices or indices may occupy */
private const val BUFFER_CAPACITY: Int = 1 shl 20

/** At or above this many bytes per triangle, indices are 32-bit rather than 16-bit */
private const val WIDE_INDEX_STRIDE: Int = 12

private const val MESH_VERTEX_LIMIT: Long = 1L shl 20
private const val MESH_TRIANGLE_LIMIT: Long = 1L shl 21

private class SubpartHeader(
    val vertexBase: Address,
    val vertexCount: Int,
    val vertexStride: Int,
    val indexBase: Address,
    val triangleCount: Int,
    val indexStride: Int
) {
    val vertexBytes: Long get() = vertexCount.toLong() * vertexStride
    val indexBytes: Long get() = triangleCount.toLong() * indexStride
}

/**
 * Reads a 'CustomParamStorageExtendedMeshShapes triangles
 *
 * Every subpart header is read first, so the totals are known, then one exactly-sized
 * [FloatArray] and [IntArray] are allocated and filled
 *
 * Subparts are inline elements, so resolving their addressses costs no reads at all, only their contents
 * are fetched
 */
internal class MeshGeometryReader {
    private val subparts = TableView(MeshShape.Subparts)
    private val subpart = StructView(SUBPART_SIZE)
    private val buffer = MemoryView(BUFFER_CAPACITY)

    fun read(memory: ProcessMemory, meshAddress: Address, transform: Mat4): CollisionMesh? {
        if (!subparts.refresh(memory, meshAddress)) return null

        val headers = subparts.elements.mapNotNull { headerAt(memory, it) }
        if (headers.isEmpty()) return null

        val vertexCount = headers.sumOf { it.vertexCount.toLong() }
        val triangleCount = headers.sumOf { it.triangleCount.toLong() }

        val vertices = FloatArray(vertexCount.toInt() * CollisionMesh.COMPONENTS)
        val indices = IntArray(triangleCount.toInt() * CollisionMesh.CORNERS)

        var vertexCursor = 0
        var indexCursor = 0
        var vertexBase = 0

        for (header in headers) {
            if (!readVertices(memory, header, vertices, vertexCursor, transform)) return null
            if (!readIndices(memory, header, indices, indexCursor, vertexBase)) return null

            vertexCursor += header.vertexCount * CollisionMesh.COMPONENTS
            indexCursor += header.triangleCount * CollisionMesh.CORNERS
            // Each subpart indexes its own vertices from zero, so every subpart after the first
            // has to be rebased into the combined array. Getting this wrong draws a recognisable
            // mesh made of triangles joining the wrong corners
            vertexBase += header.vertexCount
        }

        return CollisionMesh(vertices, indices)
    }

    private fun headerAt(memory: ProcessMemory, at: Address): SubpartHeader? {
        if (!subpart.refresh(memory, at)) return null

        val header = SubpartHeader(
            vertexBase = subpart.pointer(Subpart.VertexBase),
            vertexCount = subpart.int(Subpart.VertexCount),
            vertexStride = subpart.short(Subpart.VertexStriding).toInt() and 0xFFFF,
            indexBase = subpart.pointer(Subpart.IndexBase),
            triangleCount = subpart.int(Subpart.TriangleCount),
            indexStride = subpart.short(Subpart.IndexStriding).toInt() and 0xFFFF
        )

        // The embedded subpart at the mesh's `+0x20` has a null vertex base on every body sampled,
        // and a zero stride would divide by nothing later. Both are "not a subpart to read" rather
        // than failures, so they drop out instead of poisoning the whole mesh
        if (header.vertexBase.isNull || header.indexBase.isNull) return null
        if (header.vertexCount <= 0 || header.triangleCount <= 0) return null
        if (header.vertexStride < VERTEX_COMPONENT_BYTES * CollisionMesh.COMPONENTS) return null
        if (header.indexStride <= 0) return null
        if (header.vertexBytes > BUFFER_CAPACITY || header.indexBytes > BUFFER_CAPACITY) return null

        return header
    }

    private fun readVertices(
        memory: ProcessMemory,
        header: SubpartHeader,
        into: FloatArray,
        cursor: Int,
        transform: Mat4
    ): Boolean {
        if (!buffer.refresh(memory, header.vertexBase, header.vertexBytes.toInt())) return false

        for (i in 0 until header.vertexCount) {
            val from = i * header.vertexStride
            val to = cursor + i * CollisionMesh.COMPONENTS
            // The game's stride is 16 — three floats and a pad — and the pad is dropped here
            val placed = transform.place(
                buffer.float(from),
                buffer.float(from + VERTEX_COMPONENT_BYTES),
                buffer.float(from + VERTEX_COMPONENT_BYTES * 2)
            )
            into[to] = placed.x
            into[to + 1] = placed.y
            into[to + 2] = placed.z
        }
        return true
    }

    private fun readIndices(
        memory: ProcessMemory,
        header: SubpartHeader,
        into: IntArray,
        cursor: Int,
        vertexBase: Int
    ): Boolean {
        if (!buffer.refresh(memory, header.indexBase, header.indexBytes.toInt())) return false

        val width = if (header.indexStride >= WIDE_INDEX_STRIDE) 4 else 2

        for (triangle in 0 until header.triangleCount) {
            val from = triangle * header.indexStride
            val to = cursor + triangle * CollisionMesh.CORNERS
            for (corner in 0 until CollisionMesh.CORNERS) {
                val at = from + corner * width
                val index = if (width == 4) buffer.int(at) else buffer.short(at).toInt() and 0xFFFF
                into[to + corner] = vertexBase + index
            }
        }
        return true
    }
    private companion object {
        const val VERTEX_COMPONENT_BYTES = 4
    }
}
