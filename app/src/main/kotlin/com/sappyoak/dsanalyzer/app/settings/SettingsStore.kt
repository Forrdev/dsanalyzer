package com.sappyoak.dsanalyzer.app.settings

import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.flow.first

import com.sappyoak.dsanalyzer.app.store.Store

public class SettingsStore(
    scope: CoroutineScope,
    effects: SettingsEffects
) : Store<SettingsState, SettingsMessage, SettingsEffect>(
    scope = scope,
    name = "settings",
    initial = SettingsState(),
    reduce = ::reduceSettings,
    effects = effects
)