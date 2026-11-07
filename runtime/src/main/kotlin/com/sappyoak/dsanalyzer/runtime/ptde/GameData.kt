package com.sappyoak.dsanalyzer.runtime.ptde

import com.sappyoak.dsanalyzer.runtime.pointers.GamePointer
import com.sappyoak.dsanalyzer.runtime.pointers.Lifetime

public object GameDataMan {
    public val Pointer: GamePointer = GamePointer(
        name = "GameDataMan",
        base = PTDESignatures.GameDataMan,
        offsets = listOf(0L),
        lifetime = Lifetime.Session,
        size = 0x80
    )

    public const val PlayerGameDataPointer: Int = 0x08
    public const val NewGameCount: Int = 0x3C
    public const val InGameTimeMillis: Int = 0x68
}
