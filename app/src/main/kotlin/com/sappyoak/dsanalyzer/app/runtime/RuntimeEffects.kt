package com.sappyoak.dsanalyzer.app.runtime

import com.sappyoak.dsanalyzer.app.runtime.timing.TimingLoader
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.map

import com.sappyoak.dsanalyzer.app.store.EffectRunner
import com.sappyoak.dsanalyzer.game.Installation
import com.sappyoak.dsanalyzer.game.world.maps.MapId
import com.sappyoak.dsanalyzer.runtime.session.EngineDrawRequest
import com.sappyoak.dsanalyzer.runtime.session.FocusRequest
import com.sappyoak.dsanalyzer.runtime.session.animation.AnimationTarget

public class RuntimeEffects(
    private val link: GameLink,
    private val aiTracking: Flow<AITracking>,
    private val focus: Flow<FocusRequest>,
    private val timingsWanted: Flow<Boolean>,
    private val engineDraw: Flow<EngineDrawRequest>,
    private val timings: TimingLoader
) : EffectRunner<RuntimeEffect, RuntimeMessage> {
    override fun keyOf(effect: RuntimeEffect): Any? = effect
    override fun execute(effect: RuntimeEffect): Flow<RuntimeMessage> = when (effect) {
        RuntimeEffect.Observe -> link.state.map(RuntimeMessage::LinkChanged)
        RuntimeEffect.RequestPlacedEnemies -> flow { link.requestPlacedEnemies() }
        RuntimeEffect.RequestCharacterEffects -> flow { link.requestCharacterEffects() }
        RuntimeEffect.FollowAITracking -> flow { aiTracking.collect { link.setAIScope(it.scope) } }
        RuntimeEffect.FollowFocus -> flow { focus.collect(link::setFocus) }
        is RuntimeEffect.Animate -> flow { link.setAnimationTarget(effect.target) }
        RuntimeEffect.FollowTimingsWanted -> timingsWanted.map(RuntimeMessage::TimingsWanted)

        is RuntimeEffect.InstallEngineDraw -> flow { link.installEngineDraw(effect.install) }
        RuntimeEffect.FollowEngineDraw -> flow { engineDraw.collect(link::setEngineDraw) }

        is RuntimeEffect.WatchFlag -> flow { link.watchFlag(effect.flagId) }

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
    public data object RequestCharacterEffects : RuntimeEffect
    public data object FollowAITracking : RuntimeEffect
    public data object FollowFocus : RuntimeEffect
    public data class Animate(public val target: AnimationTarget) : RuntimeEffect
    public data object FollowTimingsWanted : RuntimeEffect

    public data class InstallEngineDraw(public val install: Boolean) : RuntimeEffect
    public data object FollowEngineDraw : RuntimeEffect

    public data class WatchFlag(public val flagId: Int?) : RuntimeEffect

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
