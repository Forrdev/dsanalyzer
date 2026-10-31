// GENERATED FILE - do not edit. Regenerate with:
//
//     ./gradlew :tools:generateParamDefs -Pptde=<DATA directory> -Pdsr=<DATA directory>
//
// Source: paramdef.paramdefbnd, TalkParam.paramdef, read from the installation

@file:Suppress("SpellCheckingInspection")

package com.sappyoak.dsanalyzer.formats.param.defs

import com.sappyoak.dsanalyzer.formats.param.ParamRow
import com.sappyoak.dsanalyzer.formats.param.ParamRowData
import com.sappyoak.dsanalyzer.formats.param.ParamRowType

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

    public companion object : ParamRowType<TalkParamRow>("TALK_PARAM_ST", 0x20, 1, ::TalkParamRow)
}
