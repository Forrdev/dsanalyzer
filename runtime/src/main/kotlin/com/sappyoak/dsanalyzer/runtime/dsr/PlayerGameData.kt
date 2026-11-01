package com.sappyoak.dsanalyzer.runtime.dsr

import com.sappyoak.dsanalyzer.runtime.pointers.GamePointer
import com.sappyoak.dsanalyzer.runtime.pointers.Lifetime

public object PlayerGameData {
    public val Pointer: GamePointer = GamePointer(
        name = "PlayerGameData",
        base = DSRSignatures.GameDataMan,
        offsets = listOf(0L, 0x10L),
        lifetime = Lifetime.World,
        size = 0x120
    )

    public const val PlayerNo: Int = 0x010

    public const val Hp: Int = 0x014

    /** The effective max, which is what the in-game bar is drawn against */
    public const val MaxHp: Int = 0x018

    /** The max before equipment and effects */
    public const val BaseMaxHp: Int = 0x01C

    public const val Mp: Int = 0x020
    public const val BaseMaxMp: Int = 0x024
    public const val MaxMp: Int = 0x028

    public const val Sp: Int = 0x030
    public const val MaxSp: Int = 0x034
    public const val BaseMaxSp: Int = 0x038

    /** The eight levelled stats, stride 8 */
    public const val Stats: Int = 0x040
    public const val StatStride: Int = 0x08

    public const val Humanity: Int = 0x084
    public const val Durability: Int = 0x088
    public const val SoulLevel: Int = 0x090
    public const val Souls: Int = 0x094
    public const val SoulMemory: Int = 0x098

    public const val Gender: Int = 0x0CA
    public const val ArcheType: Int = 0x0CE
    public const val Appearance: Int = 0x0CF
    public const val Gift: Int = 0x0D0

    public const val PoisonResist: Int = 0x100
    public const val BloodResist: Int = 0x104
    public const val DiseaseResist: Int = 0x108
    public const val CurseResist: Int = 0x10C
}
