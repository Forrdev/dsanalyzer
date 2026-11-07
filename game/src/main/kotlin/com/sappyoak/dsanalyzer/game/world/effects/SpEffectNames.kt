package com.sappyoak.dsanalyzer.game.world.effects

import com.sappyoak.dsanalyzer.game.bundledText

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

    /** The file's text: a header comment, then one line per id in id order */
    public fun toTsv(): String = buildString {
        appendLine("$COMMENT id${SEPARATOR}name${SEPARATOR}source")
        entries.forEach { appendLine("${it.id}$SEPARATOR${it.name}$SEPARATOR${it.source}") }
    }

    public companion object {
        public val None: SpEffectNames = SpEffectNames(emptyList())

        public val Bundled: SpEffectNames by lazy { parse(bundledText(NAMES_RESOURCE)) }

        /** Throws on a malformed line, naming it: the file is ours, so a bad line is a bug to fix */
        public fun parse(text: String): SpEffectNames = SpEffectNames(
            text.lineSequence().withIndex()
                .filterNot { (_, line) -> line.isBlank() || line.startsWith(COMMENT) }
                .map { (index, line) -> entryOf(index + 1, line) }
                .toList()
        )

        private fun entryOf(lineNumber: Int, line: String): SpEffectName {
            val columns = line.split(SEPARATOR)
            require(columns.size == COLUMNS) { "line $lineNumber: expected $COLUMNS tab-separated columns, got ${columns.size}" }
            val id = requireNotNull(columns[0].trim().toIntOrNull()) { "line $lineNumber: '${columns[0]}' is not an id" }
            return SpEffectName(id, columns[1].trim(), columns[2].trim())
        }
    }
}
