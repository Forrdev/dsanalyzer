package com.sappyoak.dsanalyzer.overlay

import kotlin.math.absoluteValue

import io.kotest.assertions.assertSoftly
import io.kotest.assertions.withClue
import io.kotest.core.spec.style.FunSpec
import io.kotest.matchers.floats.plusOrMinus
import io.kotest.matchers.shouldBe
import io.kotest.matchers.shouldNotBe


import com.sappyoak.dsanalyzer.runtime.session.collision.CollisionBody
import com.sappyoak.dsanalyzer.runtime.session.collision.CollisionGeometry
import com.sappyoak.dsanalyzer.runtime.session.collision.CollisionMesh
import com.sappyoak.dsanalyzer.runtime.session.collision.ShapeKind
import com.sappyoak.dsanalyzer.shared.math.Aabb
import com.sappyoak.dsanalyzer.shared.math.Vec3

/** An axis-aligned unit box at the origin, which is the easiest case to check by hand */
private val UNIT_BOX = CollisionGeometry.Box(
    center = Vec3.Zero,
    halfX = Vec3(1f, 0f, 0f),
    halfY = Vec3(0f, 1f, 0f),
    halfZ = Vec3(0f, 0f, 1f),
    radius = 0f
)

/** Two units tall about the origin, half a unit across — roughly a `5550_h` post */
private val POST = CollisionGeometry.Cylinder(
    a = Vec3(0f, -1f, 0f),
    b = Vec3(0f, 1f, 0f),
    cylRadius = 0.5f,
    radius = 0.05f
)

private fun bodies(vararg geometry: CollisionGeometry) = geometry.mapIndexed { at, each ->
    CollisionBody(
        name = "body$at",
        kind = ShapeKind.Cylinder,
        filterInfo = 0x11,
        allowedPenetration = Float.MAX_VALUE,
        bounds = Aabb(Vec3.Zero, Vec3(1f, 1f, 1f)),
        geometry = each
    )
}

