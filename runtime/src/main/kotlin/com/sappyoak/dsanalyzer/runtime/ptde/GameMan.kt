package com.sappyoak.dsanalyzer.runtime.ptde

import com.sappyoak.dsanalyzer.runtime.pointers.GamePointer
import com.sappyoak.dsanalyzer.runtime.pointers.Lifetime

public object GameMan {
    public val Pointer: GamePointer = GamePointer(
        name = "GameMan",
        base = PTDESignatures.GameMan,
        offsets = listOf(0L),
        lifetime = Lifetime.Session,
        size = 0xB9C
    )

    public const val LastBonfire: Int = 0xB04

    /** X, Y and Z consecutively from here */
    public const val StablePosition: Int = 0xB70
    public const val StableAngle: Int = 0xB84
}