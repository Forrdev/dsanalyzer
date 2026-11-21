package com.sappyoak.dsanalyzer.app.runtime

import com.sappyoak.dsanalyzer.overlay.OverlayScene

/** Nothing is drawn over the world while a menu covers it */
internal fun RuntimeState.overlayScene(settings: OverlaySettings): OverlayScene = when {
    !settings.enabled || snapshot?.camera == null -> OverlayScene.Empty
    snapshot.menu?.isBlocking == true -> OverlayScene(suppressWorldDrawing = true)
    else -> OverlayScene.Empty
}
