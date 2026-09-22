package com.sappyoak.dsanalyzer.app.maps

import com.sappyoak.dsanalyzer.app.store.Transition
import com.sappyoak.dsanalyzer.app.store.with

public fun reduceMaps(
    state: MapsState,
    message: MapsMessage
) : Transition<MapsState, MapsEffect> = when (message) {
    is MapsMessage.Opened -> MapsState(installation = message.installation, loading = true)
        .with(MapsEffect.LoadCatalog(message.installation))

    is MapsMessage.CatalogLoaded -> {
        val installation = state.installation
        val first = message.maps.firstOrNull()
        val next = state.copy(maps = message.maps, selected = first, loading = first != null)

        if (installation == null || first == null) {
            next.copy(loading = false).with()
        } else {
            next.with(MapsEffect.LoadMap(installation, first), MapsEffect.IndexEntities(installation, message.maps))
        }
    }

    is MapsMessage.MapLoaded -> state.copy(
        contents = message.contents,
        selected = message.contents.map,
        loading = false,
    ).with()

    is MapsMessage.EntitiesIndexed -> state.copy(entities = message.index).with()

    is MapsMessage.Failed -> state.copy(loading = false, problem = message.reason).with()
}