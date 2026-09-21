package com.sappyoak.dsanalyzer.app.settings

import kotlinx.serialization.Serializable

import com.sappyoak.dsanalyzer.app.connection.ConnectionSettings
import com.sappyoak.dsanalyzer.app.workspace.WorkspaceId
import com.sappyoak.dsanalyzer.game.Installation

@Serializable
public data class Settings(
    public val installations: List<Installation> = emptyList(),
    public val lastActiveWorkspaceId: WorkspaceId? = null,
    public val connection: ConnectionSettings = ConnectionSettings()
)

public interface SettingsEdit {
    public fun applyTo(settings: Settings): Settings
}

/** What features outside the settings store are allowed to do with settings */
public interface SettingsAccess {
    /** suspends until settings have finished loaded, then returns them */
    public suspend fun loaded(): Settings
    /** Queues [edit], which is saved once applied */
    public fun edit(edit: SettingsEdit)
}