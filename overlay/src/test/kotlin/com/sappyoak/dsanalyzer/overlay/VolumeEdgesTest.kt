package com.sappyoak.dsanalyzer.overlay

import io.kotest.assertions.assertSoftly
import io.kotest.core.spec.style.FunSpec
import io.kotest.matchers.shouldBe

import com.sappyoak.dsanalyzer.runtime.session.collision.CollisionBody
import com.sappyoak.dsanalyzer.runtime.session.collision.CollisionGeometry
import com.sappyoak.dsanalyzer.runtime.session.collision.CollisionMesh
import com.sappyoak.dsanalyzer.shared.math.Vec3

private val UNIT_BOX = CollisionGeometry.Box(
    center = Vec3.Zero,
    halfX = Vec3(1f, 0f, 0f),
    halfY = Vec3(0f, 1f, 0f),
    halfZ = Vec3(0f, 0f, 1f),
    radius = 0f
)

private val POST = CollisionGeometry.Cylinder(
    a = Vec3(0f, -1f, 0f),
    b = Vec3(0f, 1f, 0f),
    cylRadius = 0.5f,
    radius = 0.05f
)

private val FLAT_QUAD = floatArrayOf(
    0f, 0f, 0f,
    1f, 0f, 0f,
    1f, 1f, 0f,
    0f, 1f, 0f
)

private fun edgeCount(mesh: CollisionMesh) = mesh.featureEdges().size / EDGE_COMPONENTS

class VolumeEdgesTest : FunSpec({
    test("a box keeps its twelve edges and drops the diagonal splitting each face") {
        val box = checkNotNull(CollisionTessellation().meshOf(UNIT_BOX, emptyList(), segments = 12))
        edgeCount(box) shouldBe 12
    }

    test("a cylinder keeps both rims and every side edge, and drops the cap spokes") {
        val post = checkNotNull(CollisionTessellation().meshOf(POST, emptyList(), segments = 12))
        edgeCount(post) shouldBe 36
    }

    test("a flat quad is four edges whether its corners are shared or stored per triangle") {
        val shared = CollisionMesh(FLAT_QUAD, intArrayOf(0, 1, 2, 0, 2, 3))
        // The same quad with every corner stored once per triangle, as some meshes come
        val separate = CollisionMesh(
            vertices = floatArrayOf(
                0f, 0f, 0f, 1f, 0f, 0f, 1f, 1f, 0f,
                0f, 0f, 0f, 1f, 1f, 0f, 0f, 1f, 0f
            ),
            indices = intArrayOf(0, 1, 2, 3, 4, 5)
        )

        assertSoftly {
            edgeCount(shared) shouldBe 4
            edgeCount(separate) shouldBe 4
        }
    }

    test("a quad folded along its diagonal keeps the fold") {
        val folded = CollisionMesh(
            vertices = floatArrayOf(
                0f, 0f, 0f,
                1f, 0f, 0f,
                1f, 1f, 0f,
                0f, 1f, 1f
            ),
            indices = intArrayOf(0, 1, 2, 0, 2, 3)
        )
        edgeCount(folded) shouldBe 5
    }

    test("degenerate triangles and bad indices are skipped rather than drawn or thrown") {
        val mesh = CollisionMesh(FLAT_QUAD, intArrayOf(0, 1, 2, 0, 2, 3, 0, 0, 1, 0, 1, 9))
        edgeCount(mesh) shouldBe 4
    }

    test("edges are cached per geometry and dropped when the bodies change") {
        val edges = VolumeEdges()
        val bodies = emptyList<CollisionBody>()
        val mesh = CollisionMesh(FLAT_QUAD, intArrayOf(0, 1, 2, 0, 2, 3))
        val first = edges.edgesOf(UNIT_BOX, mesh, bodies, segments = 12)

        assertSoftly {
            (edges.edgesOf(UNIT_BOX, mesh, bodies, segments = 12) === first) shouldBe true
            (edges.edgesOf(UNIT_BOX, mesh, ArrayList(bodies), segments = 12) === first) shouldBe false
        }
    }
})
