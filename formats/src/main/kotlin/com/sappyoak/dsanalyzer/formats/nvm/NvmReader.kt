package com.sappyoak.dsanalyzer.formats.nvm

import java.nio.ByteOrder

import com.sappyoak.dsanalyzer.shared.binary.BinaryFormatException
import com.sappyoak.dsanalyzer.shared.binary.BinaryReader
import com.sappyoak.dsanalyzer.shared.binary.assertValue
import com.sappyoak.dsanalyzer.shared.math.Vec3
import com.sappyoak.dsanalyzer.shared.math.readVec3

private const val VERSION = 1

private const val BOX_COUNT_AT = 0x0C
private const val BOX_MAX_AT = 0x10
private const val BOX_LIST_AT = 0x1C
private const val BOX_CHILDREN_AT = 0x20
private const val BOX_CHILDREN = 4

/** The quadtree is three levels deep in every file seen; this only stops a loop */
private const val BOX_DEPTH_LIMIT = 16

public fun readNavmesh(reader: BinaryReader): Navmesh {
    reader.order = ByteOrder.LITTLE_ENDIAN
    reader.position = 0
    reader.assertValue(VERSION) { readInt() }

    val vertexCount = reader.readInt()
    val vertexOffset = reader.readInt()
    val triangleCount = reader.readInt()
    val triangleOffset = reader.readInt()
    val rootBox = reader.readInt()
    val entityCount = reader.readInt()

    if (vertexCount < 0 || triangleCount < 0) throw BinaryFormatException("Navmesh counts are negative", 0)
    if (entityCount != 0) throw BinaryFormatException("Navmesh entity groups are not supported", 0x18)

    val vertices = reader.at(vertexOffset.toLong()) { FloatArray(vertexCount * 3) { readFloat() } }
    val triangles = reader.at(triangleOffset.toLong()) {
        List(triangleCount) {
            NavTriangle(
                corners = IntArray(3) { readInt() },
                neighbors = IntArray(3) { readInt() },
                flags = readInt()
            )
        }
    }

    return Navmesh(
        vertices = vertices,
        triangles = triangles,
        boxes = reader.boxAt(rootBox, 0)
    )
}

private fun BinaryReader.boxAt(at: Int, depth: Int): NavBox {
    if (depth >= BOX_DEPTH_LIMIT) throw BinaryFormatException("Navmesh box tree loops", at.toLong())

    val min = at(at.toLong()) { readVec3() }
    val count = at((at + BOX_COUNT_AT).toLong()) { readInt() }
    val max = at ((at + BOX_MAX_AT).toLong()) { readVec3() }
    val list = at((at + BOX_LIST_AT).toLong()) { readInt() }
    val children = at((at + BOX_CHILDREN_AT).toLong()) { IntArray(BOX_CHILDREN) { readInt() } }.filter { it != 0 }

    return NavBox(
        min = min,
        max = max,
        children = children.map { boxAt(it, depth + 1) },
        triangles = if (children.isEmpty() && count > 0) at(list.toLong()) { IntArray(count) { readInt() } } else IntArray(0)
    )
}
