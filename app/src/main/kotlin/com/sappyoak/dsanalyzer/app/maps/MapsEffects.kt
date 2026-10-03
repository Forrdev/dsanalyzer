package com.sappyoak.dsanalyzer.app.maps

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

import com.sappyoak.dsanalyzer.app.store.EffectRunner
import com.sappyoak.dsanalyzer.game.Installation
import com.sappyoak.dsanalyzer.game.files.GameFiles
import com.sappyoak.dsanalyzer.game.files.InstallationFiles
import com.sappyoak.dsanalyzer.game.verification.loadFileManifest
import com.sappyoak.dsanalyzer.game.world.maps.CharacterModels
import com.sappyoak.dsanalyzer.game.world.maps.MapId
import com.sappyoak.dsanalyzer.game.world.maps.availableMaps
import com.sappyoak.dsanalyzer.game.world.maps.loadCharacterModels
import com.sappyoak.dsanalyzer.game.world.maps.loadMSB

public class MapsEffects(private val files: InstallationFiles) : EffectRunner<MapsEffect, MapsMessage> {
    private val characters by lazy { loadCharacterModels() }

    /** One load of each sort at a time, so switching maps quickly cancels the load being replaced */
    override fun keyOf(effect: MapsEffect): Any? = effect::class

    override fun execute(effect: MapsEffect): Flow<MapsMessage> = flow { emit(perform(effect)) }

    override fun onFailure(effect: MapsEffect, failure: Throwable): MapsMessage =
        MapsMessage.Failed(failure.message ?: failure.toString())

    private suspend fun perform(effect: MapsEffect): MapsMessage = when (effect) {
        is MapsEffect.LoadCatalog -> MapsMessage.CatalogLoaded(
            files.use(effect.installation) { games ->
                games.availableMaps(loadFileManifest(effect.installation.build.edition))
            }
        )

        is MapsEffect.LoadMap -> MapsMessage.MapLoaded(
            files.use(effect.installation) { games ->
                games.contents(effect.map, characters)
            }
        )

        is MapsEffect.IndexEntities -> files.use(effect.installation) { games ->
            val read = effect.maps.associateWith { games.loadMSB(it)?.summarize(it, characters) }
            MapsMessage.EntitiesIndexed(
                index = EntityIndex.of(read.values.filterNotNull()),
                skipped = read.filterValues { it == null }.keys.toList()
            )
        }
    }
}

private fun GameFiles.contents(map: MapId, characters: CharacterModels): MapContents =
    checkNotNull(loadMSB(map)) { "$map has no layout file" }.summarize(map, characters)

public sealed interface MapsEffect {
    public data class LoadCatalog(public val installation: Installation) : MapsEffect
    public data class LoadMap(public val installation: Installation, public val map: MapId) : MapsEffect

    public data class IndexEntities(
        public val installation: Installation,
        public val maps: List<MapId>
    ) : MapsEffect
}