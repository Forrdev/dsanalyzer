package com.sappyoak.dsanalyzer.app.data.ai

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

import com.sappyoak.dsanalyzer.app.data.IndexQueue
import com.sappyoak.dsanalyzer.app.store.EffectRunner
import com.sappyoak.dsanalyzer.game.Installation
import com.sappyoak.dsanalyzer.game.data.InstallationData
import com.sappyoak.dsanalyzer.game.world.ai.LuaScriptId

public class AiEffects(
    private val data: InstallationData,
    private val queue: IndexQueue
) : EffectRunner<AiEffect, AiMessage> {
    /** One of each at a time, so choosing another script cancels the load it replaces */
    override fun keyOf(effect: AiEffect): Any? = effect::class

    override fun execute(effect: AiEffect): Flow<AiMessage> = flow { emit(perform(effect)) }

    override fun onFailure(effect: AiEffect, failure: Throwable): AiMessage =
        AiMessage.Failed(failure.message ?: failure.toString())

    private suspend fun perform(effect: AiEffect): AiMessage = when (effect) {
        is AiEffect.LoadCatalog -> AiMessage.CatalogLoaded(data.of(effect.installation).luaScripts())

        // Decompiling every AI script waits its turn behind the other indexes
        is AiEffect.Index -> AiMessage.Indexed(queue.run("ai") { data.of(effect.installation).aiIndex() })

        // Every map's layout is read to find which characters each think row is placed with
        is AiEffect.FindCharacters ->
            AiMessage.CharactersFound(queue.run("think characters") { data.of(effect.installation).thinkCharacters() })

        is AiEffect.Load -> AiMessage.Loaded(
            script = effect.script,
            source = checkNotNull(data.of(effect.installation).lua(effect.script)) {
                "This installation has no ${effect.script}"
            }
        )
    }
}

public sealed interface AiEffect {
    public data class LoadCatalog(public val installation: Installation) : AiEffect
    public data class Index(public val installation: Installation) : AiEffect
    public data class FindCharacters(public val installation: Installation) : AiEffect
    public data class Load(public val installation: Installation, public val script: LuaScriptId) : AiEffect
}
