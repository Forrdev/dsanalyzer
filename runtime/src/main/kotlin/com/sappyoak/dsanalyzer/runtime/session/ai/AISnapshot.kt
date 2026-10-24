package com.sappyoak.dsanalyzer.runtime.session.ai

public enum class AIScope {
    Off,
    TargetingPlayer,
    All;
}

public data class AISnapshot(
    public val thinkId: Int,
    public val targetHandle: Int?,
    public val requestedAction: Int?,
    public val playingAction: Int?,
    public val timers: List<Float>,
    public val numbers: List<Int>
)
