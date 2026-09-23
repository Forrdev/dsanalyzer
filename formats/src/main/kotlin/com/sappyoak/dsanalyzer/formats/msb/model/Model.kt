package com.sappyoak.dsanalyzer.formats.msb.model

import com.sappyoak.dsanalyzer.formats.msb.assertZeros
import com.sappyoak.dsanalyzer.formats.msb.readCoded
import com.sappyoak.dsanalyzer.formats.msb.readRequiredOffset
import com.sappyoak.dsanalyzer.formats.msb.readStringAt
import com.sappyoak.dsanalyzer.shared.binary.BinaryReader

/** A model file the map's parts may place */
public data class Model(
    public val name: String,
    public val type: ModelType,
    /** Editor path to a placeholder file, unused by game */
    public val sibPath: String,
    /** How many parts place this model */
    public val instanceCount: Int
)

internal fun BinaryReader.readModel(): Model {
    val start = position
    val nameOffset = readRequiredOffset()
    val type = readCoded<ModelType>()
    skip(4) // index within its type
    val sibOffset = readRequiredOffset()
    val instanceCount = readInt()
    assertZeros(3)

    return Model(
        name = readStringAt(start, nameOffset),
        type = type,
        sibPath = readStringAt(start, sibOffset),
        instanceCount = instanceCount
    )
}