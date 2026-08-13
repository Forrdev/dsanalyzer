package com.sappyoak.dsanalyzer.app.state

import com.sappyoak.dsanalyzer.app.config.AppEnvironment
import kotlinx.coroutines.CoroutineScope

class AppEffects(
    private val environment: AppEnvironment
) {
    fun handle(
        action: Action,
        state: AppState,
        scope: CoroutineScope,
        dispatchFn: DispatchFn
    ) {
        if (action is Action.HotKey) {
            environment.update { it.copy(hotkeys = state.hotkeys) }
        }
    }
}