// GENERATED FILE - do not edit. Regenerate with:
//
//     ./gradlew :tools:generateParamDefs -Pptde=<DATA directory> -Pdsr=<DATA directory>
//
// Source: paramdef.paramdefbnd, EquipParamGoods.paramdef, read from the installation

@file:Suppress("SpellCheckingInspection")

package com.sappyoak.dsanalyzer.formats.param.defs

import com.sappyoak.dsanalyzer.formats.param.ParamField
import com.sappyoak.dsanalyzer.formats.param.ParamRow
import com.sappyoak.dsanalyzer.formats.param.ParamRowData
import com.sappyoak.dsanalyzer.formats.param.ParamRowType
import com.sappyoak.dsanalyzer.formats.param.paramdef.ParamFieldType

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
        public val PrepareToDie: ParamRowType<EquipParamGoodsRow> = ParamRowType("EQUIP_PARAM_GOODS_ST", 0x5C, 1, PREPARE_TO_DIE_FIELDS) { EquipParamGoodsRow(it, remastered = false) }
        public val Remastered: ParamRowType<EquipParamGoodsRow> = ParamRowType("EQUIP_PARAM_GOODS_ST", 0x5C, 1, REMASTERED_FIELDS) { EquipParamGoodsRow(it, remastered = true) }
    }
}

