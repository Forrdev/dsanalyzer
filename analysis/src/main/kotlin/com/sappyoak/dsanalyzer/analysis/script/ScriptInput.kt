package com.sappyoak.dsanalyzer.analysis.script

import com.sappyoak.dsanalyzer.analysis.engine.ContextKey
import com.sappyoak.dsanalyzer.analysis.engine.GameSource
import com.sappyoak.dsanalyzer.analysis.engine.RunUnit
import com.sappyoak.dsanalyzer.analysis.engine.Scope
import com.sappyoak.dsanalyzer.game.data.DecodedScript

public object ScriptInput : ContextKey<DecodedScript>("event script", Scope.PerScript) {
    override suspend fun load(source: GameSource, unit: RunUnit): DecodedScript? =
        source.data { it.script((unit as RunUnit.Script).script) }
}
