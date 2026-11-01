// GENERATED FILE - do not edit. Regenerate with:
//
//     ./gradlew :tools:generateParamDefs -Pptde=<DATA directory> -Pdsr=<DATA directory>
//
// Source: paramdef.paramdefbnd, NpcParam.paramdef, read from the installation

@file:Suppress("SpellCheckingInspection")

package com.sappyoak.dsanalyzer.formats.param.defs

import com.sappyoak.dsanalyzer.formats.param.ParamField
import com.sappyoak.dsanalyzer.formats.param.ParamRow
import com.sappyoak.dsanalyzer.formats.param.ParamRowData
import com.sappyoak.dsanalyzer.formats.param.ParamRowType
import com.sappyoak.dsanalyzer.formats.param.paramdef.ParamFieldType

/** `NPC_PARAM_ST`: PrepareToDie 0x150, Remastered 0x150 a row, data version 3 */
public class NpcParamRow(row: ParamRowData) : ParamRow(row) {
    /** `s32` at 0x0 */
    public val behaviorVariationId: Int get() = bytes.s32(0x0)

    /** `s32` at 0x4 */
    public val aiThinkId: Int get() = bytes.s32(0x4)

    /** `s32` at 0x8 */
    public val nameId: Int get() = bytes.s32(0x8)

    /** `f32` at 0xC */
    public val turnVellocity: Float get() = bytes.f32(0xC)

    /** `f32` at 0x10 */
    public val hitHeight: Float get() = bytes.f32(0x10)

    /** `f32` at 0x14 */
    public val hitRadius: Float get() = bytes.f32(0x14)

    /** `u32` at 0x18 */
    public val weight: Long get() = bytes.u32(0x18)

    /** `f32` at 0x1C */
    public val hitYOffset: Float get() = bytes.f32(0x1C)

    /** `u32` at 0x20 */
    public val hp: Long get() = bytes.u32(0x20)

    /** `u32` at 0x24 */
    public val mp: Long get() = bytes.u32(0x24)

    /** `u32` at 0x28 */
    public val getSoul: Long get() = bytes.u32(0x28)

    /** `s32` at 0x2C */
    public val itemLotId_1: Int get() = bytes.s32(0x2C)

    /** `s32` at 0x30 */
    public val itemLotId_2: Int get() = bytes.s32(0x30)

    /** `s32` at 0x34 */
    public val itemLotId_3: Int get() = bytes.s32(0x34)

    /** `s32` at 0x38 */
    public val itemLotId_4: Int get() = bytes.s32(0x38)

    /** `s32` at 0x3C */
    public val itemLotId_5: Int get() = bytes.s32(0x3C)

    /** `s32` at 0x40 */
    public val itemLotId_6: Int get() = bytes.s32(0x40)

    /** `s32` at 0x44 */
    public val humanityLotId: Int get() = bytes.s32(0x44)

    /** `s32` at 0x48 */
    public val spEffectID0: Int get() = bytes.s32(0x48)

    /** `s32` at 0x4C */
    public val spEffectID1: Int get() = bytes.s32(0x4C)

    /** `s32` at 0x50 */
    public val spEffectID2: Int get() = bytes.s32(0x50)

    /** `s32` at 0x54 */
    public val spEffectID3: Int get() = bytes.s32(0x54)

    /** `s32` at 0x58 */
    public val spEffectID4: Int get() = bytes.s32(0x58)

    /** `s32` at 0x5C */
    public val spEffectID5: Int get() = bytes.s32(0x5C)

    /** `s32` at 0x60 */
    public val spEffectID6: Int get() = bytes.s32(0x60)

    /** `s32` at 0x64 */
    public val spEffectID7: Int get() = bytes.s32(0x64)

    /** `s32` at 0x68 */
    public val gameClearSpEffectID: Int get() = bytes.s32(0x68)

    /** `f32` at 0x6C */
    public val physGuardCutRate: Float get() = bytes.f32(0x6C)

    /** `f32` at 0x70 */
    public val magGuardCutRate: Float get() = bytes.f32(0x70)

    /** `f32` at 0x74 */
    public val fireGuardCutRate: Float get() = bytes.f32(0x74)

    /** `f32` at 0x78 */
    public val thunGuardCutRate: Float get() = bytes.f32(0x78)

    /** `s32` at 0x7C */
    public val animIdOffset: Int get() = bytes.s32(0x7C)

    /** `s32` at 0x80 */
    public val moveAnimId: Int get() = bytes.s32(0x80)

    /** `s32` at 0x84 */
    public val spMoveAnimId1: Int get() = bytes.s32(0x84)

    /** `s32` at 0x88 */
    public val spMoveAnimId2: Int get() = bytes.s32(0x88)

    /** `f32` at 0x8C */
    public val networkWarpDist: Float get() = bytes.f32(0x8C)

    /** `s32` at 0x90 */
    public val dbgBehaviorR1: Int get() = bytes.s32(0x90)

    /** `s32` at 0x94 */
    public val dbgBehaviorL1: Int get() = bytes.s32(0x94)

    /** `s32` at 0x98 */
    public val dbgBehaviorR2: Int get() = bytes.s32(0x98)

    /** `s32` at 0x9C */
    public val dbgBehaviorL2: Int get() = bytes.s32(0x9C)

    /** `s32` at 0xA0 */
    public val dbgBehaviorRL: Int get() = bytes.s32(0xA0)

    /** `s32` at 0xA4 */
    public val dbgBehaviorRR: Int get() = bytes.s32(0xA4)

