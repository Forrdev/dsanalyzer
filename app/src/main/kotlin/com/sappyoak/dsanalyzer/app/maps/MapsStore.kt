package com.sappyoak.dsanalyzer.app.maps

import kotlinx.coroutines.CoroutineScope

import com.sappyoak.dsanalyzer.app.store.Store

public class MapsStore(
    scope: CoroutineScope,
    effects: MapsEffects
) : Store<MapsState, MapsMessage, MapsEffect>(
    scope = scope,
    name = "maps",
    initial = MapsState(),
    reduce = ::reduceMaps,
    effects = effects
)