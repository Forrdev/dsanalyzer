package com.sappyoak.dsanalyzer.app.data.animations

import kotlinx.coroutines.CoroutineScope

import com.sappyoak.dsanalyzer.app.store.Store

public class AnimationsStore(
    scope: CoroutineScope,
    effects: AnimationsEffects
) : Store<AnimationsState, AnimationsMessage, AnimationsEffect>(
    scope = scope,
    name = "animations",
    initial = AnimationsState(),
    reduce = ::reduceAnimations,
    effects = effects
)
