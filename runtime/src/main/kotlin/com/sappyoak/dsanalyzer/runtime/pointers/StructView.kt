package com.sappyoak.dsanalyzer.runtime.pointers

import com.sappyoak.dsanalyzer.native.memory.Address
import com.sappyoak.dsanalyzer.native.memory.MemoryView
import com.sappyoak.dsanalyzer.shared.math.Vec3

/**
 * One structure, fetched whole and then read field by field out of the copy
 */
public class StructView(public val pointer: GamePointer) {
    private val view = MemoryView(pointer.size)

    public var address: Address = Address.Null
        private set

    public val isPresent: Boolean get() = !address.isNull

    public fun refresh(game: GameMemory): Boolean {
        val at = game.addressOf(pointer)
        if (at.isNull || !view.refresh(game.memory, at, pointer.size)) {
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

    public fun flag(offset: Int, mask: Int): Boolean = view.int(offset) and mask != 0
}