package com.sappyoak.dsanalyzer.app.runtime

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.map

import com.sappyoak.dsanalyzer.app.store.EffectRunner

public class RuntimeEffects(private val link: GameLink) : EffectRunner<RuntimeEffect, RuntimeMessage> {
    override fun keyOf(effect: RuntimeEffect): Any? = effect
    override fun execute(effect: RuntimeEffect): Flow<RuntimeMessage> = when (effect) {
        RuntimeEffect.Observe -> link.state.map(RuntimeMessage::LinkChanged)
        RuntimeEffect.RequestPlacedEnemies -> flow { link.requestPlacedEnemies() }
    }

    override fun onFailure(effect: RuntimeEffect, failure: Throwable): RuntimeMessage? = null
}


public sealed interface RuntimeEffect {
    public data object Observe : RuntimeEffect
    public data object RequestPlacedEnemies : RuntimeEffect
}