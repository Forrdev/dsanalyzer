// GENERATED FILE - do not edit. Regenerate with:
//
//     ./gradlew :tools:generateParamDefs -Pptde=<DATA directory> -Pdsr=<DATA directory>
//
// Source: paramdef.paramdefbnd, MagicParam.paramdef, read from the installation

@file:Suppress("SpellCheckingInspection")

package com.sappyoak.dsanalyzer.formats.param.defs

import com.sappyoak.dsanalyzer.formats.param.ParamField
import com.sappyoak.dsanalyzer.formats.param.ParamRow
import com.sappyoak.dsanalyzer.formats.param.ParamRowData
import com.sappyoak.dsanalyzer.formats.param.ParamRowType
import com.sappyoak.dsanalyzer.formats.param.paramdef.ParamFieldType

/** `MAGIC_PARAM_ST`: PrepareToDie 0x30, Remastered 0x30 a row, data version 2 */
public class MagicParamRow(row: ParamRowData) : ParamRow(row) {
    /** `s32` at 0x0 */
    public val yesNoDialogMessageId: Int get() = bytes.s32(0x0)

    /** `s32` at 0x4 */
    public val limitCancelSpEffectId: Int get() = bytes.s32(0x4)

    /** `s16` at 0x8 */
    public val sortId: Int get() = bytes.s16(0x8)

    /** `s16` at 0xA */
    public val refId: Int get() = bytes.s16(0xA)

    /** `s16` at 0xC */
    public val mp: Int get() = bytes.s16(0xC)

    /** `s16` at 0xE */
    public val stamina: Int get() = bytes.s16(0xE)

    /** `s16` at 0x10 */
    public val iconId: Int get() = bytes.s16(0x10)

    /** `s16` at 0x12 */
    public val behaviorId: Int get() = bytes.s16(0x12)

    /** `s16` at 0x14 */
    public val mtrlItemId: Int get() = bytes.s16(0x14)

    /** `s16` at 0x16 */
    public val replaceMagicId: Int get() = bytes.s16(0x16)

    /** `s16` at 0x18 */
    public val maxQuantity: Int get() = bytes.s16(0x18)

    /** `u8` at 0x1A */
    public val heroPoint: Int get() = bytes.u8(0x1A)

    /** `u8` at 0x1B */
    public val overDexterity: Int get() = bytes.u8(0x1B)

    /** `s8` at 0x1C */
    public val sfxVariationId: Int get() = bytes.s8(0x1C)

    /** `u8` at 0x1D */
    public val slotLength: Int get() = bytes.u8(0x1D)

    /** `u8` at 0x1E */
    public val requirementIntellect: Int get() = bytes.u8(0x1E)

    /** `u8` at 0x1F */
    public val requirementFaith: Int get() = bytes.u8(0x1F)

    /** `u8` at 0x20 */
    public val analogDexiterityMin: Int get() = bytes.u8(0x20)

    /** `u8` at 0x21 */
    public val analogDexiterityMax: Int get() = bytes.u8(0x21)

    /** `u8` at 0x22, `MAGIC_CATEGORY` */
    public val ezStateBehaviorType: Int get() = bytes.u8(0x22)

    /** `u8` at 0x23, `BEHAVIOR_REF_TYPE` */
    public val refCategory: Int get() = bytes.u8(0x23)

    /** `u8` at 0x24, `BEHAVIOR_CATEGORY` */
    public val spEffectCategory: Int get() = bytes.u8(0x24)

    /** `u8` at 0x25, `MAGIC_MOTION_TYPE` */
    public val refType: Int get() = bytes.u8(0x25)

    /** `u8` at 0x26, `GOODS_OPEN_MENU` */
    public val opmeMenuType: Int get() = bytes.u8(0x26)

    /** `u8` at 0x27, `SP_EFFECT_TYPE` */
    public val hasSpEffectType: Int get() = bytes.u8(0x27)

    /** `u8` at 0x28, `REPLACE_CATEGORY` */
    public val replaceCategory: Int get() = bytes.u8(0x28)

    /** `u8` at 0x29, `SP_EFFECT_USELIMIT_CATEGORY` */
    public val useLimitCategory: Int get() = bytes.u8(0x29)

    /** bit 0 of the `u8` at 0x2A, `MAGIC_BOOL` */
    public val vowType0: Boolean get() = bytes.flag(0x2A, 0)

