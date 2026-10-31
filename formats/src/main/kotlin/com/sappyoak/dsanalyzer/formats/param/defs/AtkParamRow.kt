// GENERATED FILE - do not edit. Regenerate with:
//
//     ./gradlew :tools:generateParamDefs -Pptde=<DATA directory> -Pdsr=<DATA directory>
//
// Source: paramdef.paramdefbnd, AtkParam.paramdef, read from the installation

@file:Suppress("SpellCheckingInspection")

package com.sappyoak.dsanalyzer.formats.param.defs

import com.sappyoak.dsanalyzer.formats.param.ParamRow
import com.sappyoak.dsanalyzer.formats.param.ParamRowData
import com.sappyoak.dsanalyzer.formats.param.ParamRowType

/** `ATK_PARAM_ST`: PrepareToDie 0x80, Remastered 0x80 a row, data version 1 */
public class AtkParamRow(row: ParamRowData) : ParamRow(row) {
    /** `f32` at 0x0 */
    public val hit0_Radius: Float get() = bytes.f32(0x0)

    /** `f32` at 0x4 */
    public val hit1_Radius: Float get() = bytes.f32(0x4)

    /** `f32` at 0x8 */
    public val hit2_Radius: Float get() = bytes.f32(0x8)

    /** `f32` at 0xC */
    public val hit3_Radius: Float get() = bytes.f32(0xC)

    /** `f32` at 0x10 */
    public val knockbackDist: Float get() = bytes.f32(0x10)

    /** `f32` at 0x14 */
    public val hitStopTime: Float get() = bytes.f32(0x14)

    /** `s32` at 0x18 */
    public val spEffectId0: Int get() = bytes.s32(0x18)

    /** `s32` at 0x1C */
    public val spEffectId1: Int get() = bytes.s32(0x1C)

    /** `s32` at 0x20 */
    public val spEffectId2: Int get() = bytes.s32(0x20)

    /** `s32` at 0x24 */
    public val spEffectId3: Int get() = bytes.s32(0x24)

    /** `s32` at 0x28 */
    public val spEffectId4: Int get() = bytes.s32(0x28)

    /** `s16` at 0x2C */
    public val hit0_DmyPoly1: Int get() = bytes.s16(0x2C)

    /** `s16` at 0x2E */
    public val hit1_DmyPoly1: Int get() = bytes.s16(0x2E)

    /** `s16` at 0x30 */
    public val hit2_DmyPoly1: Int get() = bytes.s16(0x30)

    /** `s16` at 0x32 */
    public val hit3_DmyPoly1: Int get() = bytes.s16(0x32)

    /** `s16` at 0x34 */
    public val hit0_DmyPoly2: Int get() = bytes.s16(0x34)

    /** `s16` at 0x36 */
    public val hit1_DmyPoly2: Int get() = bytes.s16(0x36)

    /** `s16` at 0x38 */
    public val hit2_DmyPoly2: Int get() = bytes.s16(0x38)

    /** `s16` at 0x3A */
    public val hit3_DmyPoly2: Int get() = bytes.s16(0x3A)

    /** `u16` at 0x3C */
    public val blowingCorrection: Int get() = bytes.u16(0x3C)

    /** `u16` at 0x3E */
    public val atkPhysCorrection: Int get() = bytes.u16(0x3E)

    /** `u16` at 0x40 */
    public val atkMagCorrection: Int get() = bytes.u16(0x40)

    /** `u16` at 0x42 */
    public val atkFireCorrection: Int get() = bytes.u16(0x42)

    /** `u16` at 0x44 */
    public val atkThunCorrection: Int get() = bytes.u16(0x44)

    /** `u16` at 0x46 */
    public val atkStamCorrection: Int get() = bytes.u16(0x46)

    /** `u16` at 0x48 */
    public val guardAtkRateCorrection: Int get() = bytes.u16(0x48)

    /** `u16` at 0x4A */
    public val guardBreakCorrection: Int get() = bytes.u16(0x4A)

    /** `u16` at 0x4C */
    public val atkThrowEscapeCorrection: Int get() = bytes.u16(0x4C)

    /** `u16` at 0x4E */
    public val atkSuperArmorCorrection: Int get() = bytes.u16(0x4E)

    /** `u16` at 0x50 */
    public val atkPhys: Int get() = bytes.u16(0x50)

    /** `u16` at 0x52 */
    public val atkMag: Int get() = bytes.u16(0x52)

    /** `u16` at 0x54 */
    public val atkFire: Int get() = bytes.u16(0x54)

    /** `u16` at 0x56 */
    public val atkThun: Int get() = bytes.u16(0x56)

    /** `u16` at 0x58 */
    public val atkStam: Int get() = bytes.u16(0x58)

    /** `u16` at 0x5A */
    public val guardAtkRate: Int get() = bytes.u16(0x5A)

    /** `u16` at 0x5C */
    public val guardBreakRate: Int get() = bytes.u16(0x5C)

    /** `u16` at 0x5E */
    public val atkSuperArmor: Int get() = bytes.u16(0x5E)

