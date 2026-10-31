// GENERATED FILE - do not edit. Regenerate with:
//
//     ./gradlew :tools:generateParamDefs -Pptde=<DATA directory> -Pdsr=<DATA directory>
//
// Source: paramdef.paramdefbnd, CalcCorrectGraph.paramdef, read from the installation

@file:Suppress("SpellCheckingInspection")

package com.sappyoak.dsanalyzer.formats.param.defs

import com.sappyoak.dsanalyzer.formats.param.ParamRow
import com.sappyoak.dsanalyzer.formats.param.ParamRowData
import com.sappyoak.dsanalyzer.formats.param.ParamRowType

/** `CACL_CORRECT_GRAPH_ST`: PrepareToDie 0x50, Remastered 0x50 a row, data version 1 */
public class CalcCorrectGraphRow(row: ParamRowData) : ParamRow(row) {
    /** `f32` at 0x0 */
    public val stageMaxVal0: Float get() = bytes.f32(0x0)

    /** `f32` at 0x4 */
    public val stageMaxVal1: Float get() = bytes.f32(0x4)

    /** `f32` at 0x8 */
    public val stageMaxVal2: Float get() = bytes.f32(0x8)

    /** `f32` at 0xC */
    public val stageMaxVal3: Float get() = bytes.f32(0xC)

    /** `f32` at 0x10 */
    public val stageMaxVal4: Float get() = bytes.f32(0x10)

    /** `f32` at 0x14 */
    public val stageMaxGrowVal0: Float get() = bytes.f32(0x14)

    /** `f32` at 0x18 */
    public val stageMaxGrowVal1: Float get() = bytes.f32(0x18)

    /** `f32` at 0x1C */
    public val stageMaxGrowVal2: Float get() = bytes.f32(0x1C)

    /** `f32` at 0x20 */
    public val stageMaxGrowVal3: Float get() = bytes.f32(0x20)

    /** `f32` at 0x24 */
    public val stageMaxGrowVal4: Float get() = bytes.f32(0x24)

    /** `f32` at 0x28 */
    public val adjPt_maxGrowVal0: Float get() = bytes.f32(0x28)

    /** `f32` at 0x2C */
    public val adjPt_maxGrowVal1: Float get() = bytes.f32(0x2C)

    /** `f32` at 0x30 */
    public val adjPt_maxGrowVal2: Float get() = bytes.f32(0x30)

    /** `f32` at 0x34 */
    public val adjPt_maxGrowVal3: Float get() = bytes.f32(0x34)

    /** `f32` at 0x38 */
    public val adjPt_maxGrowVal4: Float get() = bytes.f32(0x38)

    /** `f32` at 0x3C */
    public val init_inclination_soul: Float get() = bytes.f32(0x3C)

    /** `f32` at 0x40 */
    public val adjustment_value: Float get() = bytes.f32(0x40)

    /** `f32` at 0x44 */
    public val boundry_inclination_soul: Float get() = bytes.f32(0x44)

    /** `f32` at 0x48 */
    public val boundry_value: Float get() = bytes.f32(0x48)

    public companion object : ParamRowType<CalcCorrectGraphRow>("CACL_CORRECT_GRAPH_ST", 0x50, 1, ::CalcCorrectGraphRow)
}
