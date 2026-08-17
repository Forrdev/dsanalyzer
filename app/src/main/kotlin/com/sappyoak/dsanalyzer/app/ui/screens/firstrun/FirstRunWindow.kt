package com.sappyoak.dsanalyzer.app.ui.screens.firstrun

import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.ui.Modifier

import com.sappyoak.dsanalyzer.app.AppServices
import com.sappyoak.dsanalyzer.app.state.Action
import com.sappyoak.dsanalyzer.app.state.Store
import com.sappyoak.dsanalyzer.app.state.global.FirstRunSequence
import com.sappyoak.dsanalyzer.app.state.global.FirstRunStep
import com.sappyoak.dsanalyzer.app.state.global.GlobalState
import com.sappyoak.dsanalyzer.app.state.workspace.WorkspaceAction
import com.sappyoak.dsanalyzer.app.ui.BaseTypography
import com.sappyoak.dsanalyzer.app.ui.Theme
import com.sappyoak.dsanalyzer.app.ui.components.FilePickers
import kotlinx.coroutines.launch

@Composable
fun FirstRunWindow(
    sequence: FirstRunSequence,
    global: GlobalState,
    store: Store,
    services: AppServices
) {
    val scope = rememberCoroutineScope()

    MaterialTheme(typography = BaseTypography) {
        Surface(Modifier.fillMaxSize(), color = Theme.colors.background) {
            FirstRunScreen(
                sequence = sequence,
                globalState = global,
                onChooseDataPath = {
                    val path = FilePickers.chooseDirectory("Where should tool data go?")
                        ?: return@FirstRunScreen
                    services.environment.update { it.copy(dataPath = path) }
                    store.dispatchLifecycle(WorkspaceAction.FirstRunStepCompleted(
                        step = FirstRunStep.DataPath,
                        sequence = sequence.copy(dataPath = path)
                    ))
                },
                onChooseInstallation = {
                    val path = FilePickers.chooseDirectory("Where is the game installed?")
                        ?: return@FirstRunScreen
                    val inspection = services.gameInstallInspector.inspect(path)
                    val identity = inspection.identity ?: return@FirstRunScreen

                    services.environment.selectInstallation(identity, path)

                    store.dispatchLifecycle(WorkspaceAction.FirstRunStepCompleted(
                        step = FirstRunStep.Installation,
                        sequence = sequence.copy(
                            installationKey = identity.pathSegment,
                            identity = identity,
                            needsExtraction = inspection.archiveCount > 0 && inspection.isUsable
                        )
                    ))
                },
                onExtract = {
                    scope.launch {
                        store.dispatchGlobal(Action.Setup.ExtractionRequested)
                    }
                    store.dispatchLifecycle(WorkspaceAction.FirstRunStepCompleted(FirstRunStep.Extraction, sequence))
                },
                onSkipExtraction = {
                    store.dispatchLifecycle(
                        WorkspaceAction.FirstRunStepCompleted(FirstRunStep.Extraction, sequence)
                    )
                },
                onCreateWorkspace = { title ->
                    val key = sequence.installationKey ?: return@FirstRunScreen
                    val identity = sequence.identity ?: return@FirstRunScreen

                    store.dispatchLifecycle(
                        WorkspaceAction.Open(key, identity, title)
                    )
                    store.dispatchLifecycle(WorkspaceAction.FirstRunFinished)

                    // Then we need to start the scan
                }
            )
        }
    }
}