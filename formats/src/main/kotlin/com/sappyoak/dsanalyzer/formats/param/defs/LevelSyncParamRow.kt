// GENERATED FILE - do not edit. Regenerate with:
//
//     ./gradlew :tools:generateParamDefs -Pptde=<DATA directory> -Pdsr=<DATA directory>
//
// Source: paramdef.paramdefbnd, LevelSyncParam.paramdef, read from the installation

@file:Suppress("SpellCheckingInspection")

package com.sappyoak.dsanalyzer.formats.param.defs

import com.sappyoak.dsanalyzer.formats.param.ParamField
import com.sappyoak.dsanalyzer.formats.param.ParamRow
import com.sappyoak.dsanalyzer.formats.param.ParamRowData
import com.sappyoak.dsanalyzer.formats.param.ParamRowType
import com.sappyoak.dsanalyzer.formats.param.paramdef.ParamFieldType

/** `LEVELSYNC_PARAM_ST`: 0xDC bytes (Remastered only) a row, data version 1 */
public class LevelSyncParamRow(row: ParamRowData) : ParamRow(row) {
    /** `s16` at 0x0 */
    public val sCLUA: Int get() = bytes.s16(0x0)

    /** `s16` at 0x2 */
    public val sDCLUR: Int get() = bytes.s16(0x2)

    /** `s16` at 0x4 */
    public val sDCWLM: Int get() = bytes.s16(0x4)

    /** `u8` at 0x6 */
    public val mLWSUR: Int get() = bytes.u8(0x6)

    /** `u8` at 0x7 */
    public val mLWSUA: Int get() = bytes.u8(0x7)

    /** `u8` at 0x8 */
    public val mLRSUR: Int get() = bytes.u8(0x8)

    /** `u8` at 0x9 */
    public val mLRSUA: Int get() = bytes.u8(0x9)

    /** `u8` at 0xA */
    public val mWLUWS0: Int get() = bytes.u8(0xA)

    /** `u8` at 0xB */
    public val mWLUWS1: Int get() = bytes.u8(0xB)

    /** `u8` at 0xC */
    public val mWLUWS2: Int get() = bytes.u8(0xC)

    /** `u8` at 0xD */
    public val mWLUWS3: Int get() = bytes.u8(0xD)

    /** `u8` at 0xE */
    public val mWLUWS4: Int get() = bytes.u8(0xE)

    /** `u8` at 0xF */
    public val mWLUWS5: Int get() = bytes.u8(0xF)

    /** `u8` at 0x10 */
    public val mWLUWS6: Int get() = bytes.u8(0x10)

    /** `u8` at 0x11 */
    public val mWLUWS7: Int get() = bytes.u8(0x11)

    /** `u8` at 0x12 */
    public val mWLUWS8: Int get() = bytes.u8(0x12)

    /** `u8` at 0x13 */
    public val mWLUWS9: Int get() = bytes.u8(0x13)

    /** `u8` at 0x14 */
    public val mWLUWS10: Int get() = bytes.u8(0x14)

    /** `u8` at 0x15 */
    public val mWLURS0: Int get() = bytes.u8(0x15)

    /** `u8` at 0x16 */
    public val mWLURS1: Int get() = bytes.u8(0x16)

    /** `u8` at 0x17 */
    public val mWLURS2: Int get() = bytes.u8(0x17)

    /** `u8` at 0x18 */
    public val mWLURS3: Int get() = bytes.u8(0x18)

    /** `u8` at 0x19 */
    public val mWLURS4: Int get() = bytes.u8(0x19)

    /** `u8` at 0x1A */
    public val mWLURS5: Int get() = bytes.u8(0x1A)

    /** `u8` at 0x1B */
    public val mWLURS6: Int get() = bytes.u8(0x1B)

    /** `u8` at 0x1C */
    public val mWLURS7: Int get() = bytes.u8(0x1C)

    /** `u8` at 0x1D */
    public val mWLURS8: Int get() = bytes.u8(0x1D)