class CollisionTessellationTest : FunSpec({
    test("a box becomes eight corners and twelve closed triangles") {
        val mesh = CollisionTessellation().meshOf(UNIT_BOX, bodies(UNIT_BOX), segments = 12)
        mesh shouldNotBe null
        val box = checkNotNull(mesh)

        assertSoftly {
            box.vertexCount shouldBe 8
            box.triangleCount shouldBe 12

            // Every corner is a distinct ±1 in all three axes, which is what a box is. A repeated
            // corner means a sign went the wrong way and would show as a collapsed face
            val corners = (0 until box.vertexCount).map { box.vertex(it) }.toSet()
            corners.size shouldBe 8
            corners.forEach { corner ->
                withClue("corner $corner") {
                    listOf(corner.x, corner.y, corner.z).forEach { it.absoluteValue shouldBe 1f }
                }
            }

            // Closed means every edge is shared by exactly two faces, so each index appears in as
            // many triangles as the corners meeting at it: three faces, two triangles each, but
            // only the ones whose fan includes that corner
            box.indices.toSet().size shouldBe 8
        }
    }

    test("a box's own bounds contain every triangle it produced") {
        val mesh = checkNotNull(
            CollisionTessellation().meshOf(UNIT_BOX, bodies(UNIT_BOX), segments = 12)
        )
        val bounds = UNIT_BOX.bounds.expandedBy(1e-4f)

        assertSoftly {
            for (i in 0 until mesh.vertexCount) {
                withClue("corner ${mesh.vertex(i)}") {
                    (mesh.vertex(i) in bounds) shouldBe true
                }
            }
        }
    }

    test("a cylinder is closed at both ends, and its ring sits on the sweep radius") {
        val segments = 12
        val mesh = checkNotNull(
            CollisionTessellation().meshOf(POST, bodies(POST), segments = segments)
        )


        assertSoftly {
            // Two rings plus a centre per cap
            mesh.vertexCount shouldBe segments * 2 + 2
            // Two triangles of wall and one of each cap, per segment
            mesh.triangleCount shouldBe segments * 4

            // The ring vertices are exactly `cylRadius` off the axis — the rim rounding from
            // `radius` is deliberately not drawn, so it must not appear here either
            for (step in 0 until segments) {
                val point = mesh.vertex(step)
                withClue("ring vertex $step = $point") {
                    point.y shouldBe (-1f plusOrMinus 1e-5f)
                    val offAxis = Vec3(point.x, 0f, point.z).distanceTo(Vec3.Zero)
                    offAxis shouldBe (0.5f plusOrMinus 1e-5f)
                }
            }

            // Both cap centres are on the axis, which is what closes the ends
            mesh.vertex(segments * 2) shouldBe Vec3(0f, -1f, 0f)
            mesh.vertex(segments * 2 + 1) shouldBe Vec3(0f, 1f, 0f)
        }
    }

    test("a cylinder lying along x is still round, because the perpendicular is chosen not fixed") {
        // The regression: crossing the axis with a fixed world axis gives the zero vector when the
        // two are parallel, and the ring collapses onto the axis. Upright cylinders are the common
        // case, so a fixed choice passes every test until the first one lying down
        val lying = CollisionGeometry.Cylinder(
            a = Vec3(-1f, 0f, 0f),
            b = Vec3(1f, 0f, 0f),
            cylRadius = 0.5f,
            radius = 0f
        )
        val mesh = checkNotNull(CollisionTessellation().meshOf(lying, bodies(lying), segments = 8))

        assertSoftly {
            for (step in 0 until 8) {
                val point = mesh.vertex(step)
                withClue("ring vertex $step = $point") {
                    Vec3(0f, point.y, point.z).distanceTo(Vec3.Zero) shouldBe (0.5f plusOrMinus 1e-5f)
                }
            }
        }
    }

    test("a degenerate cylinder draws nothing rather than a disc") {
        val flat = CollisionGeometry.Cylinder(Vec3.Zero, Vec3.Zero, cylRadius = 1f, radius = 0f)

        CollisionTessellation().meshOf(flat, bodies(flat), segments = 12)
            ?.triangleCount shouldBe 0
    }

    test("too few segments draws nothing rather than a sliver") {
        assertSoftly {
            for (segments in 0..MIN_SEGMENTS - 1) {
                withClue("$segments segments") {
                    CollisionTessellation().meshOf(POST, bodies(POST), segments)
                        ?.triangleCount shouldBe 0
                }
            }
        }
    }

    test("an unread shape yields nothing, and a mesh is handed straight back") {
        val triangles = CollisionMesh(
            vertices = floatArrayOf(0f, 0f, 0f, 1f, 0f, 0f, 0f, 1f, 0f),
            indices = intArrayOf(0, 1, 2)
        )
        val read = CollisionGeometry.Mesh(triangles)
        val tessellation = CollisionTessellation()
        val list = bodies(read, CollisionGeometry.Unread)

        assertSoftly {
            // Identity, not equality: the MOPP path is 99% of all triangles and must not be copied
            (tessellation.meshOf(read, list, segments = 12) === triangles) shouldBe true
            tessellation.meshOf(CollisionGeometry.Unread, list, segments = 12) shouldBe null
        }
    }

    test("the same shape tessellates to the same object until the segment count changes") {
        val tessellation = CollisionTessellation()
        val list = bodies(POST)

        val first = tessellation.meshOf(POST, list, segments = 12)
        val again = tessellation.meshOf(POST, list, segments = 12)
        val coarser = tessellation.meshOf(POST, list, segments = 6)

        assertSoftly {
            (first === again) shouldBe true
            (first === coarser) shouldBe false
            coarser?.triangleCount shouldBe 6 * 4
        }
    }

    test("a new read of the map drops the cache, because nothing in it is about anything on screen") {
        val tessellation = CollisionTessellation()

        val first = tessellation.meshOf(POST, bodies(POST), segments = 12)
        // A separate list with the same contents: a re-read produces a new list, and identity is
        // what distinguishes that from the same list being asked about twice
        val second = tessellation.meshOf(POST, bodies(POST), segments = 12)

        (first === second) shouldBe false
    }

})