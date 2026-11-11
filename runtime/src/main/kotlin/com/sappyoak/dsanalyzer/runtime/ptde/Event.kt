package com.sappyoak.dsanalyzer.runtime.ptde

import com.sappyoak.dsanalyzer.runtime.pointers.GameMemory
import com.sappyoak.dsanalyzer.runtime.pointers.GamePointer
import com.sappyoak.dsanalyzer.runtime.pointers.GameTable
import com.sappyoak.dsanalyzer.runtime.pointers.Lifetime
import com.sappyoak.dsanalyzer.runtime.pointers.StructView
import com.sappyoak.dsanalyzer.runtime.pointers.TableElements
import com.sappyoak.dsanalyzer.runtime.pointers.TableShape
import com.sappyoak.dsanalyzer.runtime.session.FlagChange

/**
 * 'NS_FRPG::EmevdMan' which holds one event script slot per map block
 *
 * In addition to events, a non-null slot is a **Second independent statement**
 * that a block is loaded, arrived at through a different manager, a different allocation
 * and a different file. Agreement with [WorldBlock.MapDataPointer] is far stronger
 * evidence than either alone, and disagreement would be interesting on its own, a block
 * whose MSB is resident but whose events are not is a state worth noticing
 */
public object EmevdMan {
    public val Pointer: GamePointer = GamePointer(
        name = "EmevdMan",
        base = PTDESignatures.EmevdMan,
        offsets = listOf(0L),
        lifetime = Lifetime.Session,
        size = 0x1C
    )

    /**
     * One 'NS_FRPG::EmevdResCap' per block, null where that block's script is not resident.
     *
     * The constructor allocates exactly '0x44' bytes for [SlotCount] of '17'
     */
    public val Scripts: GameTable = GameTable(
        name = "EmevdScripts",
        shape = TableShape.Counted(lengthAt = SlotCount, firstAt = SlotsPointer, indirect = true),
        elements = TableElements.Pointers,
        limit = 64
    )

    public const val SlotCount: Int = 0x08
    public const val SlotsPointer: Int = 0x0C
}

/** The high bit of a word, which is where a flag's bit zero sits */
private const val HIGH_BIT: Int = Int.MIN_VALUE

/**
 * The whole flag block, fetched as one and compared against the previous fetch
 */
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

    /** Where [flagId] is set, or null when the block is absent or those digits name no flag */
    public fun isSet(flagId: Int): Boolean? {
        if (!view.isPresent) return null
        val at = EventFlags.locate(flagId) ?: return null
        return view.int(at.byteOffset).toUInt() and at.mask != 0u
    }

    /** Forgets the recorded block, so the next fetch records instead of reporting every flag */
    public fun reset() {
        seeded = false
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
                EventFlags.flagAt(offset, bit)?.let { changes.add(FlagChange(it, now and mask != 0)) }
                differing = differing and mask.inv()
            }
        }

        return changes
    }

    private fun record(): List<FlagChange> {
        for (word in words.indices) {
            words[word] = view.int(word * Int.SIZE_BYTES)
        }
        seeded = true
        return emptyList()
    }
}
