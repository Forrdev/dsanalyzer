package com.sappyoak.dsanalyzer.overlay

import io.kotest.assertions.assertSoftly
import io.kotest.core.spec.style.FunSpec
import io.kotest.matchers.floats.plusOrMinus
import io.kotest.matchers.floats.shouldBeBetween
import io.kotest.matchers.shouldBe

import com.sappyoak.dsanalyzer.shared.math.Frustum
import com.sappyoak.dsanalyzer.shared.math.Mat4

private const val WIDTH = 1920
private const val HEIGHT = 1080

/** As in [TriangleProjectionTest]: at the origin looking down +Z */
private val WORLD_TO_CLIP =
    Mat4.Identity.orthonormalInverse * Frustum(0.75f, 16f / 9f, 0.1f, 1000f).projection

/** Solid collision filling the whole screen at one [depth] */
private fun wallAt(depth: Float) = OcclusionBuffer().apply {
    prepare(WIDTH, HEIGHT)
    cover(-10f, -10f, depth, 4000f, -10f, depth, -10f, 3000f, depth)
    finish()
}

/** One edge between two `x, y, z` ends */
private fun edge(vararg ends: Float) = ends

private fun trace(edge: FloatArray, occlusion: OcclusionBuffer): VolumeSpans {
    val spans = VolumeSpans(16)
    spans.trace(edge, WORLD_TO_CLIP, WIDTH, HEIGHT, occlusion)
    return spans
}

private fun onScreenX(x: Float, y: Float, z: Float) =
    screenX(WORLD_TO_CLIP.clipX(x, y, z), WORLD_TO_CLIP.clipW(x, y, z), WIDTH)

class VolumeSpansTest : FunSpec({
    test("an edge behind solid collision is dropped entirely") {
        trace(edge(-1f, 0f, 12f, 1f, 0f, 12f), wallAt(8f)).count shouldBe 0
    }

    test("an edge in front of it is kept whole") {
        val spans = trace(edge(-1f, 0f, 4f, 1f, 0f, 4f), wallAt(8f))

        assertSoftly {
            spans.count shouldBe 1
            spans.coords[0] shouldBe onScreenX(-1f, 0f, 4f).plusOrMinus(0.01f)
            spans.coords[2] shouldBe onScreenX(1f, 0f, 4f).plusOrMinus(0.01f)
        }
    }

    test("an edge passing through it is cut where it goes behind") {
        // Crosses the wall halfway along, at x = 0, which is the middle of the screen
        val spans = trace(edge(-1f, 0f, 4f, 1f, 0f, 12f), wallAt(8f))

        assertSoftly {
            spans.count shouldBe 1
            spans.coords[0] shouldBe onScreenX(-1f, 0f, 4f).plusOrMinus(0.01f)
            // Within one sample step of the true crossing
            spans.coords[2].shouldBeBetween(WIDTH / 2f - CELL_SIZE, WIDTH / 2f + CELL_SIZE, 0f)
        }
    }

    test("an edge flush with solid collision stays visible") {
        // A death camera laid over a floor: the same depth, give or take float error
        trace(edge(-1f, 0f, 8f, 1f, 0f, 8f), wallAt(8f)).count shouldBe 1
    }

    test("with nothing rasterised every on-screen edge is kept") {
        val empty = OcclusionBuffer()

        assertSoftly {
            trace(edge(-1f, 0f, 12f, 1f, 0f, 12f), empty).count shouldBe 1
            // Entirely off the side of the screen
            trace(edge(100f, 0f, 5f, 120f, 0f, 5f), empty).count shouldBe 0
            // Entirely behind the camera
            trace(edge(-1f, 0f, -5f, 1f, 0f, -5f), empty).count shouldBe 0
        }
    }

    test("an edge running behind the camera is clipped there and kept on screen") {
        val spans = trace(edge(1f, -1f, -5f, 1f, -1f, 10f), OcclusionBuffer())

        assertSoftly {
            spans.count shouldBe 1
            for (i in 0 until SPAN_COMPONENTS step 2) {
                spans.coords[i].shouldBeBetween(0f, WIDTH.toFloat(), 0.01f)
                spans.coords[i + 1].shouldBeBetween(0f, HEIGHT.toFloat(), 0.01f)
            }
            // The far end is where it was, unclipped
            spans.coords[2] shouldBe onScreenX(1f, -1f, 10f).plusOrMinus(0.01f)
        }
    }
})
