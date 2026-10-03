package com.sappyoak.dsanalyzer.runtime.session

import kotlin.time.Duration

import com.sappyoak.dsanalyzer.game.world.maps.MapId
import com.sappyoak.dsanalyzer.runtime.ptde.FlagChange

private const val UNSTABLE_DISTANCE = 1f

public data class RuntimeSnapshot(
    public val inGameTimeMillis: Int,
    public val frame: Long,
    public val place: WorldPlace,
    public val loaded: Boolean,
    public val reloaded: Boolean,
    public val player: PlayerSnapshot?,
    public val world: WorldSnapshot?,
    public val flagChanges: List<FlagChange>,
    public val cost: SampleCost
) {
    public val divergence: Float?
        get() {
            val here = player?.position ?: return null
            val stable = world?.stablePosition ?: return null
            return here.distanceTo(stable)
        }

    public val offStableGround: Boolean
        get() = (divergence ?: 0f) > UNSTABLE_DISTANCE

    public val map: MapId? get() = place.loadedMap
    public val inWorld: Boolean get() = loaded && map != null
}

public data class SampleCost(
    public val elapsed: Duration,
    public val present: Int,
    public val expected: Int
) {
    public val complete: Boolean get() = present == expected
}