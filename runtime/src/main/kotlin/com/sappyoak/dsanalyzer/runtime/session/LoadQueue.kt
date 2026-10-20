package com.sappyoak.dsanalyzer.runtime.session

import com.sappyoak.dsanalyzer.game.world.maps.MapId
import com.sappyoak.dsanalyzer.runtime.ptde.LoadStage


/** One map block's load state */
public data class BlockLoad(
    public val index: Int,
    public val stage: Int,
    public val idle: Boolean,
    public val loaded: Boolean,
    public val pending: Boolean,
    /** The block was matched against a requested map this tick, or force-loaded */
    public val requested: Boolean,
    /** The request survived its gates, the block genuinely should be resident */
    public val wanted: Boolean,
    /** Something is waiting on this block, which holds it against unloading */
    public val pinned: Boolean
) {
    public val active: Boolean get() = stage != LoadStage.Idle || requested || wanted || pinned
}

/**
 * What the game has been asked to load and how far each block got
 */
public data class LoadQueueSnapshot(
    public val current: MapId?,
    public val previous: MapId?,
    public val warpTarget: MapId?,
    public val blocks: List<BlockLoad>
) {
    public val loaded: List<BlockLoad> get() = blocks.filter { it.loaded }
    public val pending: List<BlockLoad> get() = blocks.filter { it.pending }

    /** True while the game is holding two maps at once, which is the handoff a transition makes */
    public val handingOver: Boolean get() = previous != null && current != null
}
