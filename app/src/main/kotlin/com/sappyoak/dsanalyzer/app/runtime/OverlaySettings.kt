package com.sappyoak.dsanalyzer.app.runtime

import kotlinx.serialization.Serializable

import com.sappyoak.dsanalyzer.app.settings.Settings
import com.sappyoak.dsanalyzer.app.settings.SettingsEdit
import com.sappyoak.dsanalyzer.runtime.session.FocusRequest

private const val MAX_TARGET_RANGE = 1000f

@Serializable
public data class OverlaySettings(
    public val enabled: Boolean = true,
    public val target: TargetPanelSettings = TargetPanelSettings()
)

@Serializable
public data class TargetPanelSettings(
    public val enabled: Boolean = true,
    public val range: Float = DEFAULT_TARGET_RANGE,
    public val hitWindows: Boolean = true
) {
    public companion object {
        public const val DEFAULT_TARGET_RANGE: Float = 30f
    }
}

internal val Settings.focusRequest: FocusRequest
    get() = if (overlay.enabled && overlay.target.enabled) FocusRequest(enabled = true, range = overlay.target.range)
    else FocusRequest.Off

public data class OverlayEnabled(public val enabled: Boolean) : SettingsEdit {
    override fun applyTo(settings: Settings): Settings =
        settings.copy(overlay = settings.overlay.copy(enabled = enabled))
}

public data class ShowTargetPanel(public val enabled: Boolean) : SettingsEdit {
    override fun applyTo(settings: Settings): Settings =
        settings.overlay.target.copy(enabled = enabled).into(settings)
}

public data class TargetPanelRange(public val units: Float) : SettingsEdit {
    override fun applyTo(settings: Settings): Settings =
        settings.overlay.target.copy(range = units.coerceIn(0f, MAX_TARGET_RANGE)).into(settings)
}

public data class DrawHitWindows(public val enabled: Boolean) : SettingsEdit {
    override fun applyTo(settings: Settings): Settings =
        settings.overlay.target.copy(hitWindows = enabled).into(settings)
}

private fun TargetPanelSettings.into(settings: Settings): Settings =
    settings.copy(overlay = settings.overlay.copy(target = this))
