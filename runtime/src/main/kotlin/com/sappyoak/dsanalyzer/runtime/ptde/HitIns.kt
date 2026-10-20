package com.sappyoak.dsanalyzer.runtime.ptde

import com.sappyoak.dsanalyzer.runtime.pointers.GamePointer
import com.sappyoak.dsanalyzer.runtime.pointers.Lifetime

public object HitIns {
    public val Pointer: GamePointer = GamePointer(
        name = "HitIns",
        base = PTDESignatures.WorldChrMan,
        offsets = THROUGH_CHARACTER + ChrIns.HitInsPointer.toLong(),
        lifetime = Lifetime.World,
        size = 0x20
    )

    public const val OwnerPointer: Int = 0x08
    public const val EntryIndex: Int = 0x0C
    public const val BlockIndex: Int = 0x0F
}