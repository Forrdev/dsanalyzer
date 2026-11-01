// GENERATED FILE - do not edit. Regenerate with:
//
//     ./gradlew :tools:generateParamDefs -Pptde=<DATA directory> -Pdsr=<DATA directory>
//
// Source: paramdef.paramdefbnd, TalkParam.paramdef, read from the installation

@file:Suppress("SpellCheckingInspection")

package com.sappyoak.dsanalyzer.formats.param.defs

import com.sappyoak.dsanalyzer.formats.param.ParamField
import com.sappyoak.dsanalyzer.formats.param.ParamRow
import com.sappyoak.dsanalyzer.formats.param.ParamRowData
import com.sappyoak.dsanalyzer.formats.param.ParamRowType
import com.sappyoak.dsanalyzer.formats.param.paramdef.ParamFieldType

/** `TALK_PARAM_ST`: PrepareToDie 0x20, Remastered 0x20 a row, data version 1 */
public class TalkParamRow(row: ParamRowData) : ParamRow(row) {
    /** `s32` at 0x0 */
    public val msgId: Int get() = bytes.s32(0x0)

    /** `s32` at 0x4 */
    public val voiceId: Int get() = bytes.s32(0x4)

    /** `s32` at 0x8 */
    public val motionId: Int get() = bytes.s32(0x8)

    /** `s32` at 0xC */
    public val returnPos: Int get() = bytes.s32(0xC)

    /** `s32` at 0x10 */
    public val reactionId: Int get() = bytes.s32(0x10)

    /** `s32` at 0x14 */
    public val eventId: Int get() = bytes.s32(0x14)

    /** `u8` at 0x18 */
    public val isMotionLoop: Int get() = bytes.u8(0x18)

    public companion object : ParamRowType<TalkParamRow>("TALK_PARAM_ST", 0x20, 1, FIELDS, ::TalkParamRow)
}

private val FIELDS: List<ParamField<TalkParamRow>> = listOf<ParamField<TalkParamRow>>(
    ParamField("msgId", ParamFieldType.S32, 0x0, null, null, null, "メッセージID") { it.msgId },
    ParamField("voiceId", ParamFieldType.S32, 0x4, null, null, null, "ボイスID") { it.voiceId },
    ParamField("motionId", ParamFieldType.S32, 0x8, null, null, null, "モーションID") { it.motionId },
    ParamField("returnPos", ParamFieldType.S32, 0xC, null, null, null, "復帰位置") { it.returnPos },
    ParamField("reactionId", ParamFieldType.S32, 0x10, null, null, null, "リアクションID") { it.reactionId },
    ParamField("eventId", ParamFieldType.S32, 0x14, null, null, null, "イベントID") { it.eventId },
    ParamField("isMotionLoop", ParamFieldType.U8, 0x18, null, null, null, "モーションループ有効") { it.isMotionLoop }
)
