package com.sappyoak.dsanalyzer.overlay

import com.sappyoak.dsanalyzer.runtime.session.CameraSnapshot
import com.sappyoak.dsanalyzer.runtime.session.CollisionBody
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

public data class MarkerView(
    public val markers: List<WorldMarker> = emptyList(),
    public val drawDistance: Float = DEFAULT_DRAW_DISTANCE
) {
    public companion object {
        public const val DEFAULT_DRAW_DISTANCE = 120f

        public val Empty: MarkerView = MarkerView()
    }
}

public data class CollisionView(
    public val bodies: List<CollisionBody>,
    public val radius: Float = DEFAULT_RADIUS,
    public val triangleBudget: Int = DEFAULT_TRIANGLE_BUDGET,
) {
    public companion object {
        public const val DEFAULT_RADIUS: Float = 30f
        public const val DEFAULT_TRIANGLE_BUDGET = 5000
    }
}

public data class OverlayScene(
    public val camera: CameraSnapshot?,
    public val markers: MarkerView = MarkerView.Empty,
    public val collision: CollisionView? = null,
    public val suppressWorldDrawing: Boolean = false
) {
    public companion object {
        public val Empty: OverlayScene = OverlayScene(camera = null)
    }
}