package com.sappyoak.dsanalyzer.formats.nvm

import io.kotest.assertions.assertSoftly
import io.kotest.assertions.throwables.shouldThrow
import io.kotest.core.spec.style.FunSpec
import io.kotest.matchers.shouldBe

import com.sappyoak.dsanalyzer.shared.binary.BinaryFormatException
import com.sappyoak.dsanalyzer.shared.math.Vec3

private val NAVMESH = readNavmesh(navmesh())

class NvmReaderTest : FunSpec({
    test("vertices and triangles are read from where the header points") {
        assertSoftly {
            NAVMESH.vertexCount shouldBe 4
            NAVMESH.vertex(2) shouldBe Vec3(1f, 0f, 1f)
            NAVMESH.triangles.map { it.corners.toList() } shouldBe listOf(listOf(0, 1, 2), listOf(0, 2, 3))
            NAVMESH.triangles.map { it.flags } shouldBe listOf(TRIANGLE_FLAGS, 0)
        }
    }

    test("triangles sharing an edge name each other across it") {
        assertSoftly {
            NAVMESH.triangles[0].neighbors.toList() shouldBe listOf(OPEN_EDGE, OPEN_EDGE, 1)
            NAVMESH.triangles[1].neighbors.toList() shouldBe listOf(0, OPEN_EDGE, OPEN_EDGE)
        }
    }

    test("an edge with no neighbour is open, and every other edge is not") {
        NAVMESH.openEdges shouldBe listOf(NavEdge(0, 0), NavEdge(0, 1), NavEdge(1, 1), NavEdge(1, 2))
    }

    test("the box tree's leaves list the triangles reaching into them, one triangle in several") {
        val root = NAVMESH.boxes
        assertSoftly {
            root.min shouldBe Vec3(0f, 0f, 0f)
            root.max shouldBe Vec3(1f, 1f, 1f)
            root.triangles.toList() shouldBe emptyList()
            root.children.map { it.triangles.toList() } shouldBe listOf(listOf(0), listOf(0, 1))
            root.children.all { it.children.isEmpty() } shouldBe true
        }
    }

    test("a version other than 1, or entity groups, is turned away") {
        shouldThrow<BinaryFormatException> { readNavmesh(navmesh(version = 2)) }
        shouldThrow<BinaryFormatException> { readNavmesh(navmesh(entityCount = 1)) }
    }
})
