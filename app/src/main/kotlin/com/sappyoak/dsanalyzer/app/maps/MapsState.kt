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
    public val entities: EntityIndex = EntityIndex.Empty,
    public val query: String = "",
    public val kinds: Set<EntryKind> = EntryKind.entries.toSet(),
    public val problem: String? = null
)