// GENERATED FILE - do not edit. Regenerate with:
//
//     ./gradlew :tools:generateParamDefs -Pptde=<DATA directory> -Pdsr=<DATA directory>
//
// Source: paramdef.paramdefbnd, SpEffect.paramdef, read from the installation

@file:Suppress("SpellCheckingInspection")

package com.sappyoak.dsanalyzer.formats.param.defs

import com.sappyoak.dsanalyzer.formats.param.ParamRow
import com.sappyoak.dsanalyzer.formats.param.ParamRowData
import com.sappyoak.dsanalyzer.formats.param.ParamRowType

/** `SP_EFFECT_PARAM_ST`: PrepareToDie 0x170, Remastered 0x170 a row, data version 1 */
public class SpEffectRow(row: ParamRowData) : ParamRow(row) {
    /** `s32` at 0x0 */
    public val iconId: Int get() = bytes.s32(0x0)

    /** `f32` at 0x4 */
    public val conditionHp: Float get() = bytes.f32(0x4)

    /** `f32` at 0x8 */
    public val effectEndurance: Float get() = bytes.f32(0x8)

    /** `f32` at 0xC */
    public val motionInterval: Float get() = bytes.f32(0xC)

    /** `f32` at 0x10 */
    public val maxHpRate: Float get() = bytes.f32(0x10)

    /** `f32` at 0x14 */
    public val maxMpRate: Float get() = bytes.f32(0x14)

    /** `f32` at 0x18 */
    public val maxStaminaRate: Float get() = bytes.f32(0x18)

    /** `f32` at 0x1C */
    public val slashDamageCutRate: Float get() = bytes.f32(0x1C)

    /** `f32` at 0x20 */
    public val blowDamageCutRate: Float get() = bytes.f32(0x20)

    /** `f32` at 0x24 */
    public val thrustDamageCutRate: Float get() = bytes.f32(0x24)

    /** `f32` at 0x28 */
    public val neutralDamageCutRate: Float get() = bytes.f32(0x28)

    /** `f32` at 0x2C */
    public val magicDamageCutRate: Float get() = bytes.f32(0x2C)

    /** `f32` at 0x30 */
    public val fireDamageCutRate: Float get() = bytes.f32(0x30)

    /** `f32` at 0x34 */
    public val thunderDamageCutRate: Float get() = bytes.f32(0x34)

    /** `f32` at 0x38 */
    public val physicsAttackRate: Float get() = bytes.f32(0x38)

    /** `f32` at 0x3C */
    public val magicAttackRate: Float get() = bytes.f32(0x3C)

    /** `f32` at 0x40 */
    public val fireAttackRate: Float get() = bytes.f32(0x40)

    /** `f32` at 0x44 */
    public val thunderAttackRate: Float get() = bytes.f32(0x44)

    /** `f32` at 0x48 */
    public val physicsAttackPowerRate: Float get() = bytes.f32(0x48)

    /** `f32` at 0x4C */
    public val magicAttackPowerRate: Float get() = bytes.f32(0x4C)

    /** `f32` at 0x50 */
    public val fireAttackPowerRate: Float get() = bytes.f32(0x50)

    /** `f32` at 0x54 */
    public val thunderAttackPowerRate: Float get() = bytes.f32(0x54)

    /** `s32` at 0x58 */
    public val physicsAttackPower: Int get() = bytes.s32(0x58)

    /** `s32` at 0x5C */
    public val magicAttackPower: Int get() = bytes.s32(0x5C)

    /** `s32` at 0x60 */
    public val fireAttackPower: Int get() = bytes.s32(0x60)

    /** `s32` at 0x64 */
    public val thunderAttackPower: Int get() = bytes.s32(0x64)

    /** `f32` at 0x68 */
    public val physicsDiffenceRate: Float get() = bytes.f32(0x68)

    /** `f32` at 0x6C */
    public val magicDiffenceRate: Float get() = bytes.f32(0x6C)

    /** `f32` at 0x70 */
    public val fireDiffenceRate: Float get() = bytes.f32(0x70)

    /** `f32` at 0x74 */
    public val thunderDiffenceRate: Float get() = bytes.f32(0x74)

