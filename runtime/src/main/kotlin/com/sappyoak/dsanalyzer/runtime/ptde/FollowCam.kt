package com.sappyoak.dsanalyzer.runtime.ptde

import com.sappyoak.dsanalyzer.native.memory.Signature
import com.sappyoak.dsanalyzer.native.memory.SignatureTarget
import com.sappyoak.dsanalyzer.runtime.pointers.GamePointer
import com.sappyoak.dsanalyzer.runtime.pointers.Lifetime

public object FollowCam {
    public val Pointer: GamePointer = GamePointer(
        name = "ChrFollowCam",
        base = Signature.parse(
            name = "ChrFollowCam",
            pattern = "D9 45 08 A1 ?? ?? ?? ?? 51 D9 1C 24 50",
            target = SignatureTarget.Embedded(4)
        ),
        offsets = listOf(0L, 0x3CL, 0x60L),
        /** Deliberately never cached as this walk is the test for whether a world is loaded */
        lifetime = Lifetime.Volatile,
        size = 0x200
    )

    public const val RotX: Int = 0xE0
    public const val RotY: Int = 0xE4
    public const val RotZ: Int = 0xE8

    public const val PosX: Int = 0x100
    public const val PosY: Int = 0x104
    public const val PosZ: Int = 0x108

    public const val CamRotX: Int = 0x140
    public const val CamRotY: Int = 0x144
    public const val CamRotZ: Int = 0x148

    public const val TargetRotX: Int = 0x150
    public const val TargetRotY: Int = 0x154
    public const val TargetRotZ: Int = 0x158
}