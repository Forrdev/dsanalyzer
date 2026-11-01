package com.sappyoak.dsanalyzer.app.data.maps

import com.sappyoak.dsanalyzer.app.store.Transition
import com.sappyoak.dsanalyzer.app.store.with
import com.sappyoak.dsanalyzer.game.world.maps.MapId

public fun reduceMaps(
    state: MapsState,
    message: MapsMessage
) : Transition<MapsState, MapsEffect> = when (message) {
    is MapsMessage.Opened -> MapsState(installation = message.installation)
        .with(MapsEffect.LoadCatalog(message.installation))

    is MapsMessage.CatalogLoaded -> {
        val installation = state.installation
        val next = state.copy(maps = message.maps)

        if (installation == null || message.maps.isEmpty()) {
            next.with()
        } else {
            next.with(MapsEffect.IndexEntities(installation, message.maps))
        }
    }

    is MapsMessage.MapLoaded -> state.accept(message.contents)

    is MapsMessage.EntitiesIndexed -> state.copy(
        entities = message.index,
        problem = message.skipped.takeIf { it.isNotEmpty() }?.let(::describeSkipped) ?: state.problem
    ).with()

    is MapsMessage.MapSelected -> state.read(message.map)

    is MapsMessage.QueryChanged -> state.copy(query = message.query).with()
    is MapsMessage.KindToggled -> state.copy(
        kinds = if (message.kind in state.kinds) state.kinds - message.kind else state.kinds + message.kind
    ).with()

    is MapsMessage.Failed -> state.copy(loadingMap = null, problem = message.reason).with()
    is MapsMessage.IndexFailed -> state.copy(problem = "Entities could not be indexed: ${message.reason}").with()
}

private fun MapsState.read(map: MapId): Transition<MapsState, MapsEffect> {
    val installation = installation

    return when {
        map == contents?.map || map == loadingMap -> with()
        installation == null -> copy(problem = "No installation is open").with()
        else -> copy(contents = null, loadingMap = map, problem = null)
            .with(MapsEffect.LoadMap(installation, map))
    }
}

private fun MapsState.accept(contents: MapContents): Transition<MapsState, MapsEffect> =
    if (contents.map == loadingMap) {
        copy(contents = contents, loadingMap = null).with()
    } else {
        with()
    }


private fun describeSkipped(maps: List<MapId>): String =
    "No layout file for ${maps.joinToString()}, so entity search will not cover " + if (maps.size == 1) "it" else "them"
