package com.sappyoak.dsanalyzer.app.data.esd

import com.sappyoak.dsanalyzer.app.data.IndexQueue
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

import com.sappyoak.dsanalyzer.app.store.EffectRunner
import com.sappyoak.dsanalyzer.game.Installation
import com.sappyoak.dsanalyzer.game.data.InstallationData
import com.sappyoak.dsanalyzer.game.files.InstallationFiles
import com.sappyoak.dsanalyzer.game.verification.loadFileManifest
import com.sappyoak.dsanalyzer.game.world.esd.EsdSource
import com.sappyoak.dsanalyzer.game.world.esd.availableTalk
import com.sappyoak.dsanalyzer.game.world.esd.kind
import com.sappyoak.dsanalyzer.game.world.esd.loadEsd
import com.sappyoak.dsanalyzer.game.world.esd.loadEsdDefinitions
import com.sappyoak.dsanalyzer.game.world.maps.availableMaps

public class EsdsEffects(
    private val data: InstallationData,
    private val queue: IndexQueue
) : EffectRunner<EsdsEffect, EsdsMessage> {
    /** One of each at a time, so choosing another ESD cancels the load it replaces */
    override fun keyOf(effect: EsdsEffect): Any? = effect::class

    override fun execute(effect: EsdsEffect): Flow<EsdsMessage> = flow { emit(perform(effect)) }

    override fun onFailure(effect: EsdsEffect, failure: Throwable): EsdsMessage =
        EsdsMessage.Failed(failure.message ?: failure.toString())

    private suspend fun perform(effect: EsdsEffect): EsdsMessage = when (effect) {
        is EsdsEffect.LoadCatalog -> EsdsMessage.CatalogLoaded(queue.run {
            data.of(effect.installation).talk()
        })

        is EsdsEffect.Load -> EsdsMessage.Loaded(
            source = effect.source,
            esd = checkNotNull(data.of(effect.installation).esd(effect.source)) {
                "This installation has no ${effect.source.title}"
            },
            definitions = loadEsdDefinitions(effect.installation.build.edition, effect.source.kind)
        )
    }
}

public sealed interface EsdsEffect {
    public data class LoadCatalog(public val installation: Installation) : EsdsEffect
    public data class Load(public val installation: Installation, public val source: EsdSource) : EsdsEffect
}
