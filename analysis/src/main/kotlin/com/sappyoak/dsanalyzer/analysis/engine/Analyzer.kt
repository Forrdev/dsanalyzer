package com.sappyoak.dsanalyzer.analysis.engine

import kotlinx.serialization.Serializable
import com.sappyoak.dsanalyzer.game.GameBuild

@JvmInline
@Serializable
public value class AnalyzerId(public val value: String) {
    override fun toString(): String = value
}

/**
 * Looks at game data for the shape of a bug
 *
 * [input] decides what the analyzer is handed and how often, so scope and data requirements follow
 * from what it reads rather than being declared beside it where they could disagree
 */
public interface Analyzer<C : Any> {
    public val descriptor: AnalyzerDescriptor
    public val input: ContextKey<C>

    public suspend fun analyze(context: AnalysisContext<C>) : AnalysisResult
}

/** Everything an analyzer is given, the data for one unit and what the run knows */
public class AnalysisContext<out C : Any>(
    public val data: C,
    public val unit: RunUnit,
    public val run: RunInfo
)


public class RunInfo(
    public val build: GameBuild,
    private val values: Map<String, Double>,
    /** The results of this analyzer's declared dependencies, for the same unit */
    public val upstream: Map<AnalyzerId, AnalysisResult>
) {
    /** The value chosen from [parameter] this run, or its default */
    public fun valueOf(parameter: AnalyzerParameter): Double = values[parameter.id] ?: parameter.default
}

