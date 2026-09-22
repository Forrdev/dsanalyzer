package com.sappyoak.dsanalyzer.app.maps

import com.sappyoak.dsanalyzer.game.Installation
import com.sappyoak.dsanalyzer.game.world.maps.MapId

public sealed interface MapsMessage {
    public data class Opened(public val installation: Installation) : MapsMessage
    public data class CatalogLoaded(public val maps: List<MapId>) : MapsMessage
    public data class MapLoaded(public val contents: MapContents) : MapsMessage
    public data class EntitiesIndexed(public val index: EntityIndex) : MapsMessage

    public data class Failed(public val reason: String) : MapsMessage
}