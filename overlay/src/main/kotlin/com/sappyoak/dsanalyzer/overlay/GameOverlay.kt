package com.sappyoak.dsanalyzer.overlay

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.window.Window
import androidx.compose.ui.window.WindowPosition
import androidx.compose.ui.window.rememberWindowState

import com.sappyoak.dsanalyzer.native.windows.DisplayState
import com.sappyoak.dsanalyzer.native.windows.OverlaySurface
import com.sappyoak.dsanalyzer.native.windows.Viewport
import com.sappyoak.dsanalyzer.native.windows.ViewportSource

@Composable
public fun GameOverlay(source: ViewportSource, scene: OverlayScene, enabled: Boolean) {
    if (!enabled) return

    var viewport by remember { mutableStateOf<Viewport?>(null) }
    var surface by remember { mutableStateOf<OverlaySurface?>(null) }

    LaunchedEffect(source) {
        source.track { seen -> viewport = seen }
    }

    val current = viewport ?: return
    if (current.state == DisplayState.NotForeground) return

    Window(
        onCloseRequest = {},
        title = OVERLAY_WINDOW_TITLE,
        undecorated = true,
        transparent = true,
        alwaysOnTop = true,
        resizable = false,
        focusable = false,
        // Compose insists on a position; the real one is applied by `place` below, in pixels
        state = rememberWindowState(position = WindowPosition.PlatformDefault)
    ) {
        LaunchedEffect(Unit) { surface = awaitOverlaySurface() }

        // Keyed on the bounds, so a reposition happens when the game moves and not every frame
        LaunchedEffect(surface, current.bounds) {
            surface?.place(current.bounds)
        }

        OverlayContent(current, scene)
    }
}