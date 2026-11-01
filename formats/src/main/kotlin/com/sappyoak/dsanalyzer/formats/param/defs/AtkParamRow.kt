// GENERATED FILE - do not edit. Regenerate with:
//
//     ./gradlew :tools:generateParamDefs -Pptde=<DATA directory> -Pdsr=<DATA directory>
//
// Source: paramdef.paramdefbnd, AtkParam.paramdef, read from the installation

@file:Suppress("SpellCheckingInspection")

package com.sappyoak.dsanalyzer.formats.param.defs

import com.sappyoak.dsanalyzer.formats.param.ParamField
import com.sappyoak.dsanalyzer.formats.param.ParamRow
import com.sappyoak.dsanalyzer.formats.param.ParamRowData
import com.sappyoak.dsanalyzer.formats.param.ParamRowType
import com.sappyoak.dsanalyzer.formats.param.paramdef.ParamFieldType

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

    public companion object : ParamRowType<AtkParamRow>("ATK_PARAM_ST", 0x80, 1, FIELDS, ::AtkParamRow)
}

private val FIELDS: List<ParamField<AtkParamRow>> = listOf<ParamField<AtkParamRow>>(
    ParamField("hit0_Radius", ParamFieldType.F32, 0x0, null, null, null, "あたり0 半径") { it.hit0_Radius },
    ParamField("hit1_Radius", ParamFieldType.F32, 0x4, null, null, null, "あたり1 半径") { it.hit1_Radius },
    ParamField("hit2_Radius", ParamFieldType.F32, 0x8, null, null, null, "あたり2 半径") { it.hit2_Radius },
    ParamField("hit3_Radius", ParamFieldType.F32, 0xC, null, null, null, "あたり3 半径") { it.hit3_Radius },
    ParamField("knockbackDist", ParamFieldType.F32, 0x10, null, null, null, "ノックバック距離[m]") { it.knockbackDist },
    ParamField("hitStopTime", ParamFieldType.F32, 0x14, null, null, null, "ヒットストップ時間[s]") { it.hitStopTime },
    ParamField("spEffectId0", ParamFieldType.S32, 0x18, null, null, null, "特殊効果0") { it.spEffectId0 },
    ParamField("spEffectId1", ParamFieldType.S32, 0x1C, null, null, null, "特殊効果1") { it.spEffectId1 },
    ParamField("spEffectId2", ParamFieldType.S32, 0x20, null, null, null, "特殊効果2") { it.spEffectId2 },
    ParamField("spEffectId3", ParamFieldType.S32, 0x24, null, null, null, "特殊効果3") { it.spEffectId3 },
    ParamField("spEffectId4", ParamFieldType.S32, 0x28, null, null, null, "特殊効果4") { it.spEffectId4 },
    ParamField("hit0_DmyPoly1", ParamFieldType.S16, 0x2C, null, null, null, "あたり0 ダミポリ1") { it.hit0_DmyPoly1 },
    ParamField("hit1_DmyPoly1", ParamFieldType.S16, 0x2E, null, null, null, "あたり1 ダミポリ1") { it.hit1_DmyPoly1 },
    ParamField("hit2_DmyPoly1", ParamFieldType.S16, 0x30, null, null, null, "あたり2 ダミポリ1") { it.hit2_DmyPoly1 },
    ParamField("hit3_DmyPoly1", ParamFieldType.S16, 0x32, null, null, null, "あたり3 ダミポリ1") { it.hit3_DmyPoly1 },
    ParamField("hit0_DmyPoly2", ParamFieldType.S16, 0x34, null, null, null, "あたり0 ダミポリ2") { it.hit0_DmyPoly2 },
    ParamField("hit1_DmyPoly2", ParamFieldType.S16, 0x36, null, null, null, "あたり1 ダミポリ2") { it.hit1_DmyPoly2 },
    ParamField("hit2_DmyPoly2", ParamFieldType.S16, 0x38, null, null, null, "あたり2 ダミポリ2") { it.hit2_DmyPoly2 },
    ParamField("hit3_DmyPoly2", ParamFieldType.S16, 0x3A, null, null, null, "あたり3 ダミポリ2") { it.hit3_DmyPoly2 },
    ParamField("blowingCorrection", ParamFieldType.U16, 0x3C, null, null, null, "吹き飛ばし補正値") { it.blowingCorrection },
    ParamField("atkPhysCorrection", ParamFieldType.U16, 0x3E, null, null, null, "物理攻撃力補正値") { it.atkPhysCorrection },
    ParamField("atkMagCorrection", ParamFieldType.U16, 0x40, null, null, null, "魔法攻撃力補正値") { it.atkMagCorrection },
    ParamField("atkFireCorrection", ParamFieldType.U16, 0x42, null, null, null, "炎攻撃力補正値") { it.atkFireCorrection },
    ParamField("atkThunCorrection", ParamFieldType.U16, 0x44, null, null, null, "電撃攻撃力補正値") { it.atkThunCorrection },
    ParamField("atkStamCorrection", ParamFieldType.U16, 0x46, null, null, null, "スタミナ攻撃力補正値") { it.atkStamCorrection },
    ParamField("guardAtkRateCorrection", ParamFieldType.U16, 0x48, null, null, null, "はじき攻撃力補正値") { it.guardAtkRateCorrection },
    ParamField("guardBreakCorrection", ParamFieldType.U16, 0x4A, null, null, null, "はじき防御力補正値") { it.guardBreakCorrection },
    ParamField("atkThrowEscapeCorrection", ParamFieldType.U16, 0x4C, null, null, null, "投げ抜け攻撃力補正値") { it.atkThrowEscapeCorrection },
    ParamField("atkSuperArmorCorrection", ParamFieldType.U16, 0x4E, null, null, null, "SA攻撃力補正値") { it.atkSuperArmorCorrection },
    ParamField("atkPhys", ParamFieldType.U16, 0x50, null, null, null, "物理攻撃力") { it.atkPhys },
    ParamField("atkMag", ParamFieldType.U16, 0x52, null, null, null, "魔法攻撃力") { it.atkMag },
    ParamField("atkFire", ParamFieldType.U16, 0x54, null, null, null, "炎攻撃力") { it.atkFire },
    ParamField("atkThun", ParamFieldType.U16, 0x56, null, null, null, "電撃攻撃力") { it.atkThun },
    ParamField("atkStam", ParamFieldType.U16, 0x58, null, null, null, "スタミナ攻撃力") { it.atkStam },
    ParamField("guardAtkRate", ParamFieldType.U16, 0x5A, null, null, null, "はじき攻撃力") { it.guardAtkRate },
    ParamField("guardBreakRate", ParamFieldType.U16, 0x5C, null, null, null, "はじき防御力") { it.guardBreakRate },
    ParamField("atkSuperArmor", ParamFieldType.U16, 0x5E, null, null, null, "SA攻撃力") { it.atkSuperArmor },
    ParamField("atkThrowEscape", ParamFieldType.U16, 0x60, null, null, null, "投げ抜け攻撃力") { it.atkThrowEscape },
    ParamField("atkObj", ParamFieldType.U16, 0x62, null, null, null, "オブジェ攻撃力") { it.atkObj },
    ParamField("guardStaminaCutRate", ParamFieldType.S16, 0x64, null, null, null, "ガード時スタミナカット率補正") { it.guardStaminaCutRate },
    ParamField("guardRate", ParamFieldType.S16, 0x66, null, null, null, "ガード倍率") { it.guardRate },
    ParamField("throwTypeId", ParamFieldType.U16, 0x68, null, null, null, "投げタイプID") { it.throwTypeId },
    ParamField("hit0_hitType", ParamFieldType.U8, 0x6A, null, null, "ATK_PARAM_HIT_TYPE", "あたり0 部位") { it.hit0_hitType },
    ParamField("hit1_hitType", ParamFieldType.U8, 0x6B, null, null, "ATK_PARAM_HIT_TYPE", "あたり1 部位") { it.hit1_hitType },
    ParamField("hit2_hitType", ParamFieldType.U8, 0x6C, null, null, "ATK_PARAM_HIT_TYPE", "あたり2 部位") { it.hit2_hitType },
    ParamField("hit3_hitType", ParamFieldType.U8, 0x6D, null, null, "ATK_PARAM_HIT_TYPE", "あたり3 部位") { it.hit3_hitType },
    ParamField("hti0_Priority", ParamFieldType.U8, 0x6E, null, null, null, "あたり0 優先順位") { it.hti0_Priority },
    ParamField("hti1_Priority", ParamFieldType.U8, 0x6F, null, null, null, "あたり1 優先順位") { it.hti1_Priority },
    ParamField("hti2_Priority", ParamFieldType.U8, 0x70, null, null, null, "あたり2 優先順位") { it.hti2_Priority },
    ParamField("hti3_Priority", ParamFieldType.U8, 0x71, null, null, null, "あたり3 優先順位") { it.hti3_Priority },
    ParamField("dmgLevel", ParamFieldType.U8, 0x72, null, null, null, "ダメージレベル") { it.dmgLevel },
    ParamField("mapHitType", ParamFieldType.U8, 0x73, null, null, "ATK_PARAM_MAP_HIT", "マップあたり参照") { it.mapHitType },
    ParamField("guardCutCancelRate", ParamFieldType.S8, 0x74, null, null, null, "ガードカット率無効化倍率") { it.guardCutCancelRate },
    ParamField("atkAttribute", ParamFieldType.U8, 0x75, null, null, "ATKPARAM_ATKATTR_TYPE", "物理属性") { it.atkAttribute },
    ParamField("spAttribute", ParamFieldType.U8, 0x76, null, null, "ATKPARAM_SPATTR_TYPE", "特殊属性") { it.spAttribute },
    ParamField("atkType", ParamFieldType.U8, 0x77, null, null, "BEHAVIOR_ATK_TYPE", "攻撃属性[SFX/SE]") { it.atkType },
    ParamField("atkMaterial", ParamFieldType.U8, 0x78, null, null, "WEP_MATERIAL_ATK", "攻撃材質[SFX/SE]") { it.atkMaterial },
    ParamField("atkSize", ParamFieldType.U8, 0x79, null, null, "BEHAVIOR_ATK_SIZE", "攻撃サイズ[SFX/SE]") { it.atkSize },
    ParamField("defMaterial", ParamFieldType.U8, 0x7A, null, null, "WEP_MATERIAL_DEF", "防御材質[SE]") { it.defMaterial },
    ParamField("defSfxMaterial", ParamFieldType.U8, 0x7B, null, null, "WEP_MATERIAL_DEF_SFX", "防御材質[SFX]") { it.defSfxMaterial },
    ParamField("hitSourceType", ParamFieldType.U8, 0x7C, null, null, "ATK_PARAM_HIT_SOURCE", "あたり発生源") { it.hitSourceType },
    ParamField("throwFlag", ParamFieldType.U8, 0x7D, null, null, "ATK_PATAM_THROWFLAG_TYPE", "投げ") { it.throwFlag },
    ParamField("disableGuard", ParamFieldType.U8, 0x7E, 0, 1, "ATK_PARAM_BOOL", "ガード不可フラグ") { it.disableGuard },
    ParamField("disableStaminaAttack", ParamFieldType.U8, 0x7E, 1, 1, "ATK_PARAM_BOOL", "スタミナ減らない") { it.disableStaminaAttack },
    ParamField("disableHitSpEffect", ParamFieldType.U8, 0x7E, 2, 1, "ATK_PARAM_BOOL", "ヒット時特殊効果無効") { it.disableHitSpEffect },
    ParamField("ignoreNotifyMissSwingForAI", ParamFieldType.U8, 0x7E, 3, 1, "ATK_PARAM_BOOL", "AIに空振り通知しない") { it.ignoreNotifyMissSwingForAI },
    ParamField("repeatHitSfx", ParamFieldType.U8, 0x7E, 4, 1, "ATK_PARAM_BOOL", "ＨＩＴ時にＳＦＸを何度も出すか") { it.repeatHitSfx },
    ParamField("isArrowAtk", ParamFieldType.U8, 0x7E, 5, 1, "ATK_PARAM_BOOL", "矢攻撃か") { it.isArrowAtk },
    ParamField("isGhostAtk", ParamFieldType.U8, 0x7E, 6, 1, "ATK_PARAM_BOOL", "霊体攻撃か") { it.isGhostAtk },
    ParamField("isDisableNoDamage", ParamFieldType.U8, 0x7E, 7, 1, "ATK_PARAM_BOOL", "無敵無効か") { it.isDisableNoDamage }
)
