package com.sappyoak.dsanalyzer.game.world.params

import com.sappyoak.dsanalyzer.formats.fmg.Fmg
import com.sappyoak.dsanalyzer.game.files.GameFiles
import com.sappyoak.dsanalyzer.game.world.text.TextCategory
import com.sappyoak.dsanalyzer.game.world.text.TextLanguage
import com.sappyoak.dsanalyzer.game.world.text.loadText

private const val LEVELS = 100

public val GameParam<*>.namedBy: TextCategory?
    get() = when (this) {
        GameParam.EquipParamWeapon -> TextCategory.WeaponNames
        GameParam.EquipParamProtector -> TextCategory.ArmorNames
        GameParam.EquipParamAccessory -> TextCategory.AccessoryNames
        GameParam.EquipParamGoods -> TextCategory.ItemNames
        GameParam.Magic -> TextCategory.SpellNames
        else -> null
    }


/** Weapons and armour carry their upgrade level in the id, and the text names only the base */
private val GameParam<*>.upgrades: Boolean
    get() = this == GameParam.EquipParamWeapon || this == GameParam.EquipParamProtector


/** Row names from the game's own text, for the params [namedBy] covers */
public class ParamNames(private val text: Map<GameParam<*>, Fmg>) {
    /**
     * The row's name, or null when the text has none. An upgraded row the text does not name reads
     * as its base with the level, 'Dagger +5'
     */
    public fun nameOf(param: GameParam<*>, id: Int): String? {
        val names = text[param] ?: return null
        names.named(id)?.let { return it }
        if (!param.upgrades) return null

        val level = id % LEVELS
        return if (level == 0) null else names.named(id - level)?.let { "$it +$level" }
    }

    public companion object {
        public val None: ParamNames = ParamNames(emptyMap())
    }
}

/** Blank entries are placeholders in the shipped text, not names */
private fun Fmg.named(id: Int): String? = this[id]?.takeIf { it.isNotBlank() }

/** Reads every category [namedBy] needs at once; a language the install lacks gives no names */
public fun GameFiles.loadParamNames(language: TextLanguage): ParamNames {
    val wanted = GameParam.entries.mapNotNull { param -> param.namedBy?.let { param to it } }
    val text = loadText(language, wanted.map { it.second }.distinct())
    return ParamNames(wanted.mapNotNull { (param, category) -> text[category]?.let { param to it } }.toMap())
}
