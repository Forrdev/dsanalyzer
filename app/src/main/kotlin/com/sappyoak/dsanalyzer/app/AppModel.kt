package com.sappyoak.dsanalyzer.app

import kotlinx.coroutines.CoroutineScope


import com.sappyoak.dsanalyzer.app.paths.ToolPaths
import com.sappyoak.dsanalyzer.app.serialization.jsonSerializer
import com.sappyoak.dsanalyzer.app.settings.SettingsFile
import com.sappyoak.dsanalyzer.app.startup.StartupEffects
import com.sappyoak.dsanalyzer.app.startup.StartupStore
import com.sappyoak.dsanalyzer.app.workspace.WorkspaceDirectory

public class AppModel(
    public val startup: StartupStore
)

public fun createAppModel(
    scope: CoroutineScope,
    paths: ToolPaths
): AppModel {
    val settings = SettingsFile(paths, jsonSerializer)
    val workspaces = WorkspaceDirectory(paths, jsonSerializer)

    return AppModel(
        startup = StartupStore(
            scope,
            StartupEffects(settings, workspaces)
        )
    )
}