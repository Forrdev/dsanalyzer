package com.sappyoak.dsanalyzer.app.runtime

import kotlinx.coroutines.CoroutineScope

import com.sappyoak.dsanalyzer.app.store.Store

public class RuntimeStore(
    scope: CoroutineScope,
    effects: RuntimeEffects
) : Store<RuntimeState, RuntimeMessage, RuntimeEffect>(
    scope = scope,
    name = "runtime",
    initial = RuntimeState(),
    reduce = ::reduceRuntime,
    effects = effects
)
