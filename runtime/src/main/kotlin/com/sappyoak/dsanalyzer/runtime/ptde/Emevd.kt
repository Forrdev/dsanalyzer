package com.sappyoak.dsanalyzer.runtime.ptde

import com.sappyoak.dsanalyzer.native.memory.Signature
import com.sappyoak.dsanalyzer.native.memory.SignatureTarget
import com.sappyoak.dsanalyzer.runtime.pointers.GamePointer
import com.sappyoak.dsanalyzer.runtime.pointers.GameTable
import com.sappyoak.dsanalyzer.runtime.pointers.Lifetime
import com.sappyoak.dsanalyzer.runtime.pointers.TableElements
import com.sappyoak.dsanalyzer.runtime.pointers.TableShape

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
    internal val Base: Signature = Signature.parse(
        name = "EmevdMan",
        pattern = "83 FF FF 73 06 03 F8 03 39 EB 02 33 FF A1 ?? ?? ?? ?? 83 C0 04",
        target = SignatureTarget.Embedded(14)
    )

    public val Pointer: GamePointer = GamePointer(
        name = "EmevdMan",
        base = Base,
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