package com.sappyoak.dsanalyzer.runtime.dsr

import com.sappyoak.dsanalyzer.runtime.pointers.GamePointer
import com.sappyoak.dsanalyzer.runtime.pointers.Lifetime

/** Slots in the pool, and so the cap on how many animations one character can blend at once */
internal const val ANIM_SLOT_COUNT: Int = 16

internal const val ANIM_SLOT_SIZE: Int = 0x78

/** The slot array is embedded here rather than allocated separately */
private const val ANIM_SLOTS_AT: Int = 0x200

/**
 * Enough to cover the embedded slot array, which runs well past the last field named below.
 * Sizing this to the furthest named offset would put every slot read outside the fetched buffer
 */
private const val ANIM_DATA_SIZE: Int = ANIM_SLOTS_AT + ANIM_SLOT_COUNT * ANIM_SLOT_SIZE

public object AnimData {
    public val Pointer: GamePointer = GamePointer(
        name = "AnimData",
        base = DSRSignatures.WorldChrMan,
        offsets = listOf(
            0,
            WorldChrMan.PlayerPointer.toLong(),
            ChrIns.ChrCtrlPointer.toLong(),
            ChrCtrl.AnimDataPointer.toLong()
        ),
        lifetime = Lifetime.World,
        size = ANIM_DATA_SIZE
    )

    public const val SlotCount: Int = 0x00

    /** → the slot array embedded at [ANIM_SLOTS_AT] */
    public const val SlotsPointer: Int = 0x08

    /** → 'hkaAnimatedSkeleton' */
    public const val SkeletonPointer: Int = 0x18

    /** Scales how fast every animation on this character advances */
    public const val PlaySpeed: Int = 0xA8

    /** Within the skeleton: an 'hkArray<FrpgDefaultAnimationControl*>' of [ANIM_SLOT_COUNT] */
    public const val SkeletonControls: Int = 0x20
}

public object AnimSlot {
    public const val Handle: Int = 0x00
    public const val Serial: Int = 0x02

    /** → the control this slot owns for its whole life */
    public const val ControlPointer: Int = 0x08

    /** Back to the owning [AnimData]. Remastered only; PTDE has no equivalent field */
    public const val ManagerPointer: Int = 0x18

    /** Seconds since the slot was started, never reset while it stays occupied */
    public const val Elapsed: Int = 0x28

    /** The eviction selector's first key; written as 1 on start */
    public const val Priority: Int = 0x2C

    /** Set to '-1.0' each tick, then filled in by whichever state branch runs */
    public const val PendingWeight: Int = 0x40

    public const val State: Int = 0x4C

    /** The control's local time as of last tick, which is how root motion is differenced */
    public const val PreviousLocalTime: Int = 0x54

    /** The handle this slot is crossfading away from */
    public const val FadeFrom: Int = 0x58
    public const val FadeDuration: Int = 0x5C
    public const val FadeRemaining: Int = 0x60
}

/** 'NS_FRPG::FrpgDefaultAnimationControl', FromSoft's subclass of 'hkaDefaultAnimationControl' */
public object AnimControl {
    public const val LocalTime: Int = 0x10
    public const val MasterWeight: Int = 0x14
    public const val Binding: Int = 0x28

    /** What the tick copies into [MasterWeight] while a slot is playing */
    public const val WeightScale: Int = 0x58

    public const val PlaybackSpeed: Int = 0x5C
    public const val Cycles: Int = 0x60

    /** '-1' for a loop, '1' for a one-shot */
    public const val MaxCycles: Int = 0x68

    public const val EaseRate: Int = 0x90
    public const val EaseT: Int = 0x94
    public const val EaseState: Int = 0x98
}

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
