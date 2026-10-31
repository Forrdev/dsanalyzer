// GENERATED FILE - do not edit. Regenerate with:
//
//     ./gradlew :tools:generateParamDefs -Pptde=<DATA directory> -Pdsr=<DATA directory>
//
// Source: paramdef.paramdefbnd, SpEffectVfx.paramdef, read from the installation

@file:Suppress("SpellCheckingInspection")

package com.sappyoak.dsanalyzer.formats.param.defs

import com.sappyoak.dsanalyzer.formats.param.ParamRow
import com.sappyoak.dsanalyzer.formats.param.ParamRowData
import com.sappyoak.dsanalyzer.formats.param.ParamRowType

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

    public companion object : ParamRowType<SpEffectVfxRow>("SP_EFFECT_VFX_PARAM_ST", 0x40, 1, ::SpEffectVfxRow)
}
