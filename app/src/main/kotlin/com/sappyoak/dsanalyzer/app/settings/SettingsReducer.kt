package com.sappyoak.dsanalyzer.app.settings

import com.sappyoak.dsanalyzer.app.store.Transition
import com.sappyoak.dsanalyzer.app.store.with

public fun reduceSettings(
    state: SettingsState,
    message: SettingsMessage
) : Transition<SettingsState, SettingsEffect> = when (message) {
    SettingsMessage.Load -> state.with(SettingsEffect.Load)

    is SettingsMessage.Loaded -> state.finishLoading(
        loaded = message.settings,
        problem = message.recoveredFrom?.let(SettingsProblem::Recovered)
    )

    is SettingsMessage.LoadFailed -> state.finishLoading(
        loaded = Settings(),
        problem = SettingsProblem.Unreadable(message.detail)
    )

    is SettingsMessage.Edited -> when {
        !state.loaded -> state.copy(pending = state.pending + message.edit).with()
        else -> state.saving(message.edit.applyTo(state.settings))
    }

    is SettingsMessage.SaveFailed -> state.copy(problem = SettingsProblem.NotSaved(message.detail)).with()

    SettingsMessage.ProblemDismissed -> state.copy(problem = null).with()
}

private fun SettingsState.finishLoading(
    loaded: Settings,
    problem: SettingsProblem?
): Transition<SettingsState, SettingsEffect> {
    val applied = pending.fold(loaded) { settings, edit -> edit.applyTo(settings) }
    val next = copy(settings = loaded, loaded = true, pending = emptyList(), problem = problem)
    return next.saving(applied)
}

private fun SettingsState.saving(next: Settings): Transition<SettingsState, SettingsEffect> =
    if (next == settings) with()
    else copy(settings = next).with(SettingsEffect.Save(next))