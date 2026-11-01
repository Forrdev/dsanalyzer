// GENERATED FILE - do not edit. Regenerate with:
//
//     ./gradlew :tools:generateParamDefs -Pptde=<DATA directory> -Pdsr=<DATA directory>
//
// Source: paramdef.paramdefbnd, QwcJudgeParam.paramdef, read from the installation

@file:Suppress("SpellCheckingInspection")

package com.sappyoak.dsanalyzer.formats.param.defs

import com.sappyoak.dsanalyzer.formats.param.ParamField
import com.sappyoak.dsanalyzer.formats.param.ParamRow
import com.sappyoak.dsanalyzer.formats.param.ParamRowData
import com.sappyoak.dsanalyzer.formats.param.ParamRowType
import com.sappyoak.dsanalyzer.formats.param.paramdef.ParamFieldType

/** `QWC_JUDGE_PARAM_ST`: PrepareToDie 0x10, Remastered 0x10 a row, data version 1 */
public class QwcJudgeParamRow(row: ParamRowData) : ParamRow(row) {
    /** `s16` at 0x0 */
    public val pcJudgeUnderWB: Int get() = bytes.s16(0x0)

    /** `s16` at 0x2 */
    public val pcJudgeTopWB: Int get() = bytes.s16(0x2)

    /** `s16` at 0x4 */
    public val pcJudgeUnderLR: Int get() = bytes.s16(0x4)

    /** `s16` at 0x6 */
    public val pcJudgeTopLR: Int get() = bytes.s16(0x6)

    /** `s16` at 0x8 */
    public val areaJudgeUnderWB: Int get() = bytes.s16(0x8)

    /** `s16` at 0xA */
    public val areaJudgeTopWB: Int get() = bytes.s16(0xA)

    /** `s16` at 0xC */
    public val areaJudgeUnderLR: Int get() = bytes.s16(0xC)

    /** `s16` at 0xE */
    public val areaJudgeTopLR: Int get() = bytes.s16(0xE)

    public companion object : ParamRowType<QwcJudgeParamRow>("QWC_JUDGE_PARAM_ST", 0x10, 1, FIELDS, ::QwcJudgeParamRow)
}

private val FIELDS: List<ParamField<QwcJudgeParamRow>> = listOf<ParamField<QwcJudgeParamRow>>(
    ParamField("pcJudgeUnderWB", ParamFieldType.S16, 0x0, null, null, null, "PC-黒条件（下限）") { it.pcJudgeUnderWB },
    ParamField("pcJudgeTopWB", ParamFieldType.S16, 0x2, null, null, null, "PC-白条件（上限）") { it.pcJudgeTopWB },
    ParamField("pcJudgeUnderLR", ParamFieldType.S16, 0x4, null, null, null, "PC-左条件（下限）") { it.pcJudgeUnderLR },
    ParamField("pcJudgeTopLR", ParamFieldType.S16, 0x6, null, null, null, "PC-右条件（上限）") { it.pcJudgeTopLR },
    ParamField("areaJudgeUnderWB", ParamFieldType.S16, 0x8, null, null, null, "エリア-黒条件（下限）") { it.areaJudgeUnderWB },
    ParamField("areaJudgeTopWB", ParamFieldType.S16, 0xA, null, null, null, "エリア-白条件（上限）") { it.areaJudgeTopWB },
    ParamField("areaJudgeUnderLR", ParamFieldType.S16, 0xC, null, null, null, "エリア-左条件（下限）") { it.areaJudgeUnderLR },
    ParamField("areaJudgeTopLR", ParamFieldType.S16, 0xE, null, null, null, "エリア-右条件（上限）") { it.areaJudgeTopLR }
)
