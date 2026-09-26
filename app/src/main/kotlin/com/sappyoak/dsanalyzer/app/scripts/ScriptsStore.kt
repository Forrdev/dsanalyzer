package com.sappyoak.dsanalyzer.app.scripts

import kotlinx.coroutines.CoroutineScope

import com.sappyoak.dsanalyzer.app.store.Store

public class ScriptsStore(
    scope: CoroutineScope,
    effects: ScriptsEffects
): Store<ScriptsState, ScriptsMessage, ScriptsEffect>(
    scope = scope,
    name = "Scripts",
    initial = ScriptsState(),
    reduce = ::reduceScripts,
    effects = effects
)