package com.sappyoak.dsanalyzer.app.analysis

import com.sappyoak.dsanalyzer.analysis.engine.AnalysisResult
import com.sappyoak.dsanalyzer.app.store.Transition
import com.sappyoak.dsanalyzer.app.store.with

public fun reduceAnalysis(
    state: AnalysisState,
    message: AnalysisMessage
): Transition<AnalysisState, AnalysisEffect> = when (message) {
    is AnalysisMessage.Opened -> AnalysisState(installation = message.installation).with()

    is AnalysisMessage.Toggled -> {
        val id = message.analyzer
        state.copy(enabled = if (id in state.enabled) state.enabled - id else state.enabled + id).with()
    }

    is AnalysisMessage.RunRequested -> {
        val installation = state.installation
        if (installation == null || state.running || state.enabled.isEmpty()) {
            state.with()
        } else {
            state.copy(running = true, units = 0, results = emptyMap(), problem = null)
                .with(AnalysisEffect.Run(installation, state.enabled))
        }
    }

    is AnalysisMessage.Started -> state.copy(units = message.units).with()
    is AnalysisMessage.Concluded -> state.copy(
        results = state.results + (message.analyzer to
                (state.results[message.analyzer].orEmpty() + (message.unit to message.result))),
        leads = (message.result as? AnalysisResult.Leads)
            ?.let { (state.leads + it.leads).sortedBy { lead -> lead.key.value }}
            ?: state.leads
    ).with()

    is AnalysisMessage.Finished -> state.copy(running = false).with()

    is AnalysisMessage.Failed -> state.copy(running = false, problem = message.reason).with()
}