    /** `s32` at 0xA8 */
    public val dbgBehaviorRD: Int get() = bytes.s32(0xA8)

    /** `s32` at 0xAC */
    public val dbgBehaviorRU: Int get() = bytes.s32(0xAC)

    /** `s32` at 0xB0 */
    public val dbgBehaviorLL: Int get() = bytes.s32(0xB0)

    /** `s32` at 0xB4 */
    public val dbgBehaviorLR: Int get() = bytes.s32(0xB4)

    /** `s32` at 0xB8 */
    public val dbgBehaviorLD: Int get() = bytes.s32(0xB8)

    /** `s32` at 0xBC */
    public val dbgBehaviorLU: Int get() = bytes.s32(0xBC)

    /** `s32` at 0xC0 */
    public val animIdOffset2: Int get() = bytes.s32(0xC0)

    /** `f32` at 0xC4 */
    public val partsDamageRate1: Float get() = bytes.f32(0xC4)

    /** `f32` at 0xC8 */
    public val partsDamageRate2: Float get() = bytes.f32(0xC8)

    /** `f32` at 0xCC */
    public val partsDamageRate3: Float get() = bytes.f32(0xCC)

    /** `f32` at 0xD0 */
    public val partsDamageRate4: Float get() = bytes.f32(0xD0)

    /** `f32` at 0xD4 */
    public val partsDamageRate5: Float get() = bytes.f32(0xD4)

    /** `f32` at 0xD8 */
    public val partsDamageRate6: Float get() = bytes.f32(0xD8)

    /** `f32` at 0xDC */
    public val partsDamageRate7: Float get() = bytes.f32(0xDC)

    /** `f32` at 0xE0 */
    public val partsDamageRate8: Float get() = bytes.f32(0xE0)

    /** `f32` at 0xE4 */
    public val weakPartsDamageRate: Float get() = bytes.f32(0xE4)

    /** `f32` at 0xE8 */
    public val superArmorRecoverCorrection: Float get() = bytes.f32(0xE8)

    /** `f32` at 0xEC */
    public val superArmorBrakeKnockbackDist: Float get() = bytes.f32(0xEC)

    /** `u16` at 0xF0 */
    public val stamina: Int get() = bytes.u16(0xF0)

    /** `u16` at 0xF2 */
    public val staminaRecoverBaseVel: Int get() = bytes.u16(0xF2)

    /** `u16` at 0xF4 */
    public val def_phys: Int get() = bytes.u16(0xF4)

    /** `s16` at 0xF6 */
    public val def_slash: Int get() = bytes.s16(0xF6)

    /** `s16` at 0xF8 */
    public val def_blow: Int get() = bytes.s16(0xF8)

    /** `s16` at 0xFA */
    public val def_thrust: Int get() = bytes.s16(0xFA)

    /** `u16` at 0xFC */
    public val def_mag: Int get() = bytes.u16(0xFC)

    /** `u16` at 0xFE */
    public val def_fire: Int get() = bytes.u16(0xFE)

    /** `u16` at 0x100 */
    public val def_thunder: Int get() = bytes.u16(0x100)

    /** `u16` at 0x102 */
    public val defFlickPower: Int get() = bytes.u16(0x102)

    /** `u16` at 0x104 */
    public val resist_poison: Int get() = bytes.u16(0x104)

    /** `u16` at 0x106 */
    public val resist_desease: Int get() = bytes.u16(0x106)

    /** `u16` at 0x108 */
    public val resist_blood: Int get() = bytes.u16(0x108)

    /** `u16` at 0x10A */
    public val resist_curse: Int get() = bytes.u16(0x10A)

    /** `s16` at 0x10C */
    public val ghostModelId: Int get() = bytes.s16(0x10C)

    /** `s16` at 0x10E */
    public val normalChangeResouceId: Int get() = bytes.s16(0x10E)

    /** `s16` at 0x110 */
    public val guardAngle: Int get() = bytes.s16(0x110)

    /** `s16` at 0x112 */
    public val slashGuardCutRate: Int get() = bytes.s16(0x112)

    /** `s16` at 0x114 */
    public val blowGuardCutRate: Int get() = bytes.s16(0x114)

    /** `s16` at 0x116 */
    public val thrustGuardCutRate: Int get() = bytes.s16(0x116)

    /** `s16` at 0x118 */
    public val superArmorDurability: Int get() = bytes.s16(0x118)

    /** `s16` at 0x11A */
    public val normalChangeTexChrId: Int get() = bytes.s16(0x11A)

    /** `u16` at 0x11C, `NPC_ITEMDROP_TYPE` */
    public val dropType: Int get() = bytes.u16(0x11C)

    /** `u8` at 0x11E */
    public val knockbackRate: Int get() = bytes.u8(0x11E)

    /** `u8` at 0x11F */
    public val knockbackParamId: Int get() = bytes.u8(0x11F)

    /** `u8` at 0x120 */
    public val fallDamageDump: Int get() = bytes.u8(0x120)

    /** `u8` at 0x121 */
    public val staminaGuardDef: Int get() = bytes.u8(0x121)

    /** `u8` at 0x122 */
    public val pcAttrB: Int get() = bytes.u8(0x122)

    /** `u8` at 0x123 */
    public val pcAttrW: Int get() = bytes.u8(0x123)

    /** `u8` at 0x124 */
    public val pcAttrL: Int get() = bytes.u8(0x124)

    /** `u8` at 0x125 */
    public val pcAttrR: Int get() = bytes.u8(0x125)

