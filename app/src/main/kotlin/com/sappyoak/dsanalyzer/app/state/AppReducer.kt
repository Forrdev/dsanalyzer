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
            problems = state.health.problems + action.message
        )
    )
}