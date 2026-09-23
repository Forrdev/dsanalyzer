package com.sappyoak.dsanalyzer.formats.msb.region

import com.sappyoak.dsanalyzer.formats.msb.assertZeros
import com.sappyoak.dsanalyzer.formats.msb.readCoded
import com.sappyoak.dsanalyzer.formats.msb.readEntityId
import com.sappyoak.dsanalyzer.formats.msb.readRequiredOffset
import com.sappyoak.dsanalyzer.formats.msb.readStringAt
import com.sappyoak.dsanalyzer.shared.binary.BinaryFormatException
import com.sappyoak.dsanalyzer.shared.binary.BinaryReader
import com.sappyoak.dsanalyzer.shared.binary.assertValue
import com.sappyoak.dsanalyzer.shared.math.Vec3
import com.sappyoak.dsanalyzer.shared.math.readVec3

public data class Region(
    public val name: String,
    public val shape: Shape,
    public val position: Vec3,
    public val rotation: Vec3,
    public val entityId: Int?
)

internal fun BinaryReader.readRegion(): Region {
    val start = position
    val nameOffset = readRequiredOffset()
    assertZeros(1)
    skip(4)

    val shapeType = readCoded<ShapeType>()
    val location = readVec3()
    val rotation = readVec3()
    val emptyOffsets = List(2) { readRequiredOffset() }
    val shapeAt = position
    val shapeOffset = readInt().toLong()
    val entityOffset = readRequiredOffset()
    assertZeros(1)

    if ((shapeType != ShapeType.Point) != (shapeOffset != 0L)) {
        throw BinaryFormatException("$shapeType region has shape data offset $shapeOffset", shapeAt)
    }

    emptyOffsets.forEach { at(start + it) { assertValue(0) { readInt() } } }

    return Region(
        name = readStringAt(start, nameOffset),
        shape = at(start + shapeOffset) { readShape(shapeType) },
        position = location,
        rotation = rotation,
        entityId = at(start + entityOffset) { readEntityId() }
    )
}