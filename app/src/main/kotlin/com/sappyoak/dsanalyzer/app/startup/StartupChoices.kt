package com.sappyoak.dsanalyzer.app.startup

import com.sappyoak.dsanalyzer.app.settings.Settings
import com.sappyoak.dsanalyzer.app.settings.SettingsEdit
import com.sappyoak.dsanalyzer.app.workspace.WorkspaceId
import com.sappyoak.dsanalyzer.game.Installation

public data class StartupChoices(
    public val installations: List<Installation>,
    public val lastActiveWorkspaceId: WorkspaceId?
) : SettingsEdit {
    override fun applyTo(settings: Settings): Settings =
        settings.copy(installations = installations, lastActiveWorkspaceId = lastActiveWorkspaceId)
}