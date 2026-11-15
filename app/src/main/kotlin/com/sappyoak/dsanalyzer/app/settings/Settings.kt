package com.sappyoak.dsanalyzer.app.settings

import kotlinx.serialization.Serializable

import com.sappyoak.dsanalyzer.app.connection.ConnectionSettings
import com.sappyoak.dsanalyzer.app.runtime.EngineDrawSettings
import com.sappyoak.dsanalyzer.app.runtime.OverlaySettings
import com.sappyoak.dsanalyzer.app.runtime.RuntimeSettings
import com.sappyoak.dsanalyzer.app.runtime.WarpSettings
import com.sappyoak.dsanalyzer.app.workspace.WorkspaceId
import com.sappyoak.dsanalyzer.game.Installation

@Serializable
public data class Settings(
    public val installations: List<Installation> = emptyList(),
    public val lastActiveWorkspaceId: WorkspaceId? = null,
    public val connection: ConnectionSettings = ConnectionSettings(),
    public val overlay: OverlaySettings = OverlaySettings(),
    public val runtime: RuntimeSettings = RuntimeSettings(),
    public val tool: ToolSettings = ToolSettings(),
    public val engineDraw: EngineDrawSettings = EngineDrawSettings(),
    public val warps: WarpSettings = WarpSettings()
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


@Serializable
public data class ToolSettings(
    public val cavesOnLaunch: Boolean = false,
    public val translatedNames: Boolean = true
)

public data class CavesOnLaunch(public val enabled: Boolean) : SettingsEdit {
    override fun applyTo(settings: Settings): Settings =
        settings.copy(tool = settings.tool.copy(cavesOnLaunch = enabled))
}

public data class UseTranslatedNames(public val enabled: Boolean) : SettingsEdit {
    override fun applyTo(settings: Settings): Settings =
        settings.copy(tool = settings.tool.copy(translatedNames = enabled))
}
