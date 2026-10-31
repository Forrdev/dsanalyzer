package com.sappyoak.dsanalyzer.formats.param

import java.nio.ByteOrder

import com.sappyoak.dsanalyzer.shared.binary.BinaryFormatException
import com.sappyoak.dsanalyzer.shared.binary.BinaryReader

private const val PARAM_TYPE_AT = 0x0C
private const val PARAM_TYPE_SIZE = 32
private const val BIG_ENDIAN_AT = 0x2C
private const val ROWS_AT = 0x30
private const val ROW_ENTRY_SIZE = 12

public fun readParam(reader: BinaryReader): Param {
    reader.order = ByteOrder.LITTLE_ENDIAN
    reader.position = 0
    val namesAt = reader.readInt()
    val dataAt = reader.readShort().toInt() and 0xFFFF
    reader.skip(2)
    val dataVersion = reader.readShort().toInt() and 0xFFFF
    val rowCount = reader.readShort().toInt() and 0xFFFF
    val paramType = reader.at(PARAM_TYPE_AT.toLong()) { readFixedString(PARAM_TYPE_SIZE) }
    if (reader.at(BIG_ENDIAN_AT.toLong()) { readByte() }.toInt() != 0) {
        throw BinaryFormatException("Param $paramType is big endian, which Dark Souls on PC never is", BIG_ENDIAN_AT)
    }

    val entries = reader.at(ROWS_AT.toLong()) { List(rowCount) { Triple(readInt(), readInt(), readInt()) } }
    val rowSize = when {
        entries.size > 1 -> entries[1].second - entries[0].second
        entries.size == 1 -> namesAt - entries[0].second
        else -> 0
    }
    entries.forEachIndexed { index, (_, offset, _) ->
        if (offset != dataAt + index * rowSize) {
            throw BinaryFormatException("Param $paramType row $index is not where even spacing puts it", ROWS_AT + index * ROW_ENTRY_SIZE)
        }
    }

    val bytes = reader.at(0) { readBytes(reader.size.toInt()) }
    return Param(
        paramType = paramType,
        dataVersion = dataVersion,
        rowSize = rowSize,
        rows = entries.map { (id, offset, nameAt) ->
            ParamRowData(
                id = id,
                name = if (nameAt == 0) "" else reader.at(nameAt.toLong()) { readShiftJIS() },
                bytes = RowBytes(bytes, offset, rowSize)
            )
        }
    )
}