private val PREPARE_TO_DIE_FIELDS: List<ParamField<EquipParamGoodsRow>> = listOf<ParamField<EquipParamGoodsRow>>(
    ParamField("refId", ParamFieldType.S32, 0x0, null, null, null, "呼び出しID") { it.refId },
    ParamField("sfxVariationId", ParamFieldType.S32, 0x4, null, null, null, "SFXバリエーションID") { it.sfxVariationId },
    ParamField("weight", ParamFieldType.F32, 0x8, null, null, null, "重量[kg]") { it.weight },
    ParamField("basicPrice", ParamFieldType.S32, 0xC, null, null, null, "基本価格") { it.basicPrice },
    ParamField("sellValue", ParamFieldType.S32, 0x10, null, null, null, "販売価格") { it.sellValue },
    ParamField("behaviorId", ParamFieldType.S32, 0x14, null, null, null, "行動ID") { it.behaviorId },
    ParamField("replaceItemId", ParamFieldType.S32, 0x18, null, null, null, "差し替えアイテムID") { it.replaceItemId },
    ParamField("sortId", ParamFieldType.S32, 0x1C, null, null, null, "ソートID") { it.sortId },
    ParamField("qwcId", ParamFieldType.S32, 0x20, null, null, null, "QWCID") { it.qwcId },
    ParamField("yesNoDialogMessageId", ParamFieldType.S32, 0x24, null, null, null, "YES/NOメッセージID") { it.yesNoDialogMessageId },
    ParamField("magicId", ParamFieldType.S32, 0x28, null, null, null, "魔法ID") { it.magicId },
    ParamField("iconId", ParamFieldType.U16, 0x2C, null, null, null, "アイコンID") { it.iconId },
    ParamField("modelId", ParamFieldType.U16, 0x2E, null, null, null, "モデルID") { it.modelId },
    ParamField("shopLv", ParamFieldType.S16, 0x30, null, null, null, "ショップレベル") { it.shopLv },
    ParamField("compTrophySedId", ParamFieldType.S16, 0x32, null, null, null, "コンプトロフィーSEQ番号") { it.compTrophySedId },
    ParamField("trophySeqId", ParamFieldType.S16, 0x34, null, null, null, "トロフィーSEQ番号") { it.trophySeqId },
    ParamField("maxNum", ParamFieldType.S16, 0x36, null, null, null, "最大所持数") { it.maxNum },
    ParamField("consumeHeroPoint", ParamFieldType.U8, 0x38, null, null, null, "消費人間性") { it.consumeHeroPoint },
    ParamField("overDexterity", ParamFieldType.U8, 0x39, null, null, null, "技量オーバー開始値") { it.overDexterity },
    ParamField("goodsType", ParamFieldType.U8, 0x3A, null, null, "GOODS_TYPE", "道具のタイプ") { it.goodsType },
    ParamField("refCategory", ParamFieldType.U8, 0x3B, null, null, "BEHAVIOR_REF_TYPE", "IDカテゴリ") { it.refCategory },
    ParamField("spEffectCategory", ParamFieldType.U8, 0x3C, null, null, "BEHAVIOR_CATEGORY", "特殊効果カテゴリ") { it.spEffectCategory },
    ParamField("goodsCategory", ParamFieldType.U8, 0x3D, null, null, "GOODS_CATEGORY", "道具カテゴリ") { it.goodsCategory },
    ParamField("goodsUseAnim", ParamFieldType.U8, 0x3E, null, null, "GOODS_USE_ANIM", "道具使用時アニメ") { it.goodsUseAnim },
    ParamField("opmeMenuType", ParamFieldType.U8, 0x3F, null, null, "GOODS_OPEN_MENU", "メニュー開くか") { it.opmeMenuType },
    ParamField("useLimitCategory", ParamFieldType.U8, 0x40, null, null, "SP_EFFECT_USELIMIT_CATEGORY", "特殊効果カテゴリによる使用制限") { it.useLimitCategory },
    ParamField("replaceCategory", ParamFieldType.U8, 0x41, null, null, "REPLACE_CATEGORY", "差し替えカテゴリ") { it.replaceCategory },
    ParamField("vowType0", ParamFieldType.U8, 0x42, 0, 1, "EQUIP_BOOL", "誓約0") { it.vowType0 },
    ParamField("vowType1", ParamFieldType.U8, 0x42, 1, 1, "EQUIP_BOOL", "誓約1") { it.vowType1 },
    ParamField("vowType2", ParamFieldType.U8, 0x42, 2, 1, "EQUIP_BOOL", "誓約2") { it.vowType2 },
    ParamField("vowType3", ParamFieldType.U8, 0x42, 3, 1, "EQUIP_BOOL", "誓約3") { it.vowType3 },
    ParamField("vowType4", ParamFieldType.U8, 0x42, 4, 1, "EQUIP_BOOL", "誓約4") { it.vowType4 },
    ParamField("vowType5", ParamFieldType.U8, 0x42, 5, 1, "EQUIP_BOOL", "誓約5") { it.vowType5 },
    ParamField("vowType6", ParamFieldType.U8, 0x42, 6, 1, "EQUIP_BOOL", "誓約6") { it.vowType6 },
    ParamField("vowType7", ParamFieldType.U8, 0x42, 7, 1, "EQUIP_BOOL", "誓約7") { it.vowType7 },
    ParamField("vowType8", ParamFieldType.U8, 0x43, 0, 1, "EQUIP_BOOL", "誓約8") { it.vowType8 },
    ParamField("vowType9", ParamFieldType.U8, 0x43, 1, 1, "EQUIP_BOOL", "誓約9") { it.vowType9 },
    ParamField("vowType10", ParamFieldType.U8, 0x43, 2, 1, "EQUIP_BOOL", "誓約10") { it.vowType10 },
    ParamField("vowType11", ParamFieldType.U8, 0x43, 3, 1, "EQUIP_BOOL", "誓約11") { it.vowType11 },
    ParamField("vowType12", ParamFieldType.U8, 0x43, 4, 1, "EQUIP_BOOL", "誓約12") { it.vowType12 },
    ParamField("vowType13", ParamFieldType.U8, 0x43, 5, 1, "EQUIP_BOOL", "誓約13") { it.vowType13 },
    ParamField("vowType14", ParamFieldType.U8, 0x43, 6, 1, "EQUIP_BOOL", "誓約14") { it.vowType14 },
    ParamField("vowType15", ParamFieldType.U8, 0x43, 7, 1, "EQUIP_BOOL", "誓約15") { it.vowType15 },
    ParamField("enable_live", ParamFieldType.U8, 0x44, 0, 1, "EQUIP_BOOL", "生存使用可") { it.enable_live },
    ParamField("enable_gray", ParamFieldType.U8, 0x44, 1, 1, "EQUIP_BOOL", "グレイ使用可") { it.enable_gray },
    ParamField("enable_white", ParamFieldType.U8, 0x44, 2, 1, "EQUIP_BOOL", "白使用可") { it.enable_white },
    ParamField("enable_black", ParamFieldType.U8, 0x44, 3, 1, "EQUIP_BOOL", "黒使用可") { it.enable_black },
    ParamField("enable_multi", ParamFieldType.U8, 0x44, 4, 1, "EQUIP_BOOL", "マルチプレイ可") { it.enable_multi },
    ParamField("disable_offline", ParamFieldType.U8, 0x44, 5, 1, "EQUIP_BOOL", "オフラインで使用不可") { it.disable_offline },
    ParamField("isEquip", ParamFieldType.U8, 0x44, 6, 1, "EQUIP_BOOL", "装備可能") { it.isEquip },
    ParamField("isConsume", ParamFieldType.U8, 0x44, 7, 1, "EQUIP_BOOL", "消耗品か") { it.isConsume },
    ParamField("isAutoEquip", ParamFieldType.U8, 0x45, 0, 1, "EQUIP_BOOL", "自動装備するか？") { it.isAutoEquip },
    ParamField("isEstablishment", ParamFieldType.U8, 0x45, 1, 1, "EQUIP_BOOL", "設置型アイテムか？") { it.isEstablishment },
    ParamField("isOnlyOne", ParamFieldType.U8, 0x45, 2, 1, "EQUIP_BOOL", "1個しか持てないか") { it.isOnlyOne },
    ParamField("isDrop", ParamFieldType.U8, 0x45, 3, 1, "EQUIP_BOOL", "捨てれるか") { it.isDrop },
    ParamField("isDeposit", ParamFieldType.U8, 0x45, 4, 1, "EQUIP_BOOL", "預けれるか") { it.isDeposit },
    ParamField("isDisableHand", ParamFieldType.U8, 0x45, 5, 1, "EQUIP_BOOL", "右素手に使えないか") { it.isDisableHand },
    ParamField("isTravelItem", ParamFieldType.U8, 0x45, 6, 1, "EQUIP_BOOL", "旅するアイテムか") { it.isTravelItem },
    ParamField("isSuppleItem", ParamFieldType.U8, 0x45, 7, 1, "EQUIP_BOOL", "補充アイテムか") { it.isSuppleItem },
    ParamField("isFullSuppleItem", ParamFieldType.U8, 0x46, 0, 1, "EQUIP_BOOL", "補充済みアイテムか") { it.isFullSuppleItem },
    ParamField("isEnhance", ParamFieldType.U8, 0x46, 1, 1, "EQUIP_BOOL", "エンチャントするか？") { it.isEnhance },
    ParamField("isFixItem", ParamFieldType.U8, 0x46, 2, 1, "EQUIP_BOOL", "修理アイテムか") { it.isFixItem },
    ParamField("disableMultiDropShare", ParamFieldType.U8, 0x46, 3, 1, "EQUIP_BOOL", "マルチドロップ共有禁止か") { it.disableMultiDropShare },
    ParamField("disableUseAtColiseum", ParamFieldType.U8, 0x46, 4, 1, "EQUIP_BOOL", "闘技場で使用禁止か") { it.disableUseAtColiseum },
    ParamField("disableUseAtOutOfColiseum", ParamFieldType.U8, 0x46, 5, 1, "EQUIP_BOOL", "闘技場以外で使用禁止か") { it.disableUseAtOutOfColiseum },
    ParamField("vagrantItemLotId", ParamFieldType.S32, 0x50, null, null, null, "ベイグラント時アイテム抽選ID") { it.vagrantItemLotId },
    ParamField("vagrantBonusEneDropItemLotId", ParamFieldType.S32, 0x54, null, null, null, "ベイグラントボーナス敵ドロップアイテム抽選ID") { it.vagrantBonusEneDropItemLotId },
    ParamField("vagrantItemEneDropItemLotId", ParamFieldType.S32, 0x58, null, null, null, "ベイグラントアイテム敵ドロップアイテム抽選ID") { it.vagrantItemEneDropItemLotId }
)

