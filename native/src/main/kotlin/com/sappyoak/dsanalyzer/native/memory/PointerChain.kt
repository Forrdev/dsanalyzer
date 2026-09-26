package com.sappyoak.dsanalyzer.native.memory

import com.sappyoak.dsanalyzer.native.process.ProcessMemory

/**
 * A walk from a known base to an object, following one pointer per offset
 *
 * Every offset is followed. The walk starts at [base], and each offset
 * is added to the address reached so far before reading the pointer stored there.
 * Where a field sits inside the object the walk arrives at is not part of the chain,
 * so one resolved chain serves every field of that object
 */
public class PointerChain(
    public val base: Address,
    public val offsets: List<Long>
) {
    public fun resolve(memory: ProcessMemory): Address {
        var pointer = base
        for (offset in offsets) {
            if (pointer.isNull) return Address.Null
            pointer = memory.pointerAt(pointer + offset) ?: return Address.Null
        }
        return pointer
    }

    public override fun toString(): String =
        "${base}${offsets.joinToString("") { " -> 0x${it.toString(16).uppercase()}" }}"

    public companion object {
        public fun of(base: Address, vararg offsets: Long): PointerChain =
            PointerChain(base, offsets.toList())
    }
}
