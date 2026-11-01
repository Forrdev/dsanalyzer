package com.sappyoak.dsanalyzer.tools.params

import com.sappyoak.dsanalyzer.formats.param.paramdef.PlacedField
import com.sappyoak.dsanalyzer.game.GameEdition

/** The name of the table a sole row type is built with */
internal const val SOLE_TABLE = "FIELDS"

/** The name of the table one edition's row type is built with, 'PREPARE_TO_DIE_FIELDS' */
internal fun tableFor(edition: GameEdition): String =
    edition.name.replace(Regex("(?<=[a-z])(?=[A-Z])"), "_").uppercase() + "_$SOLE_TABLE"

internal fun emitFieldTable(
    constant: String,
    className: String,
    plans: List<FieldPlan>,
    varies: Boolean,
    side: (FieldPlan) -> PlacedField?
): String = buildString {
    append("private val $constant: List<ParamField<$className>> = listOf<ParamField<$className>>(\n")
    append(
        plans.mapNotNull { plan -> side(plan)?.let { entry(plan, it, varies) } }.joinToString(",\n")
    )
    append("\n)\n")
}

private fun entry(plan: FieldPlan, placed: PlacedField, varies: Boolean): String {
    val field = placed.field
    val nullable = varies && (plan.base == null || plan.other == null)
    val read = if (nullable) "checkNotNull(it.${plan.name})" else "it.${plan.name}"
    val arguments = listOf(
        "\"${plan.name}\"",
        "ParamFieldType.${field.type.name}",
        hex(placed.offset),
        if (field.bitCount == null) "null" else "${placed.bit}",
        "${field.bitCount}",
        field.enumName?.let { "\"$it\"" } ?: "null",
        literal(field.displayName)
    )
    return "    ParamField(${arguments.joinToString(", ")}) { $read }"
}

/** A Kotlin string literal: the paramdef's names are free text and may hold any of these */
private fun literal(text: String): String = buildString {
    append('"')
    text.forEach { c ->
        when (c) {
            '\\' -> append("\\\\")
            '"' -> append("\\\"")
            '$' -> append("\\\$")
            '\n' -> append("\\n")
            '\r' -> append("\\r")
            '\t' -> append("\\t")
            else -> if (c < ' ') append("\\u%04X".format(c.code)) else append(c)
        }
    }
    append('"')
}
