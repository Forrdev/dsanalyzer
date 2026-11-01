// GENERATED FILE - do not edit. Regenerate with:
//
//     ./gradlew :tools:generateParamDefs -Pptde=<DATA directory> -Pdsr=<DATA directory>
//
// Source: paramdef.paramdefbnd, EnemyStandardInfo.paramdef, read from the installation

@file:Suppress("SpellCheckingInspection")

package com.sappyoak.dsanalyzer.formats.param.defs

import com.sappyoak.dsanalyzer.formats.param.ParamField
import com.sappyoak.dsanalyzer.formats.param.ParamRow
import com.sappyoak.dsanalyzer.formats.param.ParamRowData
import com.sappyoak.dsanalyzer.formats.param.ParamRowType
import com.sappyoak.dsanalyzer.formats.param.paramdef.ParamFieldType

/** `ENEMY_STANDARD_INFO_BANK`: PrepareToDie 0x80, Remastered 0x80 a row, data version 1 */
public class EnemyStandardInfoRow(row: ParamRowData) : ParamRow(row) {
    /** `s32` at 0x0, `ENEMY_BEHAVIOR_ID` */
    public val enemyBehaviorID: Int get() = bytes.s32(0x0)

    /** `u16` at 0x4 */
    public val hP: Int get() = bytes.u16(0x4)

    /** `u16` at 0x6 */
    public val attackPower: Int get() = bytes.u16(0x6)

    /** `s32` at 0x8, `ChrType` */
    public val chrType: Int get() = bytes.s32(0x8)

    /** `f32` at 0xC */
    public val hitHeight: Float get() = bytes.f32(0xC)

    /** `f32` at 0x10 */
    public val hitRadius: Float get() = bytes.f32(0x10)

    /** `f32` at 0x14 */
    public val weight: Float get() = bytes.f32(0x14)

    /** `f32` at 0x18 */
    public val dynamicFriction: Float get() = bytes.f32(0x18)

    /** `f32` at 0x1C */
    public val staticFriction: Float get() = bytes.f32(0x1C)

    /** `s32` at 0x20 */
    public val upperDefState: Int get() = bytes.s32(0x20)

    /** `s32` at 0x24 */
    public val actionDefState: Int get() = bytes.s32(0x24)

    /** `f32` at 0x28 */
    public val rotY_per_Second: Float get() = bytes.f32(0x28)

    /** `u8` at 0x40 */
    public val rotY_per_Second_old: Int get() = bytes.u8(0x40)

    /** `u8` at 0x41 */
    public val enableSideStep: Int get() = bytes.u8(0x41)

    /** `u8` at 0x42 */
    public val useRagdollHit: Int get() = bytes.u8(0x42)

    /** `u16` at 0x48 */
    public val stamina: Int get() = bytes.u16(0x48)

    /** `u16` at 0x4A */
    public val staminaRecover: Int get() = bytes.u16(0x4A)

    /** `u16` at 0x4C */
    public val staminaConsumption: Int get() = bytes.u16(0x4C)

    /** `u16` at 0x4E */
    public val deffenct_Phys: Int get() = bytes.u16(0x4E)

    public companion object : ParamRowType<EnemyStandardInfoRow>("ENEMY_STANDARD_INFO_BANK", 0x80, 1, FIELDS, ::EnemyStandardInfoRow)
}

private val FIELDS: List<ParamField<EnemyStandardInfoRow>> = listOf<ParamField<EnemyStandardInfoRow>>(
    ParamField("enemyBehaviorID", ParamFieldType.S32, 0x0, null, null, "ENEMY_BEHAVIOR_ID", "挙動ｉｄ") { it.enemyBehaviorID },
    ParamField("hP", ParamFieldType.U16, 0x4, null, null, null, "ヒットポイント") { it.hP },
    ParamField("attackPower", ParamFieldType.U16, 0x6, null, null, null, "攻撃力") { it.attackPower },
    ParamField("chrType", ParamFieldType.S32, 0x8, null, null, "ChrType", "キャラタイプ") { it.chrType },
    ParamField("hitHeight", ParamFieldType.F32, 0xC, null, null, null, "あたりの高さ[m]") { it.hitHeight },
    ParamField("hitRadius", ParamFieldType.F32, 0x10, null, null, null, "あたりの半径[m]") { it.hitRadius },
    ParamField("weight", ParamFieldType.F32, 0x14, null, null, null, "重さ[kg]") { it.weight },
    ParamField("dynamicFriction", ParamFieldType.F32, 0x18, null, null, null, "動摩擦力") { it.dynamicFriction },
    ParamField("staticFriction", ParamFieldType.F32, 0x1C, null, null, null, "静摩擦力") { it.staticFriction },
    ParamField("upperDefState", ParamFieldType.S32, 0x20, null, null, null, "上半身初期状態") { it.upperDefState },
    ParamField("actionDefState", ParamFieldType.S32, 0x24, null, null, null, "アクション初期状態") { it.actionDefState },
    ParamField("rotY_per_Second", ParamFieldType.F32, 0x28, null, null, null, "単位時間当たり旋回できる角度[deg/s]") { it.rotY_per_Second },
    ParamField("rotY_per_Second_old", ParamFieldType.U8, 0x40, null, null, null, "未使用") { it.rotY_per_Second_old },
    ParamField("enableSideStep", ParamFieldType.U8, 0x41, null, null, null, "左右移動できるか") { it.enableSideStep },
    ParamField("useRagdollHit", ParamFieldType.U8, 0x42, null, null, null, "キャラあたりにラグドールを使用するか") { it.useRagdollHit },
    ParamField("stamina", ParamFieldType.U16, 0x48, null, null, null, "スタミナ量") { it.stamina },
    ParamField("staminaRecover", ParamFieldType.U16, 0x4A, null, null, null, "スタミナ回復") { it.staminaRecover },
    ParamField("staminaConsumption", ParamFieldType.U16, 0x4C, null, null, null, "スタミナ基本消費") { it.staminaConsumption },
    ParamField("deffenct_Phys", ParamFieldType.U16, 0x4E, null, null, null, "物理防御力") { it.deffenct_Phys }
)
