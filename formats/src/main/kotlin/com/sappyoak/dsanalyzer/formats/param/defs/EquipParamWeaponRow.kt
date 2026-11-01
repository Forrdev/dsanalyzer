// GENERATED FILE - do not edit. Regenerate with:
//
//     ./gradlew :tools:generateParamDefs -Pptde=<DATA directory> -Pdsr=<DATA directory>
//
// Source: paramdef.paramdefbnd, EquipParamWeapon.paramdef, read from the installation

@file:Suppress("SpellCheckingInspection")

package com.sappyoak.dsanalyzer.formats.param.defs

import com.sappyoak.dsanalyzer.formats.param.ParamField
import com.sappyoak.dsanalyzer.formats.param.ParamRow
import com.sappyoak.dsanalyzer.formats.param.ParamRowData
import com.sappyoak.dsanalyzer.formats.param.ParamRowType
import com.sappyoak.dsanalyzer.formats.param.paramdef.ParamFieldType

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
        public val PrepareToDie: ParamRowType<EquipParamWeaponRow> = ParamRowType("EQUIP_PARAM_WEAPON_ST", 0x110, 1, PREPARE_TO_DIE_FIELDS) { EquipParamWeaponRow(it, remastered = false) }
        public val Remastered: ParamRowType<EquipParamWeaponRow> = ParamRowType("EQUIP_PARAM_WEAPON_ST", 0x110, 1, REMASTERED_FIELDS) { EquipParamWeaponRow(it, remastered = true) }
    }
}

