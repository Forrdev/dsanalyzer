package com.sappyoak.dsanalyzer.runtime.read

import com.sappyoak.dsanalyzer.native.memory.Address
import com.sappyoak.dsanalyzer.runtime.pointers.GameMemory
import com.sappyoak.dsanalyzer.runtime.pointers.StructView
import com.sappyoak.dsanalyzer.runtime.pointers.TableView
import com.sappyoak.dsanalyzer.runtime.ptde.LoadStage
import com.sappyoak.dsanalyzer.runtime.ptde.NO_MAP
import com.sappyoak.dsanalyzer.runtime.ptde.WORLD_BLOCK_INFO_SIZE
import com.sappyoak.dsanalyzer.runtime.ptde.WORLD_BLOCK_RES_SIZE
import com.sappyoak.dsanalyzer.runtime.ptde.WorldBlockInfo
import com.sappyoak.dsanalyzer.runtime.ptde.WorldBlockRes
import com.sappyoak.dsanalyzer.runtime.ptde.WorldRes
import com.sappyoak.dsanalyzer.runtime.session.BlockLoad
import com.sappyoak.dsanalyzer.runtime.session.BlockState
import com.sappyoak.dsanalyzer.runtime.session.HeldBy
import com.sappyoak.dsanalyzer.runtime.session.LoadQueueSnapshot


internal class LoadQueueReader {
    private val worldRes = StructView(WorldRes.Pointer)
    private val blocks = TableView(WorldRes.Blocks)
    private val infos = TableView(WorldRes.BlockInfos)
    private val block = StructView(WORLD_BLOCK_RES_SIZE)
    private val info = StructView(WORLD_BLOCK_INFO_SIZE)

    fun read(game: GameMemory): LoadQueueSnapshot? {
        if (!worldRes.refresh(game)) return null

        val requests = MapRequests(
            current = worldRes.int(WorldRes.CurrentMap),
            previous = worldRes.int(WorldRes.PreviousMap),
            warpTarget = worldRes.int(WorldRes.WarpTarget)
        )
        val mapIds = mapIds(game)

        val found = if (blocks.refresh(game.memory, worldRes.address)) {
            blocks.elements.mapIndexedNotNull { index, at ->
                blockAt(game, index, at, mapIds.getOrElse(index) { NO_MAP }, requests)
            }
        } else {
            emptyList()
        }

        return LoadQueueSnapshot(
            current = WorldRes.mapOf(requests.current),
            previous = WorldRes.mapOf(requests.previous),
            warpTarget = WorldRes.mapOf(requests.warpTarget),
            blocks = found
        )
    }

    private fun mapIds(game: GameMemory): List<Int> {
        if (!infos.refresh(game.memory, worldRes.address)) return emptyList()

        return infos.elements.map { at ->
            if (info.refresh(game.memory, at)) info.int(WorldBlockInfo.PackedMapId) else NO_MAP
        }
    }

    private fun blockAt(
        game: GameMemory,
        index: Int,
        at: Address,
        packedMapId: Int,
        requests: MapRequests
    ): BlockLoad? {
        if (!block.refresh(game.memory, at)) return null

        val stage = block.int(WorldBlockRes.Stage)
        val forced = block.unsigned(WorldBlockRes.ForceLoad) != 0
        val latched = block.unsigned(WorldBlockRes.Latched) != 0
        val pinned = block.unsigned(WorldBlockRes.PinLoaded) != 0 ||
                block.unsigned(WorldBlockRes.PinOther) != 0

        return BlockLoad(
            index = index,
            mapId = WorldRes.mapOf(packedMapId),
            stage = stage,
            state = blockStateOf(stage),
            heldBy = requests.slotFor(packedMapId) ?: when {
                forced -> HeldBy.Forced
                pinned -> HeldBy.Pinned
                latched -> HeldBy.Referenced
                else -> HeldBy.Nothing
            },


            requested = block.unsigned(WorldBlockRes.Requested) != 0,
            latched = latched,
            wanted = block.unsigned(WorldBlockRes.WantLoaded) != 0,
            pinned = pinned,
            suppressed = block.unsigned(WorldBlockRes.Suppress) != 0
        )
    }
}

internal fun blockStateOf(stage: Int): BlockState = when {
    LoadStage.isIdle(stage) -> BlockState.Absent
    LoadStage.isLoading(stage) -> BlockState.Loading
    LoadStage.isResident(stage) -> BlockState.Resident
    LoadStage.isUnloading(stage) -> BlockState.Unloading
    else -> BlockState.Unknown
}

internal data class MapRequests(val current: Int, val previous: Int, val warpTarget: Int) {
    fun slotFor(packedMapId: Int): HeldBy? = when {
        packedMapId == NO_MAP -> null
        packedMapId == warpTarget -> HeldBy.WarpTarget
        packedMapId == current -> HeldBy.CurrentMap
        packedMapId == previous -> HeldBy.PreviousMap
        else -> null
    }
}