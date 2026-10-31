// GENERATED FILE - do not edit. Regenerate with:
//
//     ./gradlew :tools:generateParamDefs -Pptde=<DATA directory> -Pdsr=<DATA directory>
//
// Source: paramdef.paramdefbnd, QwcJudgeParam.paramdef, read from the installation

@file:Suppress("SpellCheckingInspection")

package com.sappyoak.dsanalyzer.formats.param.defs

import com.sappyoak.dsanalyzer.formats.param.ParamRow
import com.sappyoak.dsanalyzer.formats.param.ParamRowData
import com.sappyoak.dsanalyzer.formats.param.ParamRowType

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

    public companion object : ParamRowType<QwcJudgeParamRow>("QWC_JUDGE_PARAM_ST", 0x10, 1, ::QwcJudgeParamRow)
}
