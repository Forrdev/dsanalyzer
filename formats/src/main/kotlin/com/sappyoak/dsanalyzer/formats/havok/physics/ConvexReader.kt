package com.sappyoak.dsanalyzer.formats.havok.physics

import com.sappyoak.dsanalyzer.formats.havok.HavokFile

internal const val CONVEX_VERTICES = "hkpConvexVerticesShape"

private const val LANES = 4
private const val VECTOR4_BYTES = 16

/**
 * The hull's points come transposed, four to a block: the block's x vector holds four x values,
 * and so on. The last block is padded out, so the count says where the points stop
 */
internal fun HavokFile.convexAt(at: Int, layout: ConvexLayout): StaticShape {
    val blocks = data.array(at + layout.rotatedVertices)
    val count = minOf(data.int(at + layout.numVertices), blocks.count * LANES)
    val vertices = FloatArray(maxOf(count, 0) * 3)

    for (i in 0 until count) {
        val block = checkNotNull(blocks.offset) + (i / LANES) * layout.blockSize
        val lane = (i % LANES) * Float.SIZE_BYTES
        vertices[i * 3] = data.float(block + layout.blockX + lane)
        vertices[i * 3 + 1] = data.float(block + layout.blockY + lane)
        vertices[i * 3 + 2] = data.float(block + layout.blockZ + lane)
    }

    val planes = data.array(at + layout.planeEquations)
    return StaticShape.Convex(
        vertices = vertices,
        planes = (0 until planes.count).map { data.vec4(checkNotNull(planes.offset) + it * VECTOR4_BYTES) },
        radius = data.float(at + layout.radius)
    )
}
