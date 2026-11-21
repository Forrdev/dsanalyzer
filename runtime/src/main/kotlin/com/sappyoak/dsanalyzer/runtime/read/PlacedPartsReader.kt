package com.sappyoak.dsanalyzer.runtime.read

import com.sappyoak.dsanalyzer.native.memory.Address
import com.sappyoak.dsanalyzer.runtime.layout.PlacedPartsLayout
import com.sappyoak.dsanalyzer.runtime.pointers.GameMemory
import com.sappyoak.dsanalyzer.runtime.pointers.StructView
import com.sappyoak.dsanalyzer.runtime.pointers.TableView
import com.sappyoak.dsanalyzer.runtime.read.ai.POINTER_ROOM
import com.sappyoak.dsanalyzer.runtime.snapshot.BONFIRE_PART_PREFIX
import com.sappyoak.dsanalyzer.runtime.snapshot.BlockRoster
import com.sappyoak.dsanalyzer.runtime.snapshot.PlacedEnemy

private const val NAME_LIMIT = 32

internal class PlacedPartsReader(private val layout: PlacedPartsLayout) {
    private val manager = StructView(layout.manager)
    private val blocks = TableView(layout.blocks)
    private val block = StructView(layout.blockSize)
    private val mapData = StructView(layout.partsList + POINTER_ROOM)
    private val parts = TableView(layout.parts)
    private val part = StructView(layout.partSize)

    fun read(game: GameMemory): List<BlockRoster> {
        if (!manager.refresh(game)) return emptyList()
        if (!blocks.refresh(game.memory, manager.address)) return emptyList()

        return blocks.elements.mapIndexedNotNull { index, at -> rosterAt(game, index, at) }
    }

    private fun rosterAt(game: GameMemory, index: Int, at: Address): BlockRoster? {
        if (!block.refresh(game.memory, at)) return null

        // Null here is the test for "this block is not loaded", and most of the seventeen are not
        val msb = block.pointer(layout.mapData)
        if (msb.isNull || !mapData.refresh(game.memory, msb)) return null

        // No null test of its own: refreshing a table against a null owner already fails, and the
        // count this would have probed is the first thing that refresh reads
        if (!parts.refresh(game.memory, mapData.pointer(layout.partsList))) return null

        val enemies = parts.elements.mapIndexedNotNull { slot, address -> enemyAt(game, slot, address) }
        return BlockRoster(blockIndex = index, partCount = parts.length, enemies = enemies)
    }

    private fun enemyAt(game: GameMemory, slot: Int, at: Address): PlacedEnemy? {
        if (!part.refresh(game.memory, at)) return null
        if (part.int(layout.type) != layout.enemyType) return null

        val name = part.ascii(layout.name, NAME_LIMIT)
        if (name.startsWith(BONFIRE_PART_PREFIX)) return null

        return PlacedEnemy(
            index = slot,
            name = name,
            entityId = part.int(layout.entityId),
            position = part.vec3(layout.position),
            npcParamId = part.int(layout.npcParamId),
            thinkParamId = part.int(layout.thinkParamId)
        )
    }
}
