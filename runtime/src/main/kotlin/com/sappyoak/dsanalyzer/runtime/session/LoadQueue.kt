package com.sappyoak.dsanalyzer.runtime.session

import com.sappyoak.dsanalyzer.game.world.maps.MapId
import com.sappyoak.dsanalyzer.native.memory.Address
import com.sappyoak.dsanalyzer.runtime.pointers.GameMemory
import com.sappyoak.dsanalyzer.runtime.pointers.StructView
import com.sappyoak.dsanalyzer.runtime.pointers.TableView
import com.sappyoak.dsanalyzer.runtime.ptde.LoadStage
import com.sappyoak.dsanalyzer.runtime.ptde.WORLD_BLOCK_RES_SIZE
import com.sappyoak.dsanalyzer.runtime.ptde.WorldBlockRes
import com.sappyoak.dsanalyzer.runtime.ptde.WorldRes

/** One map block's load state */
public data class BlockLoad(
    public val index: Int,
    public val stage: Int,
    /** The block was matched against a requested map this tick, or force-loaded */
    public val requested: Boolean,
    /** The request survived its gates, the block genuinely should be resident */
    public val wanted: Boolean,
    /** Something is waiting on this block, which holds it against unloading */
    public val pinned: Boolean
) {
    public val loaded: Boolean get() = stage == LoadStage.Loaded
    public val pending: Boolean get() = LoadStage.isPending(stage)

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

internal class LoadQueueReader {
    private val worldRes = StructView(WorldRes.Pointer)
    private val blocks = TableView(WorldRes.Blocks)
    private val block = StructView(WORLD_BLOCK_RES_SIZE)

    public fun read(game: GameMemory): LoadQueueSnapshot? {
        if (!worldRes.refresh(game)) return null

        val found = if (blocks.refresh(game.memory, worldRes.address)) {
            blocks.elements.mapIndexedNotNull { index, at -> blockAt(game, index, at) }
        } else {
            emptyList()
        }

        return LoadQueueSnapshot(
            current = WorldRes.mapOf(worldRes.int(WorldRes.CurrentMap)),
            previous = WorldRes.mapOf(worldRes.int(WorldRes.PreviousMap)),
            warpTarget = WorldRes.mapOf(worldRes.int(WorldRes.WarpTarget)),
            blocks = found
        )
    }

    private fun blockAt(game: GameMemory, index: Int, at: Address): BlockLoad? {
        if (!block.refresh(game.memory, at)) return null

        return BlockLoad(
            index = index,
            stage = block.int(WorldBlockRes.Stage),
            requested = block.unsigned(WorldBlockRes.Requested) != 0 ||
                block.unsigned(WorldBlockRes.ForceLoad) != 0,
            wanted = block.unsigned(WorldBlockRes.WantLoaded) != 0,
            pinned = block.unsigned(WorldBlockRes.PinLoaded) != 0
        )
    }
}