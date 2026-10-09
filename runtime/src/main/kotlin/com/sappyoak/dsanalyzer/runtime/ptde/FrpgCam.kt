package com.sappyoak.dsanalyzer.runtime.ptde

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