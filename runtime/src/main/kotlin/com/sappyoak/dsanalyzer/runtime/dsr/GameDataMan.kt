package com.sappyoak.dsanalyzer.runtime.dsr

import com.sappyoak.dsanalyzer.runtime.pointers.GamePointer
import com.sappyoak.dsanalyzer.runtime.pointers.Lifetime

/** Through [GameDataMan.InGameTimeMillis] */
private const val GAME_DATA_MAN_SIZE: Int = 0xA8

/** Enough to cover [MenuMan.ArrayBase] and a reasonable run of  entries */
private const val MENU_MAN_SIZE: Int = 0x80

public object GameDataMan {
    public val Pointer: GamePointer = GamePointer(
        name = "GameDataMan",
        base = DSRSignatures.GameDataMan,
        offsets = listOf(0L),
        lifetime = Lifetime.World,
        size = GAME_DATA_MAN_SIZE
    )

    public const val TrophyEquipDataPointer: Int = 0x08
    public const val PlayerGameDataPointer: Int = 0x10
    public const val ProfileSummaryPointer: Int = 0x60
    public const val PcOptionDataPointer: Int = 0x68
    public const val QwcDataPointer: Int = 0x70
    public const val InGameTimeMillis: Int = 0xA4
}

public object MenuMan {
    public val Pointer: GamePointer = GamePointer(
        name = "MenuMan",
        base = DSRSignatures.MenuMan,
        offsets = listOf(0L),
        lifetime = Lifetime.World,
        size = MENU_MAN_SIZE
    )

    public const val ArrayBase: Int = 0x30
}