    /** `u8` at 0x126 */
    public val areaAttrB: Int get() = bytes.u8(0x126)

    /** `u8` at 0x127 */
    public val areaAttrW: Int get() = bytes.u8(0x127)

    /** `u8` at 0x128 */
    public val areaAttrL: Int get() = bytes.u8(0x128)

    /** `u8` at 0x129 */
    public val areaAttrR: Int get() = bytes.u8(0x129)

    /** `u8` at 0x12A */
    public val mpRecoverBaseVel: Int get() = bytes.u8(0x12A)

    /** `u8` at 0x12B */
    public val flickDamageCutRate: Int get() = bytes.u8(0x12B)

    /** `s8` at 0x12C */
    public val defaultLodParamId: Int get() = bytes.s8(0x12C)

    /** `u8` at 0x12D, `NPC_DRAW_TYPE` */
    public val drawType: Int get() = bytes.u8(0x12D)

    /** `u8` at 0x12E, `NPC_TYPE` */
    public val npcType: Int get() = bytes.u8(0x12E)

    /** `u8` at 0x12F, `NPC_TEMA_TYPE` */
    public val teamType: Int get() = bytes.u8(0x12F)

    /** `u8` at 0x130, `NPC_MOVE_TYPE` */
    public val moveType: Int get() = bytes.u8(0x130)

    /** `u8` at 0x131 */
    public val lockDist: Int get() = bytes.u8(0x131)

    /** `u8` at 0x132, `WEP_MATERIAL_DEF` */
    public val material: Int get() = bytes.u8(0x132)

    /** `u8` at 0x133, `WEP_MATERIAL_DEF_SFX` */
    public val materialSfx: Int get() = bytes.u8(0x133)

    /** `u8` at 0x134, `WEP_MATERIAL_DEF` */
    public val material_Weak: Int get() = bytes.u8(0x134)

    /** `u8` at 0x135, `WEP_MATERIAL_DEF_SFX` */
    public val materialSfx_Weak: Int get() = bytes.u8(0x135)

    /** `u8` at 0x136, `ATK_PARAM_PARTSDMGTYPE` */
    public val partsDamageType: Int get() = bytes.u8(0x136)

    /** `u8` at 0x137 */
    public val maxUndurationAng: Int get() = bytes.u8(0x137)

    /** `s8` at 0x138 */
    public val guardLevel: Int get() = bytes.s8(0x138)

    /** `u8` at 0x139, `NPC_BURN_TYPE` */
    public val burnSfxType: Int get() = bytes.u8(0x139)

    /** `s8` at 0x13A */
    public val poisonGuardResist: Int get() = bytes.s8(0x13A)

    /** `s8` at 0x13B */
    public val diseaseGuardResist: Int get() = bytes.s8(0x13B)

    /** `s8` at 0x13C */
    public val bloodGuardResist: Int get() = bytes.s8(0x13C)

    /** `s8` at 0x13D */
    public val curseGuardResist: Int get() = bytes.s8(0x13D)

    /** `u8` at 0x13E */
    public val parryAttack: Int get() = bytes.u8(0x13E)

    /** `u8` at 0x13F */
    public val parryDefence: Int get() = bytes.u8(0x13F)

    /** `u8` at 0x140, `NPC_SFX_SIZE` */
    public val sfxSize: Int get() = bytes.u8(0x140)

    /** `u8` at 0x141 */
    public val pushOutCamRegionRadius: Int get() = bytes.u8(0x141)

    /** `u8` at 0x142, `NPC_HITSTOP_TYPE` */
    public val hitStopType: Int get() = bytes.u8(0x142)

    /** `u8` at 0x143 */
    public val ladderEndChkOffsetTop: Int get() = bytes.u8(0x143)

    /** `u8` at 0x144 */
    public val ladderEndChkOffsetLow: Int get() = bytes.u8(0x144)

    /** bit 0 of the `u8` at 0x145, `NPC_BOOL` */
    public val useRagdollCamHit: Boolean get() = bytes.flag(0x145, 0)

    /** bit 1 of the `u8` at 0x145, `NPC_BOOL` */
    public val disableClothRigidHit: Boolean get() = bytes.flag(0x145, 1)

    /** bit 2 of the `u8` at 0x145, `NPC_BOOL` */
    public val useRagdoll: Boolean get() = bytes.flag(0x145, 2)

    /** bit 3 of the `u8` at 0x145, `NPC_BOOL` */
    public val isDemon: Boolean get() = bytes.flag(0x145, 3)

    /** bit 4 of the `u8` at 0x145, `NPC_BOOL` */
    public val isGhost: Boolean get() = bytes.flag(0x145, 4)

    /** bit 5 of the `u8` at 0x145, `NPC_BOOL` */
    public val isNoDamageMotion: Boolean get() = bytes.flag(0x145, 5)

    /** bit 6 of the `u8` at 0x145, `NPC_BOOL` */
    public val isUnduration: Boolean get() = bytes.flag(0x145, 6)

    /** bit 7 of the `u8` at 0x145, `NPC_BOOL` */
    public val isChangeWanderGhost: Boolean get() = bytes.flag(0x145, 7)

    /** bit 0 of the `u8` at 0x146 */
    public val modelDispMask0: Boolean get() = bytes.flag(0x146, 0)

    /** bit 1 of the `u8` at 0x146 */
    public val modelDispMask1: Boolean get() = bytes.flag(0x146, 1)

    /** bit 2 of the `u8` at 0x146 */
    public val modelDispMask2: Boolean get() = bytes.flag(0x146, 2)

