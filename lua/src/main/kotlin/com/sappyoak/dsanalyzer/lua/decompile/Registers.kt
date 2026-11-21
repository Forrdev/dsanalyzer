package com.sappyoak.dsanalyzer.lua.decompile

/**
 * The temporaries waiting to be used, by register. Lua 5.0 fills each temporary once and uses it once,
 * so a value leaves the map when it is read. Where each register was last filled is kept after the value
 * is used, so a value that turns out to have been a local can be placed
 */
internal class Registers private constructor(
    private val slots: MutableMap<Int, Slot>,
    private val filledAt: MutableMap<Int, Int>,
    /** The register of a call left open for all its results, which a following B or C of 0 reaches up to */
    var openTop: Int?
) {
    constructor() : this(HashMap(), HashMap(), null)

    val isEmpty: Boolean get() = slots.isEmpty() && openTop == null

    /** The registers holding a waiting value, lowest first */
    val pending: List<Int> get() = slots.keys.sorted()

    /** The first register above every waiting value, or [floor] when nothing waits above it */
    fun firstFree(floor: Int): Int = maxOf(floor, (slots.keys.maxOrNull() ?: -1) + 1)

    fun put(register: Int, slot: Slot, pc: Int) {
        slots[register] = slot
        filledAt[register] = pc
    }

    fun peek(register: Int): Slot? = slots[register]

    fun take(register: Int): Slot = slots.remove(register) ?: unsupported("r$register is read before it is filled")

    fun takeOrNull(register: Int): Slot? = slots.remove(register)

    /** The pc of the last instruction that filled [register], if any did */
    fun filledAt(register: Int): Int? = filledAt[register]

    /** How many registers straight above [register] hold something */
    fun filledAbove(register: Int): Int {
        var count = 0
        while (slots.containsKey(register + 1 + count)) count++
        return count
    }

    /** The open call's register, which must exist, and closes it */
    fun closeOpenTop(): Int = (openTop ?: unsupported("A B or C of 0 without an open call")).also { openTop = null }

    /** Takes on [other]'s contents, after work done on a copy is kept */
    fun adopt(other: Registers) {
        if (other === this) return
        slots.clear()
        slots.putAll(other.slots)
        filledAt.clear()
        filledAt.putAll(other.filledAt)
        openTop = other.openTop
    }

    fun copy(): Registers = Registers(HashMap(slots), HashMap(filledAt), openTop)

    /** A nested block starts with nothing waiting, but remembers where the enclosing code filled registers */
    fun forBlock(): Registers = Registers(HashMap(), HashMap(filledAt), null)
}
