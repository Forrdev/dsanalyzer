package com.sappyoak.dsanalyzer.formats.havok

import io.kotest.assertions.assertSoftly
import io.kotest.core.spec.style.FunSpec
import io.kotest.matchers.floats.plusOrMinus
import io.kotest.matchers.shouldBe
import io.kotest.matchers.types.shouldBeInstanceOf

import com.sappyoak.dsanalyzer.formats.havok.packfile.readPackfile
import com.sappyoak.dsanalyzer.formats.havok.physics.MeshSubpart
import com.sappyoak.dsanalyzer.formats.havok.physics.StaticShape
import com.sappyoak.dsanalyzer.formats.havok.physics.rigidBodies
import com.sappyoak.dsanalyzer.shared.math.Vec3
import com.sappyoak.dsanalyzer.shared.math.Vec4

private val BODIES = readPackfile(collisionFile()).rigidBodies()
private val MESH = BODIES.first { it.name == MESH_BODY }.shape.shouldBeInstanceOf<StaticShape.Mesh>()
private val SUBPART = MESH.subparts.single()

private const val CLOSE = 1e-5f

private fun MeshSubpart.shouldHaveVertex(index: Int, expected: Vec3) {
    val actual = vertex(index)
    assertSoftly {
        actual.x shouldBe (expected.x plusOrMinus CLOSE)
        actual.y shouldBe (expected.y plusOrMinus CLOSE)
        actual.z shouldBe (expected.z plusOrMinus CLOSE)
    }
}

class StaticPhysicsTest : FunSpec({
    test("every body of the root container's physics data is read, in order, by name") {
        BODIES.map { it.name } shouldBe listOf(MESH_BODY, CONVEX_BODY, SPHERE_BODY)
    }

    test("a body keeps its filter and its transform, without Havok's unset w lanes") {
        val body = BODIES.first()
        assertSoftly {
            body.collisionFilterInfo shouldBe MESH_FILTER
            body.transform.r0 shouldBe Vec4(1f, 0f, 0f, 0f)
            body.transform.r2 shouldBe Vec4(0f, 0f, 1f, 0f)
            body.transform.r3 shouldBe Vec4(1f, 2f, 3f, 1f)
        }
    }

    test("a MOPP is seen through to the FromSoft mesh it wraps, with its material and bounds") {
        assertSoftly {
            MESH.triangleCount shouldBe 2
            SUBPART.materialNameData shouldBe MATERIAL_NAME_DATA
            MESH.bounds.center shouldBe Vec3(0.5f, 0f, 0.5f)
        }
    }

    test("vertices come out placed by the subpart's transform: scale, then turn, then move") {
        SUBPART.shouldHaveVertex(0, Vec3(10f, 0f, 0f))
        SUBPART.shouldHaveVertex(1, Vec3(10f, 0f, -2f))
        SUBPART.shouldHaveVertex(2, Vec3(12f, 0f, -2f))
        SUBPART.shouldHaveVertex(3, Vec3(12f, 0f, 0f))
    }

    test("indices skip each row's padding, and a flipped subpart rewinds every other triangle") {
        SUBPART.indices.toList() shouldBe listOf(0, 1, 2, 0, 3, 2)
    }

    test("a convex hull's points are untransposed, and the padding lanes past its count dropped") {
        val hull = BODIES.first { it.name == CONVEX_BODY }.shape.shouldBeInstanceOf<StaticShape.Convex>()
        assertSoftly {
            hull.vertices.toList() shouldBe listOf(0f, 0f, 0f, 1f, 0f, 0f, 0f, 1f, 0f, 0f, 0f, 1f, 1f, 1f, 1f)
            hull.planes shouldBe listOf(Vec4(1f, 0f, 0f, -1.05f), Vec4(0f, 1f, 0f, -1.05f))
            hull.radius shouldBe CONVEX_RADIUS
        }
    }

    test("a shape with no reader is named, not dropped") {
        BODIES.last().shape shouldBe StaticShape.Unsupported(SPHERE_CLASS)
    }
})
