// GENERATED FILE - do not edit. Regenerate with:
//
//     ./gradlew :tools:generateParamDefs -Pptde=<DATA directory> -Pdsr=<DATA directory>
//
// Source: paramdef.paramdefbnd, BulletParam.paramdef, read from the installation

@file:Suppress("SpellCheckingInspection")

package com.sappyoak.dsanalyzer.formats.param.defs

import com.sappyoak.dsanalyzer.formats.param.ParamRow
import com.sappyoak.dsanalyzer.formats.param.ParamRowData
import com.sappyoak.dsanalyzer.formats.param.ParamRowType

/** `BULLET_PARAM_ST`: PrepareToDie 0xA0, Remastered 0xA0 a row, data version 2 */
public class BulletParamRow(row: ParamRowData) : ParamRow(row) {
    /** `s32` at 0x0 */
    public val atkId_Bullet: Int get() = bytes.s32(0x0)

    /** `s32` at 0x4 */
    public val sfxId_Bullet: Int get() = bytes.s32(0x4)

    /** `s32` at 0x8 */
    public val sfxId_Hit: Int get() = bytes.s32(0x8)

    /** `s32` at 0xC */
    public val sfxId_Flick: Int get() = bytes.s32(0xC)

    /** `f32` at 0x10 */
    public val life: Float get() = bytes.f32(0x10)

    /** `f32` at 0x14 */
    public val dist: Float get() = bytes.f32(0x14)

    /** `f32` at 0x18 */
    public val shootInterval: Float get() = bytes.f32(0x18)

    /** `f32` at 0x1C */
    public val gravityInRange: Float get() = bytes.f32(0x1C)

    /** `f32` at 0x20 */
    public val gravityOutRange: Float get() = bytes.f32(0x20)

    /** `f32` at 0x24 */
    public val hormingStopRange: Float get() = bytes.f32(0x24)

    /** `f32` at 0x28 */
    public val initVellocity: Float get() = bytes.f32(0x28)

    /** `f32` at 0x2C */
    public val accelInRange: Float get() = bytes.f32(0x2C)

    /** `f32` at 0x30 */
    public val accelOutRange: Float get() = bytes.f32(0x30)

    /** `f32` at 0x34 */
    public val maxVellocity: Float get() = bytes.f32(0x34)

    /** `f32` at 0x38 */
    public val minVellocity: Float get() = bytes.f32(0x38)

    /** `f32` at 0x3C */
    public val accelTime: Float get() = bytes.f32(0x3C)

    /** `f32` at 0x40 */
    public val homingBeginDist: Float get() = bytes.f32(0x40)

    /** `f32` at 0x44 */
    public val hitRadius: Float get() = bytes.f32(0x44)

    /** `f32` at 0x48 */
    public val hitRadiusMax: Float get() = bytes.f32(0x48)

    /** `f32` at 0x4C */
    public val spreadTime: Float get() = bytes.f32(0x4C)

    /** `f32` at 0x50 */
    public val expDelay: Float get() = bytes.f32(0x50)

    /** `f32` at 0x54 */
    public val hormingOffsetRange: Float get() = bytes.f32(0x54)

    /** `f32` at 0x58 */
    public val dmgHitRecordLifeTime: Float get() = bytes.f32(0x58)

    /** `f32` at 0x5C */
    public val externalForce: Float get() = bytes.f32(0x5C)

    /** `s32` at 0x60 */
    public val spEffectIDForShooter: Int get() = bytes.s32(0x60)

    /** `s32` at 0x64 */
    public val autoSearchNPCThinkID: Int get() = bytes.s32(0x64)

    /** `s32` at 0x68 */
    public val hitBulletID: Int get() = bytes.s32(0x68)

    /** `s32` at 0x6C */
    public val spEffectId0: Int get() = bytes.s32(0x6C)

    /** `s32` at 0x70 */
    public val spEffectId1: Int get() = bytes.s32(0x70)

    /** `s32` at 0x74 */
    public val spEffectId2: Int get() = bytes.s32(0x74)

    /** `s32` at 0x78 */
    public val spEffectId3: Int get() = bytes.s32(0x78)

    /** `s32` at 0x7C */
    public val spEffectId4: Int get() = bytes.s32(0x7C)

    /** `u16` at 0x80 */
    public val numShoot: Int get() = bytes.u16(0x80)

    /** `s16` at 0x82 */
    public val homingAngle: Int get() = bytes.s16(0x82)

    /** `s16` at 0x84 */
    public val shootAngle: Int get() = bytes.s16(0x84)