    /** `s32` at 0x78 */
    public val physicsDiffence: Int get() = bytes.s32(0x78)

    /** `s32` at 0x7C */
    public val magicDiffence: Int get() = bytes.s32(0x7C)

    /** `s32` at 0x80 */
    public val fireDiffence: Int get() = bytes.s32(0x80)

    /** `s32` at 0x84 */
    public val thunderDiffence: Int get() = bytes.s32(0x84)

    /** `f32` at 0x88 */
    public val noGuardDamageRate: Float get() = bytes.f32(0x88)

    /** `f32` at 0x8C */
    public val vitalSpotChangeRate: Float get() = bytes.f32(0x8C)

    /** `f32` at 0x90 */
    public val normalSpotChangeRate: Float get() = bytes.f32(0x90)

    /** `f32` at 0x94 */
    public val maxHpChangeRate: Float get() = bytes.f32(0x94)

    /** `s32` at 0x98 */
    public val behaviorId: Int get() = bytes.s32(0x98)

    /** `f32` at 0x9C */
    public val changeHpRate: Float get() = bytes.f32(0x9C)

    /** `s32` at 0xA0 */
    public val changeHpPoint: Int get() = bytes.s32(0xA0)

    /** `f32` at 0xA4 */
    public val changeMpRate: Float get() = bytes.f32(0xA4)

    /** `s32` at 0xA8 */
    public val changeMpPoint: Int get() = bytes.s32(0xA8)

    /** `s32` at 0xAC */
    public val mpRecoverChangeSpeed: Int get() = bytes.s32(0xAC)

    /** `f32` at 0xB0 */
    public val changeStaminaRate: Float get() = bytes.f32(0xB0)

    /** `s32` at 0xB4 */
    public val changeStaminaPoint: Int get() = bytes.s32(0xB4)

    /** `s32` at 0xB8 */
    public val staminaRecoverChangeSpeed: Int get() = bytes.s32(0xB8)

    /** `f32` at 0xBC */
    public val magicEffectTimeChange: Float get() = bytes.f32(0xBC)

    /** `s32` at 0xC0 */
    public val insideDurability: Int get() = bytes.s32(0xC0)

    /** `s32` at 0xC4 */
    public val maxDurability: Int get() = bytes.s32(0xC4)

    /** `f32` at 0xC8 */
    public val staminaAttackRate: Float get() = bytes.f32(0xC8)

    /** `s32` at 0xCC */
    public val poizonAttackPower: Int get() = bytes.s32(0xCC)

    /** `s32` at 0xD0 */
    public val registIllness: Int get() = bytes.s32(0xD0)

    /** `s32` at 0xD4 */
    public val registBlood: Int get() = bytes.s32(0xD4)

    /** `s32` at 0xD8 */
    public val registCurse: Int get() = bytes.s32(0xD8)

    /** `f32` at 0xDC */
    public val fallDamageRate: Float get() = bytes.f32(0xDC)

    /** `f32` at 0xE0 */
    public val soulRate: Float get() = bytes.f32(0xE0)

    /** `f32` at 0xE4 */
    public val equipWeightChangeRate: Float get() = bytes.f32(0xE4)

    /** `f32` at 0xE8 */
    public val allItemWeightChangeRate: Float get() = bytes.f32(0xE8)

    /** `s32` at 0xEC */
    public val soul: Int get() = bytes.s32(0xEC)

    /** `s32` at 0xF0 */
    public val animIdOffset: Int get() = bytes.s32(0xF0)

    /** `f32` at 0xF4 */
    public val haveSoulRate: Float get() = bytes.f32(0xF4)

    /** `f32` at 0xF8 */
    public val targetPriority: Float get() = bytes.f32(0xF8)

    /** `s32` at 0xFC */
    public val sightSearchEnemyCut: Int get() = bytes.s32(0xFC)

    /** `s32` at 0x100 */
    public val hearingSearchEnemyCut: Int get() = bytes.s32(0x100)

    /** `f32` at 0x104 */
    public val grabityRate: Float get() = bytes.f32(0x104)

    /** `f32` at 0x108 */
    public val registPoizonChangeRate: Float get() = bytes.f32(0x108)

    /** `f32` at 0x10C */
    public val registIllnessChangeRate: Float get() = bytes.f32(0x10C)

