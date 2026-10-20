package com.sappyoak.dsanalyzer.runtime.read

import com.sappyoak.dsanalyzer.native.memory.Address
import com.sappyoak.dsanalyzer.runtime.pointers.GameMemory
import com.sappyoak.dsanalyzer.runtime.pointers.StructView
import com.sappyoak.dsanalyzer.runtime.pointers.TableView
import com.sappyoak.dsanalyzer.runtime.ptde.MAP_PART_SIZE
import com.sappyoak.dsanalyzer.runtime.ptde.MapPart
import com.sappyoak.dsanalyzer.runtime.ptde.MapPartType
import com.sappyoak.dsanalyzer.runtime.ptde.MapParts
import com.sappyoak.dsanalyzer.runtime.ptde.MsbResCap
import com.sappyoak.dsanalyzer.runtime.ptde.WORLD_BLOCK_SIZE
import com.sappyoak.dsanalyzer.runtime.ptde.WorldBlock
import com.sappyoak.dsanalyzer.runtime.ptde.WorldChrMan
import com.sappyoak.dsanalyzer.runtime.session.BlockRoster
import com.sappyoak.dsanalyzer.runtime.session.PlacedEnemy

private const val NAME_LIMIT = 32

internal class PlacedPartsReader {
    private val manager = StructView(WorldChrMan.Pointer)
    private val blocks = TableView(WorldChrMan.Blocks)
    private val block = StructView(WORLD_BLOCK_SIZE)
    private val mapData = StructView(MsbResCap.PartsList + Int.SIZE_BYTES)
    private val parts = TableView(MapParts.All)
    private val part = StructView(MAP_PART_SIZE)

    fun read(game: GameMemory): List<BlockRoster> {
        if (!manager.refresh(game)) return emptyList()
        if (!blocks.refresh(game.memory, manager.address)) return emptyList()

        return blocks.elements.mapIndexedNotNull { index, at -> rosterAt(game, index, at) }
    }

    private fun rosterAt(game: GameMemory, index: Int, at: Address): BlockRoster? {
        if (!block.refresh(game.memory, at)) return null

        // Null here is the test for "this block is not loaded", and most of the seventeen are not
        val msb = block.pointer(WorldBlock.MapDataPointer)
        if (msb.isNull || !mapData.refresh(game.memory, msb)) return null

        // No null test of its own: refreshing a table against a null owner already fails, and the
        // count this would have probed is the first thing that refresh reads
        if (!parts.refresh(game.memory, mapData.pointer(MsbResCap.PartsList))) return null

        val enemies = parts.elements.mapIndexedNotNull { slot, address -> enemyAt(game, slot, address) }
        return BlockRoster(blockIndex = index, partCount = parts.length, enemies = enemies)
    }

    private fun enemyAt(game: GameMemory, slot: Int, at: Address): PlacedEnemy? {
        if (!part.refresh(game.memory, at)) return null
        if (part.int(MapPart.Type) != MapPartType.Enemy) return null

        return PlacedEnemy(
            index = slot,
            name = part.ascii(MapPart.Name, NAME_LIMIT),
            entityId = part.int(MapPart.EntityId),
            position = part.vec3(MapPart.Position),
            npcParamId = part.int(MapPart.NpcParamId),
            thinkParamId = part.int(MapPart.ThinkParamId)
        )
    }
}