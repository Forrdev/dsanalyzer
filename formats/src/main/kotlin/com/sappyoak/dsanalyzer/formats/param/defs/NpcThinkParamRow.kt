// GENERATED FILE - do not edit. Regenerate with:
//
//     ./gradlew :tools:generateParamDefs -Pptde=<DATA directory> -Pdsr=<DATA directory>
//
// Source: paramdef.paramdefbnd, NpcThinkParam.paramdef, read from the installation

@file:Suppress("SpellCheckingInspection")

package com.sappyoak.dsanalyzer.formats.param.defs

import com.sappyoak.dsanalyzer.formats.param.ParamField
import com.sappyoak.dsanalyzer.formats.param.ParamRow
import com.sappyoak.dsanalyzer.formats.param.ParamRowData
import com.sappyoak.dsanalyzer.formats.param.ParamRowType
import com.sappyoak.dsanalyzer.formats.param.paramdef.ParamFieldType

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

    public companion object : ParamRowType<NpcThinkParamRow>("NPC_THINK_PARAM_ST", 0x70, 1, FIELDS, ::NpcThinkParamRow)
}

private val FIELDS: List<ParamField<NpcThinkParamRow>> = listOf<ParamField<NpcThinkParamRow>>(
    ParamField("logicId", ParamFieldType.S32, 0x0, null, null, null, "ロジックスクリプトID") { it.logicId },
    ParamField("battleGoalID", ParamFieldType.S32, 0x4, null, null, null, "戦闘ゴールID") { it.battleGoalID },
    ParamField("nearDist", ParamFieldType.F32, 0x8, null, null, null, "近距離 距離[m]") { it.nearDist },
    ParamField("midDist", ParamFieldType.F32, 0xC, null, null, null, "中距離 距離[m]") { it.midDist },
    ParamField("farDist", ParamFieldType.F32, 0x10, null, null, null, "遠距離 距離[m]") { it.farDist },
    ParamField("outDist", ParamFieldType.F32, 0x14, null, null, null, "範囲外 距離[m]") { it.outDist },
    ParamField("backHomeLife_OnHitEneWal", ParamFieldType.F32, 0x18, null, null, null, "敵壁接触時のBackHome時間[sec]") { it.backHomeLife_OnHitEneWal },
    ParamField("goalID_ToCaution", ParamFieldType.S32, 0x1C, null, null, null, "ゴール：警戒状態") { it.goalID_ToCaution },
    ParamField("idAttackCannotMove", ParamFieldType.S32, 0x20, null, null, null, "動けなくなったときに行うEzState番号") { it.idAttackCannotMove },
    ParamField("goalID_ToFind", ParamFieldType.S32, 0x24, null, null, null, "ゴール：発見状態") { it.goalID_ToFind },
    ParamField("callHelp_ActionAnimId", ParamFieldType.S32, 0x28, null, null, null, "仲間呼び 応答アクションアニメID") { it.callHelp_ActionAnimId },
    ParamField("callHelp_CallActionId", ParamFieldType.S32, 0x2C, null, null, null, "仲間呼び_仲間呼びアクションID") { it.callHelp_CallActionId },
    ParamField("eye_dist", ParamFieldType.U16, 0x30, null, null, null, "視覚_距離[m]") { it.eye_dist },
    ParamField("ear_dist", ParamFieldType.U16, 0x32, null, null, null, "聴覚_距離[cm]") { it.ear_dist },
    ParamField("ear_soundcut_dist", ParamFieldType.U16, 0x34, null, null, null, "聴覚　影響カット距離[m]") { it.ear_soundcut_dist },
    ParamField("nose_dist", ParamFieldType.U16, 0x36, null, null, null, "嗅覚_距離[m]") { it.nose_dist },
    ParamField("maxBackhomeDist", ParamFieldType.U16, 0x38, null, null, null, "何があっても帰宅する距離[m]") { it.maxBackhomeDist },
    ParamField("backhomeDist", ParamFieldType.U16, 0x3A, null, null, null, "戦闘しつつ帰宅する距離[m]") { it.backhomeDist },
    ParamField("backhomeBattleDist", ParamFieldType.U16, 0x3C, null, null, null, "巣に帰るのをあきらめて戦闘する距離[m]") { it.backhomeBattleDist },
    ParamField("nonBattleActLife", ParamFieldType.U16, 0x3E, null, null, null, "敵を意識しているときの非戦闘行動時間[sec]") { it.nonBattleActLife },
    ParamField("backHome_LookTargetTime", ParamFieldType.U16, 0x40, null, null, null, "帰宅時：ターゲットを見ている時間[sec]") { it.backHome_LookTargetTime },
    ParamField("backHome_LookTargetDist", ParamFieldType.U16, 0x42, null, null, null, "帰宅時：ターゲットを見ている距離[m]") { it.backHome_LookTargetDist },
    ParamField("sightTargetForgetTime", ParamFieldType.U16, 0x44, null, null, null, "視覚ターゲット忘れる時間[frame]") { it.sightTargetForgetTime },
    ParamField("soundTargetForgetTime", ParamFieldType.U16, 0x46, null, null, null, "音ターゲット忘れる時間[frame]") { it.soundTargetForgetTime },
    ParamField("battleStartDist", ParamFieldType.U16, 0x48, null, null, null, "敵を発見してから戦闘を開始する距離[m]") { it.battleStartDist },
    ParamField("callHelp_MyPeerId", ParamFieldType.U16, 0x4A, null, null, null, "仲間呼び 自分の仲間グループID") { it.callHelp_MyPeerId },
    ParamField("callHelp_CallPeerId", ParamFieldType.U16, 0x4C, null, null, null, "仲間呼び 呼ぶ仲間グループID") { it.callHelp_CallPeerId },
    ParamField("targetSys_DmgEffectRate", ParamFieldType.U16, 0x4E, null, null, null, "ダメージ影響率[％]") { it.targetSys_DmgEffectRate },
    ParamField("teamAttackEffectivity", ParamFieldType.U8, 0x50, null, null, null, "チーム攻撃影響力[0-100]") { it.teamAttackEffectivity },
    ParamField("eye_angX", ParamFieldType.U8, 0x51, null, null, null, "視覚_角度（高さ）[deg]") { it.eye_angX },
    ParamField("eye_angY", ParamFieldType.U8, 0x52, null, null, null, "視覚_角度（幅）[deg]") { it.eye_angY },
    ParamField("ear_angX", ParamFieldType.U8, 0x53, null, null, null, "聴覚　角度（高さ）[deg]") { it.ear_angX },
    ParamField("ear_angY", ParamFieldType.U8, 0x54, null, null, null, "聴覚　角度（幅）[deg]") { it.ear_angY },
    ParamField("callHelp_CallValidMinDistTarget", ParamFieldType.U8, 0x55, null, null, null, "仲間呼び_ターゲットとの最低距離[m]") { it.callHelp_CallValidMinDistTarget },
    ParamField("callHelp_CallValidRange", ParamFieldType.U8, 0x56, null, null, null, "仲間呼び_仲間を呼ぶ有効距離[m]") { it.callHelp_CallValidRange },
    ParamField("callHelp_ForgetTimeByArrival", ParamFieldType.U8, 0x57, null, null, null, "仲間呼び 応答してから忘れる時間[sec]") { it.callHelp_ForgetTimeByArrival },
    ParamField("callHelp_MinWaitTime", ParamFieldType.U8, 0x58, null, null, null, "応答時の待機最小時間[ssm=>ss．mSec]") { it.callHelp_MinWaitTime },
    ParamField("callHelp_MaxWaitTime", ParamFieldType.U8, 0x59, null, null, null, "応答時の待機最大時間[ssm=>ss．mSec]") { it.callHelp_MaxWaitTime },
    ParamField("goalAction_ToCaution", ParamFieldType.U8, 0x5A, null, null, "NPC_THINK_GOAL_ACTION", "ゴールアクション：警戒状態") { it.goalAction_ToCaution },
    ParamField("goalAction_ToFind", ParamFieldType.U8, 0x5B, null, null, "NPC_THINK_GOAL_ACTION", "ゴールアクション：発見状態") { it.goalAction_ToFind },
    ParamField("callHelp_ReplyBehaviorType", ParamFieldType.U8, 0x5C, null, null, "NPC_THINK_REPLY_BEHAVIOR_TYPE", "仲間呼び 応答後の行動タイプ") { it.callHelp_ReplyBehaviorType },
    ParamField("disablePathMove", ParamFieldType.U8, 0x5D, null, null, null, "パス移動しない") { it.disablePathMove },
    ParamField("skipArrivalVisibleCheck", ParamFieldType.U8, 0x5E, null, null, "ON_OFF", "視線による到着判定をスキップするか？") { it.skipArrivalVisibleCheck },
    ParamField("thinkAttr_doAdmirer", ParamFieldType.U8, 0x5F, null, null, "ON_OFF", "取巻き役になるか？") { it.thinkAttr_doAdmirer },
    ParamField("enableNaviFlg_Edge", ParamFieldType.U8, 0x60, 0, 1, null, "フラグ「崖」通れるか？") { it.enableNaviFlg_Edge },
    ParamField("enableNaviFlg_LargeSpace", ParamFieldType.U8, 0x60, 1, 1, null, "フラグ「広い」通れるか？") { it.enableNaviFlg_LargeSpace },
    ParamField("enableNaviFlg_Ladder", ParamFieldType.U8, 0x60, 2, 1, null, "フラグ「梯子」通れるか？") { it.enableNaviFlg_Ladder },
    ParamField("enableNaviFlg_Hole", ParamFieldType.U8, 0x60, 3, 1, null, "フラグ「穴」通れるか？") { it.enableNaviFlg_Hole },
    ParamField("enableNaviFlg_Door", ParamFieldType.U8, 0x60, 4, 1, null, "フラグ「扉」通れるか？") { it.enableNaviFlg_Door },
    ParamField("enableNaviFlg_InSideWall", ParamFieldType.U8, 0x60, 5, 1, null, "フラグ「壁中」通れるか？") { it.enableNaviFlg_InSideWall }
)
