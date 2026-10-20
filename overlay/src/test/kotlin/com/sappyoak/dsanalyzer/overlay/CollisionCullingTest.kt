package com.sappyoak.dsanalyzer.overlay

import io.kotest.assertions.assertSoftly
import io.kotest.core.spec.style.FunSpec
import io.kotest.matchers.collections.shouldBeEmpty
import io.kotest.matchers.shouldBe

import com.sappyoak.dsanalyzer.runtime.session.collision.CollisionBody
import com.sappyoak.dsanalyzer.runtime.session.collision.CollisionGeometry
import com.sappyoak.dsanalyzer.runtime.session.collision.CollisionMesh
import com.sappyoak.dsanalyzer.runtime.session.collision.ShapeKind
import com.sappyoak.dsanalyzer.shared.math.Aabb
import com.sappyoak.dsanalyzer.shared.math.Vec3

private val ALL_KINDS = ShapeKind.entries.toSet()

private val A_MESH = CollisionMesh(
    vertices = floatArrayOf(0f, 0f, 0f, 1f, 0f, 0f, 0f, 1f, 0f),
    indices = intArrayOf(0, 1, 2)
)

private const val MAP_FILTER = 0x1C
private const val OBJECT_FILTER = 0x11

private fun body(
    name: String,
    center: Vec3,
    halfExtents: Vec3 = Vec3(1f, 1f, 1f),
    geometry: CollisionGeometry = CollisionGeometry.Mesh(A_MESH),
    kind: ShapeKind? = ShapeKind.Mopp,
    filterInfo: Int = MAP_FILTER
) = CollisionBody(name, kind, filterInfo, Float.MAX_VALUE, Aabb(center, halfExtents), geometry)


class CollisionCullingTest : FunSpec({
    test("distance is measured to the box, so a large body underfoot is near") {
        // Eighty units across with its centre far away, which is an ordinary map floor piece
        val floor = body("1000B1", center = Vec3(80f, 0f, 0f), halfExtents = Vec3(80f, 1f, 80f))
        val eye = Vec3.Zero

        assertSoftly {
            // Its centre is 80 units off, well outside the radius; its surface is at the origin
            floor.bounds.distanceSquaredTo(eye) shouldBe 0f
            listOf(floor).nearest(eye, radius = 10f, kinds = ALL_KINDS) shouldBe listOf(floor)
        }
    }

    test("results come back nearest first, because the budget cuts from the end") {
        val near = body("near", center = Vec3(5f, 0f, 0f))
        val middle = body("middle", center = Vec3(15f, 0f, 0f))
        val far = body("far", center = Vec3(25f, 0f, 0f))

        val ordered = listOf(far, near, middle).nearest(Vec3.Zero, radius = 100f, kinds = ALL_KINDS)

        ordered.map { it.name } shouldBe listOf("near", "middle", "far")
    }

    test("bodies beyond the radius are dropped") {
        val inside = body("inside", center = Vec3(5f, 0f, 0f))
        val outside = body("outside", center = Vec3(500f, 0f, 0f))

        listOf(inside, outside).nearest(Vec3.Zero, radius = 30f, kinds = ALL_KINDS).map { it.name } shouldBe listOf("inside")
    }

    test("bodies with no geometry never reach the projector") {
        val unread = body(
            name = "?",
            center = Vec3.Zero,
            geometry = CollisionGeometry.Unread,
            kind = ShapeKind.Cylinder
        )
        val mopp = body("1000B1", center = Vec3.Zero)

        listOf(unread, mopp).nearest(Vec3.Zero, radius = 30f, kinds = ALL_KINDS).map { it.name } shouldBe listOf("1000B1")
    }

    test("a parametric shape is drawn") {
        // regression guarding against a culler that still asks for a mesh
        val cylinder = body(
            name = "5550_h",
            center = Vec3.Zero,
            geometry = CollisionGeometry.Cylinder(
                a = Vec3(0f, -1f, 0f),
                b = Vec3(0f, 1f, 0f),
                cylRadius = 0.5f,
                radius = 0.05f
            ),
            kind = ShapeKind.Cylinder
        )

        listOf(cylinder).nearest(Vec3.Zero, radius = 30f, kinds = ALL_KINDS).map { it.name } shouldBe listOf("5550_h")
    }
    test("an empty bounding box is dropped rather than treated as a point at the origin") {
        val unreadable = body("?", center = Vec3.Zero, halfExtents = Vec3.Zero)

        listOf(unreadable).nearest(Vec3.Zero, radius = 30f, kinds = ALL_KINDS).shouldBeEmpty()
    }

    test("a non-positive radius draws nothing") {
        assertSoftly {
            listOf(body("a", Vec3.Zero)).nearest(Vec3.Zero, radius = 0f, kinds = ALL_KINDS).shouldBeEmpty()
            listOf(body("a", Vec3.Zero)).nearest(Vec3.Zero, radius = -1f, kinds = ALL_KINDS).shouldBeEmpty()
        }
    }

    test("a kind that is switched off is dropped before its geometry is touched") {
        val mopp = body("1000B1", center = Vec3.Zero)
        val cylinder = body("5550_h", center = Vec3.Zero, kind = ShapeKind.Cylinder)

        assertSoftly {
            listOf(mopp, cylinder)
                .nearest(Vec3.Zero, radius = 30f, kinds = setOf(ShapeKind.Mopp))
                .map { it.name } shouldBe listOf("1000B1")

            listOf(mopp, cylinder)
                .nearest(Vec3.Zero, radius = 30f, kinds = setOf(ShapeKind.Cylinder))
                .map { it.name } shouldBe listOf("5550_h")

            // Every switch off draws nothing, rather than falling back to everything
            listOf(mopp, cylinder).nearest(Vec3.Zero, radius = 30f, kinds = emptySet()).shouldBeEmpty()
        }
    }

    test("no filter set means every filter, which is the opposite of how kinds reads") {
        val map = body("1000B1", Vec3.Zero, filterInfo = MAP_FILTER)
        val obj = body("2200_h", Vec3.Zero, filterInfo = OBJECT_FILTER)

        listOf(map, obj).nearest(Vec3.Zero, radius = 30f, kinds = ALL_KINDS, filters = emptySet())
            .map { it.name } shouldBe listOf("1000B1", "2200_h")
    }

    test("filter zero is a real id and is not treated as absent") {
        val zero = body("world", Vec3.Zero, filterInfo = 0)
        val map = body("1000B1", Vec3.Zero, filterInfo = MAP_FILTER)

        assertSoftly {
            listOf(zero, map).nearest(Vec3.Zero, 30f, ALL_KINDS, filters = setOf(0))
                .map { it.name } shouldBe listOf("world")
            listOf(zero, map).nearest(Vec3.Zero, 30f, ALL_KINDS, filters = setOf(MAP_FILTER))
                .map { it.name } shouldBe listOf("1000B1")
        }
    }

    test("a body whose kind could not be read is never drawn") {
        val unknown = body("?", center = Vec3.Zero, kind = null)

        listOf(unknown).nearest(Vec3.Zero, radius = 30f, kinds = ALL_KINDS).shouldBeEmpty()
    }
})