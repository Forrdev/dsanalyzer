// GENERATED FILE - do not edit. Regenerate with:
//
//     ./gradlew :tools:generateParamDefs -Pptde=<DATA directory> -Pdsr=<DATA directory>
//
// Source: paramdef.paramdefbnd, ReinforceParamWeapon.paramdef, read from the installation

@file:Suppress("SpellCheckingInspection")

package com.sappyoak.dsanalyzer.formats.param.defs

import com.sappyoak.dsanalyzer.formats.param.ParamField
import com.sappyoak.dsanalyzer.formats.param.ParamRow
import com.sappyoak.dsanalyzer.formats.param.ParamRowData
import com.sappyoak.dsanalyzer.formats.param.ParamRowType
import com.sappyoak.dsanalyzer.formats.param.paramdef.ParamFieldType

/** `REINFORCE_PARAM_WEAPON_ST`: PrepareToDie 0x60, Remastered 0x58 a row, data version 1 */
public class ReinforceParamWeaponRow(row: ParamRowData, private val remastered: Boolean) : ParamRow(row) {
    /** `f32` at 0x0 */
    public val physicsAtkRate: Float get() = bytes.f32(0x0)

    /** `f32` at 0x4 */
    public val magicAtkRate: Float get() = bytes.f32(0x4)

    /** `f32` at 0x8 */
    public val fireAtkRate: Float get() = bytes.f32(0x8)

    /** `f32` at 0xC */
    public val thunderAtkRate: Float get() = bytes.f32(0xC)

    /** `f32` at 0x10 */
    public val staminaAtkRate: Float get() = bytes.f32(0x10)

    /** `f32` at 0x14 */
    public val saWeaponAtkRate: Float get() = bytes.f32(0x14)

    /** `f32` at 0x18 */
    public val saDurabilityRate: Float get() = bytes.f32(0x18)

    /** `f32` at 0x1C */
    public val correctStrengthRate: Float get() = bytes.f32(0x1C)

    /** `f32` at 0x20 */
    public val correctAgilityRate: Float get() = bytes.f32(0x20)

    /** `f32` at 0x24 */
    public val correctMagicRate: Float get() = bytes.f32(0x24)

    /** `f32` at 0x28 */
    public val correctFaithRate: Float get() = bytes.f32(0x28)

    /** `f32` at 0x2C */
    public val physicsGuardCutRate: Float get() = bytes.f32(0x2C)

    /** `f32` at 0x30 */
    public val magicGuardCutRate: Float get() = bytes.f32(0x30)

    /** `f32` at 0x34 */
    public val fireGuardCutRate: Float get() = bytes.f32(0x34)

    /** `f32` at 0x38 */
    public val thunderGuardCutRate: Float get() = bytes.f32(0x38)

    /** `f32` at 0x3C */
    public val poisonGuardResistRate: Float get() = bytes.f32(0x3C)

    /** `f32` at 0x40 */
    public val diseaseGuardResistRate: Float get() = bytes.f32(0x40)

    /** `f32` at 0x44 */
    public val bloodGuardResistRate: Float get() = bytes.f32(0x44)

    /** `f32` at 0x48 */
    public val curseGuardResistRate: Float get() = bytes.f32(0x48)

    /** `f32` at 0x4C */
    public val staminaGuardDefRate: Float get() = bytes.f32(0x4C)

    /** `u8` at 0x50 */
    public val spEffectId1: Int get() = bytes.u8(0x50)

    /** `u8` at 0x51 */
    public val spEffectId2: Int get() = bytes.u8(0x51)

    /** `u8` at 0x52 */
    public val spEffectId3: Int get() = bytes.u8(0x52)

    /** `u8` at 0x53 */
    public val residentSpEffectId1: Int get() = bytes.u8(0x53)

    /** `u8` at 0x54 */
    public val residentSpEffectId2: Int get() = bytes.u8(0x54)

    /** `u8` at 0x55 */
    public val residentSpEffectId3: Int get() = bytes.u8(0x55)

    /** `u8` at 0x56 */
    public val materialSetId: Int get() = bytes.u8(0x56)

    /** Remastered: `u8` at 0x57. Remastered only */
    public val reinforcementLevel: Int? get() = if (remastered) bytes.u8(0x57) else null

    public companion object {
        public val PrepareToDie: ParamRowType<ReinforceParamWeaponRow> = ParamRowType("REINFORCE_PARAM_WEAPON_ST", 0x60, 1, PREPARE_TO_DIE_FIELDS) { ReinforceParamWeaponRow(it, remastered = false) }
        public val Remastered: ParamRowType<ReinforceParamWeaponRow> = ParamRowType("REINFORCE_PARAM_WEAPON_ST", 0x58, 1, REMASTERED_FIELDS) { ReinforceParamWeaponRow(it, remastered = true) }
    }
}