    /** `u16` at 0x60 */
    public val atkThrowEscape: Int get() = bytes.u16(0x60)

    /** `u16` at 0x62 */
    public val atkObj: Int get() = bytes.u16(0x62)

    /** `s16` at 0x64 */
    public val guardStaminaCutRate: Int get() = bytes.s16(0x64)

    /** `s16` at 0x66 */
    public val guardRate: Int get() = bytes.s16(0x66)

    /** `u16` at 0x68 */
    public val throwTypeId: Int get() = bytes.u16(0x68)

    /** `u8` at 0x6A, `ATK_PARAM_HIT_TYPE` */
    public val hit0_hitType: Int get() = bytes.u8(0x6A)

    /** `u8` at 0x6B, `ATK_PARAM_HIT_TYPE` */
    public val hit1_hitType: Int get() = bytes.u8(0x6B)

    /** `u8` at 0x6C, `ATK_PARAM_HIT_TYPE` */
    public val hit2_hitType: Int get() = bytes.u8(0x6C)

    /** `u8` at 0x6D, `ATK_PARAM_HIT_TYPE` */
    public val hit3_hitType: Int get() = bytes.u8(0x6D)

    /** `u8` at 0x6E */
    public val hti0_Priority: Int get() = bytes.u8(0x6E)

    /** `u8` at 0x6F */
    public val hti1_Priority: Int get() = bytes.u8(0x6F)

    /** `u8` at 0x70 */
    public val hti2_Priority: Int get() = bytes.u8(0x70)

    /** `u8` at 0x71 */
    public val hti3_Priority: Int get() = bytes.u8(0x71)

    /** `u8` at 0x72 */
    public val dmgLevel: Int get() = bytes.u8(0x72)

    /** `u8` at 0x73, `ATK_PARAM_MAP_HIT` */
    public val mapHitType: Int get() = bytes.u8(0x73)

    /** `s8` at 0x74 */
    public val guardCutCancelRate: Int get() = bytes.s8(0x74)

    /** `u8` at 0x75, `ATKPARAM_ATKATTR_TYPE` */
    public val atkAttribute: Int get() = bytes.u8(0x75)

    /** `u8` at 0x76, `ATKPARAM_SPATTR_TYPE` */
    public val spAttribute: Int get() = bytes.u8(0x76)

    /** `u8` at 0x77, `BEHAVIOR_ATK_TYPE` */
    public val atkType: Int get() = bytes.u8(0x77)

    /** `u8` at 0x78, `WEP_MATERIAL_ATK` */
    public val atkMaterial: Int get() = bytes.u8(0x78)

    /** `u8` at 0x79, `BEHAVIOR_ATK_SIZE` */
    public val atkSize: Int get() = bytes.u8(0x79)

    /** `u8` at 0x7A, `WEP_MATERIAL_DEF` */
    public val defMaterial: Int get() = bytes.u8(0x7A)

    /** `u8` at 0x7B, `WEP_MATERIAL_DEF_SFX` */
    public val defSfxMaterial: Int get() = bytes.u8(0x7B)

    /** `u8` at 0x7C, `ATK_PARAM_HIT_SOURCE` */
    public val hitSourceType: Int get() = bytes.u8(0x7C)

    /** `u8` at 0x7D, `ATK_PATAM_THROWFLAG_TYPE` */
    public val throwFlag: Int get() = bytes.u8(0x7D)

    /** bit 0 of the `u8` at 0x7E, `ATK_PARAM_BOOL` */
    public val disableGuard: Boolean get() = bytes.flag(0x7E, 0)

    /** bit 1 of the `u8` at 0x7E, `ATK_PARAM_BOOL` */
    public val disableStaminaAttack: Boolean get() = bytes.flag(0x7E, 1)

    /** bit 2 of the `u8` at 0x7E, `ATK_PARAM_BOOL` */
    public val disableHitSpEffect: Boolean get() = bytes.flag(0x7E, 2)

    /** bit 3 of the `u8` at 0x7E, `ATK_PARAM_BOOL` */
    public val ignoreNotifyMissSwingForAI: Boolean get() = bytes.flag(0x7E, 3)

    /** bit 4 of the `u8` at 0x7E, `ATK_PARAM_BOOL` */
    public val repeatHitSfx: Boolean get() = bytes.flag(0x7E, 4)

    /** bit 5 of the `u8` at 0x7E, `ATK_PARAM_BOOL` */
    public val isArrowAtk: Boolean get() = bytes.flag(0x7E, 5)

    /** bit 6 of the `u8` at 0x7E, `ATK_PARAM_BOOL` */
    public val isGhostAtk: Boolean get() = bytes.flag(0x7E, 6)

    /** bit 7 of the `u8` at 0x7E, `ATK_PARAM_BOOL` */
    public val isDisableNoDamage: Boolean get() = bytes.flag(0x7E, 7)

    public companion object : ParamRowType<AtkParamRow>("ATK_PARAM_ST", 0x80, 1, ::AtkParamRow)
}