    /** `f32` at 0x110 */
    public val registBloodChangeRate: Float get() = bytes.f32(0x110)

    /** `f32` at 0x114 */
    public val registCurseChangeRate: Float get() = bytes.f32(0x114)

    /** `f32` at 0x118 */
    public val soulStealRate: Float get() = bytes.f32(0x118)

    /** `f32` at 0x11C */
    public val lifeReductionRate: Float get() = bytes.f32(0x11C)

    /** `f32` at 0x120 */
    public val hpRecoverRate: Float get() = bytes.f32(0x120)

    /** `s32` at 0x124 */
    public val replaceSpEffectId: Int get() = bytes.s32(0x124)

    /** `s32` at 0x128 */
    public val cycleOccurrenceSpEffectId: Int get() = bytes.s32(0x128)

    /** `s32` at 0x12C */
    public val atkOccurrenceSpEffectId: Int get() = bytes.s32(0x12C)

    /** `f32` at 0x130 */
    public val guardDefFlickPowerRate: Float get() = bytes.f32(0x130)

    /** `f32` at 0x134 */
    public val guardStaminaCutRate: Float get() = bytes.f32(0x134)

    /** `s16` at 0x138 */
    public val rayCastPassedTime: Int get() = bytes.s16(0x138)

    /** `s16` at 0x13A */
    public val changeSuperArmorPoint: Int get() = bytes.s16(0x13A)

    /** `s16` at 0x13C */
    public val bowDistRate: Int get() = bytes.s16(0x13C)

    /** `u16` at 0x13E, `SP_EFFECT_SPCATEGORY` */
    public val spCategory: Int get() = bytes.u16(0x13E)

    /** `u8` at 0x140 */
    public val categoryPriority: Int get() = bytes.u8(0x140)

    /** `s8` at 0x141, `SP_EFFECT_SAVE_CATEGORY` */
    public val saveCategory: Int get() = bytes.s8(0x141)

    /** `u8` at 0x142 */
    public val changeMagicSlot: Int get() = bytes.u8(0x142)

    /** `u8` at 0x143 */
    public val changeMiracleSlot: Int get() = bytes.u8(0x143)

    /** `s8` at 0x144 */
    public val heroPointDamage: Int get() = bytes.s8(0x144)

    /** `u8` at 0x145 */
    public val defFlickPower: Int get() = bytes.u8(0x145)

    /** `u8` at 0x146 */
    public val flickDamageCutRate: Int get() = bytes.u8(0x146)

    /** `u8` at 0x147 */
    public val bloodDamageRate: Int get() = bytes.u8(0x147)

    /** `s8` at 0x148, `ATKPARAM_REP_DMGTYPE` */
    public val dmgLv_None: Int get() = bytes.s8(0x148)

    /** `s8` at 0x149, `ATKPARAM_REP_DMGTYPE` */
    public val dmgLv_S: Int get() = bytes.s8(0x149)

    /** `s8` at 0x14A, `ATKPARAM_REP_DMGTYPE` */
    public val dmgLv_M: Int get() = bytes.s8(0x14A)

    /** `s8` at 0x14B, `ATKPARAM_REP_DMGTYPE` */
    public val dmgLv_L: Int get() = bytes.s8(0x14B)

    /** `s8` at 0x14C, `ATKPARAM_REP_DMGTYPE` */
    public val dmgLv_BlowM: Int get() = bytes.s8(0x14C)

    /** `s8` at 0x14D, `ATKPARAM_REP_DMGTYPE` */
    public val dmgLv_Push: Int get() = bytes.s8(0x14D)

    /** `s8` at 0x14E, `ATKPARAM_REP_DMGTYPE` */
    public val dmgLv_Strike: Int get() = bytes.s8(0x14E)

    /** `s8` at 0x14F, `ATKPARAM_REP_DMGTYPE` */
    public val dmgLv_BlowS: Int get() = bytes.s8(0x14F)

    /** `s8` at 0x150, `ATKPARAM_REP_DMGTYPE` */
    public val dmgLv_Min: Int get() = bytes.s8(0x150)

    /** `s8` at 0x151, `ATKPARAM_REP_DMGTYPE` */
    public val dmgLv_Uppercut: Int get() = bytes.s8(0x151)

