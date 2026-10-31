// GENERATED FILE - do not edit. Regenerate with:
//
//     ./gradlew :tools:generateParamDefs -Pptde=<DATA directory> -Pdsr=<DATA directory>
//
// Source: paramdef.paramdefbnd, EquipParamWeapon.paramdef, read from the installation

@file:Suppress("SpellCheckingInspection")

package com.sappyoak.dsanalyzer.formats.param.defs

import com.sappyoak.dsanalyzer.formats.param.ParamRow
import com.sappyoak.dsanalyzer.formats.param.ParamRowData
import com.sappyoak.dsanalyzer.formats.param.ParamRowType

/** `EQUIP_PARAM_WEAPON_ST`: PrepareToDie 0x110, Remastered 0x110 a row, data version 1 */
public class EquipParamWeaponRow(row: ParamRowData, private val remastered: Boolean) : ParamRow(row) {
    /** `s32` at 0x0 */
    public val behaviorVariationId: Int get() = bytes.s32(0x0)

    /** `s32` at 0x4 */
    public val sortId: Int get() = bytes.s32(0x4)

    /** `u32` at 0x8 */
    public val wanderingEquipId: Long get() = bytes.u32(0x8)

    /** `f32` at 0xC */
    public val weight: Float get() = bytes.f32(0xC)

    /** `f32` at 0x10 */
    public val weaponWeightRate: Float get() = bytes.f32(0x10)

    /** `s32` at 0x14 */
    public val fixPrice: Int get() = bytes.s32(0x14)

    /** `s32` at 0x18 */
    public val basicPrice: Int get() = bytes.s32(0x18)

    /** `s32` at 0x1C */
    public val sellValue: Int get() = bytes.s32(0x1C)

    /** `f32` at 0x20 */
    public val correctStrength: Float get() = bytes.f32(0x20)

    /** `f32` at 0x24 */
    public val correctAgility: Float get() = bytes.f32(0x24)

    /** `f32` at 0x28 */
    public val correctMagic: Float get() = bytes.f32(0x28)

    /** `f32` at 0x2C */
    public val correctFaith: Float get() = bytes.f32(0x2C)

    /** `f32` at 0x30 */
    public val physGuardCutRate: Float get() = bytes.f32(0x30)

    /** `f32` at 0x34 */
    public val magGuardCutRate: Float get() = bytes.f32(0x34)

    /** `f32` at 0x38 */
    public val fireGuardCutRate: Float get() = bytes.f32(0x38)

    /** `f32` at 0x3C */
    public val thunGuardCutRate: Float get() = bytes.f32(0x3C)

    /** `s32` at 0x40 */
    public val spEffectBehaviorId0: Int get() = bytes.s32(0x40)

    /** `s32` at 0x44 */
    public val spEffectBehaviorId1: Int get() = bytes.s32(0x44)

    /** `s32` at 0x48 */
    public val spEffectBehaviorId2: Int get() = bytes.s32(0x48)

    /** `s32` at 0x4C */
    public val residentSpEffectId: Int get() = bytes.s32(0x4C)

    /** `s32` at 0x50 */
    public val residentSpEffectId1: Int get() = bytes.s32(0x50)

    /** `s32` at 0x54 */
    public val residentSpEffectId2: Int get() = bytes.s32(0x54)

    /** `s32` at 0x58 */
    public val materialSetId: Int get() = bytes.s32(0x58)

    /** `s32` at 0x5C */
    public val originEquipWep: Int get() = bytes.s32(0x5C)

    /** `s32` at 0x60 */
    public val originEquipWep1: Int get() = bytes.s32(0x60)

    /** `s32` at 0x64 */
    public val originEquipWep2: Int get() = bytes.s32(0x64)

    /** `s32` at 0x68 */
    public val originEquipWep3: Int get() = bytes.s32(0x68)

    /** `s32` at 0x6C */
    public val originEquipWep4: Int get() = bytes.s32(0x6C)

    /** `s32` at 0x70 */
    public val originEquipWep5: Int get() = bytes.s32(0x70)

    /** `s32` at 0x74 */
    public val originEquipWep6: Int get() = bytes.s32(0x74)

    /** `s32` at 0x78 */
    public val originEquipWep7: Int get() = bytes.s32(0x78)

    /** `s32` at 0x7C */
    public val originEquipWep8: Int get() = bytes.s32(0x7C)

    /** `s32` at 0x80 */
    public val originEquipWep9: Int get() = bytes.s32(0x80)

