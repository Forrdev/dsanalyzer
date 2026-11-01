// GENERATED FILE - do not edit. Regenerate with:
//
//     ./gradlew :tools:generateParamDefs -Pptde=<DATA directory> -Pdsr=<DATA directory>
//
// Source: paramdef.paramdefbnd, EquipParamAccessory.paramdef, read from the installation

@file:Suppress("SpellCheckingInspection")

package com.sappyoak.dsanalyzer.formats.param.defs

import com.sappyoak.dsanalyzer.formats.param.ParamField
import com.sappyoak.dsanalyzer.formats.param.ParamRow
import com.sappyoak.dsanalyzer.formats.param.ParamRowData
import com.sappyoak.dsanalyzer.formats.param.ParamRowType
import com.sappyoak.dsanalyzer.formats.param.paramdef.ParamFieldType

/** `EQUIP_PARAM_ACCESSORY_ST`: PrepareToDie 0x40, Remastered 0x40 a row, data version 1 */
public class EquipParamAccessoryRow(row: ParamRowData) : ParamRow(row) {
    /** `s32` at 0x0 */
    public val refId: Int get() = bytes.s32(0x0)

    /** `s32` at 0x4 */
    public val sfxVariationId: Int get() = bytes.s32(0x4)

    /** `f32` at 0x8 */
    public val weight: Float get() = bytes.f32(0x8)

    /** `s32` at 0xC */
    public val behaviorId: Int get() = bytes.s32(0xC)

    /** `s32` at 0x10 */
    public val basicPrice: Int get() = bytes.s32(0x10)

    /** `s32` at 0x14 */
    public val sellValue: Int get() = bytes.s32(0x14)

    /** `s32` at 0x18 */
    public val sortId: Int get() = bytes.s32(0x18)

    /** `s32` at 0x1C */
    public val qwcId: Int get() = bytes.s32(0x1C)

    /** `u16` at 0x20 */
    public val equipModelId: Int get() = bytes.u16(0x20)

    /** `u16` at 0x22 */
    public val iconId: Int get() = bytes.u16(0x22)

    /** `s16` at 0x24 */
    public val shopLv: Int get() = bytes.s16(0x24)

    /** `s16` at 0x26 */
    public val trophySGradeId: Int get() = bytes.s16(0x26)

    /** `s16` at 0x28 */
    public val trophySeqId: Int get() = bytes.s16(0x28)

    /** `u8` at 0x2A, `EQUIP_MODEL_CATEGORY` */
    public val equipModelCategory: Int get() = bytes.u8(0x2A)

    /** `u8` at 0x2B, `EQUIP_MODEL_GENDER` */
    public val equipModelGender: Int get() = bytes.u8(0x2B)

    /** `u8` at 0x2C, `ACCESSORY_CATEGORY` */
    public val accessoryCategory: Int get() = bytes.u8(0x2C)

    /** `u8` at 0x2D, `BEHAVIOR_REF_TYPE` */
    public val refCategory: Int get() = bytes.u8(0x2D)

    /** `u8` at 0x2E, `BEHAVIOR_CATEGORY` */
    public val spEffectCategory: Int get() = bytes.u8(0x2E)

    /** `s32` at 0x30 */
    public val vagrantItemLotId: Int get() = bytes.s32(0x30)

    /** `s32` at 0x34 */
    public val vagrantBonusEneDropItemLotId: Int get() = bytes.s32(0x34)

    /** `s32` at 0x38 */
    public val vagrantItemEneDropItemLotId: Int get() = bytes.s32(0x38)

    /** bit 0 of the `u8` at 0x3C, `EQUIP_BOOL` */
    public val isDeposit: Boolean get() = bytes.flag(0x3C, 0)

    /** bit 1 of the `u8` at 0x3C, `EQUIP_BOOL` */
    public val isEquipOutBrake: Boolean get() = bytes.flag(0x3C, 1)

    /** bit 2 of the `u8` at 0x3C, `EQUIP_BOOL` */
    public val disableMultiDropShare: Boolean get() = bytes.flag(0x3C, 2)

