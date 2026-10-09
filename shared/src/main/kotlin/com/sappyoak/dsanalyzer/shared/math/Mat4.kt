package com.sappyoak.dsanalyzer.shared.math

import kotlin.math.abs
import kotlin.math.tan


/**
 * A 4x4 transform, row-major and applied to row vectors. "p' = p * M"
 *
 * The convention is not a preference is it the games. 'NS_FRPG::FrpgCam' stores it
 * camera-to-world transform as four consecutive rows with the **position** in row 3.
 */
public data class Mat4(
    public val r0: Vec4,
    public val r1: Vec4,
    public val r2: Vec4,
    public val r3: Vec4
) {
    /** The inverse, valid only when the 3x3 part is orthonormal */
    public val orthonormalInverse: Mat4 get() = Mat4(
        r0 = Vec4(r0.x, r1.x, r2.x, 0f),
        r1 = Vec4(r0.y, r1.y, r2.y, 0f),
        r2 = Vec4(r0.z, r1.z, r2.z, 0f),
        r3 = Vec4(
            x = -(r3.x * r0.x + r3.y * r0.y + r3.z * r0.z),
            y = -(r3.x * r1.x + r3.y * r1.y + r3.z * r1.z),
            z = -(r3.x * r2.x + r3.y * r2.y + r3.z * r2.z),
            w = 1f
        )
    )

    public operator fun times(other: Mat4): Mat4 = Mat4(
        r0 = other.transform(r0),
        r1 = other.transform(r1),
        r2 = other.transform(r2),
        r3 = other.transform(r3)
    )

    public fun transform(point: Vec4): Vec4 = Vec4(
        x = point.x * r0.x + point.y * r1.x + point.z * r2.x + point.w * r3.x,
        y = point.x * r0.y + point.y * r1.y + point.z * r2.y + point.w * r3.y,
        z = point.x * r0.z + point.y * r1.z + point.z * r2.z + point.w * r3.z,
        w = point.x * r0.w + point.y * r1.w + point.z * r2.w + point.w * r3.w
    )

    /**
     * Whether the 3x3 part is orthonormal to [tolerance].
     */
    public fun isOrthonormal(tolerance: Float = 1e-4f): Boolean {
        val rows = listOf(r0.xyz, r1.xyz, r2.xyz)
        val unit = rows.all { abs(it.dot(it) - 1f) <= tolerance }
        val perpendicular = abs(rows[0].dot(rows[1])) <= tolerance &&
                abs(rows[0].dot(rows[2])) <= tolerance &&
                abs(rows[1].dot(rows[2])) <= tolerance
        return unit && perpendicular
    }

    public companion object {
        public val Identity: Mat4 = Mat4(
            r0 = Vec4(1f, 0f, 0f, 0f),
            r1 = Vec4(0f, 1f, 0f, 0f),
            r2 = Vec4(0f, 0f, 1f, 0f),
            r3 = Vec4(0f, 0f, 0f, 1f)
        )

        /**
         * A perspective project in the left-handed convention. Clip 'w' carries view-space
         * depth and clip 'z / w' runs 0 at the near plane to 1 at the far plane
         *
         * [fovRadians] is the **vertical** field of view.
         */
        public fun perspective(
            fovRadians: Float,
            aspect: Float,
            near: Float,
            far: Float
        ): Mat4 {
            val height = 1f / tan(fovRadians / 2f)
            val depth = far / (far - near)

            return Mat4(
                r0 = Vec4(height / aspect, 0f, 0f, 0f),
                r1 = Vec4(0f, height, 0f, 0f),
                r2 = Vec4(0f, 0f, depth, 1f),
                r3 = Vec4(0f, 0f, -near * depth, 0f)
            )
        }
    }
}