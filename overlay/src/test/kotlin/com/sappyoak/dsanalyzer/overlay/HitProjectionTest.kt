package com.sappyoak.dsanalyzer.overlay

import io.kotest.assertions.assertSoftly
import io.kotest.core.spec.style.FunSpec
import io.kotest.matchers.floats.plusOrMinus
import io.kotest.matchers.shouldBe

import com.sappyoak.dsanalyzer.runtime.session.attack.HitShape
import com.sappyoak.dsanalyzer.shared.math.Frustum
import com.sappyoak.dsanalyzer.shared.math.Mat4
import com.sappyoak.dsanalyzer.shared.math.Vec3

private const val WIDTH = 1920
private const val HEIGHT = 1080

/** As in [TriangleProjectionTest]: at the origin looking down +Z */
private val WORLD_TO_CLIP =
    Mat4.Identity.orthonormalInverse * Frustum(0.75f, 16f / 9f, 0.1f, 1000f).projection

private val projector = HitProjector(WORLD_TO_CLIP, Vec3.Zero, WIDTH, HEIGHT)

class HitProjectionTest : FunSpec({
    test("a sphere ahead lands mid-screen as one circle, half the size at twice the distance") {
        val near = checkNotNull(projector.ends(HitShape.Sphere(Vec3(0f, 0f, 10f), 1f)))
        val far = checkNotNull(projector.ends(HitShape.Sphere(Vec3(0f, 0f, 20f), 1f)))

        assertSoftly {
            near.a shouldBe near.b
            near.a.x shouldBe ((WIDTH / 2f) plusOrMinus 0.5f)
            near.a.y shouldBe ((HEIGHT / 2f) plusOrMinus 0.5f)
            far.a.radius shouldBe ((near.a.radius / 2f) plusOrMinus 0.5f)
        }
    }

    test("a capsule pointing straight away keeps its full radius at each end, so it never thins to a line") {
        val ends = checkNotNull(projector.ends(HitShape.Capsule(Vec3(0f, 0f, 10f), Vec3(0f, 0f, 20f), 1f)))
        val sphere = checkNotNull(projector.ends(HitShape.Sphere(Vec3(0f, 0f, 10f), 1f)))

        assertSoftly {
            ends.a.radius shouldBe (sphere.a.radius plusOrMinus 0.01f)
            ends.b.radius shouldBe ((ends.a.radius / 2f) plusOrMinus 0.5f)
        }
    }

    test("a shape with an end behind the eye is dropped whole") {
        assertSoftly {
            projector.ends(HitShape.Capsule(Vec3(0f, 0f, 10f), Vec3(0f, 0f, -1f), 1f)) shouldBe null
            projector.ends(HitShape.Sphere(Vec3(0f, 0f, -5f), 1f)) shouldBe null
        }
    }
})