private val REMASTERED_FIELDS: List<ParamField<EquipParamGoodsRow>> = listOf<ParamField<EquipParamGoodsRow>>(
    ParamField("refId", ParamFieldType.S32, 0x0, null, null, null, "呼び出しID") { it.refId },
    ParamField("sfxVariationId", ParamFieldType.S32, 0x4, null, null, null, "SFXバリエーションID") { it.sfxVariationId },
    ParamField("weight", ParamFieldType.F32, 0x8, null, null, null, "重量[kg]") { it.weight },
    ParamField("basicPrice", ParamFieldType.S32, 0xC, null, null, null, "基本価格") { it.basicPrice },
    ParamField("sellValue", ParamFieldType.S32, 0x10, null, null, null, "販売価格") { it.sellValue },
    ParamField("behaviorId", ParamFieldType.S32, 0x14, null, null, null, "行動ID") { it.behaviorId },
    ParamField("replaceItemId", ParamFieldType.S32, 0x18, null, null, null, "差し替えアイテムID") { it.replaceItemId },
    ParamField("sortId", ParamFieldType.S32, 0x1C, null, null, null, "ソートID") { it.sortId },
    ParamField("qwcId", ParamFieldType.S32, 0x20, null, null, null, "QWCID") { it.qwcId },
    ParamField("yesNoDialogMessageId", ParamFieldType.S32, 0x24, null, null, null, "YES/NOメッセージID") { it.yesNoDialogMessageId },
    ParamField("magicId", ParamFieldType.S32, 0x28, null, null, null, "魔法ID") { it.magicId },
    ParamField("iconId", ParamFieldType.U16, 0x2C, null, null, null, "アイコンID") { it.iconId },
    ParamField("modelId", ParamFieldType.U16, 0x2E, null, null, null, "モデルID") { it.modelId },
    ParamField("shopLv", ParamFieldType.S16, 0x30, null, null, null, "ショップレベル") { it.shopLv },
    ParamField("compTrophySedId", ParamFieldType.S16, 0x32, null, null, null, "コンプトロフィーSEQ番号") { it.compTrophySedId },
    ParamField("trophySeqId", ParamFieldType.S16, 0x34, null, null, null, "トロフィーSEQ番号") { it.trophySeqId },
    ParamField("maxNum", ParamFieldType.S16, 0x36, null, null, null, "最大所持数") { it.maxNum },
    ParamField("consumeHeroPoint", ParamFieldType.U8, 0x38, null, null, null, "消費人間性") { it.consumeHeroPoint },
    ParamField("overDexterity", ParamFieldType.U8, 0x39, null, null, null, "技量オーバー開始値") { it.overDexterity },
    ParamField("goodsType", ParamFieldType.U8, 0x3A, null, null, "GOODS_TYPE", "道具のタイプ") { it.goodsType },
    ParamField("refCategory", ParamFieldType.U8, 0x3B, null, null, "BEHAVIOR_REF_TYPE", "IDカテゴリ") { it.refCategory },
    ParamField("spEffectCategory", ParamFieldType.U8, 0x3C, null, null, "BEHAVIOR_CATEGORY", "特殊効果カテゴリ") { it.spEffectCategory },
    ParamField("goodsCategory", ParamFieldType.U8, 0x3D, null, null, "GOODS_CATEGORY", "道具カテゴリ") { it.goodsCategory },
    ParamField("goodsUseAnim", ParamFieldType.U8, 0x3E, null, null, "GOODS_USE_ANIM", "道具使用時アニメ") { it.goodsUseAnim },
    ParamField("opmeMenuType", ParamFieldType.U8, 0x3F, null, null, "GOODS_OPEN_MENU", "メニュー開くか") { it.opmeMenuType },
    ParamField("useLimitCategory", ParamFieldType.U8, 0x40, null, null, "SP_EFFECT_USELIMIT_CATEGORY", "特殊効果カテゴリによる使用制限") { it.useLimitCategory },
    ParamField("replaceCategory", ParamFieldType.U8, 0x41, null, null, "REPLACE_CATEGORY", "差し替えカテゴリ") { it.replaceCategory },
    ParamField("vowType0", ParamFieldType.U8, 0x42, 0, 1, "EQUIP_BOOL", "誓約0") { it.vowType0 },
    ParamField("vowType1", ParamFieldType.U8, 0x42, 1, 1, "EQUIP_BOOL", "誓約1") { it.vowType1 },
    ParamField("vowType2", ParamFieldType.U8, 0x42, 2, 1, "EQUIP_BOOL", "誓約2") { it.vowType2 },
    ParamField("vowType3", ParamFieldType.U8, 0x42, 3, 1, "EQUIP_BOOL", "誓約3") { it.vowType3 },
    ParamField("vowType4", ParamFieldType.U8, 0x42, 4, 1, "EQUIP_BOOL", "誓約4") { it.vowType4 },
    ParamField("vowType5", ParamFieldType.U8, 0x42, 5, 1, "EQUIP_BOOL", "誓約5") { it.vowType5 },
    ParamField("vowType6", ParamFieldType.U8, 0x42, 6, 1, "EQUIP_BOOL", "誓約6") { it.vowType6 },
    ParamField("vowType7", ParamFieldType.U8, 0x42, 7, 1, "EQUIP_BOOL", "誓約7") { it.vowType7 },
    ParamField("vowType8", ParamFieldType.U8, 0x43, 0, 1, "EQUIP_BOOL", "誓約8") { it.vowType8 },
    ParamField("vowType9", ParamFieldType.U8, 0x43, 1, 1, "EQUIP_BOOL", "誓約9") { it.vowType9 },
    ParamField("vowType10", ParamFieldType.U8, 0x43, 2, 1, "EQUIP_BOOL", "誓約10") { it.vowType10 },
    ParamField("vowType11", ParamFieldType.U8, 0x43, 3, 1, "EQUIP_BOOL", "誓約11") { it.vowType11 },
    ParamField("vowType12", ParamFieldType.U8, 0x43, 4, 1, "EQUIP_BOOL", "誓約12") { it.vowType12 },
    ParamField("vowType13", ParamFieldType.U8, 0x43, 5, 1, "EQUIP_BOOL", "誓約13") { it.vowType13 },
    ParamField("vowType14", ParamFieldType.U8, 0x43, 6, 1, "EQUIP_BOOL", "誓約14") { it.vowType14 },
    ParamField("vowType15", ParamFieldType.U8, 0x43, 7, 1, "EQUIP_BOOL", "誓約15") { it.vowType15 },
    ParamField("enable_live", ParamFieldType.U8, 0x44, 0, 1, "EQUIP_BOOL", "生存使用可") { it.enable_live },
    ParamField("enable_gray", ParamFieldType.U8, 0x44, 1, 1, "EQUIP_BOOL", "グレイ使用可") { it.enable_gray },
    ParamField("enable_white", ParamFieldType.U8, 0x44, 2, 1, "EQUIP_BOOL", "白使用可") { it.enable_white },
    ParamField("enable_black", ParamFieldType.U8, 0x44, 3, 1, "EQUIP_BOOL", "黒使用可") { it.enable_black },
    ParamField("enable_multi", ParamFieldType.U8, 0x44, 4, 1, "EQUIP_BOOL", "マルチプレイ可") { it.enable_multi },
    ParamField("disable_offline", ParamFieldType.U8, 0x44, 6, 1, "EQUIP_BOOL", "オフラインで使用不可") { it.disable_offline },
    ParamField("isEquip", ParamFieldType.U8, 0x44, 7, 1, "EQUIP_BOOL", "装備可能") { it.isEquip },
    ParamField("isConsume", ParamFieldType.U8, 0x45, 0, 1, "EQUIP_BOOL", "消耗品か") { it.isConsume },
    ParamField("isAutoEquip", ParamFieldType.U8, 0x45, 1, 1, "EQUIP_BOOL", "自動装備するか？") { it.isAutoEquip },
    ParamField("isEstablishment", ParamFieldType.U8, 0x45, 2, 1, "EQUIP_BOOL", "設置型アイテムか？") { it.isEstablishment },
    ParamField("isOnlyOne", ParamFieldType.U8, 0x45, 3, 1, "EQUIP_BOOL", "1個しか持てないか") { it.isOnlyOne },
    ParamField("isDrop", ParamFieldType.U8, 0x45, 4, 1, "EQUIP_BOOL", "捨てれるか") { it.isDrop },
    ParamField("isDeposit", ParamFieldType.U8, 0x45, 5, 1, "EQUIP_BOOL", "預けれるか") { it.isDeposit },
    ParamField("isDisableHand", ParamFieldType.U8, 0x45, 6, 1, "EQUIP_BOOL", "右素手に使えないか") { it.isDisableHand },
    ParamField("isTravelItem", ParamFieldType.U8, 0x45, 7, 1, "EQUIP_BOOL", "旅するアイテムか") { it.isTravelItem },
    ParamField("isSuppleItem", ParamFieldType.U8, 0x46, 0, 1, "EQUIP_BOOL", "補充アイテムか") { it.isSuppleItem },
    ParamField("isFullSuppleItem", ParamFieldType.U8, 0x46, 1, 1, "EQUIP_BOOL", "補充済みアイテムか") { it.isFullSuppleItem },
    ParamField("isEnhance", ParamFieldType.U8, 0x46, 2, 1, "EQUIP_BOOL", "エンチャントするか？") { it.isEnhance },
    ParamField("isFixItem", ParamFieldType.U8, 0x46, 3, 1, "EQUIP_BOOL", "修理アイテムか") { it.isFixItem },
    ParamField("disableMultiDropShare", ParamFieldType.U8, 0x46, 4, 1, "EQUIP_BOOL", "マルチドロップ共有禁止か") { it.disableMultiDropShare },
    ParamField("disableUseAtColiseum", ParamFieldType.U8, 0x46, 5, 1, "EQUIP_BOOL", "闘技場で使用禁止か") { it.disableUseAtColiseum },
    ParamField("disableUseAtOutOfColiseum", ParamFieldType.U8, 0x46, 6, 1, "EQUIP_BOOL", "闘技場以外で使用禁止か") { it.disableUseAtOutOfColiseum },
    ParamField("vagrantItemLotId", ParamFieldType.S32, 0x50, null, null, null, "ベイグラント時アイテム抽選ID") { it.vagrantItemLotId },
    ParamField("vagrantBonusEneDropItemLotId", ParamFieldType.S32, 0x54, null, null, null, "ベイグラントボーナス敵ドロップアイテム抽選ID") { it.vagrantBonusEneDropItemLotId },
    ParamField("vagrantItemEneDropItemLotId", ParamFieldType.S32, 0x58, null, null, null, "ベイグラントアイテム敵ドロップアイテム抽選ID") { it.vagrantItemEneDropItemLotId },
    ParamField("enable_pvp", ParamFieldType.U8, 0x44, 5, 1, "EQUIP_BOOL", "戦闘") { checkNotNull(it.enable_pvp) }
)
