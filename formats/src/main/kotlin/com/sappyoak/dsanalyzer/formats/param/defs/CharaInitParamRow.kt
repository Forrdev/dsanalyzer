// GENERATED FILE - do not edit. Regenerate with:
//
//     ./gradlew :tools:generateParamDefs -Pptde=<DATA directory> -Pdsr=<DATA directory>
//
// Source: paramdef.paramdefbnd, CharaInitParam.paramdef, read from the installation

@file:Suppress("SpellCheckingInspection")

package com.sappyoak.dsanalyzer.formats.param.defs

import com.sappyoak.dsanalyzer.formats.param.ParamField
import com.sappyoak.dsanalyzer.formats.param.ParamRow
import com.sappyoak.dsanalyzer.formats.param.ParamRowData
import com.sappyoak.dsanalyzer.formats.param.ParamRowType
import com.sappyoak.dsanalyzer.formats.param.paramdef.ParamFieldType

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

    public companion object : ParamRowType<CharaInitParamRow>("CHARACTER_INIT_PARAM", 0xF0, 1, FIELDS, ::CharaInitParamRow)
}

private val FIELDS: List<ParamField<CharaInitParamRow>> = listOf<ParamField<CharaInitParamRow>>(
    ParamField("baseRec_mp", ParamFieldType.F32, 0x0, null, null, null, "ＭＰ回復速度基本値[s]") { it.baseRec_mp },
    ParamField("baseRec_sp", ParamFieldType.F32, 0x4, null, null, null, "スタミナ回復速度基本値[s]") { it.baseRec_sp },
    ParamField("red_Falldam", ParamFieldType.F32, 0x8, null, null, null, "落下ダメージ軽減補正[%]") { it.red_Falldam },
    ParamField("soul", ParamFieldType.S32, 0xC, null, null, null, "初期ソウル") { it.soul },
    ParamField("equip_Wep_Right", ParamFieldType.S32, 0x10, null, null, null, "右手武器") { it.equip_Wep_Right },
    ParamField("equip_Subwep_Right", ParamFieldType.S32, 0x14, null, null, null, "右手予備武器") { it.equip_Subwep_Right },
    ParamField("equip_Wep_Left", ParamFieldType.S32, 0x18, null, null, null, "左手武器") { it.equip_Wep_Left },
    ParamField("equip_Subwep_Left", ParamFieldType.S32, 0x1C, null, null, null, "左手予備武器") { it.equip_Subwep_Left },
    ParamField("equip_Helm", ParamFieldType.S32, 0x20, null, null, null, "頭防具") { it.equip_Helm },
    ParamField("equip_Armer", ParamFieldType.S32, 0x24, null, null, null, "胴体防具") { it.equip_Armer },
    ParamField("equip_Gaunt", ParamFieldType.S32, 0x28, null, null, null, "腕防具") { it.equip_Gaunt },
    ParamField("equip_Leg", ParamFieldType.S32, 0x2C, null, null, null, "脚防具") { it.equip_Leg },
    ParamField("equip_Arrow", ParamFieldType.S32, 0x30, null, null, null, "矢") { it.equip_Arrow },
    ParamField("equip_Bolt", ParamFieldType.S32, 0x34, null, null, null, "ボルト") { it.equip_Bolt },
    ParamField("equip_SubArrow", ParamFieldType.S32, 0x38, null, null, null, "予備矢") { it.equip_SubArrow },
    ParamField("equip_SubBolt", ParamFieldType.S32, 0x3C, null, null, null, "予備ボルト") { it.equip_SubBolt },
    ParamField("equip_Accessory01", ParamFieldType.S32, 0x40, null, null, null, "装飾品1") { it.equip_Accessory01 },
    ParamField("equip_Accessory02", ParamFieldType.S32, 0x44, null, null, null, "装飾品2") { it.equip_Accessory02 },
    ParamField("equip_Accessory03", ParamFieldType.S32, 0x48, null, null, null, "装飾品3") { it.equip_Accessory03 },
    ParamField("equip_Accessory04", ParamFieldType.S32, 0x4C, null, null, null, "装飾品4") { it.equip_Accessory04 },
    ParamField("equip_Accessory05", ParamFieldType.S32, 0x50, null, null, null, "装飾品5") { it.equip_Accessory05 },
    ParamField("equip_Skill_01", ParamFieldType.S32, 0x54, null, null, null, "スキル1") { it.equip_Skill_01 },
    ParamField("equip_Skill_02", ParamFieldType.S32, 0x58, null, null, null, "スキル2") { it.equip_Skill_02 },
    ParamField("equip_Skill_03", ParamFieldType.S32, 0x5C, null, null, null, "スキル3") { it.equip_Skill_03 },
    ParamField("equip_Spell_01", ParamFieldType.S32, 0x60, null, null, null, "魔法・奇跡1") { it.equip_Spell_01 },
    ParamField("equip_Spell_02", ParamFieldType.S32, 0x64, null, null, null, "魔法・奇跡2") { it.equip_Spell_02 },
    ParamField("equip_Spell_03", ParamFieldType.S32, 0x68, null, null, null, "魔法・奇跡3") { it.equip_Spell_03 },
    ParamField("equip_Spell_04", ParamFieldType.S32, 0x6C, null, null, null, "魔法・奇跡4") { it.equip_Spell_04 },
    ParamField("equip_Spell_05", ParamFieldType.S32, 0x70, null, null, null, "魔法・奇跡5") { it.equip_Spell_05 },
    ParamField("equip_Spell_06", ParamFieldType.S32, 0x74, null, null, null, "魔法・奇跡6") { it.equip_Spell_06 },
    ParamField("equip_Spell_07", ParamFieldType.S32, 0x78, null, null, null, "魔法・奇跡7") { it.equip_Spell_07 },
    ParamField("item_01", ParamFieldType.S32, 0x7C, null, null, null, "アイテム01") { it.item_01 },
    ParamField("item_02", ParamFieldType.S32, 0x80, null, null, null, "アイテム02") { it.item_02 },
    ParamField("item_03", ParamFieldType.S32, 0x84, null, null, null, "アイテム03") { it.item_03 },
    ParamField("item_04", ParamFieldType.S32, 0x88, null, null, null, "アイテム04") { it.item_04 },
    ParamField("item_05", ParamFieldType.S32, 0x8C, null, null, null, "アイテム05") { it.item_05 },
    ParamField("item_06", ParamFieldType.S32, 0x90, null, null, null, "アイテム06") { it.item_06 },
    ParamField("item_07", ParamFieldType.S32, 0x94, null, null, null, "アイテム07") { it.item_07 },
    ParamField("item_08", ParamFieldType.S32, 0x98, null, null, null, "アイテム08") { it.item_08 },
    ParamField("item_09", ParamFieldType.S32, 0x9C, null, null, null, "アイテム09") { it.item_09 },
    ParamField("item_10", ParamFieldType.S32, 0xA0, null, null, null, "アイテム10") { it.item_10 },
    ParamField("npcPlayerFaceGenId", ParamFieldType.S32, 0xA4, null, null, null, "フェイスジェンパラメータID") { it.npcPlayerFaceGenId },
    ParamField("npcPlayerThinkId", ParamFieldType.S32, 0xA8, null, null, null, "NPCプレイヤーの思考ID") { it.npcPlayerThinkId },
    ParamField("baseHp", ParamFieldType.U16, 0xAC, null, null, null, "ＨＰ基本値") { it.baseHp },
    ParamField("baseMp", ParamFieldType.U16, 0xAE, null, null, null, "ＭＰ基本値") { it.baseMp },
    ParamField("baseSp", ParamFieldType.U16, 0xB0, null, null, null, "スタミナ基本値") { it.baseSp },
    ParamField("arrowNum", ParamFieldType.U16, 0xB2, null, null, null, "矢の所持数") { it.arrowNum },
    ParamField("boltNum", ParamFieldType.U16, 0xB4, null, null, null, "ボルトの所持数") { it.boltNum },
    ParamField("subArrowNum", ParamFieldType.U16, 0xB6, null, null, null, "予備矢の所持数") { it.subArrowNum },
    ParamField("subBoltNum", ParamFieldType.U16, 0xB8, null, null, null, "予備ボルトの所持数") { it.subBoltNum },
    ParamField("qWC_sb", ParamFieldType.S16, 0xBA, null, null, null, "傾向係数　S-B軸") { it.qWC_sb },
    ParamField("qWC_mw", ParamFieldType.S16, 0xBC, null, null, null, "傾向係数　M-W軸") { it.qWC_mw },
    ParamField("qWC_cd", ParamFieldType.S16, 0xBE, null, null, null, "傾向係数　C-D軸") { it.qWC_cd },
    ParamField("soulLv", ParamFieldType.S16, 0xC0, null, null, null, "ソウルLv") { it.soulLv },
    ParamField("baseVit", ParamFieldType.U8, 0xC2, null, null, null, "体力") { it.baseVit },
    ParamField("baseWil", ParamFieldType.U8, 0xC3, null, null, null, "精神") { it.baseWil },
    ParamField("baseEnd", ParamFieldType.U8, 0xC4, null, null, null, "頑強") { it.baseEnd },
    ParamField("baseStr", ParamFieldType.U8, 0xC5, null, null, null, "筋力") { it.baseStr },
    ParamField("baseDex", ParamFieldType.U8, 0xC6, null, null, null, "俊敏") { it.baseDex },
    ParamField("baseMag", ParamFieldType.U8, 0xC7, null, null, null, "魔力") { it.baseMag },
    ParamField("baseFai", ParamFieldType.U8, 0xC8, null, null, null, "信仰") { it.baseFai },
    ParamField("baseLuc", ParamFieldType.U8, 0xC9, null, null, null, "運") { it.baseLuc },
    ParamField("baseHeroPoint", ParamFieldType.U8, 0xCA, null, null, null, "人間性") { it.baseHeroPoint },
    ParamField("baseDurability", ParamFieldType.U8, 0xCB, null, null, null, "耐久力") { it.baseDurability },
    ParamField("itemNum_01", ParamFieldType.U8, 0xCC, null, null, null, "アイテム01の所持数") { it.itemNum_01 },
    ParamField("itemNum_02", ParamFieldType.U8, 0xCD, null, null, null, "アイテム02の所持数") { it.itemNum_02 },
    ParamField("itemNum_03", ParamFieldType.U8, 0xCE, null, null, null, "アイテム03の所持数") { it.itemNum_03 },
    ParamField("itemNum_04", ParamFieldType.U8, 0xCF, null, null, null, "アイテム個数04") { it.itemNum_04 },
    ParamField("itemNum_05", ParamFieldType.U8, 0xD0, null, null, null, "アイテム個数05") { it.itemNum_05 },
    ParamField("itemNum_06", ParamFieldType.U8, 0xD1, null, null, null, "アイテム個数06") { it.itemNum_06 },
    ParamField("itemNum_07", ParamFieldType.U8, 0xD2, null, null, null, "アイテム個数07") { it.itemNum_07 },
    ParamField("itemNum_08", ParamFieldType.U8, 0xD3, null, null, null, "アイテム個数08") { it.itemNum_08 },
    ParamField("itemNum_09", ParamFieldType.U8, 0xD4, null, null, null, "アイテム個数09") { it.itemNum_09 },
    ParamField("itemNum_10", ParamFieldType.U8, 0xD5, null, null, null, "アイテム個数10") { it.itemNum_10 },
    ParamField("bodyScaleHead", ParamFieldType.S8, 0xD6, null, null, null, "頭部") { it.bodyScaleHead },
    ParamField("bodyScaleBreast", ParamFieldType.S8, 0xD7, null, null, null, "胸部") { it.bodyScaleBreast },
    ParamField("bodyScaleAbdomen", ParamFieldType.S8, 0xD8, null, null, null, "腹部") { it.bodyScaleAbdomen },
    ParamField("bodyScaleArm", ParamFieldType.S8, 0xD9, null, null, null, "腕部") { it.bodyScaleArm },
    ParamField("bodyScaleLeg", ParamFieldType.S8, 0xDA, null, null, null, "脚部") { it.bodyScaleLeg },
    ParamField("gestureId0", ParamFieldType.S8, 0xDB, null, null, null, "ジェスチャーID0") { it.gestureId0 },
    ParamField("gestureId1", ParamFieldType.S8, 0xDC, null, null, null, "ジェスチャーID1") { it.gestureId1 },
    ParamField("gestureId2", ParamFieldType.S8, 0xDD, null, null, null, "ジェスチャーID2") { it.gestureId2 },
    ParamField("gestureId3", ParamFieldType.S8, 0xDE, null, null, null, "ジェスチャーID3") { it.gestureId3 },
    ParamField("gestureId4", ParamFieldType.S8, 0xDF, null, null, null, "ジェスチャーID4") { it.gestureId4 },
    ParamField("gestureId5", ParamFieldType.S8, 0xE0, null, null, null, "ジェスチャーID5") { it.gestureId5 },
    ParamField("gestureId6", ParamFieldType.S8, 0xE1, null, null, null, "ジェスチャーID6") { it.gestureId6 },
    ParamField("npcPlayerType", ParamFieldType.U8, 0xE2, null, null, "NPC_TYPE", "NPCプレイヤーのNPCタイプ") { it.npcPlayerType },
    ParamField("npcPlayerDrawType", ParamFieldType.U8, 0xE3, null, null, "NPC_DRAW_TYPE", "NPCプレイヤーの描画タイプ") { it.npcPlayerDrawType },
    ParamField("npcPlayerSex", ParamFieldType.U8, 0xE4, null, null, "CHARACTER_INIT_SEX", "NPCプレイヤーの性別") { it.npcPlayerSex },
    ParamField("vowType", ParamFieldType.U8, 0xE5, 0, 4, "CHRINIT_VOW_TYPE", "誓約") { it.vowType }
)
