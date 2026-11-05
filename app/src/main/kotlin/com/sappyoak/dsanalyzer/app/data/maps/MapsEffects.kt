package com.sappyoak.dsanalyzer.app.data.maps

import com.sappyoak.dsanalyzer.app.data.IndexQueue
import io.github.oshai.kotlinlogging.KotlinLogging
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

import com.sappyoak.dsanalyzer.app.store.EffectRunner
import com.sappyoak.dsanalyzer.game.Installation
import com.sappyoak.dsanalyzer.game.data.InstallationData
import com.sappyoak.dsanalyzer.game.world.maps.MapId
import com.sappyoak.dsanalyzer.game.world.maps.loadCharacterModels

private val logger = KotlinLogging.logger {  }

public class MapsEffects(
    private val data: InstallationData,
    private val queue: IndexQueue
) : EffectRunner<MapsEffect, MapsMessage> {

    /** One load of each sort at a time, so switching maps quickly cancels the load being replaced */
    override fun keyOf(effect: MapsEffect): Any? = effect::class

    override fun execute(effect: MapsEffect): Flow<MapsMessage> = flow { emit(perform(effect)) }

    override fun onFailure(effect: MapsEffect, failure: Throwable): MapsMessage {
        val reason = failure.message ?: failure.toString()
        return if (effect is MapsEffect.IndexEntities) MapsMessage.IndexFailed(reason)
        else MapsMessage.Failed(reason)
    }

    private suspend fun perform(effect: MapsEffect): MapsMessage {
        val game = data.of(effect.installation)
        return when (effect) {
            is MapsEffect.LoadCatalog -> MapsMessage.CatalogLoaded(game.catalog().maps)
            is MapsEffect.LoadMap -> {
                val msb = checkNotNull(game.msb(effect.map)) { "${effect.map} has no layout file" }
                MapsMessage.MapLoaded(msb.summarize(effect.map, loadCharacterModels()))
            }

            is MapsEffect.IndexEntities -> queue.run {
                val read = effect.maps.associateWith { game.msb(it)?.summarize(it, loadCharacterModels()) }
                MapsMessage.EntitiesIndexed(
                    index = EntityIndex.of(read.values.filterNotNull()),
                    skipped = read.filterValues { it == null }.keys.toList()
                )
            }
        }
    }
}

public sealed interface MapsEffect {
    public val installation: Installation

    public data class LoadCatalog(override val installation: Installation) : MapsEffect
    public data class LoadMap(override val installation: Installation, public val map: MapId) : MapsEffect

    public data class IndexEntities(
        override val installation: Installation,
        public val maps: List<MapId>
    ) : MapsEffect
}
