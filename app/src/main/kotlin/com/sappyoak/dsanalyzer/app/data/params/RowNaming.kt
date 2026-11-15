package com.sappyoak.dsanalyzer.app.data.params

import com.sappyoak.dsanalyzer.formats.param.ParamRow
import com.sappyoak.dsanalyzer.game.world.params.ParamNames
import com.sappyoak.dsanalyzer.game.world.params.RowNameTranslations

/** A row's name as shown, and whether it is a translation the models disagreed on */
public data class RowLabel(public val name: String, public val tentative: Boolean = false)

/**
 * How rows are named: the game's own text where it names the row, else the English for its Japanese
 * name when translations are on, else the name the param file stores
 */
public data class RowNaming(
    public val names: ParamNames = ParamNames.None,
    public val translations: RowNameTranslations = RowNameTranslations.None
) {
    public fun labelOf(entry: ParamEntry, row: ParamRow): RowLabel {
        names.nameOf(entry.param, row.id)?.let { return RowLabel(it) }
        val translated = translations[row.name] ?: return RowLabel(row.name)
        return RowLabel(translated.english, tentative = !translated.reviewed)
    }

    public fun nameOf(entry: ParamEntry, row: ParamRow): String = labelOf(entry, row).name
}
