package com.sappyoak.dsanalyzer.formats.param.paramdef

/** One field at its place in the row. [bit] is set for a bitfield: where in its unit it starts */
public class PlacedField(public val field: ParamDefField, public val offset: Int, public val bit: Int?)

public class ParamLayout(public val fields: List<PlacedField>, public val rowSize: Int) {
    private val byName = fields.associateBy { it.field.name }

    public operator fun get(name: String): PlacedField? = byName[name]
}

/**
 * Places fields in order. A bitfield joins the unit the bitfield before it opened when both are
 * the same type and the bits still fit; otherwise it opens a unit of its own. Padding bits count
 * as 'u8'.
 */
internal fun layOut(fields: List<ParamDefField>): ParamLayout {
    var offset = 0
    var unit: BitUnit? = null
    val placed = ArrayList<PlacedField>(fields.size)

    for (field in fields) {
        val bits = field.bitCount
        if (bits == null) {
            unit = null
            placed += PlacedField(field, offset, null)
            offset += field.type.size * (field.arrayLength ?: 1)
            continue
        }

        val type = if (field.isPadding) ParamFieldType.U8 else field.type
        val open = unit
        if (open != null && open.type == type && open.used + bits <= type.size * Byte.SIZE_BITS) {
            placed += PlacedField(field, open.offset, open.used)
            open.used += bits
        } else {
            placed += PlacedField(field, offset, 0)
            unit = BitUnit(type, offset, bits)
            offset += type.size
        }
    }
    return ParamLayout(placed, offset)
}

/** The storage unit bitfields are packing into */
private class BitUnit(val type: ParamFieldType, val offset: Int, var used: Int)
