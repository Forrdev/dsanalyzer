package com.sappyoak.dsanalyzer.formats.msb

import java.nio.ByteOrder

import com.sappyoak.dsanalyzer.formats.msb.model.readModel
import com.sappyoak.dsanalyzer.formats.msb.part.readPart
import com.sappyoak.dsanalyzer.formats.msb.region.readRegion
import com.sappyoak.dsanalyzer.shared.binary.BinaryFormatException
import com.sappyoak.dsanalyzer.shared.binary.BinaryReader
import com.sappyoak.dsanalyzer.shared.binary.assertValue

private const val MODEL_PARAM = "MODEL_PARAM_ST"
private const val EVENT_PARAM = "EVENT_PARAM_ST"
private const val POINT_PARAM = "POINT_PARAM_ST"
private const val PARTS_PARAM = "PARTS_PARAM_ST"
private const val NO_REFERENCE = -1

public fun readMSB(reader: BinaryReader): MSB {
    reader.order = ByteOrder.LITTLE_ENDIAN
    reader.position = 0

    val models = reader.readParamList(MODEL_PARAM, isLast = false) { readModel() }
    reader.readParamList(EVENT_PARAM, isLast = false) { }
    val regions = reader.readParamList(POINT_PARAM, isLast = false) { readRegion() }
    val parts = reader.readParamList(PARTS_PARAM, isLast = true) { readPart() }

    return MSB(models, regions, parts)
}

/** An offset the format requires to point somewhere, so a zero means the data is malformed */
internal fun BinaryReader.readRequiredOffset(): Long {
    val at = position
    return readInt().toLong().takeUnless { it == 0L }
        ?: throw BinaryFormatException("required offset is zero", at)
}

internal fun BinaryReader.assertZeros(count: Int) = repeat(count) { assertValue(0) { readInt() } }
internal fun BinaryReader.readStringAt(start: Long, offset: Long): String = at(start + offset) { readShiftJIS() }

internal fun BinaryReader.readEntityId(): Int? = readInt().takeUnless { it == NO_REFERENCE }

internal fun BinaryReader.readPartIndex(): PartIndex? = readInt().asReference(::PartIndex)
internal inline fun <T> Int.asReference(wrap: (Int) -> T): T? = if (this == NO_REFERENCE) null else wrap(this)
