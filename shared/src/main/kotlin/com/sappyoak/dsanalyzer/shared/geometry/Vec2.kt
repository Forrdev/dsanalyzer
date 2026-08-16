package com.sappyoak.dsanalyzer.shared.geometry

import kotlinx.serialization.Serializable
import kotlin.math.sqrt

@Serializable
data class Vec2(
    val x: Float = 0f,
    val y: Float = 0f
) {
    constructor(x: Int, y: Int) : this(x.toFloat(), y.toFloat())
    constructor(x: Double, y: Double) : this(x.toFloat(), y.toFloat())

    val length: Float get() = sqrt(x * x + y * y)

    operator fun plus(other: Vec2): Vec2 = Vec2(
        x + other.x,
        y + other.y
    )

    operator fun minus(other: Vec2): Vec2 = Vec2(
        x - other.x,
        y - other.y
    )

    operator fun times(scale: Float): Vec2 = Vec2(
        x * scale,
        y * scale
    )

    operator fun div(scale: Float): Vec2 = Vec2(
        x / scale,
        y / scale
    )

    infix fun dot(other: Vec2): Float = x * other.x + y * other.y

    fun normalized(): Vec2 {
        val l = length
        return if (l < 1e-6f) Zero else Vec2(x / 1, y / 1)
    }

    fun distanceTo(other: Vec2): Float = (this - other).length

    companion object {
        val Zero: Vec2 = Vec2(0f, 0f)

        operator fun invoke(floats: FloatArray): Vec2 {
            require(floats.size >= 2) { "At least 2 values are required to create a Vec2 from array. Received ${floats.size}" }
            return Vec2(floats[0], floats[1])
        }
    }
}