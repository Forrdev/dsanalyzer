// GENERATED FILE - do not edit. Regenerate with:
//
//     ./gradlew :tools:generateParamDefs -Pptde=<DATA directory> -Pdsr=<DATA directory>
//
// Source: paramdef.paramdefbnd, ItemLotParam.paramdef, read from the installation

@file:Suppress("SpellCheckingInspection")

package com.sappyoak.dsanalyzer.formats.param.defs

import com.sappyoak.dsanalyzer.formats.param.ParamRow
import com.sappyoak.dsanalyzer.formats.param.ParamRowData
import com.sappyoak.dsanalyzer.formats.param.ParamRowType

/** `ITEMLOT_PARAM_ST`: PrepareToDie 0x94, Remastered 0x94 a row, data version 2 */
public class ItemLotParamRow(row: ParamRowData) : ParamRow(row) {
    /** `s32` at 0x0 */
    public val lotItemId01: Int get() = bytes.s32(0x0)

    /** `s32` at 0x4 */
    public val lotItemId02: Int get() = bytes.s32(0x4)

    /** `s32` at 0x8 */
    public val lotItemId03: Int get() = bytes.s32(0x8)

    /** `s32` at 0xC */
    public val lotItemId04: Int get() = bytes.s32(0xC)

    /** `s32` at 0x10 */
    public val lotItemId05: Int get() = bytes.s32(0x10)

    /** `s32` at 0x14 */
    public val lotItemId06: Int get() = bytes.s32(0x14)

    /** `s32` at 0x18 */
    public val lotItemId07: Int get() = bytes.s32(0x18)

    /** `s32` at 0x1C */
    public val lotItemId08: Int get() = bytes.s32(0x1C)

    /** `s32` at 0x20, `ITEMLOT_ITEMCATEGORY` */
    public val lotItemCategory01: Int get() = bytes.s32(0x20)

    /** `s32` at 0x24, `ITEMLOT_ITEMCATEGORY` */
    public val lotItemCategory02: Int get() = bytes.s32(0x24)

    /** `s32` at 0x28, `ITEMLOT_ITEMCATEGORY` */
    public val lotItemCategory03: Int get() = bytes.s32(0x28)

    /** `s32` at 0x2C, `ITEMLOT_ITEMCATEGORY` */
    public val lotItemCategory04: Int get() = bytes.s32(0x2C)

    /** `s32` at 0x30, `ITEMLOT_ITEMCATEGORY` */
    public val lotItemCategory05: Int get() = bytes.s32(0x30)

    /** `s32` at 0x34, `ITEMLOT_ITEMCATEGORY` */
    public val lotItemCategory06: Int get() = bytes.s32(0x34)

    /** `s32` at 0x38, `ITEMLOT_ITEMCATEGORY` */
    public val lotItemCategory07: Int get() = bytes.s32(0x38)

    /** `s32` at 0x3C, `ITEMLOT_ITEMCATEGORY` */
    public val lotItemCategory08: Int get() = bytes.s32(0x3C)

    /** `u16` at 0x40 */
    public val lotItemBasePoint01: Int get() = bytes.u16(0x40)

    /** `u16` at 0x42 */
    public val lotItemBasePoint02: Int get() = bytes.u16(0x42)

    /** `u16` at 0x44 */
    public val lotItemBasePoint03: Int get() = bytes.u16(0x44)

    /** `u16` at 0x46 */
    public val lotItemBasePoint04: Int get() = bytes.u16(0x46)

    /** `u16` at 0x48 */
    public val lotItemBasePoint05: Int get() = bytes.u16(0x48)

    /** `u16` at 0x4A */
    public val lotItemBasePoint06: Int get() = bytes.u16(0x4A)

    /** `u16` at 0x4C */
    public val lotItemBasePoint07: Int get() = bytes.u16(0x4C)

    /** `u16` at 0x4E */
    public val lotItemBasePoint08: Int get() = bytes.u16(0x4E)

    /** `u16` at 0x50 */
    public val cumulateLotPoint01: Int get() = bytes.u16(0x50)

    /** `u16` at 0x52 */
    public val cumulateLotPoint02: Int get() = bytes.u16(0x52)

    /** `u16` at 0x54 */
    public val cumulateLotPoint03: Int get() = bytes.u16(0x54)

    /** `u16` at 0x56 */
    public val cumulateLotPoint04: Int get() = bytes.u16(0x56)

    /** `u16` at 0x58 */
    public val cumulateLotPoint05: Int get() = bytes.u16(0x58)

    /** `u16` at 0x5A */
    public val cumulateLotPoint06: Int get() = bytes.u16(0x5A)

    /** `u16` at 0x5C */
    public val cumulateLotPoint07: Int get() = bytes.u16(0x5C)

    /** `u16` at 0x5E */
    public val cumulateLotPoint08: Int get() = bytes.u16(0x5E)

    /** `s32` at 0x60 */
    public val getItemFlagId01: Int get() = bytes.s32(0x60)

    /** `s32` at 0x64 */
    public val getItemFlagId02: Int get() = bytes.s32(0x64)

