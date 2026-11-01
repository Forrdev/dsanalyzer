// GENERATED FILE - do not edit. Regenerate with:
//
//     ./gradlew :tools:generateParamDefs -Pptde=<DATA directory> -Pdsr=<DATA directory>
//
// Source: paramdef.paramdefbnd, BehaviorParam.paramdef, read from the installation

@file:Suppress("SpellCheckingInspection")

package com.sappyoak.dsanalyzer.formats.param.defs

import com.sappyoak.dsanalyzer.formats.param.ParamField
import com.sappyoak.dsanalyzer.formats.param.ParamRow
import com.sappyoak.dsanalyzer.formats.param.ParamRowData
import com.sappyoak.dsanalyzer.formats.param.ParamRowType
import com.sappyoak.dsanalyzer.formats.param.paramdef.ParamFieldType

/** `BEHAVIOR_PARAM_ST`: PrepareToDie 0x20, Remastered 0x20 a row, data version 2 */
public class BehaviorParamRow(row: ParamRowData) : ParamRow(row) {
    /** `s32` at 0x0 */
    public val variationId: Int get() = bytes.s32(0x0)

    /** `s32` at 0x4 */
    public val behaviorJudgeId: Int get() = bytes.s32(0x4)

    /** `u8` at 0x8 */
    public val ezStateBehaviorType_old: Int get() = bytes.u8(0x8)

    /** `u8` at 0x9, `BEHAVIOR_REF_TYPE` */
    public val refType: Int get() = bytes.u8(0x9)

    /** `s32` at 0xC */
    public val refId: Int get() = bytes.s32(0xC)

    /** `s32` at 0x10 */
    public val sfxVariationId: Int get() = bytes.s32(0x10)

    /** `s32` at 0x14 */
    public val stamina: Int get() = bytes.s32(0x14)

    /** `s32` at 0x18 */
    public val mp: Int get() = bytes.s32(0x18)

    /** `u8` at 0x1C, `BEHAVIOR_CATEGORY` */
    public val category: Int get() = bytes.u8(0x1C)

    /** `u8` at 0x1D */
    public val heroPoint: Int get() = bytes.u8(0x1D)

    public companion object : ParamRowType<BehaviorParamRow>("BEHAVIOR_PARAM_ST", 0x20, 2, FIELDS, ::BehaviorParamRow)
}

private val FIELDS: List<ParamField<BehaviorParamRow>> = listOf<ParamField<BehaviorParamRow>>(
    ParamField("variationId", ParamFieldType.S32, 0x0, null, null, null, "行動バリエーションID") { it.variationId },
    ParamField("behaviorJudgeId", ParamFieldType.S32, 0x4, null, null, null, "行動判定ID") { it.behaviorJudgeId },
    ParamField("ezStateBehaviorType_old", ParamFieldType.U8, 0x8, null, null, null, "IDルール用") { it.ezStateBehaviorType_old },
    ParamField("refType", ParamFieldType.U8, 0x9, null, null, "BEHAVIOR_REF_TYPE", "参照IDタイプ") { it.refType },
    ParamField("refId", ParamFieldType.S32, 0xC, null, null, null, "参照ID") { it.refId },
    ParamField("sfxVariationId", ParamFieldType.S32, 0x10, null, null, null, "SFX バリエーションID") { it.sfxVariationId },
    ParamField("stamina", ParamFieldType.S32, 0x14, null, null, null, "消費スタミナ") { it.stamina },
    ParamField("mp", ParamFieldType.S32, 0x18, null, null, null, "消費MP") { it.mp },
    ParamField("category", ParamFieldType.U8, 0x1C, null, null, "BEHAVIOR_CATEGORY", "カテゴリ") { it.category },
    ParamField("heroPoint", ParamFieldType.U8, 0x1D, null, null, null, "消費人間性") { it.heroPoint }
)
