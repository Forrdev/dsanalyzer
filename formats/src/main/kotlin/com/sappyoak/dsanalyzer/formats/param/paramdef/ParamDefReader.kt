package com.sappyoak.dsanalyzer.formats.param.paramdef

import java.nio.ByteOrder

import com.sappyoak.dsanalyzer.shared.binary.BinaryFormatException
import com.sappyoak.dsanalyzer.shared.binary.BinaryReader

/** Dark Souls' paramdef format: Shift-JIS strings, 0xB0-byte fields */
private const val FORMAT_VERSION = 104
private const val FIELD_SIZE = 0xB0

private const val PARAM_TYPE_AT = 0x0C
private const val PARAM_TYPE_SIZE = 32
private const val BIG_ENDIAN_AT = 0x2C

private const val DISPLAY_NAME_SIZE = 64
private const val DISPLAY_TYPE_SIZE = 8
private const val DISPLAY_FORMAT_SIZE = 8
private const val INTERNAL_SIZE = 32

/** 'name', 'name:bits' or 'name[length]', with the spaces one SpEffect field has around its colon */
private val INTERNAL_NAME = Regex("""^([^\s:\[]+)\s*(?::\s*(\d+))?\s*(?:\[(\d+)])?$""")

public fun readParamDef(reader: BinaryReader): ParamDef {
    reader.order = ByteOrder.LITTLE_ENDIAN
    reader.position = 0
    reader.skip(4) // file size
    val headerSize = reader.readShort().toInt() and 0xFFFF
    val dataVersion = reader.readShort().toInt() and 0xFFFF
    val fieldCount = reader.readShort().toInt() and 0xFFFF
    val fieldSize = reader.readShort().toInt() and 0xFFFF
    val paramType = reader.at(PARAM_TYPE_AT.toLong()) { readFixedString(PARAM_TYPE_SIZE) }

    val (bigEndian, unicode, format) = reader.at(BIG_ENDIAN_AT.toLong()) {
        Triple(readByte().toInt(), readByte().toInt(), readShort().toInt())
    }
    if (bigEndian != 0 || unicode != 0 || format != FORMAT_VERSION || fieldSize != FIELD_SIZE) {
        throw BinaryFormatException("Paramdef $paramType is format $format, not Dark Souls' $FORMAT_VERSION", BIG_ENDIAN_AT)
    }

    return ParamDef(
        paramType = paramType,
        dataVersion = dataVersion,
        fields = List(fieldCount) { reader.fieldAt(headerSize + it * FIELD_SIZE) }
    )
}

private fun BinaryReader.fieldAt(at: Int): ParamDefField = at(at.toLong()) {
    val displayName = readFixedShiftJis(DISPLAY_NAME_SIZE)
    val code = readFixedString(DISPLAY_TYPE_SIZE).trim()
    skip(DISPLAY_FORMAT_SIZE.toLong())
    val default = readFloat()
    val minimum = readFloat()
    val maximum = readFloat()
    skip(8) // increment and edit flags, which only an editor needs
    skip(4) // byte count, which the type and the name already give
    val descriptionAt = readInt()
    val internalType = readFixedString(INTERNAL_SIZE).trim()
    val internalName = readFixedShiftJis(INTERNAL_SIZE).trim()
    val sortId = readInt()

    val type = ParamFieldType.of(code) ?: throw BinaryFormatException("Unknown paramdef type '$code'", at)
    val parts = INTERNAL_NAME.matchEntire(internalName)
        ?: throw BinaryFormatException("Paramdef field name '$internalName' does not parse", at)

    ParamDefField(
        name = parts.groupValues[1],
        type = type,
        internalType = internalType,
        bitCount = parts.groupValues[2].toIntOrNull(),
        arrayLength = parts.groupValues[3].toIntOrNull(),
        displayName = displayName,
        description = if (descriptionAt == 0) null else at(descriptionAt.toLong()) { readShiftJIS() },
        default = default,
        minimum = minimum,
        maximum = maximum,
        sortId = sortId
    )
}