    /** bit 1 of the `u8` at 0x2A, `MAGIC_BOOL` */
    public val vowType1: Boolean get() = bytes.flag(0x2A, 1)

    /** bit 2 of the `u8` at 0x2A, `MAGIC_BOOL` */
    public val vowType2: Boolean get() = bytes.flag(0x2A, 2)

    /** bit 3 of the `u8` at 0x2A, `MAGIC_BOOL` */
    public val vowType3: Boolean get() = bytes.flag(0x2A, 3)

    /** bit 4 of the `u8` at 0x2A, `MAGIC_BOOL` */
    public val vowType4: Boolean get() = bytes.flag(0x2A, 4)

    /** bit 5 of the `u8` at 0x2A, `MAGIC_BOOL` */
    public val vowType5: Boolean get() = bytes.flag(0x2A, 5)

    /** bit 6 of the `u8` at 0x2A, `MAGIC_BOOL` */
    public val vowType6: Boolean get() = bytes.flag(0x2A, 6)

    /** bit 7 of the `u8` at 0x2A, `MAGIC_BOOL` */
    public val vowType7: Boolean get() = bytes.flag(0x2A, 7)

    /** bit 0 of the `u8` at 0x2B, `MAGIC_BOOL` */
    public val enable_multi: Boolean get() = bytes.flag(0x2B, 0)

    /** bit 1 of the `u8` at 0x2B, `MAGIC_BOOL` */
    public val enable_multi_only: Boolean get() = bytes.flag(0x2B, 1)

    /** bit 2 of the `u8` at 0x2B, `MAGIC_BOOL` */
    public val isEnchant: Boolean get() = bytes.flag(0x2B, 2)

    /** bit 3 of the `u8` at 0x2B, `MAGIC_BOOL` */
    public val isShieldEnchant: Boolean get() = bytes.flag(0x2B, 3)

    /** bit 4 of the `u8` at 0x2B, `MAGIC_BOOL` */
    public val enable_live: Boolean get() = bytes.flag(0x2B, 4)

    /** bit 5 of the `u8` at 0x2B, `MAGIC_BOOL` */
    public val enable_gray: Boolean get() = bytes.flag(0x2B, 5)

    /** bit 6 of the `u8` at 0x2B, `MAGIC_BOOL` */
    public val enable_white: Boolean get() = bytes.flag(0x2B, 6)

    /** bit 7 of the `u8` at 0x2B, `MAGIC_BOOL` */
    public val enable_black: Boolean get() = bytes.flag(0x2B, 7)

    /** bit 0 of the `u8` at 0x2C, `MAGIC_BOOL` */
    public val disableOffline: Boolean get() = bytes.flag(0x2C, 0)

    /** bit 1 of the `u8` at 0x2C, `MAGIC_BOOL` */
    public val castResonanceMagic: Boolean get() = bytes.flag(0x2C, 1)

    /** bit 0 of the `u8` at 0x2D, `MAGIC_BOOL` */
    public val vowType8: Boolean get() = bytes.flag(0x2D, 0)

    /** bit 1 of the `u8` at 0x2D, `MAGIC_BOOL` */
    public val vowType9: Boolean get() = bytes.flag(0x2D, 1)

    /** bit 2 of the `u8` at 0x2D, `MAGIC_BOOL` */
    public val vowType10: Boolean get() = bytes.flag(0x2D, 2)

    /** bit 3 of the `u8` at 0x2D, `MAGIC_BOOL` */
    public val vowType11: Boolean get() = bytes.flag(0x2D, 3)

    /** bit 4 of the `u8` at 0x2D, `MAGIC_BOOL` */
    public val vowType12: Boolean get() = bytes.flag(0x2D, 4)

    /** bit 5 of the `u8` at 0x2D, `MAGIC_BOOL` */
    public val vowType13: Boolean get() = bytes.flag(0x2D, 5)

    /** bit 6 of the `u8` at 0x2D, `MAGIC_BOOL` */
    public val vowType14: Boolean get() = bytes.flag(0x2D, 6)

    /** bit 7 of the `u8` at 0x2D, `MAGIC_BOOL` */
    public val vowType15: Boolean get() = bytes.flag(0x2D, 7)