    /** `s16` at 0x86 */
    public val shootAngleInterval: Int get() = bytes.s16(0x86)

    /** `s16` at 0x88 */
    public val shootAngleXInterval: Int get() = bytes.s16(0x88)

    /** `s8` at 0x8A */
    public val damageDamp: Int get() = bytes.s8(0x8A)

    /** `s8` at 0x8B */
    public val spelDamageDamp: Int get() = bytes.s8(0x8B)

    /** `s8` at 0x8C */
    public val fireDamageDamp: Int get() = bytes.s8(0x8C)

    /** `s8` at 0x8D */
    public val thunderDamageDamp: Int get() = bytes.s8(0x8D)

    /** `s8` at 0x8E */
    public val staminaDamp: Int get() = bytes.s8(0x8E)

    /** `s8` at 0x8F */
    public val knockbackDamp: Int get() = bytes.s8(0x8F)

    /** `s8` at 0x90 */
    public val shootAngleXZ: Int get() = bytes.s8(0x90)

    /** `u8` at 0x91 */
    public val lockShootLimitAng: Int get() = bytes.u8(0x91)

    /** `u8` at 0x92 */
    public val isPenetrate: Int get() = bytes.u8(0x92)

    /** `u8` at 0x93 */
    public val prevVelocityDirRate: Int get() = bytes.u8(0x93)

    /** `u8` at 0x94, `ATKPARAM_ATKATTR_TYPE` */
    public val atkAttribute: Int get() = bytes.u8(0x94)

    /** `u8` at 0x95, `ATKPARAM_SPATTR_TYPE` */
    public val spAttribute: Int get() = bytes.u8(0x95)

    /** `u8` at 0x96, `ATK_TYPE` */
    public val material_AttackType: Int get() = bytes.u8(0x96)

    /** `u8` at 0x97, `WEP_MATERIAL_ATK` */
    public val material_AttackMaterial: Int get() = bytes.u8(0x97)

    /** `u8` at 0x98, `ATK_SIZE` */
    public val material_Size: Int get() = bytes.u8(0x98)

    /** `u8` at 0x99, `BULLET_LAUNCH_CONDITION_TYPE` */
    public val launchConditionType: Int get() = bytes.u8(0x99)

    /** bits 0-2 of the `u8` at 0x9A, `BULLET_FOLLOW_TYPE` */
    public val followType: Int get() = bytes.bits(0x9A, 1, 0, 3)

    /** bits 3-5 of the `u8` at 0x9A, `BULLET_EMITTE_POS_TYPE` */
    public val emittePosType: Int get() = bytes.bits(0x9A, 1, 3, 3)

    /** bit 6 of the `u8` at 0x9A, `ON_OFF` */
    public val isAttackSFX: Boolean get() = bytes.flag(0x9A, 6)

    /** bit 7 of the `u8` at 0x9A, `ON_OFF` */
    public val isEndlessHit: Boolean get() = bytes.flag(0x9A, 7)

    /** bit 0 of the `u8` at 0x9B, `ON_OFF` */
    public val isPenetrateMap: Boolean get() = bytes.flag(0x9B, 0)

    /** bit 1 of the `u8` at 0x9B, `ON_OFF` */
    public val isHitBothTeam: Boolean get() = bytes.flag(0x9B, 1)

    /** bit 2 of the `u8` at 0x9B, `ON_OFF` */
    public val isUseSharedHitList: Boolean get() = bytes.flag(0x9B, 2)

    /** bit 3 of the `u8` at 0x9B, `ON_OFF` */
    public val isUseMultiDmyPolyIfPlace: Boolean get() = bytes.flag(0x9B, 3)

    /** bits 4-5 of the `u8` at 0x9B, `BULLET_ATTACH_EFFECT_TYPE` */
    public val attachEffectType: Int get() = bytes.bits(0x9B, 1, 4, 2)

    /** bit 6 of the `u8` at 0x9B */
    public val isHitForceMagic: Boolean get() = bytes.flag(0x9B, 6)

    /** bit 7 of the `u8` at 0x9B */
    public val isIgnoreSfxIfHitWater: Boolean get() = bytes.flag(0x9B, 7)

    /** bit 0 of the `u8` at 0x9C */
    public val isIgnoreMoveStateIfHitWater: Boolean get() = bytes.flag(0x9C, 0)

    /** bit 1 of the `u8` at 0x9C */
    public val isHitDarkForceMagic: Boolean get() = bytes.flag(0x9C, 1)

    public companion object : ParamRowType<BulletParamRow>("BULLET_PARAM_ST", 0xA0, 2, ::BulletParamRow)
}