    /** `s8` at 0x152, `ATKPARAM_REP_DMGTYPE` */
    public val dmgLv_BlowLL: Int get() = bytes.s8(0x152)

    /** `s8` at 0x153, `ATKPARAM_REP_DMGTYPE` */
    public val dmgLv_Breath: Int get() = bytes.s8(0x153)

    /** `u8` at 0x154, `ATKPARAM_ATKATTR_TYPE` */
    public val atkAttribute: Int get() = bytes.u8(0x154)

    /** `u8` at 0x155, `ATKPARAM_SPATTR_TYPE` */
    public val spAttribute: Int get() = bytes.u8(0x155)

    /** `u8` at 0x156, `SP_EFFECT_TYPE` */
    public val stateInfo: Int get() = bytes.u8(0x156)

    /** `u8` at 0x157, `SP_EFE_WEP_CHANGE_PARAM` */
    public val wepParamChange: Int get() = bytes.u8(0x157)

    /** `u8` at 0x158, `SP_EFFECT_MOVE_TYPE` */
    public val moveType: Int get() = bytes.u8(0x158)

    /** `u8` at 0x159, `SP_EFFECT_TYPE` */
    public val lifeReductionType: Int get() = bytes.u8(0x159)

    /** `u8` at 0x15A, `SP_EFFECT_THROW_CONDITION_TYPE` */
    public val throwCondition: Int get() = bytes.u8(0x15A)

    /** `s8` at 0x15B */
    public val addBehaviorJudgeId_condition: Int get() = bytes.s8(0x15B)

    /** `u8` at 0x15C */
    public val addBehaviorJudgeId_add: Int get() = bytes.u8(0x15C)

    /** bit 0 of the `u8` at 0x15D, `SP_EFFECT_BOOL` */
    public val effectTargetSelf: Boolean get() = bytes.flag(0x15D, 0)

    /** bit 1 of the `u8` at 0x15D, `SP_EFFECT_BOOL` */
    public val effectTargetFriend: Boolean get() = bytes.flag(0x15D, 1)

    /** bit 2 of the `u8` at 0x15D, `SP_EFFECT_BOOL` */
    public val effectTargetEnemy: Boolean get() = bytes.flag(0x15D, 2)

    /** bit 3 of the `u8` at 0x15D, `SP_EFFECT_BOOL` */
    public val effectTargetPlayer: Boolean get() = bytes.flag(0x15D, 3)

    /** bit 4 of the `u8` at 0x15D, `SP_EFFECT_BOOL` */
    public val effectTargetAI: Boolean get() = bytes.flag(0x15D, 4)

    /** bit 5 of the `u8` at 0x15D, `SP_EFFECT_BOOL` */
    public val effectTargetLive: Boolean get() = bytes.flag(0x15D, 5)

    /** bit 6 of the `u8` at 0x15D, `SP_EFFECT_BOOL` */
    public val effectTargetGhost: Boolean get() = bytes.flag(0x15D, 6)

    /** bit 7 of the `u8` at 0x15D, `SP_EFFECT_BOOL` */
    public val effectTargetWhiteGhost: Boolean get() = bytes.flag(0x15D, 7)

    /** bit 0 of the `u8` at 0x15E, `SP_EFFECT_BOOL` */
    public val effectTargetBlackGhost: Boolean get() = bytes.flag(0x15E, 0)

    /** bit 1 of the `u8` at 0x15E, `SP_EFFECT_BOOL` */
    public val effectTargetAttacker: Boolean get() = bytes.flag(0x15E, 1)

    /** bit 2 of the `u8` at 0x15E, `SP_EFFECT_BOOL` */
    public val dispIconNonactive: Boolean get() = bytes.flag(0x15E, 2)

    /** bit 3 of the `u8` at 0x15E, `SP_EFFECT_BOOL` */
    public val useSpEffectEffect: Boolean get() = bytes.flag(0x15E, 3)

    /** bit 4 of the `u8` at 0x15E, `SP_EFFECT_BOOL` */
    public val bAdjustMagicAblity: Boolean get() = bytes.flag(0x15E, 4)

