package com.sappyoak.dsanalyzer.shared.math

public data class Vec4(
    public val x: Float,
    public val y: Float,
    public val z: Float,
    public val w: Float
) {
    public val xyz: Vec3 get() = Vec3(x, y, z)

    public companion object {
        public val Zero: Vec4 = Vec4(0f, 0f, 0f, 0f)
    }
}

/** This position as a homogenous point */
public fun Vec3.asPoint(): Vec4 = Vec4(x, y, z, 1f)

public fun Vec3.asDirection(): Vec4 = Vec4(x, y, z, 0f)