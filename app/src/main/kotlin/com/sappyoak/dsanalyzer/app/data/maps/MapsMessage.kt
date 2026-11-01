package com.sappyoak.dsanalyzer.app.data.maps

import com.sappyoak.dsanalyzer.game.Installation
import com.sappyoak.dsanalyzer.game.world.maps.EntryKind
import com.sappyoak.dsanalyzer.game.world.maps.MapId

public sealed interface MapsMessage {
    public data class Opened(public val installation: Installation) : MapsMessage
    public data class CatalogLoaded(public val maps: List<MapId>) : MapsMessage
    public data class MapLoaded(public val contents: MapContents) : MapsMessage
    public data class EntitiesIndexed(
        public val index: EntityIndex,
        /** Maps with no readable layout, which the index says nothing about */
        public val skipped: List<MapId> = emptyList()
    ) : MapsMessage

    public data class MapSelected(public val map: MapId) : MapsMessage

    public data class QueryChanged(public val query: String) : MapsMessage
    public data class KindToggled(public val kind: EntryKind) : MapsMessage

    public data class Failed(public val reason: String) : MapsMessage
    /** The entity index could not be built, which leaves any map load running untouched */
    public data class IndexFailed(public val reason: String) : MapsMessage
}
