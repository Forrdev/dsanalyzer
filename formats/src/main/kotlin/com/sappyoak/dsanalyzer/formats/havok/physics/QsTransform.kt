package com.sappyoak.dsanalyzer.formats.havok.physics

import com.sappyoak.dsanalyzer.shared.math.Vec3
import com.sappyoak.dsanalyzer.shared.math.Vec4

/** An 'hkQsTransform': scale, then rotation by a unit quaternion 'x, y, z, w', then translation */
internal class QsTransform(private val translation: Vec4, private val rotation: Vec4, private val scale: Vec4) {
    private val identity = translation.x == 0f && translation.y == 0f && translation.z == 0f &&
            rotation.x == 0f && rotation.y == 0f && rotation.z == 0f && rotation.w == 1f &&
            scale.x == 1f && scale.y == 1f && scale.z == 1f

    /** Writes [point] transformed into [out] at [at] */
    fun apply(point: Vec3, out: FloatArray, at: Int) {
        if (identity) {
            out[at] = point.x; out[at + 1] = point.y; out[at + 2] = point.z
            return
        }
        val x = point.x * scale.x
        val y = point.y * scale.y
        val z = point.z * scale.z

        // v + 2q x (q x v + wv), the quaternion rotation without building a matrix
        val cx = rotation.y * z - rotation.z * y + rotation.w * x
        val cy = rotation.z * x - rotation.x * z + rotation.w * y
        val cz = rotation.x * y - rotation.y * x + rotation.w * z
        out[at] = x + 2f * (rotation.y * cz - rotation.z * cy) + translation.x
        out[at + 1] = y + 2f * (rotation.z * cx - rotation.x * cz) + translation.y
        out[at + 2] = z + 2f * (rotation.x * cy - rotation.y * cx) + translation.z
    }
}
