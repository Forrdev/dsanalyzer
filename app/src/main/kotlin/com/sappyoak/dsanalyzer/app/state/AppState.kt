package com.sappyoak.dsanalyzer.app.state

import com.sappyoak.dsanalyzer.app.config.AppEnvironment
import com.sappyoak.dsanalyzer.app.config.HotKeyBinding
import com.sappyoak.dsanalyzer.app.config.HotKeySettings

import com.sappyoak.dsanalyzer.app.state.readiness.*

data class AppState(
    val setup: SetupState = SetupState(),
    val hotkeys: HotKeySettings = HotKeySettings(),
    /** Set while waiting for the user to press a key */
    val capturingHotKey: HotKeyBinding? = null,
    val health: HealthState = HealthState(),
    val cacheSizeBytes: Long = 0L
) {
    val readiness: Readiness get() = ReadinessChecker.evaluate(this)
    val needsFirstRunSetup: Boolean get() = !readiness.isReady

    companion object {
        fun from(environment: AppEnvironment): AppState {
            val settings = environment.settings
            return AppState(
                hotkeys = settings.hotkeys,
                health = HealthState()
            )
        }
    }
}
