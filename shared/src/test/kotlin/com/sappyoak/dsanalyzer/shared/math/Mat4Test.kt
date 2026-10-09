package com.sappyoak.dsanalyzer.shared.math

import io.kotest.assertions.assertSoftly
import io.kotest.core.spec.style.FunSpec
import io.kotest.matchers.floats.plusOrMinus
import io.kotest.matchers.nulls.shouldBeNull
import io.kotest.matchers.nulls.shouldNotBeNull
import io.kotest.matchers.shouldBe
import kotlin.math.PI

private const val TOLERANCE = 1e-5f

private const val WIDTH = 1920
private const val HEIGHT = 1080

/**
 * A camera at (10, 5, 20) yawed a quarter turn, so it looks down +X.
 *
 * Written out as basis rows rather than built from angles on purpose: the point of these tests is
 * the arithmetic on a matrix shaped like the game's, and deriving the matrix from eulers here would
 * test the derivation instead. `right x up = forward` holds, which is the left-handed convention
 * [Mat4.perspective] assumes.
 */
private val CAMERA = Mat4(
    r0 = Vec4(0f, 0f, -1f, 0f),
    r1 = Vec4(0f, 1f, 0f, 0f),
    r2 = Vec4(1f, 0f, 0f, 0f),
    r3 = Vec4(10f, 5f, 20f, 1f)
)

private val EYE = Vec3(10f, 5f, 20f)

/** 90 degrees vertical at 1:1, which makes the half-extent at depth d exactly d */
private val SQUARE_90 = Frustum(fovRadians = (PI / 2).toFloat(), aspect = 1f, near = 0.1f, far = 100f)

private fun Vec4.shouldBeClose(expected: Vec4) = assertSoftly {
    x shouldBe (expected.x plusOrMinus TOLERANCE)
    y shouldBe (expected.y plusOrMinus TOLERANCE)
    z shouldBe (expected.z plusOrMinus TOLERANCE)
    w shouldBe (expected.w plusOrMinus TOLERANCE)
}

private fun Mat4.shouldBeClose(expected: Mat4) {
    r0.shouldBeClose(expected.r0)
    r1.shouldBeClose(expected.r1)
    r2.shouldBeClose(expected.r2)
    r3.shouldBeClose(expected.r3)
}

class Mat4Test : FunSpec({
    test("a camera transform composed with its inverse is the identity") {
        (CAMERA * CAMERA.orthonormalInverse).shouldBeClose(Mat4.Identity)
    }

    test("the inverse puts the camera at the origin") {
        val view = CAMERA.orthonormalInverse
        view.transform(EYE.asPoint()).shouldBeClose(Vec4(0f, 0f, 0f, 1f))
    }

    test("the inverse measures depth along forward and height along up") {
        val view = CAMERA.orthonormalInverse
        // Ten along the camera's forward (+X in the world) and three along its up
        val ahead = Vec3(EYE.x + 10f, EYE.y + 3f, EYE.z)

        view.transform(ahead.asPoint()).shouldBeClose(Vec4(0f, 3f, 10f, 1f))
    }

    test("a translation does not move a direction") {
        val view = CAMERA.orthonormalInverse
        // The camera's own forward, as a direction, is +Z in view space whatever the position is
        view.transform(Vec3(1f, 0f, 0f).asDirection()).shouldBeClose(Vec4(0f, 0f, 1f, 0f))
    }

    test("orthonormality is recognised, and a scaled basis is not") {
        assertSoftly {
            CAMERA.isOrthonormal() shouldBe true
            Mat4.Identity.isOrthonormal() shouldBe true
            CAMERA.copy(r1 = Vec4(0f, 2f, 0f, 0f)).isOrthonormal() shouldBe false
            CAMERA.copy(r0 = Vec4(0f, 1f, -1f, 0f)).isOrthonormal() shouldBe false
        }
    }

    context("projection") {
        val worldToClip = CAMERA.orthonormalInverse * SQUARE_90.projection

        test("a point straight ahead lands in the middle of the window") {
            val screen = worldToClip.project(Vec3(EYE.x + 10f, EYE.y, EYE.z), WIDTH, HEIGHT)
                .shouldNotBeNull()

            assertSoftly {
                screen.x shouldBe (WIDTH / 2f plusOrMinus 0.01f)
                screen.y shouldBe (HEIGHT / 2f plusOrMinus 0.01f)
            }
        }

        test("a point on the top edge of the frustum lands on the top of the window") {
            // At 90 degrees vertical the half-height at depth 10 is 10, so this is exactly the edge
            val screen = worldToClip.project(Vec3(EYE.x + 10f, EYE.y + 10f, EYE.z), WIDTH, HEIGHT)
                .shouldNotBeNull()

            assertSoftly {
                screen.y shouldBe (0f plusOrMinus 0.01f)
                screen.x shouldBe (WIDTH / 2f plusOrMinus 0.01f)
            }
        }

        test("depth runs from zero at the near plane to one at the far plane") {
            val near = worldToClip.project(Vec3(EYE.x + SQUARE_90.near, EYE.y, EYE.z), WIDTH, HEIGHT)
                .shouldNotBeNull()
            val far = worldToClip.project(Vec3(EYE.x + SQUARE_90.far, EYE.y, EYE.z), WIDTH, HEIGHT)
                .shouldNotBeNull()

            assertSoftly {
                near.depth shouldBe (0f plusOrMinus 1e-3f)
                far.depth shouldBe (1f plusOrMinus 1e-3f)
            }
        }

        test("a point behind the camera is rejected rather than mirrored onto the window") {
            worldToClip.project(Vec3(EYE.x - 10f, EYE.y, EYE.z), WIDTH, HEIGHT).shouldBeNull()
        }

        test("the camera's own position is rejected, being on the wrong side of the near plane") {
            worldToClip.project(EYE, WIDTH, HEIGHT).shouldBeNull()
        }

        test("a point beyond the side of the frustum is rejected") {
            // Twenty along the camera's right at depth ten, where the half-width is ten
            worldToClip.project(Vec3(EYE.x + 10f, EYE.y, EYE.z - 20f), WIDTH, HEIGHT).shouldBeNull()
        }
    }

    test("a wider field of view fits more in, so the same point sits nearer the middle") {
        val narrow = CAMERA.orthonormalInverse * SQUARE_90.projection
        val wide = CAMERA.orthonormalInverse * SQUARE_90.copy(fovRadians = (PI / 1.5).toFloat()).projection
        val offCentre = Vec3(EYE.x + 10f, EYE.y + 5f, EYE.z)

        val fromNarrow = narrow.project(offCentre, WIDTH, HEIGHT).shouldNotBeNull()
        val fromWide = wide.project(offCentre, WIDTH, HEIGHT).shouldNotBeNull()

        assertSoftly {
            // Both above the middle, the wide one less so
            (fromNarrow.y < HEIGHT / 2f) shouldBe true
            (fromWide.y > fromNarrow.y) shouldBe true
        }
    }
})