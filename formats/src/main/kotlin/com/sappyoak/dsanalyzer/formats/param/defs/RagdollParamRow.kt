// GENERATED FILE - do not edit. Regenerate with:
//
//     ./gradlew :tools:generateParamDefs -Pptde=<DATA directory> -Pdsr=<DATA directory>
//
// Source: paramdef.paramdefbnd, RagdollParam.paramdef, read from the installation

@file:Suppress("SpellCheckingInspection")

package com.sappyoak.dsanalyzer.formats.param.defs

import com.sappyoak.dsanalyzer.formats.param.ParamField
import com.sappyoak.dsanalyzer.formats.param.ParamRow
import com.sappyoak.dsanalyzer.formats.param.ParamRowData
import com.sappyoak.dsanalyzer.formats.param.ParamRowType
import com.sappyoak.dsanalyzer.formats.param.paramdef.ParamFieldType

/** `RAGDOLL_PARAM_ST`: PrepareToDie 0x30, Remastered 0x30 a row, data version 1 */
public class RagdollParamRow(row: ParamRowData) : ParamRow(row) {
    /** `f32` at 0x0 */
    public val hierarchyGain: Float get() = bytes.f32(0x0)

    /** `f32` at 0x4 */
    public val velocityDamping: Float get() = bytes.f32(0x4)

    /** `f32` at 0x8 */
    public val accelGain: Float get() = bytes.f32(0x8)

    /** `f32` at 0xC */
    public val velocityGain: Float get() = bytes.f32(0xC)

    /** `f32` at 0x10 */
    public val positionGain: Float get() = bytes.f32(0x10)

    /** `f32` at 0x14 */
    public val maxLinerVelocity: Float get() = bytes.f32(0x14)

    /** `f32` at 0x18 */
    public val maxAngularVelocity: Float get() = bytes.f32(0x18)

    /** `f32` at 0x1C */
    public val snapGain: Float get() = bytes.f32(0x1C)

    /** `u8` at 0x20, `RAGDOLL_PARAM_BOOL` */
    public val enable: Int get() = bytes.u8(0x20)

    /** `s8` at 0x21 */
    public val partsHitMaskNo: Int get() = bytes.s8(0x21)

    public companion object : ParamRowType<RagdollParamRow>("RAGDOLL_PARAM_ST", 0x30, 1, FIELDS, ::RagdollParamRow)
}

private val FIELDS: List<ParamField<RagdollParamRow>> = listOf<ParamField<RagdollParamRow>>(
    ParamField("hierarchyGain", ParamFieldType.F32, 0x0, null, null, null, "ヒエラルキーゲイン") { it.hierarchyGain },
    ParamField("velocityDamping", ParamFieldType.F32, 0x4, null, null, null, "速度減衰") { it.velocityDamping },
    ParamField("accelGain", ParamFieldType.F32, 0x8, null, null, null, "加速度ゲイン") { it.accelGain },
    ParamField("velocityGain", ParamFieldType.F32, 0xC, null, null, null, "速度ゲイン") { it.velocityGain },
    ParamField("positionGain", ParamFieldType.F32, 0x10, null, null, null, "位置ゲイン") { it.positionGain },
    ParamField("maxLinerVelocity", ParamFieldType.F32, 0x14, null, null, null, "最大速度") { it.maxLinerVelocity },
    ParamField("maxAngularVelocity", ParamFieldType.F32, 0x18, null, null, null, "最大角速度") { it.maxAngularVelocity },
    ParamField("snapGain", ParamFieldType.F32, 0x1C, null, null, null, "スナップゲイン") { it.snapGain },
    ParamField("enable", ParamFieldType.U8, 0x20, null, null, "RAGDOLL_PARAM_BOOL", "動くか") { it.enable },
    ParamField("partsHitMaskNo", ParamFieldType.S8, 0x21, null, null, null, "部位あたりマスク番号") { it.partsHitMaskNo }
)
