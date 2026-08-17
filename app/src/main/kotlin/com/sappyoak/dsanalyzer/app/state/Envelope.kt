package com.sappyoak.dsanalyzer.app.state

import kotlinx.serialization.Serializable

import com.sappyoak.dsanalyzer.app.state.workspace.WorkspaceAction

/**
 * An action delivery mechanism to separate our actions for different targets. This allows
 * us to use one reducer for every workspace rather than build one for each.
 */
@Serializable
sealed interface Envelope {
    /** Applies to the whole application: hotkeys, installations, etc */
    @Serializable
    data class Global(val action: Action) : Envelope

    /**
     * Applies to one workspace.
     *
     * The id is attached by the scoped dispatcher, so no screen ever constructs one of these
     */
    @Serializable
    data class Scoped(val workspaceId: String, val action: Action) : Envelope

    /** Creates, closes and switches workspaces. Global by nature, since it changes the set */
    @Serializable
    data class Lifecycle(val action: WorkspaceAction) : Envelope
}