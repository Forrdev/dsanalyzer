package com.sappyoak.dsanalyzer.runtime.session.ai

internal class AISelection(private val gracePeriod: Int) {
    private var reads: Int = 0
    private val lastTargeting = HashMap<Int, Int>()

    fun select(
        scope: AIScope,
        targets: Map<Int, Int?>,
        player: Int?
    ): Set<Int> {
        reads++
        if (scope != AIScope.TargetingPlayer) lastTargeting.clear()

        return when (scope) {
            AIScope.Off -> emptySet()
            AIScope.All -> targets.keys
            AIScope.TargetingPlayer -> targetingPlayer(targets, player)
        }
    }

    private fun targetingPlayer(targets: Map<Int, Int?>, player: Int?): Set<Int> {
        if (player != null) {
            targets.forEach { (handle, target) -> if (target == player) lastTargeting[handle] = reads }
        }

        // gone from the world or past grace period
        lastTargeting.entries.removeAll { (handle, at) -> handle !in targets || reads - at > gracePeriod }
        return lastTargeting.keys.toSet()
    }
}
