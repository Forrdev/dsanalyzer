package com.sappyoak.dsanalyzer.app.analysis

import com.sappyoak.dsanalyzer.analysis.engine.AnalysisResult
import com.sappyoak.dsanalyzer.analysis.engine.RunUnit

/**
 * One analyzer's outcomes across its units, worst first, so a failure or missing data is read
 * before the clean count that would otherwise suggest everything was looked at
 */
public fun Map<RunUnit, AnalysisResult>.summary(): String {
    val counts = values.groupingBy { it.label }.eachCount()
    return ORDER.mapNotNull { label -> counts[label]?.let { "$it $label" } }.joinToString(" · ")
}

private val ORDER = listOf("failed", "unavailable", "with leads", "clean", "not applicable")

private val AnalysisResult.label: String
    get() = when (this) {
        is AnalysisResult.Failed -> "failed"
        is AnalysisResult.Unavailable -> "unavailable"
        is AnalysisResult.Leads -> "with leads"
        is AnalysisResult.Clean -> "clean"
        is AnalysisResult.NotApplicable -> "not applicable"
    }