    /** `s32` at 0x84 */
    public val originEquipWep10: Int get() = bytes.s32(0x84)

    /** `s32` at 0x88 */
    public val originEquipWep11: Int get() = bytes.s32(0x88)

    /** `s32` at 0x8C */
    public val originEquipWep12: Int get() = bytes.s32(0x8C)

    /** `s32` at 0x90 */
    public val originEquipWep13: Int get() = bytes.s32(0x90)

    /** `s32` at 0x94 */
    public val originEquipWep14: Int get() = bytes.s32(0x94)

    /** `s32` at 0x98 */
    public val originEquipWep15: Int get() = bytes.s32(0x98)

    /** `f32` at 0x9C */
    public val antiDemonDamageRate: Float get() = bytes.f32(0x9C)

    /** `f32` at 0xA0 */
    public val antSaintDamageRate: Float get() = bytes.f32(0xA0)

    /** `f32` at 0xA4 */
    public val antWeakA_DamageRate: Float get() = bytes.f32(0xA4)

    /** `f32` at 0xA8 */
    public val antWeakB_DamageRate: Float get() = bytes.f32(0xA8)

    /** `s32` at 0xAC */
    public val vagrantItemLotId: Int get() = bytes.s32(0xAC)

    /** `s32` at 0xB0 */
    public val vagrantBonusEneDropItemLotId: Int get() = bytes.s32(0xB0)

    /** `s32` at 0xB4 */
    public val vagrantItemEneDropItemLotId: Int get() = bytes.s32(0xB4)

    /** `u16` at 0xB8 */
    public val equipModelId: Int get() = bytes.u16(0xB8)

    /** `u16` at 0xBA */
    public val iconId: Int get() = bytes.u16(0xBA)

    /** `u16` at 0xBC */
    public val durability: Int get() = bytes.u16(0xBC)

    /** `u16` at 0xBE */
    public val durabilityMax: Int get() = bytes.u16(0xBE)

    /** `u16` at 0xC0 */
    public val attackThrowEscape: Int get() = bytes.u16(0xC0)

    /** `s16` at 0xC2 */
    public val parryDamageLife: Int get() = bytes.s16(0xC2)

    /** `u16` at 0xC4 */
    public val attackBasePhysics: Int get() = bytes.u16(0xC4)

    /** `u16` at 0xC6 */
    public val attackBaseMagic: Int get() = bytes.u16(0xC6)

    /** `u16` at 0xC8 */
    public val attackBaseFire: Int get() = bytes.u16(0xC8)

    /** `u16` at 0xCA */
    public val attackBaseThunder: Int get() = bytes.u16(0xCA)

    /** `u16` at 0xCC */
    public val attackBaseStamina: Int get() = bytes.u16(0xCC)

    /** `u16` at 0xCE */
    public val saWeaponDamage: Int get() = bytes.u16(0xCE)

    /** `s16` at 0xD0 */
    public val saDurability: Int get() = bytes.s16(0xD0)

    /** `s16` at 0xD2 */
    public val guardAngle: Int get() = bytes.s16(0xD2)

    /** `s16` at 0xD4 */
    public val staminaGuardDef: Int get() = bytes.s16(0xD4)

    /** `s16` at 0xD6 */
    public val reinforceTypeId: Int get() = bytes.s16(0xD6)

    /** `s16` at 0xD8 */
    public val trophySGradeId: Int get() = bytes.s16(0xD8)

    /** `s16` at 0xDA */
    public val trophySeqId: Int get() = bytes.s16(0xDA)

    /** `s16` at 0xDC */
    public val throwAtkRate: Int get() = bytes.s16(0xDC)

    /** `s16` at 0xDE */
    public val bowDistRate: Int get() = bytes.s16(0xDE)

    /** `u8` at 0xE0, `EQUIP_MODEL_CATEGORY` */
    public val equipModelCategory: Int get() = bytes.u8(0xE0)

    /** `u8` at 0xE1, `EQUIP_MODEL_GENDER` */
    public val equipModelGender: Int get() = bytes.u8(0xE1)

    /** `u8` at 0xE2, `WEAPON_CATEGORY` */
    public val weaponCategory: Int get() = bytes.u8(0xE2)

    /** `u8` at 0xE3, `WEPMOTION_CATEGORY` */
    public val wepmotionCategory: Int get() = bytes.u8(0xE3)

