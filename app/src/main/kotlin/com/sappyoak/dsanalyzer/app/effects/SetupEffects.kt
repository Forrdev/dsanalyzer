package com.sappyoak.dsanalyzer.app.effects

import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import kotlin.io.path.*
import java.nio.file.Path

import com.sappyoak.dsanalyzer.game.assets.fs.ExtractionCacheWarmer
import com.sappyoak.dsanalyzer.game.identity.GameIdentity
import com.sappyoak.dsanalyzer.shared.io.*

import com.sappyoak.dsanalyzer.app.AppServices
import com.sappyoak.dsanalyzer.app.data.Resources
import com.sappyoak.dsanalyzer.app.state.*
import com.sappyoak.dsanalyzer.app.state.workspace.InstallationEntry
import com.sappyoak.dsanalyzer.app.state.workspace.WorkspaceState
import com.sappyoak.dsanalyzer.app.ui.components.FilePickers



class SetupEffects(private val services: AppServices) : AutoCloseable {
    private val environment = services.environment
    private var extractJob: Job? = null

    fun handle(
        action: Action,
        workspace: WorkspaceState,
        scope: CoroutineScope,
        dispatch: (Action) -> Unit
    ) {
        when (action) {
            Action.Setup.DataPathRequested -> {
                val path = FilePickers.chooseDirectory(
                    title = "Where should tool data go?",
                    startAt = workspace.setup.dataPath
                ) ?: return

                dispatch(Action.Setup.DataPathChosen(
                    path = path,
                    freeSpaceBytes = path.freeSpaceOnDisk(),
                    writable = path.isWritable()
                ))
            }


            Action.Setup.GamePathRequested -> {
                val path = FilePickers.chooseDirectory("Where is the game installed") ?: return
                dispatch(Action.Setup.GamePathChosen(path, services.gameInstallInspector.inspect(path)))
            }

            Action.Setup.ExtractPathRequested -> {
                val path = FilePickers.chooseDirectory(
                    title = "Where to extract game files",
                    startAt = workspace.setup.extractedPath
                ) ?: return
                dispatch(Action.Setup.ExtractPathChosen(path, path.freeSpaceOnDisk()))
            }

            Action.Setup.CacheSizeRequested -> scope.launch {
                val size = withContext(Dispatchers.IO) {
                    environment.cacheSizeFor(workspace.identity)
                }
                dispatch(Action.Setup.CacheSizeMeasured(size))
            }

            Action.Setup.CacheClearRequested -> scope.launch {
                withContext(Dispatchers.IO) {
                    environment.extractedPathFor(workspace.identity).deleteTree()
                }
                dispatch(Action.Setup.ExtractionFinished(ExtractionState.NotStarted))
                dispatch(Action.Setup.CacheSizeRequested)
            }

            else -> Unit
        }
    }

    override fun close() {
        extractJob?.cancel()
        extractJob = null
    }

    private fun extract(
        workspace: WorkspaceState,
        scope: CoroutineScope,
        dispatch: (Action) -> Unit
    ) {
        val gamePath = environment.gamePathFor(workspace.identity) ?: return
        val outputPath = environment.extractedPathFor(workspace.identity)

        extractJob?.cancel()
        extractJob = scope.launch {
            val result = withContext(Dispatchers.IO) {
                outputPath.createDirectories()

                val pathDictionaries = Resources.loadPathDictionaries()
                val warmer = ExtractionCacheWarmer(gamePath, outputPath, pathDictionaries)
                warmer.warm(
                    onProgress = { done, total, current ->
                        dispatch(Action.Setup.ExtractionProgress(current, done, total))
                    },
                    shouldContinue = { extractJob?.isCancelled == false }
                )
            }

            val extractionState = if (result.error != null) {
                ExtractionState.Failed(result.error!!)
            } else {
                ExtractionState.Complete(result.written, result.skipped)
            }

            dispatch(Action.Setup.ExtractionFinished(extractionState))
            dispatch(Action.Setup.CacheSizeRequested)
        }
    }

    private fun listInstallations(): List<InstallationEntry> {
        val settings = environment.settings
        return settings.installations.map { (key, install) ->
            val version = install.version
            InstallationEntry(
                key = key,
                path = install.gamePath ?: Path.of(""),
                version = version,
                buildId = install.buildId,
                isActive = key == settings.activeInstallation,
                isAvailable = install.gamePath?.exists() ?: false,
                lastScannedAt = install.lastScannedAt,
                cacheSizeBytes = version?.let {
                    install.buildId?.let { build ->
                        environment.paths.cacheSize(GameIdentity(it, build))
                    }
                } ?: 0L
            )
        }.sortedWith(compareByDescending<InstallationEntry> { it.isActive }.thenByDescending { it.lastScannedAt?.toEpochMilliseconds() })
    }
}