package com.sappyoak.dsanalyzer.tas.rng

/** 2^-32, which turns a draw's 32 bits into a float in [0, 1) */
private const val UNIT: Double = 1.0 / 4294967296.0

/**
 * The AI and combat generator: `s1 = s1 * 0x85 + s2; s2 = s1 * 5 + s2 * 7`, the second update using
 * the new `s1`. A draw advances it and returns `s1 ^ s2` (`ptde-rng.md`)
 */
public data class GameRngState(public val s1: Int, public val s2: Int) {
    public fun next(): GameRngState {
        val first = s1 * 0x85 + s2
        return GameRngState(first, first * 5 + s2 * 7)
    }

    /** What `GameRng_NextFloat` returns for the draw that leaves the state at [next] */
    public fun nextFloat(): Float = next().let { ((it.s1 xor it.s2).toLong() and 0xFFFFFFFFL) * UNIT }.toFloat()

    /** The next [count] floats a run of draws from here would see */
    public fun upcoming(count: Int): List<Float> =
        generateSequence(this) { it.next() }.take(count).map { it.nextFloat() }.toList()

    /** Draws from here to [to], or null if it is not reached within [limit] */
    public fun drawsTo(to: GameRngState, limit: Int): Int? {
        var state = this
        for (draws in 0..limit) {
            if (state == to) return draws
            state = state.next()
        }
        return null
    }

    public companion object {
        /**
         * What `GameMan_ctor` seeds it with. That runs once per process, so this is the state of a
         * fresh boot only: loads and quitouts carry the stream on (`ptde-rng-update.md`)
         */
        public val Seed: GameRngState = GameRngState(0x74915435, 0xE8353554.toInt())
    }
}
