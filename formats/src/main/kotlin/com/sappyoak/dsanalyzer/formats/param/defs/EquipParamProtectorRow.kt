// GENERATED FILE - do not edit. Regenerate with:
//
//     ./gradlew :tools:generateParamDefs -Pptde=<DATA directory> -Pdsr=<DATA directory>
//
// Source: paramdef.paramdefbnd, EquipParamProtector.paramdef, read from the installation

@file:Suppress("SpellCheckingInspection")

package com.sappyoak.dsanalyzer.formats.param.defs

import com.sappyoak.dsanalyzer.formats.param.ParamRow
import com.sappyoak.dsanalyzer.formats.param.ParamRowData
import com.sappyoak.dsanalyzer.formats.param.ParamRowType

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

    public companion object : ParamRowType<EquipParamProtectorRow>("EQUIP_PARAM_PROTECTOR_ST", 0xE8, 2, ::EquipParamProtectorRow)
}
