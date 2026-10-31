// GENERATED FILE - do not edit. Regenerate with:
//
//     ./gradlew :tools:generateParamDefs -Pptde=<DATA directory> -Pdsr=<DATA directory>
//
// Source: paramdef.paramdefbnd, KnockBackParam.paramdef, read from the installation

@file:Suppress("SpellCheckingInspection")

package com.sappyoak.dsanalyzer.formats.param.defs

import com.sappyoak.dsanalyzer.formats.param.ParamRow
import com.sappyoak.dsanalyzer.formats.param.ParamRowData
import com.sappyoak.dsanalyzer.formats.param.ParamRowType

/** `KNOCKBACK_PARAM_ST`: PrepareToDie 0x80, Remastered 0x80 a row, data version 1 */
public class KnockBackParamRow(row: ParamRowData) : ParamRow(row) {
    /** `f32` at 0x0 */
    public val damage_Min_ContTime: Float get() = bytes.f32(0x0)

    /** `f32` at 0x4 */
    public val damage_S_ContTime: Float get() = bytes.f32(0x4)

    /** `f32` at 0x8 */
    public val damage_M_ContTime: Float get() = bytes.f32(0x8)

    /** `f32` at 0xC */
    public val damage_L_ContTime: Float get() = bytes.f32(0xC)

    /** `f32` at 0x10 */
    public val damage_BlowS_ContTime: Float get() = bytes.f32(0x10)

    /** `f32` at 0x14 */
    public val damage_BlowM_ContTime: Float get() = bytes.f32(0x14)

    /** `f32` at 0x18 */
    public val damage_Strike_ContTime: Float get() = bytes.f32(0x18)

    /** `f32` at 0x1C */
    public val damage_Uppercut_ContTime: Float get() = bytes.f32(0x1C)

    /** `f32` at 0x20 */
    public val damage_Push_ContTime: Float get() = bytes.f32(0x20)

    /** `f32` at 0x24 */
    public val damage_Breath_ContTime: Float get() = bytes.f32(0x24)

    /** `f32` at 0x28 */
    public val damage_HeadShot_ContTime: Float get() = bytes.f32(0x28)

    /** `f32` at 0x2C */
    public val guard_S_ContTime: Float get() = bytes.f32(0x2C)

    /** `f32` at 0x30 */
    public val guard_L_ContTime: Float get() = bytes.f32(0x30)

    /** `f32` at 0x34 */
    public val guard_LL_ContTime: Float get() = bytes.f32(0x34)

    /** `f32` at 0x38 */
    public val guardBrake_ContTime: Float get() = bytes.f32(0x38)

    /** `f32` at 0x3C */
    public val damage_Min_DecTime: Float get() = bytes.f32(0x3C)

    /** `f32` at 0x40 */
    public val damage_S_DecTime: Float get() = bytes.f32(0x40)

    /** `f32` at 0x44 */
    public val damage_M_DecTime: Float get() = bytes.f32(0x44)

    /** `f32` at 0x48 */
    public val damage_L_DecTime: Float get() = bytes.f32(0x48)

    /** `f32` at 0x4C */
    public val damage_BlowS_DecTime: Float get() = bytes.f32(0x4C)

    /** `f32` at 0x50 */
    public val damage_BlowM_DecTime: Float get() = bytes.f32(0x50)

    /** `f32` at 0x54 */
    public val damage_Strike_DecTime: Float get() = bytes.f32(0x54)

    /** `f32` at 0x58 */
    public val damage_Uppercut_DecTime: Float get() = bytes.f32(0x58)

    /** `f32` at 0x5C */
    public val damage_Push_DecTime: Float get() = bytes.f32(0x5C)

    /** `f32` at 0x60 */
    public val damage_Breath_DecTime: Float get() = bytes.f32(0x60)

    /** `f32` at 0x64 */
    public val damage_HeadShot_DecTime: Float get() = bytes.f32(0x64)

    /** `f32` at 0x68 */
    public val guard_S_DecTime: Float get() = bytes.f32(0x68)

    /** `f32` at 0x6C */
    public val guard_L_DecTime: Float get() = bytes.f32(0x6C)

    /** `f32` at 0x70 */
    public val guard_LL_DecTime: Float get() = bytes.f32(0x70)

    /** `f32` at 0x74 */
    public val guardBrake_DecTime: Float get() = bytes.f32(0x74)

    public companion object : ParamRowType<KnockBackParamRow>("KNOCKBACK_PARAM_ST", 0x80, 1, ::KnockBackParamRow)
}
