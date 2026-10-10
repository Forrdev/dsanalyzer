package com.sappyoak.dsanalyzer.overlay

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.BasicText
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

import com.sappyoak.dsanalyzer.native.windows.DisplayState
import com.sappyoak.dsanalyzer.native.windows.Viewport

private val OUTLINE = Color(0x5500E5FF)
private val LABEL = Color(0xDD00E5FF)
private val WARNING = Color(0xFFFFC107)

/** Long enough to be unmistakably ours, short enough not to cover anything */
private const val TICK_FRACTION = 0.04f
private val OUTLINE_WIDTH = 3.dp

@Composable
public fun OverlayContent(viewport: Viewport, scene: OverlayScene) {
    if (scene.suppressWorldDrawing) return

    MarkerLayer(scene)
    Box(modifier = Modifier.fillMaxSize()) {
        Canvas(modifier = Modifier.fillMaxSize()) {
            val stroke = OUTLINE_WIDTH.toPx()

            // Inset by half the stroke. A line centred *on* the boundary renders half its width
            // outside the canvas and that half is clipped — at the right and bottom edges the
            // whole mark lands outside and nothing is drawn at all. Drawing a border at exactly
            // 0 and `size` is the most natural thing to write here and it does not work
            val half = stroke / 2f
            val left = half
            val top = half
            val right = size.width - half
            val bottom = size.height - half

            val armX = size.width * TICK_FRACTION
            val armY = size.height * TICK_FRACTION

            // Corners rather than a full border: a complete rectangle over a game reads as a
            // broken frame, while four ticks read as a measurement
            listOf(
                Triple(Offset(left, top), Offset(left + armX, top), Offset(left, top + armY)),
                Triple(Offset(right, top), Offset(right - armX, top), Offset(right, top + armY)),
                Triple(Offset(left, bottom), Offset(left + armX, bottom), Offset(left, bottom - armY)),
                Triple(Offset(right, bottom), Offset(right - armX, bottom), Offset(right, bottom - armY))
            ).forEach { (corner, acrossTo, downTo) ->
                drawLine(OUTLINE, corner, acrossTo, strokeWidth = stroke)
                drawLine(OUTLINE, corner, downTo, strokeWidth = stroke)
            }
        }

        viewport.state.explain()?.let { notice ->
            BasicText(
                text = notice,
                style = TextStyle(color = WARNING, fontSize = 13.sp),
                modifier = Modifier.align(Alignment.TopCenter).padding(8.dp)
            )
        }

        BasicText(
            text = "${viewport.bounds.width} x ${viewport.bounds.height}",
            style = TextStyle(color = LABEL, fontSize = 11.sp),
            modifier = Modifier.align(Alignment.BottomStart).padding(8.dp)
        )
    }
}

private fun DisplayState.explain(): String? = when (this) {
    DisplayState.Overlayable, DisplayState.NotForeground -> null
    DisplayState.ExclusiveFullscreen ->
        "The game has the display exclusively, so nothing can draw over it - switch it to borderless windowed"
    DisplayState.Unknown ->
        "Cannot tell whether the game is in exclusive fullscreen, so an empty overlay may mean that"
}