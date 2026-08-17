package com.sappyoak.dsanalyzer.app.state.workspace

import kotlinx.serialization.Serializable

import com.sappyoak.dsanalyzer.app.state.SetupState
import com.sappyoak.dsanalyzer.game.identity.GameIdentity

/**
 * One workspace: an installation, its findings, and what is being worked on
 */
@Serializable
data class WorkspaceState(
    val id: String,
    /**
     * The installation this workspace is about. This is fixed for the lifetime of the
     * workspace
     */
    val identity: GameIdentity,
    val installationKey: String,
    val title: String,
    val setup: SetupState = SetupState(),
    val cacheSizeBytes: Long = 0,
)