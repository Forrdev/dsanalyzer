package com.sappyoak.dsanalyzer.runtime.pointers

import com.sappyoak.dsanalyzer.native.memory.Signature

/**
 * A named structure in a running game.
 *
 * Every offset in [offsets] is followed from the address [base] names, arriving at the structure
 * itself. Pointers that share a [base] instance share one scan.
 */
public class GamePointer(
    public val name: String,
    public val base: Signature,
    public val offsets: List<Long>,
    public val lifetime: Lifetime,
    /** Bytes to fetch, which has to cover the last field anything reads out of this */
    public val size: Int
) {
    init {
        require(size > 0) {
            "$name has no size to read as a structure"
        }
    }

    override fun toString(): String = name
}