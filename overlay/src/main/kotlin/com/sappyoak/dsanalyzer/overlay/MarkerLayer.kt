package com.sappyoak.dsanalyzer.overlay

import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.drawscope.DrawScope
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.text.TextMeasurer
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.drawText
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

private val PLAYER_COLOR = Color(0xFF69F0AE)
private val CHARACTER_COLOR = Color(0xFFFF5252)
private val PLACED_COLOR = Color(0xFFFFC107)

private val DOT_RADIUS = 3.5.dp
private val STEM_WIDTH = 1.5.dp
private val BASE_HALF_WIDTH = 4.dp
private val LABEL_GAP = 6.dp
private const val LABEL_LIMIT = 20

/** How far either side of the head the occlusion test samples */
private val OCCLUSION_SPREAD = 7.dp

internal fun DrawScope.drawMarkers(
    scene: OverlayScene,
    measurer: TextMeasurer,
    occlusion: OcclusionBuffer?
) {
    val camera = scene.camera ?: return
    if (scene.markers.markers.isEmpty()) return

    val width = size.width.toInt()
    val height = size.height.toInt()
    if (width <= 0 || height <= 0) return

    val projected = scene.markers.projectedOnto(
        worldToClip = camera.worldToClip,
        eye = camera.position,
        width = width,
        height = height
    )

    val spread = OCCLUSION_SPREAD.toPx()
    val visible = when (occlusion) {
        null -> projected
        else -> projected.filterNot { occlusion.isHidden(it.head.x, it.head.y, spread, it.depth) }
    }

    visible.forEach { drawPin(it) }

    // Nearest last in the list, so the closest markers are the ones that keep their labels
    visible.takeLast(LABEL_LIMIT).forEach { drawLabel(it, measurer) }
}

private fun DrawScope.drawPin(projected: ProjectedMarker) {
    val color = projected.marker.kind.color().copy(alpha = projected.strength)
    val head = Offset(projected.head.x, projected.head.y)
    val stem = STEM_WIDTH.toPx()

    projected.feet?.let { groundPoint ->
        val ground = Offset(groundPoint.x, groundPoint.y)
        drawLine(color, ground, head, strokeWidth = stem)

        // A tick across the ground point, so the spot itself stays visible when the stem is
        // nearly end-on and collapses to almost nothing
        val half = BASE_HALF_WIDTH.toPx()
        drawLine(
            color,
            Offset(ground.x - half, ground.y),
            Offset(ground.x + half, ground.y),
            strokeWidth = stem
        )
    }

    val radius = DOT_RADIUS.toPx()
    drawCircle(color.copy(alpha = projected.strength * 0.4f), radius = radius, center = head)
    drawCircle(color, radius = radius, center = head, style = Stroke(width = stem))
}

private fun DrawScope.drawLabel(projected: ProjectedMarker, measurer: TextMeasurer) {
    val gap = LABEL_GAP.toPx() + DOT_RADIUS.toPx()

    drawText(
        textMeasurer = measurer,
        text = projected.marker.label,
        topLeft = Offset(projected.head.x + gap, projected.head.y - gap),
        style = TextStyle(
            color = projected.marker.kind.color().copy(alpha = projected.strength),
            fontSize = 11.sp
        )
    )
}

private fun MarkerKind.color(): Color = when (this) {
    MarkerKind.Player -> PLAYER_COLOR
    MarkerKind.Character -> CHARACTER_COLOR
    MarkerKind.Placed -> PLACED_COLOR
}