package com.sappyoak.dsanalyzer.app.analysis

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.emitAll
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.mapNotNull

import com.sappyoak.dsanalyzer.analysis.BUILT_IN_ANALYZERS
import com.sappyoak.dsanalyzer.analysis.engine.AnalysisRun
import com.sappyoak.dsanalyzer.analysis.engine.AnalyzerId
import com.sappyoak.dsanalyzer.analysis.engine.RunEvent
import com.sappyoak.dsanalyzer.app.store.EffectRunner
import com.sappyoak.dsanalyzer.game.Installation
import com.sappyoak.dsanalyzer.game.data.InstallationData


public class AnalysisEffects(private val data: InstallationData) : EffectRunner<AnalysisEffect, AnalysisMessage> {
    /** One run at a time; opening another installation's run replaces it */
    override fun keyOf(effect: AnalysisEffect): Any? = effect::class

    override fun execute(effect: AnalysisEffect): Flow<AnalysisMessage> = when (effect) {
        is AnalysisEffect.Run -> flow {
            val source = InstallationSource.open(data.of(effect.installation))
            val analyzers = BUILT_IN_ANALYZERS.filter { it.descriptor.id in effect.analyzers }
            emitAll(AnalysisRun(analyzers, source).execute().mapNotNull(::messageOf))
        }
    }

    override fun onFailure(effect: AnalysisEffect, failure: Throwable): AnalysisMessage =
        AnalysisMessage.Failed(failure.message ?: failure.toString())
}

/** Unit starts are progress the state does not keep; everything else becomes a message */
private fun messageOf(event: RunEvent): AnalysisMessage? = when (event) {
    is RunEvent.Started -> AnalysisMessage.Started(event.units)
    is RunEvent.UnitStarted -> null
    is RunEvent.Concluded -> AnalysisMessage.Concluded(event.analyzer, event.unit, event.result)
    is RunEvent.Finished -> AnalysisMessage.Finished
}

public sealed interface AnalysisEffect {
    public data class Run(public val installation: Installation, public val analyzers: Set<AnalyzerId>) : AnalysisEffect
}
