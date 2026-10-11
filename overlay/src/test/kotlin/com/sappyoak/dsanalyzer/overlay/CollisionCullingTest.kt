package com.sappyoak.dsanalyzer.overlay

import io.kotest.assertions.assertSoftly
import io.kotest.core.spec.style.FunSpec
import io.kotest.matchers.collections.shouldBeEmpty
import io.kotest.matchers.shouldBe

import com.sappyoak.dsanalyzer.runtime.ptde.ShapeKind
import com.sappyoak.dsanalyzer.runtime.session.CollisionBody
import com.sappyoak.dsanalyzer.runtime.session.CollisionMesh
import com.sappyoak.dsanalyzer.shared.math.Aabb
import com.sappyoak.dsanalyzer.shared.math.Vec3

private val A_MESH = CollisionMesh(
    vertices = floatArrayOf(0f, 0f, 0f, 1f, 0f, 0f, 0f, 1f, 0f),
    indices = intArrayOf(0, 1, 2)
)

private fun body(
    name: String,
    center: Vec3,
    halfExtents: Vec3 = Vec3(1f, 1f, 1f),
    mesh: CollisionMesh? = A_MESH,
    kind: ShapeKind? = ShapeKind.Mopp
) = CollisionBody(name, kind, Aabb(center, halfExtents), mesh)

class CollisionCullingTest : FunSpec({
    test("distance is measured to the box, so a large body underfoot is near") {
        // Eighty units across with its centre far away, which is an ordinary map floor piece
        val floor = body("1000B1", center = Vec3(80f, 0f, 0f), halfExtents = Vec3(80f, 1f, 80f))
        val eye = Vec3.Zero

        assertSoftly {
            // Its centre is 80 units off, well outside the radius; its surface is at the origin
            floor.bounds.distanceSquaredTo(eye) shouldBe 0f
            listOf(floor).nearest(eye, radius = 10f) shouldBe listOf(floor)
        }
    }

    test("results come back nearest first, because the budget cuts from the end") {
        val near = body("near", center = Vec3(5f, 0f, 0f))
        val middle = body("middle", center = Vec3(15f, 0f, 0f))
        val far = body("far", center = Vec3(25f, 0f, 0f))

        val ordered = listOf(far, near, middle).nearest(Vec3.Zero, radius = 100f)

        ordered.map { it.name } shouldBe listOf("near", "middle", "far")
    }

    test("bodies beyond the radius are dropped") {
        val inside = body("inside", center = Vec3(5f, 0f, 0f))
        val outside = body("outside", center = Vec3(500f, 0f, 0f))

        listOf(inside, outside).nearest(Vec3.Zero, radius = 30f).map { it.name } shouldBe listOf("inside")
    }

    test("bodies with no geometry never reach the projector") {
        val cylinder = body("5550_h", center = Vec3.Zero, mesh = null, kind = ShapeKind.Cylinder)
        val mopp = body("1000B1", center = Vec3.Zero)

        listOf(cylinder, mopp).nearest(Vec3.Zero, radius = 30f).map { it.name } shouldBe listOf("1000B1")
    }

    test("an empty bounding box is dropped rather than treated as a point at the origin") {
        val unreadable = body("?", center = Vec3.Zero, halfExtents = Vec3.Zero)

        listOf(unreadable).nearest(Vec3.Zero, radius = 30f).shouldBeEmpty()
    }

    test("a non-positive radius draws nothing") {
        assertSoftly {
            listOf(body("a", Vec3.Zero)).nearest(Vec3.Zero, radius = 0f).shouldBeEmpty()
            listOf(body("a", Vec3.Zero)).nearest(Vec3.Zero, radius = -1f).shouldBeEmpty()
        }
    }
})