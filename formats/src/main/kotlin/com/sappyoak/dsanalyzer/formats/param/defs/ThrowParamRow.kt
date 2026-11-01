// GENERATED FILE - do not edit. Regenerate with:
//
//     ./gradlew :tools:generateParamDefs -Pptde=<DATA directory> -Pdsr=<DATA directory>
//
// Source: paramdef.paramdefbnd, ThrowParam.paramdef, read from the installation

@file:Suppress("SpellCheckingInspection")

package com.sappyoak.dsanalyzer.formats.param.defs

import com.sappyoak.dsanalyzer.formats.param.ParamField
import com.sappyoak.dsanalyzer.formats.param.ParamRow
import com.sappyoak.dsanalyzer.formats.param.ParamRowData
import com.sappyoak.dsanalyzer.formats.param.ParamRowType
import com.sappyoak.dsanalyzer.formats.param.paramdef.ParamFieldType

/** `THROW_INFO_BANK`: PrepareToDie 0x40, Remastered 0x40 a row, data version 1 */
public class ThrowParamRow(row: ParamRowData) : ParamRow(row) {
    /** `s32` at 0x0 */
    public val atkChrId: Int get() = bytes.s32(0x0)

    /** `s32` at 0x4 */
    public val defChrId: Int get() = bytes.s32(0x4)

    /** `f32` at 0x8 */
    public val dist: Float get() = bytes.f32(0x8)

    /** `f32` at 0xC */
    public val diffAngMin: Float get() = bytes.f32(0xC)

    /** `f32` at 0x10 */
    public val diffAngMax: Float get() = bytes.f32(0x10)

    /** `f32` at 0x14 */
    public val upperYRange: Float get() = bytes.f32(0x14)

    /** `f32` at 0x18 */
    public val lowerYRange: Float get() = bytes.f32(0x18)

    /** `f32` at 0x1C */
    public val diffAngMyToDef: Float get() = bytes.f32(0x1C)

    /** `s32` at 0x20 */
    public val throwTypeId: Int get() = bytes.s32(0x20)

    /** `s32` at 0x24 */
    public val atkAnimId: Int get() = bytes.s32(0x24)

    /** `s32` at 0x28 */
    public val defAnimId: Int get() = bytes.s32(0x28)

    /** `u16` at 0x2C */
    public val escHp: Int get() = bytes.u16(0x2C)

    /** `u16` at 0x2E */
    public val selfEscCycleTime: Int get() = bytes.u16(0x2E)

    /** `u16` at 0x30 */
    public val sphereCastRadiusRateTop: Int get() = bytes.u16(0x30)

    /** `u16` at 0x32 */
    public val sphereCastRadiusRateLow: Int get() = bytes.u16(0x32)

    /** `u8` at 0x34, `THROW_PAD_TYPE` */
    public val padType: Int get() = bytes.u8(0x34)

    /** `u8` at 0x35, `THROW_ENABLE_STATE` */
    public val atkEnableState: Int get() = bytes.u8(0x35)

    /** `u8` at 0x36 */
    public val atkSorbDmyId: Int get() = bytes.u8(0x36)

    /** `u8` at 0x37 */
    public val defSorbDmyId: Int get() = bytes.u8(0x37)

    /** `u8` at 0x38, `THROW_TYPE` */
    public val throwType: Int get() = bytes.u8(0x38)

    /** `u8` at 0x39 */
    public val selfEscCycleCnt: Int get() = bytes.u8(0x39)

    /** `u8` at 0x3A, `THROW_DMY_CHR_DIR_TYPE` */
    public val dmyHasChrDirType: Int get() = bytes.u8(0x3A)

    /** bit 0 of the `u8` at 0x3B */
    public val isTurnAtker: Boolean get() = bytes.flag(0x3B, 0)

    /** bit 1 of the `u8` at 0x3B */
    public val isSkipWepCate: Boolean get() = bytes.flag(0x3B, 1)

    /** bit 2 of the `u8` at 0x3B */
    public val isSkipSphereCast: Boolean get() = bytes.flag(0x3B, 2)

    public companion object : ParamRowType<ThrowParamRow>("THROW_INFO_BANK", 0x40, 1, FIELDS, ::ThrowParamRow)
}