private val PREPARE_TO_DIE_FIELDS: List<ParamField<EquipParamWeaponRow>> = listOf<ParamField<EquipParamWeaponRow>>(
    ParamField("behaviorVariationId", ParamFieldType.S32, 0x0, null, null, null, "行動バリエーションID") { it.behaviorVariationId },
    ParamField("sortId", ParamFieldType.S32, 0x4, null, null, null, "ソートID") { it.sortId },
    ParamField("wanderingEquipId", ParamFieldType.U32, 0x8, null, null, null, "徘徊装備ID") { it.wanderingEquipId },
    ParamField("weight", ParamFieldType.F32, 0xC, null, null, null, "重量[kg]") { it.weight },
    ParamField("weaponWeightRate", ParamFieldType.F32, 0x10, null, null, null, "装備重量比率") { it.weaponWeightRate },
    ParamField("fixPrice", ParamFieldType.S32, 0x14, null, null, null, "修理価格") { it.fixPrice },
    ParamField("basicPrice", ParamFieldType.S32, 0x18, null, null, null, "基本価格") { it.basicPrice },
    ParamField("sellValue", ParamFieldType.S32, 0x1C, null, null, null, "販売価格") { it.sellValue },
    ParamField("correctStrength", ParamFieldType.F32, 0x20, null, null, null, "筋力補正") { it.correctStrength },
    ParamField("correctAgility", ParamFieldType.F32, 0x24, null, null, null, "俊敏補正") { it.correctAgility },
    ParamField("correctMagic", ParamFieldType.F32, 0x28, null, null, null, "魔力補正") { it.correctMagic },
    ParamField("correctFaith", ParamFieldType.F32, 0x2C, null, null, null, "信仰補正") { it.correctFaith },
    ParamField("physGuardCutRate", ParamFieldType.F32, 0x30, null, null, null, "ガード時物理攻撃カット率") { it.physGuardCutRate },
    ParamField("magGuardCutRate", ParamFieldType.F32, 0x34, null, null, null, "ガード時魔法攻撃カット率") { it.magGuardCutRate },
    ParamField("fireGuardCutRate", ParamFieldType.F32, 0x38, null, null, null, "ガード時炎攻撃力カット率") { it.fireGuardCutRate },
    ParamField("thunGuardCutRate", ParamFieldType.F32, 0x3C, null, null, null, "ガード時電撃攻撃力カット率") { it.thunGuardCutRate },
    ParamField("spEffectBehaviorId0", ParamFieldType.S32, 0x40, null, null, null, "特殊効果行動ID0") { it.spEffectBehaviorId0 },
    ParamField("spEffectBehaviorId1", ParamFieldType.S32, 0x44, null, null, null, "特殊効果行動ID1") { it.spEffectBehaviorId1 },
    ParamField("spEffectBehaviorId2", ParamFieldType.S32, 0x48, null, null, null, "特殊効果行動ID2") { it.spEffectBehaviorId2 },
    ParamField("residentSpEffectId", ParamFieldType.S32, 0x4C, null, null, null, "常駐特殊効果ID") { it.residentSpEffectId },
    ParamField("residentSpEffectId1", ParamFieldType.S32, 0x50, null, null, null, "常駐特殊効果ID1") { it.residentSpEffectId1 },
    ParamField("residentSpEffectId2", ParamFieldType.S32, 0x54, null, null, null, "常駐特殊効果ID2") { it.residentSpEffectId2 },
    ParamField("materialSetId", ParamFieldType.S32, 0x58, null, null, null, "素材ID") { it.materialSetId },
    ParamField("originEquipWep", ParamFieldType.S32, 0x5C, null, null, null, "派生元") { it.originEquipWep },
    ParamField("originEquipWep1", ParamFieldType.S32, 0x60, null, null, null, "派生元 強化+1") { it.originEquipWep1 },
    ParamField("originEquipWep2", ParamFieldType.S32, 0x64, null, null, null, "派生元 強化+2") { it.originEquipWep2 },
    ParamField("originEquipWep3", ParamFieldType.S32, 0x68, null, null, null, "派生元 強化+3") { it.originEquipWep3 },
    ParamField("originEquipWep4", ParamFieldType.S32, 0x6C, null, null, null, "派生元 強化+4") { it.originEquipWep4 },
    ParamField("originEquipWep5", ParamFieldType.S32, 0x70, null, null, null, "派生元 強化+5") { it.originEquipWep5 },
    ParamField("originEquipWep6", ParamFieldType.S32, 0x74, null, null, null, "派生元 強化+6") { it.originEquipWep6 },
    ParamField("originEquipWep7", ParamFieldType.S32, 0x78, null, null, null, "派生元 強化+7") { it.originEquipWep7 },
    ParamField("originEquipWep8", ParamFieldType.S32, 0x7C, null, null, null, "派生元 強化+8") { it.originEquipWep8 },
    ParamField("originEquipWep9", ParamFieldType.S32, 0x80, null, null, null, "派生元 強化+9") { it.originEquipWep9 },
    ParamField("originEquipWep10", ParamFieldType.S32, 0x84, null, null, null, "派生元 強化+10") { it.originEquipWep10 },
    ParamField("originEquipWep11", ParamFieldType.S32, 0x88, null, null, null, "派生元 強化+11") { it.originEquipWep11 },
    ParamField("originEquipWep12", ParamFieldType.S32, 0x8C, null, null, null, "派生元 強化+12") { it.originEquipWep12 },
    ParamField("originEquipWep13", ParamFieldType.S32, 0x90, null, null, null, "派生元 強化+13") { it.originEquipWep13 },
    ParamField("originEquipWep14", ParamFieldType.S32, 0x94, null, null, null, "派生元 強化+14") { it.originEquipWep14 },
    ParamField("originEquipWep15", ParamFieldType.S32, 0x98, null, null, null, "派生元 強化+15") { it.originEquipWep15 },
    ParamField("antiDemonDamageRate", ParamFieldType.F32, 0x9C, null, null, null, "対デーモンダメージ倍率") { it.antiDemonDamageRate },
    ParamField("antSaintDamageRate", ParamFieldType.F32, 0xA0, null, null, null, "対神聖ダメージ倍率") { it.antSaintDamageRate },
    ParamField("antWeakA_DamageRate", ParamFieldType.F32, 0xA4, null, null, null, "対弱点Aダメージ倍率") { it.antWeakA_DamageRate },
    ParamField("antWeakB_DamageRate", ParamFieldType.F32, 0xA8, null, null, null, "対弱点Bダメージ倍率") { it.antWeakB_DamageRate },
    ParamField("vagrantItemLotId", ParamFieldType.S32, 0xAC, null, null, null, "ベイグラント時アイテム抽選ID") { it.vagrantItemLotId },
    ParamField("vagrantBonusEneDropItemLotId", ParamFieldType.S32, 0xB0, null, null, null, "ベイグラントボーナス敵ドロップアイテム抽選ID") { it.vagrantBonusEneDropItemLotId },
    ParamField("vagrantItemEneDropItemLotId", ParamFieldType.S32, 0xB4, null, null, null, "ベイグラントアイテム敵ドロップアイテム抽選ID") { it.vagrantItemEneDropItemLotId },
    ParamField("equipModelId", ParamFieldType.U16, 0xB8, null, null, null, "装備モデル番号") { it.equipModelId },
    ParamField("iconId", ParamFieldType.U16, 0xBA, null, null, null, "アイコンID") { it.iconId },
    ParamField("durability", ParamFieldType.U16, 0xBC, null, null, null, "耐久度") { it.durability },
    ParamField("durabilityMax", ParamFieldType.U16, 0xBE, null, null, null, "耐久度最大値") { it.durabilityMax },
    ParamField("attackThrowEscape", ParamFieldType.U16, 0xC0, null, null, null, "投げ抜け攻撃力基本値") { it.attackThrowEscape },
    ParamField("parryDamageLife", ParamFieldType.S16, 0xC2, null, null, null, "パリィ発生時間[frame]") { it.parryDamageLife },
    ParamField("attackBasePhysics", ParamFieldType.U16, 0xC4, null, null, null, "物理攻撃力基本値") { it.attackBasePhysics },
    ParamField("attackBaseMagic", ParamFieldType.U16, 0xC6, null, null, null, "魔法攻撃力基本値") { it.attackBaseMagic },
    ParamField("attackBaseFire", ParamFieldType.U16, 0xC8, null, null, null, "炎攻撃力基本値") { it.attackBaseFire },
    ParamField("attackBaseThunder", ParamFieldType.U16, 0xCA, null, null, null, "電撃攻撃力基本値") { it.attackBaseThunder },
    ParamField("attackBaseStamina", ParamFieldType.U16, 0xCC, null, null, null, "スタミナ攻撃力") { it.attackBaseStamina },
    ParamField("saWeaponDamage", ParamFieldType.U16, 0xCE, null, null, null, "SA武器攻撃力") { it.saWeaponDamage },
    ParamField("saDurability", ParamFieldType.S16, 0xD0, null, null, null, "SA耐久値") { it.saDurability },
    ParamField("guardAngle", ParamFieldType.S16, 0xD2, null, null, null, "ガード範囲[deg]") { it.guardAngle },
    ParamField("staminaGuardDef", ParamFieldType.S16, 0xD4, null, null, null, "ガード時スタミナ防御力") { it.staminaGuardDef },
    ParamField("reinforceTypeId", ParamFieldType.S16, 0xD6, null, null, null, "強化タイプID") { it.reinforceTypeId },
    ParamField("trophySGradeId", ParamFieldType.S16, 0xD8, null, null, null, "トロフィーＳグレードID") { it.trophySGradeId },
    ParamField("trophySeqId", ParamFieldType.S16, 0xDA, null, null, null, "トロフィーSEQ番号") { it.trophySeqId },
    ParamField("throwAtkRate", ParamFieldType.S16, 0xDC, null, null, null, "投げ攻撃力倍率") { it.throwAtkRate },
    ParamField("bowDistRate", ParamFieldType.S16, 0xDE, null, null, null, "弓飛距離補正[％]") { it.bowDistRate },
    ParamField("equipModelCategory", ParamFieldType.U8, 0xE0, null, null, "EQUIP_MODEL_CATEGORY", "装備モデル種別") { it.equipModelCategory },
    ParamField("equipModelGender", ParamFieldType.U8, 0xE1, null, null, "EQUIP_MODEL_GENDER", "装備モデル性別") { it.equipModelGender },
    ParamField("weaponCategory", ParamFieldType.U8, 0xE2, null, null, "WEAPON_CATEGORY", "武器カテゴリ") { it.weaponCategory },
    ParamField("wepmotionCategory", ParamFieldType.U8, 0xE3, null, null, "WEPMOTION_CATEGORY", "武器モーションカテゴリ") { it.wepmotionCategory },
    ParamField("guardmotionCategory", ParamFieldType.U8, 0xE4, null, null, "GUARDMOTION_CATEGORY", "ガードモーションカテゴリ") { it.guardmotionCategory },
    ParamField("atkMaterial", ParamFieldType.U8, 0xE5, null, null, "WEP_MATERIAL_ATK", "攻撃材質") { it.atkMaterial },
    ParamField("defMaterial", ParamFieldType.U8, 0xE6, null, null, "WEP_MATERIAL_DEF", "防御材質") { it.defMaterial },
    ParamField("defSfxMaterial", ParamFieldType.U8, 0xE7, null, null, "WEP_MATERIAL_DEF_SFX", "防御SFX材質") { it.defSfxMaterial },
    ParamField("correctType", ParamFieldType.U8, 0xE8, null, null, "WEP_CORRECT_TYPE", "補正タイプ") { it.correctType },
    ParamField("spAttribute", ParamFieldType.U8, 0xE9, null, null, "ATKPARAM_SPATTR_TYPE", "特殊属性") { it.spAttribute },
    ParamField("spAtkcategory", ParamFieldType.U8, 0xEA, null, null, null, "特殊攻撃カテゴリ") { it.spAtkcategory },
    ParamField("wepmotionOneHandId", ParamFieldType.U8, 0xEB, null, null, null, "武器モーション片手ID") { it.wepmotionOneHandId },
    ParamField("wepmotionBothHandId", ParamFieldType.U8, 0xEC, null, null, null, "武器モーション両手ID") { it.wepmotionBothHandId },
    ParamField("properStrength", ParamFieldType.U8, 0xED, null, null, null, "装備適正筋力") { it.properStrength },
    ParamField("properAgility", ParamFieldType.U8, 0xEE, null, null, null, "装備適正俊敏") { it.properAgility },
    ParamField("properMagic", ParamFieldType.U8, 0xEF, null, null, null, "装備適正魔力") { it.properMagic },
    ParamField("properFaith", ParamFieldType.U8, 0xF0, null, null, null, "装備適正信仰") { it.properFaith },
    ParamField("overStrength", ParamFieldType.U8, 0xF1, null, null, null, "筋力オーバー開始値") { it.overStrength },
    ParamField("attackBaseParry", ParamFieldType.U8, 0xF2, null, null, null, "パリィ攻撃基本値") { it.attackBaseParry },
    ParamField("defenseBaseParry", ParamFieldType.U8, 0xF3, null, null, null, "パリィ防御値") { it.defenseBaseParry },
    ParamField("guardBaseRepel", ParamFieldType.U8, 0xF4, null, null, null, "はじき防御力基本値") { it.guardBaseRepel },
    ParamField("attackBaseRepel", ParamFieldType.U8, 0xF5, null, null, null, "はじき攻撃力基本値") { it.attackBaseRepel },
    ParamField("guardCutCancelRate", ParamFieldType.S8, 0xF6, null, null, null, "ガードカット無効化倍率") { it.guardCutCancelRate },
    ParamField("guardLevel", ParamFieldType.S8, 0xF7, null, null, null, "ガードレベル") { it.guardLevel },
    ParamField("slashGuardCutRate", ParamFieldType.S8, 0xF8, null, null, null, "斬撃攻撃カット率") { it.slashGuardCutRate },
    ParamField("blowGuardCutRate", ParamFieldType.S8, 0xF9, null, null, null, "打撃攻撃カット率") { it.blowGuardCutRate },
    ParamField("thrustGuardCutRate", ParamFieldType.S8, 0xFA, null, null, null, "刺突攻撃カット率") { it.thrustGuardCutRate },
    ParamField("poisonGuardResist", ParamFieldType.S8, 0xFB, null, null, null, "毒耐性カット率") { it.poisonGuardResist },
    ParamField("diseaseGuardResist", ParamFieldType.S8, 0xFC, null, null, null, "疫病攻撃カット率") { it.diseaseGuardResist },
    ParamField("bloodGuardResist", ParamFieldType.S8, 0xFD, null, null, null, "出血攻撃カット率") { it.bloodGuardResist },
    ParamField("curseGuardResist", ParamFieldType.S8, 0xFE, null, null, null, "呪攻撃カット率") { it.curseGuardResist },
    ParamField("isDurabilityDivergence", ParamFieldType.U8, 0xFF, null, null, "DURABILITY_DIVERGENCE_CATEGORY", "耐久度で分岐するか") { it.isDurabilityDivergence },
    ParamField("rightHandEquipable", ParamFieldType.U8, 0x100, 0, 1, "EQUIP_BOOL", "右手装備") { it.rightHandEquipable },
    ParamField("leftHandEquipable", ParamFieldType.U8, 0x100, 1, 1, "EQUIP_BOOL", "左手装備") { it.leftHandEquipable },
    ParamField("bothHandEquipable", ParamFieldType.U8, 0x100, 2, 1, "EQUIP_BOOL", "両手装備") { it.bothHandEquipable },
    ParamField("arrowSlotEquipable", ParamFieldType.U8, 0x100, 3, 1, "EQUIP_BOOL", "弓矢弾装備") { it.arrowSlotEquipable },
    ParamField("boltSlotEquipable", ParamFieldType.U8, 0x100, 4, 1, "EQUIP_BOOL", "弩矢弾装備") { it.boltSlotEquipable },
    ParamField("enableGuard", ParamFieldType.U8, 0x100, 5, 1, "EQUIP_BOOL", "ガード可能") { it.enableGuard },
    ParamField("enableParry", ParamFieldType.U8, 0x100, 6, 1, "EQUIP_BOOL", "パリィ可能") { it.enableParry },
    ParamField("enableMagic", ParamFieldType.U8, 0x100, 7, 1, "EQUIP_BOOL", "魔法可能") { it.enableMagic },
    ParamField("enableSorcery", ParamFieldType.U8, 0x101, 0, 1, "EQUIP_BOOL", "呪術可能") { it.enableSorcery },
    ParamField("enableMiracle", ParamFieldType.U8, 0x101, 1, 1, "EQUIP_BOOL", "奇蹟可能") { it.enableMiracle },
    ParamField("enableVowMagic", ParamFieldType.U8, 0x101, 2, 1, "EQUIP_BOOL", "誓約魔法可能") { it.enableVowMagic },
    ParamField("isNormalAttackType", ParamFieldType.U8, 0x101, 3, 1, "EQUIP_BOOL", "通常") { it.isNormalAttackType },
    ParamField("isBlowAttackType", ParamFieldType.U8, 0x101, 4, 1, "EQUIP_BOOL", "打撃") { it.isBlowAttackType },
    ParamField("isSlashAttackType", ParamFieldType.U8, 0x101, 5, 1, "EQUIP_BOOL", "斬撃") { it.isSlashAttackType },
    ParamField("isThrustAttackType", ParamFieldType.U8, 0x101, 6, 1, "EQUIP_BOOL", "刺突") { it.isThrustAttackType },
    ParamField("isEnhance", ParamFieldType.U8, 0x101, 7, 1, "EQUIP_BOOL", "エンチャント可能か？") { it.isEnhance },
    ParamField("isLuckCorrect", ParamFieldType.U8, 0x102, 0, 1, "EQUIP_BOOL", "運補正あるか") { it.isLuckCorrect },
    ParamField("isCustom", ParamFieldType.U8, 0x102, 1, 1, "EQUIP_BOOL", "強化できるか？") { it.isCustom },
    ParamField("disableBaseChangeReset", ParamFieldType.U8, 0x102, 2, 1, "EQUIP_BOOL", "転職リセット禁止か") { it.disableBaseChangeReset },
    ParamField("disableRepair", ParamFieldType.U8, 0x102, 3, 1, "EQUIP_BOOL", "修理禁止か") { it.disableRepair },
    ParamField("isDarkHand", ParamFieldType.U8, 0x102, 4, 1, "EQUIP_BOOL", "ダークハンドか") { it.isDarkHand },
    ParamField("simpleModelForDlc", ParamFieldType.U8, 0x102, 5, 1, "EQUIP_BOOL", "DLC用シンプルモデルありか") { it.simpleModelForDlc },
    ParamField("lanternWep", ParamFieldType.U8, 0x102, 6, 1, "EQUIP_BOOL", "ランタン武器") { it.lanternWep },
    ParamField("isVersusGhostWep", ParamFieldType.U8, 0x102, 7, 1, "EQUIP_BOOL", "対霊武器") { it.isVersusGhostWep },
    ParamField("baseChangeCategory", ParamFieldType.U8, 0x103, 0, 6, "WEP_BASE_CHANGE_CATEGORY", "武器転職カテゴリ") { it.baseChangeCategory },
    ParamField("isDragonSlayer", ParamFieldType.U8, 0x103, 6, 1, "EQUIP_BOOL", "竜狩りか") { it.isDragonSlayer },
    ParamField("isDeposit", ParamFieldType.U8, 0x103, 7, 1, "EQUIP_BOOL", "預けれるか") { it.isDeposit },
    ParamField("disableMultiDropShare", ParamFieldType.U8, 0x104, 0, 1, "EQUIP_BOOL", "マルチドロップ共有禁止か") { it.disableMultiDropShare },
    ParamField("oldSortId", ParamFieldType.S16, 0x106, null, null, null, "旧ソートID") { it.oldSortId }
)

