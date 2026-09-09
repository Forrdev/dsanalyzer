package com.sappyoak.dsanalyzer.app.startup

import java.nio.file.Path

import com.sappyoak.dsanalyzer.app.settings.Settings
import com.sappyoak.dsanalyzer.app.workspace.Workspace
import com.sappyoak.dsanalyzer.app.workspace.WorkspaceId
import com.sappyoak.dsanalyzer.game.InstallationCheck
import com.sappyoak.dsanalyzer.game.InstallationId

public sealed interface StartupMessage {
    /** Begins the bootstrap sequence, dispatched once by whoever builds the store */
    public data object Start : StartupMessage

    public data class SettingsLoaded(
        public val settings: Settings,
        public val recoveredFrom: Path? = null
    ) : StartupMessage

    public data class FolderChosen(public val folder: Path) : StartupMessage

    public data class FolderInspected(
        public val folder: Path,
        public val check: InstallationCheck
    ) : StartupMessage

    public data class WorkspacesListed(public val workspaces: List<Workspace>) : StartupMessage
    public data class WorkspaceChosen(public val id: WorkspaceId) : StartupMessage

    public data class WorkspaceCreationRequest(
        public val name: String,
        public val installationId: InstallationId
    ) : StartupMessage

    public data class WorkspaceCreated(public val workspace: Workspace) : StartupMessage

    public data object RejectionDismissed : StartupMessage
    public data object NoticesDismissed : StartupMessage

    public data class OperationFailed(
        public val operation: FailedStartupOperation,
        public val detail: String
    ) : StartupMessage
}

public sealed interface FailedStartupOperation {
    public data object LoadSettings : FailedStartupOperation
    public data object SaveSettings : FailedStartupOperation
    public data object CheckInstallations : FailedStartupOperation
    public data object ListWorkspaces : FailedStartupOperation

    public data class InspectFolder(public val folder: Path) : FailedStartupOperation
    public data class CreateWorkspace(public val name: String) : FailedStartupOperation
}
