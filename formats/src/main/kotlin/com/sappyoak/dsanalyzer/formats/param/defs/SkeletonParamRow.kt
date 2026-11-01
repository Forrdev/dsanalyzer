// GENERATED FILE - do not edit. Regenerate with:
//
//     ./gradlew :tools:generateParamDefs -Pptde=<DATA directory> -Pdsr=<DATA directory>
//
// Source: paramdef.paramdefbnd, SkeletonParam.paramdef, read from the installation

@file:Suppress("SpellCheckingInspection")

package com.sappyoak.dsanalyzer.formats.param.defs

import com.sappyoak.dsanalyzer.formats.param.ParamField
import com.sappyoak.dsanalyzer.formats.param.ParamRow
import com.sappyoak.dsanalyzer.formats.param.ParamRowData
import com.sappyoak.dsanalyzer.formats.param.ParamRowType
import com.sappyoak.dsanalyzer.formats.param.paramdef.ParamFieldType

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

    public companion object : ParamRowType<SkeletonParamRow>("SKELETON_PARAM_ST", 0x30, 1, FIELDS, ::SkeletonParamRow)
}

private val FIELDS: List<ParamField<SkeletonParamRow>> = listOf<ParamField<SkeletonParamRow>>(
    ParamField("neckTurnGain", ParamFieldType.F32, 0x0, null, null, null, "首振りゲイン") { it.neckTurnGain },
    ParamField("originalGroundHeightMS", ParamFieldType.S16, 0x4, null, null, null, "原点から地面への高さ[cm]") { it.originalGroundHeightMS },
    ParamField("minAnkleHeightMS", ParamFieldType.S16, 0x6, null, null, null, "足首を上げられる最低の高さ[cm]") { it.minAnkleHeightMS },
    ParamField("maxAnkleHeightMS", ParamFieldType.S16, 0x8, null, null, null, "足首を上げられる最大の高さ[cm]") { it.maxAnkleHeightMS },
    ParamField("cosineMaxKneeAngle", ParamFieldType.S16, 0xA, null, null, null, "ひざを曲げられる最大の角度(コサインの値)") { it.cosineMaxKneeAngle },
    ParamField("cosineMinKneeAngle", ParamFieldType.S16, 0xC, null, null, null, "ひざを曲げられる最小の角度") { it.cosineMinKneeAngle },
    ParamField("footPlantedAnkleHeightMS", ParamFieldType.S16, 0xE, null, null, null, "足首がくっついていると仮定する最低位置[cm]") { it.footPlantedAnkleHeightMS },
    ParamField("footRaisedAnkleHeightMS", ParamFieldType.S16, 0x10, null, null, null, "足首が離れていると仮定する最高位置[cm]") { it.footRaisedAnkleHeightMS },
    ParamField("raycastDistanceUp", ParamFieldType.S16, 0x12, null, null, null, "足首よりどれぐらい上からレイキャストするか[cm]") { it.raycastDistanceUp },
    ParamField("raycastDistanceDown", ParamFieldType.S16, 0x14, null, null, null, "足首からどれぐらい下までレイキャストするか[cm]") { it.raycastDistanceDown },
    ParamField("footEndLS_X", ParamFieldType.S16, 0x16, null, null, null, "つま先位置X[cm]") { it.footEndLS_X },
    ParamField("footEndLS_Y", ParamFieldType.S16, 0x18, null, null, null, "つま先位置Y[cm]") { it.footEndLS_Y },
    ParamField("footEndLS_Z", ParamFieldType.S16, 0x1A, null, null, null, "つま先位置Z[cm]") { it.footEndLS_Z },
    ParamField("onOffGain", ParamFieldType.S16, 0x1C, null, null, null, "足の接地をオンオフするときのゲイン[1/100]") { it.onOffGain },
    ParamField("groundAscendingGain", ParamFieldType.S16, 0x1E, null, null, null, "地面の高さが高くなったときのゲイン[1/100]") { it.groundAscendingGain },
    ParamField("groundDescendingGain", ParamFieldType.S16, 0x20, null, null, null, "地面の高さが低くなったときのゲイン[1/100]") { it.groundDescendingGain },
    ParamField("footRaisedGain", ParamFieldType.S16, 0x22, null, null, null, "足が上がったときのゲイン[1/100]") { it.footRaisedGain },
    ParamField("footPlantedGain", ParamFieldType.S16, 0x24, null, null, null, "足が接地したときのゲイン[1/100]") { it.footPlantedGain },
    ParamField("footUnlockGain", ParamFieldType.S16, 0x26, null, null, null, "足がロック/アンロックしたときのゲイン[1/100]") { it.footUnlockGain },
    ParamField("kneeAxisType", ParamFieldType.U8, 0x28, null, null, "SKELETON_PARAM_KNEE_AXIS_DIR", "ひざの軸方向") { it.kneeAxisType },
    ParamField("useFootLocking", ParamFieldType.U8, 0x29, null, null, "RAGDOLL_PARAM_BOOL", "足/足首をロック（固定？）するか") { it.useFootLocking },
    ParamField("footPlacementOn", ParamFieldType.U8, 0x2A, null, null, "RAGDOLL_PARAM_BOOL", "足の接地が有効か") { it.footPlacementOn },
    ParamField("twistKneeAxisType", ParamFieldType.U8, 0x2B, null, null, "SKELETON_PARAM_KNEE_AXIS_DIR", "ひねり用ひざ関節の軸方向") { it.twistKneeAxisType },
    ParamField("neckTurnPriority", ParamFieldType.S8, 0x2C, null, null, null, "首振り優先度") { it.neckTurnPriority },
    ParamField("neckTurnMaxAngle", ParamFieldType.U8, 0x2D, null, null, null, "首振り最大角度") { it.neckTurnMaxAngle }
)
