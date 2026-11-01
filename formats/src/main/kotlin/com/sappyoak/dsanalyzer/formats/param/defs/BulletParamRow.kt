// GENERATED FILE - do not edit. Regenerate with:
//
//     ./gradlew :tools:generateParamDefs -Pptde=<DATA directory> -Pdsr=<DATA directory>
//
// Source: paramdef.paramdefbnd, BulletParam.paramdef, read from the installation

@file:Suppress("SpellCheckingInspection")

package com.sappyoak.dsanalyzer.formats.param.defs

import com.sappyoak.dsanalyzer.formats.param.ParamField
import com.sappyoak.dsanalyzer.formats.param.ParamRow
import com.sappyoak.dsanalyzer.formats.param.ParamRowData
import com.sappyoak.dsanalyzer.formats.param.ParamRowType
import com.sappyoak.dsanalyzer.formats.param.paramdef.ParamFieldType

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

    public companion object : ParamRowType<BulletParamRow>("BULLET_PARAM_ST", 0xA0, 2, FIELDS, ::BulletParamRow)
}

private val FIELDS: List<ParamField<BulletParamRow>> = listOf<ParamField<BulletParamRow>>(
    ParamField("atkId_Bullet", ParamFieldType.S32, 0x0, null, null, null, "攻撃ID") { it.atkId_Bullet },
    ParamField("sfxId_Bullet", ParamFieldType.S32, 0x4, null, null, null, "SFXID【弾】") { it.sfxId_Bullet },
    ParamField("sfxId_Hit", ParamFieldType.S32, 0x8, null, null, null, "SFXID【着弾】") { it.sfxId_Hit },
    ParamField("sfxId_Flick", ParamFieldType.S32, 0xC, null, null, null, "SFXID【はじき時】") { it.sfxId_Flick },
    ParamField("life", ParamFieldType.F32, 0x10, null, null, null, "寿命[s]") { it.life },
    ParamField("dist", ParamFieldType.F32, 0x14, null, null, null, "射程距離[m]") { it.dist },
    ParamField("shootInterval", ParamFieldType.F32, 0x18, null, null, null, "発射間隔[s]") { it.shootInterval },
    ParamField("gravityInRange", ParamFieldType.F32, 0x1C, null, null, null, "射程距離内重力[m/s^2]") { it.gravityInRange },
    ParamField("gravityOutRange", ParamFieldType.F32, 0x20, null, null, null, "射程距離外重力[m/s^2]") { it.gravityOutRange },
    ParamField("hormingStopRange", ParamFieldType.F32, 0x24, null, null, null, "誘導停止距離[m]") { it.hormingStopRange },
    ParamField("initVellocity", ParamFieldType.F32, 0x28, null, null, null, "初速[m/s]") { it.initVellocity },
    ParamField("accelInRange", ParamFieldType.F32, 0x2C, null, null, null, "射程距離内加速度[m/s^2]") { it.accelInRange },
    ParamField("accelOutRange", ParamFieldType.F32, 0x30, null, null, null, "射程距離外加速度[m/s^2]") { it.accelOutRange },
    ParamField("maxVellocity", ParamFieldType.F32, 0x34, null, null, null, "最高速度[m/s]") { it.maxVellocity },
    ParamField("minVellocity", ParamFieldType.F32, 0x38, null, null, null, "最低速度[m/s]") { it.minVellocity },
    ParamField("accelTime", ParamFieldType.F32, 0x3C, null, null, null, "加速開始時間[s]") { it.accelTime },
    ParamField("homingBeginDist", ParamFieldType.F32, 0x40, null, null, null, "誘導開始距離[m]") { it.homingBeginDist },
    ParamField("hitRadius", ParamFieldType.F32, 0x44, null, null, null, "初期弾半径[m]") { it.hitRadius },
    ParamField("hitRadiusMax", ParamFieldType.F32, 0x48, null, null, null, "最大弾半径[m]") { it.hitRadiusMax },
    ParamField("spreadTime", ParamFieldType.F32, 0x4C, null, null, null, "範囲拡散時間[s]") { it.spreadTime },
    ParamField("expDelay", ParamFieldType.F32, 0x50, null, null, null, "発動遅延[s]") { it.expDelay },
    ParamField("hormingOffsetRange", ParamFieldType.F32, 0x54, null, null, null, "誘導ずらし量[m]") { it.hormingOffsetRange },
    ParamField("dmgHitRecordLifeTime", ParamFieldType.F32, 0x58, null, null, null, "ダメージヒット履歴の生存時間[s]") { it.dmgHitRecordLifeTime },
    ParamField("externalForce", ParamFieldType.F32, 0x5C, null, null, null, "外力[m/s^2]") { it.externalForce },
    ParamField("spEffectIDForShooter", ParamFieldType.S32, 0x60, null, null, null, "射撃した人にかける特殊効果") { it.spEffectIDForShooter },
    ParamField("autoSearchNPCThinkID", ParamFieldType.S32, 0x64, null, null, null, "ファンネルNPC思考ID") { it.autoSearchNPCThinkID },
    ParamField("hitBulletID", ParamFieldType.S32, 0x68, null, null, null, "発生弾丸ID") { it.hitBulletID },
    ParamField("spEffectId0", ParamFieldType.S32, 0x6C, null, null, null, "特殊効果ID0") { it.spEffectId0 },
    ParamField("spEffectId1", ParamFieldType.S32, 0x70, null, null, null, "特殊効果ID1") { it.spEffectId1 },
    ParamField("spEffectId2", ParamFieldType.S32, 0x74, null, null, null, "特殊効果ID2") { it.spEffectId2 },
    ParamField("spEffectId3", ParamFieldType.S32, 0x78, null, null, null, "特殊効果ID3") { it.spEffectId3 },
    ParamField("spEffectId4", ParamFieldType.S32, 0x7C, null, null, null, "特殊効果ID4") { it.spEffectId4 },
    ParamField("numShoot", ParamFieldType.U16, 0x80, null, null, null, "発射数") { it.numShoot },
    ParamField("homingAngle", ParamFieldType.S16, 0x82, null, null, null, "誘導性能[deg/s]") { it.homingAngle },
    ParamField("shootAngle", ParamFieldType.S16, 0x84, null, null, null, "発射角度[deg]") { it.shootAngle },
    ParamField("shootAngleInterval", ParamFieldType.S16, 0x86, null, null, null, "発射角度間隔[deg]") { it.shootAngleInterval },
    ParamField("shootAngleXInterval", ParamFieldType.S16, 0x88, null, null, null, "発射仰角間隔[deg]") { it.shootAngleXInterval },
    ParamField("damageDamp", ParamFieldType.S8, 0x8A, null, null, null, "物理攻撃力減衰率[%/s]") { it.damageDamp },
    ParamField("spelDamageDamp", ParamFieldType.S8, 0x8B, null, null, null, "魔法攻撃力減衰率[%/s]") { it.spelDamageDamp },
    ParamField("fireDamageDamp", ParamFieldType.S8, 0x8C, null, null, null, "炎攻撃力減衰率[%/s]") { it.fireDamageDamp },
    ParamField("thunderDamageDamp", ParamFieldType.S8, 0x8D, null, null, null, "電撃攻撃力減衰率[%/s]") { it.thunderDamageDamp },
    ParamField("staminaDamp", ParamFieldType.S8, 0x8E, null, null, null, "スタミナダメージ減衰率[%/s]") { it.staminaDamp },
    ParamField("knockbackDamp", ParamFieldType.S8, 0x8F, null, null, null, "ノックバック減衰率[%/s]") { it.knockbackDamp },
    ParamField("shootAngleXZ", ParamFieldType.S8, 0x90, null, null, null, "発射仰角[deg]") { it.shootAngleXZ },
    ParamField("lockShootLimitAng", ParamFieldType.U8, 0x91, null, null, null, "ロック方向制限角度") { it.lockShootLimitAng },
    ParamField("isPenetrate", ParamFieldType.U8, 0x92, null, null, null, "キャラ・OBJを貫通？") { it.isPenetrate },
    ParamField("prevVelocityDirRate", ParamFieldType.U8, 0x93, null, null, null, "前回の移動方向加算率[%]") { it.prevVelocityDirRate },
    ParamField("atkAttribute", ParamFieldType.U8, 0x94, null, null, "ATKPARAM_ATKATTR_TYPE", "物理属性") { it.atkAttribute },
    ParamField("spAttribute", ParamFieldType.U8, 0x95, null, null, "ATKPARAM_SPATTR_TYPE", "特殊属性") { it.spAttribute },
    ParamField("material_AttackType", ParamFieldType.U8, 0x96, null, null, "ATK_TYPE", "SFX材質 攻撃タイプ") { it.material_AttackType },
    ParamField("material_AttackMaterial", ParamFieldType.U8, 0x97, null, null, "WEP_MATERIAL_ATK", "SFX材質 攻撃材質") { it.material_AttackMaterial },
    ParamField("material_Size", ParamFieldType.U8, 0x98, null, null, "ATK_SIZE", "SFX材質 サイズ") { it.material_Size },
    ParamField("launchConditionType", ParamFieldType.U8, 0x99, null, null, "BULLET_LAUNCH_CONDITION_TYPE", "発生条件") { it.launchConditionType },
    ParamField("followType", ParamFieldType.U8, 0x9A, 0, 3, "BULLET_FOLLOW_TYPE", "追従タイプ") { it.followType },
    ParamField("emittePosType", ParamFieldType.U8, 0x9A, 3, 3, "BULLET_EMITTE_POS_TYPE", "発生源タイプ") { it.emittePosType },
    ParamField("isAttackSFX", ParamFieldType.U8, 0x9A, 6, 1, "ON_OFF", "刺さったままになるか") { it.isAttackSFX },
    ParamField("isEndlessHit", ParamFieldType.U8, 0x9A, 7, 1, "ON_OFF", "あたり続けるか？") { it.isEndlessHit },
    ParamField("isPenetrateMap", ParamFieldType.U8, 0x9B, 0, 1, "ON_OFF", "マップを貫通？") { it.isPenetrateMap },
    ParamField("isHitBothTeam", ParamFieldType.U8, 0x9B, 1, 1, "ON_OFF", "敵味方共にあたる？") { it.isHitBothTeam },
    ParamField("isUseSharedHitList", ParamFieldType.U8, 0x9B, 2, 1, "ON_OFF", "ヒットリストを共有するか？") { it.isUseSharedHitList },
    ParamField("isUseMultiDmyPolyIfPlace", ParamFieldType.U8, 0x9B, 3, 1, "ON_OFF", "複数のダミポリを使うか？") { it.isUseMultiDmyPolyIfPlace },
    ParamField("attachEffectType", ParamFieldType.U8, 0x9B, 4, 2, "BULLET_ATTACH_EFFECT_TYPE", "アタッチ効果タイプ") { it.attachEffectType },
    ParamField("isHitForceMagic", ParamFieldType.U8, 0x9B, 6, 1, null, "フォース魔法に当たるか") { it.isHitForceMagic },
    ParamField("isIgnoreSfxIfHitWater", ParamFieldType.U8, 0x9B, 7, 1, null, "水面衝突時のエフェクト無視するか") { it.isIgnoreSfxIfHitWater },
    ParamField("isIgnoreMoveStateIfHitWater", ParamFieldType.U8, 0x9C, 0, 1, null, "水面衝突時の状態遷移を無視するか") { it.isIgnoreMoveStateIfHitWater },
    ParamField("isHitDarkForceMagic", ParamFieldType.U8, 0x9C, 1, 1, null, "闇フォース魔法に当たるか") { it.isHitDarkForceMagic }
)
