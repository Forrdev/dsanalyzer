// GENERATED FILE - do not edit. Regenerate with:
//
//     ./gradlew :tools:generateParamDefs -Pptde=<DATA directory> -Pdsr=<DATA directory>
//
// Source: paramdef.paramdefbnd, NpcThinkParam.paramdef, read from the installation

@file:Suppress("SpellCheckingInspection")

package com.sappyoak.dsanalyzer.formats.param.defs

import com.sappyoak.dsanalyzer.formats.param.ParamRow
import com.sappyoak.dsanalyzer.formats.param.ParamRowData
import com.sappyoak.dsanalyzer.formats.param.ParamRowType

/** `NPC_THINK_PARAM_ST`: PrepareToDie 0x70, Remastered 0x70 a row, data version 1 */
public class NpcThinkParamRow(row: ParamRowData) : ParamRow(row) {
    /** `s32` at 0x0 */
    public val logicId: Int get() = bytes.s32(0x0)

    /** `s32` at 0x4 */
    public val battleGoalID: Int get() = bytes.s32(0x4)

    /** `f32` at 0x8 */
    public val nearDist: Float get() = bytes.f32(0x8)

    /** `f32` at 0xC */
    public val midDist: Float get() = bytes.f32(0xC)

    /** `f32` at 0x10 */
    public val farDist: Float get() = bytes.f32(0x10)

    /** `f32` at 0x14 */
    public val outDist: Float get() = bytes.f32(0x14)

    /** `f32` at 0x18 */
    public val backHomeLife_OnHitEneWal: Float get() = bytes.f32(0x18)

    /** `s32` at 0x1C */
    public val goalID_ToCaution: Int get() = bytes.s32(0x1C)

    /** `s32` at 0x20 */
    public val idAttackCannotMove: Int get() = bytes.s32(0x20)

    /** `s32` at 0x24 */
    public val goalID_ToFind: Int get() = bytes.s32(0x24)

    /** `s32` at 0x28 */
    public val callHelp_ActionAnimId: Int get() = bytes.s32(0x28)

    /** `s32` at 0x2C */
    public val callHelp_CallActionId: Int get() = bytes.s32(0x2C)

    /** `u16` at 0x30 */
    public val eye_dist: Int get() = bytes.u16(0x30)

    /** `u16` at 0x32 */
    public val ear_dist: Int get() = bytes.u16(0x32)

    /** `u16` at 0x34 */
    public val ear_soundcut_dist: Int get() = bytes.u16(0x34)

    /** `u16` at 0x36 */
    public val nose_dist: Int get() = bytes.u16(0x36)

    /** `u16` at 0x38 */
    public val maxBackhomeDist: Int get() = bytes.u16(0x38)

    /** `u16` at 0x3A */
    public val backhomeDist: Int get() = bytes.u16(0x3A)

    /** `u16` at 0x3C */
    public val backhomeBattleDist: Int get() = bytes.u16(0x3C)

    /** `u16` at 0x3E */
    public val nonBattleActLife: Int get() = bytes.u16(0x3E)

    /** `u16` at 0x40 */
    public val backHome_LookTargetTime: Int get() = bytes.u16(0x40)

    /** `u16` at 0x42 */
    public val backHome_LookTargetDist: Int get() = bytes.u16(0x42)

    /** `u16` at 0x44 */
    public val sightTargetForgetTime: Int get() = bytes.u16(0x44)

    /** `u16` at 0x46 */
    public val soundTargetForgetTime: Int get() = bytes.u16(0x46)

    /** `u16` at 0x48 */
    public val battleStartDist: Int get() = bytes.u16(0x48)

    /** `u16` at 0x4A */
    public val callHelp_MyPeerId: Int get() = bytes.u16(0x4A)

    /** `u16` at 0x4C */
    public val callHelp_CallPeerId: Int get() = bytes.u16(0x4C)

    /** `u16` at 0x4E */
    public val targetSys_DmgEffectRate: Int get() = bytes.u16(0x4E)

    /** `u8` at 0x50 */
    public val teamAttackEffectivity: Int get() = bytes.u8(0x50)

    /** `u8` at 0x51 */
    public val eye_angX: Int get() = bytes.u8(0x51)

    /** `u8` at 0x52 */
    public val eye_angY: Int get() = bytes.u8(0x52)

    /** `u8` at 0x53 */
    public val ear_angX: Int get() = bytes.u8(0x53)

    /** `u8` at 0x54 */
    public val ear_angY: Int get() = bytes.u8(0x54)

    /** `u8` at 0x55 */
    public val callHelp_CallValidMinDistTarget: Int get() = bytes.u8(0x55)

    /** `u8` at 0x56 */
    public val callHelp_CallValidRange: Int get() = bytes.u8(0x56)

    /** `u8` at 0x57 */
    public val callHelp_ForgetTimeByArrival: Int get() = bytes.u8(0x57)

    /** `u8` at 0x58 */
    public val callHelp_MinWaitTime: Int get() = bytes.u8(0x58)

    /** `u8` at 0x59 */
    public val callHelp_MaxWaitTime: Int get() = bytes.u8(0x59)

    /** `u8` at 0x5A, `NPC_THINK_GOAL_ACTION` */
    public val goalAction_ToCaution: Int get() = bytes.u8(0x5A)

    /** `u8` at 0x5B, `NPC_THINK_GOAL_ACTION` */
    public val goalAction_ToFind: Int get() = bytes.u8(0x5B)

    /** `u8` at 0x5C, `NPC_THINK_REPLY_BEHAVIOR_TYPE` */
    public val callHelp_ReplyBehaviorType: Int get() = bytes.u8(0x5C)

    /** `u8` at 0x5D */
    public val disablePathMove: Int get() = bytes.u8(0x5D)

    /** `u8` at 0x5E, `ON_OFF` */
    public val skipArrivalVisibleCheck: Int get() = bytes.u8(0x5E)

    /** `u8` at 0x5F, `ON_OFF` */
    public val thinkAttr_doAdmirer: Int get() = bytes.u8(0x5F)

    /** bit 0 of the `u8` at 0x60 */
    public val enableNaviFlg_Edge: Boolean get() = bytes.flag(0x60, 0)

    /** bit 1 of the `u8` at 0x60 */
    public val enableNaviFlg_LargeSpace: Boolean get() = bytes.flag(0x60, 1)

    /** bit 2 of the `u8` at 0x60 */
    public val enableNaviFlg_Ladder: Boolean get() = bytes.flag(0x60, 2)

    /** bit 3 of the `u8` at 0x60 */
    public val enableNaviFlg_Hole: Boolean get() = bytes.flag(0x60, 3)

    /** bit 4 of the `u8` at 0x60 */
    public val enableNaviFlg_Door: Boolean get() = bytes.flag(0x60, 4)

    /** bit 5 of the `u8` at 0x60 */
    public val enableNaviFlg_InSideWall: Boolean get() = bytes.flag(0x60, 5)

    public companion object : ParamRowType<NpcThinkParamRow>("NPC_THINK_PARAM_ST", 0x70, 1, ::NpcThinkParamRow)
}
