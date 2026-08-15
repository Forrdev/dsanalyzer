package com.sappyoak.dsanalyzer.app.effects

import kotlinx.coroutines.CoroutineScope

import com.sappyoak.dsanalyzer.shared.freeSpaceAt
import com.sappyoak.dsanalyzer.app.AppServices
import com.sappyoak.dsanalyzer.app.state.*
import com.sappyoak.dsanalyzer.app.ui.components.FilePickers
import com.sappyoak.dsanalyzer.domain.Problem
import com.sappyoak.dsanalyzer.domain.ProblemResolution
import kotlin.io.path.isWritable

class SetupEffects(private val services: AppServices) {
    private val environment = services.environment

    fun handle(
        action: Action.Setup,
        state: AppState,
        scope: CoroutineScope,
        dispatch: DispatchFn
    ) {
        when (action) {
            Action.Setup.GamePathRequested -> {
                FilePickers.chooseDirectory("Select the game's Directory. For PTDE this will be the DATA dir inside of the main folder")?.let { path ->
                    dispatch(Action.Setup.GamePathChosen(path))
                }
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

            Action.Setup.ExtractPathRequested -> {
                FilePickers.chooseDirectory(
                    "Where to extract game files to if running PTDE",
                    startAt = state.setup.extractedPath
                )?.let { path ->
                    dispatch(Action.Setup.ExtractPathChosen(path, path.freeSpaceAt()))
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
            }

            is Action.Setup.InstallationRemoved -> {
                environment.forgetInstallation(action.key)
            }

            else -> Unit
        }
    }
}