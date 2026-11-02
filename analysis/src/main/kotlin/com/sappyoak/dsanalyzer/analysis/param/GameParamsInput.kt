package com.sappyoak.dsanalyzer.analysis.param

import com.sappyoak.dsanalyzer.analysis.engine.ContextKey
import com.sappyoak.dsanalyzer.analysis.engine.GameSource
import com.sappyoak.dsanalyzer.analysis.engine.Requirement
import com.sappyoak.dsanalyzer.analysis.engine.RunUnit
import com.sappyoak.dsanalyzer.analysis.engine.Scope
import com.sappyoak.dsanalyzer.game.world.params.GameParams
import com.sappyoak.dsanalyzer.game.world.params.loadGameParams

public object GameParamsInput : ContextKey<GameParams>("game params", Scope.Game, setOf(Requirement.GameParams)) {
    override suspend fun load(source: GameSource, unit: RunUnit): GameParams? =
        source.read { files -> files.loadGameParams(source.build.edition) }
}
