package com.sappyoak.dsanalyzer.app.data.params

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

import com.sappyoak.dsanalyzer.app.store.EffectRunner
import com.sappyoak.dsanalyzer.game.Installation
import com.sappyoak.dsanalyzer.game.data.InstallationData
import com.sappyoak.dsanalyzer.game.files.InstallationFiles
import com.sappyoak.dsanalyzer.game.world.params.ParamNames
import com.sappyoak.dsanalyzer.game.world.params.loadGameParams
import com.sappyoak.dsanalyzer.game.world.params.loadParamNames
import com.sappyoak.dsanalyzer.game.world.text.TextLanguage
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

/** Until there is a language setting; deferred */
private val NAME_LANGUAGE = TextLanguage.English

public class ParamsEffects(
    private val data: InstallationData
) : EffectRunner<ParamsEffect, ParamsMessage> {
    override fun keyOf(effect: ParamsEffect): Any? = effect::class

    override fun execute(effect: ParamsEffect): Flow<ParamsMessage> = flow { emit(perform(effect)) }

    override fun onFailure(effect: ParamsEffect, failure: Throwable): ParamsMessage =
        ParamsMessage.Failed(failure.message ?: failure.toString())

    private suspend fun perform(effect: ParamsEffect): ParamsMessage = when (effect) {
        is ParamsEffect.Load -> data.of(effect.installation).let { game ->
            ParamsMessage.Loaded(
                params = checkNotNull(game.params()) { "This installation has no GameParam.parambnd" },
                names = game.paramNames(NAME_LANGUAGE)
            )
        }

        is ParamsEffect.Search -> withContext(Dispatchers.Default) {
            ParamsMessage.SearchCompleted(searchParams(effect.entries, effect.names, effect.query))
        }
    }
}

public sealed interface ParamsEffect {
    public data class Load(public val installation: Installation) : ParamsEffect

    public data class Search(
        public val entries: Collection<ParamEntry>,
        public val names: ParamNames,
        public val query: ParamQuery
    ) : ParamsEffect
}
