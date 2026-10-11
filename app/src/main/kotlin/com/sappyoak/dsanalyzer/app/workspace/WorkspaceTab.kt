package com.sappyoak.dsanalyzer.app.workspace

public enum class WorkspaceTab(public val label: String, public val needsInstallation: Boolean = true) {
    Maps("Maps"),
    Scripts("Scripts"),
    Runtime("Runtime"),
    Settings("Settings", needsInstallation = false);
}