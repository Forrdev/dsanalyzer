package com.sappyoak.dsanalyzer.overlay

import kotlin.math.ceil
import kotlin.math.hypot
import kotlin.math.max
import kotlin.math.min

import com.sappyoak.dsanalyzer.shared.math.Mat4

/** Floats per span: two screen `x, y` endpoints */
internal const val SPAN_COMPONENTS: Int = 4

/** Pixels between depth samples along an edge: one per cell, the resolution the buffer has anyway */
private const val STEP: Float = CELL_SIZE.toFloat()

/**
 * How far behind solid collision a volume edge may sit and still count as in front, in world units
 * and as a share of its depth. Volumes are commonly flush with the geometry they cover, a death
 * camera over a floor or an exit in a doorway, and without slack those edges would flicker
 */
private const val MIN_BIAS: Float = 0.1f
private const val BIAS_SHARE: Float = 0.01f

/**
 * The visible stretches of volume edges, as screen-space segments.
 *
 * A volume is never written into the [OcclusionBuffer], so it can never hide a marker. What it gets
 * instead is a depth test against the buffer, sample by sample along each edge: the stretches in
 * front of solid collision are kept and the rest dropped. Lines rather than triangles because a
 * volume is a few large triangles, and anything ordered per triangle orders them wrongly.
 */
internal class VolumeSpans(private val capacity: Int) {
    val coords: FloatArray = FloatArray(capacity * SPAN_COMPONENTS)

    var count: Int = 0
        private set

    val isFull: Boolean get() = count >= capacity

    private val clip = FloatArray(8)
    /** The on-screen part of the edge being traced, as a range of its parameter */
    private var from = 0f
    private var to = 1f

    fun reset() {
        count = 0
    }

    /** Traces every edge in [edges]; false once [capacity] is reached, as [TriangleProjector.append] */
    fun trace(
        edges: FloatArray,
        worldToClip: Mat4,
        width: Int,
        height: Int,
        occlusion: OcclusionBuffer
    ): Boolean {
        var at = 0
        while (at + EDGE_COMPONENTS <= edges.size) {
            if (isFull) return false
            for (end in 0..1) {
                val x = edges[at + end * 3]
                val y = edges[at + end * 3 + 1]
                val z = edges[at + end * 3 + 2]
                clip[end * 4] = worldToClip.clipX(x, y, z)
                clip[end * 4 + 1] = worldToClip.clipY(x, y, z)
                clip[end * 4 + 2] = worldToClip.clipZ(x, y, z)
                clip[end * 4 + 3] = worldToClip.clipW(x, y, z)
            }
            if (clipToNearPlane()) sample(width, height, occlusion)
            at += EDGE_COMPONENTS
        }
        return true
    }

    /** Moves whichever end is behind the near plane onto it; false when both are behind */
    private fun clipToNearPlane(): Boolean {
        val za = clip[2]
        val zb = clip[6]
        if (za < NEAR_PLANE && zb < NEAR_PLANE) return false
        if (za >= NEAR_PLANE && zb >= NEAR_PLANE) return true

        val t = (NEAR_PLANE - za) / (zb - za)
        val moved = if (za < NEAR_PLANE) 0 else 4
        for (i in 0..3) clip[moved + i] = clip[i] + (clip[4 + i] - clip[i]) * t
        return true
    }

    private fun sample(width: Int, height: Int, occlusion: OcclusionBuffer) {
        val wa = clip[3]
        val wb = clip[7]
        if (wa < MIN_W || wb < MIN_W) return

        val ax = screenX(clip[0], wa, width)
        val ay = screenY(clip[1], wa, height)
        val dx = screenX(clip[4], wb, width) - ax
        val dy = screenY(clip[5], wb, height) - ay
        val inverseA = 1f / wa
        val inverseB = 1f / wb

        // Only the on-screen stretch is sampled: an edge clipped at the near plane can run thousands
        // of pixels off the side, and every one of those samples would be wasted
        if (!clipToScreen(ax, ay, dx, dy, width, height)) return
        val steps = max(1, ceil(hypot(dx, dy) * (to - from) / STEP).toInt())

        var start = -1f
        var last = -1f
        for (i in 0..steps) {
            val t = from + (to - from) * i / steps
            // Linear in 1 / w across the screen, which is what makes the depth perspective-correct
            val depth = 1f / (inverseA + (inverseB - inverseA) * t)
            val solid = occlusion.depthAt(ax + dx * t, ay + dy * t)

            if (depth <= solid + max(MIN_BIAS, depth * BIAS_SHARE)) {
                if (start < 0f) start = t
                last = t
            } else if (start >= 0f) {
                emit(ax, ay, dx, dy, start, last)
                start = -1f
            }
        }
        if (start >= 0f) emit(ax, ay, dx, dy, start, last)
    }

    /**
     * Narrows [from] and [to] to the part of `a + t * d` on a [width] by [height] screen:
     * Liang–Barsky, one slab test per screen edge. False when none of it is on screen.
     */
    private fun clipToScreen(ax: Float, ay: Float, dx: Float, dy: Float, width: Int, height: Int): Boolean {
        from = 0f
        to = 1f
        return slab(-dx, ax) && slab(dx, width - ax) && slab(-dy, ay) && slab(dy, height - ay)
    }

    /** One side of the screen, as `p * t <= q`; false once the range is empty */
    private fun slab(p: Float, q: Float): Boolean {
        if (p == 0f) return q >= 0f
        val r = q / p
        if (p < 0f) from = max(from, r) else to = min(to, r)
        return from <= to
    }

    private fun emit(ax: Float, ay: Float, dx: Float, dy: Float, start: Float, end: Float) {
        // A single visible sample has no length to draw
        if (end <= start || isFull) return
        val at = count * SPAN_COMPONENTS
        coords[at] = ax + dx * start
        coords[at + 1] = ay + dy * start
        coords[at + 2] = ax + dx * end
        coords[at + 3] = ay + dy * end
        count++
    }
}
