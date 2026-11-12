package com.sappyoak.dsanalyzer.app.runtime

import kotlinx.serialization.Serializable

import com.sappyoak.dsanalyzer.app.settings.Settings
import com.sappyoak.dsanalyzer.app.settings.SettingsEdit
import com.sappyoak.dsanalyzer.overlay.MarkerView

/** Beyond this a draw distance stops meaning anything: the frustum cull is doing the work */
private const val MAX_DRAW_DISTANCE = 1000f

@Serializable
public data class OverlaySettings(
    public val enabled: Boolean = true,
    public val characters: CharacterOverlaySettings = CharacterOverlaySettings(),
)

@Serializable
public data class CharacterOverlaySettings(
    public val draw: CharacterDrawing = CharacterDrawing.All,
    public val drawDistance: Float = MarkerView.DEFAULT_DRAW_DISTANCE,
    public val hitWindows: Boolean = true
)

@Serializable
public enum class CharacterDrawing {
    None,
    Instantiated,
    All;
}

public data class OverlayEnabled(public val enabled: Boolean) : SettingsEdit {
    override fun applyTo(settings: Settings): Settings =
        settings.copy(overlay = settings.overlay.copy(enabled = enabled))
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

public data class DrawHitWindows(public val enabled: Boolean) : SettingsEdit {
    override fun applyTo(settings: Settings): Settings =
        settings.overlay.characters.copy(hitWindows = enabled).into(settings)
}

private fun CharacterOverlaySettings.into(settings: Settings): Settings =
    settings.copy(overlay = settings.overlay.copy(characters = this))

