package com.sappyoak.dsanalyzer.runtime.write.debugdraw

import com.sappyoak.dsanalyzer.native.memory.Address
import com.sappyoak.dsanalyzer.native.process.ProcessMemory
import com.sappyoak.dsanalyzer.runtime.layout.DebugDrawLayout
import com.sappyoak.dsanalyzer.runtime.pointers.StructView
import com.sappyoak.dsanalyzer.runtime.read.debugdraw.HitInsWalk
import com.sappyoak.dsanalyzer.runtime.read.debugdraw.MsbHitFilter
import com.sappyoak.dsanalyzer.runtime.write.GameMutation
import com.sappyoak.dsanalyzer.runtime.write.GameWriter

/** Bit 7 of the tag: the tool has given this part its filter */
internal const val TAGGED: Int = 0x80
internal const val FILTER_BITS: Int = 0x1F

internal class CollisionTags(private val draw: DebugDrawLayout) {
    private val layout = draw.hitIns
    private val walk = HitInsWalk(layout)
    private val instance = StructView(maxOf(layout.partIndex, layout.msb, layout.tag) + Long.SIZE_BYTES)

    @GameMutation
    fun tag(memory: ProcessMemory, writer: GameWriter, manager: Address): Int {
        val lookup = MsbHitFilter(draw.partFilters)
        return walk.instancesOf(memory, manager).count { at ->
            if (!instance.refresh(memory, at)) return@count false
            val filter = lookup.filterOf(memory, instance.pointer(layout.msb), instance.int(layout.partIndex) and 0xFFFF)
            filter != null && filter <= FILTER_BITS && write(writer, at, TAGGED or filter)
        }
    }

    @GameMutation
    fun clear(memory: ProcessMemory, writer: GameWriter, manager: Address): Int =
        walk.instancesOf(memory, manager).count { at -> instance.refresh(memory, at) && write(writer, at, 0) }

    @GameMutation
    private fun write(writer: GameWriter, at: Address, value: Int): Boolean =
        instance.unsigned(layout.tag) == value || writer.perform(at + layout.tag.toLong(), byteArrayOf(value.toByte()))
}
