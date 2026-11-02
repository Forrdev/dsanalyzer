package com.sappyoak.dsanalyzer.analysis.engine

import com.sappyoak.dsanalyzer.analysis.Citation

/**
 * One hypothesis an analyzer raises, built to be triaged. A lead is not a
 * [com.sappyoak.dsanalyzer.analysis.Finding]. A finding is a curated claim with
 * evidence. A lead becomes one when it can be confirmed
 */
public data class Lead(
    /** Stable across runs, so a verdict and a promoted finding stay joined to it */
    public val key: LeadKey,
    public val kind: LeadKind,
    public val title: String,
    /** What was found and why it might better */
    public val detail: String,
    public val confidence: Confidence,
    public val citations: List<Citation>,
    /** Things to check in-game, cheapest first */
    public val reproduction: List<String> = emptyList(),
    public val patterns: Set<BugPattern> = emptySet(),
    /** A known trick this resembles, a strong prior that the shape is real */
    public val precedent: String? = null
)

/** An analyzer's id and a local part it chooses from what it found */
@JvmInline
public value class LeadKey(public val value: String) {
    override fun toString(): String = value

    public companion object {
        public fun of(analyzer: AnalyzerId, local: String): LeadKey = LeadKey("$analyzer:$local")
    }
}

/** What kind of thing a lead is, declared by its analyzer, with how much it would matter if real */
public data class LeadKind(
    public val id: String,
    public val label: String,
    public val weight: Int
)

public enum class Confidence(public val weight: Int) {
    /** Matches a shape known to have produced a real trick */
    High(3),
    /** Matches the shape, but a precondition could not be confirmed */
    Medium(2),
    /** A heuristic hit, expect mostly noise */
    Low(1);
}
