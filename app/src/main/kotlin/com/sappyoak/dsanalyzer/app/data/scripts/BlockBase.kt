package com.sappyoak.dsanalyzer.app.data.scripts

/**
 * Where a callee's 'sourceStartByte' counts from inside its caller's 'InitializeEvent'
 */
public sealed interface BlockBase {
    public data class Derived(
        public val offset: Int,
        /** Call sits where the caller passes exactly what the callee reads */
        public val exact: Int,
        public val sites: Int
    ) : BlockBase

    public data object Ambiguous : BlockBase
}

public data class BlockFit(
    public val argsLength: Int,
    public val needed: Int
)

private val CANDIDATES = listOf(8, 0)

private const val ARGUMENT_BYTES = 4

public fun deriveBlockBase(fits: List<BlockFit>): BlockBase {
    val scored = CANDIDATES.mapNotNull { offset -> offset.scoreAgainst(fits) }
    val (offset, exact) = scored.maxByOrNull { (_, count) -> count } ?: return BlockBase.Ambiguous

    if (scored.count { (_, other) -> other == exact } > 1) return BlockBase.Ambiguous

    return BlockBase.Derived(offset = offset, exact = exact, sites = fits.size)
}

/**
 * This offset and the number of call sites it fits exactly, or null where it cannot be right
 *
 * A negative surplus means the callee reads past the end of what its caller passed, which no
 * correct offset produces
 */
private fun Int.scoreAgainst(fits: List<BlockFit>): Pair<Int, Int>? {
    if (fits.isEmpty()) return null

    val surpluses = fits.map { fit -> fit.argsLength - this - fit.needed.toWholeArguments() }
    return if (surpluses.any { it < 0 }) null else this to surpluses.count { it == 0 }
}

/** Arguments are four bytes, so a callee reading one byte of one still costs the whole four */
private fun Int.toWholeArguments(): Int =
    if (this % ARGUMENT_BYTES == 0) this else this + ARGUMENT_BYTES - this % ARGUMENT_BYTES