private val REMASTERED_FIELDS: List<ParamField<EquipParamWeaponRow>> = listOf<ParamField<EquipParamWeaponRow>>(
    ParamField("behaviorVariationId", ParamFieldType.S32, 0x0, null, null, null, "行動バリエーションID") { it.behaviorVariationId },
    ParamField("sortId", ParamFieldType.S32, 0x4, null, null, null, "ソートID") { it.sortId },
    ParamField("wanderingEquipId", ParamFieldType.U32, 0x8, null, null, null, "徘徊装備ID") { it.wanderingEquipId },
    ParamField("weight", ParamFieldType.F32, 0xC, null, null, null, "重量[kg]") { it.weight },
    ParamField("weaponWeightRate", ParamFieldType.F32, 0x10, null, null, null, "装備重量比率") { it.weaponWeightRate },
    ParamField("fixPrice", ParamFieldType.S32, 0x14, null, null, null, "修理価格") { it.fixPrice },
    ParamField("basicPrice", ParamFieldType.S32, 0x18, null, null, null, "基本価格") { it.basicPrice },
    ParamField("sellValue", ParamFieldType.S32, 0x1C, null, null, null, "販売価格") { it.sellValue },
    ParamField("correctStrength", ParamFieldType.F32, 0x20, null, null, null, "筋力補正") { it.correctStrength },
    ParamField("correctAgility", ParamFieldType.F32, 0x24, null, null, null, "俊敏補正") { it.correctAgility },
    ParamField("correctMagic", ParamFieldType.F32, 0x28, null, null, null, "魔力補正") { it.correctMagic },
    ParamField("correctFaith", ParamFieldType.F32, 0x2C, null, null, null, "信仰補正") { it.correctFaith },
    ParamField("physGuardCutRate", ParamFieldType.F32, 0x30, null, null, null, "ガード時物理攻撃カット率") { it.physGuardCutRate },
    ParamField("magGuardCutRate", ParamFieldType.F32, 0x34, null, null, null, "ガード時魔法攻撃カット率") { it.magGuardCutRate },
    ParamField("fireGuardCutRate", ParamFieldType.F32, 0x38, null, null, null, "ガード時炎攻撃力カット率") { it.fireGuardCutRate },
    ParamField("thunGuardCutRate", ParamFieldType.F32, 0x3C, null, null, null, "ガード時電撃攻撃力カット率") { it.thunGuardCutRate },
    ParamField("spEffectBehaviorId0", ParamFieldType.S32, 0x40, null, null, null, "特殊効果行動ID0") { it.spEffectBehaviorId0 },
    ParamField("spEffectBehaviorId1", ParamFieldType.S32, 0x44, null, null, null, "特殊効果行動ID1") { it.spEffectBehaviorId1 },
    ParamField("spEffectBehaviorId2", ParamFieldType.S32, 0x48, null, null, null, "特殊効果行動ID2") { it.spEffectBehaviorId2 },
    ParamField("residentSpEffectId", ParamFieldType.S32, 0x4C, null, null, null, "常駐特殊効果ID") { it.residentSpEffectId },
    ParamField("residentSpEffectId1", ParamFieldType.S32, 0x50, null, null, null, "常駐特殊効果ID1") { it.residentSpEffectId1 },
    ParamField("residentSpEffectId2", ParamFieldType.S32, 0x54, null, null, null, "常駐特殊効果ID2") { it.residentSpEffectId2 },
    ParamField("materialSetId", ParamFieldType.S32, 0x58, null, null, null, "素材ID") { it.materialSetId },
    ParamField("originEquipWep", ParamFieldType.S32, 0x5C, null, null, null, "派生元") { it.originEquipWep },
    ParamField("originEquipWep1", ParamFieldType.S32, 0x60, null, null, null, "派生元 強化+1") { it.originEquipWep1 },
    ParamField("originEquipWep2", ParamFieldType.S32, 0x64, null, null, null, "派生元 強化+2") { it.originEquipWep2 },
    ParamField("originEquipWep3", ParamFieldType.S32, 0x68, null, null, null, "派生元 強化+3") { it.originEquipWep3 },
    ParamField("originEquipWep4", ParamFieldType.S32, 0x6C, null, null, null, "派生元 強化+4") { it.originEquipWep4 },
    ParamField("originEquipWep5", ParamFieldType.S32, 0x70, null, null, null, "派生元 強化+5") { it.originEquipWep5 },
    ParamField("originEquipWep6", ParamFieldType.S32, 0x74, null, null, null, "派生元 強化+6") { it.originEquipWep6 },
    ParamField("originEquipWep7", ParamFieldType.S32, 0x78, null, null, null, "派生元 強化+7") { it.originEquipWep7 },
    ParamField("originEquipWep8", ParamFieldType.S32, 0x7C, null, null, null, "派生元 強化+8") { it.originEquipWep8 },
    ParamField("originEquipWep9", ParamFieldType.S32, 0x80, null, null, null, "派生元 強化+9") { it.originEquipWep9 },
    ParamField("originEquipWep10", ParamFieldType.S32, 0x84, null, null, null, "派生元 強化+10") { it.originEquipWep10 },
    ParamField("originEquipWep11", ParamFieldType.S32, 0x88, null, null, null, "派生元 強化+11") { it.originEquipWep11 },
    ParamField("originEquipWep12", ParamFieldType.S32, 0x8C, null, null, null, "派生元 強化+12") { it.originEquipWep12 },
    ParamField("originEquipWep13", ParamFieldType.S32, 0x90, null, null, null, "派生元 強化+13") { it.originEquipWep13 },
    ParamField("originEquipWep14", ParamFieldType.S32, 0x94, null, null, null, "派生元 強化+14") { it.originEquipWep14 },
    ParamField("originEquipWep15", ParamFieldType.S32, 0x98, null, null, null, "派生元 強化+15") { it.originEquipWep15 },
    ParamField("antiDemonDamageRate", ParamFieldType.F32, 0x9C, null, null, null, "対デーモンダメージ倍率") { it.antiDemonDamageRate },
    ParamField("antSaintDamageRate", ParamFieldType.F32, 0xA0, null, null, null, "対神聖ダメージ倍率") { it.antSaintDamageRate },
    ParamField("antWeakA_DamageRate", ParamFieldType.F32, 0xA4, null, null, null, "対弱点Aダメージ倍率") { it.antWeakA_DamageRate },
    ParamField("antWeakB_DamageRate", ParamFieldType.F32, 0xA8, null, null, null, "対弱点Bダメージ倍率") { it.antWeakB_DamageRate },
    ParamField("vagrantItemLotId", ParamFieldType.S32, 0xAC, null, null, null, "ベイグラント時アイテム抽選ID") { it.vagrantItemLotId },
    ParamField("vagrantBonusEneDropItemLotId", ParamFieldType.S32, 0xB0, null, null, null, "ベイグラントボーナス敵ドロップアイテム抽選ID") { it.vagrantBonusEneDropItemLotId },
    ParamField("vagrantItemEneDropItemLotId", ParamFieldType.S32, 0xB4, null, null, null, "ベイグラントアイテム敵ドロップアイテム抽選ID") { it.vagrantItemEneDropItemLotId },
    ParamField("equipModelId", ParamFieldType.U16, 0xB8, null, null, null, "装備モデル番号") { it.equipModelId },
    ParamField("iconId", ParamFieldType.U16, 0xBA, null, null, null, "アイコンID") { it.iconId },
    ParamField("durability", ParamFieldType.U16, 0xBC, null, null, null, "耐久度") { it.durability },
    ParamField("durabilityMax", ParamFieldType.U16, 0xBE, null, null, null, "耐久度最大値") { it.durabilityMax },
    ParamField("attackThrowEscape", ParamFieldType.U16, 0xC0, null, null, null, "投げ抜け攻撃力基本値") { it.attackThrowEscape },
    ParamField("parryDamageLife", ParamFieldType.S16, 0xC2, null, null, null, "パリィ発生時間[frame]") { it.parryDamageLife },
    ParamField("attackBasePhysics", ParamFieldType.U16, 0xC4, null, null, null, "物理攻撃力基本値") { it.attackBasePhysics },
    ParamField("attackBaseMagic", ParamFieldType.U16, 0xC6, null, null, null, "魔法攻撃力基本値") { it.attackBaseMagic },
    ParamField("attackBaseFire", ParamFieldType.U16, 0xC8, null, null, null, "炎攻撃力基本値") { it.attackBaseFire },
    ParamField("attackBaseThunder", ParamFieldType.U16, 0xCA, null, null, null, "電撃攻撃力基本値") { it.attackBaseThunder },
    ParamField("attackBaseStamina", ParamFieldType.U16, 0xCC, null, null, null, "スタミナ攻撃力") { it.attackBaseStamina },
    ParamField("saWeaponDamage", ParamFieldType.U16, 0xCE, null, null, null, "SA武器攻撃力") { it.saWeaponDamage },
    ParamField("saDurability", ParamFieldType.S16, 0xD0, null, null, null, "SA耐久値") { it.saDurability },
    ParamField("guardAngle", ParamFieldType.S16, 0xD2, null, null, null, "ガード範囲[deg]") { it.guardAngle },
    ParamField("staminaGuardDef", ParamFieldType.S16, 0xD4, null, null, null, "ガード時スタミナ防御力") { it.staminaGuardDef },
    ParamField("reinforceTypeId", ParamFieldType.S16, 0xD6, null, null, null, "強化タイプID") { it.reinforceTypeId },
    ParamField("trophySGradeId", ParamFieldType.S16, 0xD8, null, null, null, "トロフィーＳグレードID") { it.trophySGradeId },
    ParamField("trophySeqId", ParamFieldType.S16, 0xDA, null, null, null, "トロフィーSEQ番号") { it.trophySeqId },
    ParamField("throwAtkRate", ParamFieldType.S16, 0xDC, null, null, null, "投げ攻撃力倍率") { it.throwAtkRate },
    ParamField("bowDistRate", ParamFieldType.S16, 0xDE, null, null, null, "弓飛距離補正[％]") { it.bowDistRate },
    ParamField("equipModelCategory", ParamFieldType.U8, 0xE0, null, null, "EQUIP_MODEL_CATEGORY", "装備モデル種別") { it.equipModelCategory },
    ParamField("equipModelGender", ParamFieldType.U8, 0xE1, null, null, "EQUIP_MODEL_GENDER", "装備モデル性別") { it.equipModelGender },
    ParamField("weaponCategory", ParamFieldType.U8, 0xE2, null, null, "WEAPON_CATEGORY", "武器カテゴリ") { it.weaponCategory },
    ParamField("wepmotionCategory", ParamFieldType.U8, 0xE3, null, null, "WEPMOTION_CATEGORY", "武器モーションカテゴリ") { it.wepmotionCategory },
    ParamField("guardmotionCategory", ParamFieldType.U8, 0xE4, null, null, "GUARDMOTION_CATEGORY", "ガードモーションカテゴリ") { it.guardmotionCategory },
    ParamField("atkMaterial", ParamFieldType.U8, 0xE5, null, null, "WEP_MATERIAL_ATK", "攻撃材質") { it.atkMaterial },
    ParamField("defMaterial", ParamFieldType.U8, 0xE6, null, null, "WEP_MATERIAL_DEF", "防御材質") { it.defMaterial },
    ParamField("defSfxMaterial", ParamFieldType.U8, 0xE7, null, null, "WEP_MATERIAL_DEF_SFX", "防御SFX材質") { it.defSfxMaterial },
    ParamField("correctType", ParamFieldType.U8, 0xE8, null, null, "WEP_CORRECT_TYPE", "補正タイプ") { it.correctType },
    ParamField("spAttribute", ParamFieldType.U8, 0xE9, null, null, "ATKPARAM_SPATTR_TYPE", "特殊属性") { it.spAttribute },
    ParamField("spAtkcategory", ParamFieldType.U8, 0xEA, null, null, null, "特殊攻撃カテゴリ") { it.spAtkcategory },
    ParamField("wepmotionOneHandId", ParamFieldType.U8, 0xEB, null, null, null, "武器モーション片手ID") { it.wepmotionOneHandId },
    ParamField("wepmotionBothHandId", ParamFieldType.U8, 0xEC, null, null, null, "武器モーション両手ID") { it.wepmotionBothHandId },
    ParamField("properStrength", ParamFieldType.U8, 0xED, null, null, null, "装備適正筋力") { it.properStrength },
    ParamField("properAgility", ParamFieldType.U8, 0xEE, null, null, null, "装備適正俊敏") { it.properAgility },
    ParamField("properMagic", ParamFieldType.U8, 0xEF, null, null, null, "装備適正魔力") { it.properMagic },
    ParamField("properFaith", ParamFieldType.U8, 0xF0, null, null, null, "装備適正信仰") { it.properFaith },
    ParamField("overStrength", ParamFieldType.U8, 0xF1, null, null, null, "筋力オーバー開始値") { it.overStrength },
    ParamField("attackBaseParry", ParamFieldType.U8, 0xF2, null, null, null, "パリィ攻撃基本値") { it.attackBaseParry },
    ParamField("defenseBaseParry", ParamFieldType.U8, 0xF3, null, null, null, "パリィ防御値") { it.defenseBaseParry },
    ParamField("guardBaseRepel", ParamFieldType.U8, 0xF4, null, null, null, "はじき防御力基本値") { it.guardBaseRepel },
    ParamField("attackBaseRepel", ParamFieldType.U8, 0xF5, null, null, null, "はじき攻撃力基本値") { it.attackBaseRepel },
    ParamField("guardCutCancelRate", ParamFieldType.S8, 0xF6, null, null, null, "ガードカット無効化倍率") { it.guardCutCancelRate },
    ParamField("guardLevel", ParamFieldType.S8, 0xF7, null, null, null, "ガードレベル") { it.guardLevel },
    ParamField("slashGuardCutRate", ParamFieldType.S8, 0xF8, null, null, null, "斬撃攻撃カット率") { it.slashGuardCutRate },
    ParamField("blowGuardCutRate", ParamFieldType.S8, 0xF9, null, null, null, "打撃攻撃カット率") { it.blowGuardCutRate },
    ParamField("thrustGuardCutRate", ParamFieldType.S8, 0xFA, null, null, null, "刺突攻撃カット率") { it.thrustGuardCutRate },
    ParamField("poisonGuardResist", ParamFieldType.S8, 0xFB, null, null, null, "毒耐性カット率") { it.poisonGuardResist },
    ParamField("diseaseGuardResist", ParamFieldType.S8, 0xFC, null, null, null, "疫病攻撃カット率") { it.diseaseGuardResist },
    ParamField("bloodGuardResist", ParamFieldType.S8, 0xFD, null, null, null, "出血攻撃カット率") { it.bloodGuardResist },
    ParamField("curseGuardResist", ParamFieldType.S8, 0xFE, null, null, null, "呪攻撃カット率") { it.curseGuardResist },
    ParamField("isDurabilityDivergence", ParamFieldType.U8, 0xFF, null, null, "DURABILITY_DIVERGENCE_CATEGORY", "耐久度で分岐するか") { it.isDurabilityDivergence },
    ParamField("rightHandEquipable", ParamFieldType.U8, 0x100, 0, 1, "EQUIP_BOOL", "右手装備") { it.rightHandEquipable },
    ParamField("leftHandEquipable", ParamFieldType.U8, 0x100, 1, 1, "EQUIP_BOOL", "左手装備") { it.leftHandEquipable },
    ParamField("bothHandEquipable", ParamFieldType.U8, 0x100, 2, 1, "EQUIP_BOOL", "両手装備") { it.bothHandEquipable },
    ParamField("arrowSlotEquipable", ParamFieldType.U8, 0x100, 3, 1, "EQUIP_BOOL", "弓矢弾装備") { it.arrowSlotEquipable },
    ParamField("boltSlotEquipable", ParamFieldType.U8, 0x100, 4, 1, "EQUIP_BOOL", "弩矢弾装備") { it.boltSlotEquipable },
    ParamField("enableGuard", ParamFieldType.U8, 0x100, 5, 1, "EQUIP_BOOL", "ガード可能") { it.enableGuard },
    ParamField("enableParry", ParamFieldType.U8, 0x100, 6, 1, "EQUIP_BOOL", "パリィ可能") { it.enableParry },
    ParamField("enableMagic", ParamFieldType.U8, 0x100, 7, 1, "EQUIP_BOOL", "魔法可能") { it.enableMagic },
    ParamField("enableSorcery", ParamFieldType.U8, 0x101, 0, 1, "EQUIP_BOOL", "呪術可能") { it.enableSorcery },
    ParamField("enableMiracle", ParamFieldType.U8, 0x101, 1, 1, "EQUIP_BOOL", "奇蹟可能") { it.enableMiracle },
    ParamField("enableVowMagic", ParamFieldType.U8, 0x101, 2, 1, "EQUIP_BOOL", "誓約魔法可能") { it.enableVowMagic },
    ParamField("isNormalAttackType", ParamFieldType.U8, 0x101, 3, 1, "EQUIP_BOOL", "通常") { it.isNormalAttackType },
    ParamField("isBlowAttackType", ParamFieldType.U8, 0x101, 4, 1, "EQUIP_BOOL", "打撃") { it.isBlowAttackType },
    ParamField("isSlashAttackType", ParamFieldType.U8, 0x101, 5, 1, "EQUIP_BOOL", "斬撃") { it.isSlashAttackType },
    ParamField("isThrustAttackType", ParamFieldType.U8, 0x101, 6, 1, "EQUIP_BOOL", "刺突") { it.isThrustAttackType },
    ParamField("isEnhance", ParamFieldType.U8, 0x101, 7, 1, "EQUIP_BOOL", "エンチャント可能か？") { it.isEnhance },
    ParamField("isLuckCorrect", ParamFieldType.U8, 0x102, 0, 1, "EQUIP_BOOL", "運補正あるか") { it.isLuckCorrect },
    ParamField("isCustom", ParamFieldType.U8, 0x102, 1, 1, "EQUIP_BOOL", "強化できるか？") { it.isCustom },
    ParamField("disableBaseChangeReset", ParamFieldType.U8, 0x102, 2, 1, "EQUIP_BOOL", "転職リセット禁止か") { it.disableBaseChangeReset },
    ParamField("disableRepair", ParamFieldType.U8, 0x102, 3, 1, "EQUIP_BOOL", "修理禁止か") { it.disableRepair },
    ParamField("isDarkHand", ParamFieldType.U8, 0x102, 4, 1, "EQUIP_BOOL", "ダークハンドか") { it.isDarkHand },
    ParamField("simpleModelForDlc", ParamFieldType.U8, 0x102, 5, 1, "EQUIP_BOOL", "DLC用シンプルモデルありか") { it.simpleModelForDlc },
    ParamField("lanternWep", ParamFieldType.U8, 0x102, 6, 1, "EQUIP_BOOL", "ランタン武器") { it.lanternWep },
    ParamField("isVersusGhostWep", ParamFieldType.U8, 0x102, 7, 1, "EQUIP_BOOL", "対霊武器") { it.isVersusGhostWep },
    ParamField("baseChangeCategory", ParamFieldType.U8, 0x103, 0, 6, "WEP_BASE_CHANGE_CATEGORY", "武器転職カテゴリ") { it.baseChangeCategory },
    ParamField("isDragonSlayer", ParamFieldType.U8, 0x103, 6, 1, "EQUIP_BOOL", "竜狩りか") { it.isDragonSlayer },
    ParamField("isDeposit", ParamFieldType.U8, 0x103, 7, 1, "EQUIP_BOOL", "預けれるか") { it.isDeposit },
    ParamField("disableMultiDropShare", ParamFieldType.U8, 0x104, 0, 1, "EQUIP_BOOL", "マルチドロップ共有禁止か") { it.disableMultiDropShare },
    ParamField("oldSortId", ParamFieldType.S16, 0x106, null, null, null, "旧ソートID") { it.oldSortId },
    ParamField("levelSyncCorrectID", ParamFieldType.S16, 0x108, null, null, null, "レベルの同期ID") { checkNotNull(it.levelSyncCorrectID) }
)
