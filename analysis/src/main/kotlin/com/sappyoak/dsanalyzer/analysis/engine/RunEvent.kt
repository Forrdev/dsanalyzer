package com.sappyoak.dsanalyzer.analysis.engine

public sealed interface RunEvent {
    public data class Started(
        public val analyzers: Int,
        public val units: Int
    ) : RunEvent

    public data class UnitStarted(public val unit: RunUnit) : RunEvent
    public data class Concluded(
        public val analyzer: AnalyzerId,
        public val unit: RunUnit,
        public val result: AnalysisResult
    ) : RunEvent

    public data class Finished(public val report: RunReport) : RunEvent
}
