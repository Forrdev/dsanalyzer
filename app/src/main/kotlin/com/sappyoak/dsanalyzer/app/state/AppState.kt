package com.sappyoak.dsanalyzer.app.state

import com.sappyoak.dsanalyzer.app.config.AppEnvironment
import com.sappyoak.dsanalyzer.app.config.HotKeyBinding
import com.sappyoak.dsanalyzer.app.config.HotKeySettings

data class AppState(
    val hotkeys: HotKeySettings = HotKeySettings(),
    /** Set while waiting for the user to press a key */
    val capturingHotKey: HotKeyBinding? = null,
    val health: HealthState = HealthState()
) {
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
