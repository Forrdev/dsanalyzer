// GENERATED FILE - do not edit. Regenerate with:
//
//     ./gradlew :tools:generateParamDefs -Pptde=<DATA directory> -Pdsr=<DATA directory>
//
// Source: paramdef.paramdefbnd, GameAreaParam.paramdef, read from the installation

@file:Suppress("SpellCheckingInspection")

package com.sappyoak.dsanalyzer.formats.param.defs

import com.sappyoak.dsanalyzer.formats.param.ParamRow
import com.sappyoak.dsanalyzer.formats.param.ParamRowData
import com.sappyoak.dsanalyzer.formats.param.ParamRowType

/** `GAME_AREA_PARAM_ST`: PrepareToDie 0x20, Remastered 0x20 a row, data version 1 */
public class GameAreaParamRow(row: ParamRowData) : ParamRow(row) {
    /** `u32` at 0x0 */
    public val bonusSoul_single: Long get() = bytes.u32(0x0)

    /** `u32` at 0x4 */
    public val bonusSoul_multi: Long get() = bytes.u32(0x4)

    /** `s32` at 0x8 */
    public val humanityPointCountFlagIdTop: Int get() = bytes.s32(0x8)

    /** `u16` at 0xC */
    public val humanityDropPoint1: Int get() = bytes.u16(0xC)

    /** `u16` at 0xE */
    public val humanityDropPoint2: Int get() = bytes.u16(0xE)

    /** `u16` at 0x10 */
    public val humanityDropPoint3: Int get() = bytes.u16(0x10)

    /** `u16` at 0x12 */
    public val humanityDropPoint4: Int get() = bytes.u16(0x12)

    /** `u16` at 0x14 */
    public val humanityDropPoint5: Int get() = bytes.u16(0x14)

    /** `u16` at 0x16 */
    public val humanityDropPoint6: Int get() = bytes.u16(0x16)

    /** `u16` at 0x18 */
    public val humanityDropPoint7: Int get() = bytes.u16(0x18)

    /** `u16` at 0x1A */
    public val humanityDropPoint8: Int get() = bytes.u16(0x1A)

    /** `u16` at 0x1C */
    public val humanityDropPoint9: Int get() = bytes.u16(0x1C)

    /** `u16` at 0x1E */
    public val humanityDropPoint10: Int get() = bytes.u16(0x1E)

    public companion object : ParamRowType<GameAreaParamRow>("GAME_AREA_PARAM_ST", 0x20, 1, ::GameAreaParamRow)
}
