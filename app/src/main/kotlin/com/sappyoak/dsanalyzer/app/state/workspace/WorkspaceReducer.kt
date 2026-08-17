package com.sappyoak.dsanalyzer.app.state.workspace

import com.sappyoak.dsanalyzer.app.state.Action
import com.sappyoak.dsanalyzer.app.state.ExtractionState
import com.sappyoak.dsanalyzer.app.state.Store

fun Store.reduceWorkspace(state: WorkspaceState, action: Action): WorkspaceState = when (action) {
    is Action.Setup.GamePathChosen -> state.copy(
        setup = state.setup.copy(
            gamePath = action.path,
            installKind = action.inspection.kind,
            inspection = action.inspection,
            gamePathResolves = true,
            version = action.inspection.version,
            identityUnknown = action.inspection.identity?.buildId?.isUnknown ?: true
        )
    )

    is Action.Setup.ExtractionProgress -> state.copy(
        setup = state.setup.copy(
            extraction = ExtractionState.Running(action.current, action.done, action.total)
        )
    )

    is Action.Setup.ExtractionFinished -> state.copy(
        setup = state.setup.copy(extraction = action.result)
    )

    is Action.Setup.CacheSizeMeasured -> state.copy(cacheSizeBytes = action.bytes)

    else -> state
}