    public companion object : ParamRowType<MagicParamRow>("MAGIC_PARAM_ST", 0x30, 2, FIELDS, ::MagicParamRow)
}

private val FIELDS: List<ParamField<MagicParamRow>> = listOf<ParamField<MagicParamRow>>(
    ParamField("yesNoDialogMessageId", ParamFieldType.S32, 0x0, null, null, null, "Yes/NoダイアログメッセージID") { it.yesNoDialogMessageId },
    ParamField("limitCancelSpEffectId", ParamFieldType.S32, 0x4, null, null, null, "使用制限から外れる特殊効果ID") { it.limitCancelSpEffectId },
    ParamField("sortId", ParamFieldType.S16, 0x8, null, null, null, "SortID") { it.sortId },
    ParamField("refId", ParamFieldType.S16, 0xA, null, null, null, "呼び出しID") { it.refId },
    ParamField("mp", ParamFieldType.S16, 0xC, null, null, null, "消費MP") { it.mp },
    ParamField("stamina", ParamFieldType.S16, 0xE, null, null, null, "消費スタミナ") { it.stamina },
    ParamField("iconId", ParamFieldType.S16, 0x10, null, null, null, "アイコンID") { it.iconId },
    ParamField("behaviorId", ParamFieldType.S16, 0x12, null, null, null, "行動ID") { it.behaviorId },
    ParamField("mtrlItemId", ParamFieldType.S16, 0x14, null, null, null, "必要アイテムID") { it.mtrlItemId },
    ParamField("replaceMagicId", ParamFieldType.S16, 0x16, null, null, null, "差し替える魔法ID") { it.replaceMagicId },
    ParamField("maxQuantity", ParamFieldType.S16, 0x18, null, null, null, "最大個数") { it.maxQuantity },
    ParamField("heroPoint", ParamFieldType.U8, 0x1A, null, null, null, "消費人間性") { it.heroPoint },
    ParamField("overDexterity", ParamFieldType.U8, 0x1B, null, null, null, "技量オーバー開始値") { it.overDexterity },
    ParamField("sfxVariationId", ParamFieldType.S8, 0x1C, null, null, null, "SFXバリエーションID") { it.sfxVariationId },
    ParamField("slotLength", ParamFieldType.U8, 0x1D, null, null, null, "必要スロット") { it.slotLength },
    ParamField("requirementIntellect", ParamFieldType.U8, 0x1E, null, null, null, "装備条件【知力】") { it.requirementIntellect },
    ParamField("requirementFaith", ParamFieldType.U8, 0x1F, null, null, null, "装備条件【理力】") { it.requirementFaith },
    ParamField("analogDexiterityMin", ParamFieldType.U8, 0x20, null, null, null, "アナログ最低技量") { it.analogDexiterityMin },
    ParamField("analogDexiterityMax", ParamFieldType.U8, 0x21, null, null, null, "アナログ最大技量") { it.analogDexiterityMax },
    ParamField("ezStateBehaviorType", ParamFieldType.U8, 0x22, null, null, "MAGIC_CATEGORY", "カテゴリ") { it.ezStateBehaviorType },
    ParamField("refCategory", ParamFieldType.U8, 0x23, null, null, "BEHAVIOR_REF_TYPE", "IDカテゴリ") { it.refCategory },
    ParamField("spEffectCategory", ParamFieldType.U8, 0x24, null, null, "BEHAVIOR_CATEGORY", "特殊効果カテゴリ") { it.spEffectCategory },
    ParamField("refType", ParamFieldType.U8, 0x25, null, null, "MAGIC_MOTION_TYPE", "モーションカテゴリ") { it.refType },
    ParamField("opmeMenuType", ParamFieldType.U8, 0x26, null, null, "GOODS_OPEN_MENU", "使用時メニュータイプ") { it.opmeMenuType },
    ParamField("hasSpEffectType", ParamFieldType.U8, 0x27, null, null, "SP_EFFECT_TYPE", "どの常態か？") { it.hasSpEffectType },
    ParamField("replaceCategory", ParamFieldType.U8, 0x28, null, null, "REPLACE_CATEGORY", "差し替えカテゴリ") { it.replaceCategory },
    ParamField("useLimitCategory", ParamFieldType.U8, 0x29, null, null, "SP_EFFECT_USELIMIT_CATEGORY", "特殊効果カテゴリによる使用制限") { it.useLimitCategory },
    ParamField("vowType0", ParamFieldType.U8, 0x2A, 0, 1, "MAGIC_BOOL", "誓約0") { it.vowType0 },
    ParamField("vowType1", ParamFieldType.U8, 0x2A, 1, 1, "MAGIC_BOOL", "誓約1") { it.vowType1 },
    ParamField("vowType2", ParamFieldType.U8, 0x2A, 2, 1, "MAGIC_BOOL", "誓約2") { it.vowType2 },
    ParamField("vowType3", ParamFieldType.U8, 0x2A, 3, 1, "MAGIC_BOOL", "誓約3") { it.vowType3 },
    ParamField("vowType4", ParamFieldType.U8, 0x2A, 4, 1, "MAGIC_BOOL", "誓約4") { it.vowType4 },
    ParamField("vowType5", ParamFieldType.U8, 0x2A, 5, 1, "MAGIC_BOOL", "誓約5") { it.vowType5 },
    ParamField("vowType6", ParamFieldType.U8, 0x2A, 6, 1, "MAGIC_BOOL", "誓約6") { it.vowType6 },
    ParamField("vowType7", ParamFieldType.U8, 0x2A, 7, 1, "MAGIC_BOOL", "誓約7") { it.vowType7 },
    ParamField("enable_multi", ParamFieldType.U8, 0x2B, 0, 1, "MAGIC_BOOL", "マルチでも使用可能か") { it.enable_multi },
    ParamField("enable_multi_only", ParamFieldType.U8, 0x2B, 1, 1, "MAGIC_BOOL", "マルチ専用か") { it.enable_multi_only },
    ParamField("isEnchant", ParamFieldType.U8, 0x2B, 2, 1, "MAGIC_BOOL", "エンチャントか") { it.isEnchant },
    ParamField("isShieldEnchant", ParamFieldType.U8, 0x2B, 3, 1, "MAGIC_BOOL", "盾エンチャントか") { it.isShieldEnchant },
    ParamField("enable_live", ParamFieldType.U8, 0x2B, 4, 1, "MAGIC_BOOL", "生存使用可") { it.enable_live },
    ParamField("enable_gray", ParamFieldType.U8, 0x2B, 5, 1, "MAGIC_BOOL", "グレイ使用可") { it.enable_gray },
    ParamField("enable_white", ParamFieldType.U8, 0x2B, 6, 1, "MAGIC_BOOL", "白使用可") { it.enable_white },
    ParamField("enable_black", ParamFieldType.U8, 0x2B, 7, 1, "MAGIC_BOOL", "黒使用可") { it.enable_black },
    ParamField("disableOffline", ParamFieldType.U8, 0x2C, 0, 1, "MAGIC_BOOL", "オフラインで使用不可か") { it.disableOffline },
    ParamField("castResonanceMagic", ParamFieldType.U8, 0x2C, 1, 1, "MAGIC_BOOL", "共鳴魔法配信するか") { it.castResonanceMagic },
    ParamField("vowType8", ParamFieldType.U8, 0x2D, 0, 1, "MAGIC_BOOL", "誓約8") { it.vowType8 },
    ParamField("vowType9", ParamFieldType.U8, 0x2D, 1, 1, "MAGIC_BOOL", "誓約9") { it.vowType9 },
    ParamField("vowType10", ParamFieldType.U8, 0x2D, 2, 1, "MAGIC_BOOL", "誓約10") { it.vowType10 },
    ParamField("vowType11", ParamFieldType.U8, 0x2D, 3, 1, "MAGIC_BOOL", "誓約11") { it.vowType11 },
    ParamField("vowType12", ParamFieldType.U8, 0x2D, 4, 1, "MAGIC_BOOL", "誓約12") { it.vowType12 },
    ParamField("vowType13", ParamFieldType.U8, 0x2D, 5, 1, "MAGIC_BOOL", "誓約13") { it.vowType13 },
    ParamField("vowType14", ParamFieldType.U8, 0x2D, 6, 1, "MAGIC_BOOL", "誓約14") { it.vowType14 },
    ParamField("vowType15", ParamFieldType.U8, 0x2D, 7, 1, "MAGIC_BOOL", "誓約15") { it.vowType15 }
)
