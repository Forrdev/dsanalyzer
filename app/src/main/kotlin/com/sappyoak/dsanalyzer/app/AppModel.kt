package com.sappyoak.dsanalyzer.app

import kotlinx.coroutines.CoroutineScope


import com.sappyoak.dsanalyzer.app.paths.ToolPaths
import com.sappyoak.dsanalyzer.app.serialization.jsonSerializer
import com.sappyoak.dsanalyzer.app.settings.SettingsFile
import com.sappyoak.dsanalyzer.app.startup.StartupEffects
import com.sappyoak.dsanalyzer.app.startup.StartupStore
import com.sappyoak.dsanalyzer.app.verification.VerificationCache
import com.sappyoak.dsanalyzer.app.verification.VerificationEffects
import com.sappyoak.dsanalyzer.app.verification.VerificationStore
import com.sappyoak.dsanalyzer.app.workspace.WorkspaceDirectory

public class AppModel(
    public val startup: StartupStore,
    public val verification: VerificationStore
)

public fun createAppModel(
    scope: CoroutineScope,
    paths: ToolPaths
): AppModel {
    val settings = SettingsFile(paths.settings, jsonSerializer)
    val workspaces = WorkspaceDirectory(paths.workspaces, jsonSerializer)

    return AppModel(
        startup = StartupStore(
            scope,
            StartupEffects(settings, workspaces)
        ),
        verification = VerificationStore(
            scope,
            VerificationEffects(
                cache = VerificationCache(paths.verificationFile, jsonSerializer)
            )
        )
    )
}