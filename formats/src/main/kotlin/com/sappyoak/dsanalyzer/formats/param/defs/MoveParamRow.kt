// GENERATED FILE - do not edit. Regenerate with:
//
//     ./gradlew :tools:generateParamDefs -Pptde=<DATA directory> -Pdsr=<DATA directory>
//
// Source: paramdef.paramdefbnd, MoveParam.paramdef, read from the installation

@file:Suppress("SpellCheckingInspection")

package com.sappyoak.dsanalyzer.formats.param.defs

import com.sappyoak.dsanalyzer.formats.param.ParamField
import com.sappyoak.dsanalyzer.formats.param.ParamRow
import com.sappyoak.dsanalyzer.formats.param.ParamRowData
import com.sappyoak.dsanalyzer.formats.param.ParamRowType
import com.sappyoak.dsanalyzer.formats.param.paramdef.ParamFieldType

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

    public companion object : ParamRowType<MoveParamRow>("MOVE_PARAM_ST", 0x90, 1, FIELDS, ::MoveParamRow)
}

private val FIELDS: List<ParamField<MoveParamRow>> = listOf<ParamField<MoveParamRow>>(
    ParamField("stayId", ParamFieldType.S32, 0x0, null, null, null, "待機") { it.stayId },
    ParamField("walkF", ParamFieldType.S32, 0x4, null, null, null, "歩行 前") { it.walkF },
    ParamField("walkB", ParamFieldType.S32, 0x8, null, null, null, "歩行 後") { it.walkB },
    ParamField("walkL", ParamFieldType.S32, 0xC, null, null, null, "歩行 左") { it.walkL },
    ParamField("walkR", ParamFieldType.S32, 0x10, null, null, null, "歩行 右") { it.walkR },
    ParamField("dashF", ParamFieldType.S32, 0x14, null, null, null, "走行 前") { it.dashF },
    ParamField("dashB", ParamFieldType.S32, 0x18, null, null, null, "走行 後") { it.dashB },
    ParamField("dashL", ParamFieldType.S32, 0x1C, null, null, null, "走行 左") { it.dashL },
    ParamField("dashR", ParamFieldType.S32, 0x20, null, null, null, "走行 右") { it.dashR },
    ParamField("superDash", ParamFieldType.S32, 0x24, null, null, null, "ダッシュ移動") { it.superDash },
    ParamField("escapeF", ParamFieldType.S32, 0x28, null, null, null, "緊急回避 前") { it.escapeF },
    ParamField("escapeB", ParamFieldType.S32, 0x2C, null, null, null, "緊急回避 後") { it.escapeB },
    ParamField("escapeL", ParamFieldType.S32, 0x30, null, null, null, "緊急回避 左") { it.escapeL },
    ParamField("escapeR", ParamFieldType.S32, 0x34, null, null, null, "緊急回避 右") { it.escapeR },
    ParamField("turnL", ParamFieldType.S32, 0x38, null, null, null, "90度旋回 左") { it.turnL },
    ParamField("trunR", ParamFieldType.S32, 0x3C, null, null, null, "90度旋回 右") { it.trunR },
    ParamField("largeTurnL", ParamFieldType.S32, 0x40, null, null, null, "180度旋回 左") { it.largeTurnL },
    ParamField("largeTurnR", ParamFieldType.S32, 0x44, null, null, null, "180度旋回 右") { it.largeTurnR },
    ParamField("stepMove", ParamFieldType.S32, 0x48, null, null, null, "ステップ移動") { it.stepMove },
    ParamField("flyStay", ParamFieldType.S32, 0x4C, null, null, null, "飛行待機") { it.flyStay },
    ParamField("flyWalkF", ParamFieldType.S32, 0x50, null, null, null, "飛行前進") { it.flyWalkF },
    ParamField("flyWalkFL", ParamFieldType.S32, 0x54, null, null, null, "飛行左前進") { it.flyWalkFL },
    ParamField("flyWalkFR", ParamFieldType.S32, 0x58, null, null, null, "飛行右前進") { it.flyWalkFR },
    ParamField("flyWalkFL2", ParamFieldType.S32, 0x5C, null, null, null, "飛行左前進2") { it.flyWalkFL2 },
    ParamField("flyWalkFR2", ParamFieldType.S32, 0x60, null, null, null, "飛行右前進2") { it.flyWalkFR2 },
    ParamField("flyDashF", ParamFieldType.S32, 0x64, null, null, null, "高速飛行前進") { it.flyDashF },
    ParamField("flyDashFL", ParamFieldType.S32, 0x68, null, null, null, "高速飛行左前進") { it.flyDashFL },
    ParamField("flyDashFR", ParamFieldType.S32, 0x6C, null, null, null, "高速飛行右前進") { it.flyDashFR },
    ParamField("flyDashFL2", ParamFieldType.S32, 0x70, null, null, null, "高速飛行左前進2") { it.flyDashFL2 },
    ParamField("flyDashFR2", ParamFieldType.S32, 0x74, null, null, null, "高速飛行右前進2") { it.flyDashFR2 },
    ParamField("dashEscapeF", ParamFieldType.S32, 0x78, null, null, null, "ダッシュ緊急回避前") { it.dashEscapeF },
    ParamField("dashEscapeB", ParamFieldType.S32, 0x7C, null, null, null, "ダッシュ緊急回避後") { it.dashEscapeB },
    ParamField("dashEscapeL", ParamFieldType.S32, 0x80, null, null, null, "ダッシュ緊急回避左") { it.dashEscapeL },
    ParamField("dashEscapeR", ParamFieldType.S32, 0x84, null, null, null, "ダッシュ緊急回避右") { it.dashEscapeR },
    ParamField("analogMoveParamId", ParamFieldType.S32, 0x88, null, null, null, "アナログ移動パラＩＤ") { it.analogMoveParamId }
)
