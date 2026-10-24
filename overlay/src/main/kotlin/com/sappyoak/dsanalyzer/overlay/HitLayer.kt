package com.sappyoak.dsanalyzer.overlay

import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Rect
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Paint
import androidx.compose.ui.graphics.Path
import androidx.compose.ui.graphics.drawscope.DrawScope
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.unit.dp

import com.sappyoak.dsanalyzer.runtime.session.attack.HitSnapshot

private val LIVE_COLOR = Color(0xFFFF6E40)
private val CLOSED_COLOR = Color(0xFFFFD180)

private const val LIVE_ALPHA = 0.3f
private const val CLOSED_ALPHA = 0.15f
private val OUTLINE_WIDTH = 1.5.dp

/**
 * Each hit's swept volume, and the outline of where every live hit is now.
 *
 * Drawn over collision and never hidden by it
 */
internal fun DrawScope.drawHits(scene: OverlayScene) {
    val camera = scene.camera ?: return
    if (scene.hits.isEmpty()) return

    val width = size.width.toInt()
    val height = size.height.toInt()
    if (width <= 0 || height <= 0) return

    val projector = HitProjector(camera.worldToClip, camera.position, width, height)
    val path = Path()
    val (live, closed) = scene.hits.partition { it.live }

    drawSweeps(closed, projector, path, CLOSED_COLOR.copy(alpha = CLOSED_ALPHA))
    drawSweeps(live, projector, path, LIVE_COLOR.copy(alpha = LIVE_ALPHA))

    val outline = Stroke(width = OUTLINE_WIDTH.toPx())
    live.forEach { hit ->
        val now = hit.sweep.lastOrNull()?.let(projector::ends) ?: return@forEach
        path.reset()
        path.stadium(now)
        drawPath(path, LIVE_COLOR, style = outline)
    }
}

/**
 * [hits]' sweeps, all in one layer at [color]'s alpha. Every piece is drawn opaque into the layer,
 * so where pieces overlap the volume is no darker than where they do not
 */
private fun DrawScope.drawSweeps(hits: List<HitSnapshot>, projector: HitProjector, path: Path, color: Color) {
    if (hits.isEmpty()) return

    val canvas = drawContext.canvas
    canvas.saveLayer(Rect(Offset.Zero, size), Paint().apply { alpha = color.alpha })
    val solid = color.copy(alpha = 1f)

    hits.forEach { hit ->
        var last: ScreenEnds? = null
        hit.sweep.forEach { shape ->
            val ends = projector.ends(shape)
            if (ends != null) {
                fill(path, solid) { band(ends.a, ends.b) }
                drawCircle(solid, ends.a.radius, ends.a.center)
                drawCircle(solid, ends.b.radius, ends.b.center)
                last?.let { from ->
                    fill(path, solid) { face(from, ends) }
                    fill(path, solid) { band(from.a, ends.a) }
                    fill(path, solid) { band(from.b, ends.b) }
                }
            }
            last = ends
        }
    }
    canvas.restore()
}

/** One piece, as its own draw so its winding never cancels another's */
private inline fun DrawScope.fill(path: Path, color: Color, piece: Path.() -> Unit) {
    path.reset()
    path.piece()
    drawPath(path, color)
}
