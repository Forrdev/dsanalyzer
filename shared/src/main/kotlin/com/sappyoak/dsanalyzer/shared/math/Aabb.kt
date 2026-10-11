package com.sappyoak.dsanalyzer.shared.math

import kotlin.math.abs

/**
 * An axis-aligned bounding box, held as a center and half-extents
 *
 * That pairing rather than min/max because it is how the game stores it.
 * A Havok mesh shape keeps 'aabbCenter' and 'aabbHalfExtents' as two vectors
 */
public data class Aabb(
    public val center: Vec3,
    public val halfExtents: Vec3
) {
    public val min: Vec3 get() = Vec3(
        center.x - halfExtents.x,
        center.y - halfExtents.y,
        center.z - halfExtents.z
    )

    public val max: Vec3 get() = Vec3(
        center.x + halfExtents.x,
        center.y + halfExtents.y,
        center.z + halfExtents.z
    )

    /**
     * Whether this box encloses no volume
     *
     * Negative extents count as empty rather than being normalized because
     * a negative extent is not a box drawn backwards, its a read that landed
     * on something that is not an AABB
     */
    public val isEmpty: Boolean
        get() = halfExtents.x <= 0f || halfExtents.y <= 0f || halfExtents.z <= 0f

    public operator fun contains(point: Vec3): Boolean =
        abs(point.x - center.x) <= halfExtents.x &&
                abs(point.y - center.y) <= halfExtents.y &&
                abs(point.z - center.z) <= halfExtents.z

    /**
     * The squared distance from [point] to the nearest surface of this
     * box, or '0' inside it
     */
    public fun distanceSquaredTo(point: Vec3): Float {
        val dx = axisDistance(point.x, center.x, halfExtents.x)
        val dy = axisDistance(point.y, center.y, halfExtents.y)
        val dz = axisDistance(point.z, center.z, halfExtents.z)
        return dx * dx + dy * dy + dz * dz
    }

    public fun expandedBy(margin: Float): Aabb = Aabb(
        center = center,
        halfExtents = Vec3(halfExtents.x + margin, halfExtents.y + margin, halfExtents.z + margin)
    )

    private fun axisDistance(value: Float, middle: Float, extent: Float): Float {
        val offset = abs(value - middle) - extent
        return if (offset > 0f) offset else 0f
    }

    public companion object {
        public val Empty: Aabb = Aabb(Vec3.Zero, Vec3.Zero)

        /** The box bounded by two corners, in either order */
        public fun between(a: Vec3, b: Vec3): Aabb {
            val center = Vec3((a.x + b.x) / 2f, (a.y + b.y) / 2f, (a.z + b.z) / 2f)
            return Aabb(
                center = center,
                halfExtents = Vec3(
                    abs(a.x - b.x) / 2f,
                    abs(a.y - b.y) / 2f,
                    abs(a.z - b.z) / 2f
                )
            )
        }
    }
}