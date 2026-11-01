// GENERATED FILE - do not edit. Regenerate with:
//
//     ./gradlew :tools:generateParamDefs -Pptde=<DATA directory> -Pdsr=<DATA directory>
//
// Source: paramdef.paramdefbnd, EquipParamProtector.paramdef, read from the installation

@file:Suppress("SpellCheckingInspection")

package com.sappyoak.dsanalyzer.formats.param.defs

import com.sappyoak.dsanalyzer.formats.param.ParamField
import com.sappyoak.dsanalyzer.formats.param.ParamRow
import com.sappyoak.dsanalyzer.formats.param.ParamRowData
import com.sappyoak.dsanalyzer.formats.param.ParamRowType
import com.sappyoak.dsanalyzer.formats.param.paramdef.ParamFieldType

/** `EQUIP_PARAM_PROTECTOR_ST`: PrepareToDie 0xE8, Remastered 0xE8 a row, data version 2 */
public class EquipParamProtectorRow(row: ParamRowData) : ParamRow(row) {
    /** `s32` at 0x0 */
    public val sortId: Int get() = bytes.s32(0x0)

    /** `u32` at 0x4 */
    public val wanderingEquipId: Long get() = bytes.u32(0x4)

    /** `s32` at 0x8 */
    public val vagrantItemLotId: Int get() = bytes.s32(0x8)

    /** `s32` at 0xC */
    public val vagrantBonusEneDropItemLotId: Int get() = bytes.s32(0xC)

    /** `s32` at 0x10 */
    public val vagrantItemEneDropItemLotId: Int get() = bytes.s32(0x10)

    /** `s32` at 0x14 */
    public val fixPrice: Int get() = bytes.s32(0x14)

    /** `s32` at 0x18 */
    public val basicPrice: Int get() = bytes.s32(0x18)

    /** `s32` at 0x1C */
    public val sellValue: Int get() = bytes.s32(0x1C)

    /** `f32` at 0x20 */
    public val weight: Float get() = bytes.f32(0x20)

    /** `s32` at 0x24 */
    public val residentSpEffectId: Int get() = bytes.s32(0x24)

    /** `s32` at 0x28 */
    public val residentSpEffectId2: Int get() = bytes.s32(0x28)

    /** `s32` at 0x2C */
    public val residentSpEffectId3: Int get() = bytes.s32(0x2C)

    /** `s32` at 0x30 */
    public val materialSetId: Int get() = bytes.s32(0x30)

    /** `f32` at 0x34 */
    public val partsDamageRate: Float get() = bytes.f32(0x34)

    /** `f32` at 0x38 */
    public val corectSARecover: Float get() = bytes.f32(0x38)

    /** `s32` at 0x3C */
    public val originEquipPro: Int get() = bytes.s32(0x3C)

    /** `s32` at 0x40 */
    public val originEquipPro1: Int get() = bytes.s32(0x40)

    /** `s32` at 0x44 */
    public val originEquipPro2: Int get() = bytes.s32(0x44)

    /** `s32` at 0x48 */
    public val originEquipPro3: Int get() = bytes.s32(0x48)

    /** `s32` at 0x4C */
    public val originEquipPro4: Int get() = bytes.s32(0x4C)

    /** `s32` at 0x50 */
    public val originEquipPro5: Int get() = bytes.s32(0x50)

    /** `s32` at 0x54 */
    public val originEquipPro6: Int get() = bytes.s32(0x54)

    /** `s32` at 0x58 */
    public val originEquipPro7: Int get() = bytes.s32(0x58)

    /** `s32` at 0x5C */
    public val originEquipPro8: Int get() = bytes.s32(0x5C)

    /** `s32` at 0x60 */
    public val originEquipPro9: Int get() = bytes.s32(0x60)

    /** `s32` at 0x64 */
    public val originEquipPro10: Int get() = bytes.s32(0x64)

    /** `s32` at 0x68 */
    public val originEquipPro11: Int get() = bytes.s32(0x68)

    /** `s32` at 0x6C */
    public val originEquipPro12: Int get() = bytes.s32(0x6C)

    /** `s32` at 0x70 */
    public val originEquipPro13: Int get() = bytes.s32(0x70)

    /** `s32` at 0x74 */
    public val originEquipPro14: Int get() = bytes.s32(0x74)

    /** `s32` at 0x78 */
    public val originEquipPro15: Int get() = bytes.s32(0x78)

    /** `f32` at 0x7C */
    public val faceScaleM_ScaleX: Float get() = bytes.f32(0x7C)

    /** `f32` at 0x80 */
    public val faceScaleM_ScaleZ: Float get() = bytes.f32(0x80)

    /** `f32` at 0x84 */
    public val faceScaleM_MaxX: Float get() = bytes.f32(0x84)

