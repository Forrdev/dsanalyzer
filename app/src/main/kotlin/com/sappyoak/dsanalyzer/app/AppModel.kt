package com.sappyoak.dsanalyzer.app

import com.sappyoak.dsanalyzer.app.analysis.AnalysisEffects
import com.sappyoak.dsanalyzer.app.analysis.AnalysisStore
import io.github.oshai.kotlinlogging.KotlinLogging
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.runBlocking

import com.sappyoak.dsanalyzer.app.connection.ConnectionEffects
import com.sappyoak.dsanalyzer.app.connection.ConnectionMessage
import com.sappyoak.dsanalyzer.app.connection.ConnectionStore
import com.sappyoak.dsanalyzer.app.data.IndexQueue
import com.sappyoak.dsanalyzer.app.data.esd.EsdsEffects
import com.sappyoak.dsanalyzer.app.data.esd.EsdsStore
import com.sappyoak.dsanalyzer.app.data.maps.MapsEffects
import com.sappyoak.dsanalyzer.app.data.maps.MapsStore
import com.sappyoak.dsanalyzer.app.data.params.ParamsEffects
import com.sappyoak.dsanalyzer.app.data.params.ParamsStore
import com.sappyoak.dsanalyzer.app.paths.ToolPaths
import com.sappyoak.dsanalyzer.app.runtime.GameLink
import com.sappyoak.dsanalyzer.app.runtime.RuntimeEffects
import com.sappyoak.dsanalyzer.app.runtime.RuntimeStore
import com.sappyoak.dsanalyzer.app.runtime.drawsHitWindows
import com.sappyoak.dsanalyzer.app.runtime.timing.TimingLoader
import com.sappyoak.dsanalyzer.app.data.scripts.ScriptsEffects
import com.sappyoak.dsanalyzer.app.data.scripts.ScriptsStore
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
import com.sappyoak.dsanalyzer.app.workspace.WorkspaceStores
import com.sappyoak.dsanalyzer.game.data.InstallationData
import com.sappyoak.dsanalyzer.game.files.InstallationFiles
import com.sappyoak.dsanalyzer.native.process.Processes
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.flow.map

private val logger = KotlinLogging.logger {  }

public class AppModel(
    public val settings: SettingsStore,
    public val workspace: WorkspaceStores,
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
    }

    override fun close() {
        runCatching {
            runBlocking { settings.flush() }
        }.onFailure { logger.error(it) { "Settings were not saved on exit" } }
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
    val data = InstallationData(installationFiles, scope)
    val indexes = IndexQueue()
    val link = GameLink(scope, processes = { Processes.Current })

    return AppModel(
        settings = settings,
        workspace = WorkspaceStores(
            analysis = AnalysisStore(scope, AnalysisEffects(data)),
            params = ParamsStore(scope, ParamsEffects(data)),
            esds = EsdsStore(scope, EsdsEffects(data, indexes)),
            maps = MapsStore(scope, MapsEffects(data, indexes)),
            scripts = ScriptsStore(scope, ScriptsEffects(data, indexes)),
            runtime = RuntimeStore(scope, RuntimeEffects(
                link = link,
                aiTracking = settings.state.map { it.settings.runtime.ai.tracking }.distinctUntilChanged(),
                timingsWanted = settings.state.map { it.settings.drawsHitWindows }.distinctUntilChanged(),
                timings = TimingLoader(data)
            ))
        ),
        startup = StartupStore(
            scope,
            StartupEffects(settings, workspaces)
        ),
        verification = VerificationStore(
            scope,
            VerificationEffects(
                cache = VerificationCache(paths.verificationFile, jsonSerializer),
                data = data
            )
        ),
        connection = ConnectionStore(
            scope,
            ConnectionEffects(settings, link)
        ),
        installationFiles = installationFiles
    )
}
