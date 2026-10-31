// GENERATED FILE - do not edit. Regenerate with:
//
//     ./gradlew :tools:generateParamDefs -Pptde=<DATA directory> -Pdsr=<DATA directory>
//
// Source: paramdef.paramdefbnd, MagicParam.paramdef, read from the installation

@file:Suppress("SpellCheckingInspection")

package com.sappyoak.dsanalyzer.formats.param.defs

import com.sappyoak.dsanalyzer.formats.param.ParamRow
import com.sappyoak.dsanalyzer.formats.param.ParamRowData
import com.sappyoak.dsanalyzer.formats.param.ParamRowType

/** `MAGIC_PARAM_ST`: PrepareToDie 0x30, Remastered 0x30 a row, data version 2 */
public class MagicParamRow(row: ParamRowData) : ParamRow(row) {
    /** `s32` at 0x0 */
    public val yesNoDialogMessageId: Int get() = bytes.s32(0x0)

    /** `s32` at 0x4 */
    public val limitCancelSpEffectId: Int get() = bytes.s32(0x4)

    /** `s16` at 0x8 */
    public val sortId: Int get() = bytes.s16(0x8)

    /** `s16` at 0xA */
    public val refId: Int get() = bytes.s16(0xA)

    /** `s16` at 0xC */
    public val mp: Int get() = bytes.s16(0xC)

    /** `s16` at 0xE */
    public val stamina: Int get() = bytes.s16(0xE)

    /** `s16` at 0x10 */
    public val iconId: Int get() = bytes.s16(0x10)

    /** `s16` at 0x12 */
    public val behaviorId: Int get() = bytes.s16(0x12)

    /** `s16` at 0x14 */
    public val mtrlItemId: Int get() = bytes.s16(0x14)

    /** `s16` at 0x16 */
    public val replaceMagicId: Int get() = bytes.s16(0x16)

    /** `s16` at 0x18 */
    public val maxQuantity: Int get() = bytes.s16(0x18)

    /** `u8` at 0x1A */
    public val heroPoint: Int get() = bytes.u8(0x1A)

    /** `u8` at 0x1B */
    public val overDexterity: Int get() = bytes.u8(0x1B)

    /** `s8` at 0x1C */
    public val sfxVariationId: Int get() = bytes.s8(0x1C)

    /** `u8` at 0x1D */
    public val slotLength: Int get() = bytes.u8(0x1D)

    /** `u8` at 0x1E */
    public val requirementIntellect: Int get() = bytes.u8(0x1E)

    /** `u8` at 0x1F */
    public val requirementFaith: Int get() = bytes.u8(0x1F)

    /** `u8` at 0x20 */
    public val analogDexiterityMin: Int get() = bytes.u8(0x20)

    /** `u8` at 0x21 */
    public val analogDexiterityMax: Int get() = bytes.u8(0x21)

    /** `u8` at 0x22, `MAGIC_CATEGORY` */
    public val ezStateBehaviorType: Int get() = bytes.u8(0x22)

    /** `u8` at 0x23, `BEHAVIOR_REF_TYPE` */
    public val refCategory: Int get() = bytes.u8(0x23)

    /** `u8` at 0x24, `BEHAVIOR_CATEGORY` */
    public val spEffectCategory: Int get() = bytes.u8(0x24)

    /** `u8` at 0x25, `MAGIC_MOTION_TYPE` */
    public val refType: Int get() = bytes.u8(0x25)

    /** `u8` at 0x26, `GOODS_OPEN_MENU` */
    public val opmeMenuType: Int get() = bytes.u8(0x26)

    /** `u8` at 0x27, `SP_EFFECT_TYPE` */
    public val hasSpEffectType: Int get() = bytes.u8(0x27)

    /** `u8` at 0x28, `REPLACE_CATEGORY` */
    public val replaceCategory: Int get() = bytes.u8(0x28)

    /** `u8` at 0x29, `SP_EFFECT_USELIMIT_CATEGORY` */
    public val useLimitCategory: Int get() = bytes.u8(0x29)

    /** bit 0 of the `u8` at 0x2A, `MAGIC_BOOL` */
    public val vowType0: Boolean get() = bytes.flag(0x2A, 0)

