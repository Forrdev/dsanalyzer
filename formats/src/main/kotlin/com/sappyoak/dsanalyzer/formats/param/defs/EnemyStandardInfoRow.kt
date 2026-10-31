// GENERATED FILE - do not edit. Regenerate with:
//
//     ./gradlew :tools:generateParamDefs -Pptde=<DATA directory> -Pdsr=<DATA directory>
//
// Source: paramdef.paramdefbnd, EnemyStandardInfo.paramdef, read from the installation

@file:Suppress("SpellCheckingInspection")

package com.sappyoak.dsanalyzer.formats.param.defs

import com.sappyoak.dsanalyzer.formats.param.ParamRow
import com.sappyoak.dsanalyzer.formats.param.ParamRowData
import com.sappyoak.dsanalyzer.formats.param.ParamRowType

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

    public companion object : ParamRowType<EnemyStandardInfoRow>("ENEMY_STANDARD_INFO_BANK", 0x80, 1, ::EnemyStandardInfoRow)
}
