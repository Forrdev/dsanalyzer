package com.sappyoak.dsanalyzer.tools.params

import com.sappyoak.dsanalyzer.formats.param.paramdef.ParamDef
import com.sappyoak.dsanalyzer.formats.param.paramdef.ParamFieldType
import com.sappyoak.dsanalyzer.formats.param.paramdef.PlacedField
import com.sappyoak.dsanalyzer.game.GameEdition

/** Kotlin's hard keywords, plus the two names every row already has */
private val RESERVED = setOf(
    "as", "break", "class", "continue", "do", "else", "false", "for", "fun", "if", "in",
    "interface", "is", "null", "object", "package", "return", "super", "this", "throw",
    "true", "try", "typealias", "typeof", "val", "var", "when", "while", "id", "name"
)

private val IDENTIFIER = Regex("[A-Za-z_][A-Za-z0-9_]*")

/** One property, and where each edition reads it from. A null side means that edition lacks the field */
internal class FieldPlan(
    val name: String,
    val type: String,
    val base: PlacedField?,
    val other: PlacedField?
) {
    val differs: Boolean
        get() = base == null || other == null || read(base) != read(other)

    fun emit(editions: EditionPair, varies: Boolean): String {
        val present = checkNotNull(base ?: other)
        if (!varies) return property(name, type, null, where(present), read(present))

        val doc = listOfNotNull(
            base?.let { "${editions.base}: ${where(it)}" },
            other?.let { "${editions.other}: ${where(it)}" }
        ).joinToString("; ")

        val flag = editions.flag
        return when {
            base == null ->
                property(name, type, "?", "$doc. ${editions.other} only", "if ($flag) ${read(present)} else null")
            other == null ->
                property(name, type, "?", "$doc. ${editions.base} only", "if ($flag) null else ${read(present)}")
            differs ->
                property(name, type, null, doc, "if ($flag) ${read(other)} else ${read(base)}")
            else -> property(name, type, null, where(base), read(base))
        }
    }
}

private fun property(name: String, type: String, nullable: String?, doc: String, body: String): String =
    "    /** $doc */\n    public val $name: $type${nullable.orEmpty()} get() = $body"

internal fun plansFor(
    className: String,
    defs: Map<GameEdition, ParamDef>,
    editions: EditionPair
): List<FieldPlan> {
    val base = placed(defs[editions.base])
    val other = placed(defs[editions.other])

    return (base.keys + other.keys).map { name ->
        val types = listOfNotNull(base[name], other[name]).map(::kotlinType).distinct()
        require(types.size == 1) {
            "$className.$name is ${types.joinToString(" in one edition and ")} in the other, " +
                    "which one row class cannot express"
        }
        FieldPlan(name = name, type = types.single(), base = base[name], other = other[name])
    }
}

private fun placed(def: ParamDef?): Map<String, PlacedField> {
    if (def == null) return emptyMap()
    val fields = def.layout.fields.filterNot { it.field.isPadding }
    fields.forEach {
        require(it.field.arrayLength == null) {
            "${def.paramType}.${it.field.name} is an array, which only padding has been"
        }
    }
    val byName = fields.associateBy { propertyName(it) }
    require(byName.size == fields.size) {
        val repeated = fields.map(::propertyName).groupingBy { it }.eachCount().filterValues { it > 1 }
        "${def.paramType} gives more than one field the name ${repeated.keys.joinToString()}"
    }
    return byName
}

/** The expression that reads the field, which is also the identity the cross-edition comparison turns on */
private fun read(placed: PlacedField): String {
    val field = placed.field
    val bits = field.bitCount
    return when {
        bits == 1 -> {
            val bit = checkNotNull(placed.bit)
            "bytes.flag(${hex(placed.offset + bit / 8)}, ${bit % 8})"
        }
        bits != null -> "bytes.bits(${hex(placed.offset)}, ${field.type.size}, ${placed.bit}, $bits)"
        else -> "bytes.${field.type.code}(${hex(placed.offset)})"
    }
}

private fun where(placed: PlacedField): String {
    val field = placed.field
    val bits = field.bitCount
    val enum = field.enumName?.let { ", `$it`" }.orEmpty()
    return when {
        bits == 1 -> "bit ${placed.bit} of the `${field.type.code}` at ${hex(placed.offset)}$enum"
        bits != null ->
            "bits ${placed.bit}-${checkNotNull(placed.bit) + bits - 1} of the " +
                    "`${field.type.code}` at ${hex(placed.offset)}$enum"
        else -> "`${field.type.code}` at ${hex(placed.offset)}$enum"
    }
}

private fun kotlinType(placed: PlacedField): String = when {
    placed.field.bitCount == 1 -> "Boolean"
    placed.field.bitCount != null -> "Int"
    placed.field.type == ParamFieldType.F32 -> "Float"
    placed.field.type == ParamFieldType.U32 -> "Long"
    else -> "Int"
}

private fun propertyName(placed: PlacedField): String {
    val name = placed.field.name.replaceFirstChar { it.lowercaseChar() }
    require(IDENTIFIER.matches(name) && name !in RESERVED) { "Paramdef field '$name' cannot be a property name" }
    return name
}

internal fun hex(value: Int): String = "0x${value.toString(16).uppercase()}"
