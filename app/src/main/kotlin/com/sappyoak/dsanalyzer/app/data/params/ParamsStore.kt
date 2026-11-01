package com.sappyoak.dsanalyzer.app.data.params

import kotlinx.coroutines.CoroutineScope

import com.sappyoak.dsanalyzer.app.store.Store

public class ParamsStore(
    scope: CoroutineScope,
    effects: ParamsEffects
) : Store<ParamsState, ParamsMessage, ParamsEffect>(
    scope = scope,
    name = "params",
    initial = ParamsState(),
    reduce = ::reduceParams,
    effects = effects
)
