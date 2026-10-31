// GENERATED FILE - do not edit. Regenerate with:
//
//     ./gradlew :tools:generateParamDefs -Pptde=<DATA directory> -Pdsr=<DATA directory>
//
// Source: paramdef.paramdefbnd, AiStandardInfo.paramdef, read from the installation

@file:Suppress("SpellCheckingInspection")

package com.sappyoak.dsanalyzer.formats.param.defs

import com.sappyoak.dsanalyzer.formats.param.ParamRow
import com.sappyoak.dsanalyzer.formats.param.ParamRowData
import com.sappyoak.dsanalyzer.formats.param.ParamRowType

/** `AI_STANDARD_INFO_BANK`: PrepareToDie 0x80, Remastered 0x80 a row, data version 1 */
public class AiStandardInfoRow(row: ParamRowData) : ParamRow(row) {
    /** `u16` at 0x0 */
    public val radarRange: Int get() = bytes.u16(0x0)

    /** `u8` at 0x2 */
    public val radarAngleX: Int get() = bytes.u8(0x2)

    /** `u8` at 0x3 */
    public val radarAngleY: Int get() = bytes.u8(0x3)

    /** `u16` at 0x4 */
    public val territorySize: Int get() = bytes.u16(0x4)

    /** `u8` at 0x6 */
    public val threatBeforeAttackRate: Int get() = bytes.u8(0x6)

    /** `u8` at 0x7, `ON_OFF` */
    public val forceThreatOnFirstLocked: Int get() = bytes.u8(0x7)

    /** `u16` at 0x20 */
    public val attack1_Distance: Int get() = bytes.u16(0x20)

    /** `u16` at 0x22 */
    public val attack1_Margin: Int get() = bytes.u16(0x22)

    /** `u8` at 0x24 */
    public val attack1_Rate: Int get() = bytes.u8(0x24)

    /** `u8` at 0x25, `ACTION_PATTERN` */
    public val attack1_ActionID: Int get() = bytes.u8(0x25)

    /** `u8` at 0x26 */
    public val attack1_DelayMin: Int get() = bytes.u8(0x26)

    /** `u8` at 0x27 */
    public val attack1_DelayMax: Int get() = bytes.u8(0x27)

    /** `u8` at 0x28 */
    public val attack1_ConeAngle: Int get() = bytes.u8(0x28)

    /** `u16` at 0x30 */
    public val attack2_Distance: Int get() = bytes.u16(0x30)

    /** `u16` at 0x32 */
    public val attack2_Margin: Int get() = bytes.u16(0x32)

    /** `u8` at 0x34 */
    public val attack2_Rate: Int get() = bytes.u8(0x34)

    /** `u8` at 0x35, `ACTION_PATTERN` */
    public val attack2_ActionID: Int get() = bytes.u8(0x35)

    /** `u8` at 0x36 */
    public val attack2_DelayMin: Int get() = bytes.u8(0x36)

    /** `u8` at 0x37 */
    public val attack2_DelayMax: Int get() = bytes.u8(0x37)

    /** `u8` at 0x38 */
    public val attack2_ConeAngle: Int get() = bytes.u8(0x38)

    /** `u16` at 0x40 */
    public val attack3_Distance: Int get() = bytes.u16(0x40)

    /** `u16` at 0x42 */
    public val attack3_Margin: Int get() = bytes.u16(0x42)

    /** `u8` at 0x44 */
    public val attack3_Rate: Int get() = bytes.u8(0x44)

    /** `u8` at 0x45, `ACTION_PATTERN` */
    public val attack3_ActionID: Int get() = bytes.u8(0x45)

    /** `u8` at 0x46 */
    public val attack3_DelayMin: Int get() = bytes.u8(0x46)

    /** `u8` at 0x47 */
    public val attack3_DelayMax: Int get() = bytes.u8(0x47)

    /** `u8` at 0x48 */
    public val attack3_ConeAngle: Int get() = bytes.u8(0x48)

    /** `u16` at 0x50 */
    public val attack4_Distance: Int get() = bytes.u16(0x50)

    /** `u16` at 0x52 */
    public val attack4_Margin: Int get() = bytes.u16(0x52)

    /** `u8` at 0x54 */
    public val attack4_Rate: Int get() = bytes.u8(0x54)

    /** `u8` at 0x55, `ACTION_PATTERN` */
    public val attack4_ActionID: Int get() = bytes.u8(0x55)

    /** `u8` at 0x56 */
    public val attack4_DelayMin: Int get() = bytes.u8(0x56)

    /** `u8` at 0x57 */
    public val attack4_DelayMax: Int get() = bytes.u8(0x57)

    /** `u8` at 0x58 */
    public val attack4_ConeAngle: Int get() = bytes.u8(0x58)

    public companion object : ParamRowType<AiStandardInfoRow>("AI_STANDARD_INFO_BANK", 0x80, 1, ::AiStandardInfoRow)
}