    /** `s32` at 0x68 */
    public val getItemFlagId03: Int get() = bytes.s32(0x68)

    /** `s32` at 0x6C */
    public val getItemFlagId04: Int get() = bytes.s32(0x6C)

    /** `s32` at 0x70 */
    public val getItemFlagId05: Int get() = bytes.s32(0x70)

    /** `s32` at 0x74 */
    public val getItemFlagId06: Int get() = bytes.s32(0x74)

    /** `s32` at 0x78 */
    public val getItemFlagId07: Int get() = bytes.s32(0x78)

    /** `s32` at 0x7C */
    public val getItemFlagId08: Int get() = bytes.s32(0x7C)

    /** `s32` at 0x80 */
    public val getItemFlagId: Int get() = bytes.s32(0x80)

    /** `s32` at 0x84 */
    public val cumulateNumFlagId: Int get() = bytes.s32(0x84)

    /** `u8` at 0x88 */
    public val cumulateNumMax: Int get() = bytes.u8(0x88)

    /** `u8` at 0x89 */
    public val lotItem_Rarity: Int get() = bytes.u8(0x89)

    /** `u8` at 0x8A */
    public val lotItemNum01: Int get() = bytes.u8(0x8A)

    /** `u8` at 0x8B */
    public val lotItemNum02: Int get() = bytes.u8(0x8B)

    /** `u8` at 0x8C */
    public val lotItemNum03: Int get() = bytes.u8(0x8C)

    /** `u8` at 0x8D */
    public val lotItemNum04: Int get() = bytes.u8(0x8D)

    /** `u8` at 0x8E */
    public val lotItemNum05: Int get() = bytes.u8(0x8E)

    /** `u8` at 0x8F */
    public val lotItemNum06: Int get() = bytes.u8(0x8F)

    /** `u8` at 0x90 */
    public val lotItemNum07: Int get() = bytes.u8(0x90)

    /** `u8` at 0x91 */
    public val lotItemNum08: Int get() = bytes.u8(0x91)

    /** bit 0 of the `u16` at 0x92, `ITEMLOT_ENABLE_LUCK` */
    public val enableLuck01: Boolean get() = bytes.flag(0x92, 0)

    /** bit 1 of the `u16` at 0x92, `ITEMLOT_ENABLE_LUCK` */
    public val enableLuck02: Boolean get() = bytes.flag(0x92, 1)

    /** bit 2 of the `u16` at 0x92, `ITEMLOT_ENABLE_LUCK` */
    public val enableLuck03: Boolean get() = bytes.flag(0x92, 2)

    /** bit 3 of the `u16` at 0x92, `ITEMLOT_ENABLE_LUCK` */
    public val enableLuck04: Boolean get() = bytes.flag(0x92, 3)

    /** bit 4 of the `u16` at 0x92, `ITEMLOT_ENABLE_LUCK` */
    public val enableLuck05: Boolean get() = bytes.flag(0x92, 4)

    /** bit 5 of the `u16` at 0x92, `ITEMLOT_ENABLE_LUCK` */
    public val enableLuck06: Boolean get() = bytes.flag(0x92, 5)

    /** bit 6 of the `u16` at 0x92, `ITEMLOT_ENABLE_LUCK` */
    public val enableLuck07: Boolean get() = bytes.flag(0x92, 6)

    /** bit 7 of the `u16` at 0x92, `ITEMLOT_ENABLE_LUCK` */
    public val enableLuck08: Boolean get() = bytes.flag(0x92, 7)

    /** bit 8 of the `u16` at 0x92, `ITEMLOT_CUMULATE_RESET` */
    public val cumulateReset01: Boolean get() = bytes.flag(0x93, 0)

    /** bit 9 of the `u16` at 0x92, `ITEMLOT_CUMULATE_RESET` */
    public val cumulateReset02: Boolean get() = bytes.flag(0x93, 1)

    /** bit 10 of the `u16` at 0x92, `ITEMLOT_CUMULATE_RESET` */
    public val cumulateReset03: Boolean get() = bytes.flag(0x93, 2)

    /** bit 11 of the `u16` at 0x92, `ITEMLOT_CUMULATE_RESET` */
    public val cumulateReset04: Boolean get() = bytes.flag(0x93, 3)

    /** bit 12 of the `u16` at 0x92, `ITEMLOT_CUMULATE_RESET` */
    public val cumulateReset05: Boolean get() = bytes.flag(0x93, 4)

    /** bit 13 of the `u16` at 0x92, `ITEMLOT_CUMULATE_RESET` */
    public val cumulateReset06: Boolean get() = bytes.flag(0x93, 5)

    /** bit 14 of the `u16` at 0x92, `ITEMLOT_CUMULATE_RESET` */
    public val cumulateReset07: Boolean get() = bytes.flag(0x93, 6)

    /** bit 15 of the `u16` at 0x92, `ITEMLOT_CUMULATE_RESET` */
    public val cumulateReset08: Boolean get() = bytes.flag(0x93, 7)

    public companion object : ParamRowType<ItemLotParamRow>("ITEMLOT_PARAM_ST", 0x94, 2, ::ItemLotParamRow)
}