    /** `f32` at 0x88 */
    public val faceScaleM_MaxZ: Float get() = bytes.f32(0x88)

    /** `f32` at 0x8C */
    public val faceScaleF_ScaleX: Float get() = bytes.f32(0x8C)

    /** `f32` at 0x90 */
    public val faceScaleF_ScaleZ: Float get() = bytes.f32(0x90)

    /** `f32` at 0x94 */
    public val faceScaleF_MaxX: Float get() = bytes.f32(0x94)

    /** `f32` at 0x98 */
    public val faceScaleF_MaxZ: Float get() = bytes.f32(0x98)

    /** `s32` at 0x9C */
    public val qwcId: Int get() = bytes.s32(0x9C)

    /** `u16` at 0xA0 */
    public val equipModelId: Int get() = bytes.u16(0xA0)

    /** `u16` at 0xA2 */
    public val iconIdM: Int get() = bytes.u16(0xA2)

    /** `u16` at 0xA4 */
    public val iconIdF: Int get() = bytes.u16(0xA4)

    /** `u16` at 0xA6 */
    public val knockBack: Int get() = bytes.u16(0xA6)

    /** `u16` at 0xA8 */
    public val knockbackBounceRate: Int get() = bytes.u16(0xA8)

    /** `u16` at 0xAA */
    public val durability: Int get() = bytes.u16(0xAA)

    /** `u16` at 0xAC */
    public val durabilityMax: Int get() = bytes.u16(0xAC)

    /** `s16` at 0xAE */
    public val saDurability: Int get() = bytes.s16(0xAE)

    /** `u16` at 0xB0 */
    public val defFlickPower: Int get() = bytes.u16(0xB0)

    /** `u16` at 0xB2 */
    public val defensePhysics: Int get() = bytes.u16(0xB2)

    /** `u16` at 0xB4 */
    public val defenseMagic: Int get() = bytes.u16(0xB4)

    /** `u16` at 0xB6 */
    public val defenseFire: Int get() = bytes.u16(0xB6)

    /** `u16` at 0xB8 */
    public val defenseThunder: Int get() = bytes.u16(0xB8)

    /** `s16` at 0xBA */
    public val defenseSlash: Int get() = bytes.s16(0xBA)

    /** `s16` at 0xBC */
    public val defenseBlow: Int get() = bytes.s16(0xBC)

    /** `s16` at 0xBE */
    public val defenseThrust: Int get() = bytes.s16(0xBE)

    /** `u16` at 0xC0 */
    public val resistPoison: Int get() = bytes.u16(0xC0)

    /** `u16` at 0xC2 */
    public val resistDisease: Int get() = bytes.u16(0xC2)

    /** `u16` at 0xC4 */
    public val resistBlood: Int get() = bytes.u16(0xC4)

    /** `u16` at 0xC6 */
    public val resistCurse: Int get() = bytes.u16(0xC6)

    /** `s16` at 0xC8 */
    public val reinforceTypeId: Int get() = bytes.s16(0xC8)

    /** `s16` at 0xCA */
    public val trophySGradeId: Int get() = bytes.s16(0xCA)

    /** `s16` at 0xCC */
    public val shopLv: Int get() = bytes.s16(0xCC)

    /** `u8` at 0xCE */
    public val knockbackParamId: Int get() = bytes.u8(0xCE)

    /** `u8` at 0xCF */
    public val flickDamageCutRate: Int get() = bytes.u8(0xCF)

    /** `u8` at 0xD0, `EQUIP_MODEL_CATEGORY` */
    public val equipModelCategory: Int get() = bytes.u8(0xD0)

    /** `u8` at 0xD1, `EQUIP_MODEL_GENDER` */
    public val equipModelGender: Int get() = bytes.u8(0xD1)

    /** `u8` at 0xD2, `PROTECTOR_CATEGORY` */
    public val protectorCategory: Int get() = bytes.u8(0xD2)

    /** `u8` at 0xD3, `WEP_MATERIAL_DEF` */
    public val defenseMaterial: Int get() = bytes.u8(0xD3)

    /** `u8` at 0xD4, `WEP_MATERIAL_DEF_SFX` */
    public val defenseMaterialSfx: Int get() = bytes.u8(0xD4)

    /** `u8` at 0xD5, `ATK_PARAM_PARTSDMGTYPE` */
    public val partsDmgType: Int get() = bytes.u8(0xD5)

    /** `u8` at 0xD6, `WEP_MATERIAL_DEF` */
    public val defenseMaterial_Weak: Int get() = bytes.u8(0xD6)

    /** `u8` at 0xD7, `WEP_MATERIAL_DEF_SFX` */
    public val defenseMaterialSfx_Weak: Int get() = bytes.u8(0xD7)

    /** bit 0 of the `u8` at 0xD8, `EQUIP_BOOL` */
    public val isDeposit: Boolean get() = bytes.flag(0xD8, 0)

