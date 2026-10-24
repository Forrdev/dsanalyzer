package com.sappyoak.dsanalyzer.app.runtime

import com.sappyoak.dsanalyzer.app.runtime.timing.TimingLoader
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.map

import com.sappyoak.dsanalyzer.app.store.EffectRunner
import com.sappyoak.dsanalyzer.game.Installation
import com.sappyoak.dsanalyzer.game.world.maps.MapId

public class RuntimeEffects(
    private val link: GameLink,
    private val aiTracking: Flow<AITracking>,
    private val timingsWanted: Flow<Boolean>,
    private val timings: TimingLoader
) : EffectRunner<RuntimeEffect, RuntimeMessage> {
    override fun keyOf(effect: RuntimeEffect): Any? = effect
    override fun execute(effect: RuntimeEffect): Flow<RuntimeMessage> = when (effect) {
        RuntimeEffect.Observe -> link.state.map(RuntimeMessage::LinkChanged)
        RuntimeEffect.RequestPlacedEnemies -> flow { link.requestPlacedEnemies() }
        RuntimeEffect.RequestCollision -> flow { link.requestCollision() }
        RuntimeEffect.FollowAITracking -> flow { aiTracking.collect { link.setAIScope(it.scope) } }
        RuntimeEffect.FollowTimingsWanted -> timingsWanted.map(RuntimeMessage::TimingsWanted)
        is RuntimeEffect.LoadMapTimings -> flow {
            emit(RuntimeMessage.TimingsLoaded(timings.forMap(effect.installation, effect.map, effect.known), effect.map))
        }
        is RuntimeEffect.LoadModelTimings -> flow {
            emit(RuntimeMessage.TimingsLoaded(timings.forModels(effect.installation, effect.models), map = null))
        }
    }

    override fun onFailure(effect: RuntimeEffect, failure: Throwable): RuntimeMessage? =
        if (effect is RuntimeEffect.LoadMapTimings) RuntimeMessage.TimingsLoaded(emptyMap(), effect.map) else null
}


public sealed interface RuntimeEffect {
    public data object Observe : RuntimeEffect
    public data object RequestPlacedEnemies : RuntimeEffect
    public data object RequestCollision : RuntimeEffect
    public data object FollowAITracking : RuntimeEffect
    public data object FollowTimingsWanted : RuntimeEffect

    public data class LoadMapTimings(
        public val installation: Installation,
        public val map: MapId,
        public val known: Set<Int>
    ) : RuntimeEffect

    public data class LoadModelTimings(
        public val installation: Installation,
        public val models: Set<Int>
    ) : RuntimeEffect
}
