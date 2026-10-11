package com.sappyoak.dsanalyzer.runtime.session

import io.kotest.assertions.assertSoftly
import io.kotest.assertions.withClue
import io.kotest.core.spec.style.FunSpec
import io.kotest.matchers.collections.shouldBeEmpty
import io.kotest.matchers.floats.plusOrMinus
import io.kotest.matchers.shouldBe

import com.sappyoak.dsanalyzer.runtime.ptde.ShapeKind
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
                CollisionBody("7800B0", ShapeKind.Mopp, QUAD_PARENT_BOUNDS, QUAD),
                // A cylinder: listed, named, and contributing nothing
                CollisionBody("5550_h", ShapeKind.Cylinder, Aabb.Empty, null)
            ),
            skipped = 1
        )

        assertSoftly {
            snapshot.triangleCount shouldBe 2
            snapshot.bodies.size shouldBe 2
            snapshot.skipped shouldBe 1
            CollisionSnapshot.Empty.bodies.shouldBeEmpty()
            CollisionSnapshot.Empty.triangleCount shouldBe 0
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