    /** `u8` at 0x1E */
    public val mWLURS9: Int get() = bytes.u8(0x1E)

    /** `u8` at 0x1F */
    public val mWLURS10: Int get() = bytes.u8(0x1F)

    /** `u8` at 0x20 */
    public val mWLUA0: Int get() = bytes.u8(0x20)

    /** `u8` at 0x21 */
    public val mWLUA1: Int get() = bytes.u8(0x21)

    /** `u8` at 0x22 */
    public val mWLUA2: Int get() = bytes.u8(0x22)

    /** `u8` at 0x23 */
    public val mWLUA3: Int get() = bytes.u8(0x23)

    /** `u8` at 0x24 */
    public val mWLUA4: Int get() = bytes.u8(0x24)

    /** `u8` at 0x25 */
    public val mWLUA5: Int get() = bytes.u8(0x25)

    /** `u8` at 0x26 */
    public val mWLUA6: Int get() = bytes.u8(0x26)

    /** `u8` at 0x27 */
    public val mWLUA7: Int get() = bytes.u8(0x27)

    /** `u8` at 0x28 */
    public val mWLUA8: Int get() = bytes.u8(0x28)

    /** `u8` at 0x29 */
    public val mWLUA9: Int get() = bytes.u8(0x29)

    /** `u8` at 0x2A */
    public val mWLUA10: Int get() = bytes.u8(0x2A)

    /** `u8` at 0x2B */
    public val mLASUR: Int get() = bytes.u8(0x2B)

    /** `u8` at 0x2C */
    public val mLASUA: Int get() = bytes.u8(0x2C)

    /** `f32` at 0x30 */
    public val summonTimeoutTime: Float get() = bytes.f32(0x30)

    /** `u32` at 0x34 */
    public val singGetMax: Long get() = bytes.u32(0x34)

    /** `f32` at 0x38 */
    public val signDownloadSpan: Float get() = bytes.f32(0x38)

    /** `f32` at 0x3C */
    public val nitoSignDownloadSpan: Float get() = bytes.f32(0x3C)

    /** `f32` at 0x40 */
    public val signUpdateSpan: Float get() = bytes.f32(0x40)

    /** `u32` at 0x44 */
    public val maxBreakInTargetListCount: Long get() = bytes.u32(0x44)

    /** `f32` at 0x48 */
    public val breakInRequestIntervalTimeSec: Float get() = bytes.f32(0x48)

    /** `f32` at 0x4C */
    public val breakInRequestTimeOutSec: Float get() = bytes.f32(0x4C)

    /** `u32` at 0x50 */
    public val reloadSignTotalCount_0: Long get() = bytes.u32(0x50)

    /** `f32` at 0x54 */
    public val reloadSignIntervalTime_0: Float get() = bytes.f32(0x54)

    /** `f32` at 0x58 */
    public val reloadSignIntervalTime_1: Float get() = bytes.f32(0x58)

    /** `u32` at 0x5C */
    public val reloadSignTotalCount_1: Long get() = bytes.u32(0x5C)

    /** `u32` at 0x60 */
    public val reloadSignCellCount: Long get() = bytes.u32(0x60)

    /** `f32` at 0x64 */
    public val reloadSignIntervalTime_2: Float get() = bytes.f32(0x64)

    /** `u32` at 0x68 */
    public val reloadGhostTotalCount: Long get() = bytes.u32(0x68)

    /** `u32` at 0x6C */
    public val reloadGhostCellCount: Long get() = bytes.u32(0x6C)

    /** `u32` at 0x70 */
    public val maxGhostTotalCount: Long get() = bytes.u32(0x70)

    /** `f32` at 0x74 */
    public val updateWanderGhostIntervalTime: Float get() = bytes.f32(0x74)

    /** `f32` at 0x78 */
    public val minReplayIntervalTime: Float get() = bytes.f32(0x78)

