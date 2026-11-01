package com.sappyoak.dsanalyzer.runtime.dsr

import com.sappyoak.dsanalyzer.runtime.pointers.GamePointer
import com.sappyoak.dsanalyzer.runtime.pointers.Lifetime

/** Remastered carries two more request entries than PTDE, and the band divider moved by one */
internal const val ANIM_REQUEST_COUNT: Int = 31
internal const val ANIM_REQUEST_STRIDE: Int = 0xA8

public object AnimRequestMgr {
    /** Covers the entry array and the manager fields that follow it, which end at `0x1486` */
    public val Pointer: GamePointer = GamePointer(
        name = "AnimRequestMgr",
        base = DSRSignatures.WorldChrMan,
        offsets = listOf(
            0L,
            WorldChrMan.PlayerPointer.toLong(),
            ChrIns.ChrCtrlPointer.toLong(),
            ChrCtrl.AnimRequestMgrPointer.toLong()
        ),
        lifetime = Lifetime.World,
        size = 0x1490
    )

    /** → [AnimData]. `31 * 0xA8` is `0x1458`, so the entry array closes exactly here */
    public const val AnimDataPointer: Int = 0x1458

    /** → the channel table; channel `i` is at `table + 0x08 + i * 8` */
    public const val ChannelTablePointer: Int = 0x1460
    public const val ChannelTableFirst: Int = 0x08

    public const val GroupHandles: Int = 0x1468
    public const val AuxHandles: Int = 0x1478

    /** One `u16`; PTDE clears the two halves separately */
    public const val FrameFlags: Int = 0x1484

    public const val Band1First: Int = 0x00
    public const val Band1Last: Int = 0x0D
    public const val Band2First: Int = 0x0F
    public const val Band2Last: Int = 0x1E

    /**
     * In neither band's clear loop, so its enable flag latches for the character's whole life.
     * Confirmed flagged on all 24 characters in a live world, always with an animation id of `-1`
     */
    public const val LatchedEntry: Int = 0x0E

    public val SpecialEntries: List<Int> = listOf(0x1B, 0x1C)
}

/** One request entry, stride [ANIM_REQUEST_STRIDE] */
public object AnimRequestEntry {
    /** Negative means empty */
    public const val AnimationId: Int = 0x00

    public const val ChannelHandle: Int = 0x04
    public const val ChannelSerial: Int = 0x06

    /** Set inline beside [EnableFlag], but only on [AnimRequestMgr.SpecialEntries] */
    public const val InlineEnableFlag: Int = 0x7C

    /** PTDE's `+0x94` */
    public const val EnableFlag: Int = 0xA0
}

/** One of six play channels, reached through [AnimRequestMgr.ChannelTablePointer] */
public object AnimChannel {
    public const val Count: Int = 6

    /** The low half always equals the channel's own index, which the teardown path validates */
    public const val OwnHandle: Int = 0x00

    /** → [AnimData], which every channel on a character shares */
    public const val AnimDataPointer: Int = 0x08

    /**
     * The slot this channel is driving
     *
     * Written in exactly one place, `AnimRequestMgr_CollectRequests` @ `0x14043A900`, and discarded
     * in exactly one place, `AnimOwner_ClearCurrentAnim` @ `0x14043CB20`, which does not check
     * whether the stop it attempted succeeded. One writer and one unconditional discarder is the
     * whole poopwalk mechanism — and Remastered routes all three clear sites through that single
     * function, where PTDE inlines one of them
     */
    public const val CurrentSlotHandle: Int = 0x140
    public const val CurrentSlotSerial: Int = 0x142

    /** Reset to `-1.0f` on teardown */
    public const val FadeWeight: Int = 0x144
}
