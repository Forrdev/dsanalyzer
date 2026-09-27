package com.sappyoak.dsanalyzer.runtime.ptde

import com.sappyoak.dsanalyzer.native.memory.Signature
import com.sappyoak.dsanalyzer.native.memory.SignatureTarget
import com.sappyoak.dsanalyzer.runtime.pointers.GamePointer
import com.sappyoak.dsanalyzer.runtime.pointers.Lifetime

/** The game's logic tick, which in-game time advances by one of every 1000 / 30 milliseconds */
private const val TICK_MILLIS = 1000.0 / 30.0

public object GameDataMan {
    public val Pointer: GamePointer = GamePointer(
        name = "GameDataMan",
        base = Signature.parse(
            name = "GameDataMan",
            pattern = "8B 0D ?? ?? ?? ?? 8B 41 30 8B 4D 64",
            target = SignatureTarget.Embedded(2)
        ),
        offsets = listOf(0L),
        lifetime = Lifetime.Session,
        size = 0x70
    )

    public const val NewGameCount: Int = 0x3C
    public const val InGameTimeMillis: Int = 0x68

    /** The logic tick in-game time has reached, for talking about a moment rather than a duration */
    public fun frameOf(inGameTimeMillis: Int): Long = (inGameTimeMillis / TICK_MILLIS).toLong()
}