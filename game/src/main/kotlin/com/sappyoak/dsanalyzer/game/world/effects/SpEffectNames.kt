package com.sappyoak.dsanalyzer.game.world.effects


private const val NAMES_RESOURCE = "/definitions/ds1-speffect-names.tsv"
private const val COMMENT = '#'
private const val SEPARATOR = '\t'
private const val COLUMNS = 3

public data class SpEffectName(
    public val id: Int,
    public val name: String,
    public val source: String
) {
    init {
        require(SEPARATOR !in name && SEPARATOR !in source) { "SpEffect $id: a tab cannot appear in a column" }
    }
}

public class SpEffectNames(entries: Collection<SpEffectName>) {
    private val byId: Map<Int, SpEffectName> = entries.associateBy { it.id }

    public val entries: List<SpEffectName> get() = byId.values.sortedBy { it.id }

    public operator fun get(id: Int): SpEffectName? = byId[id]

    public fun nameOf(id: Int): String? = byId[id]?.name

    /**
     * These names plus every [generated] one for an id not named yet. A name already in the file is
     * never replaced, so hand edits survive a regeneration
     */
    public fun withGenerated(generated: Collection<SpEffectName>): SpEffectNames =
        SpEffectNames(generated.filterNot { it.id in byId } + byId.values)

    public companion object {
        public val None: SpEffectNames = SpEffectNames(emptyList())
    }
}
