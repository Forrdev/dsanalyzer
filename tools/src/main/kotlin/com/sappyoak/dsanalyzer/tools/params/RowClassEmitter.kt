package com.sappyoak.dsanalyzer.tools.params

import com.sappyoak.dsanalyzer.formats.param.paramdef.ParamDef
import com.sappyoak.dsanalyzer.game.GameEdition

internal const val DEFS_PACKAGE = "com.sappyoak.dsanalyzer.formats.param.defs"

internal fun generatedHeader(source: String): String = """
    |// GENERATED FILE - do not edit. Regenerate with:
    |//
    |//     ./gradlew :tools:generateParamDefs -Pptde=<DATA directory> -Pdsr=<DATA directory>
    |//
    |// Source: $source, read from the installation
    |
    """.trimMargin()

/**
 * The editions being generated for, base first
 *
 * The base is what a plain accessor reads as, and [flag] names the constructor parameter saying a row
 * came from the other. One row class serves both: a field placed differently reads through a
 * conditional, and a field only one edition declares is nullable
 */
internal class EditionPair(val base: GameEdition, val other: GameEdition) {
    val flag: String = other.name.replaceFirstChar { it.lowercaseChar() }
}

/**
 * One row class per paramdef, covering every edition that declares it
 *
 * A param both editions place identically emits exactly what it did before any of this was
 * edition-aware — plain accessors and a companion that *is* the single row type. Keeping that output
 * byte-identical is deliberate: 33 of 39 params take this path, so a regeneration shows a diff only
 * where something really differs
 */
internal fun emitRowClass(
    className: String,
    defFile: String,
    defs: Map<GameEdition, ParamDef>,
    editions: EditionPair,
    varies: Boolean
): String {
    require(defs.isNotEmpty()) { "$className has no paramdef in either edition" }
    require(!varies || defs.size == 2) { "$className varies but only ${defs.keys} declares it" }
    val plans = plansFor(className, defs, editions)

    return buildString {
        append(generatedHeader("paramdef.paramdefbnd, $defFile"))
        append("\n@file:Suppress(\"SpellCheckingInspection\")\n\n")
        append("package $DEFS_PACKAGE\n\n")
        append("import com.sappyoak.dsanalyzer.formats.param.ParamField\n")
        append("import com.sappyoak.dsanalyzer.formats.param.ParamRow\n")
        append("import com.sappyoak.dsanalyzer.formats.param.ParamRowData\n")
        append("import com.sappyoak.dsanalyzer.formats.param.ParamRowType\n")
        append("import com.sappyoak.dsanalyzer.formats.param.paramdef.ParamFieldType\n\n")
        append(classDoc(defs, editions))
        append("public class $className(row: ParamRowData")
        if (varies) append(", private val ${editions.flag}: Boolean")
        append(") : ParamRow(row) {\n")
        plans.forEach { append(it.emit(editions, varies)).append("\n\n") }
        append(if (varies) variantTypes(className, defs, editions) else soleType(className, defs.values.first()))
        append("}\n")
        append(fieldTables(className, plans, defs, editions, varies))
    }
}

private fun fieldTables(
    className: String,
    plans: List<FieldPlan>,
    defs: Map<GameEdition, ParamDef>,
    editions: EditionPair,
    varies: Boolean
): String {
    if (!varies) return "\n" + emitFieldTable(SOLE_TABLE, className, plans, varies = false) { it.base ?: it.other }

    return orderedBy(editions, defs).joinToString("") { (edition, _) ->
        "\n" + emitFieldTable(tableFor(edition), className, plans, varies = true) {
            if (edition == editions.base) it.base else it.other
        }
    }
}

private fun classDoc(defs: Map<GameEdition, ParamDef>, editions: EditionPair): String {
    val any = defs.values.first()
    val sizes = orderedBy(editions, defs).joinToString(", ") { (edition, def) ->
        val size = hex(def.layout.rowSize)
        if (defs.size == 1) "$size bytes ($edition only)" else "$edition $size"
    }
    return "/** `${any.paramType}`: $sizes a row, data version ${any.dataVersion} */\n"
}

private fun soleType(className: String, def: ParamDef): String =
    "    public companion object : ParamRowType<$className>(" +
            "\"${def.paramType}\", ${hex(def.layout.rowSize)}, ${def.dataVersion}, $SOLE_TABLE, ::$className)\n"

/** One row type per edition, each with that edition's own row size, data version, table and flag value */
private fun variantTypes(className: String, defs: Map<GameEdition, ParamDef>, editions: EditionPair): String =
    buildString {
        append("    public companion object {\n")
        orderedBy(editions, defs).forEach { (edition, def) ->
            append("        public val $edition: ParamRowType<$className> = ParamRowType(")
            append("\"${def.paramType}\", ${hex(def.layout.rowSize)}, ${def.dataVersion}, ${tableFor(edition)}")
            append(") { $className(it, ${editions.flag} = ${edition == editions.other}) }\n")
        }
        append("    }\n")
    }

private fun orderedBy(editions: EditionPair, defs: Map<GameEdition, ParamDef>): List<Pair<GameEdition, ParamDef>> =
    listOf(editions.base, editions.other).mapNotNull { edition -> defs[edition]?.let { edition to it } }
