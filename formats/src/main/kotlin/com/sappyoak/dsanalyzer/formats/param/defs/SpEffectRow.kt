// GENERATED FILE - do not edit. Regenerate with:
//
//     ./gradlew :tools:generateParamDefs -Pptde=<DATA directory> -Pdsr=<DATA directory>
//
// Source: paramdef.paramdefbnd, SpEffect.paramdef, read from the installation

@file:Suppress("SpellCheckingInspection")

package com.sappyoak.dsanalyzer.formats.param.defs

import com.sappyoak.dsanalyzer.formats.param.ParamField
import com.sappyoak.dsanalyzer.formats.param.ParamRow
import com.sappyoak.dsanalyzer.formats.param.ParamRowData
import com.sappyoak.dsanalyzer.formats.param.ParamRowType
import com.sappyoak.dsanalyzer.formats.param.paramdef.ParamFieldType

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

    public companion object : ParamRowType<SpEffectRow>("SP_EFFECT_PARAM_ST", 0x170, 1, FIELDS, ::SpEffectRow)
}

private val FIELDS: List<ParamField<SpEffectRow>> = listOf<ParamField<SpEffectRow>>(
    ParamField("iconId", ParamFieldType.S32, 0x0, null, null, null, "アイコンID") { it.iconId },
    ParamField("conditionHp", ParamFieldType.F32, 0x4, null, null, null, "発動条件　残りHP比率[%]") { it.conditionHp },
    ParamField("effectEndurance", ParamFieldType.F32, 0x8, null, null, null, "効果持続時間　時間[s]") { it.effectEndurance },
    ParamField("motionInterval", ParamFieldType.F32, 0xC, null, null, null, "発動間隔[s]") { it.motionInterval },
    ParamField("maxHpRate", ParamFieldType.F32, 0x10, null, null, null, "最大HP倍率[%]") { it.maxHpRate },
    ParamField("maxMpRate", ParamFieldType.F32, 0x14, null, null, null, "最大MP倍率[%]") { it.maxMpRate },
    ParamField("maxStaminaRate", ParamFieldType.F32, 0x18, null, null, null, "最大スタミナ倍率[%]") { it.maxStaminaRate },
    ParamField("slashDamageCutRate", ParamFieldType.F32, 0x1C, null, null, null, "防御側：斬撃ダメージ倍率") { it.slashDamageCutRate },
    ParamField("blowDamageCutRate", ParamFieldType.F32, 0x20, null, null, null, "防御側：打撃ダメージ倍率") { it.blowDamageCutRate },
    ParamField("thrustDamageCutRate", ParamFieldType.F32, 0x24, null, null, null, "防御側：刺突ダメージ倍率") { it.thrustDamageCutRate },
    ParamField("neutralDamageCutRate", ParamFieldType.F32, 0x28, null, null, null, "防御側：無属性ダメージ倍率") { it.neutralDamageCutRate },
    ParamField("magicDamageCutRate", ParamFieldType.F32, 0x2C, null, null, null, "防御側：魔法ダメージ倍率") { it.magicDamageCutRate },
    ParamField("fireDamageCutRate", ParamFieldType.F32, 0x30, null, null, null, "防御側：炎ダメージ倍率") { it.fireDamageCutRate },
    ParamField("thunderDamageCutRate", ParamFieldType.F32, 0x34, null, null, null, "防御側：電撃ダメージ倍率") { it.thunderDamageCutRate },
    ParamField("physicsAttackRate", ParamFieldType.F32, 0x38, null, null, null, "攻撃側：物理ダメージ倍率") { it.physicsAttackRate },
    ParamField("magicAttackRate", ParamFieldType.F32, 0x3C, null, null, null, "攻撃側：魔法ダメージ倍率") { it.magicAttackRate },
    ParamField("fireAttackRate", ParamFieldType.F32, 0x40, null, null, null, "攻撃側：炎ダメージ倍率") { it.fireAttackRate },
    ParamField("thunderAttackRate", ParamFieldType.F32, 0x44, null, null, null, "攻撃側：電撃ダメージ倍率") { it.thunderAttackRate },
    ParamField("physicsAttackPowerRate", ParamFieldType.F32, 0x48, null, null, null, "物理攻撃力倍率") { it.physicsAttackPowerRate },
    ParamField("magicAttackPowerRate", ParamFieldType.F32, 0x4C, null, null, null, "魔法攻撃力倍率") { it.magicAttackPowerRate },
    ParamField("fireAttackPowerRate", ParamFieldType.F32, 0x50, null, null, null, "炎攻撃力倍率") { it.fireAttackPowerRate },
    ParamField("thunderAttackPowerRate", ParamFieldType.F32, 0x54, null, null, null, "電撃攻撃力倍率") { it.thunderAttackPowerRate },
    ParamField("physicsAttackPower", ParamFieldType.S32, 0x58, null, null, null, "物理攻撃力[point]") { it.physicsAttackPower },
    ParamField("magicAttackPower", ParamFieldType.S32, 0x5C, null, null, null, "魔法攻撃力[point]") { it.magicAttackPower },
    ParamField("fireAttackPower", ParamFieldType.S32, 0x60, null, null, null, "炎攻撃力[point]") { it.fireAttackPower },
    ParamField("thunderAttackPower", ParamFieldType.S32, 0x64, null, null, null, "電撃攻撃力[point]") { it.thunderAttackPower },
    ParamField("physicsDiffenceRate", ParamFieldType.F32, 0x68, null, null, null, "物理防御力倍率") { it.physicsDiffenceRate },
    ParamField("magicDiffenceRate", ParamFieldType.F32, 0x6C, null, null, null, "魔法防御力倍率") { it.magicDiffenceRate },
    ParamField("fireDiffenceRate", ParamFieldType.F32, 0x70, null, null, null, "炎防御力倍率") { it.fireDiffenceRate },
    ParamField("thunderDiffenceRate", ParamFieldType.F32, 0x74, null, null, null, "電撃防御力倍率") { it.thunderDiffenceRate },
    ParamField("physicsDiffence", ParamFieldType.S32, 0x78, null, null, null, "物理防御力[point]") { it.physicsDiffence },
    ParamField("magicDiffence", ParamFieldType.S32, 0x7C, null, null, null, "魔法防御力[point]") { it.magicDiffence },
    ParamField("fireDiffence", ParamFieldType.S32, 0x80, null, null, null, "炎防御力[point]") { it.fireDiffence },
    ParamField("thunderDiffence", ParamFieldType.S32, 0x84, null, null, null, "電撃防御力[point]") { it.thunderDiffence },
    ParamField("noGuardDamageRate", ParamFieldType.F32, 0x88, null, null, null, "隙ダメージ倍率") { it.noGuardDamageRate },
    ParamField("vitalSpotChangeRate", ParamFieldType.F32, 0x8C, null, null, null, "スィートスポット倍率") { it.vitalSpotChangeRate },
    ParamField("normalSpotChangeRate", ParamFieldType.F32, 0x90, null, null, null, "ノーマルヒット倍率") { it.normalSpotChangeRate },
    ParamField("maxHpChangeRate", ParamFieldType.F32, 0x94, null, null, null, "最大HP変化倍率") { it.maxHpChangeRate },
    ParamField("behaviorId", ParamFieldType.S32, 0x98, null, null, null, "行動ID指定枠") { it.behaviorId },
    ParamField("changeHpRate", ParamFieldType.F32, 0x9C, null, null, null, "HPダメージ量[%]") { it.changeHpRate },
    ParamField("changeHpPoint", ParamFieldType.S32, 0xA0, null, null, null, "HPダメージ[point]") { it.changeHpPoint },
    ParamField("changeMpRate", ParamFieldType.F32, 0xA4, null, null, null, "MPダメージ量[%]") { it.changeMpRate },
    ParamField("changeMpPoint", ParamFieldType.S32, 0xA8, null, null, null, "MPダメージ[point]") { it.changeMpPoint },
    ParamField("mpRecoverChangeSpeed", ParamFieldType.S32, 0xAC, null, null, null, "MP回復速度変化[point]") { it.mpRecoverChangeSpeed },
    ParamField("changeStaminaRate", ParamFieldType.F32, 0xB0, null, null, null, "スタミナダメージ量[%]") { it.changeStaminaRate },
    ParamField("changeStaminaPoint", ParamFieldType.S32, 0xB4, null, null, null, "スタミナダメージ[point]") { it.changeStaminaPoint },
    ParamField("staminaRecoverChangeSpeed", ParamFieldType.S32, 0xB8, null, null, null, "スタミナ回復速度変化[point]") { it.staminaRecoverChangeSpeed },
    ParamField("magicEffectTimeChange", ParamFieldType.F32, 0xBC, null, null, null, "魔法効果時間変化") { it.magicEffectTimeChange },
    ParamField("insideDurability", ParamFieldType.S32, 0xC0, null, null, null, "耐久度変化：内部損耗度[point]") { it.insideDurability },
    ParamField("maxDurability", ParamFieldType.S32, 0xC4, null, null, null, "耐久度変化：最大損耗度変化[point]") { it.maxDurability },
    ParamField("staminaAttackRate", ParamFieldType.F32, 0xC8, null, null, null, "スタミナ攻撃力倍率") { it.staminaAttackRate },
    ParamField("poizonAttackPower", ParamFieldType.S32, 0xCC, null, null, null, "毒耐性攻撃力[point]") { it.poizonAttackPower },
    ParamField("registIllness", ParamFieldType.S32, 0xD0, null, null, null, "疫病耐性攻撃力[point]") { it.registIllness },
    ParamField("registBlood", ParamFieldType.S32, 0xD4, null, null, null, "出血耐性攻撃力[point]") { it.registBlood },
    ParamField("registCurse", ParamFieldType.S32, 0xD8, null, null, null, "呪耐性攻撃力[point]") { it.registCurse },
    ParamField("fallDamageRate", ParamFieldType.F32, 0xDC, null, null, null, "落下ダメージ倍率") { it.fallDamageRate },
    ParamField("soulRate", ParamFieldType.F32, 0xE0, null, null, null, "取得ソウル倍率") { it.soulRate },
    ParamField("equipWeightChangeRate", ParamFieldType.F32, 0xE4, null, null, null, "装備重量変化倍率") { it.equipWeightChangeRate },
    ParamField("allItemWeightChangeRate", ParamFieldType.F32, 0xE8, null, null, null, "所持重量変化倍率") { it.allItemWeightChangeRate },
    ParamField("soul", ParamFieldType.S32, 0xEC, null, null, null, "ソウル加算") { it.soul },
    ParamField("animIdOffset", ParamFieldType.S32, 0xF0, null, null, null, "アニメIDオフセット") { it.animIdOffset },
    ParamField("haveSoulRate", ParamFieldType.F32, 0xF4, null, null, null, "所持ソウル率") { it.haveSoulRate },
    ParamField("targetPriority", ParamFieldType.F32, 0xF8, null, null, null, "ターゲット優先度加算分") { it.targetPriority },
    ParamField("sightSearchEnemyCut", ParamFieldType.S32, 0xFC, null, null, null, "視覚索敵カット") { it.sightSearchEnemyCut },
    ParamField("hearingSearchEnemyCut", ParamFieldType.S32, 0x100, null, null, null, "聴覚索敵カット") { it.hearingSearchEnemyCut },
    ParamField("grabityRate", ParamFieldType.F32, 0x104, null, null, null, "グラビティ率") { it.grabityRate },
    ParamField("registPoizonChangeRate", ParamFieldType.F32, 0x108, null, null, null, "毒耐性変化倍率") { it.registPoizonChangeRate },
    ParamField("registIllnessChangeRate", ParamFieldType.F32, 0x10C, null, null, null, "疫病耐性変化倍率") { it.registIllnessChangeRate },
    ParamField("registBloodChangeRate", ParamFieldType.F32, 0x110, null, null, null, "出血耐性変化倍率") { it.registBloodChangeRate },
    ParamField("registCurseChangeRate", ParamFieldType.F32, 0x114, null, null, null, "呪耐性変化倍率") { it.registCurseChangeRate },
    ParamField("soulStealRate", ParamFieldType.F32, 0x118, null, null, null, "ソウルスティール係数") { it.soulStealRate },
    ParamField("lifeReductionRate", ParamFieldType.F32, 0x11C, null, null, null, "防御：寿命係数") { it.lifeReductionRate },
    ParamField("hpRecoverRate", ParamFieldType.F32, 0x120, null, null, null, "HP回復量係数") { it.hpRecoverRate },
    ParamField("replaceSpEffectId", ParamFieldType.S32, 0x124, null, null, null, "差し替える特殊効果") { it.replaceSpEffectId },
    ParamField("cycleOccurrenceSpEffectId", ParamFieldType.S32, 0x128, null, null, null, "周期発生特殊効果") { it.cycleOccurrenceSpEffectId },
    ParamField("atkOccurrenceSpEffectId", ParamFieldType.S32, 0x12C, null, null, null, "攻撃発生特殊効果") { it.atkOccurrenceSpEffectId },
    ParamField("guardDefFlickPowerRate", ParamFieldType.F32, 0x130, null, null, null, "ガード時はじき防御力アップ倍率") { it.guardDefFlickPowerRate },
    ParamField("guardStaminaCutRate", ParamFieldType.F32, 0x134, null, null, null, "ガード時スタミナカット倍率") { it.guardStaminaCutRate },
    ParamField("rayCastPassedTime", ParamFieldType.S16, 0x138, null, null, null, "視線通過：発動時間[ms]") { it.rayCastPassedTime },
    ParamField("changeSuperArmorPoint", ParamFieldType.S16, 0x13A, null, null, null, "SA値[point]") { it.changeSuperArmorPoint },
    ParamField("bowDistRate", ParamFieldType.S16, 0x13C, null, null, null, "弓飛距離補正[％]") { it.bowDistRate },
    ParamField("spCategory", ParamFieldType.U16, 0x13E, null, null, "SP_EFFECT_SPCATEGORY", "特殊効果カテゴリ") { it.spCategory },
    ParamField("categoryPriority", ParamFieldType.U8, 0x140, null, null, null, "カテゴリ内優先度") { it.categoryPriority },
    ParamField("saveCategory", ParamFieldType.S8, 0x141, null, null, "SP_EFFECT_SAVE_CATEGORY", "保存カテゴリ") { it.saveCategory },
    ParamField("changeMagicSlot", ParamFieldType.U8, 0x142, null, null, null, "魔法登録枠変化　魔法スロット") { it.changeMagicSlot },
    ParamField("changeMiracleSlot", ParamFieldType.U8, 0x143, null, null, null, "奇跡登録枠変化　奇跡スロット") { it.changeMiracleSlot },
    ParamField("heroPointDamage", ParamFieldType.S8, 0x144, null, null, null, "人間性ダメージ値") { it.heroPointDamage },
    ParamField("defFlickPower", ParamFieldType.U8, 0x145, null, null, null, "はじき防御力_上書き") { it.defFlickPower },
    ParamField("flickDamageCutRate", ParamFieldType.U8, 0x146, null, null, null, "はじき時ダメージ減衰率[%]_上書き") { it.flickDamageCutRate },
    ParamField("bloodDamageRate", ParamFieldType.U8, 0x147, null, null, null, "出血ダメージ補正倍率") { it.bloodDamageRate },
    ParamField("dmgLv_None", ParamFieldType.S8, 0x148, null, null, "ATKPARAM_REP_DMGTYPE", "DL_ダメージなし（0）") { it.dmgLv_None },
    ParamField("dmgLv_S", ParamFieldType.S8, 0x149, null, null, "ATKPARAM_REP_DMGTYPE", "DL_小（1）") { it.dmgLv_S },
    ParamField("dmgLv_M", ParamFieldType.S8, 0x14A, null, null, "ATKPARAM_REP_DMGTYPE", "DL_中（2）") { it.dmgLv_M },
    ParamField("dmgLv_L", ParamFieldType.S8, 0x14B, null, null, "ATKPARAM_REP_DMGTYPE", "DL_大（3）") { it.dmgLv_L },
    ParamField("dmgLv_BlowM", ParamFieldType.S8, 0x14C, null, null, "ATKPARAM_REP_DMGTYPE", "DL_吹っ飛び（4）") { it.dmgLv_BlowM },
    ParamField("dmgLv_Push", ParamFieldType.S8, 0x14D, null, null, "ATKPARAM_REP_DMGTYPE", "DL_プッシュ（5）") { it.dmgLv_Push },
    ParamField("dmgLv_Strike", ParamFieldType.S8, 0x14E, null, null, "ATKPARAM_REP_DMGTYPE", "DL_叩きつけ（6）") { it.dmgLv_Strike },
    ParamField("dmgLv_BlowS", ParamFieldType.S8, 0x14F, null, null, "ATKPARAM_REP_DMGTYPE", "DL_小吹っ飛び（7）") { it.dmgLv_BlowS },
    ParamField("dmgLv_Min", ParamFieldType.S8, 0x150, null, null, "ATKPARAM_REP_DMGTYPE", "DL_極小（8）") { it.dmgLv_Min },
    ParamField("dmgLv_Uppercut", ParamFieldType.S8, 0x151, null, null, "ATKPARAM_REP_DMGTYPE", "DL_打ち上げ（9）") { it.dmgLv_Uppercut },
    ParamField("dmgLv_BlowLL", ParamFieldType.S8, 0x152, null, null, "ATKPARAM_REP_DMGTYPE", "DL_特大吹っ飛び(10)") { it.dmgLv_BlowLL },
    ParamField("dmgLv_Breath", ParamFieldType.S8, 0x153, null, null, "ATKPARAM_REP_DMGTYPE", "DL_ブレス(11)") { it.dmgLv_Breath },
    ParamField("atkAttribute", ParamFieldType.U8, 0x154, null, null, "ATKPARAM_ATKATTR_TYPE", "物理属性") { it.atkAttribute },
    ParamField("spAttribute", ParamFieldType.U8, 0x155, null, null, "ATKPARAM_SPATTR_TYPE", "特殊属性") { it.spAttribute },
    ParamField("stateInfo", ParamFieldType.U8, 0x156, null, null, "SP_EFFECT_TYPE", "状態変化タイプ") { it.stateInfo },
    ParamField("wepParamChange", ParamFieldType.U8, 0x157, null, null, "SP_EFE_WEP_CHANGE_PARAM", "対武器パラメータ変化") { it.wepParamChange },
    ParamField("moveType", ParamFieldType.U8, 0x158, null, null, "SP_EFFECT_MOVE_TYPE", "移動タイプ") { it.moveType },
    ParamField("lifeReductionType", ParamFieldType.U8, 0x159, null, null, "SP_EFFECT_TYPE", "防御：寿命減少タイプ") { it.lifeReductionType },
    ParamField("throwCondition", ParamFieldType.U8, 0x15A, null, null, "SP_EFFECT_THROW_CONDITION_TYPE", "投げ条件") { it.throwCondition },
    ParamField("addBehaviorJudgeId_condition", ParamFieldType.S8, 0x15B, null, null, null, "行動判定IDに加算する条件値") { it.addBehaviorJudgeId_condition },
    ParamField("addBehaviorJudgeId_add", ParamFieldType.U8, 0x15C, null, null, null, "行動判定IDに加算する加算値") { it.addBehaviorJudgeId_add },
    ParamField("effectTargetSelf", ParamFieldType.U8, 0x15D, 0, 1, "SP_EFFECT_BOOL", "効果対象：所属　自分") { it.effectTargetSelf },
    ParamField("effectTargetFriend", ParamFieldType.U8, 0x15D, 1, 1, "SP_EFFECT_BOOL", "効果対象：所属　味方") { it.effectTargetFriend },
    ParamField("effectTargetEnemy", ParamFieldType.U8, 0x15D, 2, 1, "SP_EFFECT_BOOL", "効果対象：所属　敵") { it.effectTargetEnemy },
    ParamField("effectTargetPlayer", ParamFieldType.U8, 0x15D, 3, 1, "SP_EFFECT_BOOL", "効果対象：操作　PC") { it.effectTargetPlayer },
    ParamField("effectTargetAI", ParamFieldType.U8, 0x15D, 4, 1, "SP_EFFECT_BOOL", "効果対象：操作　AI") { it.effectTargetAI },
    ParamField("effectTargetLive", ParamFieldType.U8, 0x15D, 5, 1, "SP_EFFECT_BOOL", "効果対象：状態　生存") { it.effectTargetLive },
    ParamField("effectTargetGhost", ParamFieldType.U8, 0x15D, 6, 1, "SP_EFFECT_BOOL", "効果対象：状態　全ゴースト") { it.effectTargetGhost },
    ParamField("effectTargetWhiteGhost", ParamFieldType.U8, 0x15D, 7, 1, "SP_EFFECT_BOOL", "効果対象：状態　白ゴースト") { it.effectTargetWhiteGhost },
    ParamField("effectTargetBlackGhost", ParamFieldType.U8, 0x15E, 0, 1, "SP_EFFECT_BOOL", "効果対象：状態　黒ゴースト") { it.effectTargetBlackGhost },
    ParamField("effectTargetAttacker", ParamFieldType.U8, 0x15E, 1, 1, "SP_EFFECT_BOOL", "効果対象：攻撃者に発動") { it.effectTargetAttacker },
    ParamField("dispIconNonactive", ParamFieldType.U8, 0x15E, 2, 1, "SP_EFFECT_BOOL", "発動してなくてもアイコン表示") { it.dispIconNonactive },
    ParamField("useSpEffectEffect", ParamFieldType.U8, 0x15E, 3, 1, "SP_EFFECT_BOOL", "特殊効果エフェクトを使用するか") { it.useSpEffectEffect },
    ParamField("bAdjustMagicAblity", ParamFieldType.U8, 0x15E, 4, 1, "SP_EFFECT_BOOL", "魔力補正するか？") { it.bAdjustMagicAblity },
    ParamField("bAdjustFaithAblity", ParamFieldType.U8, 0x15E, 5, 1, "SP_EFFECT_BOOL", "信仰補正するか？") { it.bAdjustFaithAblity },
    ParamField("bGameClearBonus", ParamFieldType.U8, 0x15E, 6, 1, "SP_EFFECT_BOOL", "周回ボーナス用か？") { it.bGameClearBonus },
    ParamField("magParamChange", ParamFieldType.U8, 0x15E, 7, 1, "SP_EFFECT_BOOL", "対魔法パラメータ変化") { it.magParamChange },
    ParamField("miracleParamChange", ParamFieldType.U8, 0x15F, 0, 1, "SP_EFFECT_BOOL", "対奇跡パラメータ変化") { it.miracleParamChange },
    ParamField("clearSoul", ParamFieldType.U8, 0x15F, 1, 1, "SP_EFFECT_BOOL", "所持ソウルクリアするか") { it.clearSoul },
    ParamField("requestSOS", ParamFieldType.U8, 0x15F, 2, 1, "SP_EFFECT_BOOL", "SOSサイン　判定フラグ") { it.requestSOS },
    ParamField("requestBlackSOS", ParamFieldType.U8, 0x15F, 3, 1, "SP_EFFECT_BOOL", "ブラックSOSサイン　判定フラグ") { it.requestBlackSOS },
    ParamField("requestForceJoinBlackSOS", ParamFieldType.U8, 0x15F, 4, 1, "SP_EFFECT_BOOL", "ブラック強制参加SOSサイン　判定フラグ") { it.requestForceJoinBlackSOS },
    ParamField("requestKickSession", ParamFieldType.U8, 0x15F, 5, 1, "SP_EFFECT_BOOL", "キック　判定フラグ") { it.requestKickSession },
    ParamField("requestLeaveSession", ParamFieldType.U8, 0x15F, 6, 1, "SP_EFFECT_BOOL", "退出　判定フラグ") { it.requestLeaveSession },
    ParamField("requestNpcInveda", ParamFieldType.U8, 0x15F, 7, 1, "SP_EFFECT_BOOL", "NPCへの侵入　判定フラグ") { it.requestNpcInveda },
    ParamField("noDead", ParamFieldType.U8, 0x160, 0, 1, "SP_EFFECT_BOOL", "成仏不可　判定フラグ") { it.noDead },
    ParamField("bCurrHPIndependeMaxHP", ParamFieldType.U8, 0x160, 1, 1, "SP_EFFECT_BOOL", "最大HPが増減しても、現在HPは影響しないか？") { it.bCurrHPIndependeMaxHP },
    ParamField("corrosionIgnore", ParamFieldType.U8, 0x160, 2, 1, "SP_EFFECT_BOOL", "腐食無視") { it.corrosionIgnore },
    ParamField("sightSearchCutIgnore", ParamFieldType.U8, 0x160, 3, 1, "SP_EFFECT_BOOL", "視覚索敵カット無視") { it.sightSearchCutIgnore },
    ParamField("hearingSearchCutIgnore", ParamFieldType.U8, 0x160, 4, 1, "SP_EFFECT_BOOL", "聴覚索敵カット無視") { it.hearingSearchCutIgnore },
    ParamField("antiMagicIgnore", ParamFieldType.U8, 0x160, 5, 1, "SP_EFFECT_BOOL", "アンチマジック無効") { it.antiMagicIgnore },
    ParamField("fakeTargetIgnore", ParamFieldType.U8, 0x160, 6, 1, "SP_EFFECT_BOOL", "偽ターゲット無効") { it.fakeTargetIgnore },
    ParamField("fakeTargetIgnoreUndead", ParamFieldType.U8, 0x160, 7, 1, "SP_EFFECT_BOOL", "偽ターゲット無効_不死系") { it.fakeTargetIgnoreUndead },
    ParamField("fakeTargetIgnoreAnimal", ParamFieldType.U8, 0x161, 0, 1, "SP_EFFECT_BOOL", "偽ターゲット無効_獣系") { it.fakeTargetIgnoreAnimal },
    ParamField("grabityIgnore", ParamFieldType.U8, 0x161, 1, 1, "SP_EFFECT_BOOL", "グラビティ無効") { it.grabityIgnore },
    ParamField("disablePoison", ParamFieldType.U8, 0x161, 2, 1, "SP_EFFECT_BOOL", "毒無効") { it.disablePoison },
    ParamField("disableDisease", ParamFieldType.U8, 0x161, 3, 1, "SP_EFFECT_BOOL", "疫病無効") { it.disableDisease },
    ParamField("disableBlood", ParamFieldType.U8, 0x161, 4, 1, "SP_EFFECT_BOOL", "出血無効") { it.disableBlood },
    ParamField("disableCurse", ParamFieldType.U8, 0x161, 5, 1, "SP_EFFECT_BOOL", "呪無効") { it.disableCurse },
    ParamField("enableCharm", ParamFieldType.U8, 0x161, 6, 1, "SP_EFFECT_BOOL", "魅了有効") { it.enableCharm },
    ParamField("enableLifeTime", ParamFieldType.U8, 0x161, 7, 1, "SP_EFFECT_BOOL", "寿命延長するか？") { it.enableLifeTime },
    ParamField("hasTarget", ParamFieldType.U8, 0x162, 0, 1, "SP_EFFECT_BOOL", "敵を把握しているか？") { it.hasTarget },
    ParamField("isFireDamageCancel", ParamFieldType.U8, 0x162, 1, 1, "SP_EFFECT_BOOL", "解除条件:炎ダメージ") { it.isFireDamageCancel },
    ParamField("isExtendSpEffectLife", ParamFieldType.U8, 0x162, 2, 1, "SP_EFFECT_BOOL", "寿命延長効果で延長するか？") { it.isExtendSpEffectLife },
    ParamField("requestLeaveColiseumSession", ParamFieldType.U8, 0x162, 3, 1, "SP_EFFECT_BOOL", "闘技場退出　判定フラグ") { it.requestLeaveColiseumSession },
    ParamField("vowType0", ParamFieldType.U8, 0x163, 0, 1, "SP_EFFECT_BOOL", "誓約0") { it.vowType0 },
    ParamField("vowType1", ParamFieldType.U8, 0x163, 1, 1, "SP_EFFECT_BOOL", "誓約1") { it.vowType1 },
    ParamField("vowType2", ParamFieldType.U8, 0x163, 2, 1, "SP_EFFECT_BOOL", "誓約2") { it.vowType2 },
    ParamField("vowType3", ParamFieldType.U8, 0x163, 3, 1, "SP_EFFECT_BOOL", "誓約3") { it.vowType3 },
    ParamField("vowType4", ParamFieldType.U8, 0x163, 4, 1, "SP_EFFECT_BOOL", "誓約4") { it.vowType4 },
    ParamField("vowType5", ParamFieldType.U8, 0x163, 5, 1, "SP_EFFECT_BOOL", "誓約5") { it.vowType5 },
    ParamField("vowType6", ParamFieldType.U8, 0x163, 6, 1, "SP_EFFECT_BOOL", "誓約6") { it.vowType6 },
    ParamField("vowType7", ParamFieldType.U8, 0x163, 7, 1, "SP_EFFECT_BOOL", "誓約7") { it.vowType7 },
    ParamField("vowType8", ParamFieldType.U8, 0x164, 0, 1, "SP_EFFECT_BOOL", "誓約8") { it.vowType8 },
    ParamField("vowType9", ParamFieldType.U8, 0x164, 1, 1, "SP_EFFECT_BOOL", "誓約9") { it.vowType9 },
    ParamField("vowType10", ParamFieldType.U8, 0x164, 2, 1, "SP_EFFECT_BOOL", "誓約10") { it.vowType10 },
    ParamField("vowType11", ParamFieldType.U8, 0x164, 3, 1, "SP_EFFECT_BOOL", "誓約11") { it.vowType11 },
    ParamField("vowType12", ParamFieldType.U8, 0x164, 4, 1, "SP_EFFECT_BOOL", "誓約12") { it.vowType12 },
    ParamField("vowType13", ParamFieldType.U8, 0x164, 5, 1, "SP_EFFECT_BOOL", "誓約13") { it.vowType13 },
    ParamField("vowType14", ParamFieldType.U8, 0x164, 6, 1, "SP_EFFECT_BOOL", "誓約14") { it.vowType14 },
    ParamField("vowType15", ParamFieldType.U8, 0x164, 7, 1, "SP_EFFECT_BOOL", "誓約15") { it.vowType15 }
)
