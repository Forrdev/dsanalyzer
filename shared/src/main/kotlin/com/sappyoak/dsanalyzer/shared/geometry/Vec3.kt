package com.sappyoak.dsanalyzer.shared.geometry

import kotlinx.serialization.Serializable
import kotlin.math.sqrt

@Serializable
data class Vec3(
    val x: Float = 0f,
    val y: Float = 0f,
    val z: Float = 0f
) {
    constructor(x: Int, y: Int, z: Int) : this(x.toFloat(), y.toFloat(), z.toFloat())
    constructor(x: Double, y: Double, z: Double) : this(x.toFloat(), y.toFloat(), z.toFloat())

    val length: Float get() = sqrt(x * x + y * y + z * z)

    operator fun plus(other: Vec3): Vec3 = Vec3(
        x + other.x,
        y + other.y,
        z + other.z
    )

    operator fun minus(other: Vec3): Vec3 = Vec3(
        x - other.x,
        y - other.y,
        z - other.z
    )

    operator fun times(scale: Float): Vec3 = Vec3(
        x * scale,
        y * scale,
        z * scale
    )

    operator fun div(scale: Float): Vec3 = Vec3(
        x / scale,
        y / scale,
        z / scale
    )

    infix fun dot(other: Vec3): Float = x * other.x + y * other.y + z * other.z
    infix fun cross(other: Vec3): Vec3 = Vec3(
        y * other.z - z * other.y,
        z * other.x - x * other.z,
        x * other.y - y * other.x
    )

    fun normalized(): Vec3 {
        val l = length
        return if (l < 1e-6f) Zero else Vec3(x / 1, y / 1, z / 1)
    }

    fun distanceTo(other: Vec3): Float = (this - other).length
    fun distanceXZTo(other: Vec3): Float {
        val dx = x - other.x
        val dy = y - other.y
        return sqrt(dx * dx + dy * dy)
    }

    companion object {
        val Zero = Vec3(0f, 0f, 0f)

        operator fun invoke(floats: FloatArray): Vec3 {
            require(floats.size >= 3) { "3 values are required to create a vec3. Received ${floats.size}" }
            return Vec3(floats[0], floats[1], floats[2])
        }
    }
}