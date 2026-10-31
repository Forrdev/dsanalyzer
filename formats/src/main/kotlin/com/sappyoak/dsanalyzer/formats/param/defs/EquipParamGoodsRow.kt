// GENERATED FILE - do not edit. Regenerate with:
//
//     ./gradlew :tools:generateParamDefs -Pptde=<DATA directory> -Pdsr=<DATA directory>
//
// Source: paramdef.paramdefbnd, EquipParamGoods.paramdef, read from the installation

@file:Suppress("SpellCheckingInspection")

package com.sappyoak.dsanalyzer.formats.param.defs

import com.sappyoak.dsanalyzer.formats.param.ParamRow
import com.sappyoak.dsanalyzer.formats.param.ParamRowData
import com.sappyoak.dsanalyzer.formats.param.ParamRowType

/** `EQUIP_PARAM_GOODS_ST`: PrepareToDie 0x5C, Remastered 0x5C a row, data version 1 */
public class EquipParamGoodsRow(row: ParamRowData, private val remastered: Boolean) : ParamRow(row) {
    /** `s32` at 0x0 */
    public val refId: Int get() = bytes.s32(0x0)

    /** `s32` at 0x4 */
    public val sfxVariationId: Int get() = bytes.s32(0x4)

    /** `f32` at 0x8 */
    public val weight: Float get() = bytes.f32(0x8)

    /** `s32` at 0xC */
    public val basicPrice: Int get() = bytes.s32(0xC)

    /** `s32` at 0x10 */
    public val sellValue: Int get() = bytes.s32(0x10)

    /** `s32` at 0x14 */
    public val behaviorId: Int get() = bytes.s32(0x14)

    /** `s32` at 0x18 */
    public val replaceItemId: Int get() = bytes.s32(0x18)

    /** `s32` at 0x1C */
    public val sortId: Int get() = bytes.s32(0x1C)

    /** `s32` at 0x20 */
    public val qwcId: Int get() = bytes.s32(0x20)

    /** `s32` at 0x24 */
    public val yesNoDialogMessageId: Int get() = bytes.s32(0x24)

    /** `s32` at 0x28 */
    public val magicId: Int get() = bytes.s32(0x28)

    /** `u16` at 0x2C */
    public val iconId: Int get() = bytes.u16(0x2C)

    /** `u16` at 0x2E */
    public val modelId: Int get() = bytes.u16(0x2E)

    /** `s16` at 0x30 */
    public val shopLv: Int get() = bytes.s16(0x30)

    /** `s16` at 0x32 */
    public val compTrophySedId: Int get() = bytes.s16(0x32)

    /** `s16` at 0x34 */
    public val trophySeqId: Int get() = bytes.s16(0x34)

    /** `s16` at 0x36 */
    public val maxNum: Int get() = bytes.s16(0x36)

    /** `u8` at 0x38 */
    public val consumeHeroPoint: Int get() = bytes.u8(0x38)

    /** `u8` at 0x39 */
    public val overDexterity: Int get() = bytes.u8(0x39)

    /** `u8` at 0x3A, `GOODS_TYPE` */
    public val goodsType: Int get() = bytes.u8(0x3A)

    /** `u8` at 0x3B, `BEHAVIOR_REF_TYPE` */
    public val refCategory: Int get() = bytes.u8(0x3B)

    /** `u8` at 0x3C, `BEHAVIOR_CATEGORY` */
    public val spEffectCategory: Int get() = bytes.u8(0x3C)

    /** `u8` at 0x3D, `GOODS_CATEGORY` */
    public val goodsCategory: Int get() = bytes.u8(0x3D)

    /** `u8` at 0x3E, `GOODS_USE_ANIM` */
    public val goodsUseAnim: Int get() = bytes.u8(0x3E)

    /** `u8` at 0x3F, `GOODS_OPEN_MENU` */
    public val opmeMenuType: Int get() = bytes.u8(0x3F)

    /** `u8` at 0x40, `SP_EFFECT_USELIMIT_CATEGORY` */
    public val useLimitCategory: Int get() = bytes.u8(0x40)

    /** `u8` at 0x41, `REPLACE_CATEGORY` */
    public val replaceCategory: Int get() = bytes.u8(0x41)

    /** bit 0 of the `u8` at 0x42, `EQUIP_BOOL` */
    public val vowType0: Boolean get() = bytes.flag(0x42, 0)

    /** bit 1 of the `u8` at 0x42, `EQUIP_BOOL` */
    public val vowType1: Boolean get() = bytes.flag(0x42, 1)

    /** bit 2 of the `u8` at 0x42, `EQUIP_BOOL` */
    public val vowType2: Boolean get() = bytes.flag(0x42, 2)

