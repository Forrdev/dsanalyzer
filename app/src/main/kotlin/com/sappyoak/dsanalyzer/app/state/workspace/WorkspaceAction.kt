package com.sappyoak.dsanalyzer.app.state.workspace

import kotlinx.serialization.Serializable

import com.sappyoak.dsanalyzer.app.state.Action
import com.sappyoak.dsanalyzer.game.identity.GameIdentity

/**
 * These are separate from [Action] because these change the set of workspaces rather than the contents
 * of one. A scoped action cannot create the thing it is scoped to
 */
@Serializable
sealed interface WorkspaceAction {
    /**
     * Opens a workspace for an installation.
     *
     * Two on the same installation is allowed. Comparing analyzer configuration, or keeping
     * a broad survey besides a focused working set, are both reasonable and forbidding it would
     * be enforcing a workflow rather than a constraint
     */
    @Serializable
    data class Open(
        val installationKey: String,
        val identity: GameIdentity,
        val title: String
    ) : WorkspaceAction

    @Serializable
    data class Close(val workspaceId: String) : WorkspaceAction

    @Serializable
    data class Activate(val workspaceId: String) : WorkspaceAction

    @Serializable
    data class Rename(val workspaceId: String, val title: String) : WorkspaceAction

    /** Restores workspaces from settings on launch */
    @Serializable
    data class Restored(
        val workspaces: List<WorkspaceState>,
        val active: String?
    ) : WorkspaceAction
}