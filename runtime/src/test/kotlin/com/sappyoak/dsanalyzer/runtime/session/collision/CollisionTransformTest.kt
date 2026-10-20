package com.sappyoak.dsanalyzer.runtime.session.collision

import io.kotest.assertions.assertSoftly
import io.kotest.assertions.withClue
import io.kotest.core.spec.style.FunSpec
import io.kotest.matchers.floats.plusOrMinus
import io.kotest.matchers.shouldBe

import com.sappyoak.dsanalyzer.shared.math.Aabb
import com.sappyoak.dsanalyzer.shared.math.Mat4
import com.sappyoak.dsanalyzer.shared.math.Vec3
import com.sappyoak.dsanalyzer.shared.math.Vec4

/** A body transform: identity rotation, translation in the last row, as Havok stores it */
private fun translation(x: Float, y: Float, z: Float) = Mat4(
    Vec4(1f, 0f, 0f, 0f),
    Vec4(0f, 1f, 0f, 0f),
    Vec4(0f, 0f, 1f, 0f),
    Vec4(x, y, z, 1f)
)

class CollisionTransformTest : FunSpec({
    test("a local vertex lands where the game actually had it") {
        // Oolacile, 2026-10-11. 174 of 196 fixed bodies shared this translation; the vertex and
        // the expectation were both read out of the running game
        val body = translation(700f, -300f, 900f)

        val placed = body.place(279.157f, -12.401f, -101.910f)

        assertSoftly {
            placed.x shouldBe (979.157f plusOrMinus 0.001f)
            placed.y shouldBe (-312.401f plusOrMinus 0.001f)
            placed.z shouldBe (798.090f plusOrMinus 0.001f)
        }
    }

    test("an identity transform leaves a point alone") {
        val placed = Mat4.Identity.place(1f, 2f, 3f)
        placed shouldBe Vec3(1f, 2f, 3f)
    }

    test("a rotation is applied, not just the translation") {
        // A quarter turn about Y, in the row-vector layout Havok writes
        val rotated = Mat4(
            Vec4(0f, 0f, 1f, 0f),
            Vec4(0f, 1f, 0f, 0f),
            Vec4(-1f, 0f, 0f, 0f),
            Vec4(0f, 0f, 0f, 1f)
        )

        val placed = rotated.place(1f, 0f, 0f)

        assertSoftly {
            placed.x shouldBe (0f plusOrMinus 1e-5f)
            placed.z shouldBe (1f plusOrMinus 1e-5f)
        }
    }

    test("a translated box keeps its size and moves its center") {
        val box = Aabb(center = Vec3(1f, 2f, 3f), halfExtents = Vec3(4f, 5f, 6f))

        val moved = box.transformedBy(translation(10f, 20f, 30f))

        assertSoftly {
            moved.center shouldBe Vec3(11f, 22f, 33f)
            moved.halfExtents shouldBe Vec3(4f, 5f, 6f)
        }
    }

    test("a rotated box is re-fitted rather than rotated in place") {
        val half = 0.70710678f
        val spun = Mat4(
            Vec4(half, 0f, half, 0f),
            Vec4(0f, 1f, 0f, 0f),
            Vec4(-half, 0f, half, 0f),
            Vec4(0f, 0f, 0f, 1f)
        )
        val box = Aabb(center = Vec3.Zero, halfExtents = Vec3(1f, 1f, 1f))

        val fitted = box.transformedBy(spun)

        assertSoftly {
            withClue("a unit square spun 45 degrees spans sqrt(2) on each axis it moved in") {
                fitted.halfExtents.x shouldBe (1.41421f plusOrMinus 1e-4f)
                fitted.halfExtents.z shouldBe (1.41421f plusOrMinus 1e-4f)
            }
            withClue("the axis the rotation left alone is unchanged") {
                fitted.halfExtents.y shouldBe (1f plusOrMinus 1e-5f)
            }
            fitted.center shouldBe Vec3.Zero
        }
    }

    test("the bounds and the geometry make the same journey") {
        val body = translation(700f, -300f, 900f)
        val local = Vec3(279.157f, -12.401f, -101.910f)
        val box = Aabb(center = local, halfExtents = Vec3(2.55f, 0.05f, 2.55f))

        val placedVertex = body.place(local.x, local.y, local.z)
        val placedBox = box.transformedBy(body)

        assertSoftly {
            placedBox.center.x shouldBe (placedVertex.x plusOrMinus 1e-3f)
            placedBox.center.y shouldBe (placedVertex.y plusOrMinus 1e-3f)
            placedBox.center.z shouldBe (placedVertex.z plusOrMinus 1e-3f)
        }
    }
})