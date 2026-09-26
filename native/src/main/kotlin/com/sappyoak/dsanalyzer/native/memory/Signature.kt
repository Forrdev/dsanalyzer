package com.sappyoak.dsanalyzer.native.memory

import com.sappyoak.dsanalyzer.native.process.ProcessMemory

/** Signatures are instructions, so only the regions holding code can match one */
private val CODE: (MemoryRegion) -> Boolean = { it.readable && it.executable }

/**
 * Where the address a [Signature] name sites, relative to the bytes that matched
 *
 * Every form names an address in the target directly.
 */
public sealed interface SignatureTarget {
    /** The match itself, which is an instruction rather than data */
    public data object Match : SignatureTarget

    /**
     * The pointer sized operand [at] bytes from the match, read from the instruction itself
     */
    public data class Embedded(public val at: Int) : SignatureTarget

    /**
     * A signed 32 bit displacement [at] bytes from the match, counted from the end of the
     * instruction that holds it. [instructionEnd] is measured from the match rather than from
     * the displacement, which is how x86-64 addresses data relative to RIP
     */
    public data class Relative(
        public val at: Int,
        public val instructionEnd: Int
    ) : SignatureTarget
}

/** A named byte signature and the address the bytes it matches name */
public class Signature(
    public val name: String,
    public val pattern: AOBPattern,
    public val target: SignatureTarget = SignatureTarget.Match
) {
    override fun toString(): String = name

    public companion object {
        public fun parse(
            name: String,
            pattern: String,
            target: SignatureTarget = SignatureTarget.Match
        ): Signature = Signature(name, AOBPattern.parse(pattern), target)
    }
}