    /** bit 1 of the `u8` at 0xD8, `EQUIP_BOOL` */
    public val headEquip: Boolean get() = bytes.flag(0xD8, 1)

    /** bit 2 of the `u8` at 0xD8, `EQUIP_BOOL` */
    public val bodyEquip: Boolean get() = bytes.flag(0xD8, 2)

    /** bit 3 of the `u8` at 0xD8, `EQUIP_BOOL` */
    public val armEquip: Boolean get() = bytes.flag(0xD8, 3)

    /** bit 4 of the `u8` at 0xD8, `EQUIP_BOOL` */
    public val legEquip: Boolean get() = bytes.flag(0xD8, 4)

    /** bit 5 of the `u8` at 0xD8, `EQUIP_BOOL` */
    public val useFaceScale: Boolean get() = bytes.flag(0xD8, 5)

    /** bit 6 of the `u8` at 0xD8 */
    public val invisibleFlag00: Boolean get() = bytes.flag(0xD8, 6)

    /** bit 7 of the `u8` at 0xD8 */
    public val invisibleFlag01: Boolean get() = bytes.flag(0xD8, 7)

    /** bit 0 of the `u8` at 0xD9 */
    public val invisibleFlag02: Boolean get() = bytes.flag(0xD9, 0)

    /** bit 1 of the `u8` at 0xD9 */
    public val invisibleFlag03: Boolean get() = bytes.flag(0xD9, 1)

    /** bit 2 of the `u8` at 0xD9 */
    public val invisibleFlag04: Boolean get() = bytes.flag(0xD9, 2)

    /** bit 3 of the `u8` at 0xD9 */
    public val invisibleFlag05: Boolean get() = bytes.flag(0xD9, 3)

    /** bit 4 of the `u8` at 0xD9 */
    public val invisibleFlag06: Boolean get() = bytes.flag(0xD9, 4)

    /** bit 5 of the `u8` at 0xD9 */
    public val invisibleFlag07: Boolean get() = bytes.flag(0xD9, 5)

    /** bit 6 of the `u8` at 0xD9 */
    public val invisibleFlag08: Boolean get() = bytes.flag(0xD9, 6)

    /** bit 7 of the `u8` at 0xD9 */
    public val invisibleFlag09: Boolean get() = bytes.flag(0xD9, 7)

    /** bit 0 of the `u8` at 0xDA */
    public val invisibleFlag10: Boolean get() = bytes.flag(0xDA, 0)

    /** bit 1 of the `u8` at 0xDA */
    public val invisibleFlag11: Boolean get() = bytes.flag(0xDA, 1)

    /** bit 2 of the `u8` at 0xDA */
    public val invisibleFlag12: Boolean get() = bytes.flag(0xDA, 2)

    /** bit 3 of the `u8` at 0xDA */
    public val invisibleFlag13: Boolean get() = bytes.flag(0xDA, 3)

    /** bit 4 of the `u8` at 0xDA */
    public val invisibleFlag14: Boolean get() = bytes.flag(0xDA, 4)

    /** bit 5 of the `u8` at 0xDA */
    public val invisibleFlag15: Boolean get() = bytes.flag(0xDA, 5)

    /** bit 6 of the `u8` at 0xDA */
    public val invisibleFlag16: Boolean get() = bytes.flag(0xDA, 6)

    /** bit 7 of the `u8` at 0xDA */
    public val invisibleFlag17: Boolean get() = bytes.flag(0xDA, 7)

    /** bit 0 of the `u8` at 0xDB */
    public val invisibleFlag18: Boolean get() = bytes.flag(0xDB, 0)

    /** bit 1 of the `u8` at 0xDB */
    public val invisibleFlag19: Boolean get() = bytes.flag(0xDB, 1)

    /** bit 2 of the `u8` at 0xDB */
    public val invisibleFlag20: Boolean get() = bytes.flag(0xDB, 2)

    /** bit 3 of the `u8` at 0xDB */
    public val invisibleFlag21: Boolean get() = bytes.flag(0xDB, 3)

    /** bit 4 of the `u8` at 0xDB */
    public val invisibleFlag22: Boolean get() = bytes.flag(0xDB, 4)

    /** bit 5 of the `u8` at 0xDB */
    public val invisibleFlag23: Boolean get() = bytes.flag(0xDB, 5)

    /** bit 6 of the `u8` at 0xDB */
    public val invisibleFlag24: Boolean get() = bytes.flag(0xDB, 6)

    /** bit 7 of the `u8` at 0xDB */
    public val invisibleFlag25: Boolean get() = bytes.flag(0xDB, 7)

    /** bit 0 of the `u8` at 0xDC */
    public val invisibleFlag26: Boolean get() = bytes.flag(0xDC, 0)