    /** `u8` at 0xE4, `GUARDMOTION_CATEGORY` */
    public val guardmotionCategory: Int get() = bytes.u8(0xE4)

    /** `u8` at 0xE5, `WEP_MATERIAL_ATK` */
    public val atkMaterial: Int get() = bytes.u8(0xE5)

    /** `u8` at 0xE6, `WEP_MATERIAL_DEF` */
    public val defMaterial: Int get() = bytes.u8(0xE6)

    /** `u8` at 0xE7, `WEP_MATERIAL_DEF_SFX` */
    public val defSfxMaterial: Int get() = bytes.u8(0xE7)

    /** `u8` at 0xE8, `WEP_CORRECT_TYPE` */
    public val correctType: Int get() = bytes.u8(0xE8)

    /** `u8` at 0xE9, `ATKPARAM_SPATTR_TYPE` */
    public val spAttribute: Int get() = bytes.u8(0xE9)

    /** `u8` at 0xEA */
    public val spAtkcategory: Int get() = bytes.u8(0xEA)

    /** `u8` at 0xEB */
    public val wepmotionOneHandId: Int get() = bytes.u8(0xEB)

    /** `u8` at 0xEC */
    public val wepmotionBothHandId: Int get() = bytes.u8(0xEC)

    /** `u8` at 0xED */
    public val properStrength: Int get() = bytes.u8(0xED)

    /** `u8` at 0xEE */
    public val properAgility: Int get() = bytes.u8(0xEE)

    /** `u8` at 0xEF */
    public val properMagic: Int get() = bytes.u8(0xEF)

    /** `u8` at 0xF0 */
    public val properFaith: Int get() = bytes.u8(0xF0)

    /** `u8` at 0xF1 */
    public val overStrength: Int get() = bytes.u8(0xF1)

    /** `u8` at 0xF2 */
    public val attackBaseParry: Int get() = bytes.u8(0xF2)

    /** `u8` at 0xF3 */
    public val defenseBaseParry: Int get() = bytes.u8(0xF3)

    /** `u8` at 0xF4 */
    public val guardBaseRepel: Int get() = bytes.u8(0xF4)

    /** `u8` at 0xF5 */
    public val attackBaseRepel: Int get() = bytes.u8(0xF5)

    /** `s8` at 0xF6 */
    public val guardCutCancelRate: Int get() = bytes.s8(0xF6)

    /** `s8` at 0xF7 */
    public val guardLevel: Int get() = bytes.s8(0xF7)

    /** `s8` at 0xF8 */
    public val slashGuardCutRate: Int get() = bytes.s8(0xF8)

    /** `s8` at 0xF9 */
    public val blowGuardCutRate: Int get() = bytes.s8(0xF9)

    /** `s8` at 0xFA */
    public val thrustGuardCutRate: Int get() = bytes.s8(0xFA)

    /** `s8` at 0xFB */
    public val poisonGuardResist: Int get() = bytes.s8(0xFB)

    /** `s8` at 0xFC */
    public val diseaseGuardResist: Int get() = bytes.s8(0xFC)

    /** `s8` at 0xFD */
    public val bloodGuardResist: Int get() = bytes.s8(0xFD)

    /** `s8` at 0xFE */
    public val curseGuardResist: Int get() = bytes.s8(0xFE)

    /** `u8` at 0xFF, `DURABILITY_DIVERGENCE_CATEGORY` */
    public val isDurabilityDivergence: Int get() = bytes.u8(0xFF)

    /** bit 0 of the `u8` at 0x100, `EQUIP_BOOL` */
    public val rightHandEquipable: Boolean get() = bytes.flag(0x100, 0)

    /** bit 1 of the `u8` at 0x100, `EQUIP_BOOL` */
    public val leftHandEquipable: Boolean get() = bytes.flag(0x100, 1)

    /** bit 2 of the `u8` at 0x100, `EQUIP_BOOL` */
    public val bothHandEquipable: Boolean get() = bytes.flag(0x100, 2)

    /** bit 3 of the `u8` at 0x100, `EQUIP_BOOL` */
    public val arrowSlotEquipable: Boolean get() = bytes.flag(0x100, 3)

    /** bit 4 of the `u8` at 0x100, `EQUIP_BOOL` */
    public val boltSlotEquipable: Boolean get() = bytes.flag(0x100, 4)

    /** bit 5 of the `u8` at 0x100, `EQUIP_BOOL` */
    public val enableGuard: Boolean get() = bytes.flag(0x100, 5)

