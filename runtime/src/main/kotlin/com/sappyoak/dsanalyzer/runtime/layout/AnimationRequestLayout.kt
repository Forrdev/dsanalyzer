package com.sappyoak.dsanalyzer.runtime.layout

import com.sappyoak.dsanalyzer.runtime.pointers.GamePointer

public data class AnimRequestLayout(
    public val pointer: GamePointer,
    public val entryCount: Int,
    public val entryStride: Int,
    public val entry: AnimRequestEntryLayout,

    /** → the slot manager, the same object the character's own `AnimData` pointer reaches */
    public val animData: Int,

    /** → the channel table object; the channels themselves start at [channelTableFirst] inside it */
    public val channelTable: Int,

    public val channelTableFirst: Int,

    /** Four channel handles, one per channel group */
    public val groupHandles: Int,

    /** Three further channel handles, torn down on their own path */
    public val auxHandles: Int,

    public val bands: List<AnimRequestBand>,

    /**
     * The entry sitting between the two bands, which **no band clears**
     *
     * Its enable flag is set during initialisation and nothing ever unsets it: the suppression pass
     * skips it because its link field is null, and neither band's clear loop covers it. Confirmed
     * flagged on all 24 characters in a live world, always with an animation id of `-1`.
     *
     * Harmless to the game, because the game tests the flag together with a non-negative animation
     * id. Not harmless to a reader: anything keying on [AnimRequestEntryLayout.enableFlag] alone
     * reports a winning request with no animation on **every character in the world**
     */
    public val latchedEntry: Int,

    /**
     * The two entries each band special-cases, setting their flags inline instead of by the
     * highest-index rule, because they are the entries that own a channel group outright
     */
    public val specialEntries: List<Int>,

    public val channel: AnimChannelLayout
) {
    init {
        require(entryCount * entryStride == animData) {
            "entry array must close on the first manager field: " +
                    "$entryCount * $entryStride != $animData"
        }
    }
}

/**
 * One request entry
 *
 * The live-request predicate is **both** fields together — `enableFlag != 0 && animationId >= 0` —
 * exactly as the game's own collection pass tests it. See [AnimRequestLayout.latchedEntry] for what
 * reading the flag alone costs
 */
public data class AnimRequestEntryLayout(
    /** Negative means the entry is empty */
    public val animationId: Int,

    /** `{ index:16, serial:16 }`; the serial half is at [channelSerial] */
    public val channelHandle: Int,

    public val channelSerial: Int,

    /**
     * A per-branch enable over a small link graph, **not** a winner mark. Three passes write it in
     * order: a band clears its entries and sets the highest-indexed one with a non-negative
     * animation id, a suppression pass clears any entry whose suppressor link points at a live
     * entry, and a propagation pass pushes the flag along the other link
     */
    public val enableFlag: Int,

    /**
     * Set to 1 beside [enableFlag], but only on the two entries a band special-cases. At the same
     * offset in both editions, unlike [enableFlag]
     */
    public val inlineEnableFlag: Int
)

public data class AnimRequestBand(
    public val first: Int,
    public val last: Int
) {
    init {
        require(first <= last) { "band $first..$last is inverted" }
    }

    public val indices: IntRange get() = first..last
}

public data class AnimChannelLayout(
    public val count: Int,

    /** `{ channelIndex:16, serial:16 }`; the low half always equals the channel's own index */
    public val ownHandle: Int,

    /** → the slot manager, which every channel on a character shares */
    public val animData: Int,

    public val currentSlotHandle: Int,
    public val currentSlotSerial: Int,

    /** Reset to `-1.0f` when the channel is torn down */
    public val fadeWeight: Int
)
