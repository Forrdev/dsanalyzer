// GENERATED FILE - do not edit. Regenerate with:
//
//     ./gradlew :tools:generateParamDefs -Pptde=<DATA directory> -Pdsr=<DATA directory>
//
// Source: paramdef.paramdefbnd, ObjActParam.paramdef, read from the installation

@file:Suppress("SpellCheckingInspection")

package com.sappyoak.dsanalyzer.formats.param.defs

import com.sappyoak.dsanalyzer.formats.param.ParamRow
import com.sappyoak.dsanalyzer.formats.param.ParamRowData
import com.sappyoak.dsanalyzer.formats.param.ParamRowType

/** `OBJ_ACT_PARAM_ST`: PrepareToDie 0x20, Remastered 0x20 a row, data version 1 */
public class ObjActParamRow(row: ParamRowData) : ParamRow(row) {
    /** `s32` at 0x0 */
    public val actionEnableMsgId: Int get() = bytes.s32(0x0)

    /** `s32` at 0x4 */
    public val actionFailedMsgId: Int get() = bytes.s32(0x4)

    /** `s32` at 0x8 */
    public val spQualifiedPassEventFlag: Int get() = bytes.s32(0x8)

    /** `u16` at 0xC */
    public val validDist: Int get() = bytes.u16(0xC)

    /** `u16` at 0xE */
    public val playerAnimId: Int get() = bytes.u16(0xE)

    /** `u16` at 0x10 */
    public val chrAnimId: Int get() = bytes.u16(0x10)

    /** `u16` at 0x12 */
    public val spQualifiedId: Int get() = bytes.u16(0x12)

    /** `u16` at 0x14 */
    public val spQualifiedId2: Int get() = bytes.u16(0x14)

    /** `u8` at 0x16 */
    public val objDummyId: Int get() = bytes.u8(0x16)

    /** `u8` at 0x17 */
    public val objAnimId: Int get() = bytes.u8(0x17)

    /** `u8` at 0x18 */
    public val validPlayerAngle: Int get() = bytes.u8(0x18)

    /** `u8` at 0x19, `OBJACT_SP_QUALIFIED_TYPE` */
    public val spQualifiedType: Int get() = bytes.u8(0x19)

    /** `u8` at 0x1A, `OBJACT_SP_QUALIFIED_TYPE` */
    public val spQualifiedType2: Int get() = bytes.u8(0x1A)

    /** `u8` at 0x1B */
    public val validObjAngle: Int get() = bytes.u8(0x1B)

    /** `u8` at 0x1C, `OBJACT_CHR_SORB_TYPE` */
    public val chrSorbType: Int get() = bytes.u8(0x1C)

    /** `u8` at 0x1D, `OBJACT_EVENT_KICK_TIMING` */
    public val eventKickTiming: Int get() = bytes.u8(0x1D)

    public companion object : ParamRowType<ObjActParamRow>("OBJ_ACT_PARAM_ST", 0x20, 1, ::ObjActParamRow)
}
