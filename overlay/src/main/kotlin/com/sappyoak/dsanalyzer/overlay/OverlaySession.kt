package com.sappyoak.dsanalyzer.overlay

import kotlinx.coroutines.delay

import com.sappyoak.dsanalyzer.native.windows.OverlaySurface
import com.sappyoak.dsanalyzer.native.windows.Viewport
import com.sappyoak.dsanalyzer.native.windows.ViewportSource
import com.sappyoak.dsanalyzer.native.windows.ViewportUpdate
import com.sappyoak.dsanalyzer.native.windows.WindowTracker

public const val OVERLAY_WINDOW_TITLE: String = "dsanalyzer-overlay-surface"

/** How often the game's window is checked for having moved. Far below a frame, and not a redraw */
private const val TRACK_MILLIS = 100L

/** How long to keep looking for our own window before giving up on styling it */
private const val HANDLE_ATTEMPTS = 40
private const val HANDLE_GAP_MILLIS = 25L

public suspend fun awaitOverlaySurface(title: String = OVERLAY_WINDOW_TITLE): OverlaySurface? {
    repeat(HANDLE_ATTEMPTS) {
        OverlaySurface.named(title)?.let { surface ->
            surface.makeClickThrough()
            return surface
        }
        delay(HANDLE_GAP_MILLIS)
    }
    return null
}

public suspend fun ViewportSource.track(onChange: (Viewport?) -> Unit) {
    val tracker = WindowTracker(this)
    while (true) {
        when (val update = tracker.poll()) {
            is ViewportUpdate.Changed -> onChange(update.viewport)
            ViewportUpdate.Lost -> onChange(null)
            null -> Unit
        }
        delay(TRACK_MILLIS)
    }
}