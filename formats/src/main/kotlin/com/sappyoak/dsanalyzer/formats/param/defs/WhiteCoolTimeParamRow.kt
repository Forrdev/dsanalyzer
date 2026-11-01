// GENERATED FILE - do not edit. Regenerate with:
//
//     ./gradlew :tools:generateParamDefs -Pptde=<DATA directory> -Pdsr=<DATA directory>
//
// Source: paramdef.paramdefbnd, WhiteCoolTimeParam.paramdef, read from the installation

@file:Suppress("SpellCheckingInspection")

package com.sappyoak.dsanalyzer.formats.param.defs

import com.sappyoak.dsanalyzer.formats.param.ParamField
import com.sappyoak.dsanalyzer.formats.param.ParamRow
import com.sappyoak.dsanalyzer.formats.param.ParamRowData
import com.sappyoak.dsanalyzer.formats.param.ParamRowType
import com.sappyoak.dsanalyzer.formats.param.paramdef.ParamFieldType

/** `WHITE_COOL_TIME_PARAM_ST`: 0x10 bytes (Remastered only) a row, data version 1 */
public class WhiteCoolTimeParamRow(row: ParamRowData) : ParamRow(row) {
    /** `f32` at 0x0 */
    public val timeLimit0: Float get() = bytes.f32(0x0)

    /** `f32` at 0x4 */
    public val timeLimit1: Float get() = bytes.f32(0x4)

    /** `f32` at 0x8 */
    public val timeLimit2: Float get() = bytes.f32(0x8)

    /** `f32` at 0xC */
    public val timeLimit3: Float get() = bytes.f32(0xC)

    public companion object : ParamRowType<WhiteCoolTimeParamRow>("WHITE_COOL_TIME_PARAM_ST", 0x10, 1, FIELDS, ::WhiteCoolTimeParamRow)
}

private val FIELDS: List<ParamField<WhiteCoolTimeParamRow>> = listOf<ParamField<WhiteCoolTimeParamRow>>(
    ParamField("timeLimit0", ParamFieldType.F32, 0x0, null, null, null, "timeLimit0") { it.timeLimit0 },
    ParamField("timeLimit1", ParamFieldType.F32, 0x4, null, null, null, "timeLimit1") { it.timeLimit1 },
    ParamField("timeLimit2", ParamFieldType.F32, 0x8, null, null, null, "timeLimit2") { it.timeLimit2 },
    ParamField("timeLimit3", ParamFieldType.F32, 0xC, null, null, null, "timeLimit3") { it.timeLimit3 }
)
