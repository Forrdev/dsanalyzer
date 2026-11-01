// GENERATED FILE - do not edit. Regenerate with:
//
//     ./gradlew :tools:generateParamDefs -Pptde=<DATA directory> -Pdsr=<DATA directory>
//
// Source: paramdef.paramdefbnd, SpEffectVfx.paramdef, read from the installation

@file:Suppress("SpellCheckingInspection")

package com.sappyoak.dsanalyzer.formats.param.defs

import com.sappyoak.dsanalyzer.formats.param.ParamField
import com.sappyoak.dsanalyzer.formats.param.ParamRow
import com.sappyoak.dsanalyzer.formats.param.ParamRowData
import com.sappyoak.dsanalyzer.formats.param.ParamRowType
import com.sappyoak.dsanalyzer.formats.param.paramdef.ParamFieldType

/** `SP_EFFECT_VFX_PARAM_ST`: PrepareToDie 0x40, Remastered 0x40 a row, data version 1 */
public class SpEffectVfxRow(row: ParamRowData) : ParamRow(row) {
    /** `s32` at 0x0 */
    public val midstSfxId: Int get() = bytes.s32(0x0)

    /** `s32` at 0x4 */
    public val midstSeId: Int get() = bytes.s32(0x4)

    /** `s32` at 0x8 */
    public val initSfxId: Int get() = bytes.s32(0x8)

    /** `s32` at 0xC */
    public val initSeId: Int get() = bytes.s32(0xC)

    /** `s32` at 0x10 */
    public val finishSfxId: Int get() = bytes.s32(0x10)

    /** `s32` at 0x14 */
    public val finishSeId: Int get() = bytes.s32(0x14)

    /** `f32` at 0x18 */
    public val camouflageBeginDist: Float get() = bytes.f32(0x18)

    /** `f32` at 0x1C */
    public val camouflageEndDist: Float get() = bytes.f32(0x1C)

    /** `s32` at 0x20 */
    public val transformProtectorId: Int get() = bytes.s32(0x20)

    /** `s16` at 0x24 */
    public val midstDmyId: Int get() = bytes.s16(0x24)

    /** `s16` at 0x26 */
    public val initDmyId: Int get() = bytes.s16(0x26)

    /** `s16` at 0x28 */
    public val finishDmyId: Int get() = bytes.s16(0x28)

    /** `u8` at 0x2A, `SP_EFFECT_VFX_EFFECT_TYPE` */
    public val effectType: Int get() = bytes.u8(0x2A)

    /** `u8` at 0x2B, `SP_EFFECT_VFX_SOUL_PARAM_TYPE` */
    public val soulParamIdForWepEnchant: Int get() = bytes.u8(0x2B)

    /** `u8` at 0x2C, `SP_EFFECT_VFX_PLAYCATEGORY` */
    public val playCategory: Int get() = bytes.u8(0x2C)

    /** `u8` at 0x2D */
    public val playPriority: Int get() = bytes.u8(0x2D)

    /** bit 0 of the `u8` at 0x2E, `SP_EFFECT_BOOL` */
    public val existEffectForLarge: Boolean get() = bytes.flag(0x2E, 0)

    /** bit 1 of the `u8` at 0x2E, `SP_EFFECT_BOOL` */
    public val existEffectForSoul: Boolean get() = bytes.flag(0x2E, 1)

    /** bit 2 of the `u8` at 0x2E, `SP_EFFECT_BOOL` */
    public val effectInvisibleAtCamouflage: Boolean get() = bytes.flag(0x2E, 2)

    /** bit 3 of the `u8` at 0x2E, `SP_EFFECT_BOOL` */
    public val useCamouflage: Boolean get() = bytes.flag(0x2E, 3)

    /** bit 4 of the `u8` at 0x2E, `SP_EFFECT_BOOL` */
    public val invisibleAtFriendCamouflage: Boolean get() = bytes.flag(0x2E, 4)

    /** bit 5 of the `u8` at 0x2E, `SP_EFFECT_BOOL` */
    public val addMapAreaBlockOffset: Boolean get() = bytes.flag(0x2E, 5)

    /** bit 6 of the `u8` at 0x2E, `SP_EFFECT_BOOL` */
    public val halfCamouflage: Boolean get() = bytes.flag(0x2E, 6)

    /** bit 7 of the `u8` at 0x2E, `SP_EFFECT_BOOL` */
    public val isFullBodyTransformProtectorId: Boolean get() = bytes.flag(0x2E, 7)

    /** bit 0 of the `u8` at 0x2F, `SP_EFFECT_BOOL` */
    public val isInvisibleWeapon: Boolean get() = bytes.flag(0x2F, 0)

    /** bit 1 of the `u8` at 0x2F, `SP_EFFECT_BOOL` */
    public val isSilence: Boolean get() = bytes.flag(0x2F, 1)

