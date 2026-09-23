package com.sappyoak.dsanalyzer.game.world

import com.sappyoak.dsanalyzer.game.world.maps.MapId

/** The kinds of entries a map's layout holds */
public enum class EntryKind {
    Model,
    Event,
    Region,
    Part;
}

/**
 * Something in the game world that can be pointed at
 */
public sealed interface WorldRef {
    public data class Map(public val map: MapId) : WorldRef
    public data class Entry(
        public val map: MapId,
        public val kind: EntryKind,
        public val index: Int
    ) : WorldRef

    public data class Entity(public val entityId: Int) : WorldRef
}