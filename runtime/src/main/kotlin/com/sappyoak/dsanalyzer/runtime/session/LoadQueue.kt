package com.sappyoak.dsanalyzer.runtime.session

import com.sappyoak.dsanalyzer.game.world.maps.MapId
import com.sappyoak.dsanalyzer.runtime.ptde.LoadStage

public enum class BlockState { Absent, Loading, Resident, Unloading, Unknown; }

public enum class HeldBy {
    CurrentMap,
    PreviousMap,
    WarpTarget,
    Forced,
    Pinned,
    Referenced,
    Nothing;
}

public data class BlockLoad(
    public val index: Int,
    public val mapId: MapId?,
    public val stage: Int,
    public val state: BlockState,
    public val heldBy: HeldBy,
    public val requested: Boolean,
    public val latched: Boolean,
    public val wanted: Boolean,
    public val pinned: Boolean,
    public val suppressed: Boolean
) {
    public val queued: Boolean get() = wanted && state == BlockState.Absent
    public val releasing: Boolean get() = !wanted && state == BlockState.Resident
    public val active: Boolean get() = state != BlockState.Absent || wanted || pinned
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
    public val resident: List<BlockLoad> get() = blocks.filter { it.state == BlockState.Resident }
    public val loading: List<BlockLoad> get() = blocks.filter { it.state == BlockState.Loading }
    public val unloading: List<BlockLoad> get() = blocks.filter { it.state == BlockState.Unloading }
    public val queued: List<BlockLoad> get() = blocks.filter { it.queued }
    public val releasing: List<BlockLoad> get() = blocks.filter { it.releasing }

    /** True while the game is holding two maps at once, which is the handoff a transition makes */
    public val handingOver: Boolean get() = previous != null && current != null
}
