package com.sappyoak.dsanalyzer.app.startup

import com.sappyoak.dsanalyzer.app.store.Store
import kotlinx.coroutines.CoroutineScope

public class StartupStore(
    scope: CoroutineScope,
    effects: StartupEffects
) : Store<StartupState, StartupMessage, StartupEffect>(
    scope = scope,
    name = "startup",
    initial = StartupState(),
    reduce = ::reduceStartup,
    effects = effects
)