    /** bit 1 of the `u8` at 0x2A, `MAGIC_BOOL` */
    public val vowType1: Boolean get() = bytes.flag(0x2A, 1)

    /** bit 2 of the `u8` at 0x2A, `MAGIC_BOOL` */
    public val vowType2: Boolean get() = bytes.flag(0x2A, 2)

    /** bit 3 of the `u8` at 0x2A, `MAGIC_BOOL` */
    public val vowType3: Boolean get() = bytes.flag(0x2A, 3)

    /** bit 4 of the `u8` at 0x2A, `MAGIC_BOOL` */
    public val vowType4: Boolean get() = bytes.flag(0x2A, 4)

    /** bit 5 of the `u8` at 0x2A, `MAGIC_BOOL` */
    public val vowType5: Boolean get() = bytes.flag(0x2A, 5)

    /** bit 6 of the `u8` at 0x2A, `MAGIC_BOOL` */
    public val vowType6: Boolean get() = bytes.flag(0x2A, 6)

    /** bit 7 of the `u8` at 0x2A, `MAGIC_BOOL` */
    public val vowType7: Boolean get() = bytes.flag(0x2A, 7)

    /** bit 0 of the `u8` at 0x2B, `MAGIC_BOOL` */
    public val enable_multi: Boolean get() = bytes.flag(0x2B, 0)

    /** bit 1 of the `u8` at 0x2B, `MAGIC_BOOL` */
    public val enable_multi_only: Boolean get() = bytes.flag(0x2B, 1)

    /** bit 2 of the `u8` at 0x2B, `MAGIC_BOOL` */
    public val isEnchant: Boolean get() = bytes.flag(0x2B, 2)

    /** bit 3 of the `u8` at 0x2B, `MAGIC_BOOL` */
    public val isShieldEnchant: Boolean get() = bytes.flag(0x2B, 3)

    /** bit 4 of the `u8` at 0x2B, `MAGIC_BOOL` */
    public val enable_live: Boolean get() = bytes.flag(0x2B, 4)

    /** bit 5 of the `u8` at 0x2B, `MAGIC_BOOL` */
    public val enable_gray: Boolean get() = bytes.flag(0x2B, 5)

    /** bit 6 of the `u8` at 0x2B, `MAGIC_BOOL` */
    public val enable_white: Boolean get() = bytes.flag(0x2B, 6)

    /** bit 7 of the `u8` at 0x2B, `MAGIC_BOOL` */
    public val enable_black: Boolean get() = bytes.flag(0x2B, 7)

    /** bit 0 of the `u8` at 0x2C, `MAGIC_BOOL` */
    public val disableOffline: Boolean get() = bytes.flag(0x2C, 0)

    /** bit 1 of the `u8` at 0x2C, `MAGIC_BOOL` */
    public val castResonanceMagic: Boolean get() = bytes.flag(0x2C, 1)

    /** bit 0 of the `u8` at 0x2D, `MAGIC_BOOL` */
    public val vowType8: Boolean get() = bytes.flag(0x2D, 0)

    /** bit 1 of the `u8` at 0x2D, `MAGIC_BOOL` */
    public val vowType9: Boolean get() = bytes.flag(0x2D, 1)

    /** bit 2 of the `u8` at 0x2D, `MAGIC_BOOL` */
    public val vowType10: Boolean get() = bytes.flag(0x2D, 2)

    /** bit 3 of the `u8` at 0x2D, `MAGIC_BOOL` */
    public val vowType11: Boolean get() = bytes.flag(0x2D, 3)

    /** bit 4 of the `u8` at 0x2D, `MAGIC_BOOL` */
    public val vowType12: Boolean get() = bytes.flag(0x2D, 4)

    /** bit 5 of the `u8` at 0x2D, `MAGIC_BOOL` */
    public val vowType13: Boolean get() = bytes.flag(0x2D, 5)

    /** bit 6 of the `u8` at 0x2D, `MAGIC_BOOL` */
    public val vowType14: Boolean get() = bytes.flag(0x2D, 6)

    /** bit 7 of the `u8` at 0x2D, `MAGIC_BOOL` */
    public val vowType15: Boolean get() = bytes.flag(0x2D, 7)

    public companion object : ParamRowType<MagicParamRow>("MAGIC_PARAM_ST", 0x30, 2, ::MagicParamRow)
}
