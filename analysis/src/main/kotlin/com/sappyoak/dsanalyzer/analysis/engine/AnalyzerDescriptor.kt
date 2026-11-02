package com.sappyoak.dsanalyzer.analysis.engine


/**
 * What an analyzer is, what it costs, and what can be adjusted about it
 */
public data class AnalyzerDescriptor(
    public val id: AnalyzerId,
    /**
     * Bumped when this analyzer's out would differ as in a changed default or a corrected condition, not
     * a refactor. Per analyzer, so one change invalidates only its own cached results
     */
    public val version: Int,
    public val displayName: String,
    public val summary: String,
    /** The bug in terms of its mechanism rather than its symptom, which is what lets a runner judge a lead */
    public val mechanism: String,
    public val kinds: List<LeadKind>,
    public val patterns: Set<BugPattern> = emptySet(),
    /** Documented tricks it would have found, each is a fixture it must keep finding */
    public val precedents: List<String> = emptyList(),
    public val cost: AnalyzerCost,
    public val parameters: List<AnalyzerParameter> = emptyList(),
    /** Analyzers whose results this one reads, for the same unit */
    public val dependsOn: Set<AnalyzerId> = emptySet(),
    /** Off for the noisy ones, which would other bury the ones that work */
    public val enabledByDefault: Boolean = true
)

/** Rough runtime by what dominates */
public enum class AnalyzerCost(public val label: String) {
    Instant("Set arithmetic over data already loaded"),
    Cheap("Walks scripts or tables already loaded"),
    Expensive("Parses something specifically for this");
}

/** One adjustable value where [kind] decides how it is offered */
public data class AnalyzerParameter(
    public val id: String,
    public val displayName: String,
    public val kind: ParameterKind,
    public val description: String,
    public val default: Double,
    public val range: ClosedFloatingPointRange<Double>,
    /** Which way loosening goes, which is not always the obvious one */
    public val raisingMeans: Direction = Direction.FewerLeads
) {
    public enum class Direction { FewerLeads, MoreLeads; }
}

public enum class ParameterKind {
    /** A judgement that someone could set differently, offered for adjustment */
    Threshold,
    /** Has a correct answer the game can be measured for, shown with its provenance */
    Measurement;
}

/** Data that an input needs */
public enum class Requirement(
    public val label: String,
    public val consequence: String
) {
    GameParams("Game parameters", "GameParam.parambnd is missing, so no row can be read")
}
