package com.sappyoak.dsanalyzer.overlay

import com.sappyoak.dsanalyzer.runtime.session.CameraSnapshot
import com.sappyoak.dsanalyzer.runtime.session.CollisionMesh

/**
 * One frame's worth of projected collision, shared by everything that needs it
 */
internal class CollisionFrame(initialCapacity: Int = CollisionView.DEFAULT_TRIANGLE_BUDGET) {
    private var capacity = initialCapacity

    var projector: TriangleProjector = TriangleProjector(initialCapacity)
        private set

    var buckets: DepthBuckets = DepthBuckets(initialCapacity)
        private set

    val occlusion: OcclusionBuffer = OcclusionBuffer()

    /**
     * Projects the collision worth drawing, and rasterises it for occlusion when [occlude] is set.
     *
     * Returns whether anything survived, so the caller can skip the draw entirely rather than
     * walking empty buckets.
     *
     * [occlude] is independent of whether collision is *drawn*, because the two are different
     * questions: someone may want enemies hidden behind walls without a wireframe over everything.
     * What it is not independent of is collision being *read*, there is nothing to be hidden by
     * otherwise. When it is off, the buffer is abandoned rather than left holding the last frame it
     * saw, which would otherwise occlude markers against geometry from a camera that has moved.
     */
    fun build(
        view: CollisionView,
        camera: CameraSnapshot,
        width: Int,
        height: Int,
        occlude: Boolean
    ): Boolean {
        if (view.triangleBudget <= 0 || width <= 0 || height <= 0) {
            occlusion.abandon()
            return false
        }
        resizeTo(view.triangleBudget)

        projector.reset()
        for (body in view.bodies.nearest(camera.position, view.radius)) {
            val mesh = body.mesh ?: continue
            // False means the budget is spent. Stopping here rather than carrying on is what makes
            // the nearest-first ordering mean anything
            if (!projector.append(mesh, camera.worldToClip, width, height, view.cull)) break
        }

        if (projector.count == 0) {
            occlusion.abandon()
            return false
        }

        buckets.build(projector.depths, projector.count, view.radius)
        if (occlude) rasteriseOcclusion(width, height) else occlusion.abandon()
        return true
    }

    /** Every projected triangle, in whatever order: the buffer keeps the nearest per cell anyway */
    private fun rasteriseOcclusion(width: Int, height: Int) {
        occlusion.prepare(width, height)

        val xs = projector.xs
        val ys = projector.ys
        for (triangle in 0 until projector.count) {
            val at = triangle * CollisionMesh.CORNERS
            occlusion.cover(
                xs[at], ys[at],
                xs[at + 1], ys[at + 1],
                xs[at + 2], ys[at + 2],
                projector.depths[triangle]
            )
        }
        occlusion.finish()
    }

    private fun resizeTo(budget: Int) {
        if (budget == capacity) return
        capacity = budget
        projector = TriangleProjector(budget)
        buckets = DepthBuckets(budget)
    }
}
