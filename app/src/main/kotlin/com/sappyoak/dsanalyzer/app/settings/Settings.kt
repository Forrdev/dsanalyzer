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