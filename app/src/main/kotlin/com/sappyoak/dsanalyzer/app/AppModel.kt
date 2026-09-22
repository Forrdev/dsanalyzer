package com.sappyoak.dsanalyzer.app


import kotlinx.coroutines.CoroutineScope

import com.sappyoak.dsanalyzer.app.connection.ConnectionEffects
import com.sappyoak.dsanalyzer.app.connection.ConnectionMessage
import com.sappyoak.dsanalyzer.app.connection.ConnectionStore
import com.sappyoak.dsanalyzer.app.paths.ToolPaths
import com.sappyoak.dsanalyzer.app.serialization.jsonSerializer
import com.sappyoak.dsanalyzer.app.settings.SettingsEffects
import com.sappyoak.dsanalyzer.app.settings.SettingsFile
import com.sappyoak.dsanalyzer.app.settings.SettingsMessage
import com.sappyoak.dsanalyzer.app.settings.SettingsStore
import com.sappyoak.dsanalyzer.app.startup.StartupEffects
import com.sappyoak.dsanalyzer.app.startup.StartupMessage
import com.sappyoak.dsanalyzer.app.startup.StartupStore
import com.sappyoak.dsanalyzer.app.verification.VerificationCache
import com.sappyoak.dsanalyzer.app.verification.VerificationEffects
import com.sappyoak.dsanalyzer.app.verification.VerificationMessage
import com.sappyoak.dsanalyzer.app.verification.VerificationStore
import com.sappyoak.dsanalyzer.app.workspace.WorkspaceDirectory
import com.sappyoak.dsanalyzer.game.files.ArchiveFileIndex
import com.sappyoak.dsanalyzer.native.process.Processes

public class AppModel(
    public val settings: SettingsStore,
    public val startup: StartupStore,
    public val verification: VerificationStore,
    public val connection: ConnectionStore
) {
    public fun start() {
        settings.dispatch(SettingsMessage.Load)
        startup.dispatch(StartupMessage.Start)
        verification.dispatch(VerificationMessage.Start)
        connection.dispatch(ConnectionMessage.Start)
    }
}

public fun createAppModel(
    scope: CoroutineScope,
    paths: ToolPaths
): AppModel {
    val settings = SettingsStore(scope, SettingsEffects(SettingsFile(paths.settings, jsonSerializer)))
    val workspaces = WorkspaceDirectory(paths.workspaces, jsonSerializer)

    return AppModel(
        settings = settings,
        startup = StartupStore(
            scope,
            StartupEffects(settings, workspaces)
        ),
        verification = VerificationStore(
            scope,
            VerificationEffects(
                cache = VerificationCache(paths.verificationFile, jsonSerializer),
                // this is going to need to be dynamic
                index = ArchiveFileIndex()
            )
        ),
        connection = ConnectionStore(
            scope,
            ConnectionEffects(settings, processes = { Processes.Current })
        )
    )
}