package com.sappyoak.dsanalyzer.shared.geometry

import kotlinx.serialization.Serializable
import kotlin.math.max
import kotlin.math.min

@Serializable
data class AABB(val min: Vec3, val max: Vec3) {
    val center: Vec3 get() = Vec3(
        (min.x + max.x) / 2f,
        (min.y + max.y) / 2f,
        (min.z + max.z) / 2f
    )

    val size: Vec3 get() = max - min


    fun expand(by: Float): AABB = AABB(
        Vec3(min.x - by, min.y - by, min.z - by),
        Vec3(max.x + by, max.y + by, max.z + by)
    )

    fun contains(other: Vec3): Boolean =
        other.x in min.x..max.x && other.y in min.y..max.y && other.z in min.z..max.z

    infix fun intersects(other: AABB): Boolean =
        min.x <= other.max.x && max.x >= other.min.x &&
                min.y <= other.max.y && max.y >= other.min.y &&
                min.z <= other.max.z && max.z >= other.min.z

    infix fun union(other: AABB): AABB = AABB(
        Vec3(min(min.x, other.min.x), min(min.y, other.min.y), min(min.z, other.min.z)),
        Vec3(max(max.x, other.max.x), max(max.y, other.max.y), max(max.z, other.max.z))
    )
}