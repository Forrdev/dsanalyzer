package com.sappyoak.dsanalyzer.runtime.session.collision

import io.kotest.assertions.assertSoftly
import io.kotest.assertions.withClue
import io.kotest.core.spec.style.FunSpec
import io.kotest.matchers.collections.shouldBeEmpty
import io.kotest.matchers.floats.plusOrMinus
import io.kotest.matchers.shouldBe

import com.sappyoak.dsanalyzer.shared.math.Aabb
import com.sappyoak.dsanalyzer.shared.math.Vec3

/**
 * Captured live from PTDE: body `"7800B0"`, a `hkpMoppBvTreeShape` whose child mesh's first
 * subpart held four vertices and two triangles.
 *
 * Rounded to three decimals from the capture, which is why the containment check below allows a
 * hair of tolerance.
 */
private val QUAD = CollisionMesh(
    vertices = floatArrayOf(
        246.872f, -3.149f, -16.271f,
        246.876f, -3.115f, -19.509f,
        250.911f, -3.195f, -19.503f,
        250.910f, -3.244f, -16.264f
    ),
    indices = intArrayOf(0, 1, 2, 2, 3, 0)
)

/** The same mesh's own `aabbCenter` / `aabbHalfExtents`, read from the game beside the triangles */
private val QUAD_PARENT_BOUNDS = Aabb(
    center = Vec3(156.205f, -38.749f, -36.408f),
    halfExtents = Vec3(94.706f, 35.634f, 78.165f)
)

class CollisionMeshTest : FunSpec({
    test("a mesh reports its own size in vertices and triangles") {
        assertSoftly {
            QUAD.vertexCount shouldBe 4
            QUAD.triangleCount shouldBe 2
            CollisionMesh.Empty.vertexCount shouldBe 0
            CollisionMesh.Empty.triangleCount shouldBe 0
        }
    }

    test("vertices come back as the game stored them") {
        assertSoftly {
            QUAD.vertex(0).x shouldBe (246.872f plusOrMinus 0.001f)
            QUAD.vertex(0).y shouldBe (-3.149f plusOrMinus 0.001f)
            QUAD.vertex(0).z shouldBe (-16.271f plusOrMinus 0.001f)
            // The last one, because an off-by-one in the stride shows up at the end
            QUAD.vertex(3) shouldBe Vec3(250.910f, -3.244f, -16.264f)
        }
    }

    test("the triangles lie inside the bounding box the game stores for them") {
        val bounds = QUAD_PARENT_BOUNDS.expandedBy(0.01f)

        assertSoftly {
            for (i in 0 until QUAD.vertexCount) {
                withClue("vertex $i = ${QUAD.vertex(i)}") {
                    (QUAD.vertex(i) in bounds) shouldBe true
                }
            }
        }
    }

    test("every index addresses a vertex that exists") {
        // An index past the end is what a bad rebase across subparts looks like, and it would
        // otherwise surface as an exception deep in the draw path
        assertSoftly {
            QUAD.indices.forEach { index ->
                withClue("index $index of ${QUAD.vertexCount}") {
                    (index in 0 until QUAD.vertexCount) shouldBe true
                }
            }
        }
    }

    test("a snapshot totals the triangles it actually holds") {
        val snapshot = CollisionSnapshot(
            bodies = listOf(
                CollisionBody(
                    name = "7800B0",
                    kind = ShapeKind.Mopp,
                    filterInfo = 0x20,
                    allowedPenetration = Float.MAX_VALUE,
                    bounds = QUAD_PARENT_BOUNDS,
                    geometry = CollisionGeometry.Mesh(QUAD)
                ),
                // A cylinder: real geometry, and no triangles until the overlay makes some
                CollisionBody(
                    name = "5550_h",
                    kind = ShapeKind.Cylinder,
                    filterInfo = 0x11,
                    allowedPenetration = Float.MAX_VALUE,
                    bounds = Aabb(Vec3.Zero, Vec3(0.55f, 1.05f, 0.55f)),
                    geometry = CollisionGeometry.Cylinder(
                        a = Vec3(0f, -1f, 0f),
                        b = Vec3(0f, 1f, 0f),
                        cylRadius = 0.5f,
                        radius = 0.05f
                    )
                ),
                // A shape kind with no reader: listed, named, and carrying nothing
                CollisionBody(
                    name = "?",
                    kind = null,
                    filterInfo = 0x1C,
                    allowedPenetration = Float.MAX_VALUE,
                    bounds = Aabb.Empty,
                    geometry = CollisionGeometry.Unread
                )
            ),
            failed = 2
        )

        assertSoftly {
            snapshot.triangleCount shouldBe 2
            snapshot.bodies.size shouldBe 3
            // Derived from the list rather than tallied, so it cannot disagree with it
            snapshot.unread shouldBe 1
            // And a read that never arrived is a different number, because it leaves no body
            snapshot.failed shouldBe 2
            CollisionSnapshot.Empty.bodies.shouldBeEmpty()
            CollisionSnapshot.Empty.triangleCount shouldBe 0
            CollisionSnapshot.Empty.unread shouldBe 0
        }
    }

    test("a cylinder's bounds come from its own parameters, both radii included") {
        val cylinder = CollisionGeometry.Cylinder(
            a = Vec3(0f, -1f, 0f),
            b = Vec3(0f, 1f, 0f),
            cylRadius = 0.5f,
            radius = 0.05f
        )

        assertSoftly {
            cylinder.bounds.center shouldBe Vec3.Zero
            cylinder.bounds.halfExtents.x shouldBe (0.55f plusOrMinus 1e-6f)
            // The axis direction gets the sweep too: an AABB has no idea which way the axis runs
            cylinder.bounds.halfExtents.y shouldBe (1.55f plusOrMinus 1e-6f)
            cylinder.bounds.halfExtents.z shouldBe (0.55f plusOrMinus 1e-6f)
        }
    }

    test("an oriented box's bounds grow to hold it") {
        val diagonal = 0.70710678f
        val box = CollisionGeometry.Box(
            center = Vec3(10f, 0f, 0f),
            halfX = Vec3(diagonal, 0f, diagonal),
            halfY = Vec3(0f, 2f, 0f),
            halfZ = Vec3(-diagonal, 0f, diagonal),
            radius = 0.05f
        )

        assertSoftly {
            box.bounds.center shouldBe Vec3(10f, 0f, 0f)
            box.bounds.halfExtents.x shouldBe (1.4642136f plusOrMinus 1e-5f)
            box.bounds.halfExtents.y shouldBe (2.05f plusOrMinus 1e-6f)
            box.bounds.halfExtents.z shouldBe (1.4642136f plusOrMinus 1e-5f)
        }
    }

    test("shape kinds resolve from the values the game stores, and only those") {
        assertSoftly {
            ShapeKind.of(10) shouldBe ShapeKind.Mopp
            ShapeKind.of(14) shouldBe ShapeKind.Mesh
            ShapeKind.of(2) shouldBe ShapeKind.Cylinder
            ShapeKind.of(6) shouldBe ShapeKind.ConvexVertices
            ShapeKind.of(11) shouldBe ShapeKind.ConvexTranslate
            // Null rather than a fallback, so an unseen type surfaces instead of being smoothed
            ShapeKind.of(0) shouldBe null
            ShapeKind.of(99) shouldBe null
        }
    }
})