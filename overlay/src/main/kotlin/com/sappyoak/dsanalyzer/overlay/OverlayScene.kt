package com.sappyoak.dsanalyzer.overlay

import com.sappyoak.dsanalyzer.runtime.session.CameraSnapshot
import com.sappyoak.dsanalyzer.shared.math.Vec3

public enum class MarkerKind {
    Player,
    Character,
    Placed;
}

public data class WorldMarker(
    public val position: Vec3,
    public val label: String,
    public val kind: MarkerKind
)

public data class OverlayScene(
    public val camera: CameraSnapshot?,
    public val markers: List<WorldMarker> = emptyList(),
    public val suppressWorldDrawing: Boolean = false
) {
    public companion object {
        public val Empty: OverlayScene = OverlayScene(camera = null)
    }
}