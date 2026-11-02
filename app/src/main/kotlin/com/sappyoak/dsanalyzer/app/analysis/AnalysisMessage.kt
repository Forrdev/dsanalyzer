package com.sappyoak.dsanalyzer.app.analysis

import com.sappyoak.dsanalyzer.analysis.engine.AnalysisResult
import com.sappyoak.dsanalyzer.analysis.engine.AnalyzerId
import com.sappyoak.dsanalyzer.analysis.engine.RunUnit
import com.sappyoak.dsanalyzer.game.Installation

public sealed interface AnalysisMessage {
    public data class Opened(public val installation: Installation) : AnalysisMessage
    public data class Toggled(public val analyzer: AnalyzerId) : AnalysisMessage
    public data object RunRequested : AnalysisMessage

    public data class Started(public val units: Int) : AnalysisMessage
    public data class Concluded(
        public val analyzer: AnalyzerId,
        public val unit: RunUnit,
        public val result: AnalysisResult
    ) : AnalysisMessage
    public data object Finished : AnalysisMessage

    public data class Failed(public val reason: String) : AnalysisMessage
}
