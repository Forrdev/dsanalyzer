package com.sappyoak.dsanalyzer.runtime.ptde

import com.sappyoak.dsanalyzer.runtime.pointers.GamePointer
import com.sappyoak.dsanalyzer.runtime.pointers.Lifetime

public object GameMan {
    public val Pointer: GamePointer = GamePointer(
        name = "GameMan",
        base = PTDESignatures.GameMan,
        offsets = listOf(0L),
        lifetime = Lifetime.Session,
        size = 0xB90
    )

    /** The AI and combat generator's two state words, `s1` then `s2` (`ptde-rng.md`) */
    public const val RngState: Int = 0x58

    /** The MT19937 instance, [TwisterSize] bytes: seed, `left`, a cursor into its own state, `state[624]` */
    public const val Twister: Int = 0x60
    public const val TwisterSize: Int = 0x9CC
    public const val TwisterLeft: Int = 0x04
    public const val TwisterCursor: Int = 0x08
    public const val TwisterState: Int = 0x0C
    public const val LastBonfire: Int = 0xB04

    /** X, Y and Z consecutively from here */
    public const val StablePosition: Int = 0xB70
    public const val StableAngle: Int = 0xB84
}