    public companion object : ParamRowType<EquipParamAccessoryRow>("EQUIP_PARAM_ACCESSORY_ST", 0x40, 1, FIELDS, ::EquipParamAccessoryRow)
}

private val FIELDS: List<ParamField<EquipParamAccessoryRow>> = listOf<ParamField<EquipParamAccessoryRow>>(
    ParamField("refId", ParamFieldType.S32, 0x0, null, null, null, "呼び出しID") { it.refId },
    ParamField("sfxVariationId", ParamFieldType.S32, 0x4, null, null, null, "SFXバリエーションID") { it.sfxVariationId },
    ParamField("weight", ParamFieldType.F32, 0x8, null, null, null, "重量[kg]") { it.weight },
    ParamField("behaviorId", ParamFieldType.S32, 0xC, null, null, null, "行動ID") { it.behaviorId },
    ParamField("basicPrice", ParamFieldType.S32, 0x10, null, null, null, "基本価格") { it.basicPrice },
    ParamField("sellValue", ParamFieldType.S32, 0x14, null, null, null, "販売価格") { it.sellValue },
    ParamField("sortId", ParamFieldType.S32, 0x18, null, null, null, "sortID") { it.sortId },
    ParamField("qwcId", ParamFieldType.S32, 0x1C, null, null, null, "QWCID") { it.qwcId },
    ParamField("equipModelId", ParamFieldType.U16, 0x20, null, null, null, "装備モデル番号") { it.equipModelId },
    ParamField("iconId", ParamFieldType.U16, 0x22, null, null, null, "アイコンID") { it.iconId },
    ParamField("shopLv", ParamFieldType.S16, 0x24, null, null, null, "ショップレベル") { it.shopLv },
    ParamField("trophySGradeId", ParamFieldType.S16, 0x26, null, null, null, "トロフィー") { it.trophySGradeId },
    ParamField("trophySeqId", ParamFieldType.S16, 0x28, null, null, null, "トロフィーSEQ番号") { it.trophySeqId },
    ParamField("equipModelCategory", ParamFieldType.U8, 0x2A, null, null, "EQUIP_MODEL_CATEGORY", "装備モデル種別") { it.equipModelCategory },
    ParamField("equipModelGender", ParamFieldType.U8, 0x2B, null, null, "EQUIP_MODEL_GENDER", "装備モデル性別") { it.equipModelGender },
    ParamField("accessoryCategory", ParamFieldType.U8, 0x2C, null, null, "ACCESSORY_CATEGORY", "装飾カテゴリ") { it.accessoryCategory },
    ParamField("refCategory", ParamFieldType.U8, 0x2D, null, null, "BEHAVIOR_REF_TYPE", "IDカテゴリ") { it.refCategory },
    ParamField("spEffectCategory", ParamFieldType.U8, 0x2E, null, null, "BEHAVIOR_CATEGORY", "特殊効果カテゴリ") { it.spEffectCategory },
    ParamField("vagrantItemLotId", ParamFieldType.S32, 0x30, null, null, null, "ベイグラント時アイテム抽選ID") { it.vagrantItemLotId },
    ParamField("vagrantBonusEneDropItemLotId", ParamFieldType.S32, 0x34, null, null, null, "ベイグラントボーナス敵ドロップアイテム抽選ID") { it.vagrantBonusEneDropItemLotId },
    ParamField("vagrantItemEneDropItemLotId", ParamFieldType.S32, 0x38, null, null, null, "ベイグラントアイテム敵ドロップアイテム抽選ID") { it.vagrantItemEneDropItemLotId },
    ParamField("isDeposit", ParamFieldType.U8, 0x3C, 0, 1, "EQUIP_BOOL", "預けれるか") { it.isDeposit },
    ParamField("isEquipOutBrake", ParamFieldType.U8, 0x3C, 1, 1, "EQUIP_BOOL", "外すと壊れるか") { it.isEquipOutBrake },
    ParamField("disableMultiDropShare", ParamFieldType.U8, 0x3C, 2, 1, "EQUIP_BOOL", "マルチドロップ共有禁止か") { it.disableMultiDropShare }
)
