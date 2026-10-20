package com.sappyoak.dsanalyzer.runtime.ptde.read.collision

import com.sappyoak.dsanalyzer.native.memory.Address
import com.sappyoak.dsanalyzer.runtime.pointers.GameMemory
import com.sappyoak.dsanalyzer.runtime.pointers.StructView
import com.sappyoak.dsanalyzer.runtime.pointers.TableView
import com.sappyoak.dsanalyzer.runtime.ptde.HitPartsParam
import com.sappyoak.dsanalyzer.runtime.ptde.MAP_PART_SIZE
import com.sappyoak.dsanalyzer.runtime.ptde.MapPart
import com.sappyoak.dsanalyzer.runtime.ptde.MapPartType
import com.sappyoak.dsanalyzer.runtime.ptde.MapParts
import com.sappyoak.dsanalyzer.runtime.ptde.MsbResCap
import com.sappyoak.dsanalyzer.runtime.ptde.WORLD_BLOCK_SIZE
import com.sappyoak.dsanalyzer.runtime.ptde.WorldBlock
import com.sappyoak.dsanalyzer.runtime.ptde.WorldChrMan
import com.sappyoak.dsanalyzer.runtime.session.collision.HitFilter

private const val NAME_LIMIT = 32

internal class HitFilterReader {
    private val manager = StructView(WorldChrMan.Pointer)
    private val blocks = TableView(WorldChrMan.Blocks)
    private val block = StructView(WORLD_BLOCK_SIZE)
    private val mapData = StructView(MsbResCap.PartsList + Int.SIZE_BYTES)
    private val parts = TableView(MapParts.All)
    private val part = StructView(MAP_PART_SIZE)
    private val params = StructView(PARAMS_SIZE)

    fun read(game: GameMemory): Map<String, HitFilter> {
        if (!manager.refresh(game)) return emptyMap()
        if (!blocks.refresh(game.memory, manager.address)) return emptyMap()

        // Every id each base name was seen with. One entry is a clean answer; more than one is the
        // sibling case, and collecting rather than overwriting is what makes that visible
        val seen = mutableMapOf<String, MutableSet<Int>>()
        for (at in blocks.elements) {
            collect(game, at, seen)
        }

        return seen.mapValues { (_, ids) -> hitFilterOf(ids) }
    }

    private fun collect(game: GameMemory, at: Address, into: MutableMap<String, MutableSet<Int>>) {
        if (!block.refresh(game.memory, at)) return

        // Null is the test for "this block is not loaded", and most of the seventeen are not
        val msb = block.pointer(WorldBlock.MapDataPointer)
        if (msb.isNull || !mapData.refresh(game.memory, msb)) return
        if (!parts.refresh(game.memory, mapData.pointer(MsbResCap.PartsList))) return

        for (address in parts.elements) {
            if (!part.refresh(game.memory, address)) continue
            if (part.int(MapPart.Type) != MapPartType.Collision) continue

            val name = hitFilterKeyOf(part.ascii(MapPart.Name, NAME_LIMIT))
            if (name.isEmpty()) continue

            val block = part.pointer(MapPart.TypeParams)
            if (block.isNull || !params.refresh(game.memory, block)) continue

            into.getOrPut(name) { mutableSetOf() } += params.unsigned(HitPartsParam.HitFilterId)
        }
    }

    private companion object {
        const val PARAMS_SIZE: Int = HitPartsParam.ReflectPlaneHeight + Int.SIZE_BYTES
    }
}