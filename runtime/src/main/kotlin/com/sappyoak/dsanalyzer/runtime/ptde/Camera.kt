package com.sappyoak.dsanalyzer.runtime.ptde

import com.sappyoak.dsanalyzer.runtime.pointers.GamePointer
import com.sappyoak.dsanalyzer.runtime.pointers.Lifetime

public object FollowCam {
    public val Pointer: GamePointer = GamePointer(
        name = "ChrFollowCam",
        base = PTDESignatures.ChrFollowCam,
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

public object FrpgCam {
    /**
     * Camera-to-world: four rows of four floats, row-major.
     *
     * The position is row 3 and is deliberately not declared separately, one offset that can go
     * stale beats two that can disagree.
     */
    public const val Transform: Int = 0x10

    /** Vertical, in radians. Read rather than assumed, so DSfix and resolution overrides are free */
    public const val FieldOfView: Int = 0x50
    public const val Aspect: Int = 0x54
    public const val Near: Int = 0x58
    public const val Far: Int = 0x5C
}
