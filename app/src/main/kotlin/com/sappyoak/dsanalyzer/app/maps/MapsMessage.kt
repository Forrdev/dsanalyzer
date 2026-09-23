package com.sappyoak.dsanalyzer.app.maps

import com.sappyoak.dsanalyzer.game.Installation
import com.sappyoak.dsanalyzer.game.world.EntryKind
import com.sappyoak.dsanalyzer.game.world.WorldRef
import com.sappyoak.dsanalyzer.game.world.maps.MapId

public sealed interface MapsMessage {
    public data class Opened(public val installation: Installation) : MapsMessage
    public data class CatalogLoaded(public val maps: List<MapId>) : MapsMessage
    public data class MapLoaded(public val contents: MapContents) : MapsMessage
    public data class EntitiesIndexed(public val index: EntityIndex) : MapsMessage

    /** Following a link, a list selection, or a finding */
    public data class Navigated(public val ref: WorldRef) : MapsMessage

    public data object BackRequested : MapsMessage
    public data object ForwardRequested : MapsMessage

    public data class QueryChanged(public val query: String) : MapsMessage
    public data class KindToggled(public val kind: EntryKind) : MapsMessage

    public data class Failed(public val reason: String) : MapsMessage
}