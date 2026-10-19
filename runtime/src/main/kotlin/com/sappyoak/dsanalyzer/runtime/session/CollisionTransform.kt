package com.sappyoak.dsanalyzer.runtime.session

import kotlin.math.abs

import com.sappyoak.dsanalyzer.shared.math.Aabb
import com.sappyoak.dsanalyzer.shared.math.Mat4
import com.sappyoak.dsanalyzer.shared.math.Vec3

/** One local point in world space. Row-vector, matching [Mat4.transform] */
internal fun Mat4.place(x: Float, y: Float, z: Float): Vec3 = Vec3(
    x * r0.x + y * r1.x + z * r2.x + r3.x,
    x * r0.y + y * r1.y + z * r2.y + r3.y,
    x * r0.z + y * r1.z + z * r2.z + r3.z
)

/**
 * An axis-aligned box that still contains the shape after [transform]
 */
internal fun Aabb.transformedBy(transform: Mat4): Aabb {
    val moved = transform.place(center.x, center.y, center.z)

    val spread = Vec3(
        abs(transform.r0.x) * halfExtents.x +
                abs(transform.r1.x) * halfExtents.y +
                abs(transform.r2.x) * halfExtents.z,
        abs(transform.r0.y) * halfExtents.x +
                abs(transform.r1.y) * halfExtents.y +
                abs(transform.r2.y) * halfExtents.z,
        abs(transform.r0.z) * halfExtents.x +
                abs(transform.r1.z) * halfExtents.y +
                abs(transform.r2.z) * halfExtents.z
    )

    return Aabb(center = moved, halfExtents = spread)
}