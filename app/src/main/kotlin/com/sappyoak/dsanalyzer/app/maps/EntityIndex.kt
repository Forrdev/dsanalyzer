package com.sappyoak.dsanalyzer.app.maps

import com.sappyoak.dsanalyzer.game.world.WorldRef

/**
 * Every entry that carries an entity id, across all the installation's maps.
 *
 * Only the reference is kept, not the entry it names. This index spans the whole game and is held
 * for as long as a workspace is open, while the name and subtype it would otherwise carry are read back
 * from whatever map is loaded at the time
 */
public class EntityIndex(private val byId: Map<Int, List<WorldRef.Entry>>) {
    public val size: Int get() = byId.size

    public operator fun get(entityId: Int): List<WorldRef.Entry> = byId[entityId].orEmpty()

    public companion object {
        public val Empty: EntityIndex = EntityIndex(emptyMap())

        public fun of(contents: Iterable<MapContents>): EntityIndex = EntityIndex(
            contents
                .flatMap { it.entries }
                .mapNotNull { entry -> entry.entityId?.let { it to entry.ref } }
                .groupBy({ (entityId, _) -> entityId }, { (_, ref) -> ref })
        )
    }
}