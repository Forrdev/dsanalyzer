package com.sappyoak.dsanalyzer.runtime.dsr

import com.sappyoak.dsanalyzer.runtime.pointers.GamePointer
import com.sappyoak.dsanalyzer.runtime.pointers.Lifetime

public object DSRFunctions {
    public val LevelUp: GamePointer = GamePointer(
        name = "LevelUp",
        base = DSRSignatures.LevelUp,
        offsets = emptyList(),
        lifetime = Lifetime.Process,
        size = 1
    )

    public val Warp: GamePointer = GamePointer(
        name = "Warp",
        base = DSRSignatures.Warp,
        offsets = emptyList(),
        lifetime = Lifetime.Process,
        size = 1
    )

    public val EventMan: GamePointer = GamePointer(
        name = "EventMan",
        base = DSRSignatures.EventMan,
        offsets = listOf(0L),
        lifetime = Lifetime.Session,
        size = 0x10
    )
}
