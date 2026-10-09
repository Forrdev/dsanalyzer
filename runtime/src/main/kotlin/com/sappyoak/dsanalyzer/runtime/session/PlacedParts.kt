package com.sappyoak.dsanalyzer.runtime.session

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
import com.sappyoak.dsanalyzer.shared.math.Vec3

/** The longest MSB part name seen is well under this; it bounds a read, not the format */
private const val NAME_LIMIT = 32

/**
 * An enemy a loaded block places, whether or not the gme has built a character out of it yet
 */
public data class PlacedEnemy(
    public val index: Int,
    /** The MSB part name */
    public val name: String,
    /** The id EMEVD references. Divided by 10000 it gives the area and block that own it */
    public val entityId: Int,
    public val position: Vec3,
    public val npcParamId: Int,
    /** Which NpcThinkParam row drives this enemy's AI */
    public val thinkParamId: Int
)

/** Every enemy one loaded block places, with how many parts were walked to find them */
public data class BlockRoster(
    public val blockIndex: Int,
    public val partCount: Int,
    public val enemies: List<PlacedEnemy>
)

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

        // Null here is the test for "this block is not loaded"
        val msb = block.pointer(WorldBlock.MapDataPointer)
        if (msb.isNull || !mapData.refresh(game.memory, msb)) return null

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