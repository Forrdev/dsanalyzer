package com.sappyoak.dsanalyzer.runtime.write

import com.sappyoak.dsanalyzer.runtime.layout.WriteLayout
import com.sappyoak.dsanalyzer.runtime.pointers.GameMemory
import com.sappyoak.dsanalyzer.runtime.session.CheatFlag
import com.sappyoak.dsanalyzer.runtime.session.CheatWord
import com.sappyoak.dsanalyzer.runtime.session.CHEAT_BITS

internal class CheatWriter(private val layout: WriteLayout) {
    fun set(flag: CheatFlag, on: Boolean, memory: GameMemory, writer: GameWriter): Boolean {
       if (flag !in WRITABLE) return false
       val bit = CHEAT_BITS.getValue(flag)
       val field = when (bit.word) {
           CheatWord.ChrFlags1 -> layout.chrFlags1
           CheatWord.ChrFlags2 -> layout.chrFlags2
           CheatWord.MapFlags -> layout.mapFlags
       }
        val address = memory.addressOf(field) ?: return false
        return writer.writeBits(keyOf(flag), address, field.pointer.lifetime, bit.mask, on)
    }

    companion object {
        // Need to fix naming before toggle
        val WRITABLE: Set<CheatFlag> = CheatFlag.entries.toSet() - CheatFlag.DeadModeSet

        fun keyOf(flag: CheatFlag): WriteKey = WriteKey("cheat:${flag.name}")
    }
}
