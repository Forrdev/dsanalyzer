package com.sappyoak.dsanalyzer.analysis.engine

/** Every analyzer's result for every unit it ran against */
public class RunReport(
    public val results: Map<AnalyzerId, Map<RunUnit, AnalysisResult>>
) {
    /** Every lead, sorted by key so the order is the same whichever finished first */
    public val leads: List<Lead> by lazy {
        results.values.flatMap { it.values }
            .filterIsInstance<AnalysisResult.Leads>()
            .flatMap { it.leads }
            .sortedBy { it.key.value }
    }
}
