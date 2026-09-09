package com.sappyoak.dsanalyzer.app.startup

import java.nio.file.Path

import com.sappyoak.dsanalyzer.app.settings.Settings
import com.sappyoak.dsanalyzer.game.Installation
import com.sappyoak.dsanalyzer.game.InstallationId

public sealed interface StartupEffect {
    public data object LoadSettings : StartupEffect
    public data class SaveSettings(public val settings: Settings) : StartupEffect

    public data class InspectFolder(public val folder: Path) : StartupEffect
    public data class CheckInstallation(
        public val installations: List<Installation>
    ) : StartupEffect

    public data object ListWorkspaces : StartupEffect
    public data class CreateWorkspace(
        public val name: String,
        public val installationId: InstallationId
    ) : StartupEffect
}