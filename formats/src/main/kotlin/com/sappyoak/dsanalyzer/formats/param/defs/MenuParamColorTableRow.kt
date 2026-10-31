// GENERATED FILE - do not edit. Regenerate with:
//
//     ./gradlew :tools:generateParamDefs -Pptde=<DATA directory> -Pdsr=<DATA directory>
//
// Source: paramdef.paramdefbnd, MenuParamColorTable.paramdef, read from the installation

@file:Suppress("SpellCheckingInspection")

package com.sappyoak.dsanalyzer.formats.param.defs

import com.sappyoak.dsanalyzer.formats.param.ParamRow
import com.sappyoak.dsanalyzer.formats.param.ParamRowData
import com.sappyoak.dsanalyzer.formats.param.ParamRowType

/** `MENU_PARAM_COLOR_TABLE_ST`: PrepareToDie 0x4, Remastered 0x4 a row, data version 1 */
public class MenuParamColorTableRow(row: ParamRowData) : ParamRow(row) {
    /** `u8` at 0x0 */
    public val r: Int get() = bytes.u8(0x0)

    /** `u8` at 0x1 */
    public val g: Int get() = bytes.u8(0x1)

    /** `u8` at 0x2 */
    public val b: Int get() = bytes.u8(0x2)

    /** `u8` at 0x3 */
    public val a: Int get() = bytes.u8(0x3)

    public companion object : ParamRowType<MenuParamColorTableRow>("MENU_PARAM_COLOR_TABLE_ST", 0x4, 1, ::MenuParamColorTableRow)
}
