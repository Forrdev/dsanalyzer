package com.sappyoak.dsanalyzer.app


import kotlinx.coroutines.CoroutineScope

import com.sappyoak.dsanalyzer.app.connection.ConnectionEffects
import com.sappyoak.dsanalyzer.app.connection.ConnectionMessage
import com.sappyoak.dsanalyzer.app.connection.ConnectionStore
import com.sappyoak.dsanalyzer.app.maps.MapsEffects
import com.sappyoak.dsanalyzer.app.maps.MapsStore
import com.sappyoak.dsanalyzer.app.paths.ToolPaths
import com.sappyoak.dsanalyzer.app.runtime.GameLink
import com.sappyoak.dsanalyzer.app.runtime.RuntimeEffects
import com.sappyoak.dsanalyzer.app.runtime.RuntimeMessage
import com.sappyoak.dsanalyzer.app.runtime.RuntimeStore
import com.sappyoak.dsanalyzer.app.scripts.ScriptsEffects
import com.sappyoak.dsanalyzer.app.scripts.ScriptsStore
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
import com.sappyoak.dsanalyzer.game.files.InstallationFiles
import com.sappyoak.dsanalyzer.native.process.Processes

public class AppModel(
    public val settings: SettingsStore,
    public val maps: MapsStore,
    public val scripts: ScriptsStore,
    public val runtime: RuntimeStore,
    public val startup: StartupStore,
    public val verification: VerificationStore,
    public val connection: ConnectionStore,
    private val installationFiles: InstallationFiles
) : AutoCloseable {
    public fun start() {
        settings.dispatch(SettingsMessage.Load)
        startup.dispatch(StartupMessage.Start)
        verification.dispatch(VerificationMessage.Start)
        connection.dispatch(ConnectionMessage.Start)
        runtime.dispatch(RuntimeMessage.Opened)
    }

    override fun close() {
        installationFiles.close()
    }
}

public fun createAppModel(
    scope: CoroutineScope,
    paths: ToolPaths
): AppModel {
    val settings = SettingsStore(scope, SettingsEffects(SettingsFile(paths.settings, jsonSerializer)))
    val workspaces = WorkspaceDirectory(paths.workspaces, jsonSerializer)
    val installationFiles = InstallationFiles(scope)
    val link = GameLink(scope, processes = { Processes.Current })

    return AppModel(
        settings = settings,
        maps = MapsStore(scope, MapsEffects(installationFiles)),
        scripts = ScriptsStore(scope, ScriptsEffects(installationFiles)),
        runtime = RuntimeStore(scope, RuntimeEffects(link)),
        startup = StartupStore(
            scope,
            StartupEffects(settings, workspaces)
        ),
        verification = VerificationStore(
            scope,
            VerificationEffects(
                cache = VerificationCache(paths.verificationFile, jsonSerializer),
                files = installationFiles
            )
        ),
        connection = ConnectionStore(
            scope,
            ConnectionEffects(settings, link)
        ),
        installationFiles = installationFiles
    )
}