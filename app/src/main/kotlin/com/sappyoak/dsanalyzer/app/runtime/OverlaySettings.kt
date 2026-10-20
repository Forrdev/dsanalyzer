package com.sappyoak.dsanalyzer.app.runtime

import kotlinx.serialization.Serializable

import com.sappyoak.dsanalyzer.app.settings.Settings
import com.sappyoak.dsanalyzer.app.settings.SettingsEdit
import com.sappyoak.dsanalyzer.overlay.CollisionView
import com.sappyoak.dsanalyzer.overlay.FaceCull
import com.sappyoak.dsanalyzer.overlay.MarkerView
import com.sappyoak.dsanalyzer.runtime.session.collision.ShapeKind

/** Beyond this a draw distance stops meaning anything: the frustum cull is doing the work */
private const val MAX_DRAW_DISTANCE = 1000f

/** Enough to draw a whole map block, which is about 66,000 triangles */
private const val MAX_TRIANGLE_BUDGET = 100_000

@Serializable
public data class OverlaySettings(
    public val enabled: Boolean = true,
    public val characters: CharacterOverlaySettings = CharacterOverlaySettings(),
    public val collision: CollisionOverlaySettings = CollisionOverlaySettings()
)

@Serializable
public data class CharacterOverlaySettings(
    public val drawPlayer: Boolean = true,
    public val draw: CharacterDrawing = CharacterDrawing.All,
    public val drawDistance: Float = MarkerView.DEFAULT_DRAW_DISTANCE,
    public val occludeByCollision: Boolean = false
)

@Serializable
public enum class CharacterDrawing {
    None,
    Instantiated,
    All;
}

@Serializable
public data class CollisionOverlaySettings(
    public val enabled: Boolean = false,
    public val radius: Float = CollisionView.DEFAULT_RADIUS,
    public val triangleBudget: Int = CollisionView.DEFAULT_TRIANGLE_BUDGET,
    public val kinds: Set<ShapeKind> = ShapeKind.entries.toSet(),
    public val cull: FaceCull = FaceCull.Off,
    public val opacity: Float = CollisionView.DEFAULT_OPACITY,
    public val depthCue: Boolean = true
)

public data class OverlayEnabled(public val enabled: Boolean) : SettingsEdit {
    override fun applyTo(settings: Settings): Settings =
        settings.copy(overlay = settings.overlay.copy(enabled = enabled))
}

public data class DrawPlayer(public val enabled: Boolean) : SettingsEdit {
    override fun applyTo(settings: Settings): Settings =
        settings.overlay.characters.copy(drawPlayer = enabled).into(settings)
}

public data class DrawCharacters(public val mode: CharacterDrawing) : SettingsEdit {
    override fun applyTo(settings: Settings): Settings =
        settings.overlay.characters.copy(draw = mode).into(settings)
}

public data class CharacterDrawDistance(public val units: Float) : SettingsEdit {
    override fun applyTo(settings: Settings): Settings =
        settings.overlay.characters
            .copy(drawDistance = units.coerceIn(0f, MAX_DRAW_DISTANCE))
            .into(settings)
}

public data class DrawCollision(public val enabled: Boolean) : SettingsEdit {
    override fun applyTo(settings: Settings): Settings =
        settings.overlay.collision.copy(enabled = enabled).into(settings)
}

public data class CollisionRadius(public val units: Float) : SettingsEdit {
    override fun applyTo(settings: Settings): Settings =
        settings.overlay.collision
            .copy(radius = units.coerceIn(0f, MAX_DRAW_DISTANCE))
            .into(settings)
}

public data class CollisionTriangleBudget(public val triangles: Int) : SettingsEdit {
    override fun applyTo(settings: Settings): Settings =
        settings.overlay.collision
            .copy(triangleBudget = triangles.coerceIn(0, MAX_TRIANGLE_BUDGET))
            .into(settings)
}

public data class OccludeMarkers(public val enabled: Boolean) : SettingsEdit {
    override fun applyTo(settings: Settings): Settings =
        settings.overlay.characters.copy(occludeByCollision = enabled).into(settings)
}

public data class CollisionCull(public val mode: FaceCull) : SettingsEdit {
    override fun applyTo(settings: Settings): Settings =
        settings.overlay.collision.copy(cull = mode).into(settings)
}

public data class CollisionOpacity(public val opacity: Float) : SettingsEdit {
    override fun applyTo(settings: Settings): Settings =
        settings.overlay.collision
            .copy(opacity = opacity.coerceIn(0f, 1f))
            .into(settings)
}

public data class CollisionDepthCue(public val enabled: Boolean) : SettingsEdit {
    override fun applyTo(settings: Settings): Settings =
        settings.overlay.collision.copy(depthCue = enabled).into(settings)
}

public data class CollisionKindDrawn(
    public val kind: ShapeKind,
    public val drawn: Boolean
) : SettingsEdit {
    override fun applyTo(settings: Settings): Settings {
        val kinds = settings.overlay.collision.kinds
        return settings.overlay.collision
            .copy(kinds = if (drawn) kinds + kind else kinds - kind)
            .into(settings)

    }
}

private fun CharacterOverlaySettings.into(settings: Settings): Settings =
    settings.copy(overlay = settings.overlay.copy(characters = this))

private fun CollisionOverlaySettings.into(settings: Settings): Settings =
    settings.copy(overlay = settings.overlay.copy(collision = this))