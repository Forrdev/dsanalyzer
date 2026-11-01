package com.sappyoak.dsanalyzer.formats.param.refs

import com.sappyoak.dsanalyzer.formats.param.ParamRow
import com.sappyoak.dsanalyzer.formats.param.defs.BehaviorParamRow
import com.sappyoak.dsanalyzer.formats.param.defs.ItemLotParamRow
import com.sappyoak.dsanalyzer.formats.param.defs.MagicParamRow
import com.sappyoak.dsanalyzer.formats.param.defs.ShopLineupParamRow

/** What kind of row an id points at. An attack's file depends on who uses it, so that is left to the caller */
public enum class ParamKind { Attack, Bullet, SpEffect, Weapon, Protector, Accessory, Goods, Magic }

public data class ParamRef(public val kind: ParamKind, public val id: Int)

/** The rows each of the game's lots and shops can hold, slot for slot */
private const val LOT_SLOTS = 8


// Fields whose meaning another field picks. Every mapping here was checked against the game's own
// rows: each kind's ids land in the param it names. A type outside the mapping, or a negative id,
// gives null


/** 'BEHAVIOR_REF_TYPE' 0, 1 and 2: an attack, a bullet, or an effect on the user */
public val BehaviorParamRow.target: ParamRef? get() = behaviorReference(refType, refId)

/** The same 'BEHAVIOR_REF_TYPE'. The game's spells only use bullets and effects */
public val MagicParamRow.target: ParamRef? get() = behaviorReference(refCategory, refId)

/** 'SHOP_LINEUP_EQUIPTYPE' 0 to 4 */
public val ShopLineupParamRow.item: ParamRef? get() {
    val kind = when (equipType) {
        0 -> ParamKind.Weapon
        1 -> ParamKind.Protector
        2 -> ParamKind.Accessory
        3 -> ParamKind.Goods
        4 -> ParamKind.Magic
        else -> return null
    }
    return if (equipId < 0) null else ParamRef(kind, equipId)
}

/** The lot's eight slots in order, null where a slot holds id 0. 'ITEMLOT_ITEMCATEGORY' is a high-bit tag */
public val ItemLotParamRow.items: List<ParamRef?> get() = List(LOT_SLOTS) { slot ->
    val (id, category) = when (slot) {
        0 -> lotItemId01 to lotItemCategory01
        1 -> lotItemId02 to lotItemCategory02
        2 -> lotItemId03 to lotItemCategory03
        3 -> lotItemId04 to lotItemCategory04
        4 -> lotItemId05 to lotItemCategory05
        5 -> lotItemId06 to lotItemCategory06
        6 -> lotItemId07 to lotItemCategory07
        else -> lotItemId08 to lotItemCategory08
    }
    val kind = when (category) {
        0x0000_0000 -> ParamKind.Weapon
        0x1000_0000 -> ParamKind.Protector
        0x2000_0000 -> ParamKind.Accessory
        0x4000_0000 -> ParamKind.Goods
        else -> null
    }
    if (kind == null || id <= 0) null else ParamRef(kind, id)
}

public val ParamRow.references: Map<String, ParamRef> get() = when (this) {
    is BehaviorParamRow -> listOfNotNull(target?.let { "refId" to it }).toMap()
    is MagicParamRow -> listOfNotNull(target?.let { "refId" to it }).toMap()
    is ShopLineupParamRow -> listOfNotNull(item?.let { "equipId" to it }).toMap()
    is ItemLotParamRow -> items.withIndex()
        .mapNotNull { (slot, ref) -> ref?.let { "lotItemId%02d".format(slot + 1) to it } }
        .toMap()
    else -> emptyMap()
}

private fun behaviorReference(type: Int, id: Int): ParamRef? {
    val kind = when (type) {
        0 -> ParamKind.Attack
        1 -> ParamKind.Bullet
        2 -> ParamKind.SpEffect
        else -> return null
    }
    return if (id < 0) null else ParamRef(kind, id)
}