private val PREPARE_TO_DIE_FIELDS: List<ParamField<ReinforceParamWeaponRow>> = listOf<ParamField<ReinforceParamWeaponRow>>(
    ParamField("physicsAtkRate", ParamFieldType.F32, 0x0, null, null, null, "物理攻撃力基本値") { it.physicsAtkRate },
    ParamField("magicAtkRate", ParamFieldType.F32, 0x4, null, null, null, "魔法攻撃力基本値") { it.magicAtkRate },
    ParamField("fireAtkRate", ParamFieldType.F32, 0x8, null, null, null, "炎攻撃力基本値") { it.fireAtkRate },
    ParamField("thunderAtkRate", ParamFieldType.F32, 0xC, null, null, null, "電撃攻撃力基本値") { it.thunderAtkRate },
    ParamField("staminaAtkRate", ParamFieldType.F32, 0x10, null, null, null, "スタミナ攻撃力") { it.staminaAtkRate },
    ParamField("saWeaponAtkRate", ParamFieldType.F32, 0x14, null, null, null, "SA武器攻撃力") { it.saWeaponAtkRate },
    ParamField("saDurabilityRate", ParamFieldType.F32, 0x18, null, null, null, "SA耐久値") { it.saDurabilityRate },
    ParamField("correctStrengthRate", ParamFieldType.F32, 0x1C, null, null, null, "筋力補正") { it.correctStrengthRate },
    ParamField("correctAgilityRate", ParamFieldType.F32, 0x20, null, null, null, "俊敏補正") { it.correctAgilityRate },
    ParamField("correctMagicRate", ParamFieldType.F32, 0x24, null, null, null, "魔力補正") { it.correctMagicRate },
    ParamField("correctFaithRate", ParamFieldType.F32, 0x28, null, null, null, "信仰補正") { it.correctFaithRate },
    ParamField("physicsGuardCutRate", ParamFieldType.F32, 0x2C, null, null, null, "ガード時物理攻撃カット率") { it.physicsGuardCutRate },
    ParamField("magicGuardCutRate", ParamFieldType.F32, 0x30, null, null, null, "ガード時魔法攻撃カット率") { it.magicGuardCutRate },
    ParamField("fireGuardCutRate", ParamFieldType.F32, 0x34, null, null, null, "ガード時炎攻撃カット率") { it.fireGuardCutRate },
    ParamField("thunderGuardCutRate", ParamFieldType.F32, 0x38, null, null, null, "ガード時電撃攻撃カット率") { it.thunderGuardCutRate },
    ParamField("poisonGuardResistRate", ParamFieldType.F32, 0x3C, null, null, null, "ガード時毒攻撃カット率") { it.poisonGuardResistRate },
    ParamField("diseaseGuardResistRate", ParamFieldType.F32, 0x40, null, null, null, "ガード時疫病攻撃カット率") { it.diseaseGuardResistRate },
    ParamField("bloodGuardResistRate", ParamFieldType.F32, 0x44, null, null, null, "ガード時出血攻撃カット率") { it.bloodGuardResistRate },
    ParamField("curseGuardResistRate", ParamFieldType.F32, 0x48, null, null, null, "ガード時呪攻撃カット率") { it.curseGuardResistRate },
    ParamField("staminaGuardDefRate", ParamFieldType.F32, 0x4C, null, null, null, "ガード時スタミナ防御力") { it.staminaGuardDefRate },
    ParamField("spEffectId1", ParamFieldType.U8, 0x50, null, null, null, "特殊効果ID1") { it.spEffectId1 },
    ParamField("spEffectId2", ParamFieldType.U8, 0x51, null, null, null, "特殊効果ID2") { it.spEffectId2 },
    ParamField("spEffectId3", ParamFieldType.U8, 0x52, null, null, null, "特殊効果ID3") { it.spEffectId3 },
    ParamField("residentSpEffectId1", ParamFieldType.U8, 0x53, null, null, null, "常駐特殊効果ID1") { it.residentSpEffectId1 },
    ParamField("residentSpEffectId2", ParamFieldType.U8, 0x54, null, null, null, "常駐特殊効果ID2") { it.residentSpEffectId2 },
    ParamField("residentSpEffectId3", ParamFieldType.U8, 0x55, null, null, null, "常駐特殊効果ID3") { it.residentSpEffectId3 },
    ParamField("materialSetId", ParamFieldType.U8, 0x56, null, null, null, "素材ID加算値") { it.materialSetId }
)

