// GENERATED FILE - do not edit. Regenerate with:
//
//     ./gradlew :tools:generateParamDefs -Pptde=<DATA directory> -Pdsr=<DATA directory>
//
// Source: paramdef.paramdefbnd, HitMtrlParam.paramdef, read from the installation

@file:Suppress("SpellCheckingInspection")

package com.sappyoak.dsanalyzer.formats.param.defs

import com.sappyoak.dsanalyzer.formats.param.ParamRow
import com.sappyoak.dsanalyzer.formats.param.ParamRowData
import com.sappyoak.dsanalyzer.formats.param.ParamRowType

/** `HIT_MTRL_PARAM_ST`: PrepareToDie 0x10, Remastered 0x10 a row, data version 2 */
public class HitMtrlParamRow(row: ParamRowData) : ParamRow(row) {
    /** `f32` at 0x0 */
    public val aiVolumeRate: Float get() = bytes.f32(0x0)

    /** `s32` at 0x4 */
    public val spEffectIdOnHit0: Int get() = bytes.s32(0x4)

    /** `s32` at 0x8 */
    public val spEffectIdOnHit1: Int get() = bytes.s32(0x8)

    /** bits 0-1 of the `u8` at 0xC, `HMP_FOOT_EFFECT_HEIGHT_TYPE` */
    public val footEffectHeightType: Int get() = bytes.bits(0xC, 1, 0, 2)

    /** bits 2-3 of the `u8` at 0xC, `HMP_FOOT_EFFECT_DIR_TYPE` */
    public val footEffectDirType: Int get() = bytes.bits(0xC, 1, 2, 2)

    /** bits 4-5 of the `u8` at 0xC, `HMP_FLOOR_HEIGHT_TYPE` */
    public val floorHeightType: Int get() = bytes.bits(0xC, 1, 4, 2)

    public companion object : ParamRowType<HitMtrlParamRow>("HIT_MTRL_PARAM_ST", 0x10, 2, ::HitMtrlParamRow)
}
