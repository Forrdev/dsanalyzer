package com.sappyoak.dsanalyzer.app.data.params

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.withContext

import com.sappyoak.dsanalyzer.app.store.EffectRunner
import com.sappyoak.dsanalyzer.game.Installation
import com.sappyoak.dsanalyzer.game.data.InstallationData
import com.sappyoak.dsanalyzer.game.world.params.RowNameTranslations
import com.sappyoak.dsanalyzer.game.world.params.loadRowNameTranslations
import com.sappyoak.dsanalyzer.game.world.text.TextLanguage

/** Until there is a language setting; deferred */
private val NAME_LANGUAGE = TextLanguage.English

public class ParamsEffects(
    private val data: InstallationData,
    /** Whether the setting asks for translated row names */
    private val translated: Flow<Boolean>
) : EffectRunner<ParamsEffect, ParamsMessage> {
    override fun keyOf(effect: ParamsEffect): Any? = effect::class

    override fun execute(effect: ParamsEffect): Flow<ParamsMessage> = when (effect) {
        is ParamsEffect.Load -> flow {
            val game = data.of(effect.installation)
            emit(
                ParamsMessage.Loaded(
                    params = checkNotNull(game.params()) { "This installation has no GameParam.parambnd" },
                    names = game.paramNames(NAME_LANGUAGE)
                )
            )
        }

        is ParamsEffect.Search -> flow {
            val outcome = withContext(Dispatchers.Default) { searchParams(effect.entries, effect.naming, effect.query) }
            emit(ParamsMessage.SearchCompleted(outcome))
        }

        ParamsEffect.FollowTranslations -> translated.map { on ->
            ParamsMessage.TranslationsChosen(if (on) loadRowNameTranslations() else RowNameTranslations.None)
        }
    }

    override fun onFailure(effect: ParamsEffect, failure: Throwable): ParamsMessage =
        ParamsMessage.Failed(failure.message ?: failure.toString())
}

public sealed interface ParamsEffect {
    public data class Load(public val installation: Installation) : ParamsEffect

    public data class Search(
        public val entries: Collection<ParamEntry>,
        public val naming: RowNaming,
        public val query: ParamQuery
    ) : ParamsEffect

    /** Keeps the row naming in step with the translated-names setting */
    public data object FollowTranslations : ParamsEffect
}
