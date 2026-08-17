package com.sappyoak.dsanalyzer.app.state

import com.sappyoak.dsanalyzer.app.state.global.GlobalState
import com.sappyoak.dsanalyzer.app.state.workspace.WorkspaceState


data class AppState(
    val global: GlobalState = GlobalState(),
    val workspaces: Map<String, WorkspaceState> = emptyMap(),
    val activeWorkspace: String? = null
) {
    val active: WorkspaceState? get() = activeWorkspace?.let { workspaces[it] }
    val needsFirstRunSetup: Boolean get() = workspaces.isEmpty()
}