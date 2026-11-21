package com.sappyoak.dsanalyzer.app.runtime

import kotlinx.serialization.Serializable

import com.sappyoak.dsanalyzer.app.settings.Settings
import com.sappyoak.dsanalyzer.app.settings.SettingsEdit

@Serializable
public data class OverlaySettings(public val enabled: Boolean = true)

public data class OverlayEnabled(public val enabled: Boolean) : SettingsEdit {
    override fun applyTo(settings: Settings): Settings =
        settings.copy(overlay = settings.overlay.copy(enabled = enabled))
}
