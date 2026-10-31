package com.sappyoak.dsanalyzer.formats.param

import java.nio.ByteBuffer
import java.nio.ByteOrder

public class RowBytes(bytes: ByteArray, offset: Int = 0, public val size: Int = bytes.size - offset) {
    private val buffer = ByteBuffer.wrap(bytes, offset, size).slice().order(ByteOrder.LITTLE_ENDIAN)

    public fun s8(at: Int): Int = buffer.get(at).toInt()
    public fun u8(at: Int): Int = buffer.get(at).toInt() and 0xFF
    public fun s16(at: Int): Int = buffer.getShort(at).toInt()
    public fun u16(at: Int): Int = buffer.getShort(at).toInt() and 0xFFFF
    public fun s32(at: Int): Int = buffer.getInt(at)
    public fun u32(at: Int): Long = buffer.getInt(at).toLong() and 0xFFFF_FFFFL
    public fun f32(at: Int): Float = buffer.getFloat(at)

    /** One bit of the byte at [at] */
    public fun flag(at: Int, bit: Int): Boolean = (u8(at) shr bit) and 1 != 0

    /** [count] bits from [bit] up, out of the [unitSize]-byte storage unit at [at] */
    public fun bits(at: Int, unitSize: Int, bit: Int, count: Int): Int {
        val unit = when (unitSize) {
            1 -> u8(at)
            2 -> u16(at)
            else -> s32(at)
        }
        return (unit ushr bit) and ((1 shl count) - 1)
    }
}
