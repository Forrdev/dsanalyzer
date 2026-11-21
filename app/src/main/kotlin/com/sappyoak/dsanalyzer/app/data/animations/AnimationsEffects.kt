package com.sappyoak.dsanalyzer.app.data.animations

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

import com.sappyoak.dsanalyzer.app.data.IndexQueue
import com.sappyoak.dsanalyzer.app.store.EffectRunner
import com.sappyoak.dsanalyzer.game.Installation
import com.sappyoak.dsanalyzer.game.data.InstallationData
import com.sappyoak.dsanalyzer.game.world.animations.AnimationSetId
import com.sappyoak.dsanalyzer.game.world.animations.loadTaeTemplate
import com.sappyoak.dsanalyzer.game.world.maps.loadCharacterModels

public class AnimationsEffects(
    private val data: InstallationData,
    private val queue: IndexQueue
) : EffectRunner<AnimationsEffect, AnimationsMessage> {
    /** One of each at a time, so choosing another set cancels the load it replaces */
    override fun keyOf(effect: AnimationsEffect): Any? = effect::class

    override fun execute(effect: AnimationsEffect): Flow<AnimationsMessage> = flow { emit(perform(effect)) }

    override fun onFailure(effect: AnimationsEffect, failure: Throwable): AnimationsMessage =
        AnimationsMessage.Failed(failure.message ?: failure.toString())

    private suspend fun perform(effect: AnimationsEffect): AnimationsMessage = when (effect) {
        is AnimationsEffect.LoadCatalog -> AnimationsMessage.CatalogLoaded(
            sets = queue.run("animation sets") { data.of(effect.installation).animationSets() },
            models = loadCharacterModels()
        )

        is AnimationsEffect.Load -> AnimationsMessage.Loaded(
            SetAnimations(
                set = effect.set,
                taes = data.of(effect.installation).animations(effect.set),
                template = loadTaeTemplate(effect.set.bank),
                tickHz = effect.installation.build.edition.logicTickHz
            )
        )
    }
}

public sealed interface AnimationsEffect {
    public data class LoadCatalog(public val installation: Installation) : AnimationsEffect
    public data class Load(public val installation: Installation, public val set: AnimationSetId) : AnimationsEffect
}
