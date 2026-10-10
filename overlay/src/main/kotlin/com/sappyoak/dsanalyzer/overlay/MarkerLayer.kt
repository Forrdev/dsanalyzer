package com.sappyoak.dsanalyzer.overlay

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.drawscope.DrawScope
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.text.TextMeasurer
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.drawText
import androidx.compose.ui.text.rememberTextMeasurer
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp


private val PLAYER_COLOR = Color(0xFF69F0AE)
private val CHARACTER_COLOR = Color(0xFFFF5252)
private val PLACED_COLOR = Color(0xFFFFC107)

private val DOT_RADIUS = 4.dp
private val STEM_WIDTH = 1.5.dp
private val BASE_HALF_WIDTH = 4.dp
private val LABEL_GAP = 6.dp

private const val LABEL_LIMIT = 20

@Composable
internal fun MarkerLayer(scene: OverlayScene) {
    val camera = scene.camera ?: return
    if (scene.markers.isEmpty()) return

    val measurer = rememberTextMeasurer()

    Canvas(modifier = Modifier.fillMaxSize()) {
        val width = size.width.toInt()
        val height = size.height.toInt()
        if (width <= 0 || height <= 0) return@Canvas

        val visible = scene.markers.projectedOnto(
            worldToClip = camera.worldToClip,
            eye = camera.position,
            width = width,
            height = height
        )

        visible.forEach { drawPin(it) }

        // Nearest last in the list, so the closest markers are the ones that keep their labels
        visible.takeLast(LABEL_LIMIT).forEach { drawLabel(it, measurer) }
    }
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