    /** `f32` at 0x7C */
    public val maxReplayIntervalTime: Float get() = bytes.f32(0x7C)

    /** `f32` at 0x80 */
    public val reloadGhostIntervalTime: Float get() = bytes.f32(0x80)

    /** `f32` at 0x84 */
    public val replayBonfireModeRange: Float get() = bytes.f32(0x84)

    /** `f32` at 0x88 */
    public val wanderGhostIntervalLifeTime: Float get() = bytes.f32(0x88)

    /** `f32` at 0x8C */
    public val summonMessageInterval: Float get() = bytes.f32(0x8C)

    /** `f32` at 0x90 */
    public val hostRegisterUpdateTime: Float get() = bytes.f32(0x90)

    /** `u32` at 0x94 */
    public val requestSearchQuickMatchLimit: Long get() = bytes.u32(0x94)

    /** `f32` at 0x98 */
    public val myTeamInviteTimeoutTime: Float get() = bytes.f32(0x98)

    /** `u32` at 0x9C */
    public val visitorListMax: Long get() = bytes.u32(0x9C)

    /** `f32` at 0xA0 */
    public val visitorTimeOutTime: Float get() = bytes.f32(0xA0)

    /** `f32` at 0xA4 */
    public val downloadSpan: Float get() = bytes.f32(0xA4)

    /** `f32` at 0xA8 */
    public val bonfireLowerBoundCoolTime: Float get() = bytes.f32(0xA8)

    /** `f32` at 0xAC */
    public val bonfireUpperBoundCoolTime: Float get() = bytes.f32(0xAC)

    /** `f32` at 0xB0 */
    public val resonanceMagicDbDistInterval: Float get() = bytes.f32(0xB0)

    /** `f32` at 0xB4 */
    public val inputTimeoutSec: Float get() = bytes.f32(0xB4)

    /** `f32` at 0xB8 */
    public val generalPurposeParam1: Float get() = bytes.f32(0xB8)

    /** `f32` at 0xBC */
    public val generalPurposeParam2: Float get() = bytes.f32(0xBC)

    /** `f32` at 0xC0 */
    public val generalPurposeParam3: Float get() = bytes.f32(0xC0)

    /** `f32` at 0xC4 */
    public val generalPurposeParam4: Float get() = bytes.f32(0xC4)

    /** `f32` at 0xC8 */
    public val generalPurposeParam5: Float get() = bytes.f32(0xC8)

    /** `u8` at 0xCC */
    public val mWLUWS_11: Int get() = bytes.u8(0xCC)

    /** `u8` at 0xCD */
    public val mWLUWS_12: Int get() = bytes.u8(0xCD)

    /** `u8` at 0xCE */
    public val mWLUWS_13: Int get() = bytes.u8(0xCE)

    /** `u8` at 0xCF */
    public val mWLUWS_14: Int get() = bytes.u8(0xCF)

    /** `u8` at 0xD0 */
    public val mWLUWS_15: Int get() = bytes.u8(0xD0)

    /** `u8` at 0xD1 */
    public val mWLURS_11: Int get() = bytes.u8(0xD1)

    /** `u8` at 0xD2 */
    public val mWLURS_12: Int get() = bytes.u8(0xD2)

    /** `u8` at 0xD3 */
    public val mWLURS_13: Int get() = bytes.u8(0xD3)

    /** `u8` at 0xD4 */
    public val mWLURS_14: Int get() = bytes.u8(0xD4)

    /** `u8` at 0xD5 */
    public val mWLURS_15: Int get() = bytes.u8(0xD5)

    /** `u8` at 0xD6 */
    public val mWLUAR_11: Int get() = bytes.u8(0xD6)

    /** `u8` at 0xD7 */
    public val mWLUAR_12: Int get() = bytes.u8(0xD7)

    /** `u8` at 0xD8 */
    public val mWLUAR_13: Int get() = bytes.u8(0xD8)

    /** `u8` at 0xD9 */
    public val mWLUAR_14: Int get() = bytes.u8(0xD9)

