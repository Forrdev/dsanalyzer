// GENERATED FILE - do not edit. Regenerate with:
//
//     ./gradlew :tools:generateParamDefs -Pptde=<DATA directory> -Pdsr=<DATA directory>
//
// Source: paramdef.paramdefbnd, ItemLotParam.paramdef, read from the installation

@file:Suppress("SpellCheckingInspection")

package com.sappyoak.dsanalyzer.formats.param.defs

import com.sappyoak.dsanalyzer.formats.param.ParamField
import com.sappyoak.dsanalyzer.formats.param.ParamRow
import com.sappyoak.dsanalyzer.formats.param.ParamRowData
import com.sappyoak.dsanalyzer.formats.param.ParamRowType
import com.sappyoak.dsanalyzer.formats.param.paramdef.ParamFieldType

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

    public companion object : ParamRowType<ItemLotParamRow>("ITEMLOT_PARAM_ST", 0x94, 2, FIELDS, ::ItemLotParamRow)
}

private val FIELDS: List<ParamField<ItemLotParamRow>> = listOf<ParamField<ItemLotParamRow>>(
    ParamField("lotItemId01", ParamFieldType.S32, 0x0, null, null, null, "１：アイテムID") { it.lotItemId01 },
    ParamField("lotItemId02", ParamFieldType.S32, 0x4, null, null, null, "２：アイテムID") { it.lotItemId02 },
    ParamField("lotItemId03", ParamFieldType.S32, 0x8, null, null, null, "３：アイテムID") { it.lotItemId03 },
    ParamField("lotItemId04", ParamFieldType.S32, 0xC, null, null, null, "４：アイテムID") { it.lotItemId04 },
    ParamField("lotItemId05", ParamFieldType.S32, 0x10, null, null, null, "５：アイテムID") { it.lotItemId05 },
    ParamField("lotItemId06", ParamFieldType.S32, 0x14, null, null, null, "６：アイテムID") { it.lotItemId06 },
    ParamField("lotItemId07", ParamFieldType.S32, 0x18, null, null, null, "７：アイテムID") { it.lotItemId07 },
    ParamField("lotItemId08", ParamFieldType.S32, 0x1C, null, null, null, "８：アイテムID") { it.lotItemId08 },
    ParamField("lotItemCategory01", ParamFieldType.S32, 0x20, null, null, "ITEMLOT_ITEMCATEGORY", "１：アイテムカテゴリ") { it.lotItemCategory01 },
    ParamField("lotItemCategory02", ParamFieldType.S32, 0x24, null, null, "ITEMLOT_ITEMCATEGORY", "２：アイテムカテゴリ") { it.lotItemCategory02 },
    ParamField("lotItemCategory03", ParamFieldType.S32, 0x28, null, null, "ITEMLOT_ITEMCATEGORY", "３：アイテムカテゴリ") { it.lotItemCategory03 },
    ParamField("lotItemCategory04", ParamFieldType.S32, 0x2C, null, null, "ITEMLOT_ITEMCATEGORY", "４：アイテムカテゴリ") { it.lotItemCategory04 },
    ParamField("lotItemCategory05", ParamFieldType.S32, 0x30, null, null, "ITEMLOT_ITEMCATEGORY", "５：アイテムカテゴリ") { it.lotItemCategory05 },
    ParamField("lotItemCategory06", ParamFieldType.S32, 0x34, null, null, "ITEMLOT_ITEMCATEGORY", "６：アイテムカテゴリ") { it.lotItemCategory06 },
    ParamField("lotItemCategory07", ParamFieldType.S32, 0x38, null, null, "ITEMLOT_ITEMCATEGORY", "７：アイテムカテゴリ") { it.lotItemCategory07 },
    ParamField("lotItemCategory08", ParamFieldType.S32, 0x3C, null, null, "ITEMLOT_ITEMCATEGORY", "８：アイテムカテゴリ") { it.lotItemCategory08 },
    ParamField("lotItemBasePoint01", ParamFieldType.U16, 0x40, null, null, null, "基本出現ポイント") { it.lotItemBasePoint01 },
    ParamField("lotItemBasePoint02", ParamFieldType.U16, 0x42, null, null, null, "基本出現ポイント") { it.lotItemBasePoint02 },
    ParamField("lotItemBasePoint03", ParamFieldType.U16, 0x44, null, null, null, "基本出現ポイント") { it.lotItemBasePoint03 },
    ParamField("lotItemBasePoint04", ParamFieldType.U16, 0x46, null, null, null, "基本出現ポイント") { it.lotItemBasePoint04 },
    ParamField("lotItemBasePoint05", ParamFieldType.U16, 0x48, null, null, null, "基本出現ポイント") { it.lotItemBasePoint05 },
    ParamField("lotItemBasePoint06", ParamFieldType.U16, 0x4A, null, null, null, "基本出現ポイント") { it.lotItemBasePoint06 },
    ParamField("lotItemBasePoint07", ParamFieldType.U16, 0x4C, null, null, null, "基本出現ポイント") { it.lotItemBasePoint07 },
    ParamField("lotItemBasePoint08", ParamFieldType.U16, 0x4E, null, null, null, "基本出現ポイント") { it.lotItemBasePoint08 },
    ParamField("cumulateLotPoint01", ParamFieldType.U16, 0x50, null, null, null, "累積後出現ポイント") { it.cumulateLotPoint01 },
    ParamField("cumulateLotPoint02", ParamFieldType.U16, 0x52, null, null, null, "累積後出現ポイント") { it.cumulateLotPoint02 },
    ParamField("cumulateLotPoint03", ParamFieldType.U16, 0x54, null, null, null, "累積後出現ポイント") { it.cumulateLotPoint03 },
    ParamField("cumulateLotPoint04", ParamFieldType.U16, 0x56, null, null, null, "累積後出現ポイント") { it.cumulateLotPoint04 },
    ParamField("cumulateLotPoint05", ParamFieldType.U16, 0x58, null, null, null, "累積後出現ポイント") { it.cumulateLotPoint05 },
    ParamField("cumulateLotPoint06", ParamFieldType.U16, 0x5A, null, null, null, "累積後出現ポイント") { it.cumulateLotPoint06 },
    ParamField("cumulateLotPoint07", ParamFieldType.U16, 0x5C, null, null, null, "累積後出現ポイント") { it.cumulateLotPoint07 },
    ParamField("cumulateLotPoint08", ParamFieldType.U16, 0x5E, null, null, null, "累積後出現ポイント") { it.cumulateLotPoint08 },
    ParamField("getItemFlagId01", ParamFieldType.S32, 0x60, null, null, null, "別ザクザクフラグID") { it.getItemFlagId01 },
    ParamField("getItemFlagId02", ParamFieldType.S32, 0x64, null, null, null, "別ザクザクフラグID") { it.getItemFlagId02 },
    ParamField("getItemFlagId03", ParamFieldType.S32, 0x68, null, null, null, "別ザクザクフラグID") { it.getItemFlagId03 },
    ParamField("getItemFlagId04", ParamFieldType.S32, 0x6C, null, null, null, "別ザクザクフラグID") { it.getItemFlagId04 },
    ParamField("getItemFlagId05", ParamFieldType.S32, 0x70, null, null, null, "別ザクザクフラグID") { it.getItemFlagId05 },
    ParamField("getItemFlagId06", ParamFieldType.S32, 0x74, null, null, null, "別ザクザクフラグID") { it.getItemFlagId06 },
    ParamField("getItemFlagId07", ParamFieldType.S32, 0x78, null, null, null, "別ザクザクフラグID") { it.getItemFlagId07 },
    ParamField("getItemFlagId08", ParamFieldType.S32, 0x7C, null, null, null, "別ザクザクフラグID") { it.getItemFlagId08 },
    ParamField("getItemFlagId", ParamFieldType.S32, 0x80, null, null, null, "ザクザクフラグID") { it.getItemFlagId },
    ParamField("cumulateNumFlagId", ParamFieldType.S32, 0x84, null, null, null, "抽選累積保存フラグID") { it.cumulateNumFlagId },
    ParamField("cumulateNumMax", ParamFieldType.U8, 0x88, null, null, null, "抽選累積最大数") { it.cumulateNumMax },
    ParamField("lotItem_Rarity", ParamFieldType.U8, 0x89, null, null, null, "レアリティ設定") { it.lotItem_Rarity },
    ParamField("lotItemNum01", ParamFieldType.U8, 0x8A, null, null, null, "個数") { it.lotItemNum01 },
    ParamField("lotItemNum02", ParamFieldType.U8, 0x8B, null, null, null, "個数") { it.lotItemNum02 },
    ParamField("lotItemNum03", ParamFieldType.U8, 0x8C, null, null, null, "個数") { it.lotItemNum03 },
    ParamField("lotItemNum04", ParamFieldType.U8, 0x8D, null, null, null, "個数") { it.lotItemNum04 },
    ParamField("lotItemNum05", ParamFieldType.U8, 0x8E, null, null, null, "個数") { it.lotItemNum05 },
    ParamField("lotItemNum06", ParamFieldType.U8, 0x8F, null, null, null, "個数") { it.lotItemNum06 },
    ParamField("lotItemNum07", ParamFieldType.U8, 0x90, null, null, null, "個数") { it.lotItemNum07 },
    ParamField("lotItemNum08", ParamFieldType.U8, 0x91, null, null, null, "個数") { it.lotItemNum08 },
    ParamField("enableLuck01", ParamFieldType.U16, 0x92, 0, 1, "ITEMLOT_ENABLE_LUCK", "運パラメータ有効") { it.enableLuck01 },
    ParamField("enableLuck02", ParamFieldType.U16, 0x92, 1, 1, "ITEMLOT_ENABLE_LUCK", "運パラメータ有効") { it.enableLuck02 },
    ParamField("enableLuck03", ParamFieldType.U16, 0x92, 2, 1, "ITEMLOT_ENABLE_LUCK", "運パラメータ有効") { it.enableLuck03 },
    ParamField("enableLuck04", ParamFieldType.U16, 0x92, 3, 1, "ITEMLOT_ENABLE_LUCK", "運パラメータ有効") { it.enableLuck04 },
    ParamField("enableLuck05", ParamFieldType.U16, 0x92, 4, 1, "ITEMLOT_ENABLE_LUCK", "運パラメータ有効") { it.enableLuck05 },
    ParamField("enableLuck06", ParamFieldType.U16, 0x92, 5, 1, "ITEMLOT_ENABLE_LUCK", "運パラメータ有効") { it.enableLuck06 },
    ParamField("enableLuck07", ParamFieldType.U16, 0x92, 6, 1, "ITEMLOT_ENABLE_LUCK", "運パラメータ有効") { it.enableLuck07 },
    ParamField("enableLuck08", ParamFieldType.U16, 0x92, 7, 1, "ITEMLOT_ENABLE_LUCK", "運パラメータ有効") { it.enableLuck08 },
    ParamField("cumulateReset01", ParamFieldType.U16, 0x92, 8, 1, "ITEMLOT_CUMULATE_RESET", "累積リセット") { it.cumulateReset01 },
    ParamField("cumulateReset02", ParamFieldType.U16, 0x92, 9, 1, "ITEMLOT_CUMULATE_RESET", "累積リセット") { it.cumulateReset02 },
    ParamField("cumulateReset03", ParamFieldType.U16, 0x92, 10, 1, "ITEMLOT_CUMULATE_RESET", "累積リセット") { it.cumulateReset03 },
    ParamField("cumulateReset04", ParamFieldType.U16, 0x92, 11, 1, "ITEMLOT_CUMULATE_RESET", "累積リセット") { it.cumulateReset04 },
    ParamField("cumulateReset05", ParamFieldType.U16, 0x92, 12, 1, "ITEMLOT_CUMULATE_RESET", "累積リセット") { it.cumulateReset05 },
    ParamField("cumulateReset06", ParamFieldType.U16, 0x92, 13, 1, "ITEMLOT_CUMULATE_RESET", "累積リセット") { it.cumulateReset06 },
    ParamField("cumulateReset07", ParamFieldType.U16, 0x92, 14, 1, "ITEMLOT_CUMULATE_RESET", "累積リセット") { it.cumulateReset07 },
    ParamField("cumulateReset08", ParamFieldType.U16, 0x92, 15, 1, "ITEMLOT_CUMULATE_RESET", "累積リセット") { it.cumulateReset08 }
)
