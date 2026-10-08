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
    public val size: Int,
    /**
     * For a structure that nothing points at. Some fields sit at fixed displacements inside one pooled region,
     * and no field anywhere holds their addresses, the game reaches them by arithmetic. Every offset in [offsets]
     * is dereferenced, so the walk alone cannot say this, and a chain written as though a trailing offset would do it
     * reads whatever the pool happens to store at that displacement and resolves to a plausible wrong address
     */
    public val displacement: Long = 0
) {
    init {
        require(size > 0) {
            "$name has no size to read as a structure"
        }
        require(displacement >= 0) {
            "$name displaces by $displacement, which would read behind the address it walked to"
        }
    }

    override fun toString(): String = name
}