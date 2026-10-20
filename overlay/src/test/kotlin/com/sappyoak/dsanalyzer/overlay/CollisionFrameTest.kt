package com.sappyoak.dsanalyzer.overlay

import io.kotest.assertions.assertSoftly
import io.kotest.core.spec.style.FunSpec
import io.kotest.matchers.shouldBe

import com.sappyoak.dsanalyzer.runtime.session.CameraSnapshot
import com.sappyoak.dsanalyzer.runtime.session.collision.CollisionBody
import com.sappyoak.dsanalyzer.runtime.session.collision.CollisionGeometry
import com.sappyoak.dsanalyzer.runtime.session.collision.CollisionMesh
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

private fun wall(name: String, depth: Float, half: Float = 20f) = CollisionBody(
    name = name,
    kind = ShapeKind.Mopp,
    filterInfo = 0x1C,
    allowedPenetration = Float.MAX_VALUE,
    bounds = Aabb(Vec3(0f, 0f, depth), Vec3(half, half, 1f)),
    geometry = CollisionGeometry.Mesh(
        CollisionMesh(
            vertices = floatArrayOf(
                -half, -half, depth,
                half, -half, depth,
                0f, half, depth
            ),
            indices = intArrayOf(0, 1, 2)
        )
    )
)

private fun view(
    bodies: List<CollisionBody>,
    radius: Float = 10f,
    triangleBudget: Int = 100,
    occlusionRadius: Float = 100f,
    occlusionBudget: Int = 100
) = CollisionView(
    bodies = bodies,
    radius = radius,
    triangleBudget = triangleBudget,
    occlusionRadius = occlusionRadius,
    occlusionBudget = occlusionBudget
)

private fun CollisionFrame.buildWith(view: CollisionView, occlude: Boolean = true) =
    build(view, CAMERA, WIDTH, HEIGHT, occlude)

private fun CollisionFrame.centreHidden(depth: Float) =
    occlusion.isHidden(WIDTH / 2f, HEIGHT / 2f, radius = 4f, depth = depth)

class CollisionFrameTest : FunSpec({
    test("a wall past the draw radius still hides a marker behind it") {
        val frame = CollisionFrame()
        val drawable = frame.buildWith(view(listOf(wall("far", depth = 50f))))

        assertSoftly {
            // Nothing to draw — the wall is well outside the draw radius
            drawable shouldBe false
            // but it was projected and rasterised all the same
            frame.projector.count shouldBe 1
            frame.occlusion.isReady shouldBe true
            frame.centreHidden(depth = 60f) shouldBe true
        }
    }

    test("and is not projected at all when the occlusion radius does not reach it either") {
        val frame = CollisionFrame()
        val drawable = frame.buildWith(view(listOf(wall("far", depth = 50f)), occlusionRadius = 10f))

        assertSoftly {
            drawable shouldBe false
            frame.projector.count shouldBe 0
            frame.centreHidden(depth = 60f) shouldBe false
        }
    }

    test("occlusion off means nothing past the draw radius") {
        val frame = CollisionFrame()
        val drawable = frame.buildWith(view(listOf(wall("far", depth = 50f))), occlude = false)

        assertSoftly {
            drawable shouldBe false
            frame.projector.count shouldBe 0
            // Abandoned rather than left holding an older frame, which would occlude against a
            // camera that has since moved
            frame.occlusion.isReady shouldBe false
            frame.centreHidden(depth = 60f) shouldBe false
        }
    }

    test("the drawn set is near the previs while the projected set runs to the occlusion radius") {
        val frame = CollisionFrame()
        val drawable = frame.buildWith(view(listOf(wall("far", depth = 50f), wall("near", 5f, half = 1f))))

        assertSoftly {
            drawable shouldBe true
            // Both projected...
            frame.projector.count shouldBe 2
            // ...one drawn. The near body sorts first, so the drawn triangles are a prefix
            frame.buckets.count shouldBe 1
            // and the far one still occludes
            frame.centreHidden(depth = 60f) shouldBe true
        }
    }

    test("the draw budget cuts whole bodies and leaves the occlusion pass alone") {
        // Two bodies inside the draw radius, a budget that admits one triangle. A budget that cut
        // mid-body would leave a surface with a hole in it, which reads as a bad collision read
        val frame = CollisionFrame()
        val bodies = listOf(wall("a", depth = 3f, half = 1f), wall("b", depth = 6f, half = 1f))
        val drawable = frame.buildWith(view(bodies, triangleBudget = 1))

        assertSoftly {
            drawable shouldBe true
            frame.projector.count shouldBe 2
            frame.buckets.count shouldBe 1
        }
    }

    test("a zero draw budget still occludes") {
        val frame = CollisionFrame()
        val drawable = frame.buildWith(view(listOf(wall("far", depth = 50f)), triangleBudget = 0))

        assertSoftly {
            drawable shouldBe false
            frame.occlusion.isReady shouldBe true
            frame.centreHidden(depth = 60f) shouldBe true
        }
    }

    test("nothing to do at all abandons the buffer rather than keeping the last frame") {
        val frame = CollisionFrame()

        assertSoftly {
            frame.buildWith(view(emptyList())) shouldBe false
            frame.occlusion.isReady shouldBe false

            frame.buildWith(view(listOf(wall("a", 5f)), occlusionBudget = 0), occlude = true) shouldBe true
            // Occlusion asked for but budgeted at nothing is occlusion off, not a silent fallback
            frame.occlusion.isReady shouldBe false
        }
    }
})