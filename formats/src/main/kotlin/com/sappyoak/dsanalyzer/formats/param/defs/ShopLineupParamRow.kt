// GENERATED FILE - do not edit. Regenerate with:
//
//     ./gradlew :tools:generateParamDefs -Pptde=<DATA directory> -Pdsr=<DATA directory>
//
// Source: paramdef.paramdefbnd, ShopLineupParam.paramdef, read from the installation

@file:Suppress("SpellCheckingInspection")

package com.sappyoak.dsanalyzer.formats.param.defs

import com.sappyoak.dsanalyzer.formats.param.ParamRow
import com.sappyoak.dsanalyzer.formats.param.ParamRowData
import com.sappyoak.dsanalyzer.formats.param.ParamRowType

/** `SHOP_LINEUP_PARAM`: PrepareToDie 0x20, Remastered 0x20 a row, data version 1 */
public class ShopLineupParamRow(row: ParamRowData) : ParamRow(row) {
    /** `s32` at 0x0 */
    public val equipId: Int get() = bytes.s32(0x0)

    /** `s32` at 0x4 */
    public val value: Int get() = bytes.s32(0x4)

    /** `s32` at 0x8 */
    public val mtrlId: Int get() = bytes.s32(0x8)

    /** `s32` at 0xC */
    public val eventFlag: Int get() = bytes.s32(0xC)

    /** `s32` at 0x10 */
    public val qwcId: Int get() = bytes.s32(0x10)

    /** `s16` at 0x14 */
    public val sellQuantity: Int get() = bytes.s16(0x14)

    /** `u8` at 0x16, `SHOP_LINEUP_SHOPTYPE` */
    public val shopType: Int get() = bytes.u8(0x16)

    /** `u8` at 0x17, `SHOP_LINEUP_EQUIPTYPE` */
    public val equipType: Int get() = bytes.u8(0x17)

    public companion object : ParamRowType<ShopLineupParamRow>("SHOP_LINEUP_PARAM", 0x20, 1, ::ShopLineupParamRow)
}
