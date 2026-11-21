package com.sappyoak.dsanalyzer.app.data.ai

import kotlinx.coroutines.CoroutineScope

import com.sappyoak.dsanalyzer.app.store.Store

public class AiStore(
    scope: CoroutineScope,
    effects: AiEffects
) : Store<AiState, AiMessage, AiEffect>(
    scope = scope,
    name = "ai",
    initial = AiState(),
    reduce = ::reduceAi,
    effects = effects
)
