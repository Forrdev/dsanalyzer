package com.sappyoak.dsanalyzer.tools.params

import com.sappyoak.dsanalyzer.game.GameEdition

internal const val GAME_PARAMS_PACKAGE = "com.sappyoak.dsanalyzer.game.world.params"

/**
 * A file in 'GameParam.parambnd', the row class that reads it, and the row type each edition reads it
 * through
 *
 * [types] is missing an edition where that edition's install has no such param. Its value is the row
 * class itself where both editions share one row type, and 'RowClass.Edition' where the class carries
 * one per edition
 */
internal class GameParamFile(
    val stem: String,
    val rowClass: String,
    val types: Map<GameEdition, String>
)

/**
 * The 'GameParam' sealed class: one object per file, carrying a row type per edition
 *
 * **One registry rather than one per edition**, because a param absent from an edition is expressible
 * as a null slot while two sealed classes would not be. 'GameParams.resolve' names objects on this
 * class directly, and it keeps working untouched; it would have had to become generic over two
 * unrelated types otherwise
 */
internal fun emitGameParams(files: List<GameParamFile>, editions: EditionPair): String = buildString {
    val objects = files.sortedBy { objectName(it.stem) }
    val slots = listOf(editions.base, editions.other)

    append(generatedHeader("GameParam.parambnd"))
    append("\npackage $GAME_PARAMS_PACKAGE\n\n")
    append("import com.sappyoak.dsanalyzer.formats.param.ParamRow\n")
    append("import com.sappyoak.dsanalyzer.formats.param.ParamRowType\n")
    append("import com.sappyoak.dsanalyzer.game.GameEdition\n")
    objects.map { it.rowClass }.distinct().sorted().forEach { append("import $DEFS_PACKAGE.$it\n") }

    append("\n/**\n")
    append(" * Every file 'GameParam.parambnd' holds, with the row type each edition reads it through\n")
    append(" *\n")
    append(" * A null slot means that edition's install has no such param, so nothing can read it there\n")
    append(" */\n")
    append("public sealed class GameParam<R : ParamRow>(\n")
    append("    public val fileName: String,\n")
    append(slots.joinToString(",\n") { "    public val ${slotName(it)}: ParamRowType<R>?" })
    append("\n) {\n")

    objects.forEach { file ->
        val arguments = slots.joinToString(", ") { edition ->
            "${slotName(edition)} = ${file.types[edition] ?: "null"}"
        }
        append("    public data object ${objectName(file.stem)} : ")
        append("GameParam<${file.rowClass}>(\"${file.stem}\", $arguments)\n")
    }

    append("\n    /** The row type [edition] reads this param through, or null where it has no such param */\n")
    append("    public fun typeFor(edition: GameEdition): ParamRowType<R>? = when (edition) {\n")
    slots.forEach { append("        GameEdition.$it -> ${slotName(it)}\n") }
    append("    }\n")

    append("\n    public companion object {\n")
    append("        public val entries: List<GameParam<*>> by lazy {\n")
    append("            listOf(\n")
    append(objects.joinToString(",\n") { "                ${objectName(it.stem)}" })
    append("\n            )\n        }\n    }\n}\n")
}

private fun slotName(edition: GameEdition): String = edition.name.replaceFirstChar { it.lowercaseChar() }

/** 'AtkParam_Npc' to 'AtkParamNpc', 'BehaviorParam_PC' to 'BehaviorParamPc', 'default_X' to 'X' */
internal fun objectName(stem: String): String = stem.removePrefix("default_").split('_').joinToString("") { part ->
    if (part.all { it.isUpperCase() }) part.lowercase().replaceFirstChar { it.uppercaseChar() }
    else part.replaceFirstChar { it.uppercaseChar() }
}
