package com.sappyoak.dsanalyzer.app.maps

import com.sappyoak.dsanalyzer.game.Installation
import com.sappyoak.dsanalyzer.game.world.EntryKind
import com.sappyoak.dsanalyzer.game.world.WorldRef
import com.sappyoak.dsanalyzer.game.world.maps.MapId

public data class MapsState(
    public val installation: Installation? = null,
    public val maps: List<MapId> = emptyList(),
    public val selected: MapId? = null,
    public val contents: MapContents? = null,
    public val loading: Boolean = false,
    /** Built in background once the catalog is known */
    public val entities: EntityIndex = EntityIndex.Empty,
    public val query: String = "",
    public val kinds: Set<EntryKind> = EntryKind.entries.toSet(),
    public val focused: WorldRef.Entry? = null,
    /** Where to focus once the map it belongs to finishes loading */
    public val pending: WorldRef.Entry? = null,
    public val back: List<WorldRef.Entry> = emptyList(),
    public val forward: List<WorldRef.Entry> = emptyList(),
    public val problem: String? = null
) {
    /** The entries the list shows from this map narrowed by the kind filter and the query */
    public val visible: List<EntrySummary>
        get() = contents?.entries.orEmpty().filter { it.kind in kinds && it.matches(query) }

    public val focusedEntry: EntrySummary?
        get() = focused?.let { ref -> contents?.entries?.firstOrNull { it.ref == ref } }

    public val canGoBack: Boolean get() = back.isNotEmpty()
    public val canGoForward: Boolean get() = forward.isNotEmpty()
}