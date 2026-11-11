package com.sappyoak.dsanalyzer.tas.rng

import java.nio.ByteBuffer

/** An [RngState] in a movie file: `s1`, `s2`, the twister's seed, `left`, cursor index and words */
internal object RngCodec {
    const val SIZE: Int = (2 + 3 + TWISTER_WORDS) * Int.SIZE_BYTES

    fun write(buffer: ByteBuffer, state: RngState) {
        buffer.putInt(state.game.s1).putInt(state.game.s2)
        val twister = state.twister
        buffer.putInt(twister.seed).putInt(twister.left).putInt(twister.cursor)
        twister.state.forEach(buffer::putInt)
    }

    /** Null when the twister's fields are out of range */
    fun read(buffer: ByteBuffer): RngState? {
        val game = GameRngState(buffer.int, buffer.int)
        val seed = buffer.int
        val left = buffer.int
        val cursor = buffer.int
        val words = IntArray(TWISTER_WORDS) { buffer.int }
        if (cursor !in 0..TWISTER_WORDS) return null
        return RngState(game, TwisterState(seed, left, cursor, words))
    }
}