    /** bit 3 of the `u8` at 0x146 */
    public val modelDispMask3: Boolean get() = bytes.flag(0x146, 3)

    /** bit 4 of the `u8` at 0x146 */
    public val modelDispMask4: Boolean get() = bytes.flag(0x146, 4)

    /** bit 5 of the `u8` at 0x146 */
    public val modelDispMask5: Boolean get() = bytes.flag(0x146, 5)

    /** bit 6 of the `u8` at 0x146 */
    public val modelDispMask6: Boolean get() = bytes.flag(0x146, 6)

    /** bit 7 of the `u8` at 0x146 */
    public val modelDispMask7: Boolean get() = bytes.flag(0x146, 7)

    /** bit 0 of the `u8` at 0x147 */
    public val modelDispMask8: Boolean get() = bytes.flag(0x147, 0)

    /** bit 1 of the `u8` at 0x147 */
    public val modelDispMask9: Boolean get() = bytes.flag(0x147, 1)

    /** bit 2 of the `u8` at 0x147 */
    public val modelDispMask10: Boolean get() = bytes.flag(0x147, 2)

    /** bit 3 of the `u8` at 0x147 */
    public val modelDispMask11: Boolean get() = bytes.flag(0x147, 3)

    /** bit 4 of the `u8` at 0x147 */
    public val modelDispMask12: Boolean get() = bytes.flag(0x147, 4)

    /** bit 5 of the `u8` at 0x147 */
    public val modelDispMask13: Boolean get() = bytes.flag(0x147, 5)

    /** bit 6 of the `u8` at 0x147 */
    public val modelDispMask14: Boolean get() = bytes.flag(0x147, 6)

    /** bit 7 of the `u8` at 0x147 */
    public val modelDispMask15: Boolean get() = bytes.flag(0x147, 7)

    /** bit 0 of the `u8` at 0x148 */
    public val isEnableNeckTurn: Boolean get() = bytes.flag(0x148, 0)

    /** bit 1 of the `u8` at 0x148 */
    public val disableRespawn: Boolean get() = bytes.flag(0x148, 1)

    /** bit 2 of the `u8` at 0x148 */
    public val isMoveAnimWait: Boolean get() = bytes.flag(0x148, 2)

    /** bit 3 of the `u8` at 0x148, `NPC_BOOL` */
    public val isCrowd: Boolean get() = bytes.flag(0x148, 3)

    /** bit 4 of the `u8` at 0x148, `NPC_BOOL` */
    public val isWeakSaint: Boolean get() = bytes.flag(0x148, 4)

    /** bit 5 of the `u8` at 0x148, `NPC_BOOL` */
    public val isWeakA: Boolean get() = bytes.flag(0x148, 5)

    /** bit 6 of the `u8` at 0x148, `NPC_BOOL` */
    public val isWeakB: Boolean get() = bytes.flag(0x148, 6)

    /** bits 0-2 of the `u8` at 0x149 */
    public val vowType: Int get() = bytes.bits(0x149, 1, 0, 3)

    /** bit 3 of the `u8` at 0x149, `NPC_BOOL` */
    public val disableInitializeDead: Boolean get() = bytes.flag(0x149, 3)

    public companion object : ParamRowType<NpcParamRow>("NPC_PARAM_ST", 0x150, 3, FIELDS, ::NpcParamRow)
}

