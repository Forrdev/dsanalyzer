package com.sappyoak.dsanalyzer.runtime.write.hpbar

import com.sappyoak.dsanalyzer.native.memory.Address
import com.sappyoak.dsanalyzer.runtime.layout.HpBarLayout
import com.sappyoak.dsanalyzer.runtime.pointers.GameMemory
import com.sappyoak.dsanalyzer.runtime.pointers.Lifetime
import com.sappyoak.dsanalyzer.runtime.pointers.StructView
import com.sappyoak.dsanalyzer.runtime.write.GameMutation
import com.sappyoak.dsanalyzer.runtime.write.GameWriter
import com.sappyoak.dsanalyzer.runtime.write.WriteKey
import com.sappyoak.dsanalyzer.shared.binary.toLittleEndian

private const val FREE = -1
private const val VEC3_BYTES = 3 * Float.SIZE_BYTES
private const val TIMER_BYTES = 2 * Float.SIZE_BYTES

private val KEY = WriteKey("hpbar:hidden")

/**
 * Keeps the game's floating HP bar off one character. Its 'DisableHpGauge' bit stops the game filling a
 * slot for it, and a slot already showing it is closed the way the bar's own dialog closes one; freeing
 * only the handle would leave the bar frozen on screen. The bit is journaled, so a detach puts it back
 */
internal class HpBarHider(private val layout: HpBarLayout) {
    private val bars = StructView(layout.enemies)
    private var hidden: Int? = null

    /** Hides the bar of the character at [character] with [handle], showing again any hidden before */
    fun hide(handle: Int?, character: Address?, game: GameMemory, writer: GameWriter) {
        if (handle != hidden) show(writer)
        if (handle == null || character == null) return

        val flag = character + layout.hideFlag.toLong()
        if (!writer.writeBits(KEY, flag, Lifetime.World, layout.hideMask, on = true)) return
        hidden = handle
        closeSlotsOf(handle, game, writer)
    }

    fun show(writer: GameWriter) {
        writer.revert(KEY)
        hidden = null
    }

    /** For a load: the character the bit was set on is gone, and its address may hold anything now */
    fun forget(writer: GameWriter) {
        writer.forget(KEY)
        hidden = null
    }

    @OptIn(GameMutation::class)
    private fun closeSlotsOf(handle: Int, game: GameMemory, writer: GameWriter) {
        if (!bars.refresh(game)) return
        val bar = layout.enemy

        for (index in 0 until bar.count) {
            val offset = index * bar.size
            if (bars.int(offset + bar.handle) != handle) continue

            val slot = bars.address + offset.toLong()
            writer.perform(slot + bar.handle.toLong(), FREE.toLittleEndian())
            writer.perform(slot + bar.screen.toLong(), ByteArray(VEC3_BYTES))
            writer.perform(slot + bar.shown.toLong(), ByteArray(1))
            writer.perform(slot + bar.damage.toLong(), FREE.toLittleEndian())
            writer.perform(slot + bar.timers.toLong(), ByteArray(TIMER_BYTES))
            writer.perform(slot + bar.visible.toLong(), ByteArray(1))
        }
    }
}
