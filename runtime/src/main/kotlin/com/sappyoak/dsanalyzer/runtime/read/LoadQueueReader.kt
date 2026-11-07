package com.sappyoak.dsanalyzer.runtime.read

import com.sappyoak.dsanalyzer.game.world.maps.MapId
import com.sappyoak.dsanalyzer.native.memory.Address
import com.sappyoak.dsanalyzer.runtime.layout.LoadQueueLayout
import com.sappyoak.dsanalyzer.runtime.pointers.GameMemory
import com.sappyoak.dsanalyzer.runtime.pointers.StructView
import com.sappyoak.dsanalyzer.runtime.pointers.TableView
import com.sappyoak.dsanalyzer.runtime.ptde.LoadStage
import com.sappyoak.dsanalyzer.runtime.ptde.NO_MAP
import com.sappyoak.dsanalyzer.runtime.session.BlockLoad
import com.sappyoak.dsanalyzer.runtime.session.BlockMasks
import com.sappyoak.dsanalyzer.runtime.session.BlockState
import com.sappyoak.dsanalyzer.runtime.session.GroupMask
import com.sappyoak.dsanalyzer.runtime.session.HeldBy
import com.sappyoak.dsanalyzer.runtime.session.LoadQueueSnapshot

private const val NO_MAP: Int = -1

private const val GROUP_MASK_SIZE: Int = 0x10

internal class LoadQueueReader(private val layout: LoadQueueLayout) {
    private val owner = StructView(layout.pointer)
    private val blocks = TableView(layout.blocks)
    private val infos = TableView(layout.blockInfos)
    private val block = StructView(layout.blockSize)
    private val info = StructView(layout.blockInfoSize)

    fun read(game: GameMemory): LoadQueueSnapshot? {
        if (!owner.refresh(game)) return null

        val requests = MapRequests(
            current = owner.int(layout.currentMap),
            previous = owner.int(layout.previousMap),
            warpTarget = owner.int(layout.warpTarget)
        )
        val mapIds = mapIds(game)

        val found = if (blocks.refresh(game.memory, owner.address)) {
            blocks.elements.mapIndexedNotNull { index, at ->
                blockAt(game, index, at, mapIds.getOrElse(index) { NO_MAP }, requests)
            }
        } else {
            emptyList()
        }

        return LoadQueueSnapshot(
            current = mapOf(requests.current),
            previous = mapOf(requests.previous),
            warpTarget = mapOf(requests.warpTarget),
            blocks = found
        )
    }

    private fun mapIds(game: GameMemory): List<Int> {
        if (!infos.refresh(game.memory, owner.address)) return emptyList()

        return infos.elements.map { at ->
            if (info.refresh(game.memory, at)) info.int(layout.packedMapId) else NO_MAP
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

        val stage = block.int(layout.stage)
        val forced = block.unsigned(layout.forceLoad) != 0
        val latched = block.unsigned(layout.latched) != 0
        val pinned = pinnedIn(block)
        val masks = masksIn(block)

        return BlockLoad(
            index = index,
            mapId = mapOf(packedMapId),
            stage = stage,
            state = blockStateOf(stage),
            heldBy = requests.slotFor(packedMapId) ?: when {
                forced -> HeldBy.Forced
                pinned -> HeldBy.Pinned
                latched -> HeldBy.Referenced
                else -> HeldBy.Nothing
            },
            requested = block.unsigned(layout.requested) != 0,
            latched = latched,
            wanted = block.unsigned(layout.wantLoaded) != 0,
            pinned = pinned,
            suppressed = block.unsigned(layout.suppress) != 0,
            masks = masks
        )
    }

    private fun masksIn(view: StructView): BlockMasks? {
        val first = layout.groupMasks ?: return null
        val mask = { slot: Int -> maskAt(view, first + slot * GROUP_MASK_SIZE) }
        return BlockMasks(mask(0), mask(1), mask(2), mask(3), mask(4))
    }

    private fun maskAt(view: StructView, at: Int): GroupMask = GroupMask(
        low = dwords(view.int(at), view.int(at + 4)),
        high = dwords(view.int(at + 8), view.int(at + 12))
    )

    private fun dwords(low: Int, high: Int): Long = (low.toLong() and 0xFFFFFFFFL) or (high.toLong() shl 32)

    /** False where the edition's pin offsets are unmeasured, which understates rather than invents */
    private fun pinnedIn(view: StructView): Boolean {
        val loaded = layout.pinLoaded?.let { view.unsigned(it) != 0 } ?: false
        val other = layout.pinOther?.let { view.unsigned(it) != 0 } ?: false
        return loaded || other
    }

    /**
     * Both editions pack the four bytes as `dd`, `cc`, block, area from low to high, so the dword is
     * read apart rather than compared whole
     */
    private fun mapOf(slot: Int): MapId? {
        if (slot == NO_MAP) return null
        return MapId.of(area = (slot ushr 24) and 0xFF, block = (slot ushr 16) and 0xFF)
    }
}

internal fun blockStateOf(stage: Int): BlockState = when {
    LoadStage.isIdle(stage) -> BlockState.Absent
    LoadStage.isLoading(stage) -> BlockState.Loading
    LoadStage.isResident(stage) -> BlockState.Resident
    LoadStage.isUnloading(stage) -> BlockState.Unloading
    else -> BlockState.Unknown
}
internal fun holderOf(
    slot: HeldBy?,
    forced: Boolean,
    pinned: Boolean,
    masks: BlockMasks?,
    latched: Boolean
): HeldBy = slot ?: when {
    forced -> HeldBy.Forced
    pinned -> HeldBy.Pinned
    masks?.inActiveSet == true -> HeldBy.ActiveGroups
    masks?.connected == true -> HeldBy.Connected
    latched -> HeldBy.Referenced
    else -> HeldBy.Nothing
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