    /** bit 5 of the `u8` at 0x15E, `SP_EFFECT_BOOL` */
    public val bAdjustFaithAblity: Boolean get() = bytes.flag(0x15E, 5)

    /** bit 6 of the `u8` at 0x15E, `SP_EFFECT_BOOL` */
    public val bGameClearBonus: Boolean get() = bytes.flag(0x15E, 6)

    /** bit 7 of the `u8` at 0x15E, `SP_EFFECT_BOOL` */
    public val magParamChange: Boolean get() = bytes.flag(0x15E, 7)

    /** bit 0 of the `u8` at 0x15F, `SP_EFFECT_BOOL` */
    public val miracleParamChange: Boolean get() = bytes.flag(0x15F, 0)

    /** bit 1 of the `u8` at 0x15F, `SP_EFFECT_BOOL` */
    public val clearSoul: Boolean get() = bytes.flag(0x15F, 1)

    /** bit 2 of the `u8` at 0x15F, `SP_EFFECT_BOOL` */
    public val requestSOS: Boolean get() = bytes.flag(0x15F, 2)

    /** bit 3 of the `u8` at 0x15F, `SP_EFFECT_BOOL` */
    public val requestBlackSOS: Boolean get() = bytes.flag(0x15F, 3)

    /** bit 4 of the `u8` at 0x15F, `SP_EFFECT_BOOL` */
    public val requestForceJoinBlackSOS: Boolean get() = bytes.flag(0x15F, 4)

    /** bit 5 of the `u8` at 0x15F, `SP_EFFECT_BOOL` */
    public val requestKickSession: Boolean get() = bytes.flag(0x15F, 5)

    /** bit 6 of the `u8` at 0x15F, `SP_EFFECT_BOOL` */
    public val requestLeaveSession: Boolean get() = bytes.flag(0x15F, 6)

    /** bit 7 of the `u8` at 0x15F, `SP_EFFECT_BOOL` */
    public val requestNpcInveda: Boolean get() = bytes.flag(0x15F, 7)

    /** bit 0 of the `u8` at 0x160, `SP_EFFECT_BOOL` */
    public val noDead: Boolean get() = bytes.flag(0x160, 0)

    /** bit 1 of the `u8` at 0x160, `SP_EFFECT_BOOL` */
    public val bCurrHPIndependeMaxHP: Boolean get() = bytes.flag(0x160, 1)

    /** bit 2 of the `u8` at 0x160, `SP_EFFECT_BOOL` */
    public val corrosionIgnore: Boolean get() = bytes.flag(0x160, 2)

    /** bit 3 of the `u8` at 0x160, `SP_EFFECT_BOOL` */
    public val sightSearchCutIgnore: Boolean get() = bytes.flag(0x160, 3)

    /** bit 4 of the `u8` at 0x160, `SP_EFFECT_BOOL` */
    public val hearingSearchCutIgnore: Boolean get() = bytes.flag(0x160, 4)

    /** bit 5 of the `u8` at 0x160, `SP_EFFECT_BOOL` */
    public val antiMagicIgnore: Boolean get() = bytes.flag(0x160, 5)

    /** bit 6 of the `u8` at 0x160, `SP_EFFECT_BOOL` */
    public val fakeTargetIgnore: Boolean get() = bytes.flag(0x160, 6)

    /** bit 7 of the `u8` at 0x160, `SP_EFFECT_BOOL` */
    public val fakeTargetIgnoreUndead: Boolean get() = bytes.flag(0x160, 7)

    /** bit 0 of the `u8` at 0x161, `SP_EFFECT_BOOL` */
    public val fakeTargetIgnoreAnimal: Boolean get() = bytes.flag(0x161, 0)

    /** bit 1 of the `u8` at 0x161, `SP_EFFECT_BOOL` */
    public val grabityIgnore: Boolean get() = bytes.flag(0x161, 1)

    /** bit 2 of the `u8` at 0x161, `SP_EFFECT_BOOL` */
    public val disablePoison: Boolean get() = bytes.flag(0x161, 2)

    /** bit 3 of the `u8` at 0x161, `SP_EFFECT_BOOL` */
    public val disableDisease: Boolean get() = bytes.flag(0x161, 3)

