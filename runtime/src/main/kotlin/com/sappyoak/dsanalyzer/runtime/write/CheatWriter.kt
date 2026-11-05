package com.sappyoak.dsanalyzer.runtime.write

import com.sappyoak.dsanalyzer.runtime.pointers.GameMemory
import com.sappyoak.dsanalyzer.runtime.read.CHEAT_BITS
import com.sappyoak.dsanalyzer.runtime.read.CheatWord
import com.sappyoak.dsanalyzer.runtime.session.CheatFlag

internal class CheatWriter(private val fields: Map<CheatWord, FieldWrite>) {
    fun set(
        flag: CheatFlag,
        on: Boolean,
        memory: GameMemory,
        writer: GameWriter
    ): Boolean {
        if (flag !in WRITABLE) return false
        val bit = CHEAT_BITS.getValue(flag)
        val field = fields[bit.word] ?: return false
        val address = field.addressIn(memory) ?: return false
        return writer.writeBits(keyOf(flag), address, field.lifetime, bit.mask, on)
    }

    companion object {
        // Need to fix naming before toggle
        val WRITABLE: Set<CheatFlag> = CheatFlag.entries.toSet() - CheatFlag.DeadModeSet

        fun keyOf(flag: CheatFlag): WriteKey = WriteKey("cheat:${flag.name}")
    }
}
