package com.sappyoak.dsanalyzer.runtime.session

import com.sappyoak.dsanalyzer.game.world.maps.MapId

public enum class BlockState { Absent, Loading, Resident, Unloading, Unknown; }

public enum class HeldBy {
    CurrentMap,
    PreviousMap,
    WarpTarget,
    Forced,
    Pinned,
    ActiveGroups,
    Connected,
    Referenced,
    Nothing;
}

public data class GroupMask(public val low: Long, public val high: Long) {
    public val isEmpty: Boolean get() = low == 0L && high == 0L
    public val groups: List<Int>
        get() = (0 until Long.SIZE_BITS * 2).filter { bit ->
            val word = if (bit < Long.SIZE_BITS) low else high
            (word ushr (bit % Long.SIZE_BITS)) and 1L != 0L
        }
}

public data class BlockMasks(
    public val anchorConnects: GroupMask,
    public val connectsFromA: GroupMask,
    public val activeA: GroupMask,
    public val connectsFromB: GroupMask,
    public val activeB: GroupMask
) {
    public val inActiveSet: Boolean get() = !activeA.isEmpty || !activeB.isEmpty
    public val connected: Boolean
        get() = !anchorConnects.isEmpty || !connectsFromA.isEmpty || !connectsFromB.isEmpty
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
    public val suppressed: Boolean,
    public val masks: BlockMasks?
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

public object LoadStage {
    public const val Idle: Int = 0
    public const val Loaded: Int = 7

    private val LOADING = setOf(1, 2, 3, 4, 6)
    private val UNLOADING = setOf(5, 8, 9)

    public fun isIdle(stage: Int): Boolean = stage == Idle
    public fun isLoading(stage: Int): Boolean = stage in LOADING
    public fun isResident(stage: Int): Boolean = stage == Loaded
    public fun isUnloading(stage: Int): Boolean = stage in UNLOADING
}
