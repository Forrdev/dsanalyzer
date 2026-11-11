package com.sappyoak.dsanalyzer.runtime.read.debugdraw

import com.sappyoak.dsanalyzer.native.memory.Address
import com.sappyoak.dsanalyzer.native.process.ProcessMemory
import com.sappyoak.dsanalyzer.runtime.layout.PartFilterLayout
import com.sappyoak.dsanalyzer.runtime.pointers.StructView
import com.sappyoak.dsanalyzer.runtime.pointers.TableView

/**
 * Each collision part's hit filter, by its index among its MSB's collision parts. One instance
 * serves one tagging pass: a reload can put another MSB at a freed address
 */
internal class MsbHitFilter(private val layout: PartFilterLayout) {
    private val parts = TableView(layout.parts)
    private val list = StructView(layout.partsList + Long.SIZE_BYTES)
    private val part = StructView(maxOf(layout.type, layout.indexInType, layout.typeParams) + Long.SIZE_BYTES)
    private val filters = HashMap<Address, Map<Int, Int>>()

    fun filterOf(memory: ProcessMemory, msb: Address, index: Int): Int? =
        filters.getOrPut(msb) { read(memory, msb) }[index]

    private fun read(memory: ProcessMemory, msb: Address): Map<Int, Int> {
        if (msb.isNull || !list.refresh(memory, msb)) return emptyMap()
        if (!parts.refresh(memory, list.pointer(layout.partsList))) return emptyMap()

        val found = HashMap<Int, Int>()
        for (at in parts.elements) {
            if (!part.refresh(memory, at) || part.int(layout.type) != layout.collisionType) continue
            val params = if (layout.relative) at + part.int(layout.typeParams).toLong() else part.pointer(layout.typeParams)
            val filter = memory.read(params, 1) ?: continue
            found[part.int(layout.indexInType)] = filter[0].toInt() and 0xFF
        }
        return found
    }
}

