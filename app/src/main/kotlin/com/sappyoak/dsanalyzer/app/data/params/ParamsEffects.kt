package com.sappyoak.dsanalyzer.app.data.params

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

import com.sappyoak.dsanalyzer.app.store.EffectRunner
import com.sappyoak.dsanalyzer.game.Installation
import com.sappyoak.dsanalyzer.game.files.InstallationFiles
import com.sappyoak.dsanalyzer.game.world.params.loadGameParams
import com.sappyoak.dsanalyzer.game.world.params.loadParamNames
import com.sappyoak.dsanalyzer.game.world.text.TextLanguage

/** Until there is a language setting; deferred */
private val NAME_LANGUAGE = TextLanguage.English

public class ParamsEffects(
    private val files: InstallationFiles
) : EffectRunner<ParamsEffect, ParamsMessage> {
    override fun keyOf(effect: ParamsEffect): Any? = effect::class

    override fun execute(effect: ParamsEffect): Flow<ParamsMessage> = flow { emit(perform(effect)) }

    override fun onFailure(effect: ParamsEffect, failure: Throwable): ParamsMessage =
        ParamsMessage.Failed(failure.message ?: failure.toString())

    private suspend fun perform(effect: ParamsEffect): ParamsMessage = when (effect) {
        is ParamsEffect.Load -> files.use(effect.installation) { games ->
            ParamsMessage.Loaded(
                params = checkNotNull(games.loadGameParams(effect.installation.build.edition)) {
                    "This installation has no GameParam.parambnd"
                },
                names = games.loadParamNames(NAME_LANGUAGE)
            )
        }
    }
}

public sealed interface ParamsEffect {
    public data class Load(public val installation: Installation) : ParamsEffect
}