private val FIELDS: List<ParamField<ThrowParamRow>> = listOf<ParamField<ThrowParamRow>>(
    ParamField("atkChrId", ParamFieldType.S32, 0x0, null, null, null, "投げ側キャラID") { it.atkChrId },
    ParamField("defChrId", ParamFieldType.S32, 0x4, null, null, null, "受け側キャラID") { it.defChrId },
    ParamField("dist", ParamFieldType.F32, 0x8, null, null, null, "有効距離[m]") { it.dist },
    ParamField("diffAngMin", ParamFieldType.F32, 0xC, null, null, null, "自分の向きと相手の向きの角度差範囲min") { it.diffAngMin },
    ParamField("diffAngMax", ParamFieldType.F32, 0x10, null, null, null, "自分の向きと相手の向きの角度差範囲max") { it.diffAngMax },
    ParamField("upperYRange", ParamFieldType.F32, 0x14, null, null, null, "高さ範囲上[m]") { it.upperYRange },
    ParamField("lowerYRange", ParamFieldType.F32, 0x18, null, null, null, "高さ範囲下[m]") { it.lowerYRange },
    ParamField("diffAngMyToDef", ParamFieldType.F32, 0x1C, null, null, null, "自分の向きと自分から相手への方向の角度差") { it.diffAngMyToDef },
    ParamField("throwTypeId", ParamFieldType.S32, 0x20, null, null, null, "投げタイプID") { it.throwTypeId },
    ParamField("atkAnimId", ParamFieldType.S32, 0x24, null, null, null, "投げ側アニメID") { it.atkAnimId },
    ParamField("defAnimId", ParamFieldType.S32, 0x28, null, null, null, "受け側アニメID") { it.defAnimId },
    ParamField("escHp", ParamFieldType.U16, 0x2C, null, null, null, "投げ抜けHP") { it.escHp },
    ParamField("selfEscCycleTime", ParamFieldType.U16, 0x2E, null, null, null, "自力投げ抜けサイクル時間[ms]") { it.selfEscCycleTime },
    ParamField("sphereCastRadiusRateTop", ParamFieldType.U16, 0x30, null, null, null, "スフィアキャスト半径比率_上[1/100Rate]") { it.sphereCastRadiusRateTop },
    ParamField("sphereCastRadiusRateLow", ParamFieldType.U16, 0x32, null, null, null, "スフィアキャスト半径比率_下[1/100Rate]") { it.sphereCastRadiusRateLow },
    ParamField("padType", ParamFieldType.U8, 0x34, null, null, "THROW_PAD_TYPE", "操作タイプ") { it.padType },
    ParamField("atkEnableState", ParamFieldType.U8, 0x35, null, null, "THROW_ENABLE_STATE", "投げ側の投げ可能状態タイプ") { it.atkEnableState },
    ParamField("atkSorbDmyId", ParamFieldType.U8, 0x36, null, null, null, "投げ側 吸着ダミポリID") { it.atkSorbDmyId },
    ParamField("defSorbDmyId", ParamFieldType.U8, 0x37, null, null, null, "受け側 吸着ダミポリID") { it.defSorbDmyId },
    ParamField("throwType", ParamFieldType.U8, 0x38, null, null, "THROW_TYPE", "投げ種別") { it.throwType },
    ParamField("selfEscCycleCnt", ParamFieldType.U8, 0x39, null, null, null, "自力投げ抜けサイクル回数") { it.selfEscCycleCnt },
    ParamField("dmyHasChrDirType", ParamFieldType.U8, 0x3A, null, null, "THROW_DMY_CHR_DIR_TYPE", "投げ発生時のダミポリ所持キャラの向き") { it.dmyHasChrDirType },
    ParamField("isTurnAtker", ParamFieldType.U8, 0x3B, 0, 1, null, "投げ側が旋回するか？") { it.isTurnAtker },
    ParamField("isSkipWepCate", ParamFieldType.U8, 0x3B, 1, 1, null, "武器カテゴリチェックをスキップするか？") { it.isSkipWepCate },
    ParamField("isSkipSphereCast", ParamFieldType.U8, 0x3B, 2, 1, null, "スフィアキャストをスキップするか？") { it.isSkipSphereCast }
)
