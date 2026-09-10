package com.sappyoak.dsanalyzer.app.verification

import com.sappyoak.dsanalyzer.app.store.EffectRunner
import com.sappyoak.dsanalyzer.game.InstallationId
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

public class VerificationEffects(
    private val cache: VerificationCache
) : EffectRunner<VerificationEffect, VerificationMessage> {
    public override fun execute(effect: VerificationEffect): Flow<VerificationMessage> =
        flow { perform(effect)?.let { emit(it) } }

    public override fun onFailure(effect: VerificationEffect, failure: Throwable): VerificationMessage? = null

    private suspend fun perform(effect: VerificationEffect): VerificationMessage? = when (effect) {
        VerificationEffect.LoadCache -> VerificationMessage.CacheLoaded(cache.read())

        is VerificationEffect.SaveCache -> {
            cache.write(effect.records)
            null
        }
    }
}

public sealed interface VerificationEffect {
    public data object LoadCache : VerificationEffect

    public data class SaveCache(
        public val records: Map<InstallationId, VerificationRecord>
    ) : VerificationEffect
}