package com.sappyoak.dsanalyzer.overlay

import com.sappyoak.dsanalyzer.runtime.session.CameraSnapshot
import com.sappyoak.dsanalyzer.shared.math.Vec3

public data class TargetPanel(
    public val anchor: Vec3,
    public val title: String,
    public val health: PanelHealth?,
    public val lines: List<String>,
    public val ring: HitRing? = null
)

public data class PanelHealth(public val current: Int, public val max: Int) {
    public val fraction: Float get() = if (max > 0) (current.toFloat() / max).coerceIn(0f, 1f) else 0f
}

/** A hit window drawn as a ring around a marker's head, filling through the wind-up, then flashing */
data class HitRing(
    public val fill: Float,
    public val live: Boolean,
    /** Whether a live ring shows this frame, alternating so it flashes */
    public val lit: Boolean = true
)

public data class OverlayScene(
    public val camera: CameraSnapshot?,
    public val panel: TargetPanel? = null,
    public val suppressWorldDrawing: Boolean = false
) {
    public companion object {
        public val Empty: OverlayScene = OverlayScene(camera = null)
    }
}