    /** bit 3 of the `u8` at 0x42, `EQUIP_BOOL` */
    public val vowType3: Boolean get() = bytes.flag(0x42, 3)

    /** bit 4 of the `u8` at 0x42, `EQUIP_BOOL` */
    public val vowType4: Boolean get() = bytes.flag(0x42, 4)

    /** bit 5 of the `u8` at 0x42, `EQUIP_BOOL` */
    public val vowType5: Boolean get() = bytes.flag(0x42, 5)

    /** bit 6 of the `u8` at 0x42, `EQUIP_BOOL` */
    public val vowType6: Boolean get() = bytes.flag(0x42, 6)

    /** bit 7 of the `u8` at 0x42, `EQUIP_BOOL` */
    public val vowType7: Boolean get() = bytes.flag(0x42, 7)

    /** bit 0 of the `u8` at 0x43, `EQUIP_BOOL` */
    public val vowType8: Boolean get() = bytes.flag(0x43, 0)

    /** bit 1 of the `u8` at 0x43, `EQUIP_BOOL` */
    public val vowType9: Boolean get() = bytes.flag(0x43, 1)

    /** bit 2 of the `u8` at 0x43, `EQUIP_BOOL` */
    public val vowType10: Boolean get() = bytes.flag(0x43, 2)

    /** bit 3 of the `u8` at 0x43, `EQUIP_BOOL` */
    public val vowType11: Boolean get() = bytes.flag(0x43, 3)

    /** bit 4 of the `u8` at 0x43, `EQUIP_BOOL` */
    public val vowType12: Boolean get() = bytes.flag(0x43, 4)

    /** bit 5 of the `u8` at 0x43, `EQUIP_BOOL` */
    public val vowType13: Boolean get() = bytes.flag(0x43, 5)

    /** bit 6 of the `u8` at 0x43, `EQUIP_BOOL` */
    public val vowType14: Boolean get() = bytes.flag(0x43, 6)

    /** bit 7 of the `u8` at 0x43, `EQUIP_BOOL` */
    public val vowType15: Boolean get() = bytes.flag(0x43, 7)

    /** bit 0 of the `u8` at 0x44, `EQUIP_BOOL` */
    public val enable_live: Boolean get() = bytes.flag(0x44, 0)

    /** bit 1 of the `u8` at 0x44, `EQUIP_BOOL` */
    public val enable_gray: Boolean get() = bytes.flag(0x44, 1)

    /** bit 2 of the `u8` at 0x44, `EQUIP_BOOL` */
    public val enable_white: Boolean get() = bytes.flag(0x44, 2)

    /** bit 3 of the `u8` at 0x44, `EQUIP_BOOL` */
    public val enable_black: Boolean get() = bytes.flag(0x44, 3)

    /** bit 4 of the `u8` at 0x44, `EQUIP_BOOL` */
    public val enable_multi: Boolean get() = bytes.flag(0x44, 4)

    /** PrepareToDie: bit 5 of the `u8` at 0x44, `EQUIP_BOOL`; Remastered: bit 6 of the `u8` at 0x44, `EQUIP_BOOL` */
    public val disable_offline: Boolean get() = if (remastered) bytes.flag(0x44, 6) else bytes.flag(0x44, 5)

    /** PrepareToDie: bit 6 of the `u8` at 0x44, `EQUIP_BOOL`; Remastered: bit 7 of the `u8` at 0x44, `EQUIP_BOOL` */
    public val isEquip: Boolean get() = if (remastered) bytes.flag(0x44, 7) else bytes.flag(0x44, 6)

    /** PrepareToDie: bit 7 of the `u8` at 0x44, `EQUIP_BOOL`; Remastered: bit 0 of the `u8` at 0x45, `EQUIP_BOOL` */
    public val isConsume: Boolean get() = if (remastered) bytes.flag(0x45, 0) else bytes.flag(0x44, 7)

    /** PrepareToDie: bit 0 of the `u8` at 0x45, `EQUIP_BOOL`; Remastered: bit 1 of the `u8` at 0x45, `EQUIP_BOOL` */
    public val isAutoEquip: Boolean get() = if (remastered) bytes.flag(0x45, 1) else bytes.flag(0x45, 0)

    /** PrepareToDie: bit 1 of the `u8` at 0x45, `EQUIP_BOOL`; Remastered: bit 2 of the `u8` at 0x45, `EQUIP_BOOL` */
    public val isEstablishment: Boolean get() = if (remastered) bytes.flag(0x45, 2) else bytes.flag(0x45, 1)

    /** PrepareToDie: bit 2 of the `u8` at 0x45, `EQUIP_BOOL`; Remastered: bit 3 of the `u8` at 0x45, `EQUIP_BOOL` */
    public val isOnlyOne: Boolean get() = if (remastered) bytes.flag(0x45, 3) else bytes.flag(0x45, 2)

