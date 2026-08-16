package com.sappyoak.dsanalyzer.app.effects

import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import kotlin.io.path.exists
import kotlin.io.path.isWritable
import kotlin.time.Instant
import java.nio.file.Path

import com.sappyoak.dsanalyzer.game.assets.fs.ExtractionCacheWarmer
import com.sappyoak.dsanalyzer.game.identity.GameIdentity
import com.sappyoak.dsanalyzer.domain.Problem
import com.sappyoak.dsanalyzer.domain.ProblemResolution
import com.sappyoak.dsanalyzer.shared.freeSpaceAt

import com.sappyoak.dsanalyzer.app.AppServices
import com.sappyoak.dsanalyzer.app.data.Resources
import com.sappyoak.dsanalyzer.app.state.*
import com.sappyoak.dsanalyzer.app.ui.components.FilePickers
import com.sappyoak.dsanalyzer.shared.deleteTree


class SetupEffects(private val services: AppServices) : AutoCloseable {
    private val environment = services.environment
    private var warmJob: Job? = null

    fun handle(
        action: Action.Setup,
        state: AppState,
        scope: CoroutineScope,
        dispatch: DispatchFn
    ) {
        when (action) {
            Action.Setup.GamePathRequested -> {
                FilePickers.chooseDirectory("Select the game's Directory. For PTDE this will be the DATA dir inside of the main folder")?.let { path ->
                    dispatch(Action.Setup.GamePathChosen(path, services.gameInstallInspector.inspect(path)))
                }
            }

            is Action.Setup.GamePathChosen -> {
                dispatch(Action.Setup.CacheSizeRequested)
            }

            Action.Setup.DataPathRequested -> {
                FilePickers.chooseDirectory(
                    "Where to store the tools output files",
                    startAt = state.setup.dataPath
                )?.let { path ->
                    dispatch(Action.Setup.DataPathChosen(
                        path = path,
                        freeSpaceBytes = path.freeSpaceAt(),
                        writable = path.isWritable()
                    ))
                }
            }

            is Action.Setup.DataPathChosen -> {
                if (!action.path.isWritable()) {
                    dispatch(Action.ProblemReported(Problem.critical(
                        kind = "UnwritableDataDir",
                        summary = "Cannot write to ${action.path}. The tool needs a writable directory to write files to",
                        consequence = "The Tool cannot function. It will not write any output files",
                        resolutions = listOf(ProblemResolution("Choose another directory", "Action.Setup.DataPathRequested"))
                    )))
                }
                dispatch(Action.Setup.CacheSizeRequested)
            }

            Action.Setup.ExtractPathRequested -> {
                FilePickers.chooseDirectory(
                    "Where to extract game files to if running PTDE",
                    startAt = state.setup.extractedPath
                )?.let { path ->
                    dispatch(Action.Setup.ExtractPathChosen(path, path.freeSpaceAt()))
                }
            }

            Action.Setup.ExtractionRequested -> startWarm(state, scope, dispatch)

            Action.Setup.ExtractionCancelled -> {
                warmJob?.cancel()
                warmJob = null
            }

            is Action.Setup.InstallationRemoved -> {
                environment.forgetInstallation(action.key)
                scope.launch {
                    dispatch(Action.Setup.InstallationsListed(listInstallations()))
                }
            }

            is Action.Setup.InstallationsRequested -> scope.launch {
                dispatch(Action.Setup.InstallationsListed(listInstallations()))
            }

            Action.Setup.CacheSizeRequested -> scope.launch {
                val size = withContext(Dispatchers.IO) {
                    environment.identity?.let { environment.paths.cacheSize(it) } ?: 0L
                }
                dispatch(Action.Setup.CacheSizeMeasured(size))
            }

            Action.Setup.CacheClearRequested -> clearCache(state, scope, dispatch)

            else -> Unit
        }
    }

    override fun close() {
        warmJob?.cancel()
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

    private fun startWarm(state: AppState, scope: CoroutineScope, dispatch: DispatchFn) {
        val gamePath = state.setup.gamePath ?: return
        val outputPath = state.setup.extractedPath ?: return


        warmJob?.cancel()
        warmJob = scope.launch {
            val result = withContext(Dispatchers.IO) {
                runCatching {
                    val warmer = ExtractionCacheWarmer(
                        gamePath = gamePath,
                        extractedPath = outputPath,
                        paths = Resources.loadPathDictionaries()
                    )
                    val warmed = warmer.warm(
                        onProgress = { done, total, current ->
                            dispatch(Action.Setup.ExtractionProgress(current, done, total))
                        },
                        shouldContinue = { warmJob?.isCancelled == false }
                    )

                    if (warmed.error != null) ExtractionState.Failed(warmed.error!!)
                    else ExtractionState.Complete(warmed.written, warmed.skipped)
                }.getOrElse { ExtractionState.Failed(it.message ?: "Extraction failed", it) }
            }

            dispatch(Action.Setup.ExtractionFinished(result))
            dispatch(Action.Setup.CacheSizeRequested)
        }
    }

    private fun clearCache(state: AppState, scope: CoroutineScope, dispatch: DispatchFn) {
        val path = state.setup.extractedPath ?: return

        scope.launch {
            withContext(Dispatchers.IO) {
                path.deleteTree()
            }
            dispatch(Action.Setup.ExtractionFinished(ExtractionState.NotStarted))
            dispatch(Action.Setup.CacheSizeRequested)
        }
    }


}