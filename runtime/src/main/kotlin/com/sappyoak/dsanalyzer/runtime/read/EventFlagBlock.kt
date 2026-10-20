package com.sappyoak.dsanalyzer.runtime.read

import com.sappyoak.dsanalyzer.runtime.pointers.GameMemory
import com.sappyoak.dsanalyzer.runtime.pointers.StructView
import com.sappyoak.dsanalyzer.runtime.ptde.EventFlags
import com.sappyoak.dsanalyzer.runtime.session.FlagChange

/** The high bit of a word, which is where a flag's bit zero sits */
private const val HIGH_BIT: Int = Int.MIN_VALUE

public class EventFlagBlock {
    private val view = StructView(EventFlags.Pointer)
    private val words = IntArray(EventFlags.Pointer.size / Int.SIZE_BYTES)
    private var seeded = false

    public val isPresent: Boolean get() = view.isPresent

    public fun refresh(game: GameMemory): List<FlagChange> {
        if (!view.refresh(game)) {
            seeded = false
            return emptyList()
        }

        return if (seeded) compare() else record()
    }

    public fun isSet(flagId: Int): Boolean? {
        if (!view.isPresent) return null
        val at = EventFlags.locate(flagId) ?: return null
        return view.int(at.byteOffset).toUInt() and at.mask != 0u
    }

    public fun reset() {
        seeded = false
    }

    private fun record(): List<FlagChange> {
        for (word in words.indices) {
            words[word] = view.int(word * Int.SIZE_BYTES)
        }
        seeded = true
        return emptyList()
    }

    private fun compare(): List<FlagChange> {
        val changes = mutableListOf<FlagChange>()

        for (word in words.indices) {
            val offset = word * Int.SIZE_BYTES
            val now = view.int(offset)
            if (now == words[word]) continue

            var differing = now xor words[word]
            words[word] = now

            while (differing != 0) {
                val bit = Integer.numberOfLeadingZeros(differing)
                val mask = HIGH_BIT ushr bit
                EventFlags.flagAt(offset, bit)?.let { changes += FlagChange(it, now and mask != 0) }
                differing = differing and mask.inv()
            }
        }

        return changes
    }
}