    /** `u8` at 0xDA */
    public val mWLUAR_15: Int get() = bytes.u8(0xDA)

    /** `u8` at 0xDB */
    public val generalPurposeParam6: Int get() = bytes.u8(0xDB)

    public companion object : ParamRowType<LevelSyncParamRow>("LEVELSYNC_PARAM_ST", 0xDC, 1, FIELDS, ::LevelSyncParamRow)
}

private val FIELDS: List<ParamField<LevelSyncParamRow>> = listOf<ParamField<LevelSyncParamRow>>(
    ParamField("sCLUA", ParamFieldType.S16, 0x0, null, null, null, "SCLUA") { it.sCLUA },
    ParamField("sDCLUR", ParamFieldType.S16, 0x2, null, null, null, "SDCLUR") { it.sDCLUR },
    ParamField("sDCWLM", ParamFieldType.S16, 0x4, null, null, null, "SDCWLM") { it.sDCWLM },
    ParamField("mLWSUR", ParamFieldType.U8, 0x6, null, null, null, "MLWSUR") { it.mLWSUR },
    ParamField("mLWSUA", ParamFieldType.U8, 0x7, null, null, null, "MLWSUA") { it.mLWSUA },
    ParamField("mLRSUR", ParamFieldType.U8, 0x8, null, null, null, "MLRSUR") { it.mLRSUR },
    ParamField("mLRSUA", ParamFieldType.U8, 0x9, null, null, null, "MLRSUA") { it.mLRSUA },
    ParamField("mWLUWS0", ParamFieldType.U8, 0xA, null, null, null, "MWLUWS0") { it.mWLUWS0 },
    ParamField("mWLUWS1", ParamFieldType.U8, 0xB, null, null, null, "MWLUWS1") { it.mWLUWS1 },
    ParamField("mWLUWS2", ParamFieldType.U8, 0xC, null, null, null, "MWLUWS2") { it.mWLUWS2 },
    ParamField("mWLUWS3", ParamFieldType.U8, 0xD, null, null, null, "MWLUWS3") { it.mWLUWS3 },
    ParamField("mWLUWS4", ParamFieldType.U8, 0xE, null, null, null, "MWLUWS4") { it.mWLUWS4 },
    ParamField("mWLUWS5", ParamFieldType.U8, 0xF, null, null, null, "MWLUWS5") { it.mWLUWS5 },
    ParamField("mWLUWS6", ParamFieldType.U8, 0x10, null, null, null, "MWLUWS6") { it.mWLUWS6 },
    ParamField("mWLUWS7", ParamFieldType.U8, 0x11, null, null, null, "MWLUWS7") { it.mWLUWS7 },
    ParamField("mWLUWS8", ParamFieldType.U8, 0x12, null, null, null, "MWLUWS8") { it.mWLUWS8 },
    ParamField("mWLUWS9", ParamFieldType.U8, 0x13, null, null, null, "MWLUWS9") { it.mWLUWS9 },
    ParamField("mWLUWS10", ParamFieldType.U8, 0x14, null, null, null, "MWLUWS10") { it.mWLUWS10 },
    ParamField("mWLURS0", ParamFieldType.U8, 0x15, null, null, null, "MWLURS0") { it.mWLURS0 },
    ParamField("mWLURS1", ParamFieldType.U8, 0x16, null, null, null, "MWLURS1") { it.mWLURS1 },
    ParamField("mWLURS2", ParamFieldType.U8, 0x17, null, null, null, "MWLURS2") { it.mWLURS2 },
    ParamField("mWLURS3", ParamFieldType.U8, 0x18, null, null, null, "MWLURS3") { it.mWLURS3 },
    ParamField("mWLURS4", ParamFieldType.U8, 0x19, null, null, null, "MWLURS4") { it.mWLURS4 },
    ParamField("mWLURS5", ParamFieldType.U8, 0x1A, null, null, null, "MWLURS5") { it.mWLURS5 },
    ParamField("mWLURS6", ParamFieldType.U8, 0x1B, null, null, null, "MWLURS6") { it.mWLURS6 },
    ParamField("mWLURS7", ParamFieldType.U8, 0x1C, null, null, null, "MWLURS7") { it.mWLURS7 },
    ParamField("mWLURS8", ParamFieldType.U8, 0x1D, null, null, null, "MWLURS8") { it.mWLURS8 },
    ParamField("mWLURS9", ParamFieldType.U8, 0x1E, null, null, null, "MWLURS9") { it.mWLURS9 },
    ParamField("mWLURS10", ParamFieldType.U8, 0x1F, null, null, null, "MWLURS10") { it.mWLURS10 },
    ParamField("mWLUA0", ParamFieldType.U8, 0x20, null, null, null, "MWLUA0") { it.mWLUA0 },
    ParamField("mWLUA1", ParamFieldType.U8, 0x21, null, null, null, "MWLUA1") { it.mWLUA1 },
    ParamField("mWLUA2", ParamFieldType.U8, 0x22, null, null, null, "MWLUA2") { it.mWLUA2 },
    ParamField("mWLUA3", ParamFieldType.U8, 0x23, null, null, null, "MWLUA3") { it.mWLUA3 },
    ParamField("mWLUA4", ParamFieldType.U8, 0x24, null, null, null, "MWLUA4") { it.mWLUA4 },
    ParamField("mWLUA5", ParamFieldType.U8, 0x25, null, null, null, "MWLUA5") { it.mWLUA5 },
    ParamField("mWLUA6", ParamFieldType.U8, 0x26, null, null, null, "MWLUA6") { it.mWLUA6 },
    ParamField("mWLUA7", ParamFieldType.U8, 0x27, null, null, null, "MWLUA7") { it.mWLUA7 },
    ParamField("mWLUA8", ParamFieldType.U8, 0x28, null, null, null, "MWLUA8") { it.mWLUA8 },
    ParamField("mWLUA9", ParamFieldType.U8, 0x29, null, null, null, "MWLUA9") { it.mWLUA9 },
    ParamField("mWLUA10", ParamFieldType.U8, 0x2A, null, null, null, "MWLUA10") { it.mWLUA10 },
    ParamField("mLASUR", ParamFieldType.U8, 0x2B, null, null, null, "MLASUR") { it.mLASUR },
    ParamField("mLASUA", ParamFieldType.U8, 0x2C, null, null, null, "MLASUA") { it.mLASUA },
    ParamField("summonTimeoutTime", ParamFieldType.F32, 0x30, null, null, null, "summonTimeoutTime") { it.summonTimeoutTime },
    ParamField("singGetMax", ParamFieldType.U32, 0x34, null, null, null, "singGetMax") { it.singGetMax },
    ParamField("signDownloadSpan", ParamFieldType.F32, 0x38, null, null, null, "signDownloadSpan") { it.signDownloadSpan },
    ParamField("nitoSignDownloadSpan", ParamFieldType.F32, 0x3C, null, null, null, "nitoSignDownloadSpan") { it.nitoSignDownloadSpan },
    ParamField("signUpdateSpan", ParamFieldType.F32, 0x40, null, null, null, "signUpdateSpan") { it.signUpdateSpan },
    ParamField("maxBreakInTargetListCount", ParamFieldType.U32, 0x44, null, null, null, "maxBreakInTargetListCount") { it.maxBreakInTargetListCount },
    ParamField("breakInRequestIntervalTimeSec", ParamFieldType.F32, 0x48, null, null, null, "breakInRequestIntervalTimeSec") { it.breakInRequestIntervalTimeSec },
    ParamField("breakInRequestTimeOutSec", ParamFieldType.F32, 0x4C, null, null, null, "breakInRequestTimeOutSec") { it.breakInRequestTimeOutSec },
    ParamField("reloadSignTotalCount_0", ParamFieldType.U32, 0x50, null, null, null, "reloadSignTotalCount_0") { it.reloadSignTotalCount_0 },
    ParamField("reloadSignIntervalTime_0", ParamFieldType.F32, 0x54, null, null, null, "reloadSignIntervalTime_0") { it.reloadSignIntervalTime_0 },
    ParamField("reloadSignIntervalTime_1", ParamFieldType.F32, 0x58, null, null, null, "reloadSignIntervalTime_1") { it.reloadSignIntervalTime_1 },
    ParamField("reloadSignTotalCount_1", ParamFieldType.U32, 0x5C, null, null, null, "reloadSignTotalCount_1") { it.reloadSignTotalCount_1 },
    ParamField("reloadSignCellCount", ParamFieldType.U32, 0x60, null, null, null, "reloadSignCellCount") { it.reloadSignCellCount },
    ParamField("reloadSignIntervalTime_2", ParamFieldType.F32, 0x64, null, null, null, "reloadSignIntervalTime_2") { it.reloadSignIntervalTime_2 },
    ParamField("reloadGhostTotalCount", ParamFieldType.U32, 0x68, null, null, null, "reloadGhostTotalCount") { it.reloadGhostTotalCount },
    ParamField("reloadGhostCellCount", ParamFieldType.U32, 0x6C, null, null, null, "reloadGhostCellCount") { it.reloadGhostCellCount },
    ParamField("maxGhostTotalCount", ParamFieldType.U32, 0x70, null, null, null, "maxGhostTotalCount") { it.maxGhostTotalCount },
    ParamField("updateWanderGhostIntervalTime", ParamFieldType.F32, 0x74, null, null, null, "updateWanderGhostIntervalTime") { it.updateWanderGhostIntervalTime },
    ParamField("minReplayIntervalTime", ParamFieldType.F32, 0x78, null, null, null, "minReplayIntervalTime") { it.minReplayIntervalTime },
    ParamField("maxReplayIntervalTime", ParamFieldType.F32, 0x7C, null, null, null, "maxReplayIntervalTime") { it.maxReplayIntervalTime },
    ParamField("reloadGhostIntervalTime", ParamFieldType.F32, 0x80, null, null, null, "reloadGhostIntervalTime") { it.reloadGhostIntervalTime },
    ParamField("replayBonfireModeRange", ParamFieldType.F32, 0x84, null, null, null, "replayBonfireModeRange") { it.replayBonfireModeRange },
    ParamField("wanderGhostIntervalLifeTime", ParamFieldType.F32, 0x88, null, null, null, "wanderGhostIntervalLifeTime") { it.wanderGhostIntervalLifeTime },
    ParamField("summonMessageInterval", ParamFieldType.F32, 0x8C, null, null, null, "summonMessageInterval") { it.summonMessageInterval },
    ParamField("hostRegisterUpdateTime", ParamFieldType.F32, 0x90, null, null, null, "hostRegisterUpdateTime") { it.hostRegisterUpdateTime },
    ParamField("requestSearchQuickMatchLimit", ParamFieldType.U32, 0x94, null, null, null, "requestSearchQuickMatchLimit") { it.requestSearchQuickMatchLimit },
    ParamField("myTeamInviteTimeoutTime", ParamFieldType.F32, 0x98, null, null, null, "myTeamInviteTimeoutTime") { it.myTeamInviteTimeoutTime },
    ParamField("visitorListMax", ParamFieldType.U32, 0x9C, null, null, null, "VisitorListMax") { it.visitorListMax },
    ParamField("visitorTimeOutTime", ParamFieldType.F32, 0xA0, null, null, null, "VisitorTimeOutTime") { it.visitorTimeOutTime },
    ParamField("downloadSpan", ParamFieldType.F32, 0xA4, null, null, null, "DownloadSpan") { it.downloadSpan },
    ParamField("bonfireLowerBoundCoolTime", ParamFieldType.F32, 0xA8, null, null, null, "bonfireLowerBoundCoolTime") { it.bonfireLowerBoundCoolTime },
    ParamField("bonfireUpperBoundCoolTime", ParamFieldType.F32, 0xAC, null, null, null, "bonfireUpperBoundCoolTime") { it.bonfireUpperBoundCoolTime },
    ParamField("resonanceMagicDbDistInterval", ParamFieldType.F32, 0xB0, null, null, null, "resonanceMagicDbDistInterval") { it.resonanceMagicDbDistInterval },
    ParamField("inputTimeoutSec", ParamFieldType.F32, 0xB4, null, null, null, "inputTimeoutSec") { it.inputTimeoutSec },
    ParamField("generalPurposeParam1", ParamFieldType.F32, 0xB8, null, null, null, "generalPurposeParam1") { it.generalPurposeParam1 },
    ParamField("generalPurposeParam2", ParamFieldType.F32, 0xBC, null, null, null, "generalPurposeParam2") { it.generalPurposeParam2 },
    ParamField("generalPurposeParam3", ParamFieldType.F32, 0xC0, null, null, null, "generalPurposeParam3") { it.generalPurposeParam3 },
    ParamField("generalPurposeParam4", ParamFieldType.F32, 0xC4, null, null, null, "generalPurposeParam4") { it.generalPurposeParam4 },
    ParamField("generalPurposeParam5", ParamFieldType.F32, 0xC8, null, null, null, "generalPurposeParam5") { it.generalPurposeParam5 },
    ParamField("mWLUWS_11", ParamFieldType.U8, 0xCC, null, null, null, "MWLUWS_11") { it.mWLUWS_11 },
    ParamField("mWLUWS_12", ParamFieldType.U8, 0xCD, null, null, null, "MWLUWS_12") { it.mWLUWS_12 },
    ParamField("mWLUWS_13", ParamFieldType.U8, 0xCE, null, null, null, "MWLUWS_13") { it.mWLUWS_13 },
    ParamField("mWLUWS_14", ParamFieldType.U8, 0xCF, null, null, null, "MWLUWS_14") { it.mWLUWS_14 },
    ParamField("mWLUWS_15", ParamFieldType.U8, 0xD0, null, null, null, "MWLUWS_15") { it.mWLUWS_15 },
    ParamField("mWLURS_11", ParamFieldType.U8, 0xD1, null, null, null, "MWLURS_11") { it.mWLURS_11 },
    ParamField("mWLURS_12", ParamFieldType.U8, 0xD2, null, null, null, "MWLURS_12") { it.mWLURS_12 },
    ParamField("mWLURS_13", ParamFieldType.U8, 0xD3, null, null, null, "MWLURS_13") { it.mWLURS_13 },
    ParamField("mWLURS_14", ParamFieldType.U8, 0xD4, null, null, null, "MWLURS_14") { it.mWLURS_14 },
    ParamField("mWLURS_15", ParamFieldType.U8, 0xD5, null, null, null, "MWLURS_15") { it.mWLURS_15 },
    ParamField("mWLUAR_11", ParamFieldType.U8, 0xD6, null, null, null, "MWLUAR_11") { it.mWLUAR_11 },
    ParamField("mWLUAR_12", ParamFieldType.U8, 0xD7, null, null, null, "MWLUAR_12") { it.mWLUAR_12 },
    ParamField("mWLUAR_13", ParamFieldType.U8, 0xD8, null, null, null, "MWLUAR_13") { it.mWLUAR_13 },
    ParamField("mWLUAR_14", ParamFieldType.U8, 0xD9, null, null, null, "MWLUAR_14") { it.mWLUAR_14 },
    ParamField("mWLUAR_15", ParamFieldType.U8, 0xDA, null, null, null, "MWLUAR_15") { it.mWLUAR_15 },
    ParamField("generalPurposeParam6", ParamFieldType.U8, 0xDB, null, null, null, "generalPurposeParam6") { it.generalPurposeParam6 }
)
