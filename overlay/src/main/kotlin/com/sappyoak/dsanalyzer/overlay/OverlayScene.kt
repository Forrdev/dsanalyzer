package com.sappyoak.dsanalyzer.overlay

import com.sappyoak.dsanalyzer.runtime.session.CameraSnapshot
import com.sappyoak.dsanalyzer.shared.math.Vec3

public enum class MarkerKind {
    Character,
    Placed;
}

public data class WorldMarker(
    public val position: Vec3,
    public val label: String,
    public val kind: MarkerKind,
    public val ring: HitRing? = null
)

/** A hit window drawn as a ring around a marker's head, filling through the wind-up, then flashing */
data class HitRing(
    public val fill: Float,
    public val live: Boolean,
    /** Whether a live ring shows this frame, alternating so it flashes */
    public val lit: Boolean = true
)

public data class MarkerView(
    public val markers: List<WorldMarker> = emptyList(),
    public val drawDistance: Float = DEFAULT_DRAW_DISTANCE,
) {
    public companion object {
        public const val DEFAULT_DRAW_DISTANCE = 50f

        public val Empty: MarkerView = MarkerView()
    }
}

public data class OverlayScene(
    public val camera: CameraSnapshot?,
    public val markers: MarkerView = MarkerView.Empty,
    public val suppressWorldDrawing: Boolean = false
) {
    public companion object {
        public val Empty: OverlayScene = OverlayScene(camera = null)
    }
}
