package com.sappyoak.dsanalyzer.overlay

import kotlin.math.hypot

import com.sappyoak.dsanalyzer.runtime.session.attack.HitShape
import com.sappyoak.dsanalyzer.shared.math.Mat4
import com.sappyoak.dsanalyzer.shared.math.Vec3

/** A sphere as it lands on the screen, in pixels */
internal data class ScreenCircle(val x: Float, val y: Float, val radius: Float)

/** A hit shape's two ends on the screen; a sphere's are the same circle */
internal data class ScreenEnds(val a: ScreenCircle, val b: ScreenCircle)

/**
 * Projects hit shapes onto a canvas [width] by [height]. Anything with a point behind the eye is
 * dropped whole rather than clipped: a hit is small, so one that straddles the eye is rare and brief
 */
internal class HitProjector(
    private val worldToClip: Mat4,
    private val eye: Vec3,
    private val width: Int,
    private val height: Int
) {
    fun ends(shape: HitShape): ScreenEnds? = when (shape) {
        is HitShape.Sphere -> circle(shape.center, shape.radius)?.let { ScreenEnds(it, it) }
        is HitShape.Capsule -> {
            val a = circle(shape.a, shape.radius)
            val b = circle(shape.b, shape.radius)
            if (a == null || b == null) null else ScreenEnds(a, b)
        }
    }

    /** The sphere at [center], its radius measured across the line of sight so it never foreshortens */
    private fun circle(center: Vec3, radius: Float): ScreenCircle? {
        val (x, y) = project(center) ?: return null
        val (edgeX, edgeY) = project(center.across(radius)) ?: return null
        return ScreenCircle(x, y, hypot(edgeX - x, edgeY - y))
    }

    /** [radius] from here, level and square to the line from the eye */
    private fun Vec3.across(radius: Float): Vec3 {
        val dx = x - eye.x
        val dz = z - eye.z
        val level = hypot(dx, dz)
        // Straight above or below the eye, where any level direction is square to the sight line
        if (level < MIN_LEVEL) return Vec3(x + radius, y, z)
        return Vec3(x - dz / level * radius, y, z + dx / level * radius)
    }

    /** [point]'s pixel, or null behind the eye */
    private fun project(point: Vec3): Pair<Float, Float>? {
        val w = worldToClip.clipW(point.x, point.y, point.z)
        if (w <= MIN_W) return null
        return screenX(worldToClip.clipX(point.x, point.y, point.z), w, width) to
                screenY(worldToClip.clipY(point.x, point.y, point.z), w, height)
    }

    private companion object {
        const val MIN_LEVEL = 1e-4f
    }
}
