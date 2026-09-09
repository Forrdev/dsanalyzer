package com.sappyoak.dsanalyzer.app.startup

import java.nio.file.Path

/** An operational failure that occurred, but did not stop startup */
public sealed interface StartupNotice {
    public data class SettingsRecovered(public val backup: Path) : StartupNotice
    public data class SettingsUnreadable(public val detail: String) : StartupNotice
    public data class SettingsNotSaved(public val detail: String) : StartupNotice

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