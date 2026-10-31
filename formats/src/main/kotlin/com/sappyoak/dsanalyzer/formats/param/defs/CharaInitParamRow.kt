// GENERATED FILE - do not edit. Regenerate with:
//
//     ./gradlew :tools:generateParamDefs -Pptde=<DATA directory> -Pdsr=<DATA directory>
//
// Source: paramdef.paramdefbnd, CharaInitParam.paramdef, read from the installation

@file:Suppress("SpellCheckingInspection")

package com.sappyoak.dsanalyzer.formats.param.defs

import com.sappyoak.dsanalyzer.formats.param.ParamRow
import com.sappyoak.dsanalyzer.formats.param.ParamRowData
import com.sappyoak.dsanalyzer.formats.param.ParamRowType

/** `CHARACTER_INIT_PARAM`: PrepareToDie 0xF0, Remastered 0xF0 a row, data version 1 */
public class CharaInitParamRow(row: ParamRowData) : ParamRow(row) {
    /** `f32` at 0x0 */
    public val baseRec_mp: Float get() = bytes.f32(0x0)

    /** `f32` at 0x4 */
    public val baseRec_sp: Float get() = bytes.f32(0x4)

    /** `f32` at 0x8 */
    public val red_Falldam: Float get() = bytes.f32(0x8)

    /** `s32` at 0xC */
    public val soul: Int get() = bytes.s32(0xC)

    /** `s32` at 0x10 */
    public val equip_Wep_Right: Int get() = bytes.s32(0x10)

    /** `s32` at 0x14 */
    public val equip_Subwep_Right: Int get() = bytes.s32(0x14)

    /** `s32` at 0x18 */
    public val equip_Wep_Left: Int get() = bytes.s32(0x18)

    /** `s32` at 0x1C */
    public val equip_Subwep_Left: Int get() = bytes.s32(0x1C)

    /** `s32` at 0x20 */
    public val equip_Helm: Int get() = bytes.s32(0x20)

    /** `s32` at 0x24 */
    public val equip_Armer: Int get() = bytes.s32(0x24)

    /** `s32` at 0x28 */
    public val equip_Gaunt: Int get() = bytes.s32(0x28)

    /** `s32` at 0x2C */
    public val equip_Leg: Int get() = bytes.s32(0x2C)

    /** `s32` at 0x30 */
    public val equip_Arrow: Int get() = bytes.s32(0x30)

    /** `s32` at 0x34 */
    public val equip_Bolt: Int get() = bytes.s32(0x34)

    /** `s32` at 0x38 */
    public val equip_SubArrow: Int get() = bytes.s32(0x38)

    /** `s32` at 0x3C */
    public val equip_SubBolt: Int get() = bytes.s32(0x3C)

    /** `s32` at 0x40 */
    public val equip_Accessory01: Int get() = bytes.s32(0x40)

    /** `s32` at 0x44 */
    public val equip_Accessory02: Int get() = bytes.s32(0x44)

    /** `s32` at 0x48 */
    public val equip_Accessory03: Int get() = bytes.s32(0x48)

    /** `s32` at 0x4C */
    public val equip_Accessory04: Int get() = bytes.s32(0x4C)

    /** `s32` at 0x50 */
    public val equip_Accessory05: Int get() = bytes.s32(0x50)

    /** `s32` at 0x54 */
    public val equip_Skill_01: Int get() = bytes.s32(0x54)

    /** `s32` at 0x58 */
    public val equip_Skill_02: Int get() = bytes.s32(0x58)

    /** `s32` at 0x5C */
    public val equip_Skill_03: Int get() = bytes.s32(0x5C)

    /** `s32` at 0x60 */
    public val equip_Spell_01: Int get() = bytes.s32(0x60)

    /** `s32` at 0x64 */
    public val equip_Spell_02: Int get() = bytes.s32(0x64)

    /** `s32` at 0x68 */
    public val equip_Spell_03: Int get() = bytes.s32(0x68)

    /** `s32` at 0x6C */
    public val equip_Spell_04: Int get() = bytes.s32(0x6C)

    /** `s32` at 0x70 */
    public val equip_Spell_05: Int get() = bytes.s32(0x70)

    /** `s32` at 0x74 */
    public val equip_Spell_06: Int get() = bytes.s32(0x74)

    /** `s32` at 0x78 */
    public val equip_Spell_07: Int get() = bytes.s32(0x78)

    /** `s32` at 0x7C */
    public val item_01: Int get() = bytes.s32(0x7C)

    /** `s32` at 0x80 */
    public val item_02: Int get() = bytes.s32(0x80)

    /** `s32` at 0x84 */
    public val item_03: Int get() = bytes.s32(0x84)

    /** `s32` at 0x88 */
    public val item_04: Int get() = bytes.s32(0x88)

    /** `s32` at 0x8C */
    public val item_05: Int get() = bytes.s32(0x8C)

    /** `s32` at 0x90 */
    public val item_06: Int get() = bytes.s32(0x90)

    /** `s32` at 0x94 */
    public val item_07: Int get() = bytes.s32(0x94)

    /** `s32` at 0x98 */
    public val item_08: Int get() = bytes.s32(0x98)

    /** `s32` at 0x9C */
    public val item_09: Int get() = bytes.s32(0x9C)

    /** `s32` at 0xA0 */
    public val item_10: Int get() = bytes.s32(0xA0)

    /** `s32` at 0xA4 */
    public val npcPlayerFaceGenId: Int get() = bytes.s32(0xA4)

