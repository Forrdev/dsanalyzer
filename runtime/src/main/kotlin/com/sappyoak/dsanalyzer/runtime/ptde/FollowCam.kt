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

    /** X, Y, and Z consecutively, the camera's own position in world space */
    public const val PosX: Int = 0x100

    /**
     * Pitch, yaw, and roll in radians, consecutively.
     */
    public const val Orientation: Int = 0x140

    /**
     * The pitch the camera is being pulled toward, as a lone float.
     */
    public const val TargetPitch: Int = 0x150
}