package com.sappyoak.dsanalyzer.overlay

import com.sappyoak.dsanalyzer.runtime.session.collision.CollisionMesh
import com.sappyoak.dsanalyzer.shared.math.Mat4


/** A triangle clipped by one plane has at most four corners */
private const val CLIPPED_MAX = 4

/**
 * Projects collision triangles to screen space, clipping them against the near plane first
 *
 * ## Why clipping is not optional here
 * [com.sappyoak.dsanalyzer.shared.math.project] culls a **point** behind the camera by returning
 * null, and for markers that is the whole answer, a marker behind you is simply not draw. A triangle
 * cannot be handled that way. A triangle with one corner behind the eye and two in front is
 * genuinely on screen, so dropping it leaves a hole, but projecting that corner divides by a
 * negative 'w' and mirrors it to the far side, so the triangle renders as a wedge smeared across
 * the view
 *
 * Each emitted triangle keeps a [depths] entry and [DepthBuckets] turn those into a far-to-near draw
 * order. The value stored is the **nearest** corner's clip 'w' which for this project is
 * view depth in world units, the distance along the camera axis
 *
 * **The frustum sides are not clipped** The canvas clips its own drawing, so a line running off
 * the  edge costs a little rasterization and nothing else.
 */
internal class TriangleProjector(private val capacity: Int) {
    /** Screen x of every emitted corner, three per triangle, valid up to `count * CORNERS` */
    val xs: FloatArray = FloatArray(capacity * CollisionMesh.CORNERS)

    /** Screen y, paired with [xs] */
    val ys: FloatArray = FloatArray(capacity * CollisionMesh.CORNERS)

    /** Clip 'w' of every emitted corner, paired with [xs] */
    val ws: FloatArray = FloatArray(capacity * CollisionMesh.CORNERS)

    /** One per triangle, not per corner: view depth, in world units, of its nearest corner */
    val depths: FloatArray = FloatArray(capacity)

    var count: Int = 0
        private set

    val isFull: Boolean get() = count >= capacity

    /** Clip-space scratch for the triangle being worked on, and for its clipped result */
    private val inX = FloatArray(CollisionMesh.CORNERS)
    private val inY = FloatArray(CollisionMesh.CORNERS)
    private val inZ = FloatArray(CollisionMesh.CORNERS)
    private val inW = FloatArray(CollisionMesh.CORNERS)
    private val outX = FloatArray(CLIPPED_MAX)
    private val outY = FloatArray(CLIPPED_MAX)
    private val outW = FloatArray(CLIPPED_MAX)

    /**
     * The cull in force for the mesh being appended.
     *
     * A field rather than a parameter threaded through [emit] and [addIfVisible], which would make
     * both signatures longer for a value that is constant across the whole frame
     */
    private var cull: FaceCull = FaceCull.Off

    fun reset() {
        count = 0
    }

    /** Drops everything appended after the first [triangles], so a body cut short by the budget goes whole */
    fun rewind(triangles: Int) {
        count = triangles.coerceIn(0, count)
    }

    /**
     * Projects [mesh]'s triangles, appending the ones that land on screen and survive [cull].
     *
     * Stops early and returns false once [capacity] is reached, so a caller walking several bodies
     * knows to stop rather than silently dropping the rest. Culling happens here rather than at
     * draw time so that the budget buys twice as much *visible* geometry.
     */
    fun append(
        mesh: CollisionMesh,
        worldToClip: Mat4,
        width: Int,
        height: Int,
        cull: FaceCull = FaceCull.Off
    ): Boolean {
        this.cull = cull

        val indices = mesh.indices
        val vertices = mesh.vertices

        var triangle = 0
        while (triangle < indices.size) {
            if (isFull) return false

            for (corner in 0 until CollisionMesh.CORNERS) {
                val at = indices[triangle + corner] * CollisionMesh.COMPONENTS
                // Guards a bad index rather than throwing out of a draw call. The reader rebases
                // indices across subparts, and that is exactly the arithmetic worth not trusting
                if (at < 0 || at + 2 >= vertices.size) return true
                val x = vertices[at]
                val y = vertices[at + 1]
                val z = vertices[at + 2]
                inX[corner] = worldToClip.clipX(x, y, z)
                inY[corner] = worldToClip.clipY(x, y, z)
                inZ[corner] = worldToClip.clipZ(x, y, z)
                inW[corner] = worldToClip.clipW(x, y, z)
            }

            emit(clipToNearPlane(), width, height)
            triangle += CollisionMesh.CORNERS
        }
        return true
    }

