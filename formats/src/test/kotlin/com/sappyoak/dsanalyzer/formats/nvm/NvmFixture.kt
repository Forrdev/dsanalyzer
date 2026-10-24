package com.sappyoak.dsanalyzer.formats.nvm

import com.sappyoak.dsanalyzer.formats.FixtureWriter
import com.sappyoak.dsanalyzer.formats.entry
import com.sappyoak.dsanalyzer.shared.binary.BinaryReader

/**
 * A navmesh built by hand: a unit square split into two triangles that share their diagonal,
 * under a root box with two leaves. The second leaf lists both triangles, as the game's leaves list
 * every triangle that reaches into them. Every block sits at a constant the reader must agree with
 */
internal const val TRIANGLE_FLAGS = 0x0400_0004

private const val VERTICES = 0x80
private const val TRIANGLES = 0xB0
private const val ROOT_BOX = 0xE8
private const val LEFT_BOX = 0x128
private const val RIGHT_BOX = 0x168
private const val LEFT_LIST = 0x1A8
private const val RIGHT_LIST = 0x1AC

internal fun navmesh(version: Int = 1, entityCount: Int = 0): BinaryReader = BinaryReader.of(entry {
    int(version).int(4).int(VERTICES).int(2).int(TRIANGLES).int(ROOT_BOX).int(entityCount).int(0)
    while (position < VERTICES) int(0)

    float(0f, 0f, 0f, 1f, 0f, 0f, 1f, 0f, 1f, 0f, 0f, 1f)

    // The diagonal is the first triangle's edge 2 (corner 2 to corner 0) and the second's edge 0
    int(0).int(1).int(2).int(OPEN_EDGE).int(OPEN_EDGE).int(1).int(TRIANGLE_FLAGS)
    int(0).int(2).int(3).int(0).int(OPEN_EDGE).int(OPEN_EDGE).int(0)

    box(min = 0f, max = 1f, count = 0, list = 0, children = intArrayOf(LEFT_BOX, RIGHT_BOX, 0, 0))
    box(min = 0f, max = 0.5f, count = 1, list = LEFT_LIST, children = IntArray(4))
    box(min = 0.5f, max = 1f, count = 2, list = RIGHT_LIST, children = IntArray(4))
    int(0)
    int(0).int(1)
})

private fun FixtureWriter.box(min: Float, max: Float, count: Int, list: Int, children: IntArray) {
    float(min, 0f, min).int(count)
    float(max, 1f, max).int(list)
    children.forEach(::int)
    zeros(4)
}
