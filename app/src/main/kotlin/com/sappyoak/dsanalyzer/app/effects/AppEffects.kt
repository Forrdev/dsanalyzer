package com.sappyoak.dsanalyzer.app.effects

import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

import com.sappyoak.dsanalyzer.app.AppServices
import com.sappyoak.dsanalyzer.app.state.*

class AppEffects(
    private val services: AppServices,
    private val setup: SetupEffects
) {
    private val environment get() = services.environment

    fun handle(
        action: Action,
        state: AppState,
        scope: CoroutineScope,
        dispatch: DispatchFn
    ) {
        persist(action)

        if (action is Action.HotKey) {
            environment.update { it.copy(hotkeys = state.hotkeys) }
        }

        // Switching has to tear down before the reducer's cleared state is acted on, so it
        // is handled here rather than inside SetupEffects
        if (action is Action.Setup.InstallationSelected) {
            switchInstallation(action.key, dispatch)
            return
        }

        when (action) {
            is Action.Setup -> setup.handle(action, state, scope, dispatch)
            else -> Unit
        }
    }

    /**
     * Mirrors state changes into settings, which will be updates *before* any of the
     * other effects run
     */
    private fun persist(action: Action) {
        when (action) {
            is Action.Setup.DataPathChosen -> environment.update {
                it.copy(dataPath = action.path)
            }

            else -> Unit
        }
    }

    /**
     * Switches installations without a restart
     */
    private fun switchInstallation(
        key: String,
        dispatch: DispatchFn
    ) {
        environment.activeInstallation(key)
        dispatch(Action.Setup.CacheSizeRequested)
        dispatch(Action.Setup.InstallationsRequested)
    }
}