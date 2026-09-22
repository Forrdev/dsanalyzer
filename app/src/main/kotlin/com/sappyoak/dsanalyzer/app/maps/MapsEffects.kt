package com.sappyoak.dsanalyzer.app.maps

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.withContext

import com.sappyoak.dsanalyzer.app.store.EffectRunner
import com.sappyoak.dsanalyzer.game.Installation
import com.sappyoak.dsanalyzer.game.files.GameFiles
import com.sappyoak.dsanalyzer.game.files.InstallationFiles
import com.sappyoak.dsanalyzer.game.verification.loadFileManifest
import com.sappyoak.dsanalyzer.game.world.maps.MapId
import com.sappyoak.dsanalyzer.game.world.maps.availableMaps
import com.sappyoak.dsanalyzer.game.world.maps.loadMSB

public class MapsEffects(private val files: InstallationFiles) : EffectRunner<MapsEffect, MapsMessage> {
    /** One load of each sort at a time, so switching maps quickly cancels the load being replaced */
    override fun keyOf(effect: MapsEffect): Any? = effect::class

    override fun execute(effect: MapsEffect): Flow<MapsMessage> = flow { emit(perform(effect)) }

    override fun onFailure(effect: MapsEffect, failure: Throwable): MapsMessage =
        MapsMessage.Failed(failure.message ?: failure.toString())

    private suspend fun perform(effect: MapsEffect): MapsMessage = when (effect) {
        is MapsEffect.LoadCatalog -> MapsMessage.CatalogLoaded(
            files.use(effect.installation) { games ->
                withContext(Dispatchers.IO) {
                    games.availableMaps(loadFileManifest(effect.installation.build.edition))
                }
            }
        )

        is MapsEffect.LoadMap -> MapsMessage.MapLoaded(
            files.use(effect.installation) { games ->
                withContext(Dispatchers.IO) { games.contents(effect.map) }
            }
        )

        is MapsEffect.IndexEntities -> MapsMessage.EntitiesIndexed(
            files.use(effect.installation) { games ->
                withContext(Dispatchers.IO) {
                    EntityIndex.of(effect.maps.map(games::contents))
                }
            }
        )
    }
}

private fun GameFiles.contents(map: MapId): MapContents =
    checkNotNull(loadMSB(map)) { "$map has no layout file" }.summarize(map)

public sealed interface MapsEffect {
    public data class LoadCatalog(public val installation: Installation) : MapsEffect
    public data class LoadMap(public val installation: Installation, public val map: MapId) : MapsEffect

    public data class IndexEntities(
        public val installation: Installation,
        public val maps: List<MapId>
    ) : MapsEffect
}