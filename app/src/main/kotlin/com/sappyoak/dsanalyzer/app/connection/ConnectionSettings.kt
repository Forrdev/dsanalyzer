package com.sappyoak.dsanalyzer.app.connection

import kotlinx.serialization.Serializable

import com.sappyoak.dsanalyzer.app.settings.Settings
import com.sappyoak.dsanalyzer.app.settings.SettingsEdit

@Serializable
public data class ConnectionSettings(
    public val autoConnect: Boolean = false
)

public data class AutoConnect(public val enabled: Boolean) : SettingsEdit {
    override fun applyTo(settings: Settings): Settings =
        settings.copy(connection = settings.connection.copy(autoConnect = enabled))
}