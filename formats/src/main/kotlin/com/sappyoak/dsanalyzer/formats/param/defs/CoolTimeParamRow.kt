// GENERATED FILE - do not edit. Regenerate with:
//
//     ./gradlew :tools:generateParamDefs -Pptde=<DATA directory> -Pdsr=<DATA directory>
//
// Source: paramdef.paramdefbnd, CoolTimeParam.paramdef, read from the installation

@file:Suppress("SpellCheckingInspection")

package com.sappyoak.dsanalyzer.formats.param.defs

import com.sappyoak.dsanalyzer.formats.param.ParamField
import com.sappyoak.dsanalyzer.formats.param.ParamRow
import com.sappyoak.dsanalyzer.formats.param.ParamRowData
import com.sappyoak.dsanalyzer.formats.param.ParamRowType
import com.sappyoak.dsanalyzer.formats.param.paramdef.ParamFieldType

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

    public companion object : ParamRowType<CoolTimeParamRow>("COOL_TIME_PARAM_ST", 0x20, 1, FIELDS, ::CoolTimeParamRow)
}

private val FIELDS: List<ParamField<CoolTimeParamRow>> = listOf<ParamField<CoolTimeParamRow>>(
    ParamField("limitationTime_0", ParamFieldType.F32, 0x0, null, null, null, "limitationTime_0") { it.limitationTime_0 },
    ParamField("observationTime_0", ParamFieldType.F32, 0x4, null, null, null, "observationTime_0") { it.observationTime_0 },
    ParamField("limitationTime_1", ParamFieldType.F32, 0x8, null, null, null, "limitationTime_1") { it.limitationTime_1 },
    ParamField("observationTime_1", ParamFieldType.F32, 0xC, null, null, null, "observationTime_1") { it.observationTime_1 },
    ParamField("limitationTime_2", ParamFieldType.F32, 0x10, null, null, null, "limitationTime_2") { it.limitationTime_2 },
    ParamField("observationTime_2", ParamFieldType.F32, 0x14, null, null, null, "observationTime_2") { it.observationTime_2 },
    ParamField("limitationTime_3", ParamFieldType.F32, 0x18, null, null, null, "limitationTime_3") { it.limitationTime_3 },
    ParamField("observationTime_3", ParamFieldType.F32, 0x1C, null, null, null, "observationTime_3") { it.observationTime_3 }
)
