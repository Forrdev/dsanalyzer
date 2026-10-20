package com.sappyoak.dsanalyzer.overlay

import com.sappyoak.dsanalyzer.runtime.session.CameraSnapshot
import com.sappyoak.dsanalyzer.runtime.session.collision.CollisionBody
import com.sappyoak.dsanalyzer.runtime.session.collision.ShapeKind
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
    public val drawDistance: Float = DEFAULT_DRAW_DISTANCE,
    /** Drop markers that the drawn collision hides completely */
    public val occludeByCollision: Boolean = false
) {
    public companion object {
        public const val DEFAULT_DRAW_DISTANCE = 50f

        public val Empty: MarkerView = MarkerView()
    }
}

public data class CollisionView(
    public val bodies: List<CollisionBody>,
    public val radius: Float = DEFAULT_RADIUS,
    public val triangleBudget: Int = DEFAULT_TRIANGLE_BUDGET,
    public val occlusionRadius: Float = MarkerView.DEFAULT_DRAW_DISTANCE,
    public val occlusionBudget: Int = DEFAULT_OCCLUSION_BUDGET,
    public val kinds: Set<ShapeKind> = ShapeKind.entries.toSet(),
    public val filters: Set<Int> = emptySet(),
    public val draw: Boolean = true,
    public val cull: FaceCull = FaceCull.Off,
    public val segments: Int = DEFAULT_SEGMENTS,
    public val opacity: Float = DEFAULT_OPACITY,
    /** Fade the wireframe with distance, so far geometry recedes instead of competing */
    public val depthCue: Boolean = true
) {
    public companion object {
        public const val DEFAULT_RADIUS: Float = 30f
        public const val DEFAULT_TRIANGLE_BUDGET = 3500
        public const val DEFAULT_OCCLUSION_BUDGET = 20_000
        public const val DEFAULT_SEGMENTS = 12
        public const val DEFAULT_OPACITY: Float = 0.55f
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