    /** bit 1 of the `u8` at 0xDC */
    public val invisibleFlag27: Boolean get() = bytes.flag(0xDC, 1)

    /** bit 2 of the `u8` at 0xDC */
    public val invisibleFlag28: Boolean get() = bytes.flag(0xDC, 2)

    /** bit 3 of the `u8` at 0xDC */
    public val invisibleFlag29: Boolean get() = bytes.flag(0xDC, 3)

    /** bit 4 of the `u8` at 0xDC */
    public val invisibleFlag30: Boolean get() = bytes.flag(0xDC, 4)

    /** bit 5 of the `u8` at 0xDC */
    public val invisibleFlag31: Boolean get() = bytes.flag(0xDC, 5)

    /** bit 6 of the `u8` at 0xDC */
    public val invisibleFlag32: Boolean get() = bytes.flag(0xDC, 6)

    /** bit 7 of the `u8` at 0xDC */
    public val invisibleFlag33: Boolean get() = bytes.flag(0xDC, 7)

    /** bit 0 of the `u8` at 0xDD */
    public val invisibleFlag34: Boolean get() = bytes.flag(0xDD, 0)

    /** bit 1 of the `u8` at 0xDD */
    public val invisibleFlag35: Boolean get() = bytes.flag(0xDD, 1)

    /** bit 2 of the `u8` at 0xDD */
    public val invisibleFlag36: Boolean get() = bytes.flag(0xDD, 2)

    /** bit 3 of the `u8` at 0xDD */
    public val invisibleFlag37: Boolean get() = bytes.flag(0xDD, 3)

    /** bit 4 of the `u8` at 0xDD */
    public val invisibleFlag38: Boolean get() = bytes.flag(0xDD, 4)

    /** bit 5 of the `u8` at 0xDD */
    public val invisibleFlag39: Boolean get() = bytes.flag(0xDD, 5)

    /** bit 6 of the `u8` at 0xDD */
    public val invisibleFlag40: Boolean get() = bytes.flag(0xDD, 6)

    /** bit 7 of the `u8` at 0xDD */
    public val invisibleFlag41: Boolean get() = bytes.flag(0xDD, 7)

    /** bit 0 of the `u8` at 0xDE */
    public val invisibleFlag42: Boolean get() = bytes.flag(0xDE, 0)

    /** bit 1 of the `u8` at 0xDE */
    public val invisibleFlag43: Boolean get() = bytes.flag(0xDE, 1)

    /** bit 2 of the `u8` at 0xDE */
    public val invisibleFlag44: Boolean get() = bytes.flag(0xDE, 2)

    /** bit 3 of the `u8` at 0xDE */
    public val invisibleFlag45: Boolean get() = bytes.flag(0xDE, 3)

    /** bit 4 of the `u8` at 0xDE */
    public val invisibleFlag46: Boolean get() = bytes.flag(0xDE, 4)

    /** bit 5 of the `u8` at 0xDE */
    public val invisibleFlag47: Boolean get() = bytes.flag(0xDE, 5)

    /** bit 6 of the `u8` at 0xDE, `EQUIP_BOOL` */
    public val disableMultiDropShare: Boolean get() = bytes.flag(0xDE, 6)

    /** bit 7 of the `u8` at 0xDE, `EQUIP_BOOL` */
    public val simpleModelForDlc: Boolean get() = bytes.flag(0xDE, 7)

    /** `s16` at 0xE0 */
    public val oldSortId: Int get() = bytes.s16(0xE0)

    public companion object : ParamRowType<EquipParamProtectorRow>("EQUIP_PARAM_PROTECTOR_ST", 0xE8, 2, FIELDS, ::EquipParamProtectorRow)
}

