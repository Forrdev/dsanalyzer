package com.sappyoak.dsanalyzer.app.startup

import java.nio.file.Path

/** An operational failure that occurred, but did not stop startup */
public sealed interface StartupNotice {
    public data class InstallationsUncheckable(public val detail: String) : StartupNotice
    public data class FolderNotInspected(
        public val folder: Path,
        public val detail: String
    ) : StartupNotice

    public data class WorkspacesUnreadable(public val detail: String) : StartupNotice
    public data class WorkspaceNotCreated(
        public val name: String,
        public val detail: String
    ) : StartupNotice
}

internal fun StartupNotice.describe(): String = when (this) {
    is StartupNotice.WorkspacesUnreadable -> "Existing workspaces could not be listed: $detail"
    is StartupNotice.WorkspaceNotCreated -> "The workspace $name could not be created: $detail"
    is StartupNotice.InstallationsUncheckable -> "Saved installation could not be checked: $detail"
    is StartupNotice.FolderNotInspected -> "$folder could not be checked: $detail"
}