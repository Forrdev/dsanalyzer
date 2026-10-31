// GENERATED FILE - do not edit. Regenerate with:
//
//     ./gradlew :tools:generateParamDefs -Pptde=<DATA directory> -Pdsr=<DATA directory>
//
// Source: paramdef.paramdefbnd, ThrowParam.paramdef, read from the installation

@file:Suppress("SpellCheckingInspection")

package com.sappyoak.dsanalyzer.formats.param.defs

import com.sappyoak.dsanalyzer.formats.param.ParamRow
import com.sappyoak.dsanalyzer.formats.param.ParamRowData
import com.sappyoak.dsanalyzer.formats.param.ParamRowType

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

    public companion object : ParamRowType<ThrowParamRow>("THROW_INFO_BANK", 0x40, 1, ::ThrowParamRow)
}
