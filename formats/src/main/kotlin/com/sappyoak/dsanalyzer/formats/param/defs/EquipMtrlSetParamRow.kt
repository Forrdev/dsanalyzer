// GENERATED FILE - do not edit. Regenerate with:
//
//     ./gradlew :tools:generateParamDefs -Pptde=<DATA directory> -Pdsr=<DATA directory>
//
// Source: paramdef.paramdefbnd, EquipMtrlSetParam.paramdef, read from the installation

@file:Suppress("SpellCheckingInspection")

package com.sappyoak.dsanalyzer.formats.param.defs

import com.sappyoak.dsanalyzer.formats.param.ParamRow
import com.sappyoak.dsanalyzer.formats.param.ParamRowData
import com.sappyoak.dsanalyzer.formats.param.ParamRowType

/** `EQUIP_MTRL_SET_PARAM_ST`: PrepareToDie 0x20, Remastered 0x20 a row, data version 1 */
public class EquipMtrlSetParamRow(row: ParamRowData) : ParamRow(row) {
    /** `s32` at 0x0 */
    public val materialId01: Int get() = bytes.s32(0x0)

    /** `s32` at 0x4 */
    public val materialId02: Int get() = bytes.s32(0x4)

    /** `s32` at 0x8 */
    public val materialId03: Int get() = bytes.s32(0x8)

    /** `s32` at 0xC */
    public val materialId04: Int get() = bytes.s32(0xC)

    /** `s32` at 0x10 */
    public val materialId05: Int get() = bytes.s32(0x10)

    /** `s8` at 0x14 */
    public val itemNum01: Int get() = bytes.s8(0x14)

    /** `s8` at 0x15 */
    public val itemNum02: Int get() = bytes.s8(0x15)

    /** `s8` at 0x16 */
    public val itemNum03: Int get() = bytes.s8(0x16)

    /** `s8` at 0x17 */
    public val itemNum04: Int get() = bytes.s8(0x17)

    /** `s8` at 0x18 */
    public val itemNum05: Int get() = bytes.s8(0x18)

    /** bit 0 of the `u8` at 0x19 */
    public val isDisableDispNum01: Boolean get() = bytes.flag(0x19, 0)

    /** bit 1 of the `u8` at 0x19 */
    public val isDisableDispNum02: Boolean get() = bytes.flag(0x19, 1)

    /** bit 2 of the `u8` at 0x19 */
    public val isDisableDispNum03: Boolean get() = bytes.flag(0x19, 2)

    /** bit 3 of the `u8` at 0x19 */
    public val isDisableDispNum04: Boolean get() = bytes.flag(0x19, 3)

    /** bit 4 of the `u8` at 0x19 */
    public val isDisableDispNum05: Boolean get() = bytes.flag(0x19, 4)

    public companion object : ParamRowType<EquipMtrlSetParamRow>("EQUIP_MTRL_SET_PARAM_ST", 0x20, 1, ::EquipMtrlSetParamRow)
}
