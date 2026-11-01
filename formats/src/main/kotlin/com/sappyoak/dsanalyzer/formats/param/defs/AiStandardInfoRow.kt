// GENERATED FILE - do not edit. Regenerate with:
//
//     ./gradlew :tools:generateParamDefs -Pptde=<DATA directory> -Pdsr=<DATA directory>
//
// Source: paramdef.paramdefbnd, AiStandardInfo.paramdef, read from the installation

@file:Suppress("SpellCheckingInspection")

package com.sappyoak.dsanalyzer.formats.param.defs

import com.sappyoak.dsanalyzer.formats.param.ParamField
import com.sappyoak.dsanalyzer.formats.param.ParamRow
import com.sappyoak.dsanalyzer.formats.param.ParamRowData
import com.sappyoak.dsanalyzer.formats.param.ParamRowType
import com.sappyoak.dsanalyzer.formats.param.paramdef.ParamFieldType

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

    public companion object : ParamRowType<AiStandardInfoRow>("AI_STANDARD_INFO_BANK", 0x80, 1, FIELDS, ::AiStandardInfoRow)
}

private val FIELDS: List<ParamField<AiStandardInfoRow>> = listOf<ParamField<AiStandardInfoRow>>(
    ParamField("radarRange", ParamFieldType.U16, 0x0, null, null, null, "認識距離[m]") { it.radarRange },
    ParamField("radarAngleX", ParamFieldType.U8, 0x2, null, null, null, "認識角度Ｘ[deg]") { it.radarAngleX },
    ParamField("radarAngleY", ParamFieldType.U8, 0x3, null, null, null, "認識角度Y[deg]") { it.radarAngleY },
    ParamField("territorySize", ParamFieldType.U16, 0x4, null, null, null, "縄張り距離[m]") { it.territorySize },
    ParamField("threatBeforeAttackRate", ParamFieldType.U8, 0x6, null, null, null, "攻撃前威嚇率[0〜100]") { it.threatBeforeAttackRate },
    ParamField("forceThreatOnFirstLocked", ParamFieldType.U8, 0x7, null, null, "ON_OFF", "初回認識威嚇") { it.forceThreatOnFirstLocked },
    ParamField("attack1_Distance", ParamFieldType.U16, 0x20, null, null, null, "攻撃１　間合い[m]") { it.attack1_Distance },
    ParamField("attack1_Margin", ParamFieldType.U16, 0x22, null, null, null, "攻撃１　間合い遊び[m]") { it.attack1_Margin },
    ParamField("attack1_Rate", ParamFieldType.U8, 0x24, null, null, null, "攻撃１　割合[0〜100]") { it.attack1_Rate },
    ParamField("attack1_ActionID", ParamFieldType.U8, 0x25, null, null, "ACTION_PATTERN", "攻撃１　種類") { it.attack1_ActionID },
    ParamField("attack1_DelayMin", ParamFieldType.U8, 0x26, null, null, null, "攻撃１　最小遅延時間[frame]") { it.attack1_DelayMin },
    ParamField("attack1_DelayMax", ParamFieldType.U8, 0x27, null, null, null, "攻撃１　最長遅延時間[frame]") { it.attack1_DelayMax },
    ParamField("attack1_ConeAngle", ParamFieldType.U8, 0x28, null, null, null, "攻撃１　攻撃許可円錐の角度[deg]") { it.attack1_ConeAngle },
    ParamField("attack2_Distance", ParamFieldType.U16, 0x30, null, null, null, "攻撃２　間合い[m]") { it.attack2_Distance },
    ParamField("attack2_Margin", ParamFieldType.U16, 0x32, null, null, null, "攻撃２　間合い遊び[m]") { it.attack2_Margin },
    ParamField("attack2_Rate", ParamFieldType.U8, 0x34, null, null, null, "攻撃１　割合[0〜100]") { it.attack2_Rate },
    ParamField("attack2_ActionID", ParamFieldType.U8, 0x35, null, null, "ACTION_PATTERN", "攻撃２　種類") { it.attack2_ActionID },
    ParamField("attack2_DelayMin", ParamFieldType.U8, 0x36, null, null, null, "攻撃2　最小遅延時間[frame]") { it.attack2_DelayMin },
    ParamField("attack2_DelayMax", ParamFieldType.U8, 0x37, null, null, null, "攻撃2　最長遅延時間[frame]") { it.attack2_DelayMax },
    ParamField("attack2_ConeAngle", ParamFieldType.U8, 0x38, null, null, null, "攻撃2　攻撃許可円錐の角度[deg]") { it.attack2_ConeAngle },
    ParamField("attack3_Distance", ParamFieldType.U16, 0x40, null, null, null, "攻撃３　間合い[m]") { it.attack3_Distance },
    ParamField("attack3_Margin", ParamFieldType.U16, 0x42, null, null, null, "攻撃３　間合い遊び[m]") { it.attack3_Margin },
    ParamField("attack3_Rate", ParamFieldType.U8, 0x44, null, null, null, "攻撃１　割合[0〜100]") { it.attack3_Rate },
    ParamField("attack3_ActionID", ParamFieldType.U8, 0x45, null, null, "ACTION_PATTERN", "攻撃３　種類") { it.attack3_ActionID },
    ParamField("attack3_DelayMin", ParamFieldType.U8, 0x46, null, null, null, "攻撃3　最小遅延時間[frame]") { it.attack3_DelayMin },
    ParamField("attack3_DelayMax", ParamFieldType.U8, 0x47, null, null, null, "攻撃3　最長遅延時間[frame]") { it.attack3_DelayMax },
    ParamField("attack3_ConeAngle", ParamFieldType.U8, 0x48, null, null, null, "攻撃3　攻撃許可円錐の角度[deg]") { it.attack3_ConeAngle },
    ParamField("attack4_Distance", ParamFieldType.U16, 0x50, null, null, null, "攻撃４　間合い[m]") { it.attack4_Distance },
    ParamField("attack4_Margin", ParamFieldType.U16, 0x52, null, null, null, "攻撃４　間合い遊び[m]") { it.attack4_Margin },
    ParamField("attack4_Rate", ParamFieldType.U8, 0x54, null, null, null, "攻撃１　割合[0〜100]") { it.attack4_Rate },
    ParamField("attack4_ActionID", ParamFieldType.U8, 0x55, null, null, "ACTION_PATTERN", "攻撃４　種類") { it.attack4_ActionID },
    ParamField("attack4_DelayMin", ParamFieldType.U8, 0x56, null, null, null, "攻撃4　最小遅延時間[frame]") { it.attack4_DelayMin },
    ParamField("attack4_DelayMax", ParamFieldType.U8, 0x57, null, null, null, "攻撃4　最長遅延時間[frame]") { it.attack4_DelayMax },
    ParamField("attack4_ConeAngle", ParamFieldType.U8, 0x58, null, null, null, "攻撃4　攻撃許可円錐の角度[deg]") { it.attack4_ConeAngle }
)
