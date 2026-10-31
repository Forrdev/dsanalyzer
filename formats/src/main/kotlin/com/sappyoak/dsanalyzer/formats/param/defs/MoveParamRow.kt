// GENERATED FILE - do not edit. Regenerate with:
//
//     ./gradlew :tools:generateParamDefs -Pptde=<DATA directory> -Pdsr=<DATA directory>
//
// Source: paramdef.paramdefbnd, MoveParam.paramdef, read from the installation

@file:Suppress("SpellCheckingInspection")

package com.sappyoak.dsanalyzer.formats.param.defs

import com.sappyoak.dsanalyzer.formats.param.ParamRow
import com.sappyoak.dsanalyzer.formats.param.ParamRowData
import com.sappyoak.dsanalyzer.formats.param.ParamRowType

/** `MOVE_PARAM_ST`: PrepareToDie 0x90, Remastered 0x90 a row, data version 1 */
public class MoveParamRow(row: ParamRowData) : ParamRow(row) {
    /** `s32` at 0x0 */
    public val stayId: Int get() = bytes.s32(0x0)

    /** `s32` at 0x4 */
    public val walkF: Int get() = bytes.s32(0x4)

    /** `s32` at 0x8 */
    public val walkB: Int get() = bytes.s32(0x8)

    /** `s32` at 0xC */
    public val walkL: Int get() = bytes.s32(0xC)

    /** `s32` at 0x10 */
    public val walkR: Int get() = bytes.s32(0x10)

    /** `s32` at 0x14 */
    public val dashF: Int get() = bytes.s32(0x14)

    /** `s32` at 0x18 */
    public val dashB: Int get() = bytes.s32(0x18)

    /** `s32` at 0x1C */
    public val dashL: Int get() = bytes.s32(0x1C)

    /** `s32` at 0x20 */
    public val dashR: Int get() = bytes.s32(0x20)

    /** `s32` at 0x24 */
    public val superDash: Int get() = bytes.s32(0x24)

    /** `s32` at 0x28 */
    public val escapeF: Int get() = bytes.s32(0x28)

    /** `s32` at 0x2C */
    public val escapeB: Int get() = bytes.s32(0x2C)

    /** `s32` at 0x30 */
    public val escapeL: Int get() = bytes.s32(0x30)

    /** `s32` at 0x34 */
    public val escapeR: Int get() = bytes.s32(0x34)

    /** `s32` at 0x38 */
    public val turnL: Int get() = bytes.s32(0x38)

    /** `s32` at 0x3C */
    public val trunR: Int get() = bytes.s32(0x3C)

    /** `s32` at 0x40 */
    public val largeTurnL: Int get() = bytes.s32(0x40)

    /** `s32` at 0x44 */
    public val largeTurnR: Int get() = bytes.s32(0x44)

    /** `s32` at 0x48 */
    public val stepMove: Int get() = bytes.s32(0x48)

    /** `s32` at 0x4C */
    public val flyStay: Int get() = bytes.s32(0x4C)

    /** `s32` at 0x50 */
    public val flyWalkF: Int get() = bytes.s32(0x50)

    /** `s32` at 0x54 */
    public val flyWalkFL: Int get() = bytes.s32(0x54)

    /** `s32` at 0x58 */
    public val flyWalkFR: Int get() = bytes.s32(0x58)

    /** `s32` at 0x5C */
    public val flyWalkFL2: Int get() = bytes.s32(0x5C)

    /** `s32` at 0x60 */
    public val flyWalkFR2: Int get() = bytes.s32(0x60)

    /** `s32` at 0x64 */
    public val flyDashF: Int get() = bytes.s32(0x64)

    /** `s32` at 0x68 */
    public val flyDashFL: Int get() = bytes.s32(0x68)

    /** `s32` at 0x6C */
    public val flyDashFR: Int get() = bytes.s32(0x6C)

    /** `s32` at 0x70 */
    public val flyDashFL2: Int get() = bytes.s32(0x70)

    /** `s32` at 0x74 */
    public val flyDashFR2: Int get() = bytes.s32(0x74)

    /** `s32` at 0x78 */
    public val dashEscapeF: Int get() = bytes.s32(0x78)

    /** `s32` at 0x7C */
    public val dashEscapeB: Int get() = bytes.s32(0x7C)

    /** `s32` at 0x80 */
    public val dashEscapeL: Int get() = bytes.s32(0x80)

    /** `s32` at 0x84 */
    public val dashEscapeR: Int get() = bytes.s32(0x84)

    /** `s32` at 0x88 */
    public val analogMoveParamId: Int get() = bytes.s32(0x88)

    public companion object : ParamRowType<MoveParamRow>("MOVE_PARAM_ST", 0x90, 1, ::MoveParamRow)
}
