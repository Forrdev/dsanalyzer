package com.sappyoak.dsanalyzer.runtime.ptde

import com.sappyoak.dsanalyzer.runtime.pointers.GamePointer
import com.sappyoak.dsanalyzer.runtime.pointers.Lifetime

/** Bytes to fetch per channel, which has to cover a `vec3` read at [AnimRequest.Times] */
private const val SLOT_SIZE = 0x160

/** Where each channel sits inside the region [ChrCtrl.AnimRequestPoolPointer] reaches */
private const val CHANNEL_A_AT = 0x300L
private const val CHANNEL_B_AT = 0x1210L

private val THROUGH_POOL: List<Long> = THROUGH_CHARACTER + listOf(
    ChrIns.ChrCtrlPointer.toLong(),
    ChrCtrl.AnimRequestPoolPointer.toLong()
)

private fun channel(name: String, at: Long): GamePointer = GamePointer(
    name = name,
    base = ChrIns.Base,
    offsets = THROUGH_POOL,
    lifetime = Lifetime.World,
    size = SLOT_SIZE,
    displacement = at
)

/**
 * An animation request slot. The id of an animation being asked for, not the one played.
 *
 * [AnimationId] is a one-shot. One function writes the id into it and another resets it to '-1',
 * and the two alternate. So the field reads '-1' when nothing has just been requested, every request passes
 * through it exactly one, and it runs slightly ahead of what is visibly playing
 *
 * Two channels exist per character at fixed displacements in one pooled region, and nothing points at either.
 * The game reaches them by arithmetic.
 *
 * **The displacements are not interchangeable with offsets.** The channels are separate allocations
 * that the pool hands out in sequence. So 'pool + 0x1354' reaching [ChannelB]'s id is an accident
 * of layout: it runs past the end of one allocation into another. Addressing the channel and then
 * the field keeps the two apart, which is why the id is 'x0144' here rather than the '0x1354' a
 * hand-built pointer chan would show.
 */
public object AnimRequest {
    /** The narrower channel, which does not see every request */
    public val ChannelA: GamePointer = channel("AnimRequestA", CHANNEL_A_AT)

    /** The channel every request observed so far has arrived on */
    public val ChannelB: GamePointer = channel("AnimRequestB", CHANNEL_B_AT)

    /**
     * A 'u16' bumped everytime the slot is reset.
     *
     * The cheapest change detector available: polling this beats diffing [AnimationId] which is cleared
     * to '-1' between requests and so can be missed entirely between two ticks
     */
    public const val Generation: Int = 0x02

    /** Eight entries of stride '0x20', each reset to '-1'. What they hold is unknown for now */
    public const val Slots: Int = 0x08
    public const val Timer: Int = 0x140

    /** '-1' when nothing has just been requested */
    public const val AnimationId: Int = 0x144

    /** Three consecutive floats, all reset to '-1.0'. This is likely blend or fade times based on the evidence so far */
    public const val Times: Int = 0x148
}