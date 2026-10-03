package com.sappyoak.dsanalyzer.runtime.ptde

import com.sappyoak.dsanalyzer.game.GameEdition
import com.sappyoak.dsanalyzer.native.memory.Signature
import com.sappyoak.dsanalyzer.native.memory.SignatureTarget
import com.sappyoak.dsanalyzer.runtime.pointers.GamePointer
import com.sappyoak.dsanalyzer.runtime.pointers.Lifetime

/**
 * Counted in integers on purpose.
 *
 * Dividing by a fractional tick length looses a frame exactly on the boundaries. 66000ms at 30HZ
 * comes out as 1979.9999999999998, and truncating that reports frame 1979 for a moment that is
 * frame 1980
 */
private val TICK_HZ = GameEdition.PrepareToDie.logicTickHz

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
        size = 0x80
    )

    public const val NewGameCount: Int = 0x3C
    public const val InGameTimeMillis: Int = 0x68

    /** The logic tick in-game time has reached, for talking about a moment rather than a duration */
    public fun frameOf(inGameTimeMillis: Int): Long =
        inGameTimeMillis.toLong() * TICK_HZ / 1000
}