private val FIELDS: List<ParamField<NpcParamRow>> = listOf<ParamField<NpcParamRow>>(
    ParamField("behaviorVariationId", ParamFieldType.S32, 0x0, null, null, null, "行動バリエーションID") { it.behaviorVariationId },
    ParamField("aiThinkId", ParamFieldType.S32, 0x4, null, null, null, "AI思考ID") { it.aiThinkId },
    ParamField("nameId", ParamFieldType.S32, 0x8, null, null, null, "NPC名ID") { it.nameId },
    ParamField("turnVellocity", ParamFieldType.F32, 0xC, null, null, null, "旋回速度[deg/sec]") { it.turnVellocity },
    ParamField("hitHeight", ParamFieldType.F32, 0x10, null, null, null, "あたりの高さ[m]") { it.hitHeight },
    ParamField("hitRadius", ParamFieldType.F32, 0x14, null, null, null, "あたりの半径[m]") { it.hitRadius },
    ParamField("weight", ParamFieldType.U32, 0x18, null, null, null, "重量[kg]") { it.weight },
    ParamField("hitYOffset", ParamFieldType.F32, 0x1C, null, null, null, "表示位置Yオフセット[m]") { it.hitYOffset },
    ParamField("hp", ParamFieldType.U32, 0x20, null, null, null, "ＨＰ") { it.hp },
    ParamField("mp", ParamFieldType.U32, 0x24, null, null, null, "ＭＰ") { it.mp },
    ParamField("getSoul", ParamFieldType.U32, 0x28, null, null, null, "ソウル") { it.getSoul },
    ParamField("itemLotId_1", ParamFieldType.S32, 0x2C, null, null, null, "アイテム抽選ID_1") { it.itemLotId_1 },
    ParamField("itemLotId_2", ParamFieldType.S32, 0x30, null, null, null, "アイテム抽選ID_2") { it.itemLotId_2 },
    ParamField("itemLotId_3", ParamFieldType.S32, 0x34, null, null, null, "アイテム抽選ID_3") { it.itemLotId_3 },
    ParamField("itemLotId_4", ParamFieldType.S32, 0x38, null, null, null, "アイテム抽選ID_4") { it.itemLotId_4 },
    ParamField("itemLotId_5", ParamFieldType.S32, 0x3C, null, null, null, "アイテム抽選ID_5") { it.itemLotId_5 },
    ParamField("itemLotId_6", ParamFieldType.S32, 0x40, null, null, null, "アイテム抽選ID_6") { it.itemLotId_6 },
    ParamField("humanityLotId", ParamFieldType.S32, 0x44, null, null, null, "人間性抽選ID") { it.humanityLotId },
    ParamField("spEffectID0", ParamFieldType.S32, 0x48, null, null, null, "常駐特殊効果0") { it.spEffectID0 },
    ParamField("spEffectID1", ParamFieldType.S32, 0x4C, null, null, null, "常駐特殊効果1") { it.spEffectID1 },
    ParamField("spEffectID2", ParamFieldType.S32, 0x50, null, null, null, "常駐特殊効果2") { it.spEffectID2 },
    ParamField("spEffectID3", ParamFieldType.S32, 0x54, null, null, null, "常駐特殊効果3") { it.spEffectID3 },
    ParamField("spEffectID4", ParamFieldType.S32, 0x58, null, null, null, "常駐特殊効果4") { it.spEffectID4 },
    ParamField("spEffectID5", ParamFieldType.S32, 0x5C, null, null, null, "常駐特殊効果5") { it.spEffectID5 },
    ParamField("spEffectID6", ParamFieldType.S32, 0x60, null, null, null, "常駐特殊効果6") { it.spEffectID6 },
    ParamField("spEffectID7", ParamFieldType.S32, 0x64, null, null, null, "常駐特殊効果7") { it.spEffectID7 },
    ParamField("gameClearSpEffectID", ParamFieldType.S32, 0x68, null, null, null, "周回ボーナス用特殊効果ＩＤ") { it.gameClearSpEffectID },
    ParamField("physGuardCutRate", ParamFieldType.F32, 0x6C, null, null, null, "物理攻撃カット率[％]") { it.physGuardCutRate },
    ParamField("magGuardCutRate", ParamFieldType.F32, 0x70, null, null, null, "魔法攻撃カット率[％]") { it.magGuardCutRate },
    ParamField("fireGuardCutRate", ParamFieldType.F32, 0x74, null, null, null, "炎攻撃力カット率[％]") { it.fireGuardCutRate },
    ParamField("thunGuardCutRate", ParamFieldType.F32, 0x78, null, null, null, "電撃攻撃力カット率[％]") { it.thunGuardCutRate },
    ParamField("animIdOffset", ParamFieldType.S32, 0x7C, null, null, null, "アニメIDオフセット1") { it.animIdOffset },
    ParamField("moveAnimId", ParamFieldType.S32, 0x80, null, null, null, "移動アニメパラメータID") { it.moveAnimId },
    ParamField("spMoveAnimId1", ParamFieldType.S32, 0x84, null, null, null, "特殊移動アニメパラメータID0") { it.spMoveAnimId1 },
    ParamField("spMoveAnimId2", ParamFieldType.S32, 0x88, null, null, null, "特殊移動アニメパラメータID1") { it.spMoveAnimId2 },
    ParamField("networkWarpDist", ParamFieldType.F32, 0x8C, null, null, null, "ネットワークワープ判定距離[m/秒]") { it.networkWarpDist },
    ParamField("dbgBehaviorR1", ParamFieldType.S32, 0x90, null, null, null, "R1") { it.dbgBehaviorR1 },
    ParamField("dbgBehaviorL1", ParamFieldType.S32, 0x94, null, null, null, "L1") { it.dbgBehaviorL1 },
    ParamField("dbgBehaviorR2", ParamFieldType.S32, 0x98, null, null, null, "R2") { it.dbgBehaviorR2 },
    ParamField("dbgBehaviorL2", ParamFieldType.S32, 0x9C, null, null, null, "L2") { it.dbgBehaviorL2 },
    ParamField("dbgBehaviorRL", ParamFieldType.S32, 0xA0, null, null, null, "□") { it.dbgBehaviorRL },
    ParamField("dbgBehaviorRR", ParamFieldType.S32, 0xA4, null, null, null, "○") { it.dbgBehaviorRR },
    ParamField("dbgBehaviorRD", ParamFieldType.S32, 0xA8, null, null, null, "×") { it.dbgBehaviorRD },
    ParamField("dbgBehaviorRU", ParamFieldType.S32, 0xAC, null, null, null, "△") { it.dbgBehaviorRU },
    ParamField("dbgBehaviorLL", ParamFieldType.S32, 0xB0, null, null, null, "←") { it.dbgBehaviorLL },
    ParamField("dbgBehaviorLR", ParamFieldType.S32, 0xB4, null, null, null, "→") { it.dbgBehaviorLR },
    ParamField("dbgBehaviorLD", ParamFieldType.S32, 0xB8, null, null, null, "↓") { it.dbgBehaviorLD },
    ParamField("dbgBehaviorLU", ParamFieldType.S32, 0xBC, null, null, null, "↑") { it.dbgBehaviorLU },
    ParamField("animIdOffset2", ParamFieldType.S32, 0xC0, null, null, null, "アニメIDオフセット2") { it.animIdOffset2 },
    ParamField("partsDamageRate1", ParamFieldType.F32, 0xC4, null, null, null, "部位1ダメージ倍率") { it.partsDamageRate1 },
    ParamField("partsDamageRate2", ParamFieldType.F32, 0xC8, null, null, null, "部位2ダメージ倍率") { it.partsDamageRate2 },
    ParamField("partsDamageRate3", ParamFieldType.F32, 0xCC, null, null, null, "部位3ダメージ倍率") { it.partsDamageRate3 },
    ParamField("partsDamageRate4", ParamFieldType.F32, 0xD0, null, null, null, "部位4ダメージ倍率") { it.partsDamageRate4 },
    ParamField("partsDamageRate5", ParamFieldType.F32, 0xD4, null, null, null, "部位5ダメージ倍率") { it.partsDamageRate5 },
    ParamField("partsDamageRate6", ParamFieldType.F32, 0xD8, null, null, null, "部位6ダメージ倍率") { it.partsDamageRate6 },
    ParamField("partsDamageRate7", ParamFieldType.F32, 0xDC, null, null, null, "部位7ダメージ倍率") { it.partsDamageRate7 },
    ParamField("partsDamageRate8", ParamFieldType.F32, 0xE0, null, null, null, "部位8ダメージ倍率") { it.partsDamageRate8 },
    ParamField("weakPartsDamageRate", ParamFieldType.F32, 0xE4, null, null, null, "弱点部位ダメージ倍率") { it.weakPartsDamageRate },
    ParamField("superArmorRecoverCorrection", ParamFieldType.F32, 0xE8, null, null, null, "SA回復時間補正値") { it.superArmorRecoverCorrection },
    ParamField("superArmorBrakeKnockbackDist", ParamFieldType.F32, 0xEC, null, null, null, "SAブレイク時ノックバック距離") { it.superArmorBrakeKnockbackDist },
    ParamField("stamina", ParamFieldType.U16, 0xF0, null, null, null, "スタミナ") { it.stamina },
    ParamField("staminaRecoverBaseVel", ParamFieldType.U16, 0xF2, null, null, null, "スタミナ回復基本速度[point/s]") { it.staminaRecoverBaseVel },
    ParamField("def_phys", ParamFieldType.U16, 0xF4, null, null, null, "物理防御力") { it.def_phys },
    ParamField("def_slash", ParamFieldType.S16, 0xF6, null, null, null, "斬撃防御力[％]") { it.def_slash },
    ParamField("def_blow", ParamFieldType.S16, 0xF8, null, null, null, "打撃防御力[％]") { it.def_blow },
    ParamField("def_thrust", ParamFieldType.S16, 0xFA, null, null, null, "刺突防御力[％]") { it.def_thrust },
    ParamField("def_mag", ParamFieldType.U16, 0xFC, null, null, null, "魔法防御力") { it.def_mag },
    ParamField("def_fire", ParamFieldType.U16, 0xFE, null, null, null, "炎防御力") { it.def_fire },
    ParamField("def_thunder", ParamFieldType.U16, 0x100, null, null, null, "電撃防御力") { it.def_thunder },
    ParamField("defFlickPower", ParamFieldType.U16, 0x102, null, null, null, "はじき防御力") { it.defFlickPower },
    ParamField("resist_poison", ParamFieldType.U16, 0x104, null, null, null, "毒耐性") { it.resist_poison },
    ParamField("resist_desease", ParamFieldType.U16, 0x106, null, null, null, "疫病耐性") { it.resist_desease },
    ParamField("resist_blood", ParamFieldType.U16, 0x108, null, null, null, "出血耐性") { it.resist_blood },
    ParamField("resist_curse", ParamFieldType.U16, 0x10A, null, null, null, "呪耐性") { it.resist_curse },
    ParamField("ghostModelId", ParamFieldType.S16, 0x10C, null, null, null, "徘徊ゴースト時差し替えモデルID") { it.ghostModelId },
    ParamField("normalChangeResouceId", ParamFieldType.S16, 0x10E, null, null, null, "通常時差し替えリソースID") { it.normalChangeResouceId },
    ParamField("guardAngle", ParamFieldType.S16, 0x110, null, null, null, "ガード範囲[deg]") { it.guardAngle },
    ParamField("slashGuardCutRate", ParamFieldType.S16, 0x112, null, null, null, "斬撃攻撃カット率[％]") { it.slashGuardCutRate },
    ParamField("blowGuardCutRate", ParamFieldType.S16, 0x114, null, null, null, "打撃攻撃カット率[％]") { it.blowGuardCutRate },
    ParamField("thrustGuardCutRate", ParamFieldType.S16, 0x116, null, null, null, "刺突攻撃カット率[％]") { it.thrustGuardCutRate },
    ParamField("superArmorDurability", ParamFieldType.S16, 0x118, null, null, null, "SA耐久力") { it.superArmorDurability },
    ParamField("normalChangeTexChrId", ParamFieldType.S16, 0x11A, null, null, null, "通常時差し替えテクスチャキャラID") { it.normalChangeTexChrId },
    ParamField("dropType", ParamFieldType.U16, 0x11C, null, null, "NPC_ITEMDROP_TYPE", "ドロップアイテムの表示形式") { it.dropType },
    ParamField("knockbackRate", ParamFieldType.U8, 0x11E, null, null, null, "ノックバックカット率[％]") { it.knockbackRate },
    ParamField("knockbackParamId", ParamFieldType.U8, 0x11F, null, null, null, "ノックバックパラメータID") { it.knockbackParamId },
    ParamField("fallDamageDump", ParamFieldType.U8, 0x120, null, null, null, "落下ダメージ軽減補正[％]") { it.fallDamageDump },
    ParamField("staminaGuardDef", ParamFieldType.U8, 0x121, null, null, null, "スタミナ攻撃カット率[％]") { it.staminaGuardDef },
    ParamField("pcAttrB", ParamFieldType.U8, 0x122, null, null, null, "PC-黒") { it.pcAttrB },
    ParamField("pcAttrW", ParamFieldType.U8, 0x123, null, null, null, "PC-白") { it.pcAttrW },
    ParamField("pcAttrL", ParamFieldType.U8, 0x124, null, null, null, "PC-左") { it.pcAttrL },
    ParamField("pcAttrR", ParamFieldType.U8, 0x125, null, null, null, "PC-右") { it.pcAttrR },
    ParamField("areaAttrB", ParamFieldType.U8, 0x126, null, null, null, "エリア-黒") { it.areaAttrB },
    ParamField("areaAttrW", ParamFieldType.U8, 0x127, null, null, null, "エリア-白") { it.areaAttrW },
    ParamField("areaAttrL", ParamFieldType.U8, 0x128, null, null, null, "エリア-左") { it.areaAttrL },
    ParamField("areaAttrR", ParamFieldType.U8, 0x129, null, null, null, "エリア-右") { it.areaAttrR },
    ParamField("mpRecoverBaseVel", ParamFieldType.U8, 0x12A, null, null, null, "MP回復基本速度[％/s]") { it.mpRecoverBaseVel },
    ParamField("flickDamageCutRate", ParamFieldType.U8, 0x12B, null, null, null, "はじき時ダメージ減衰率[%]") { it.flickDamageCutRate },
    ParamField("defaultLodParamId", ParamFieldType.S8, 0x12C, null, null, null, "デフォルトLODパラムID") { it.defaultLodParamId },
    ParamField("drawType", ParamFieldType.U8, 0x12D, null, null, "NPC_DRAW_TYPE", "描画タイプ") { it.drawType },
    ParamField("npcType", ParamFieldType.U8, 0x12E, null, null, "NPC_TYPE", "NPCタイプ") { it.npcType },
    ParamField("teamType", ParamFieldType.U8, 0x12F, null, null, "NPC_TEMA_TYPE", "チームタイプ") { it.teamType },
    ParamField("moveType", ParamFieldType.U8, 0x130, null, null, "NPC_MOVE_TYPE", "移動タイプ") { it.moveType },
    ParamField("lockDist", ParamFieldType.U8, 0x131, null, null, null, "ロック距離") { it.lockDist },
    ParamField("material", ParamFieldType.U8, 0x132, null, null, "WEP_MATERIAL_DEF", "防御材質【SE】") { it.material },
    ParamField("materialSfx", ParamFieldType.U8, 0x133, null, null, "WEP_MATERIAL_DEF_SFX", "防御材質【SFX】") { it.materialSfx },
    ParamField("material_Weak", ParamFieldType.U8, 0x134, null, null, "WEP_MATERIAL_DEF", "防御材質【SE】") { it.material_Weak },
    ParamField("materialSfx_Weak", ParamFieldType.U8, 0x135, null, null, "WEP_MATERIAL_DEF_SFX", "防御材質【SFX】") { it.materialSfx_Weak },
    ParamField("partsDamageType", ParamFieldType.U8, 0x136, null, null, "ATK_PARAM_PARTSDMGTYPE", "部位ダメージ適用攻撃") { it.partsDamageType },
    ParamField("maxUndurationAng", ParamFieldType.U8, 0x137, null, null, null, "起伏にあわせる最大角度") { it.maxUndurationAng },
    ParamField("guardLevel", ParamFieldType.S8, 0x138, null, null, null, "ガードレベル") { it.guardLevel },
    ParamField("burnSfxType", ParamFieldType.U8, 0x139, null, null, "NPC_BURN_TYPE", "燃焼SFXタイプ") { it.burnSfxType },
    ParamField("poisonGuardResist", ParamFieldType.S8, 0x13A, null, null, null, "毒耐性カット率[％]") { it.poisonGuardResist },
    ParamField("diseaseGuardResist", ParamFieldType.S8, 0x13B, null, null, null, "疫病攻撃カット率[％]") { it.diseaseGuardResist },
    ParamField("bloodGuardResist", ParamFieldType.S8, 0x13C, null, null, null, "出血攻撃カット率[％]") { it.bloodGuardResist },
    ParamField("curseGuardResist", ParamFieldType.S8, 0x13D, null, null, null, "呪攻撃カット率[％]") { it.curseGuardResist },
    ParamField("parryAttack", ParamFieldType.U8, 0x13E, null, null, null, "パリィ攻撃力") { it.parryAttack },
    ParamField("parryDefence", ParamFieldType.U8, 0x13F, null, null, null, "パリィ防御力") { it.parryDefence },
    ParamField("sfxSize", ParamFieldType.U8, 0x140, null, null, "NPC_SFX_SIZE", "SFXサイズ") { it.sfxSize },
    ParamField("pushOutCamRegionRadius", ParamFieldType.U8, 0x141, null, null, null, "カメラ押し出し領域半径[m]") { it.pushOutCamRegionRadius },
    ParamField("hitStopType", ParamFieldType.U8, 0x142, null, null, "NPC_HITSTOP_TYPE", "ヒットストップするか") { it.hitStopType },
    ParamField("ladderEndChkOffsetTop", ParamFieldType.U8, 0x143, null, null, null, "はしご上終端オフセット[1/10m]") { it.ladderEndChkOffsetTop },
    ParamField("ladderEndChkOffsetLow", ParamFieldType.U8, 0x144, null, null, null, "はしご下終端オフセット[1/10m]") { it.ladderEndChkOffsetLow },
    ParamField("useRagdollCamHit", ParamFieldType.U8, 0x145, 0, 1, "NPC_BOOL", "カメラヒットあたりラグドール") { it.useRagdollCamHit },
    ParamField("disableClothRigidHit", ParamFieldType.U8, 0x145, 1, 1, "NPC_BOOL", "クロスリジッドヒットを無効") { it.disableClothRigidHit },
    ParamField("useRagdoll", ParamFieldType.U8, 0x145, 2, 1, "NPC_BOOL", "あたりラグドール") { it.useRagdoll },
    ParamField("isDemon", ParamFieldType.U8, 0x145, 3, 1, "NPC_BOOL", "デーモンか") { it.isDemon },
    ParamField("isGhost", ParamFieldType.U8, 0x145, 4, 1, "NPC_BOOL", "霊体か") { it.isGhost },
    ParamField("isNoDamageMotion", ParamFieldType.U8, 0x145, 5, 1, "NPC_BOOL", "ダメージ0のときにダメージモーションなしか") { it.isNoDamageMotion },
    ParamField("isUnduration", ParamFieldType.U8, 0x145, 6, 1, "NPC_BOOL", "起伏に角度をあわせるか") { it.isUnduration },
    ParamField("isChangeWanderGhost", ParamFieldType.U8, 0x145, 7, 1, "NPC_BOOL", "徘徊ゴーストになるか") { it.isChangeWanderGhost },
    ParamField("modelDispMask0", ParamFieldType.U8, 0x146, 0, 1, null, "モデル表示マスク0") { it.modelDispMask0 },
    ParamField("modelDispMask1", ParamFieldType.U8, 0x146, 1, 1, null, "モデル表示マスク1") { it.modelDispMask1 },
    ParamField("modelDispMask2", ParamFieldType.U8, 0x146, 2, 1, null, "モデル表示マスク2") { it.modelDispMask2 },
    ParamField("modelDispMask3", ParamFieldType.U8, 0x146, 3, 1, null, "モデル表示マスク3") { it.modelDispMask3 },
    ParamField("modelDispMask4", ParamFieldType.U8, 0x146, 4, 1, null, "モデル表示マスク4") { it.modelDispMask4 },
    ParamField("modelDispMask5", ParamFieldType.U8, 0x146, 5, 1, null, "モデル表示マスク5") { it.modelDispMask5 },
    ParamField("modelDispMask6", ParamFieldType.U8, 0x146, 6, 1, null, "モデル表示マスク6") { it.modelDispMask6 },
    ParamField("modelDispMask7", ParamFieldType.U8, 0x146, 7, 1, null, "モデル表示マスク7") { it.modelDispMask7 },
    ParamField("modelDispMask8", ParamFieldType.U8, 0x147, 0, 1, null, "モデル表示マスク8") { it.modelDispMask8 },
    ParamField("modelDispMask9", ParamFieldType.U8, 0x147, 1, 1, null, "モデル表示マスク9") { it.modelDispMask9 },
    ParamField("modelDispMask10", ParamFieldType.U8, 0x147, 2, 1, null, "モデル表示マスク10") { it.modelDispMask10 },
    ParamField("modelDispMask11", ParamFieldType.U8, 0x147, 3, 1, null, "モデル表示マスク11") { it.modelDispMask11 },
    ParamField("modelDispMask12", ParamFieldType.U8, 0x147, 4, 1, null, "モデル表示マスク12") { it.modelDispMask12 },
    ParamField("modelDispMask13", ParamFieldType.U8, 0x147, 5, 1, null, "モデル表示マスク13") { it.modelDispMask13 },
    ParamField("modelDispMask14", ParamFieldType.U8, 0x147, 6, 1, null, "モデル表示マスク14") { it.modelDispMask14 },
    ParamField("modelDispMask15", ParamFieldType.U8, 0x147, 7, 1, null, "モデル表示マスク15") { it.modelDispMask15 },
    ParamField("isEnableNeckTurn", ParamFieldType.U8, 0x148, 0, 1, null, "首振り有効にするか") { it.isEnableNeckTurn },
    ParamField("disableRespawn", ParamFieldType.U8, 0x148, 1, 1, null, "リスポン禁止か") { it.disableRespawn },
    ParamField("isMoveAnimWait", ParamFieldType.U8, 0x148, 2, 1, null, "移動アニメを待つか") { it.isMoveAnimWait },
    ParamField("isCrowd", ParamFieldType.U8, 0x148, 3, 1, "NPC_BOOL", "群集用処理軽減するか") { it.isCrowd },
    ParamField("isWeakSaint", ParamFieldType.U8, 0x148, 4, 1, "NPC_BOOL", "神聖弱点か") { it.isWeakSaint },
    ParamField("isWeakA", ParamFieldType.U8, 0x148, 5, 1, "NPC_BOOL", "弱点Aか") { it.isWeakA },
    ParamField("isWeakB", ParamFieldType.U8, 0x148, 6, 1, "NPC_BOOL", "弱点Bか") { it.isWeakB },
    ParamField("vowType", ParamFieldType.U8, 0x149, 0, 3, null, "誓約") { it.vowType },
    ParamField("disableInitializeDead", ParamFieldType.U8, 0x149, 3, 1, "NPC_BOOL", "初期死亡しない") { it.disableInitializeDead }
)
