// GENERATED FILE - do not edit. Regenerate with:
//
//     ./gradlew :tools:generateParamDefs -Pptde=<DATA directory> -Pdsr=<DATA directory>
//
// Source: paramdef.paramdefbnd, GameAreaParam.paramdef, read from the installation

@file:Suppress("SpellCheckingInspection")

package com.sappyoak.dsanalyzer.formats.param.defs

import com.sappyoak.dsanalyzer.formats.param.ParamField
import com.sappyoak.dsanalyzer.formats.param.ParamRow
import com.sappyoak.dsanalyzer.formats.param.ParamRowData
import com.sappyoak.dsanalyzer.formats.param.ParamRowType
import com.sappyoak.dsanalyzer.formats.param.paramdef.ParamFieldType

/** `GAME_AREA_PARAM_ST`: PrepareToDie 0x20, Remastered 0x20 a row, data version 1 */
public class GameAreaParamRow(row: ParamRowData) : ParamRow(row) {
    /** `u32` at 0x0 */
    public val bonusSoul_single: Long get() = bytes.u32(0x0)

    /** `u32` at 0x4 */
    public val bonusSoul_multi: Long get() = bytes.u32(0x4)

    /** `s32` at 0x8 */
    public val humanityPointCountFlagIdTop: Int get() = bytes.s32(0x8)

    /** `u16` at 0xC */
    public val humanityDropPoint1: Int get() = bytes.u16(0xC)

    /** `u16` at 0xE */
    public val humanityDropPoint2: Int get() = bytes.u16(0xE)

    /** `u16` at 0x10 */
    public val humanityDropPoint3: Int get() = bytes.u16(0x10)

    /** `u16` at 0x12 */
    public val humanityDropPoint4: Int get() = bytes.u16(0x12)

    /** `u16` at 0x14 */
    public val humanityDropPoint5: Int get() = bytes.u16(0x14)

    /** `u16` at 0x16 */
    public val humanityDropPoint6: Int get() = bytes.u16(0x16)

    /** `u16` at 0x18 */
    public val humanityDropPoint7: Int get() = bytes.u16(0x18)

    /** `u16` at 0x1A */
    public val humanityDropPoint8: Int get() = bytes.u16(0x1A)

    /** `u16` at 0x1C */
    public val humanityDropPoint9: Int get() = bytes.u16(0x1C)

    /** `u16` at 0x1E */
    public val humanityDropPoint10: Int get() = bytes.u16(0x1E)

    public companion object : ParamRowType<GameAreaParamRow>("GAME_AREA_PARAM_ST", 0x20, 1, FIELDS, ::GameAreaParamRow)
}

private val FIELDS: List<ParamField<GameAreaParamRow>> = listOf<ParamField<GameAreaParamRow>>(
    ParamField("bonusSoul_single", ParamFieldType.U32, 0x0, null, null, null, "シングル時クリアボーナスソウル量") { it.bonusSoul_single },
    ParamField("bonusSoul_multi", ParamFieldType.U32, 0x4, null, null, null, "マルチプレイ時クリアボーナスソウル量") { it.bonusSoul_multi },
    ParamField("humanityPointCountFlagIdTop", ParamFieldType.S32, 0x8, null, null, null, "人間性ドロップポイントカウント先頭フラグID") { it.humanityPointCountFlagIdTop },
    ParamField("humanityDropPoint1", ParamFieldType.U16, 0xC, null, null, null, "人間性ドロップ必要ポイント1") { it.humanityDropPoint1 },
    ParamField("humanityDropPoint2", ParamFieldType.U16, 0xE, null, null, null, "人間性ドロップ必要ポイント2") { it.humanityDropPoint2 },
    ParamField("humanityDropPoint3", ParamFieldType.U16, 0x10, null, null, null, "人間性ドロップ必要ポイント3") { it.humanityDropPoint3 },
    ParamField("humanityDropPoint4", ParamFieldType.U16, 0x12, null, null, null, "人間性ドロップ必要ポイント4") { it.humanityDropPoint4 },
    ParamField("humanityDropPoint5", ParamFieldType.U16, 0x14, null, null, null, "人間性ドロップ必要ポイント5") { it.humanityDropPoint5 },
    ParamField("humanityDropPoint6", ParamFieldType.U16, 0x16, null, null, null, "人間性ドロップ必要ポイント6") { it.humanityDropPoint6 },
    ParamField("humanityDropPoint7", ParamFieldType.U16, 0x18, null, null, null, "人間性ドロップ必要ポイント7") { it.humanityDropPoint7 },
    ParamField("humanityDropPoint8", ParamFieldType.U16, 0x1A, null, null, null, "人間性ドロップ必要ポイント8") { it.humanityDropPoint8 },
    ParamField("humanityDropPoint9", ParamFieldType.U16, 0x1C, null, null, null, "人間性ドロップ必要ポイント9") { it.humanityDropPoint9 },
    ParamField("humanityDropPoint10", ParamFieldType.U16, 0x1E, null, null, null, "人間性ドロップ必要ポイント10") { it.humanityDropPoint10 }
)
