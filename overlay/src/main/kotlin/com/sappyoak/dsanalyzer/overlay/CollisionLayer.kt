package com.sappyoak.dsanalyzer.overlay

import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Rect
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Paint
import androidx.compose.ui.graphics.Path
import androidx.compose.ui.graphics.drawscope.DrawScope
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.graphics.drawscope.drawIntoCanvas
import androidx.compose.ui.unit.dp

import com.sappyoak.dsanalyzer.runtime.session.collision.CollisionMesh

private val WIREFRAME = Color(0x993F8EFC)
private val WIREFRAME_WIDTH = 1.dp
private val SURFACE = Color(0xFF05080F)
private val VOLUME = Color(0xFFFFB020)
private val VOLUME_WIDTH = 1.dp
/** How faint the farthest slice is drawn when the depth cue is on */
private const val MIN_NEARNESS = 0.25f


internal fun DrawScope.drawCollision(frame: CollisionFrame, view: CollisionView, path: Path) {
    val projector = frame.projector
    val buckets = frame.buckets
    val stroke = Stroke(width = WIREFRAME_WIDTH.toPx())

    drawIntoCanvas { canvas ->
        val paint = Paint().apply { alpha = view.opacity.coerceIn(0f, 1f) }
        canvas.saveLayer(Rect(Offset.Zero, size), paint)

        // Farthest slice first: `order` is already grouped that way, but the slices are walked
        // explicitly because each one is stroked in its own colour
        for (bucket in DEPTH_BUCKETS - 1 downTo 0) {
            val slots = buckets.sizeOf(bucket)
            if (slots == 0) continue

            path.reset()
            var any = false
            val from = buckets.startOf(bucket)
            for (slot in from until from + slots) {
                val triangle = buckets.order[slot]
                if (!projector.occludes[triangle]) continue
                path.addTriangle(projector, triangle)
                any = true
            }

            if (!any) continue

            drawPath(path, SURFACE)
            drawPath(path, WIREFRAME.fadedTo(view.depthCue, buckets.nearness(bucket)), style = stroke)
        }

        drawVolumes(projector, buckets, path, Stroke(width = VOLUME_WIDTH.toPx()))
        canvas.restore()
    }
}

private fun DrawScope.drawVolumes(
    projector: TriangleProjector,
    buckets: DepthBuckets,
    path: Path,
    stroke: Stroke
) {
    path.reset()
    var any = false
    for (slot in 0 until buckets.count) {
        val triangle = buckets.order[slot]
        if (projector.occludes[triangle]) continue
        path.addTriangle(projector, triangle)
        any = true
    }
    if (any) drawPath(path, VOLUME, style = stroke)
}

private fun Path.addTriangle(projector: TriangleProjector, triangle: Int) {
    val at = triangle * CollisionMesh.CORNERS
    moveTo(projector.xs[at], projector.ys[at])
    lineTo(projector.xs[at + 1], projector.ys[at + 1])
    lineTo(projector.xs[at + 2], projector.ys[at + 2])
    close()
}

private fun Color.fadedTo(cue: Boolean, nearness: Float): Color {
    if (!cue) return this
    return copy(alpha = alpha * (MIN_NEARNESS + (1f - MIN_NEARNESS) * nearness.coerceIn(0f, 1f)))
}