private val FIELDS: List<ParamField<EquipParamProtectorRow>> = listOf<ParamField<EquipParamProtectorRow>>(
    ParamField("sortId", ParamFieldType.S32, 0x0, null, null, null, "ソートID") { it.sortId },
    ParamField("wanderingEquipId", ParamFieldType.U32, 0x4, null, null, null, "徘徊装備ID") { it.wanderingEquipId },
    ParamField("vagrantItemLotId", ParamFieldType.S32, 0x8, null, null, null, "ベイグラント時アイテム抽選ID") { it.vagrantItemLotId },
    ParamField("vagrantBonusEneDropItemLotId", ParamFieldType.S32, 0xC, null, null, null, "ベイグラントボーナス敵ドロップアイテム抽選ID") { it.vagrantBonusEneDropItemLotId },
    ParamField("vagrantItemEneDropItemLotId", ParamFieldType.S32, 0x10, null, null, null, "ベイグラントアイテム敵ドロップアイテム抽選ID") { it.vagrantItemEneDropItemLotId },
    ParamField("fixPrice", ParamFieldType.S32, 0x14, null, null, null, "修理価格") { it.fixPrice },
    ParamField("basicPrice", ParamFieldType.S32, 0x18, null, null, null, "基本価格") { it.basicPrice },
    ParamField("sellValue", ParamFieldType.S32, 0x1C, null, null, null, "販売価格") { it.sellValue },
    ParamField("weight", ParamFieldType.F32, 0x20, null, null, null, "重量[kg]") { it.weight },
    ParamField("residentSpEffectId", ParamFieldType.S32, 0x24, null, null, null, "常駐特殊効果ID1") { it.residentSpEffectId },
    ParamField("residentSpEffectId2", ParamFieldType.S32, 0x28, null, null, null, "常駐特殊効果ID2") { it.residentSpEffectId2 },
    ParamField("residentSpEffectId3", ParamFieldType.S32, 0x2C, null, null, null, "常駐特殊効果ID3") { it.residentSpEffectId3 },
    ParamField("materialSetId", ParamFieldType.S32, 0x30, null, null, null, "素材ID") { it.materialSetId },
    ParamField("partsDamageRate", ParamFieldType.F32, 0x34, null, null, null, "部位ダメージ率") { it.partsDamageRate },
    ParamField("corectSARecover", ParamFieldType.F32, 0x38, null, null, null, "SA回復時間補正値") { it.corectSARecover },
    ParamField("originEquipPro", ParamFieldType.S32, 0x3C, null, null, null, "派生元") { it.originEquipPro },
    ParamField("originEquipPro1", ParamFieldType.S32, 0x40, null, null, null, "派生元 強化+1") { it.originEquipPro1 },
    ParamField("originEquipPro2", ParamFieldType.S32, 0x44, null, null, null, "派生元 強化+2") { it.originEquipPro2 },
    ParamField("originEquipPro3", ParamFieldType.S32, 0x48, null, null, null, "派生元 強化+3") { it.originEquipPro3 },
    ParamField("originEquipPro4", ParamFieldType.S32, 0x4C, null, null, null, "派生元 強化+4") { it.originEquipPro4 },
    ParamField("originEquipPro5", ParamFieldType.S32, 0x50, null, null, null, "派生元 強化+5") { it.originEquipPro5 },
    ParamField("originEquipPro6", ParamFieldType.S32, 0x54, null, null, null, "派生元 強化+6") { it.originEquipPro6 },
    ParamField("originEquipPro7", ParamFieldType.S32, 0x58, null, null, null, "派生元 強化+7") { it.originEquipPro7 },
    ParamField("originEquipPro8", ParamFieldType.S32, 0x5C, null, null, null, "派生元 強化+8") { it.originEquipPro8 },
    ParamField("originEquipPro9", ParamFieldType.S32, 0x60, null, null, null, "派生元 強化+9") { it.originEquipPro9 },
    ParamField("originEquipPro10", ParamFieldType.S32, 0x64, null, null, null, "派生元 強化+10") { it.originEquipPro10 },
    ParamField("originEquipPro11", ParamFieldType.S32, 0x68, null, null, null, "派生元 強化+11") { it.originEquipPro11 },
    ParamField("originEquipPro12", ParamFieldType.S32, 0x6C, null, null, null, "派生元 強化+12") { it.originEquipPro12 },
    ParamField("originEquipPro13", ParamFieldType.S32, 0x70, null, null, null, "派生元 強化+13") { it.originEquipPro13 },
    ParamField("originEquipPro14", ParamFieldType.S32, 0x74, null, null, null, "派生元 強化+14") { it.originEquipPro14 },
    ParamField("originEquipPro15", ParamFieldType.S32, 0x78, null, null, null, "派生元 強化+15") { it.originEquipPro15 },
    ParamField("faceScaleM_ScaleX", ParamFieldType.F32, 0x7C, null, null, null, "男横顔拡大スケール") { it.faceScaleM_ScaleX },
    ParamField("faceScaleM_ScaleZ", ParamFieldType.F32, 0x80, null, null, null, "男前顔拡大スケール") { it.faceScaleM_ScaleZ },
    ParamField("faceScaleM_MaxX", ParamFieldType.F32, 0x84, null, null, null, "男横顔拡大最大倍率") { it.faceScaleM_MaxX },
    ParamField("faceScaleM_MaxZ", ParamFieldType.F32, 0x88, null, null, null, "男前顔拡大最大倍率") { it.faceScaleM_MaxZ },
    ParamField("faceScaleF_ScaleX", ParamFieldType.F32, 0x8C, null, null, null, "女横顔拡大スケール") { it.faceScaleF_ScaleX },
    ParamField("faceScaleF_ScaleZ", ParamFieldType.F32, 0x90, null, null, null, "女前顔拡大スケール") { it.faceScaleF_ScaleZ },
    ParamField("faceScaleF_MaxX", ParamFieldType.F32, 0x94, null, null, null, "女横顔拡大最大倍率") { it.faceScaleF_MaxX },
    ParamField("faceScaleF_MaxZ", ParamFieldType.F32, 0x98, null, null, null, "女前顔拡大最大倍率") { it.faceScaleF_MaxZ },
    ParamField("qwcId", ParamFieldType.S32, 0x9C, null, null, null, "QWCID") { it.qwcId },
    ParamField("equipModelId", ParamFieldType.U16, 0xA0, null, null, null, "装備モデル番号") { it.equipModelId },
    ParamField("iconIdM", ParamFieldType.U16, 0xA2, null, null, null, "男用アイコンID") { it.iconIdM },
    ParamField("iconIdF", ParamFieldType.U16, 0xA4, null, null, null, "女用アイコンID") { it.iconIdF },
    ParamField("knockBack", ParamFieldType.U16, 0xA6, null, null, null, "ノックバックカット率") { it.knockBack },
    ParamField("knockbackBounceRate", ParamFieldType.U16, 0xA8, null, null, null, "ノックバック反発率") { it.knockbackBounceRate },
    ParamField("durability", ParamFieldType.U16, 0xAA, null, null, null, "耐久度") { it.durability },
    ParamField("durabilityMax", ParamFieldType.U16, 0xAC, null, null, null, "耐久度最大値") { it.durabilityMax },
    ParamField("saDurability", ParamFieldType.S16, 0xAE, null, null, null, "SA耐久値") { it.saDurability },
    ParamField("defFlickPower", ParamFieldType.U16, 0xB0, null, null, null, "はじき防御力") { it.defFlickPower },
    ParamField("defensePhysics", ParamFieldType.U16, 0xB2, null, null, null, "物理防御力") { it.defensePhysics },
    ParamField("defenseMagic", ParamFieldType.U16, 0xB4, null, null, null, "魔法防御力") { it.defenseMagic },
    ParamField("defenseFire", ParamFieldType.U16, 0xB6, null, null, null, "炎防御力") { it.defenseFire },
    ParamField("defenseThunder", ParamFieldType.U16, 0xB8, null, null, null, "電撃防御力") { it.defenseThunder },
    ParamField("defenseSlash", ParamFieldType.S16, 0xBA, null, null, null, "斬撃防御力") { it.defenseSlash },
    ParamField("defenseBlow", ParamFieldType.S16, 0xBC, null, null, null, "打撃防御力") { it.defenseBlow },
    ParamField("defenseThrust", ParamFieldType.S16, 0xBE, null, null, null, "刺突防御力") { it.defenseThrust },
    ParamField("resistPoison", ParamFieldType.U16, 0xC0, null, null, null, "毒耐性") { it.resistPoison },
    ParamField("resistDisease", ParamFieldType.U16, 0xC2, null, null, null, "疫病耐性") { it.resistDisease },
    ParamField("resistBlood", ParamFieldType.U16, 0xC4, null, null, null, "出血耐性") { it.resistBlood },
    ParamField("resistCurse", ParamFieldType.U16, 0xC6, null, null, null, "呪耐性") { it.resistCurse },
    ParamField("reinforceTypeId", ParamFieldType.S16, 0xC8, null, null, null, "強化タイプID") { it.reinforceTypeId },
    ParamField("trophySGradeId", ParamFieldType.S16, 0xCA, null, null, null, "トロフィー") { it.trophySGradeId },
    ParamField("shopLv", ParamFieldType.S16, 0xCC, null, null, null, "ショップレベル") { it.shopLv },
    ParamField("knockbackParamId", ParamFieldType.U8, 0xCE, null, null, null, "ノックバックパラメータID") { it.knockbackParamId },
    ParamField("flickDamageCutRate", ParamFieldType.U8, 0xCF, null, null, null, "はじき時ダメージ減衰率[%]") { it.flickDamageCutRate },
    ParamField("equipModelCategory", ParamFieldType.U8, 0xD0, null, null, "EQUIP_MODEL_CATEGORY", "装備モデル種別") { it.equipModelCategory },
    ParamField("equipModelGender", ParamFieldType.U8, 0xD1, null, null, "EQUIP_MODEL_GENDER", "装備モデル性別") { it.equipModelGender },
    ParamField("protectorCategory", ParamFieldType.U8, 0xD2, null, null, "PROTECTOR_CATEGORY", "防具カテゴリ") { it.protectorCategory },
    ParamField("defenseMaterial", ParamFieldType.U8, 0xD3, null, null, "WEP_MATERIAL_DEF", "防御材質【SE】") { it.defenseMaterial },
    ParamField("defenseMaterialSfx", ParamFieldType.U8, 0xD4, null, null, "WEP_MATERIAL_DEF_SFX", "防御材質【SFX】") { it.defenseMaterialSfx },
    ParamField("partsDmgType", ParamFieldType.U8, 0xD5, null, null, "ATK_PARAM_PARTSDMGTYPE", "部位ダメージ適用攻撃") { it.partsDmgType },
    ParamField("defenseMaterial_Weak", ParamFieldType.U8, 0xD6, null, null, "WEP_MATERIAL_DEF", "弱点防御材質【SE】") { it.defenseMaterial_Weak },
    ParamField("defenseMaterialSfx_Weak", ParamFieldType.U8, 0xD7, null, null, "WEP_MATERIAL_DEF_SFX", "弱点防御材質【SFX】") { it.defenseMaterialSfx_Weak },
    ParamField("isDeposit", ParamFieldType.U8, 0xD8, 0, 1, "EQUIP_BOOL", "預けれるか") { it.isDeposit },
    ParamField("headEquip", ParamFieldType.U8, 0xD8, 1, 1, "EQUIP_BOOL", "頭装備") { it.headEquip },
    ParamField("bodyEquip", ParamFieldType.U8, 0xD8, 2, 1, "EQUIP_BOOL", "胴装備") { it.bodyEquip },
    ParamField("armEquip", ParamFieldType.U8, 0xD8, 3, 1, "EQUIP_BOOL", "腕装備") { it.armEquip },
    ParamField("legEquip", ParamFieldType.U8, 0xD8, 4, 1, "EQUIP_BOOL", "脚装備") { it.legEquip },
    ParamField("useFaceScale", ParamFieldType.U8, 0xD8, 5, 1, "EQUIP_BOOL", "顔スケールを使用するか") { it.useFaceScale },
    ParamField("invisibleFlag00", ParamFieldType.U8, 0xD8, 6, 1, null, "#00#非表示") { it.invisibleFlag00 },
    ParamField("invisibleFlag01", ParamFieldType.U8, 0xD8, 7, 1, null, "#01#非表示") { it.invisibleFlag01 },
    ParamField("invisibleFlag02", ParamFieldType.U8, 0xD9, 0, 1, null, "#02#非表示") { it.invisibleFlag02 },
    ParamField("invisibleFlag03", ParamFieldType.U8, 0xD9, 1, 1, null, "#03#非表示") { it.invisibleFlag03 },
    ParamField("invisibleFlag04", ParamFieldType.U8, 0xD9, 2, 1, null, "#04#非表示") { it.invisibleFlag04 },
    ParamField("invisibleFlag05", ParamFieldType.U8, 0xD9, 3, 1, null, "#05#非表示") { it.invisibleFlag05 },
    ParamField("invisibleFlag06", ParamFieldType.U8, 0xD9, 4, 1, null, "#06#非表示") { it.invisibleFlag06 },
    ParamField("invisibleFlag07", ParamFieldType.U8, 0xD9, 5, 1, null, "#07#非表示") { it.invisibleFlag07 },
    ParamField("invisibleFlag08", ParamFieldType.U8, 0xD9, 6, 1, null, "#08#非表示") { it.invisibleFlag08 },
    ParamField("invisibleFlag09", ParamFieldType.U8, 0xD9, 7, 1, null, "#09#非表示") { it.invisibleFlag09 },
    ParamField("invisibleFlag10", ParamFieldType.U8, 0xDA, 0, 1, null, "#10#非表示") { it.invisibleFlag10 },
    ParamField("invisibleFlag11", ParamFieldType.U8, 0xDA, 1, 1, null, "#11#非表示") { it.invisibleFlag11 },
    ParamField("invisibleFlag12", ParamFieldType.U8, 0xDA, 2, 1, null, "#12#非表示") { it.invisibleFlag12 },
    ParamField("invisibleFlag13", ParamFieldType.U8, 0xDA, 3, 1, null, "#13#非表示") { it.invisibleFlag13 },
    ParamField("invisibleFlag14", ParamFieldType.U8, 0xDA, 4, 1, null, "#14#非表示") { it.invisibleFlag14 },
    ParamField("invisibleFlag15", ParamFieldType.U8, 0xDA, 5, 1, null, "#15#非表示") { it.invisibleFlag15 },
    ParamField("invisibleFlag16", ParamFieldType.U8, 0xDA, 6, 1, null, "#16#非表示") { it.invisibleFlag16 },
    ParamField("invisibleFlag17", ParamFieldType.U8, 0xDA, 7, 1, null, "#17#非表示") { it.invisibleFlag17 },
    ParamField("invisibleFlag18", ParamFieldType.U8, 0xDB, 0, 1, null, "#18#非表示") { it.invisibleFlag18 },
    ParamField("invisibleFlag19", ParamFieldType.U8, 0xDB, 1, 1, null, "#19#非表示") { it.invisibleFlag19 },
    ParamField("invisibleFlag20", ParamFieldType.U8, 0xDB, 2, 1, null, "#20#非表示") { it.invisibleFlag20 },
    ParamField("invisibleFlag21", ParamFieldType.U8, 0xDB, 3, 1, null, "#21#非表示") { it.invisibleFlag21 },
    ParamField("invisibleFlag22", ParamFieldType.U8, 0xDB, 4, 1, null, "#22#非表示") { it.invisibleFlag22 },
    ParamField("invisibleFlag23", ParamFieldType.U8, 0xDB, 5, 1, null, "#23#非表示") { it.invisibleFlag23 },
    ParamField("invisibleFlag24", ParamFieldType.U8, 0xDB, 6, 1, null, "#24#非表示") { it.invisibleFlag24 },
    ParamField("invisibleFlag25", ParamFieldType.U8, 0xDB, 7, 1, null, "#25#非表示") { it.invisibleFlag25 },
    ParamField("invisibleFlag26", ParamFieldType.U8, 0xDC, 0, 1, null, "#26#非表示") { it.invisibleFlag26 },
    ParamField("invisibleFlag27", ParamFieldType.U8, 0xDC, 1, 1, null, "#27#非表示") { it.invisibleFlag27 },
    ParamField("invisibleFlag28", ParamFieldType.U8, 0xDC, 2, 1, null, "#28#非表示") { it.invisibleFlag28 },
    ParamField("invisibleFlag29", ParamFieldType.U8, 0xDC, 3, 1, null, "#29#非表示") { it.invisibleFlag29 },
    ParamField("invisibleFlag30", ParamFieldType.U8, 0xDC, 4, 1, null, "#30#非表示") { it.invisibleFlag30 },
    ParamField("invisibleFlag31", ParamFieldType.U8, 0xDC, 5, 1, null, "#31#非表示") { it.invisibleFlag31 },
    ParamField("invisibleFlag32", ParamFieldType.U8, 0xDC, 6, 1, null, "#32#非表示") { it.invisibleFlag32 },
    ParamField("invisibleFlag33", ParamFieldType.U8, 0xDC, 7, 1, null, "#33#非表示") { it.invisibleFlag33 },
    ParamField("invisibleFlag34", ParamFieldType.U8, 0xDD, 0, 1, null, "#34#非表示") { it.invisibleFlag34 },
    ParamField("invisibleFlag35", ParamFieldType.U8, 0xDD, 1, 1, null, "#35#非表示") { it.invisibleFlag35 },
    ParamField("invisibleFlag36", ParamFieldType.U8, 0xDD, 2, 1, null, "#36#非表示") { it.invisibleFlag36 },
    ParamField("invisibleFlag37", ParamFieldType.U8, 0xDD, 3, 1, null, "#37#非表示") { it.invisibleFlag37 },
    ParamField("invisibleFlag38", ParamFieldType.U8, 0xDD, 4, 1, null, "#38#非表示") { it.invisibleFlag38 },
    ParamField("invisibleFlag39", ParamFieldType.U8, 0xDD, 5, 1, null, "#39#非表示") { it.invisibleFlag39 },
    ParamField("invisibleFlag40", ParamFieldType.U8, 0xDD, 6, 1, null, "#40#非表示") { it.invisibleFlag40 },
    ParamField("invisibleFlag41", ParamFieldType.U8, 0xDD, 7, 1, null, "#41#非表示") { it.invisibleFlag41 },
    ParamField("invisibleFlag42", ParamFieldType.U8, 0xDE, 0, 1, null, "#42#非表示") { it.invisibleFlag42 },
    ParamField("invisibleFlag43", ParamFieldType.U8, 0xDE, 1, 1, null, "#43#非表示") { it.invisibleFlag43 },
    ParamField("invisibleFlag44", ParamFieldType.U8, 0xDE, 2, 1, null, "#44#非表示") { it.invisibleFlag44 },
    ParamField("invisibleFlag45", ParamFieldType.U8, 0xDE, 3, 1, null, "#45#非表示") { it.invisibleFlag45 },
    ParamField("invisibleFlag46", ParamFieldType.U8, 0xDE, 4, 1, null, "#46#非表示") { it.invisibleFlag46 },
    ParamField("invisibleFlag47", ParamFieldType.U8, 0xDE, 5, 1, null, "#47#非表示") { it.invisibleFlag47 },
    ParamField("disableMultiDropShare", ParamFieldType.U8, 0xDE, 6, 1, "EQUIP_BOOL", "マルチドロップ共有禁止か") { it.disableMultiDropShare },
    ParamField("simpleModelForDlc", ParamFieldType.U8, 0xDE, 7, 1, "EQUIP_BOOL", "DLC用シンプルモデルありか") { it.simpleModelForDlc },
    ParamField("oldSortId", ParamFieldType.S16, 0xE0, null, null, null, "旧ソートID") { it.oldSortId }
)
