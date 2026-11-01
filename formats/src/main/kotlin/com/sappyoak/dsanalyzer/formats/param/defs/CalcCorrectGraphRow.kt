// GENERATED FILE - do not edit. Regenerate with:
//
//     ./gradlew :tools:generateParamDefs -Pptde=<DATA directory> -Pdsr=<DATA directory>
//
// Source: paramdef.paramdefbnd, CalcCorrectGraph.paramdef, read from the installation

@file:Suppress("SpellCheckingInspection")

package com.sappyoak.dsanalyzer.formats.param.defs

import com.sappyoak.dsanalyzer.formats.param.ParamField
import com.sappyoak.dsanalyzer.formats.param.ParamRow
import com.sappyoak.dsanalyzer.formats.param.ParamRowData
import com.sappyoak.dsanalyzer.formats.param.ParamRowType
import com.sappyoak.dsanalyzer.formats.param.paramdef.ParamFieldType

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

    public companion object : ParamRowType<CalcCorrectGraphRow>("CACL_CORRECT_GRAPH_ST", 0x50, 1, FIELDS, ::CalcCorrectGraphRow)
}

private val FIELDS: List<ParamField<CalcCorrectGraphRow>> = listOf<ParamField<CalcCorrectGraphRow>>(
    ParamField("stageMaxVal0", ParamFieldType.F32, 0x0, null, null, null, "閾値ポイント0") { it.stageMaxVal0 },
    ParamField("stageMaxVal1", ParamFieldType.F32, 0x4, null, null, null, "閾値ポイント1") { it.stageMaxVal1 },
    ParamField("stageMaxVal2", ParamFieldType.F32, 0x8, null, null, null, "閾値ポイント2") { it.stageMaxVal2 },
    ParamField("stageMaxVal3", ParamFieldType.F32, 0xC, null, null, null, "閾値ポイント3") { it.stageMaxVal3 },
    ParamField("stageMaxVal4", ParamFieldType.F32, 0x10, null, null, null, "閾値ポイント4") { it.stageMaxVal4 },
    ParamField("stageMaxGrowVal0", ParamFieldType.F32, 0x14, null, null, null, "閾値係数0") { it.stageMaxGrowVal0 },
    ParamField("stageMaxGrowVal1", ParamFieldType.F32, 0x18, null, null, null, "閾値係数1") { it.stageMaxGrowVal1 },
    ParamField("stageMaxGrowVal2", ParamFieldType.F32, 0x1C, null, null, null, "閾値係数2") { it.stageMaxGrowVal2 },
    ParamField("stageMaxGrowVal3", ParamFieldType.F32, 0x20, null, null, null, "閾値係数3") { it.stageMaxGrowVal3 },
    ParamField("stageMaxGrowVal4", ParamFieldType.F32, 0x24, null, null, null, "閾値係数4") { it.stageMaxGrowVal4 },
    ParamField("adjPt_maxGrowVal0", ParamFieldType.F32, 0x28, null, null, null, "調整係数0") { it.adjPt_maxGrowVal0 },
    ParamField("adjPt_maxGrowVal1", ParamFieldType.F32, 0x2C, null, null, null, "調整係数1") { it.adjPt_maxGrowVal1 },
    ParamField("adjPt_maxGrowVal2", ParamFieldType.F32, 0x30, null, null, null, "調整係数2") { it.adjPt_maxGrowVal2 },
    ParamField("adjPt_maxGrowVal3", ParamFieldType.F32, 0x34, null, null, null, "調整係数3") { it.adjPt_maxGrowVal3 },
    ParamField("adjPt_maxGrowVal4", ParamFieldType.F32, 0x38, null, null, null, "調整係数4") { it.adjPt_maxGrowVal4 },
    ParamField("init_inclination_soul", ParamFieldType.F32, 0x3C, null, null, null, "成長ソウル 初期のグラフの傾きα1") { it.init_inclination_soul },
    ParamField("adjustment_value", ParamFieldType.F32, 0x40, null, null, null, "成長ソウル 初期のsoul調整α2") { it.adjustment_value },
    ParamField("boundry_inclination_soul", ParamFieldType.F32, 0x44, null, null, null, "成長ソウル 閾値後のグラフの傾きに影響α3") { it.boundry_inclination_soul },
    ParamField("boundry_value", ParamFieldType.F32, 0x48, null, null, null, "成長ソウル 閾値 t") { it.boundry_value }
)
