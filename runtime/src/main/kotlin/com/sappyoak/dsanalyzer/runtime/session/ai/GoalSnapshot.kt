package com.sappyoak.dsanalyzer.runtime.session.ai

public data class GoalSnapshot(
    public val id: Int,
    public val name: String,
    public val life: Float?,
    public val params: List<Float>,
    public val subgoals: List<GoalSnapshot>
) {
    public val active: GoalSnapshot? get() = subgoals.firstOrNull()
    public val queued: List<GoalSnapshot> get() = subgoals.drop(1)
}
