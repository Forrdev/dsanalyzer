package com.sappyoak.dsanalyzer.runtime.dsr

import com.sappyoak.dsanalyzer.runtime.pointers.GamePointer
import com.sappyoak.dsanalyzer.runtime.pointers.Lifetime

public object EnemyHpBars {
    public const val COUNT: Int = 8
    public const val SIZE: Int = 0x24

    public val Pointer: GamePointer = GamePointer(
        name = "EnemyHpBars",
        base = DSRSignatures.MenuMan,
        offsets = listOf(0L),
        lifetime = Lifetime.World,
        size = COUNT * SIZE,
        displacement = 0xDE8
    )

    /** -1 for a free slot */
    public const val Handle: Int = 0x00
    /** Screen x, y and depth, in the 1280 by 720 menu space */
    public const val Screen: Int = 0x04
    public const val Shown: Int = 0x10
    public const val Damage: Int = 0x14
    /** Two timers, one after the other */
    public const val Timers: Int = 0x18
    public const val Visible: Int = 0x20
}
