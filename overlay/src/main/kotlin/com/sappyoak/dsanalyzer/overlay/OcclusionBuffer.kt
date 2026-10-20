package com.sappyoak.dsanalyzer.overlay

import kotlin.math.abs
import kotlin.math.max
import kotlin.math.min

/** Screen pixels per buffer cell */
internal const val CELL_SIZE = 8

/** How far across its own footprint a marker is sampled, as a fraction either side of center */
private const val SAMPLE_SPREAD  = 1

/** Below this a triangle has no inside to interpolate across, in squared cells */
private const val MIN_AREA = 1e-6f

/**
 * A coarse depth buffer of the collision that was drawn, used to hide markers and volumes behind it
 */
internal class OcclusionBuffer {
    private var depths = FloatArray(0)
    private var width = 0
    private var height = 0

    /** False until a frame has been rasterised into it, so a stale buffer is never consulted */
    var isReady: Boolean = false
        private set

    /** Sizes the buffer for the canvas and clears it to "nothing in front" */
    fun prepare(screenWidth: Int, screenHeight: Int) {
        val cellsAcross = max(1, screenWidth / CELL_SIZE)
        val cellsDown = max(1, screenHeight / CELL_SIZE)

        if (cellsAcross != width || cellsDown != height) {
            width = cellsAcross
            height = cellsDown
            depths = FloatArray(cellsAcross * cellsDown)
        }
        depths.fill(Float.MAX_VALUE)
        isReady = false
    }

    /** Nothing was rasterised this frame, so markers must not be tested against it */
    fun abandon() {
        isReady = false
    }

    fun finish() {
        isReady = true
    }

    /**
     * Writes the triangle's depth into every cell it covers, keeping the nearest.
     *
     * The depth is interpolated per cell from the corners clip 'w' ([aw], [bw], [cw]) rather than
     * taken once for the whole triangle. A long floor triangle with one corner at the player's feet
     * would otherwise claim to be that near everywhere it covers, hiding whatever stands on its far end.
     * Screen-space interpolation is linear in '1 / w' no in 'w' which is the perspective-correct
     * quantity. The edge values are already the barycentric weights, unscaled
     */
    fun cover(
        ax: Float, ay: Float, aw: Float,
        bx: Float, by: Float, bw: Float,
        cx: Float, cy: Float, cw: Float
    ) {
        if (width == 0 || height == 0) return

        val x0 = ax / CELL_SIZE
        val y0 = ay / CELL_SIZE
        val x1 = bx / CELL_SIZE
        val y1 = by / CELL_SIZE
        val x2 = cx / CELL_SIZE
        val y2 = cy / CELL_SIZE

        val area = edge(x0, y0, x1, y1, x2, y2)
        if (abs(area) < MIN_AREA) return
        val inverseA = 1f / aw
        val inverseB = 1f / bw
        val inverseC = 1f / cw

        val left = max(0, min(x0, min(x1, x2)).toInt())
        val right = min(width - 1, max(x0, max(x1, x2)).toInt())
        val top = max(0, min(y0, min(y1, y2)).toInt())
        val bottom = min(height - 1, max(y0, max(y1, y2)).toInt())
        if (left > right || top > bottom) return

        for (row in top..bottom) {
            val sampleY = row + 0.5f
            val rowAt = row * width
            for (column in left..right) {
                val sampleX = column + 0.5f
                val e0 = edge(x1, y1, x2, y2, sampleX, sampleY)
                val e1 = edge(x2, y2, x0, y0, sampleX, sampleY)
                val e2 = edge(x0, y0, x1, y1, sampleX, sampleY)

                val inside = (e0 >= 0f && e1 >= 0f && e2 >= 0f) || (e0 <= 0f && e1 <= 0f && e2 <= 0f)
                if (!inside) continue

                val inverse = (e0 * inverseA + e1 * inverseB + e2 * inverseC) / area
                if (inverse <= 0f) continue

                val cell = rowAt + column
                val depth = 1f / inverse
                if (depth < depths[cell]) depths[cell] = depth
            }
        }
    }

    /** The nearest solid depth at screen [x], [y], or [Float.MAX_VALUE] when nothing is there */
    fun depthAt(x: Float, y: Float): Float {
        if (!isReady || width == 0 || height == 0) return Float.MAX_VALUE
        val column = (x / CELL_SIZE).toInt()
        val row = (y / CELL_SIZE).toInt()
        if (x < 0f || y < 0f || column >= width || row >= height) return Float.MAX_VALUE
        return depths[row * width + column]
    }

    /**
     * Whether a mark at [x], [y] spanning [radius] pixels is behind collision at every sample.
     */
    fun isHidden(x: Float, y: Float, radius: Float, depth: Float): Boolean {
        if (!isReady || width == 0 || height == 0) return false

        for (down in -SAMPLE_SPREAD..SAMPLE_SPREAD) {
            for (across in -SAMPLE_SPREAD..SAMPLE_SPREAD) {
                val column = ((x + across * radius) / CELL_SIZE).toInt()
                val row = ((y + down * radius) / CELL_SIZE).toInt()
                if (column < 0 || column >= width || row < 0 || row >= height) return false

                // One unobstructed sample is enough to keep the whole marker
                if (depths[row * width + column] >= depth) return false
            }
        }
        return true
    }
}

/** Which side of the line `(px, py) -> (qx, qy)` the sample falls on, scaled by the line's length */
private fun edge(px: Float, py: Float, qx: Float, qy: Float, sampleX: Float, sampleY: Float): Float =
    (qx - px) * (sampleY - py) - (qy - py) * (sampleX - px)
