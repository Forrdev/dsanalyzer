package com.sappyoak.dsanalyzer.app.data.esd

import kotlinx.coroutines.CoroutineScope

import com.sappyoak.dsanalyzer.app.store.Store

public class EsdsStore(
    scope: CoroutineScope,
    effects: EsdsEffects
) : Store<EsdsState, EsdsMessage, EsdsEffect>(
    scope = scope,
    name = "esd",
    initial = EsdsState(),
    reduce = ::reduceEsds,
    effects = effects
)
