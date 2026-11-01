// GENERATED FILE - do not edit. Regenerate with:
//
//     ./gradlew :tools:generateParamDefs -Pptde=<DATA directory> -Pdsr=<DATA directory>
//
// Source: paramdef.paramdefbnd, QwcChangeParam.paramdef, read from the installation

@file:Suppress("SpellCheckingInspection")

package com.sappyoak.dsanalyzer.formats.param.defs

import com.sappyoak.dsanalyzer.formats.param.ParamField
import com.sappyoak.dsanalyzer.formats.param.ParamRow
import com.sappyoak.dsanalyzer.formats.param.ParamRowData
import com.sappyoak.dsanalyzer.formats.param.ParamRowType
import com.sappyoak.dsanalyzer.formats.param.paramdef.ParamFieldType

/** `QWC_CHANGE_PARAM_ST`: PrepareToDie 0x10, Remastered 0x10 a row, data version 1 */
public class QwcChangeParamRow(row: ParamRowData) : ParamRow(row) {
    /** `s16` at 0x0 */
    public val pcAttrB: Int get() = bytes.s16(0x0)

    /** `s16` at 0x2 */
    public val pcAttrW: Int get() = bytes.s16(0x2)

    /** `s16` at 0x4 */
    public val pcAttrL: Int get() = bytes.s16(0x4)

    /** `s16` at 0x6 */
    public val pcAttrR: Int get() = bytes.s16(0x6)

    /** `s16` at 0x8 */
    public val areaAttrB: Int get() = bytes.s16(0x8)

    /** `s16` at 0xA */
    public val areaAttrW: Int get() = bytes.s16(0xA)

    /** `s16` at 0xC */
    public val areaAttrL: Int get() = bytes.s16(0xC)

    /** `s16` at 0xE */
    public val areaAttrR: Int get() = bytes.s16(0xE)

    public companion object : ParamRowType<QwcChangeParamRow>("QWC_CHANGE_PARAM_ST", 0x10, 1, FIELDS, ::QwcChangeParamRow)
}

private val FIELDS: List<ParamField<QwcChangeParamRow>> = listOf<ParamField<QwcChangeParamRow>>(
    ParamField("pcAttrB", ParamFieldType.S16, 0x0, null, null, null, "PC-黒") { it.pcAttrB },
    ParamField("pcAttrW", ParamFieldType.S16, 0x2, null, null, null, "PC-白") { it.pcAttrW },
    ParamField("pcAttrL", ParamFieldType.S16, 0x4, null, null, null, "PC-左") { it.pcAttrL },
    ParamField("pcAttrR", ParamFieldType.S16, 0x6, null, null, null, "PC-右") { it.pcAttrR },
    ParamField("areaAttrB", ParamFieldType.S16, 0x8, null, null, null, "エリア-黒") { it.areaAttrB },
    ParamField("areaAttrW", ParamFieldType.S16, 0xA, null, null, null, "エリア-白") { it.areaAttrW },
    ParamField("areaAttrL", ParamFieldType.S16, 0xC, null, null, null, "エリア-左") { it.areaAttrL },
    ParamField("areaAttrR", ParamFieldType.S16, 0xE, null, null, null, "エリア-右") { it.areaAttrR }
)
