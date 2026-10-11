package com.sappyoak.dsanalyzer.overlay

import com.sappyoak.dsanalyzer.runtime.session.CollisionMesh
import com.sappyoak.dsanalyzer.shared.math.Mat4

/** The near plane in clip space */
private const val NEAR_PLANE = 0f

private const val MIN_W = 1e-4f

/** A triangle clipped by one plane has at most four corners */
private const val CLIPPED_MAX = 4


internal class TriangleProjector(private val capacity: Int) {
    val xs: FloatArray = FloatArray(capacity * CollisionMesh.CORNERS)
    val ys: FloatArray = FloatArray(capacity * CollisionMesh.CORNERS)

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

    fun reset() {
        count = 0
    }

    /** Projects [meth]'s triangles, appending the ones that land on screen */
    fun append(mesh: CollisionMesh, worldToClip: Mat4, width: Int, height: Int): Boolean {
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

    private fun emit(produced: Int, width: Int, height: Int) {
        if (produced < CollisionMesh.CORNERS) return

        for (apex in 1 until produced - 1) {
            if (isFull) return
            addIfVisible(0, apex, apex + 1, width, height)
        }
    }

    /** Clips the scratch triangle against 'z >=0' returning how many corners survived */
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

        val at = count * CollisionMesh.CORNERS
        xs[at] = ax; xs[at + 1] = bx; xs[at + 2] = cx
        ys[at] = ay; ys[at + 1] = by; ys[at + 2] = cy
        count++
    }

    private fun screenX(x: Float, w: Float, width: Int): Float = (x / w + 1f) * 0.5f * width

    private fun screenY(y: Float, w: Float, height: Int): Float = (1f - y / w) * 0.5f * height


}
private fun Mat4.clipX(x: Float, y: Float, z: Float): Float =
    x * r0.x + y * r1.x + z * r2.x + r3.x

private fun Mat4.clipY(x: Float, y: Float, z: Float): Float =
    x * r0.y + y * r1.y + z * r2.y + r3.y

private fun Mat4.clipZ(x: Float, y: Float, z: Float): Float =
    x * r0.z + y * r1.z + z * r2.z + r3.z

private fun Mat4.clipW(x: Float, y: Float, z: Float): Float =
    x * r0.w + y * r1.w + z * r2.w + r3.w