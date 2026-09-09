package com.sappyoak.dsanalyzer.app.startup

import java.nio.file.Path

import com.sappyoak.dsanalyzer.app.workspace.Workspace
import com.sappyoak.dsanalyzer.app.workspace.WorkspaceId
import com.sappyoak.dsanalyzer.game.Installation
import com.sappyoak.dsanalyzer.game.RejectionReason


public data class StartupState(
    public val phase: StartupPhase = StartupPhase.Loading,
    public val installations: List<Installation> = emptyList(),
    public val workspaces: List<Workspace> = emptyList(),
    public val lastActiveWorkspaceId: WorkspaceId? = null,
    public val notices: List<StartupNotice> = emptyList()
)

public sealed interface StartupPhase {
    /** Reading settings and re-checking known installations */
    public data object Loading : StartupPhase

    /** No usable installation is known, the user must pick a folder */
    public data object NeedsInstallation : StartupPhase

    /** At least one installation is usable, but no workspace is open*/
    public data object NeedsWorkspace : StartupPhase


    public data class Validating(public val folder: Path) : StartupPhase

    public data class Rejected(
        public val folder: Path,
        public val reason: RejectionReason
    ) : StartupPhase

    /** A workspace is open and the rest of the application is usable */
    public data class Ready(public val workspace: Workspace) : StartupPhase

    /** Startup could not complete, for reasons unrelated to any one folder */
    public data class Failed(public val message: String, public val err: Throwable? = null) : StartupPhase
}