    /** `s32` at 0xA8 */
    public val npcPlayerThinkId: Int get() = bytes.s32(0xA8)

    /** `u16` at 0xAC */
    public val baseHp: Int get() = bytes.u16(0xAC)

    /** `u16` at 0xAE */
    public val baseMp: Int get() = bytes.u16(0xAE)

    /** `u16` at 0xB0 */
    public val baseSp: Int get() = bytes.u16(0xB0)

    /** `u16` at 0xB2 */
    public val arrowNum: Int get() = bytes.u16(0xB2)

    /** `u16` at 0xB4 */
    public val boltNum: Int get() = bytes.u16(0xB4)

    /** `u16` at 0xB6 */
    public val subArrowNum: Int get() = bytes.u16(0xB6)

    /** `u16` at 0xB8 */
    public val subBoltNum: Int get() = bytes.u16(0xB8)

    /** `s16` at 0xBA */
    public val qWC_sb: Int get() = bytes.s16(0xBA)

    /** `s16` at 0xBC */
    public val qWC_mw: Int get() = bytes.s16(0xBC)

    /** `s16` at 0xBE */
    public val qWC_cd: Int get() = bytes.s16(0xBE)

    /** `s16` at 0xC0 */
    public val soulLv: Int get() = bytes.s16(0xC0)

    /** `u8` at 0xC2 */
    public val baseVit: Int get() = bytes.u8(0xC2)

    /** `u8` at 0xC3 */
    public val baseWil: Int get() = bytes.u8(0xC3)

    /** `u8` at 0xC4 */
    public val baseEnd: Int get() = bytes.u8(0xC4)

    /** `u8` at 0xC5 */
    public val baseStr: Int get() = bytes.u8(0xC5)

    /** `u8` at 0xC6 */
    public val baseDex: Int get() = bytes.u8(0xC6)

    /** `u8` at 0xC7 */
    public val baseMag: Int get() = bytes.u8(0xC7)

    /** `u8` at 0xC8 */
    public val baseFai: Int get() = bytes.u8(0xC8)

    /** `u8` at 0xC9 */
    public val baseLuc: Int get() = bytes.u8(0xC9)

    /** `u8` at 0xCA */
    public val baseHeroPoint: Int get() = bytes.u8(0xCA)

    /** `u8` at 0xCB */
    public val baseDurability: Int get() = bytes.u8(0xCB)

    /** `u8` at 0xCC */
    public val itemNum_01: Int get() = bytes.u8(0xCC)

    /** `u8` at 0xCD */
    public val itemNum_02: Int get() = bytes.u8(0xCD)

    /** `u8` at 0xCE */
    public val itemNum_03: Int get() = bytes.u8(0xCE)

    /** `u8` at 0xCF */
    public val itemNum_04: Int get() = bytes.u8(0xCF)

    /** `u8` at 0xD0 */
    public val itemNum_05: Int get() = bytes.u8(0xD0)

    /** `u8` at 0xD1 */
    public val itemNum_06: Int get() = bytes.u8(0xD1)

    /** `u8` at 0xD2 */
    public val itemNum_07: Int get() = bytes.u8(0xD2)

    /** `u8` at 0xD3 */
    public val itemNum_08: Int get() = bytes.u8(0xD3)

    /** `u8` at 0xD4 */
    public val itemNum_09: Int get() = bytes.u8(0xD4)

    /** `u8` at 0xD5 */
    public val itemNum_10: Int get() = bytes.u8(0xD5)

    /** `s8` at 0xD6 */
    public val bodyScaleHead: Int get() = bytes.s8(0xD6)

    /** `s8` at 0xD7 */
    public val bodyScaleBreast: Int get() = bytes.s8(0xD7)

    /** `s8` at 0xD8 */
    public val bodyScaleAbdomen: Int get() = bytes.s8(0xD8)

    /** `s8` at 0xD9 */
    public val bodyScaleArm: Int get() = bytes.s8(0xD9)

    /** `s8` at 0xDA */
    public val bodyScaleLeg: Int get() = bytes.s8(0xDA)

    /** `s8` at 0xDB */
    public val gestureId0: Int get() = bytes.s8(0xDB)

    /** `s8` at 0xDC */
    public val gestureId1: Int get() = bytes.s8(0xDC)

    /** `s8` at 0xDD */
    public val gestureId2: Int get() = bytes.s8(0xDD)

    /** `s8` at 0xDE */
    public val gestureId3: Int get() = bytes.s8(0xDE)

    /** `s8` at 0xDF */
    public val gestureId4: Int get() = bytes.s8(0xDF)

    /** `s8` at 0xE0 */
    public val gestureId5: Int get() = bytes.s8(0xE0)

    /** `s8` at 0xE1 */
    public val gestureId6: Int get() = bytes.s8(0xE1)

    /** `u8` at 0xE2, `NPC_TYPE` */
    public val npcPlayerType: Int get() = bytes.u8(0xE2)

    /** `u8` at 0xE3, `NPC_DRAW_TYPE` */
    public val npcPlayerDrawType: Int get() = bytes.u8(0xE3)

    /** `u8` at 0xE4, `CHARACTER_INIT_SEX` */
    public val npcPlayerSex: Int get() = bytes.u8(0xE4)

    /** bits 0-3 of the `u8` at 0xE5, `CHRINIT_VOW_TYPE` */
    public val vowType: Int get() = bytes.bits(0xE5, 1, 0, 4)

    public companion object : ParamRowType<CharaInitParamRow>("CHARACTER_INIT_PARAM", 0xF0, 1, ::CharaInitParamRow)
}
