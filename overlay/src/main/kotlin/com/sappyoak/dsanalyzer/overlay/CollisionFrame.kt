package com.sappyoak.dsanalyzer.overlay

import kotlin.math.max

import com.sappyoak.dsanalyzer.runtime.session.CameraSnapshot
import com.sappyoak.dsanalyzer.runtime.session.collision.CollisionBody
import com.sappyoak.dsanalyzer.runtime.session.collision.CollisionMesh
import com.sappyoak.dsanalyzer.runtime.session.collision.Passability
import com.sappyoak.dsanalyzer.runtime.session.collision.passability

/** Visible volume stretches per fame */
private const val VOLUME_SPANS: Int = 8192
/**
 * One frame's worth of projected collision, shared by everything that needs it
 */
internal class CollisionFrame(initialCapacity: Int = CollisionView.DEFAULT_TRIANGLE_BUDGET) {
    private var drawCapacity: Int = initialCapacity
    private var occlusionCapacity: Int = CollisionView.DEFAULT_OCCLUSION_BUDGET

    /** The solid collision being drawn */
    var surfaces: TriangleProjector = TriangleProjector(initialCapacity)
        private set

    var buckets: DepthBuckets = DepthBuckets(initialCapacity)
        private set

    /** All the solid collisions in reach, whatever is drawn, rasterized into [occlusion] */
    var occluders: TriangleProjector = TriangleProjector(CollisionView.DEFAULT_OCCLUSION_BUDGET)
        private set

    val occlusion: OcclusionBuffer = OcclusionBuffer()

    /**
     * Whether markers are to be tested against [occlusion] this frame. Not the same as it being
     * ready
     */
    val hidesMarkers: Boolean get()= hiding && occlusion.isReady
    private var hiding: Boolean = false

    /** The visible stretches of the volumes being drawn */
    val volumes: VolumeSpans = VolumeSpans(VOLUME_SPANS)

    private val tessellation = CollisionTessellation()
    private val outlines = VolumeEdges()


    /**
     * Projects the collision worth drawing and the collision worth hiding things behind.
     * Returns whether anything survived to draw so the caller can skip the draw entirely.
     *
     * The two sets are deliberately different. What is *drawn* follows the view's kinds,
     * filters, and cull. What *occludes* is every solid body in reach, whatever is drawn.
     * Volumes, the passable bodies, occlude nothing, so a kill plane will never hide an enemy standing above it,
     * and are depth tested against the solid collision instead. Water is neither drawn nor occluding.
     *
     * The buffer is built when markers are to be hiden or volumes drawn, and abandoned otherwise rather
     * than left holding the last frame it saw, which would occlude against a camera that has since
     * moved
     */
    fun build(
        view: CollisionView,
        camera: CameraSnapshot,
        width: Int,
        height: Int,
        occlude: Boolean
    ): Boolean {
        volumes.reset()
        if (width <= 0 || height <= 0) {
            occlusion.abandon()
            return false
        }

        resizeTo(view.triangleBudget, view.occlusionBudget)

        val drawn = view.bodies.nearest(camera.position, view.radius, view.kinds, view.filters)
        val outlined = if (view.draw) drawn.filter { it.isVolume } else emptyList()
        hiding = occlude && view.occlusionRadius > 0f

        if (view.occlusionBudget > 0 && (hiding || outlined.isNotEmpty())) {
            val reach = if (hiding) max(view.radius, view.occlusionRadius) else view.radius
            projectOccluders(view, camera, reach, width, height)
        } else {
            occluders.reset()
            occlusion.abandon()
        }

        val drawCount = projectSurfaces(view, camera, drawn, width, height)
        if (drawCount > 0) buckets.build(surfaces.depths, drawCount, view.radius)

        for (body in outlined) {
            val mesh = tessellation.meshOf(body.geometry, view.bodies, view.segments) ?: continue
            val edges = outlines.edgesOf(body.geometry, mesh, view.bodies, view.segments)
            if (!volumes.trace(edges, camera.worldToClip, width, height, occlusion)) break
        }

        return drawCount > 0 || volumes.count > 0
    }

    /** Every occluding body in [reach] unculled and unfiltered, nearest first until budget runs out */
    private fun projectOccluders(
        view: CollisionView,
        camera: CameraSnapshot,
        reach: Float,
        width: Int,
        height: Int
    ) {
        occluders.reset()
        for (body in view.bodies.nearest(camera.position, reach, kinds = null)) {
            if (!body.isSolid) continue
            val mesh = tessellation.meshOf(body.geometry, view.bodies, view.segments) ?: continue
            // false means the project is full, and nearest-first is what makes stopping here right
            if (!occluders.append(mesh, camera.worldToClip, width, height)) break
        }

        if (occluders.count == 0) {
            occlusion.abandon()
            return
        }

        occlusion.prepare(width, height)
        val xs = occluders.xs
        val ys = occluders.ys
        val ws = occluders.ws
        for (triangle in 0 until occluders.count) {
            val at = triangle * CollisionMesh.CORNERS
            occlusion.cover(
                xs[at], ys[at], ws[at],
                xs[at + 1], ys[at + 1], ws[at + 1],
                xs[at + 2], ys[at + 2], ws[at + 2]
            )
        }
        occlusion.finish()
    }

    /** The solid bodies of [drawn] into [surfaces], returning how many triangles to draw */
    private fun projectSurfaces(
        view: CollisionView,
        camera: CameraSnapshot,
        drawn: List<CollisionBody>,
        width: Int,
        height: Int
    ): Int {
        surfaces.reset()
        for (body in drawn) {
            if (!body.isSolid) continue
            val mesh = tessellation.meshOf(body.geometry, view.bodies, view.segments) ?: continue
            val before = surfaces.count
            if (!surfaces.append(mesh, camera.worldToClip, width, height, view.cull)) {
                surfaces.rewind(before)
                break
            }
        }

        return surfaces.count
    }

    private fun resizeTo(drawBudget: Int, occlusionBudget: Int) {
        var draw = max(0, drawBudget)
        if (draw != drawCapacity) {
            drawCapacity = draw
            surfaces = TriangleProjector(draw)
            buckets = DepthBuckets(draw)
        }

        val hide = max(0, occlusionBudget)
        if (hide != occlusionCapacity) {
            occlusionCapacity = hide
            occluders = TriangleProjector(hide)
        }
    }
}


private val CollisionBody.isSolid: Boolean
    get() = when (hitFilter.passability) {
        Passability.Solid, Passability.Unknown -> true
        Passability.Passable, Passability.Liquid -> false
    }

private val CollisionBody.isVolume: Boolean
    get() = hitFilter.passability == Passability.Passable
