// GENERATED FILE - do not edit. Regenerate with:
//
//     ./gradlew :tools:generateParamDefs -Pptde=<DATA directory> -Pdsr=<DATA directory>
//
// Source: paramdef.paramdefbnd, LevelSyncParam.paramdef, read from the installation

@file:Suppress("SpellCheckingInspection")

package com.sappyoak.dsanalyzer.formats.param.defs

import com.sappyoak.dsanalyzer.formats.param.ParamRow
import com.sappyoak.dsanalyzer.formats.param.ParamRowData
import com.sappyoak.dsanalyzer.formats.param.ParamRowType

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

    public companion object : ParamRowType<LevelSyncParamRow>("LEVELSYNC_PARAM_ST", 0xDC, 1, ::LevelSyncParamRow)
}