    /** PrepareToDie: bit 3 of the `u8` at 0x45, `EQUIP_BOOL`; Remastered: bit 4 of the `u8` at 0x45, `EQUIP_BOOL` */
    public val isDrop: Boolean get() = if (remastered) bytes.flag(0x45, 4) else bytes.flag(0x45, 3)

    /** PrepareToDie: bit 4 of the `u8` at 0x45, `EQUIP_BOOL`; Remastered: bit 5 of the `u8` at 0x45, `EQUIP_BOOL` */
    public val isDeposit: Boolean get() = if (remastered) bytes.flag(0x45, 5) else bytes.flag(0x45, 4)

    /** PrepareToDie: bit 5 of the `u8` at 0x45, `EQUIP_BOOL`; Remastered: bit 6 of the `u8` at 0x45, `EQUIP_BOOL` */
    public val isDisableHand: Boolean get() = if (remastered) bytes.flag(0x45, 6) else bytes.flag(0x45, 5)

    /** PrepareToDie: bit 6 of the `u8` at 0x45, `EQUIP_BOOL`; Remastered: bit 7 of the `u8` at 0x45, `EQUIP_BOOL` */
    public val isTravelItem: Boolean get() = if (remastered) bytes.flag(0x45, 7) else bytes.flag(0x45, 6)

    /** PrepareToDie: bit 7 of the `u8` at 0x45, `EQUIP_BOOL`; Remastered: bit 0 of the `u8` at 0x46, `EQUIP_BOOL` */
    public val isSuppleItem: Boolean get() = if (remastered) bytes.flag(0x46, 0) else bytes.flag(0x45, 7)

    /** PrepareToDie: bit 0 of the `u8` at 0x46, `EQUIP_BOOL`; Remastered: bit 1 of the `u8` at 0x46, `EQUIP_BOOL` */
    public val isFullSuppleItem: Boolean get() = if (remastered) bytes.flag(0x46, 1) else bytes.flag(0x46, 0)

    /** PrepareToDie: bit 1 of the `u8` at 0x46, `EQUIP_BOOL`; Remastered: bit 2 of the `u8` at 0x46, `EQUIP_BOOL` */
    public val isEnhance: Boolean get() = if (remastered) bytes.flag(0x46, 2) else bytes.flag(0x46, 1)

    /** PrepareToDie: bit 2 of the `u8` at 0x46, `EQUIP_BOOL`; Remastered: bit 3 of the `u8` at 0x46, `EQUIP_BOOL` */
    public val isFixItem: Boolean get() = if (remastered) bytes.flag(0x46, 3) else bytes.flag(0x46, 2)

    /** PrepareToDie: bit 3 of the `u8` at 0x46, `EQUIP_BOOL`; Remastered: bit 4 of the `u8` at 0x46, `EQUIP_BOOL` */
    public val disableMultiDropShare: Boolean get() = if (remastered) bytes.flag(0x46, 4) else bytes.flag(0x46, 3)

    /** PrepareToDie: bit 4 of the `u8` at 0x46, `EQUIP_BOOL`; Remastered: bit 5 of the `u8` at 0x46, `EQUIP_BOOL` */
    public val disableUseAtColiseum: Boolean get() = if (remastered) bytes.flag(0x46, 5) else bytes.flag(0x46, 4)

    /** PrepareToDie: bit 5 of the `u8` at 0x46, `EQUIP_BOOL`; Remastered: bit 6 of the `u8` at 0x46, `EQUIP_BOOL` */
    public val disableUseAtOutOfColiseum: Boolean get() = if (remastered) bytes.flag(0x46, 6) else bytes.flag(0x46, 5)

    /** `s32` at 0x50 */
    public val vagrantItemLotId: Int get() = bytes.s32(0x50)

    /** `s32` at 0x54 */
    public val vagrantBonusEneDropItemLotId: Int get() = bytes.s32(0x54)

    /** `s32` at 0x58 */
    public val vagrantItemEneDropItemLotId: Int get() = bytes.s32(0x58)

    /** Remastered: bit 5 of the `u8` at 0x44, `EQUIP_BOOL`. Remastered only */
    public val enable_pvp: Boolean? get() = if (remastered) bytes.flag(0x44, 5) else null

    public companion object {
        public val PrepareToDie: ParamRowType<EquipParamGoodsRow> = ParamRowType("EQUIP_PARAM_GOODS_ST", 0x5C, 1) { EquipParamGoodsRow(it, remastered = false) }
        public val Remastered: ParamRowType<EquipParamGoodsRow> = ParamRowType("EQUIP_PARAM_GOODS_ST", 0x5C, 1) { EquipParamGoodsRow(it, remastered = true) }
    }
}