private val REMASTERED_FIELDS: List<ParamField<ReinforceParamWeaponRow>> = listOf<ParamField<ReinforceParamWeaponRow>>(
    ParamField("physicsAtkRate", ParamFieldType.F32, 0x0, null, null, null, "物理攻撃力基本値") { it.physicsAtkRate },
    ParamField("magicAtkRate", ParamFieldType.F32, 0x4, null, null, null, "魔法攻撃力基本値") { it.magicAtkRate },
    ParamField("fireAtkRate", ParamFieldType.F32, 0x8, null, null, null, "炎攻撃力基本値") { it.fireAtkRate },
    ParamField("thunderAtkRate", ParamFieldType.F32, 0xC, null, null, null, "電撃攻撃力基本値") { it.thunderAtkRate },
    ParamField("staminaAtkRate", ParamFieldType.F32, 0x10, null, null, null, "スタミナ攻撃力") { it.staminaAtkRate },
    ParamField("saWeaponAtkRate", ParamFieldType.F32, 0x14, null, null, null, "SA武器攻撃力") { it.saWeaponAtkRate },
    ParamField("saDurabilityRate", ParamFieldType.F32, 0x18, null, null, null, "SA耐久値") { it.saDurabilityRate },
    ParamField("correctStrengthRate", ParamFieldType.F32, 0x1C, null, null, null, "筋力補正") { it.correctStrengthRate },
    ParamField("correctAgilityRate", ParamFieldType.F32, 0x20, null, null, null, "俊敏補正") { it.correctAgilityRate },
    ParamField("correctMagicRate", ParamFieldType.F32, 0x24, null, null, null, "魔力補正") { it.correctMagicRate },
    ParamField("correctFaithRate", ParamFieldType.F32, 0x28, null, null, null, "信仰補正") { it.correctFaithRate },
    ParamField("physicsGuardCutRate", ParamFieldType.F32, 0x2C, null, null, null, "ガード時物理攻撃カット率") { it.physicsGuardCutRate },
    ParamField("magicGuardCutRate", ParamFieldType.F32, 0x30, null, null, null, "ガード時魔法攻撃カット率") { it.magicGuardCutRate },
    ParamField("fireGuardCutRate", ParamFieldType.F32, 0x34, null, null, null, "ガード時炎攻撃カット率") { it.fireGuardCutRate },
    ParamField("thunderGuardCutRate", ParamFieldType.F32, 0x38, null, null, null, "ガード時電撃攻撃カット率") { it.thunderGuardCutRate },
    ParamField("poisonGuardResistRate", ParamFieldType.F32, 0x3C, null, null, null, "ガード時毒攻撃カット率") { it.poisonGuardResistRate },
    ParamField("diseaseGuardResistRate", ParamFieldType.F32, 0x40, null, null, null, "ガード時疫病攻撃カット率") { it.diseaseGuardResistRate },
    ParamField("bloodGuardResistRate", ParamFieldType.F32, 0x44, null, null, null, "ガード時出血攻撃カット率") { it.bloodGuardResistRate },
    ParamField("curseGuardResistRate", ParamFieldType.F32, 0x48, null, null, null, "ガード時呪攻撃カット率") { it.curseGuardResistRate },
    ParamField("staminaGuardDefRate", ParamFieldType.F32, 0x4C, null, null, null, "ガード時スタミナ防御力") { it.staminaGuardDefRate },
    ParamField("spEffectId1", ParamFieldType.U8, 0x50, null, null, null, "特殊効果ID1") { it.spEffectId1 },
    ParamField("spEffectId2", ParamFieldType.U8, 0x51, null, null, null, "特殊効果ID2") { it.spEffectId2 },
    ParamField("spEffectId3", ParamFieldType.U8, 0x52, null, null, null, "特殊効果ID3") { it.spEffectId3 },
    ParamField("residentSpEffectId1", ParamFieldType.U8, 0x53, null, null, null, "常駐特殊効果ID1") { it.residentSpEffectId1 },
    ParamField("residentSpEffectId2", ParamFieldType.U8, 0x54, null, null, null, "常駐特殊効果ID2") { it.residentSpEffectId2 },
    ParamField("residentSpEffectId3", ParamFieldType.U8, 0x55, null, null, null, "常駐特殊効果ID3") { it.residentSpEffectId3 },
    ParamField("materialSetId", ParamFieldType.U8, 0x56, null, null, null, "素材ID加算値") { it.materialSetId },
    ParamField("reinforcementLevel", ParamFieldType.U8, 0x57, null, null, null, "武器強化") { checkNotNull(it.reinforcementLevel) }
)
