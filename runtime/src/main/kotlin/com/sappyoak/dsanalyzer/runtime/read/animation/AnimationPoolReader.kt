package com.sappyoak.dsanalyzer.runtime.read.animation

import com.sappyoak.dsanalyzer.native.memory.Address
import com.sappyoak.dsanalyzer.native.process.ProcessMemory
import com.sappyoak.dsanalyzer.runtime.layout.AnimationLayout
import com.sappyoak.dsanalyzer.runtime.pointers.StructView
import com.sappyoak.dsanalyzer.runtime.session.animation.AnimControlSnapshot
import com.sappyoak.dsanalyzer.runtime.session.animation.AnimHandle
import com.sappyoak.dsanalyzer.runtime.session.animation.AnimSlotSnapshot
import com.sappyoak.dsanalyzer.runtime.session.animation.SlotState

internal class AnimationPool(val playSpeed: Float, val slots: List<AnimSlotSnapshot>)

/**
 * Reads a slot pool: its header, the slot array in one read, then the control of each occupied slot
 */
internal class AnimationPoolReader(private val layout: AnimationLayout) {
    private val header = StructView(
        maxOf(layout.slotCountField, layout.slotsPointer, layout.playSpeed) + Long.SIZE_BYTES
    )
    private val slots = StructView(layout.slotCapacity * layout.slotStride)
    private val control = StructView(layout.control.run {
        maxOf(localTime, masterWeight, weightScale, playbackSpeed, cycles, maxCycles)
    } + Int.SIZE_BYTES)

    fun read(memory: ProcessMemory, at: Address): AnimationPool? {
        if (!header.refresh(memory, at)) return null
        // A count other than the pool's fixed size means this is not a pool, or not one any more
        if (header.int(layout.slotCountField) != layout.slotCapacity) return null
        if (!slots.refresh(memory, header.pointer(layout.slotsPointer))) return null

        val read = List(layout.slotCapacity) { slotAt(memory, it, at) }
        if (read.any { it == null }) return null
        return AnimationPool(header.float(layout.playSpeed), read.filterNotNull())
    }

    /** Null when the slot does not point back at [pool], which means the array is not this pool's */
    private fun slotAt(memory: ProcessMemory, index: Int, pool: Address): AnimSlotSnapshot? {
        val base = index * layout.slotStride
        val slot = layout.slot
        if (slots.pointer(base + slot.manager) != pool) return null

        val state = SlotState.of(slots.short(base + slot.state).toInt())
        return AnimSlotSnapshot(
            index = index,
            handle = AnimHandle(slots.int(base + slot.handle)),
            state = state,
            priority = slots.int(base + slot.priority),
            elapsed = slots.float(base + slot.elapsed),
            fadeFrom = AnimHandle(slots.int(base + slot.fadeFrom)),
            fadeDuration = slots.float(base + slot.fadeDuration),
            fadeRemaining = slots.float(base + slot.fadeRemaining),
            control = if (state == SlotState.Free) null else controlAt(memory, slots.pointer(base + slot.control))
        )
    }

    private fun controlAt(memory: ProcessMemory, at: Address): AnimControlSnapshot? {
        if (!control.refresh(memory, at)) return null
        val fields = layout.control
        return AnimControlSnapshot(
            localTime = control.float(fields.localTime),
            masterWeight = control.float(fields.masterWeight),
            weightScale = control.float(fields.weightScale),
            playbackSpeed = control.float(fields.playbackSpeed),
            cycles = control.int(fields.cycles),
            maxCycles = control.int(fields.maxCycles)
        )
    }
}
