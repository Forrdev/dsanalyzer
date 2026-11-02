package com.sappyoak.dsanalyzer.app.analysis

import com.sappyoak.dsanalyzer.analysis.BUILT_IN_ANALYZERS
import com.sappyoak.dsanalyzer.analysis.engine.AnalysisResult
import com.sappyoak.dsanalyzer.analysis.engine.AnalyzerDescriptor
import com.sappyoak.dsanalyzer.analysis.engine.AnalyzerId
import com.sappyoak.dsanalyzer.analysis.engine.Lead
import com.sappyoak.dsanalyzer.analysis.engine.RunUnit
import com.sappyoak.dsanalyzer.game.Installation

public data class AnalysisState(
    public val installation: Installation? = null,
    public val catalogue: List<AnalyzerDescriptor> = BUILT_IN_ANALYZERS.map { it.descriptor },
    public val enabled: Set<AnalyzerId> = catalogue.filter { it.enabledByDefault }.mapTo(mutableSetOf()) { it.id },
    public val running: Boolean = false,
    public val units: Int = 0,
    public val results: Map<AnalyzerId, Map<RunUnit, AnalysisResult>> = emptyMap(),
    public val problem: String? = null
) {
    public val concluded: Int get() = results.values.sumOf { it.size }
    public val leads: List<Lead> by lazy {
        results.values.flatMap { it.values }
            .filterIsInstance<AnalysisResult.Leads>()
            .flatMap { it.leads }
            .sortedBy { it.key.value }
    }
}
