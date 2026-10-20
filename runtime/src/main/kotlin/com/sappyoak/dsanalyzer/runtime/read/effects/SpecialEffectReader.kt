package com.sappyoak.dsanalyzer.runtime.read.effects

import com.sappyoak.dsanalyzer.native.memory.Address
import com.sappyoak.dsanalyzer.runtime.layout.SpecialEffectLayout
import com.sappyoak.dsanalyzer.runtime.pointers.GameMemory
import com.sappyoak.dsanalyzer.runtime.pointers.StructView
import com.sappyoak.dsanalyzer.runtime.pointers.TableView
import com.sappyoak.dsanalyzer.runtime.session.effects.ActiveEffect
import com.sappyoak.dsanalyzer.runtime.session.effects.SpecialEffectSnapshot
import com.sappyoak.dsanalyzer.runtime.session.effects.StateInfoCounters

internal class SpecialEffectReader(private val layout: SpecialEffectLayout) {
    private val holder = StructView(layout.holderSize)
    private val chain = TableView(layout.chain)
    private val node = StructView(layout.nodeSize)
    private val rows = SpEffectRows()

    fun read(game: GameMemory, at: Address): SpecialEffectSnapshot? {
        if (!holder.refresh(game.memory, at)) return null

        // Taken before the walk so the summary is at least no newer than the list it summarises
        val counters = StateInfoCounters(holder.int(layout.stateInfoCounts))

        rows.beginPass()
        if (!chain.refresh(game.memory, at)) return null

        return SpecialEffectSnapshot(
            effects = chain.elements.mapNotNull { effectAt(game, it) },
            counters = counters,
            movedRows = rows.movedRows.toSet(),
            truncated = chain.truncated
        )
    }

    private fun effectAt(game: GameMemory, at: Address): ActiveEffect? {
        if (!node.refresh(game.memory, at)) return null

        val effectId = node.int(layout.effectId)
        // Reads through its own view, so the node copy this is built from stays intact
        val row = rows.of(game, effectId, node.pointer(layout.paramRow))

        return ActiveEffect(
            effectId = effectId,
            stateInfo = row?.stateInfo,
            flags = node.int(layout.flags),
            excluded = node.flag(layout.flags, layout.excludedMask),
            live = node.flag(layout.flags, layout.liveMask),
            durationRemaining = timed(node.float(layout.remaining)),
            durationTotal = timed(node.float(layout.total)),
            // Absent unless the row says the engine writes it: unwritten, both read 1.0, which is
            // indistinguishable from a real multiplier and would be believed
            magnitude = node.float(layout.magnitude).takeIf { row?.hasMagnitude == true },
            duration = node.float(layout.duration).takeIf { row?.hasDuration == true },
            maxHpRate = row?.maxHpRate,
            unlinkId = row?.unlinkId
        )
    }
}

private fun timed(seconds: Float): Float? = seconds.takeIf { it >= 0f }