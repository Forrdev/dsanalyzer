package com.sappyoak.dsanalyzer.runtime.ptde.read

import com.sappyoak.dsanalyzer.runtime.pointers.GameMemory
import com.sappyoak.dsanalyzer.runtime.pointers.StructView
import com.sappyoak.dsanalyzer.runtime.pointers.TableView
import com.sappyoak.dsanalyzer.runtime.ptde.WORLD_BLOCK_RES_SIZE
import com.sappyoak.dsanalyzer.runtime.ptde.WorldBlockRes
import com.sappyoak.dsanalyzer.runtime.ptde.WorldRes
import com.sappyoak.dsanalyzer.runtime.session.BlockLoad
import com.sappyoak.dsanalyzer.runtime.session.LoadQueueSnapshot

internal class LoadQueueReader {
    private val worldRes = StructView(WorldRes.Pointer)
    private val blocks = TableView(WorldRes.Blocks)
    private val block = StructView(WORLD_BLOCK_RES_SIZE)

    fun read(game: GameMemory): LoadQueueSnapshot? {
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

    private fun blockAt(game: GameMemory, index: Int, at: com.sappyoak.dsanalyzer.native.memory.Address): BlockLoad? {
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