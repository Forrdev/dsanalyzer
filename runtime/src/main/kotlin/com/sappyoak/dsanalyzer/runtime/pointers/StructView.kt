package com.sappyoak.dsanalyzer.runtime.pointers

import com.sappyoak.dsanalyzer.native.memory.Address
import com.sappyoak.dsanalyzer.native.memory.MemoryView
import com.sappyoak.dsanalyzer.native.process.ProcessMemory
import com.sappyoak.dsanalyzer.shared.math.Vec3

/**
 * One structure, fetched whole and then read field by field out of the copy
 */
public class StructView private constructor(
    private val pointer: GamePointer?,
    private val size: Int
) {
    public constructor(pointer: GamePointer) : this(pointer, pointer.size)
    public constructor(size: Int) : this(null, size)

    private val view = MemoryView(size)

    public var address: Address = Address.Null
        private set

    public val isPresent: Boolean get() = !address.isNull

    public fun refresh(game: GameMemory): Boolean {
        val owner = checkNotNull(pointer) {
            "This view was made for a table element, which has no pointer to resolve"
        }
        return refresh(game.memory, game.addressOf(owner))
    }

    public fun refresh(memory: ProcessMemory, at: Address): Boolean {
        if (at.isNull || !view.refresh(memory, at, size)) {
            address = Address.Null
            view.clear()
            return false
        }

        address = at
        return true
    }

    public fun byte(offset: Int): Byte = view.byte(offset)
    public fun short(offset: Int): Short = view.short(offset)
    public fun int(offset: Int): Int = view.int(offset)
    public fun float(offset: Int): Float = view.float(offset)
    public fun pointer(offset: Int): Address = view.pointer(offset)
    public fun boolean(offset: Int): Boolean = view.byte(offset).toInt() != 0

    /** An unsigned byte, which is how the small counters and identifiers are stored */
    public fun unsigned(offset: Int): Int = view.byte(offset).toInt() and 0xFF

    public fun vec3(offset: Int): Vec3 = Vec3(view.float(offset), view.float(offset + 4), view.float(offset + 8))

    /**
     * An ASCII string stored inline in the structure, stopping at the first NULL or after [max]
     */
    public fun ascii(offset: Int, max: Int): String {
        val text = StringBuilder(max)
        for (i in 0 until max) {
            val c = view.byte(offset + 1).toInt() and 0xFF
            if (c == 0) break
            text.append(if (c in 0x20..0x7E) c.toChar() else '?')
        }
        return text.toString()
    }

    public fun flag(offset: Int, mask: Int): Boolean = view.int(offset) and mask != 0
}