    /** bit 4 of the `u8` at 0x161, `SP_EFFECT_BOOL` */
    public val disableBlood: Boolean get() = bytes.flag(0x161, 4)

    /** bit 5 of the `u8` at 0x161, `SP_EFFECT_BOOL` */
    public val disableCurse: Boolean get() = bytes.flag(0x161, 5)

    /** bit 6 of the `u8` at 0x161, `SP_EFFECT_BOOL` */
    public val enableCharm: Boolean get() = bytes.flag(0x161, 6)

    /** bit 7 of the `u8` at 0x161, `SP_EFFECT_BOOL` */
    public val enableLifeTime: Boolean get() = bytes.flag(0x161, 7)

    /** bit 0 of the `u8` at 0x162, `SP_EFFECT_BOOL` */
    public val hasTarget: Boolean get() = bytes.flag(0x162, 0)

    /** bit 1 of the `u8` at 0x162, `SP_EFFECT_BOOL` */
    public val isFireDamageCancel: Boolean get() = bytes.flag(0x162, 1)

    /** bit 2 of the `u8` at 0x162, `SP_EFFECT_BOOL` */
    public val isExtendSpEffectLife: Boolean get() = bytes.flag(0x162, 2)

    /** bit 3 of the `u8` at 0x162, `SP_EFFECT_BOOL` */
    public val requestLeaveColiseumSession: Boolean get() = bytes.flag(0x162, 3)

    /** bit 0 of the `u8` at 0x163, `SP_EFFECT_BOOL` */
    public val vowType0: Boolean get() = bytes.flag(0x163, 0)

    /** bit 1 of the `u8` at 0x163, `SP_EFFECT_BOOL` */
    public val vowType1: Boolean get() = bytes.flag(0x163, 1)

    /** bit 2 of the `u8` at 0x163, `SP_EFFECT_BOOL` */
    public val vowType2: Boolean get() = bytes.flag(0x163, 2)

    /** bit 3 of the `u8` at 0x163, `SP_EFFECT_BOOL` */
    public val vowType3: Boolean get() = bytes.flag(0x163, 3)

    /** bit 4 of the `u8` at 0x163, `SP_EFFECT_BOOL` */
    public val vowType4: Boolean get() = bytes.flag(0x163, 4)

    /** bit 5 of the `u8` at 0x163, `SP_EFFECT_BOOL` */
    public val vowType5: Boolean get() = bytes.flag(0x163, 5)

    /** bit 6 of the `u8` at 0x163, `SP_EFFECT_BOOL` */
    public val vowType6: Boolean get() = bytes.flag(0x163, 6)

    /** bit 7 of the `u8` at 0x163, `SP_EFFECT_BOOL` */
    public val vowType7: Boolean get() = bytes.flag(0x163, 7)

    /** bit 0 of the `u8` at 0x164, `SP_EFFECT_BOOL` */
    public val vowType8: Boolean get() = bytes.flag(0x164, 0)

    /** bit 1 of the `u8` at 0x164, `SP_EFFECT_BOOL` */
    public val vowType9: Boolean get() = bytes.flag(0x164, 1)

    /** bit 2 of the `u8` at 0x164, `SP_EFFECT_BOOL` */
    public val vowType10: Boolean get() = bytes.flag(0x164, 2)

    /** bit 3 of the `u8` at 0x164, `SP_EFFECT_BOOL` */
    public val vowType11: Boolean get() = bytes.flag(0x164, 3)

    /** bit 4 of the `u8` at 0x164, `SP_EFFECT_BOOL` */
    public val vowType12: Boolean get() = bytes.flag(0x164, 4)

    /** bit 5 of the `u8` at 0x164, `SP_EFFECT_BOOL` */
    public val vowType13: Boolean get() = bytes.flag(0x164, 5)

    /** bit 6 of the `u8` at 0x164, `SP_EFFECT_BOOL` */
    public val vowType14: Boolean get() = bytes.flag(0x164, 6)

    /** bit 7 of the `u8` at 0x164, `SP_EFFECT_BOOL` */
    public val vowType15: Boolean get() = bytes.flag(0x164, 7)

    public companion object : ParamRowType<SpEffectRow>("SP_EFFECT_PARAM_ST", 0x170, 1, ::SpEffectRow)
}