    /**
     * Clips the scratch triangle against `z >= 0`, returning how many corners survived.
     *
     * Sutherland–Hodgman over a single plane: walk the edges, keep the inside corners, and where an
     * edge crosses, add the crossing point. Solving for `t` on `z` alone is what makes a
     * single-plane clip this short, and `w` has to be interpolated along with `x` and `y`, because
     * the projection divides by it and every triangle's depth is read off it.
     */
    private fun clipToNearPlane(): Int {
        var produced = 0
        for (current in 0 until CollisionMesh.CORNERS) {
            val next = if (current == CollisionMesh.CORNERS - 1) 0 else current + 1
            val currentIn = inZ[current] >= NEAR_PLANE
            val nextIn = inZ[next] >= NEAR_PLANE

            if (currentIn) {
                outX[produced] = inX[current]
                outY[produced] = inY[current]
                outW[produced] = inW[current]
                produced++
            }
            if (currentIn != nextIn) {
                val span = inZ[next] - inZ[current]
                val t = if (span == 0f) 0f else (NEAR_PLANE - inZ[current]) / span
                outX[produced] = inX[current] + (inX[next] - inX[current]) * t
                outY[produced] = inY[current] + (inY[next] - inY[current]) * t
                outW[produced] = inW[current] + (inW[next] - inW[current]) * t
                produced++
            }
        }
        return produced
    }

    /** Fans [produced] clipped corners into triangles and appends the ones worth drawing */
    private fun emit(produced: Int, width: Int, height: Int) {
        if (produced < CollisionMesh.CORNERS) return

        for (apex in 1 until produced - 1) {
            if (isFull) return
            addIfVisible(0, apex, apex + 1, width, height)
        }
    }

    private fun addIfVisible(a: Int, b: Int, c: Int, width: Int, height: Int) {
        if (outW[a] < MIN_W || outW[b] < MIN_W || outW[c] < MIN_W) return

        val ax = screenX(outX[a], outW[a], width)
        val bx = screenX(outX[b], outW[b], width)
        val cx = screenX(outX[c], outW[c], width)
        val ay = screenY(outY[a], outW[a], height)
        val by = screenY(outY[b], outW[b], height)
        val cy = screenY(outY[c], outW[c], height)

        // Entirely off one edge, which is a comparison rather than a clip. Triangles straddling an
        // edge are kept and the canvas clips them
        if (ax < 0f && bx < 0f && cx < 0f) return
        if (ay < 0f && by < 0f && cy < 0f) return
        if (ax > width && bx > width && cx > width) return
        if (ay > height && by > height && cy > height) return

        if (cull.rejects(twiceSignedArea(ax, ay, bx, by, cx, cy))) return

        val at = count * CollisionMesh.CORNERS
        xs[at] = ax; xs[at + 1] = bx; xs[at + 2] = cx
        ys[at] = ay; ys[at + 1] = by; ys[at + 2] = cy
        ws[at] = outW[a]; ws[at + 1] = outW[b]; ws[at + 2] = outW[c]
        depths[count] = minOf(outW[a], outW[b], outW[c])
        count++
    }
}

/**
 * Twice the signed area of a screen-space triangle, whose **sign is its winding**.
 *
 * Screen y grows downward, so a positive result is clockwise as seen on the display. That flip is
 * the reason this is written out with a name instead of inlined: the same expression in a y-up
 * system means the opposite thing, and nothing about reading it would say so.
 */
internal fun twiceSignedArea(
    ax: Float,
    ay: Float,
    bx: Float,
    by: Float,
    cx: Float,
    cy: Float
): Float = (bx - ax) * (cy - ay) - (cx - ax) * (by - ay)
