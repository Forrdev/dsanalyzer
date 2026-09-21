package com.sappyoak.dsanalyzer.native.memory

/** A chain of pointers from a known base to a value */
public class PointerChain(
    public val base: Address,
    public val offsets: List<Long>
) {
    public override fun toString(): String =
        "${base}${offsets.joinToString("") { " -> 0x${it.toString(16).uppercase()}" }}"

    public companion object {
        public fun of(base: Address, vararg offsets: Long): PointerChain =
            PointerChain(base, offsets.toList())
    }
}