package com.sappyoak.dsanalyzer.overlay

import io.kotest.assertions.assertSoftly
import io.kotest.core.spec.style.FunSpec
import io.kotest.matchers.floats.plusOrMinus
import io.kotest.matchers.shouldBe

import com.sappyoak.dsanalyzer.runtime.session.collision.CollisionMesh
import com.sappyoak.dsanalyzer.shared.math.Frustum
import com.sappyoak.dsanalyzer.shared.math.Mat4

private const val WIDTH = 1920
private const val HEIGHT = 1080

/** As in [TriangleProjectionTest]: at the origin looking down +Z */
private val WORLD_TO_CLIP =
    Mat4.Identity.orthonormalInverse * Frustum(0.75f, 16f / 9f, 0.1f, 1000f).projection

/** Three units below the eye, running from just ahead of it to sixty units out */
private val FLOOR = CollisionMesh(
    vertices = floatArrayOf(
        -10f, -3f, 1f,
        10f, -3f, 1f,
        10f, -3f, 60f,
        -10f, -3f, 60f
    ),
    indices = intArrayOf(0, 1, 2, 0, 2, 3)
)

private fun OcclusionBuffer.coverAll(mesh: CollisionMesh) {
    val projector = TriangleProjector(16)
    projector.append(mesh, WORLD_TO_CLIP, WIDTH, HEIGHT)
    prepare(WIDTH, HEIGHT)
    for (triangle in 0 until projector.count) {
        val at = triangle * CollisionMesh.CORNERS
        cover(
            projector.xs[at], projector.ys[at], projector.ws[at],
            projector.xs[at + 1], projector.ys[at + 1], projector.ws[at + 1],
            projector.xs[at + 2], projector.ys[at + 2], projector.ws[at + 2]
        )
    }
    finish()
}

/** Whether a small marker at world `x, y, z` would be hidden, at its own view depth */
private fun OcclusionBuffer.hides(x: Float, y: Float, z: Float): Boolean {
    val w = WORLD_TO_CLIP.clipW(x, y, z)
    val sx = screenX(WORLD_TO_CLIP.clipX(x, y, z), w, WIDTH)
    val sy = screenY(WORLD_TO_CLIP.clipY(x, y, z), w, HEIGHT)
    return isHidden(sx, sy, radius = 2f, depth = w)
}


class OcclusionBufferTest : FunSpec({
    test("depth is interpolated across a triangle in 1 / w, not taken from its nearest corner") {
        val buffer = OcclusionBuffer()
        buffer.prepare(WIDTH, HEIGHT)
        // In cells: (0, 0) and (200, 0) at w = 1, (0, 200) at w = 4
        buffer.cover(0f, 0f, 1f, 1600f, 0f, 1f, 0f, 1600f, 4f)
        buffer.finish()

        assertSoftly {
            // Cell (0, 0), centre (0.5, 0.5): weights 0.995, 0.0025 and 0.0025
            buffer.depthAt(4f, 4f) shouldBe (1f / (0.9975f + 0.0025f * 0.25f)).plusOrMinus(1e-3f)
            // Cell (0, 99), centre (0.5, 99.5): weights 0.5, 0.0025 and 0.4975
            buffer.depthAt(4f, 796f) shouldBe (1f / (0.5025f + 0.4975f * 0.25f)).plusOrMinus(1e-3f)
            // Off the triangle, and off the screen, nothing is in front
            buffer.depthAt(1900f, 1000f) shouldBe Float.MAX_VALUE
            buffer.depthAt(-1f, 4f) shouldBe Float.MAX_VALUE
        }
    }

    test("a marker standing on the far end of a floor is not hidden by the floor's near end") {
        // The regression: the floor's nearest corner is one unit away, and depth taken once per
        // triangle claimed that depth across its whole footprint
        val buffer = OcclusionBuffer()
        buffer.coverAll(FLOOR)

        assertSoftly {
            // A metre above the floor, thirty out: the floor behind it on screen is forty-five out
            buffer.hides(0f, -2f, 30f) shouldBe false
            // A metre beneath it: the floor in front on screen is twenty-two and a half out
            buffer.hides(0f, -4f, 30f) shouldBe true
        }
    }

    test("an unfinished or abandoned buffer hides nothing and reports nothing in front") {
        val buffer = OcclusionBuffer()
        buffer.coverAll(FLOOR)
        buffer.abandon()

        assertSoftly {
            buffer.hides(0f, -4f, 30f) shouldBe false
            buffer.depthAt(WIDTH / 2f, HEIGHT - 4f) shouldBe Float.MAX_VALUE
        }
    }
})
