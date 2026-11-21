package com.sappyoak.dsanalyzer.overlay

/** What the overlay draws this frame */
public data class OverlayScene(public val suppressWorldDrawing: Boolean = false) {
    public companion object {
        public val Empty: OverlayScene = OverlayScene()
    }
}
