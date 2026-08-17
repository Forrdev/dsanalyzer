package com.sappyoak.dsanalyzer.app.state

import com.sappyoak.dsanalyzer.app.state.workspace.WorkspaceAction

/**
 * A dispatcher bound to one workspace.
 *
 * Handed to a window when it is constructed and passed down to anything it opens. The wrapping
 * is invisible to callers
 */
data class ScopedDispatcher(
    private val workspaceId: String,
    private val dispatch: (Envelope) -> Unit
) {
    val id: String get() = workspaceId

    operator fun invoke(action: Action) {
        dispatch(Envelope.Scoped(workspaceId, action))
    }

    fun global(action: Action) {
        dispatch(Envelope.Global(action))
    }

    fun lifecycle(action: WorkspaceAction) {
        dispatch(Envelope.Lifecycle(action))
    }
}