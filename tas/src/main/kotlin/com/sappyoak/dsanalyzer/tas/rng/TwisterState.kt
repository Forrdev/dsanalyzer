package com.sappyoak.dsanalyzer.tas.rng

import com.sappyoak.dsanalyzer.native.memory.Address
import com.sappyoak.dsanalyzer.runtime.ptde.GameMan
import com.sappyoak.dsanalyzer.shared.binary.littleEndian

/** Words of state an MT19937 keeps */
public const val TWISTER_WORDS: Int = 624

/**
 * `GameMan`'s MT19937, kept with its cursor as an **index**: in the game the cursor is a pointer
 * into the instance's own state, and `GameMan` moves on every load, so an address would not survive
 */
public class TwisterState(
    public val seed: Int,
    public val left: Int,
    public val cursor: Int,
    state: IntArray
) {
    private val words = state.copyOf()

    init {
        require(words.size == TWISTER_WORDS) { "A twister holds $TWISTER_WORDS words" }
        require(cursor in 0..TWISTER_WORDS) { "Cursor $cursor is outside the state" }
    }

    public val state: IntArray get() = words.copyOf()

    /** The instance's bytes as they go at [instance], the cursor rebased onto it */
    public fun encode(instance: Address): ByteArray {
        val buffer = littleEndian(GameMan.TwisterSize)
        val cursorAt = instance + (GameMan.TwisterState + cursor * Int.SIZE_BYTES).toLong()
        buffer.putInt(seed).putInt(left).putInt(cursorAt.value.toInt())
        words.forEach(buffer::putInt)
        return buffer.array()
    }

    override fun equals(other: Any?): Boolean = other is TwisterState &&
            seed == other.seed && left == other.left && cursor == other.cursor && words.contentEquals(other.words)

    override fun hashCode(): Int = 31 * (31 * (31 * seed + left) + cursor) + words.contentHashCode()

    public companion object {
        /** The instance read from [instance]; null if its cursor points outside it */
        public fun decode(bytes: ByteArray, instance: Address): TwisterState? {
            val buffer = bytes.littleEndian()
            val seed = buffer.int
            val left = buffer.int
            val cursorAt = buffer.int.toLong() and 0xFFFFFFFFL
            val offset = cursorAt - (instance.value + GameMan.TwisterState)
            if (offset < 0 || offset % Int.SIZE_BYTES != 0L) return null
            val cursor = (offset / Int.SIZE_BYTES).toInt()
            if (cursor > TWISTER_WORDS) return null
            return TwisterState(seed, left, cursor, IntArray(TWISTER_WORDS) { buffer.int })
        }
    }
}
