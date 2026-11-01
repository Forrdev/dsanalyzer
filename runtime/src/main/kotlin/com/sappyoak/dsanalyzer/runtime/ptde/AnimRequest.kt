package com.sappyoak.dsanalyzer.runtime.ptde

import com.sappyoak.dsanalyzer.runtime.pointers.GamePointer
import com.sappyoak.dsanalyzer.runtime.pointers.Lifetime

internal const val ANIM_REQUEST_COUNT: Int = 29
internal const val ANIM_REQUEST_STRIDE: Int = 0x9C

public object AnimRequestMgr {
    /** Covers the entry array and the manager fields that follow it, which end at `0x11D2` */
    public val Pointer: GamePointer = GamePointer(
        name = "AnimRequestMgr",
        base = PTDESignatures.WorldChrMan,
        offsets = THROUGH_CHARACTER + listOf(
            ChrIns.ChrCtrlPointer.toLong(),
            ChrCtrl.AnimRequestMgrPointer.toLong()
        ),
        lifetime = Lifetime.World,
        size = 0x11E0
    )

    /** → [AnimData]. `29 * 0x9C` is `0x11AC`, so the entry array closes exactly here */
    public const val AnimDataPointer: Int = 0x11AC

    /** → the channel table; channel `i` is at `table + 0x04 + i * 4` */
    public const val ChannelTablePointer: Int = 0x11B0
    public const val ChannelTableFirst: Int = 0x04

    public const val GroupHandles: Int = 0x11B4
    public const val AuxHandles: Int = 0x11C4

    /** Two bytes cleared separately on the way out; Remastered packs both into one `u16` */
    public const val FrameFlags: Int = 0x11D0

    public const val Band1First: Int = 0x00
    public const val Band1Last: Int = 0x0C
    public const val Band2First: Int = 0x0E
    public const val Band2Last: Int = 0x1C

    /** In neither band's clear loop, so its enable flag latches for the character's whole life */
    public const val LatchedEntry: Int = 0x0D

    public val SpecialEntries: List<Int> = listOf(0x1A, 0x1B)
}

/** One request entry, stride [ANIM_REQUEST_STRIDE] */
public object AnimRequestEntry {
    /** Negative means empty */
    public const val AnimationId: Int = 0x00

    public const val ChannelHandle: Int = 0x04
    public const val ChannelSerial: Int = 0x06

    /** Set inline beside [EnableFlag], but only on [AnimRequestMgr.SpecialEntries] */
    public const val InlineEnableFlag: Int = 0x7C

    /** Remastered's `+0xA0` */
    public const val EnableFlag: Int = 0x94
}

/** One of six play channels, reached through [AnimRequestMgr.ChannelTablePointer] */
public object AnimChannel {
    public const val Count: Int = 6

    public const val OwnHandle: Int = 0x00

    /** → [AnimData], which every channel on a character shares */
    public const val AnimDataPointer: Int = 0x04

    /**
     * The slot this channel is driving
     *
     * Written in exactly one place, `AnimRequestMgr_CollectRequests`, and discarded in exactly one
     * place, the inline stop inside `AnimRequestMgr_ApplyAndTeardown` @ `0x00D95699`, which writes
     * `0xFFFFFFFF` here whether or not the stop it attempted succeeded. A channel holding a stale
     * handle therefore skips the stop and loses the handle in the same frame, orphaning the slot
     */
    public const val CurrentSlotHandle: Int = 0x128
    public const val CurrentSlotSerial: Int = 0x12A

    /** Reset to `-1.0f` on teardown */
    public const val FadeWeight: Int = 0x12C
}
