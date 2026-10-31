package com.sappyoak.dsanalyzer.tools.params

import com.sappyoak.dsanalyzer.formats.param.paramdef.ParamDef
import com.sappyoak.dsanalyzer.formats.param.paramdef.ParamLayout
import com.sappyoak.dsanalyzer.game.GameEdition

/**
 * How one param's layout compares across the editions
 *
 * This is the **only** place that decides whether the editions differ. [varies] is what the row class
 * emitter is handed, so there is one answer rather than two predicates that can drift apart — the
 * variants below exist to describe the difference in the report, not to pick a code shape
 */
internal sealed interface Variance {
    /** Whether the param needs a row type per edition rather than one shared */
    val varies: Boolean

    /** Every field sits in the same place, and the row size and data version agree */
    data object Identical : Variance {
        override val varies: Boolean get() = false
    }

    /**
     * The param exists in one edition only, so there is one layout and one row type. The registry
     * leaves the other edition's slot null
     */
    data class EditionOnly(val edition: GameEdition) : Variance {
        override val varies: Boolean get() = false
    }

    /**
     * Row size or data version differ while every field sits where it did — trailing padding trimmed,
     * typically. No code differs, but 'Param.typed' checks both against the type it is handed, so the
     * editions still need a type each
     */
    data object Metadata : Variance {
        override val varies: Boolean get() = true
    }

    /** One edition declares fields the other does not, and every **shared** field keeps its place */
    data class Added(val onlyIn: GameEdition, val fields: List<String>) : Variance {
        override val varies: Boolean get() = true
    }

    /** At least one shared field is placed differently. [added] is reported alongside, not instead */
    data class Moved(val fields: List<String>, val added: List<String>) : Variance {
        override val varies: Boolean get() = true
    }
}

internal class ParamComparison(
    /** The paramdef's file name without its extension, 'EquipParamGoods' */
    val stem: String,
    val variance: Variance,
    val rowSize: Map<GameEdition, Int>
)

/**
 * Compares every paramdef given, by the **place each field is read from** rather than by name and
 * order
 *
 * Reading by place is what catches a padding change. Padding is not emitted as a property, so a
 * padding field that grows or shrinks moves every field after it while leaving the property names in
 * the same order — invisible to a name comparison, and the whole reason for this one
 */
internal fun compareParamDefs(
    byEdition: Map<GameEdition, Map<String, ParamDef>>,
    editions: EditionPair
): List<ParamComparison> = byEdition.values.flatMap { it.keys }.distinct().sorted().map { stem ->
    val present = listOf(editions.base, editions.other)
        .mapNotNull { edition -> byEdition[edition]?.get(stem)?.let { edition to it } }
        .toMap()

    ParamComparison(
        stem = stem,
        variance = present.keys.singleOrNull()?.let { Variance.EditionOnly(it) }
            ?: compare(editions, present.getValue(editions.base), present.getValue(editions.other)),
        rowSize = present.mapValues { (_, def) -> def.layout.rowSize }
    )
}

private fun compare(editions: EditionPair, base: ParamDef, other: ParamDef): Variance {
    val left = placements(base.layout)
    val right = placements(other.layout)

    val moved = left.keys.intersect(right.keys).filter { left[it] != right[it] }.sorted()
    val onlyBase = (left.keys - right.keys).sorted()
    val onlyOther = (right.keys - left.keys).sorted()

    return when {
        moved.isNotEmpty() -> Variance.Moved(moved, onlyBase + onlyOther)
        onlyBase.isNotEmpty() && onlyOther.isNotEmpty() -> Variance.Moved(emptyList(), onlyBase + onlyOther)
        onlyBase.isNotEmpty() -> Variance.Added(editions.base, onlyBase)
        onlyOther.isNotEmpty() -> Variance.Added(editions.other, onlyOther)
        base.layout.rowSize != other.layout.rowSize -> Variance.Metadata
        base.dataVersion != other.dataVersion -> Variance.Metadata
        else -> Variance.Identical
    }
}

/**
 * Each field by name, against a string that differs whenever anything about where it is read from
 * does — the storage type, the byte offset, and for a bitfield its bit and width
 */
private fun placements(layout: ParamLayout): Map<String, String> =
    layout.fields.filterNot { it.field.isPadding }.associate { placed ->
        val bits = placed.field.bitCount
        val at = "${placed.field.type.code}@0x${placed.offset.toString(16).uppercase()}"
        placed.field.name to if (bits == null) at else "$at:${placed.bit}/$bits"
    }

/**
 * The comparison as lines for the console, identical params collapsed to a count
 *
 * This is the artifact the plan gates on: it has to reproduce the delta recorded in
 * `claude/dsr-param-plan.md` independently, and a disagreement is a finding rather than a nuisance
 */
internal fun report(comparisons: List<ParamComparison>): String = buildString {
    val identical = comparisons.count { it.variance is Variance.Identical }
    appendLine("$identical of ${comparisons.size} params are placed identically in both editions")

    comparisons.filterNot { it.variance is Variance.Identical }.forEach { comparison ->
        val sizes = comparison.rowSize.entries
            .joinToString(", ") { (edition, size) -> "$edition 0x${size.toString(16).uppercase()}" }
        appendLine("  ${comparison.stem}  [$sizes]")
        appendLine("    ${describe(comparison.variance)}")
    }
}

private fun describe(variance: Variance): String = when (variance) {
    is Variance.Identical -> "identical"
    is Variance.EditionOnly -> "${variance.edition} only"
    is Variance.Metadata -> "row size or data version differ; no field moved or added"
    is Variance.Added -> "${variance.onlyIn} adds ${variance.fields.joinToString()}; no shared field moved"
    is Variance.Moved -> buildString {
        append("${variance.fields.size} shared fields are placed differently")
        if (variance.fields.isNotEmpty()) append(" (${variance.fields.take(4).joinToString()}…)")
        if (variance.added.isNotEmpty()) append("; added ${variance.added.joinToString()}")
    }
}
