package com.sappyoak.dsanalyzer.app.workspace

import kotlinx.serialization.Serializable
import kotlin.uuid.Uuid

import com.sappyoak.dsanalyzer.game.InstallationId

@JvmInline
@Serializable
public value class WorkspaceId(public val value: String) {
    override fun toString() = value

    public companion object {
        public fun random(): WorkspaceId = WorkspaceId(Uuid.random().toString())
    }
}

/**
 * A users working context over one installation. Persisted at '<root>/workspaces/<id>/workspace.json.
 */
@Serializable
public data class Workspace(
    public val id: WorkspaceId,
    public val name: String,
    public val installationId: InstallationId
)