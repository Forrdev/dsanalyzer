package com.sappyoak.dsanalyzer.overlay

import io.kotest.assertions.assertSoftly
import io.kotest.core.spec.style.FunSpec
import io.kotest.matchers.shouldBe

import com.sappyoak.dsanalyzer.formats.msb.part.CollisionHitFilter
import com.sappyoak.dsanalyzer.runtime.session.CameraSnapshot
import com.sappyoak.dsanalyzer.runtime.session.collision.CollisionBody
import com.sappyoak.dsanalyzer.runtime.session.collision.CollisionGeometry
import com.sappyoak.dsanalyzer.runtime.session.collision.CollisionMesh
import com.sappyoak.dsanalyzer.runtime.session.collision.HitFilter
import com.sappyoak.dsanalyzer.runtime.session.collision.ShapeKind
import com.sappyoak.dsanalyzer.shared.math.Aabb
import com.sappyoak.dsanalyzer.shared.math.Frustum
import com.sappyoak.dsanalyzer.shared.math.Mat4
import com.sappyoak.dsanalyzer.shared.math.Vec3

private const val WIDTH = 1920
private const val HEIGHT = 1080

private val CAMERA = CameraSnapshot(
    position = Vec3.Zero,
    orientation = Vec3.Zero,
    targetPitch = 0f,
    transform = Mat4.Identity,
    frustum = Frustum(0.75f, 16f / 9f, 0.1f, 1000f)
)

/** A square facing the camera at [depth], across the middle of the view */
private fun square(depth: Float, half: Float, filter: CollisionHitFilter) = CollisionBody(
    name = "$filter@$depth",
    kind = ShapeKind.Mopp,
    filterInfo = 0x1C,
    allowedPenetration = Float.MAX_VALUE,
    bounds = Aabb(Vec3(0f, 0f, depth), Vec3(half, half, 1f)),
    geometry = CollisionGeometry.Mesh(
        CollisionMesh(
            vertices = floatArrayOf(
                -half, -half, depth,
                half, -half, depth,
                half, half, depth,
                -half, half, depth
            ),
            indices = intArrayOf(0, 1, 2, 0, 2, 3)
        )
    ),
    hitFilter = HitFilter.Known(filter)
)

private fun wall(depth: Float, half: Float = 30f) = square(depth, half, CollisionHitFilter.Normal)

private fun killPlane(depth: Float) = square(depth, half = 1f, CollisionHitFilter.KillPlane)

private fun CollisionFrame.buildWith(
    bodies: List<CollisionBody>,
    occlude: Boolean = true,
    kinds: Set<ShapeKind> = ShapeKind.entries.toSet(),
    filters: Set<Int> = emptySet()
) = build(
    CollisionView(bodies = bodies, radius = 10f, occlusionRadius = 100f, kinds = kinds, filters = filters),
    CAMERA,
    WIDTH,
    HEIGHT,
    occlude
)

private fun CollisionFrame.centreHidden(depth: Float) =
    occlusion.isHidden(WIDTH / 2f, HEIGHT / 2f, radius = 4f, depth = depth)

class CollisionFrameVolumeTest : FunSpec({
    test("a volume hides nothing, so a marker standing past a kill plane stays visible") {
        val frame = CollisionFrame()
        val drawable = frame.buildWith(listOf(killPlane(5f), wall(50f)))

        assertSoftly {
            drawable shouldBe true
            // Only the wall went into the buffer
            frame.occluders.count shouldBe 2
            frame.surfaces.count shouldBe 0
            // Between the kill plane and the wall: seen through the volume
            frame.centreHidden(depth = 20f) shouldBe false
            // Past the wall: hidden by it
            frame.centreHidden(depth = 60f) shouldBe true
            // All four sides of the kill plane are in front of the wall, and its diagonal is no side
            frame.volumes.count shouldBe 4
        }
    }

    test("a volume behind solid collision is dropped, not drawn over it") {
        val frame = CollisionFrame()
        val drawable = frame.buildWith(listOf(wall(3f), killPlane(8f)))

        assertSoftly {
            drawable shouldBe true
            frame.surfaces.count shouldBe 2
            frame.volumes.count shouldBe 0
        }
    }

    test("volumes are depth-tested even with marker occlusion off") {
        val frame = CollisionFrame()
        frame.buildWith(listOf(wall(3f), killPlane(8f)), occlude = false)

        assertSoftly {
            frame.volumes.count shouldBe 0
            // The buffer was built for the volumes, which is no reason to start hiding markers
            frame.occlusion.isReady shouldBe true
            frame.hidesMarkers shouldBe false

            frame.buildWith(listOf(wall(3f), killPlane(8f)))
            frame.hidesMarkers shouldBe true
        }
    }

    test("what occludes does not depend on which kinds or filters are drawn") {
        val frame = CollisionFrame()

        assertSoftly {
            frame.buildWith(listOf(wall(50f)), kinds = setOf(ShapeKind.Box))
            frame.centreHidden(depth = 60f) shouldBe true

            frame.buildWith(listOf(wall(50f)), filters = setOf(0x99))
            frame.centreHidden(depth = 60f) shouldBe true

            // A shape the reader could not name is solid collision all the same
            frame.buildWith(listOf(wall(50f).copy(kind = null)))
            frame.centreHidden(depth = 60f) shouldBe true
        }
    }

    test("water is neither drawn nor hides anything") {
        val frame = CollisionFrame()
        val drawable = frame.buildWith(listOf(square(5f, half = 1f, CollisionHitFilter.WaterA)))

        assertSoftly {
            drawable shouldBe false
            frame.surfaces.count shouldBe 0
            frame.volumes.count shouldBe 0

            frame.buildWith(listOf(square(5f, half = 30f, CollisionHitFilter.WaterB), wall(50f)))
            // The wall alone went into the buffer, so a marker between the two is in plain sight
            frame.occluders.count shouldBe 2
            frame.centreHidden(depth = 20f) shouldBe false
        }
    }

    test("filters still unknown are solid until they are known not to be") {
        val frame = CollisionFrame()
        frame.buildWith(listOf(square(50f, half = 30f, CollisionHitFilter.GroupSwitch)))

        frame.centreHidden(depth = 60f) shouldBe true
    }
})
