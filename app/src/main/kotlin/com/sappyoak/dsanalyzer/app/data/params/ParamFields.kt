package com.sappyoak.dsanalyzer.app.data.params

import java.math.BigDecimal

import com.sappyoak.dsanalyzer.app.data.FieldRow
import com.sappyoak.dsanalyzer.formats.param.FieldValue
import com.sappyoak.dsanalyzer.formats.param.ParamField
import com.sappyoak.dsanalyzer.game.ref.Address

public fun List<FieldValue>.toFieldRows(links: Map<String, Address>): List<FieldRow> = map { read ->
    FieldRow(
        name = read.field.name,
        value = formatValue(read.value),
        note = noteFor(read.field),
        link = links[read.field.name]
    )
}

/** A flag reads as the 0 or 1 the game stores; a float without the trailing zeros 'toString' adds */
internal fun formatValue(value: Any): String = when (value) {
    is Boolean -> if (value) "1" else "0"
    is Float ->
        if (value.isFinite()) BigDecimal(value.toString()).stripTrailingZeros().toPlainString() else value.toString()
    else -> value.toString()
}

/** Where the field sits, then its enum and the paramdef's own name for it, whichever it has */
internal fun noteFor(field: ParamField<*>): String = listOfNotNull(
    placeOf(field),
    field.enumName,
    field.label.takeIf { it.isNotBlank() }
).joinToString(" · ")

private fun placeOf(field: ParamField<*>): String {
    val at = "0x${field.offset.toString(16).uppercase()}"
    val bit = field.bit
    val count = field.bitCount
    return when {
        bit == null || count == null -> at
        count == 1 -> "bit $bit of $at"
        else -> "bits $bit-${bit + count - 1} of $at"
    }
}
