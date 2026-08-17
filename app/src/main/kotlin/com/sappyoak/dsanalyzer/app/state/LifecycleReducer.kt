package com.sappyoak.dsanalyzer.app.state

import com.sappyoak.dsanalyzer.app.state.workspace.WorkspaceAction
import com.sappyoak.dsanalyzer.app.state.workspace.WorkspaceState

fun Store.reduceLifecycle(state: AppState, action: WorkspaceAction): AppState = when (action) {
    is WorkspaceAction.Open -> {
        val id = "${action.installationKey}:${System.currentTimeMillis()}"
        val workspace = WorkspaceState(
            id = id,
            identity = action.identity,
            installationKey = action.installationKey,
            title = action.title
        )
        state.copy(
            workspaces = state.workspaces + (id to workspace),
            activeWorkspace = state.activeWorkspace ?: id
        )
    }

    is WorkspaceAction.Close -> {
        val remaining = state.workspaces - action.workspaceId
        state.copy(
            workspaces = remaining,
            activeWorkspace = if (state.activeWorkspace == action.workspaceId) {
                remaining.keys.firstOrNull()
            } else {
                state.activeWorkspace
            }
        )
    }

    is WorkspaceAction.Activate -> state.copy(activeWorkspace = action.workspaceId)

    is WorkspaceAction.Restored -> state.copy(
        workspaces = action.workspaces.associateBy { it.id },
        activeWorkspace = action.active ?: action.workspaces.firstOrNull()?.id
    )

    is WorkspaceAction.Rename -> state.copy(
        workspaces = state.workspaces.mapValues { (id, workspace) ->
            if (id == action.workspaceId) workspace.copy(title = action.title) else workspace
        }
    )
}