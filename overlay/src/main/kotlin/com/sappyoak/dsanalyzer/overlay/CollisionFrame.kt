package com.sappyoak.dsanalyzer.overlay

import kotlin.math.max

import com.sappyoak.dsanalyzer.runtime.session.CameraSnapshot
import com.sappyoak.dsanalyzer.runtime.session.collision.CollisionMesh
import com.sappyoak.dsanalyzer.runtime.session.collision.Passability
import com.sappyoak.dsanalyzer.runtime.session.collision.passability

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

    private val tessellation = CollisionTessellation()

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
        val occluding = occlude && view.occlusionBudget > 0 && view.occlusionRadius > 0f
        val radius = if (occluding) max(view.radius, view.occlusionRadius) else view.radius
        val budget = if (occluding) max(view.triangleBudget, view.occlusionBudget) else view.triangleBudget

        if (budget <= 0 || radius <= 0f || width <= 0 || height <= 0) {
            occlusion.abandon()
            return false
        }
        resizeTo(budget)

        val drawRadiusSquared = view.radius * view.radius
        var drawCount = 0
        var drawing = view.triangleBudget > 0

        projector.reset()
        for (body in view.bodies.nearest(camera.position, radius, view.kinds, view.filters)) {
            val mesh = tessellation.meshOf(body.geometry, view.bodies, view.segments) ?: continue
            // False means the projector is full. Stopping here rather than carrying on is what
            // makes the nearest-first ordering mean anything
            // A passable body is projected and drawn but never rasterised: a kill plane must not
            // hide an enemy standing above it, and neither must a level exit or a death camera
            val occludes = body.hitFilter.passability != Passability.Passable
            if (!projector.append(mesh, camera.worldToClip, width, height, view.cull, occludes)) break

            if (!drawing) continue
            val near = body.bounds.distanceSquaredTo(camera.position) <= drawRadiusSquared
            // Whole bodies: the count is taken after a body's triangles are all in, so the budget
            // never cuts a body in half and leaves a surface with a hole in it
            if (near && projector.count <= view.triangleBudget) drawCount = projector.count else drawing = false
        }

        if (occluding && projector.count > 0) rasteriseOcclusion(width, height) else occlusion.abandon()

        if (drawCount == 0) return false
        buckets.build(projector.depths, drawCount, view.radius)
        return true
    }

    /** Every projected triangle, in whatever order: the buffer keeps the nearest per cell anyway */
    private fun rasteriseOcclusion(width: Int, height: Int) {
        occlusion.prepare(width, height)

        val xs = projector.xs
        val ys = projector.ys
        for (triangle in 0 until projector.count) {
            if (!projector.occludes[triangle]) continue
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
