package com.sappyoak.dsanalyzer.analysis.engine

/**
 * What one analyzer concluded about one unit.
 *
 * Every outcome is its own case, because they mean different things to the person reading them.
 */
public sealed interface AnalysisResult {
    /** Hypotheses to triage. Never empty. An analyzer with nothing to report is [Clean] */
    public data class Leads(
        public val leads: List<Lead>,
        public val coverage: Coverage
    ) : AnalysisResult {
        init {
            require(leads.isNotEmpty()) { "No leads is Clean, not an empty Leads" }
        }
    }

    /** Looked at everything [coverage] names and matched nothing */
    public data class Clean(public val coverage: Coverage) : AnalysisResult

    /** Nothing here for it to look at */
    public data class NotApplicable(public val reason: String) : AnalysisResult

    /** Its data is missing. Set by the runner from the input's requirements, never by an analyzer */
    public data class Unavailable(public val missing: Set<Requirement>) : AnalysisResult

    /** It threw, set by the runner which catches */
    public data class Failed(public val message: String) : AnalysisResult
}

/**
 * What was examined, and what could not be. Incompleteness belongs to the run rather than to each lead.
 * An unresolved argument means some places were never examined, not that a lead is doubtful
 */
public data class Coverage(
    public val examined: Int,
    public val unit: String,
    public val gaps: List<Gap> = emptyList()
)

/** Places the analyzer could not see into and why */
public data class Gap(public val count: Int, public val reason: String)

/** Leads, or Clean when there are none. The only way an analyzer should build either */
public fun resultOf(leads: List<Lead>, coverage: Coverage): AnalysisResult =
    if (leads.isEmpty()) AnalysisResult.Clean(coverage) else AnalysisResult.Leads(leads, coverage)
