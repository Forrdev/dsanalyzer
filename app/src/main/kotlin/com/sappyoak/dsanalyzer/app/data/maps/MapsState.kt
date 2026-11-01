package com.sappyoak.dsanalyzer.app.data.maps

import com.sappyoak.dsanalyzer.game.Installation
import com.sappyoak.dsanalyzer.game.world.maps.EntryKind
import com.sappyoak.dsanalyzer.game.world.maps.MapId

public data class MapsState(
    public val installation: Installation? = null,
    public val maps: List<MapId> = emptyList(),
    public val contents: MapContents? = null,
    public val loadingMap: MapId? = null,
    /** Built in background once the catalog is known */
    public val entities: EntityIndex = EntityIndex.Empty,
    public val query: String = "",
    public val kinds: Set<EntryKind> = EntryKind.entries.toSet(),
    public val problem: String? = null
) {
    /** The entries the list shows from this map narrowed by the kind filter and the query */
    public val visible: List<EntrySummary> by lazy {
        contents?.entries.orEmpty().filter { it.kind in kinds && it.matches(query) }
    }

    public val loading: Boolean get() = loadingMap != null
    public val shown: MapId? get() = contents?.map ?: loadingMap
}
