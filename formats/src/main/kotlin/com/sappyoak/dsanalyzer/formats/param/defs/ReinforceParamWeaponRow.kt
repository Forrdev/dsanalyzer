// GENERATED FILE - do not edit. Regenerate with:
//
//     ./gradlew :tools:generateParamDefs -Pptde=<DATA directory> -Pdsr=<DATA directory>
//
// Source: paramdef.paramdefbnd, ReinforceParamWeapon.paramdef, read from the installation

@file:Suppress("SpellCheckingInspection")

package com.sappyoak.dsanalyzer.formats.param.defs

import com.sappyoak.dsanalyzer.formats.param.ParamRow
import com.sappyoak.dsanalyzer.formats.param.ParamRowData
import com.sappyoak.dsanalyzer.formats.param.ParamRowType

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
        public val PrepareToDie: ParamRowType<ReinforceParamWeaponRow> = ParamRowType("REINFORCE_PARAM_WEAPON_ST", 0x60, 1) { ReinforceParamWeaponRow(it, remastered = false) }
        public val Remastered: ParamRowType<ReinforceParamWeaponRow> = ParamRowType("REINFORCE_PARAM_WEAPON_ST", 0x58, 1) { ReinforceParamWeaponRow(it, remastered = true) }
    }
}
