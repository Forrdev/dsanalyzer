package com.sappyoak.dsanalyzer.runtime.ptde

import com.sappyoak.dsanalyzer.runtime.pointers.GamePointer
import com.sappyoak.dsanalyzer.runtime.pointers.Lifetime

/** The walk to [CharData], which the structures below continue rather than start again */
private val THROUGH_CHARACTER = listOf(0L, 4L, 0L)

public object CharMapData {
    public val Pointer: GamePointer = GamePointer(
        name = "CharMapData",
        base = CharData.Base,
        offsets = THROUGH_CHARACTER + CharData.CharMapDataPointer.toLong(),
        lifetime = Lifetime.World,
        size = 0xF0
    )

    public const val AnimDataPointer: Int = 0x14
    public const val PositionPointer: Int = 0x1C
    public const val Flags: Int = 0xC4
    public const val Warp: Int = 0xC8
    public const val WarpPosition: Int = 0xD0
    public const val WarpAngle: Int = 0xE4

    /** Bits of [Flags] */
    public const val DisableMapHit: Int = 0x00000010
}

public object CharPosData {
    public val Pointer: GamePointer = GamePointer(
        name = "CharPosData",
        base = CharData.Base,
        offsets = THROUGH_CHARACTER + listOf(
            CharData.CharMapDataPointer.toLong(),
            CharMapData.PositionPointer.toLong()
        ),
        lifetime = Lifetime.World,
        size = 0x20
    )

    public const val Angle: Int = 0x4

    /** X, Y and Z consecutively from here */
    public const val Position: Int = 0x10
}

public object AnimData {
    public val Pointer: GamePointer = GamePointer(
        name = "AnimData",
        base = CharData.Base,
        offsets = THROUGH_CHARACTER + listOf(
            CharData.CharMapDataPointer.toLong(),
            CharMapData.AnimDataPointer.toLong()
        ),
        lifetime = Lifetime.World,
        size = 0x200
    )

    public const val PlaySpeed: Int = 0x64
}