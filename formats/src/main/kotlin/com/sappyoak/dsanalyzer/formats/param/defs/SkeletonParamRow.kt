// GENERATED FILE - do not edit. Regenerate with:
//
//     ./gradlew :tools:generateParamDefs -Pptde=<DATA directory> -Pdsr=<DATA directory>
//
// Source: paramdef.paramdefbnd, SkeletonParam.paramdef, read from the installation

@file:Suppress("SpellCheckingInspection")

package com.sappyoak.dsanalyzer.formats.param.defs

import com.sappyoak.dsanalyzer.formats.param.ParamRow
import com.sappyoak.dsanalyzer.formats.param.ParamRowData
import com.sappyoak.dsanalyzer.formats.param.ParamRowType

/** `SKELETON_PARAM_ST`: PrepareToDie 0x30, Remastered 0x30 a row, data version 1 */
public class SkeletonParamRow(row: ParamRowData) : ParamRow(row) {
    /** `f32` at 0x0 */
    public val neckTurnGain: Float get() = bytes.f32(0x0)

    /** `s16` at 0x4 */
    public val originalGroundHeightMS: Int get() = bytes.s16(0x4)

    /** `s16` at 0x6 */
    public val minAnkleHeightMS: Int get() = bytes.s16(0x6)

    /** `s16` at 0x8 */
    public val maxAnkleHeightMS: Int get() = bytes.s16(0x8)

    /** `s16` at 0xA */
    public val cosineMaxKneeAngle: Int get() = bytes.s16(0xA)

    /** `s16` at 0xC */
    public val cosineMinKneeAngle: Int get() = bytes.s16(0xC)

    /** `s16` at 0xE */
    public val footPlantedAnkleHeightMS: Int get() = bytes.s16(0xE)

    /** `s16` at 0x10 */
    public val footRaisedAnkleHeightMS: Int get() = bytes.s16(0x10)

    /** `s16` at 0x12 */
    public val raycastDistanceUp: Int get() = bytes.s16(0x12)

    /** `s16` at 0x14 */
    public val raycastDistanceDown: Int get() = bytes.s16(0x14)

    /** `s16` at 0x16 */
    public val footEndLS_X: Int get() = bytes.s16(0x16)

    /** `s16` at 0x18 */
    public val footEndLS_Y: Int get() = bytes.s16(0x18)

    /** `s16` at 0x1A */
    public val footEndLS_Z: Int get() = bytes.s16(0x1A)

    /** `s16` at 0x1C */
    public val onOffGain: Int get() = bytes.s16(0x1C)

    /** `s16` at 0x1E */
    public val groundAscendingGain: Int get() = bytes.s16(0x1E)

    /** `s16` at 0x20 */
    public val groundDescendingGain: Int get() = bytes.s16(0x20)

    /** `s16` at 0x22 */
    public val footRaisedGain: Int get() = bytes.s16(0x22)

    /** `s16` at 0x24 */
    public val footPlantedGain: Int get() = bytes.s16(0x24)

    /** `s16` at 0x26 */
    public val footUnlockGain: Int get() = bytes.s16(0x26)

    /** `u8` at 0x28, `SKELETON_PARAM_KNEE_AXIS_DIR` */
    public val kneeAxisType: Int get() = bytes.u8(0x28)

    /** `u8` at 0x29, `RAGDOLL_PARAM_BOOL` */
    public val useFootLocking: Int get() = bytes.u8(0x29)

    /** `u8` at 0x2A, `RAGDOLL_PARAM_BOOL` */
    public val footPlacementOn: Int get() = bytes.u8(0x2A)

    /** `u8` at 0x2B, `SKELETON_PARAM_KNEE_AXIS_DIR` */
    public val twistKneeAxisType: Int get() = bytes.u8(0x2B)

    /** `s8` at 0x2C */
    public val neckTurnPriority: Int get() = bytes.s8(0x2C)

    /** `u8` at 0x2D */
    public val neckTurnMaxAngle: Int get() = bytes.u8(0x2D)

    public companion object : ParamRowType<SkeletonParamRow>("SKELETON_PARAM_ST", 0x30, 1, ::SkeletonParamRow)
}
