package com.sappyoak.dsanalyzer.app.maps

/**
 * Every entry that carries an entity id, across all the installation's maps.
 */
public class EntityIndex(private val byId: Map<Int, List<EntrySummary>>) {
    public val size: Int get() = byId.size

    public operator fun get(entityId: Int): List<EntrySummary> = byId[entityId].orEmpty()

    public companion object {
        public val Empty: EntityIndex = EntityIndex(emptyMap())

        public fun of(contents: Iterable<MapContents>): EntityIndex = EntityIndex(
            contents
                .flatMap { it.entries }
                .filter { it.entityId != null }
                .groupBy { checkNotNull(it.entityId) }
        )
    }
}