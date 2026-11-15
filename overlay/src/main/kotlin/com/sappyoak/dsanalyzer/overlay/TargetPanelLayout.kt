package com.sappyoak.dsanalyzer.overlay

import androidx.compose.ui.geometry.CornerRadius
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.drawscope.DrawScope
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.text.TextLayoutResult
import androidx.compose.ui.text.TextMeasurer
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.drawText
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.sappyoak.dsanalyzer.shared.math.project

private val BACKGROUND = Color(0xCC101418)
private val BORDER = Color(0x55FFFFFF)
private val TITLE = Color(0xFFFFFFFF)
private val TEXT = Color(0xFFCFD8DC)
private val HEALTH = Color(0xFFC62828)
private val HEALTH_TRACK = Color(0x66C62828)
private val WINDUP = Color(0xFFFFAB40)
private val LIVE = Color(0xFFFF1744)
private const val RING_TRACK_ALPHA = 0.25f

/** Nearer than this the panel is drawn full size; farther, it shrinks with distance like the game's bar */
private const val FULL_SIZE_DISTANCE = 6f
private const val MIN_SCALE = 0.45f

private val MIN_WIDTH = 140.dp
private val PADDING = 6.dp
private val GAP = 3.dp
private val CORNER = 4.dp
private val BORDER_WIDTH = 1.dp
private val RING_RADIUS = 8.dp
private val RING_WIDTH = 2.5.dp
private val TITLE_SIZE = 13.sp
private val TEXT_SIZE = 11.sp

internal fun DrawScope.drawTargetPanel(scene: OverlayScene, measurer: TextMeasurer) {
    val panel = scene.panel ?: return
    val camera = scene.camera ?: return
    val width = size.width.toInt()
    val height = size.height.toInt()
    if (width <= 0 || height <= 0) return

    val anchor = camera.worldToClip.project(panel.anchor, width, height) ?: return
    val scale = (FULL_SIZE_DISTANCE / camera.position.distanceTo(panel.anchor)).coerceIn(MIN_SCALE, 1f)

    val title = measurer.measure(panel.title, TextStyle(color = TITLE, fontSize = TITLE_SIZE * scale))
    val health = panel.health?.let {
        measurer.measure("${it.current} / ${it.max}", TextStyle(color = TITLE, fontSize = TEXT_SIZE * scale))
    }
    val lines = panel.lines.map { measurer.measure(it, TextStyle(color = TEXT, fontSize = TEXT_SIZE * scale)) }

    val pad = PADDING.toPx() * scale
    val gap = GAP.toPx() * scale
    val rows = listOfNotNull(title, health) + lines
    val inner = maxOf(MIN_WIDTH.toPx() * scale, rows.maxOf { it.size.width.toFloat() })
    val boxHeight = rows.sumOf { it.size.height } + gap * (rows.size - 1) + pad * 2
    val box = Size(inner + pad * 2, boxHeight)
    val topLeft = Offset(anchor.x - box.width / 2, anchor.y - box.height)
    val corner = CornerRadius(CORNER.toPx() * scale)

    drawRoundRect(BACKGROUND, topLeft, box, corner)
    drawRoundRect(BORDER, topLeft, box, corner, style = Stroke(BORDER_WIDTH.toPx()))

    var y = topLeft.y + pad
    val x = topLeft.x + pad
    drawText(title, topLeft = Offset(x, y))
    y += title.size.height + gap

    if (health != null && panel.health != null) {
        drawHealth(Offset(x, y), Size(inner, health.size.height.toFloat()), panel.health.fraction, health)
        y += health.size.height + gap
    }
    lines.forEach { line ->
        drawText(line, topLeft = Offset(x, y))
        y += line.size.height + gap
    }

    panel.ring?.let { drawRing(Offset(topLeft.x + box.width + gap, topLeft.y + pad), scale, it) }
}

private fun DrawScope.drawHealth(at: Offset, bar: Size, fraction: Float, label: TextLayoutResult) {
    drawRect(HEALTH_TRACK, at, bar)
    drawRect(HEALTH, at, Size(bar.width * fraction, bar.height))
    drawText(label, topLeft = Offset(at.x + (bar.width - label.size.width) / 2, at.y))
}

private fun DrawScope.drawRing(topLeft: Offset, scale: Float, ring: HitRing) {
    if (ring.live && !ring.lit) return

    val radius = RING_RADIUS.toPx() * scale
    val bounds = Size(radius * 2, radius * 2)
    val stroke = Stroke(width = RING_WIDTH.toPx() * scale)

    if (ring.live) {
        drawArc(LIVE, 0f, 360f, useCenter = false, topLeft = topLeft, size = bounds, style = stroke)
        return
    }
    drawArc(WINDUP.copy(alpha = RING_TRACK_ALPHA), 0f, 360f, useCenter = false, topLeft = topLeft, size = bounds, style = stroke)
    drawArc(WINDUP, -90f, ring.fill.coerceIn(0f, 1f) * 360f, useCenter = false, topLeft = topLeft, size = bounds, style = stroke)
}