    public companion object : ParamRowType<SpEffectVfxRow>("SP_EFFECT_VFX_PARAM_ST", 0x40, 1, FIELDS, ::SpEffectVfxRow)
}

private val FIELDS: List<ParamField<SpEffectVfxRow>> = listOf<ParamField<SpEffectVfxRow>>(
    ParamField("midstSfxId", ParamFieldType.S32, 0x0, null, null, null, "効果中SfxID") { it.midstSfxId },
    ParamField("midstSeId", ParamFieldType.S32, 0x4, null, null, null, "効果中SeID") { it.midstSeId },
    ParamField("initSfxId", ParamFieldType.S32, 0x8, null, null, null, "発動時SfxID") { it.initSfxId },
    ParamField("initSeId", ParamFieldType.S32, 0xC, null, null, null, "発動時SeID") { it.initSeId },
    ParamField("finishSfxId", ParamFieldType.S32, 0x10, null, null, null, "解除時SfxID") { it.finishSfxId },
    ParamField("finishSeId", ParamFieldType.S32, 0x14, null, null, null, "解除時SeID") { it.finishSeId },
    ParamField("camouflageBeginDist", ParamFieldType.F32, 0x18, null, null, null, "姿隠し開始距離[m]") { it.camouflageBeginDist },
    ParamField("camouflageEndDist", ParamFieldType.F32, 0x1C, null, null, null, "姿隠し終了距離[m]") { it.camouflageEndDist },
    ParamField("transformProtectorId", ParamFieldType.S32, 0x20, null, null, null, "変身防具ID") { it.transformProtectorId },
    ParamField("midstDmyId", ParamFieldType.S16, 0x24, null, null, null, "効果中ダミポリID") { it.midstDmyId },
    ParamField("initDmyId", ParamFieldType.S16, 0x26, null, null, null, "発動時ダミポリID") { it.initDmyId },
    ParamField("finishDmyId", ParamFieldType.S16, 0x28, null, null, null, "解除時ダミポリID") { it.finishDmyId },
    ParamField("effectType", ParamFieldType.U8, 0x2A, null, null, "SP_EFFECT_VFX_EFFECT_TYPE", "エフェクトタイプ") { it.effectType },
    ParamField("soulParamIdForWepEnchant", ParamFieldType.U8, 0x2B, null, null, "SP_EFFECT_VFX_SOUL_PARAM_TYPE", "武器エンチャント用ソウルパラムID") { it.soulParamIdForWepEnchant },
    ParamField("playCategory", ParamFieldType.U8, 0x2C, null, null, "SP_EFFECT_VFX_PLAYCATEGORY", "VFX再生カテゴリ") { it.playCategory },
    ParamField("playPriority", ParamFieldType.U8, 0x2D, null, null, null, "カテゴリ内優先度") { it.playPriority },
    ParamField("existEffectForLarge", ParamFieldType.U8, 0x2E, 0, 1, "SP_EFFECT_BOOL", "大型用エフェクトがあるか") { it.existEffectForLarge },
    ParamField("existEffectForSoul", ParamFieldType.U8, 0x2E, 1, 1, "SP_EFFECT_BOOL", "ソウル体用エフェクトがあるか") { it.existEffectForSoul },
    ParamField("effectInvisibleAtCamouflage", ParamFieldType.U8, 0x2E, 2, 1, "SP_EFFECT_BOOL", "姿隠し時にエフェクトを非表示にするか") { it.effectInvisibleAtCamouflage },
    ParamField("useCamouflage", ParamFieldType.U8, 0x2E, 3, 1, "SP_EFFECT_BOOL", "姿隠しするか") { it.useCamouflage },
    ParamField("invisibleAtFriendCamouflage", ParamFieldType.U8, 0x2E, 4, 1, "SP_EFFECT_BOOL", "姿隠し時に味方でも非表示か") { it.invisibleAtFriendCamouflage },
    ParamField("addMapAreaBlockOffset", ParamFieldType.U8, 0x2E, 5, 1, "SP_EFFECT_BOOL", "SfxIDにマップ番号を足すか") { it.addMapAreaBlockOffset },
    ParamField("halfCamouflage", ParamFieldType.U8, 0x2E, 6, 1, "SP_EFFECT_BOOL", "半透明の姿隠しか") { it.halfCamouflage },
    ParamField("isFullBodyTransformProtectorId", ParamFieldType.U8, 0x2E, 7, 1, "SP_EFFECT_BOOL", "変身防具IDが全身用か") { it.isFullBodyTransformProtectorId },
    ParamField("isInvisibleWeapon", ParamFieldType.U8, 0x2F, 0, 1, "SP_EFFECT_BOOL", "武器エンチャント用インビジブルウェポンか") { it.isInvisibleWeapon },
    ParamField("isSilence", ParamFieldType.U8, 0x2F, 1, 1, "SP_EFFECT_BOOL", "サイレンスか") { it.isSilence }
)
