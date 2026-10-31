// GENERATED FILE - do not edit. Regenerate with:
//
//     ./gradlew :tools:generateParamDefs -Pptde=<DATA directory> -Pdsr=<DATA directory>
//
// Source: paramdef.paramdefbnd, ReinforceParamProtector.paramdef, read from the installation

@file:Suppress("SpellCheckingInspection")

package com.sappyoak.dsanalyzer.formats.param.defs

import com.sappyoak.dsanalyzer.formats.param.ParamRow
import com.sappyoak.dsanalyzer.formats.param.ParamRowData
import com.sappyoak.dsanalyzer.formats.param.ParamRowType

/** `REINFORCE_PARAM_PROTECTOR_ST`: PrepareToDie 0x30, Remastered 0x30 a row, data version 1 */
public class ReinforceParamProtectorRow(row: ParamRowData) : ParamRow(row) {
    /** `f32` at 0x0 */
    public val physicsDefRate: Float get() = bytes.f32(0x0)

    /** `f32` at 0x4 */
    public val magicDefRate: Float get() = bytes.f32(0x4)

    /** `f32` at 0x8 */
    public val fireDefRate: Float get() = bytes.f32(0x8)

    /** `f32` at 0xC */
    public val thunderDefRate: Float get() = bytes.f32(0xC)

    /** `f32` at 0x10 */
    public val slashDefRate: Float get() = bytes.f32(0x10)

    /** `f32` at 0x14 */
    public val blowDefRate: Float get() = bytes.f32(0x14)

    /** `f32` at 0x18 */
    public val thrustDefRate: Float get() = bytes.f32(0x18)

    /** `f32` at 0x1C */
    public val resistPoisonRate: Float get() = bytes.f32(0x1C)

    /** `f32` at 0x20 */
    public val resistDiseaseRate: Float get() = bytes.f32(0x20)

    /** `f32` at 0x24 */
    public val resistBloodRate: Float get() = bytes.f32(0x24)

    /** `f32` at 0x28 */
    public val resistCurseRate: Float get() = bytes.f32(0x28)

    /** `u8` at 0x2C */
    public val residentSpEffectId1: Int get() = bytes.u8(0x2C)

    /** `u8` at 0x2D */
    public val residentSpEffectId2: Int get() = bytes.u8(0x2D)

    /** `u8` at 0x2E */
    public val residentSpEffectId3: Int get() = bytes.u8(0x2E)

    /** `u8` at 0x2F */
    public val materialSetId: Int get() = bytes.u8(0x2F)

    public companion object : ParamRowType<ReinforceParamProtectorRow>("REINFORCE_PARAM_PROTECTOR_ST", 0x30, 1, ::ReinforceParamProtectorRow)
}
