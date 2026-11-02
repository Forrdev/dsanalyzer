package com.sappyoak.dsanalyzer.app.analysis

import kotlinx.coroutines.CoroutineScope

import com.sappyoak.dsanalyzer.app.store.Store

public class AnalysisStore(
    scope: CoroutineScope,
    effects: AnalysisEffects
) : Store<AnalysisState, AnalysisMessage, AnalysisEffect>(
    scope = scope,
    name = "analysis",
    initial = AnalysisState(),
    reduce = ::reduceAnalysis,
    effects = effects
)
