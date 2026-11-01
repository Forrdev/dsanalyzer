// GENERATED FILE - do not edit. Regenerate with:
//
//     ./gradlew :tools:generateParamDefs -Pptde=<DATA directory> -Pdsr=<DATA directory>
//
// Source: paramdef.paramdefbnd, ObjectParam.paramdef, read from the installation

@file:Suppress("SpellCheckingInspection")

package com.sappyoak.dsanalyzer.formats.param.defs

import com.sappyoak.dsanalyzer.formats.param.ParamField
import com.sappyoak.dsanalyzer.formats.param.ParamRow
import com.sappyoak.dsanalyzer.formats.param.ParamRowData
import com.sappyoak.dsanalyzer.formats.param.ParamRowType
import com.sappyoak.dsanalyzer.formats.param.paramdef.ParamFieldType

/** `OBJECT_PARAM_ST`: PrepareToDie 0x10, Remastered 0x10 a row, data version 1 */
public class ObjectParamRow(row: ParamRowData) : ParamRow(row) {
    /** `s16` at 0x0 */
    public val hp: Int get() = bytes.s16(0x0)

    /** `u16` at 0x2 */
    public val defense: Int get() = bytes.u16(0x2)

    /** `s16` at 0x4 */
    public val extRefTexId: Int get() = bytes.s16(0x4)

    /** `s16` at 0x6 */
    public val materialId: Int get() = bytes.s16(0x6)

    /** `u8` at 0x8 */
    public val animBreakIdMax: Int get() = bytes.u8(0x8)

    /** bit 0 of the `u8` at 0x9 */
    public val isCamHit: Boolean get() = bytes.flag(0x9, 0)

    /** bit 1 of the `u8` at 0x9 */
    public val isBreakByPlayerCollide: Boolean get() = bytes.flag(0x9, 1)

    /** bit 2 of the `u8` at 0x9 */
    public val isAnimBreak: Boolean get() = bytes.flag(0x9, 2)

    /** bit 3 of the `u8` at 0x9 */
    public val isPenetrationBulletHit: Boolean get() = bytes.flag(0x9, 3)

    /** bit 4 of the `u8` at 0x9 */
    public val isChrHit: Boolean get() = bytes.flag(0x9, 4)

    /** bit 5 of the `u8` at 0x9 */
    public val isAttackBacklash: Boolean get() = bytes.flag(0x9, 5)

    /** bit 6 of the `u8` at 0x9 */
    public val isDisableBreakForFirstAppear: Boolean get() = bytes.flag(0x9, 6)

    /** bit 7 of the `u8` at 0x9 */
    public val isLadder: Boolean get() = bytes.flag(0x9, 7)

    /** bit 0 of the `u8` at 0xA */
    public val isAnimPauseOnRemoPlay: Boolean get() = bytes.flag(0xA, 0)

    /** bit 1 of the `u8` at 0xA */
    public val isDamageNoHit: Boolean get() = bytes.flag(0xA, 1)

    /** bit 2 of the `u8` at 0xA */
    public val isMoveObj: Boolean get() = bytes.flag(0xA, 2)

    /** `s8` at 0xB */
    public val defaultLodParamId: Int get() = bytes.s8(0xB)

    /** `s32` at 0xC */
    public val breakSfxId: Int get() = bytes.s32(0xC)

    public companion object : ParamRowType<ObjectParamRow>("OBJECT_PARAM_ST", 0x10, 1, FIELDS, ::ObjectParamRow)
}

private val FIELDS: List<ParamField<ObjectParamRow>> = listOf<ParamField<ObjectParamRow>>(
    ParamField("hp", ParamFieldType.S16, 0x0, null, null, null, "HP") { it.hp },
    ParamField("defense", ParamFieldType.U16, 0x2, null, null, null, "防御力") { it.defense },
    ParamField("extRefTexId", ParamFieldType.S16, 0x4, null, null, null, "外部参照テクスチャID") { it.extRefTexId },
    ParamField("materialId", ParamFieldType.S16, 0x6, null, null, null, "材質ID") { it.materialId },
    ParamField("animBreakIdMax", ParamFieldType.U8, 0x8, null, null, null, "アニメ破壊ID最大値") { it.animBreakIdMax },
    ParamField("isCamHit", ParamFieldType.U8, 0x9, 0, 1, null, "カメラが当たるか") { it.isCamHit },
    ParamField("isBreakByPlayerCollide", ParamFieldType.U8, 0x9, 1, 1, null, "プレイヤ衝突で壊れるか") { it.isBreakByPlayerCollide },
    ParamField("isAnimBreak", ParamFieldType.U8, 0x9, 2, 1, null, "アニメ破壊か") { it.isAnimBreak },
    ParamField("isPenetrationBulletHit", ParamFieldType.U8, 0x9, 3, 1, null, "貫通弾丸が当たるか") { it.isPenetrationBulletHit },
    ParamField("isChrHit", ParamFieldType.U8, 0x9, 4, 1, null, "キャラが当たるか") { it.isChrHit },
    ParamField("isAttackBacklash", ParamFieldType.U8, 0x9, 5, 1, null, "攻撃を弾くか") { it.isAttackBacklash },
    ParamField("isDisableBreakForFirstAppear", ParamFieldType.U8, 0x9, 6, 1, null, "初期出現用破壊禁止") { it.isDisableBreakForFirstAppear },
    ParamField("isLadder", ParamFieldType.U8, 0x9, 7, 1, null, "ハシゴか") { it.isLadder },
    ParamField("isAnimPauseOnRemoPlay", ParamFieldType.U8, 0xA, 0, 1, null, "ポリ劇中アニメを停止するか") { it.isAnimPauseOnRemoPlay },
    ParamField("isDamageNoHit", ParamFieldType.U8, 0xA, 1, 1, null, "ダメージが当たらないか") { it.isDamageNoHit },
    ParamField("isMoveObj", ParamFieldType.U8, 0xA, 2, 1, null, "移動オブジェか") { it.isMoveObj },
    ParamField("defaultLodParamId", ParamFieldType.S8, 0xB, null, null, null, "デフォルトLODパラムID") { it.defaultLodParamId },
    ParamField("breakSfxId", ParamFieldType.S32, 0xC, null, null, null, "破壊時SFXID") { it.breakSfxId }
)
