package com.sappyoak.dsanalyzer.runtime.session.ai

public enum class AIScope {
    Off,
    TargetingPlayer,
    All;
}

public sealed interface AITarget {
    public data object None : AITarget

    /** A sound it is turned towards */
    public data object Sound : AITarget

    public data class Character(public val handle: Int) : AITarget
}

public data class AISnapshot(
    public val thinkId: Int,
    public val target: AITarget?,
    public val requestedAction: Int?,
    public val playingAction: Int?,
    public val timers: List<Float>,
    public val numbers: List<Int>
)