    /** bit 6 of the `u8` at 0x100, `EQUIP_BOOL` */
    public val enableParry: Boolean get() = bytes.flag(0x100, 6)

    /** bit 7 of the `u8` at 0x100, `EQUIP_BOOL` */
    public val enableMagic: Boolean get() = bytes.flag(0x100, 7)

    /** bit 0 of the `u8` at 0x101, `EQUIP_BOOL` */
    public val enableSorcery: Boolean get() = bytes.flag(0x101, 0)

    /** bit 1 of the `u8` at 0x101, `EQUIP_BOOL` */
    public val enableMiracle: Boolean get() = bytes.flag(0x101, 1)

    /** bit 2 of the `u8` at 0x101, `EQUIP_BOOL` */
    public val enableVowMagic: Boolean get() = bytes.flag(0x101, 2)

    /** bit 3 of the `u8` at 0x101, `EQUIP_BOOL` */
    public val isNormalAttackType: Boolean get() = bytes.flag(0x101, 3)

    /** bit 4 of the `u8` at 0x101, `EQUIP_BOOL` */
    public val isBlowAttackType: Boolean get() = bytes.flag(0x101, 4)

    /** bit 5 of the `u8` at 0x101, `EQUIP_BOOL` */
    public val isSlashAttackType: Boolean get() = bytes.flag(0x101, 5)

    /** bit 6 of the `u8` at 0x101, `EQUIP_BOOL` */
    public val isThrustAttackType: Boolean get() = bytes.flag(0x101, 6)

    /** bit 7 of the `u8` at 0x101, `EQUIP_BOOL` */
    public val isEnhance: Boolean get() = bytes.flag(0x101, 7)

    /** bit 0 of the `u8` at 0x102, `EQUIP_BOOL` */
    public val isLuckCorrect: Boolean get() = bytes.flag(0x102, 0)

    /** bit 1 of the `u8` at 0x102, `EQUIP_BOOL` */
    public val isCustom: Boolean get() = bytes.flag(0x102, 1)

    /** bit 2 of the `u8` at 0x102, `EQUIP_BOOL` */
    public val disableBaseChangeReset: Boolean get() = bytes.flag(0x102, 2)

    /** bit 3 of the `u8` at 0x102, `EQUIP_BOOL` */
    public val disableRepair: Boolean get() = bytes.flag(0x102, 3)

    /** bit 4 of the `u8` at 0x102, `EQUIP_BOOL` */
    public val isDarkHand: Boolean get() = bytes.flag(0x102, 4)

    /** bit 5 of the `u8` at 0x102, `EQUIP_BOOL` */
    public val simpleModelForDlc: Boolean get() = bytes.flag(0x102, 5)

    /** bit 6 of the `u8` at 0x102, `EQUIP_BOOL` */
    public val lanternWep: Boolean get() = bytes.flag(0x102, 6)

    /** bit 7 of the `u8` at 0x102, `EQUIP_BOOL` */
    public val isVersusGhostWep: Boolean get() = bytes.flag(0x102, 7)

    /** bits 0-5 of the `u8` at 0x103, `WEP_BASE_CHANGE_CATEGORY` */
    public val baseChangeCategory: Int get() = bytes.bits(0x103, 1, 0, 6)

    /** bit 6 of the `u8` at 0x103, `EQUIP_BOOL` */
    public val isDragonSlayer: Boolean get() = bytes.flag(0x103, 6)

    /** bit 7 of the `u8` at 0x103, `EQUIP_BOOL` */
    public val isDeposit: Boolean get() = bytes.flag(0x103, 7)

    /** bit 0 of the `u8` at 0x104, `EQUIP_BOOL` */
    public val disableMultiDropShare: Boolean get() = bytes.flag(0x104, 0)

    /** `s16` at 0x106 */
    public val oldSortId: Int get() = bytes.s16(0x106)

    /** Remastered: `s16` at 0x108. Remastered only */
    public val levelSyncCorrectID: Int? get() = if (remastered) bytes.s16(0x108) else null

    public companion object {
        public val PrepareToDie: ParamRowType<EquipParamWeaponRow> = ParamRowType("EQUIP_PARAM_WEAPON_ST", 0x110, 1) { EquipParamWeaponRow(it, remastered = false) }
        public val Remastered: ParamRowType<EquipParamWeaponRow> = ParamRowType("EQUIP_PARAM_WEAPON_ST", 0x110, 1) { EquipParamWeaponRow(it, remastered = true) }
    }
}
