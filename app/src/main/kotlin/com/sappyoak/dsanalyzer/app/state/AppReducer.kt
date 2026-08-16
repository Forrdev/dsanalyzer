package com.sappyoak.dsanalyzer.app.state

import com.sappyoak.dsanalyzer.app.config.HotKeySettings

/**
 * The only place where state changes.
 *
 * Pure by constructions, every branch returns a new state and does nothing else.
 * Effects are absent on purpose. An action that needs work done dispatches, gets a state
 * change here, and the work happens in any effect classes alongside, never inside the function
 */
fun Store.reduce(state: AppState, action: Action): AppState = when (action) {
    Action.Setup.GamePathRequested -> state
    is Action.Setup.GamePathChosen -> state.copy(
        setup = state.setup.copy(
            gamePath = action.path,
            installKind = action.inspection.kind,
            inspection = action.inspection,
            gamePathResolves = true,
            version = action.inspection.version,
            identityUnknown = action.inspection.identity?.buildId?.isUnknown ?: true,
            suggestedPaths = emptyList()
        )
    )

    Action.Setup.DataPathRequested -> state
    is Action.Setup.DataPathChosen -> state.copy(
        setup = state.setup.copy(
            dataPath = action.path,
            freeSpaceBytes = action.freeSpaceBytes,
            dataPathWritable = action.writable
        )
    )

    Action.Setup.ExtractPathRequested -> state
    is Action.Setup.ExtractPathChosen -> state.copy(
        setup = state.setup.copy(
            extractedPath = action.path,
            freeSpaceBytes = action.freeSpaceBytes
        )
    )

    is Action.Setup.ExtractionProgress -> state.copy(
        setup = state.setup.copy(extraction = ExtractionState.Running(action.current, action.done, action.total))
    )

    is Action.Setup.ExtractionFinished -> state.copy(
        setup = state.setup.copy(extraction = action.result)
    )

    Action.Setup.ExtractionCancelled -> state.copy(
        setup = state.setup.copy(extraction = ExtractionState.NotStarted)
    )

    Action.Setup.InstallationsRequested -> state

    is Action.Setup.InstallationsListed -> state.copy(
        setup = state.setup.copy(installations = action.installations)
    )

    is Action.Setup.InstallationSelected -> state.copy(
        setup = state.setup.copy(
            activeInstallation = action.key,
            // Cleared here so readiness will re-evaluate against the newly selected installation rather
            // than carrying the previous one's answers
            gamePath = null,
            gamePathResolves = false,
            version = null,
            identityUnknown = false
        ),
        health = HealthState(),
        cacheSizeBytes = 0
    )

    is Action.Setup.InstallationRemoved -> state.copy(
        setup = state.setup.copy(
            activeInstallation = state.setup.activeInstallation?.let {
                if (it == action.key) null else it
            } ?: state.setup.activeInstallation,
            installations = state.setup.installations.filterNot { it.key == action.key }
        )
    )

    is Action.Setup.GameVersionOverridden -> state.copy(
        setup = state.setup.copy(version = action.version)
    )

    Action.Setup.CacheSizeRequested -> state
    is Action.Setup.CacheSizeMeasured -> state.copy(cacheSizeBytes = action.bytes)

    is Action.HotKey.Bound -> state.copy(
        hotkeys = state.hotkeys.with(action.binding, action.keyCode),
        capturingHotKey = null
    )

    Action.HotKey.ResetToDefaults -> state.copy(
        hotkeys = HotKeySettings(),
        capturingHotKey = null
    )

    Action.HotKey.CaptureCancelled -> state.copy(capturingHotKey = null)

    is Action.ProblemReported -> state.copy(
        health = state.health.copy(
            problems = state.health.problems + action.problem
        )
    )
}