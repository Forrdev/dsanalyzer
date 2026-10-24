package com.sappyoak.dsanalyzer.overlay

import kotlin.math.atan2
import kotlin.math.hypot

import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Rect
import androidx.compose.ui.graphics.Path

/** Below this many pixels apart, two ends are drawn as the one circle */
private const val SAME_END = 0.5f

private const val DEGREES = (180.0 / Math.PI).toFloat()

internal fun Path.band(a: ScreenCircle, b: ScreenCircle) {
    val (nx, ny) = normal(a, b) ?: return
    moveTo(a.x + nx * a.radius, a.y + ny * a.radius)
    lineTo(b.x + nx * b.radius, b.y + ny * b.radius)
    lineTo(b.x - nx * b.radius, b.y - ny * b.radius)
    lineTo(a.x - nx * a.radius, a.y - ny * a.radius)
    close()
}

/** The four-sided face a segment sweeps from [from] to [to], from end to end of each */
internal fun Path.face(from: ScreenEnds, to: ScreenEnds) {
    moveTo(from.a.x, from.a.y)
    lineTo(from.b.x, from.b.y)
    lineTo(to.b.x, to.b.y)
    lineTo(to.a.x, to.a.y)
    close()
}

/** A capsule's outline: one side, the far end's cap, the other side, and the near end's cap */
internal fun Path.stadium(ends: ScreenEnds) {
    val (a, b) = ends
    val n = normal(a, b)
    if (n == null) {
        val r = maxOf(a.radius, b.radius)
        addOval(Rect(a.x - r, a.y - r, a.x + r, a.y + r))
        return
    }

    val along = atan2(b.y - a.y, b.x - a.x) * DEGREES
    val (nx, ny) = n
    moveTo(a.x + nx * a.radius, a.y + ny * a.radius)
    lineTo(b.x + nx * b.radius, b.y + ny * b.radius)
    arcTo(b.bounds(), along + 90f, -180f, forceMoveTo = false)
    lineTo(a.x - nx * a.radius, a.y - ny * a.radius)
    arcTo(a.bounds(), along - 90f, -180f, forceMoveTo = false)
    close()
}

internal val ScreenCircle.center: Offset get() = Offset(x, y)

private fun ScreenCircle.bounds(): Rect = Rect(x - radius, y - radius, x + radius, y + radius)

/** The unit normal to the line from [a] to [b], or null when they are too close to have a line */
private fun normal(a: ScreenCircle, b: ScreenCircle): Pair<Float, Float>? {
    val dx = b.x - a.x
    val dy = b.y - a.y
    val length = hypot(dx, dy)
    if (length < SAME_END) return null
    return -dy / length to dx / length
}
