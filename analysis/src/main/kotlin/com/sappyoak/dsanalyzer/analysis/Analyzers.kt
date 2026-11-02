package com.sappyoak.dsanalyzer.analysis

import com.sappyoak.dsanalyzer.analysis.engine.Analyzer
import com.sappyoak.dsanalyzer.analysis.param.ReferenceConfusion
import com.sappyoak.dsanalyzer.analysis.script.FlagWriteRace

public val BUILT_IN_ANALYZERS: List<Analyzer<*>> = listOf(ReferenceConfusion, FlagWriteRace)
