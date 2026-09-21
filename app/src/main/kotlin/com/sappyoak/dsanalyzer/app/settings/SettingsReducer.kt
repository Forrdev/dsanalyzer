package com.sappyoak.dsanalyzer.app.settings

import com.sappyoak.dsanalyzer.app.store.Transition
import com.sappyoak.dsanalyzer.app.store.with

public fun reduceSettings(
    state: SettingsState,
    message: SettingsMessage
) : Transition<SettingsState, SettingsEffect> = when (message) {
    SettingsMessage.Load -> state.with(SettingsEffect.Load)

    is SettingsMessage.Loaded -> state.copy(
        settings = message.settings,
        loaded = true,
        problem = message.recoveredFrom?.let(SettingsProblem::Recovered)
    ).with()

    is SettingsMessage.LoadFailed -> state.copy(
        settings = Settings(),
        loaded = true,
        problem = SettingsProblem.Unreadable(message.detail)
    ).with()

    is SettingsMessage.SaveFailed -> state.copy(problem = SettingsProblem.NotSaved(message.detail)).with()
}

