package com.sappyoak.dsanalyzer.overlay

import io.kotest.assertions.assertSoftly
import io.kotest.assertions.withClue
import io.kotest.core.spec.style.FunSpec
import io.kotest.matchers.floats.plusOrMinus
import io.kotest.matchers.shouldBe

import com.sappyoak.dsanalyzer.runtime.session.collision.CollisionMesh
import com.sappyoak.dsanalyzer.shared.math.Frustum
import com.sappyoak.dsanalyzer.shared.math.Mat4

private const val WIDTH = 1920
private const val HEIGHT = 1080

/** As in [MarkerProjectionTest]: at the origin looking down +Z, forward in the game's view space */
private val WORLD_TO_CLIP =
    Mat4.Identity.orthonormalInverse * Frustum(0.75f, 16f / 9f, 0.1f, 1000f).projection

/** A triangle from three `x, y, z` corners */
private fun triangle(vararg corners: Float) = CollisionMesh(
    vertices = corners,
    indices = intArrayOf(0, 1, 2)
)

private fun project(mesh: CollisionMesh, capacity: Int = 16): TriangleProjector {
    val projector = TriangleProjector(capacity)
    projector.append(mesh, WORLD_TO_CLIP, WIDTH, HEIGHT)
    return projector
}

class TriangleProjectionTest : FunSpec({
    test("a triangle in front of the camera projects to one triangle near the view centre") {
        val projector = project(triangle(
            -1f, -1f, 10f,
            1f, -1f, 10f,
            0f, 1f, 10f
        ))

        assertSoftly {
            projector.count shouldBe 1
            // Centred on the view axis, so the corners straddle the middle of the window
            val centroidX = (projector.xs[0] + projector.xs[1] + projector.xs[2]) / 3f
            centroidX shouldBe ((WIDTH / 2).toFloat() plusOrMinus 1f)
        }
    }

    test("a triangle entirely behind the camera produces nothing") {
        val projector = project(triangle(
            -1f, -1f, -10f,
            1f, -1f, -10f,
            0f, 1f, -10f
        ))

        // Not a mirrored triangle somewhere plausible, which is what dividing first would give
        projector.count shouldBe 0
    }

    test("a triangle straddling the camera plane becomes two triangles, not nothing and not garbage") {
        val projector = project(triangle(
            -2f, -1f, 10f,
            2f, -1f, 10f,
            0f, 1f, -10f
        ))

        assertSoftly {
            projector.count shouldBe 2

            for (i in 0 until projector.count * 3) {
                withClue("corner $i = (${projector.xs[i]}, ${projector.ys[i]})") {
                    projector.xs[i].isFinite() shouldBe true
                    projector.ys[i].isFinite() shouldBe true
                    (projector.xs[i] > -BOUND && projector.xs[i] < BOUND) shouldBe true
                    (projector.ys[i] > -BOUND && projector.ys[i] < BOUND) shouldBe true
                }
            }
        }
    }

    test("a triangle with two corners behind the camera becomes one triangle") {
        val projector = project(triangle(
            0f, 0f, 10f,
            -2f, -1f, -10f,
            2f, -1f, -10f
        ))

        // Three clipped corners rather than four, so a single triangle and no fan
        projector.count shouldBe 1
    }

    test("a triangle entirely off one edge is rejected") {
        // Far to the left at a depth where the frustum is only a few units wide
        val projector = project(triangle(
            -900f, 0f, 10f,
            -880f, 0f, 10f,
            -890f, 10f, 10f
        ))

        projector.count shouldBe 0
    }

    test("the budget stops the walk and says so") {
        val two = CollisionMesh(
            vertices = floatArrayOf(
                -1f, -1f, 10f,
                1f, -1f, 10f,
                0f, 1f, 10f,
                0f, 2f, 10f
            ),
            indices = intArrayOf(0, 1, 2, 1, 2, 3)
        )
        val projector = TriangleProjector(capacity = 1)
        val finished = projector.append(two, WORLD_TO_CLIP, WIDTH, HEIGHT)

        assertSoftly {
            projector.count shouldBe 1
            // False is what tells the layer to stop walking bodies, which is what makes the
            // nearest-first ordering mean anything
            finished shouldBe false
            projector.isFull shouldBe true
        }
    }

    test("reset makes the projector reusable between frames") {
        val mesh = triangle(-1f, -1f, 10f, 1f, -1f, 10f, 0f, 1f, 10f)
        val projector = project(mesh)
        projector.count shouldBe 1

        projector.reset()
        projector.append(mesh, WORLD_TO_CLIP, WIDTH, HEIGHT)

        // Not two: the frame starts over rather than accumulating, which is the whole point of
        // reusing the arrays
        projector.count shouldBe 1
    }

    test("a triangle's depth is its nearest corner, in world units") {
        val projector = project(triangle(
            -1f, -1f, 10f,
            1f, -1f, 30f,
            0f, 1f, 20f
        ))

        assertSoftly {
            projector.count shouldBe 1
            projector.depths[0] shouldBe (10f plusOrMinus 0.01f)
        }
    }

    test("an index past the end of the vertices stops the walk instead of throwing") {
        val broken = CollisionMesh(
            vertices = floatArrayOf(0f, 0f, 10f),
            indices = intArrayOf(0, 1, 2)
        )
        val projector = TriangleProjector(16)

        assertSoftly {
            projector.append(broken, WORLD_TO_CLIP, WIDTH, HEIGHT) shouldBe true
            projector.count shouldBe 0
        }
    }
})

/** Generous: on-screen is 0..1920, and the artifact being guarded against lands far outside this */
private const val BOUND = 100_000f