// GENERATED FILE - do not edit. Regenerate with:
//
//     ./gradlew :tools:generateParamDefs -Pptde=<DATA directory> -Pdsr=<DATA directory>
//
// Source: paramdef.paramdefbnd, CoolTimeParam.paramdef, read from the installation

@file:Suppress("SpellCheckingInspection")

package com.sappyoak.dsanalyzer.formats.param.defs

import com.sappyoak.dsanalyzer.formats.param.ParamRow
import com.sappyoak.dsanalyzer.formats.param.ParamRowData
import com.sappyoak.dsanalyzer.formats.param.ParamRowType

/** `COOL_TIME_PARAM_ST`: 0x20 bytes (Remastered only) a row, data version 1 */
public class CoolTimeParamRow(row: ParamRowData) : ParamRow(row) {
    /** `f32` at 0x0 */
    public val limitationTime_0: Float get() = bytes.f32(0x0)

    /** `f32` at 0x4 */
    public val observationTime_0: Float get() = bytes.f32(0x4)

    /** `f32` at 0x8 */
    public val limitationTime_1: Float get() = bytes.f32(0x8)

    /** `f32` at 0xC */
    public val observationTime_1: Float get() = bytes.f32(0xC)

    /** `f32` at 0x10 */
    public val limitationTime_2: Float get() = bytes.f32(0x10)

    /** `f32` at 0x14 */
    public val observationTime_2: Float get() = bytes.f32(0x14)

    /** `f32` at 0x18 */
    public val limitationTime_3: Float get() = bytes.f32(0x18)

    /** `f32` at 0x1C */
    public val observationTime_3: Float get() = bytes.f32(0x1C)

    public companion object : ParamRowType<CoolTimeParamRow>("COOL_TIME_PARAM_ST", 0x20, 1, ::CoolTimeParamRow)
}
