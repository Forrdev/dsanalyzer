// GENERATED FILE - do not edit. Regenerate with:
//
//     ./gradlew :tools:generateParamDefs -Pptde=<DATA directory> -Pdsr=<DATA directory>
//
// Source: paramdef.paramdefbnd, LockCamParam.paramdef, read from the installation

@file:Suppress("SpellCheckingInspection")

package com.sappyoak.dsanalyzer.formats.param.defs

import com.sappyoak.dsanalyzer.formats.param.ParamRow
import com.sappyoak.dsanalyzer.formats.param.ParamRowData
import com.sappyoak.dsanalyzer.formats.param.ParamRowType

/** `LOCK_CAM_PARAM_ST`: PrepareToDie 0x20, Remastered 0x20 a row, data version 1 */
public class LockCamParamRow(row: ParamRowData) : ParamRow(row) {
    /** `f32` at 0x0 */
    public val camDistTarget: Float get() = bytes.f32(0x0)

    /** `f32` at 0x4 */
    public val rotRangeMinX: Float get() = bytes.f32(0x4)

    /** `f32` at 0x8 */
    public val lockRotXShiftRatio: Float get() = bytes.f32(0x8)

    /** `f32` at 0xC */
    public val chrOrgOffset_Y: Float get() = bytes.f32(0xC)

    /** `f32` at 0x10 */
    public val chrLockRangeMaxRadius: Float get() = bytes.f32(0x10)

    /** `f32` at 0x14 */
    public val camFovY: Float get() = bytes.f32(0x14)

    public companion object : ParamRowType<LockCamParamRow>("LOCK_CAM_PARAM_ST", 0x20, 1, ::LockCamParamRow)
}
