// GENERATED FILE - do not edit. Regenerate with:
//
//     ./gradlew :tools:generateParamDefs -Pptde=<DATA directory> -Pdsr=<DATA directory>
//
// Source: paramdef.paramdefbnd, NpcParam.paramdef, read from the installation

@file:Suppress("SpellCheckingInspection")

package com.sappyoak.dsanalyzer.formats.param.defs

import com.sappyoak.dsanalyzer.formats.param.ParamRow
import com.sappyoak.dsanalyzer.formats.param.ParamRowData
import com.sappyoak.dsanalyzer.formats.param.ParamRowType

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

    public companion object : ParamRowType<NpcParamRow>("NPC_PARAM_ST", 0x150, 3, ::NpcParamRow)
}
