package com.sappyoak.dsanalyzer.runtime.ptde

import com.sappyoak.dsanalyzer.runtime.pointers.GamePointer
import com.sappyoak.dsanalyzer.runtime.pointers.Lifetime

/** Slots in the pool, and so the cap on how many animations one character can blend at once */
internal const val ANIM_SLOT_COUNT: Int = 16

internal const val ANIM_SLOT_SIZE: Int = 0x60

/** The slot array is embedded here rather than allocated separately */
private const val ANIM_SLOTS_AT: Int = 0x140

/**
 * Enough to cover the embedded slot array, which runs well past the last field named below.
 * Sizing this to the furthest named offset would put every slot read outside the fetched buffer
 */
private const val ANIM_DATA_SIZE: Int = ANIM_SLOTS_AT + ANIM_SLOT_COUNT * ANIM_SLOT_SIZE

public object AnimData {
    public val Pointer: GamePointer = GamePointer(
        name = "AnimData",
        base = PTDESignatures.WorldChrMan,
        offsets = THROUGH_CHARACTER + listOf(
            ChrIns.ChrCtrlPointer.toLong(),
            ChrCtrl.AnimDataPointer.toLong()
        ),
        lifetime = Lifetime.World,
        size = ANIM_DATA_SIZE
    )

    public const val SlotCount: Int = 0x00

    /** → the slot array embedded at [ANIM_SLOTS_AT] */
    public const val SlotsPointer: Int = 0x04

    /** → 'hkaAnimatedSkeleton' */
    public const val SkeletonPointer: Int = 0x0C

    /** Scales how fast every animation on this character advances */
    public const val PlaySpeed: Int = 0x64

    /** Within the skeleton: an 'hkArray<FrpgDefaultAnimationControl*>' of [ANIM_SLOT_COUNT] */
    public const val SkeletonControls: Int = 0x10
}

public object AnimSlot {
    public const val Handle: Int = 0x00
    public const val Serial: Int = 0x02

    /** → the control this slot owns for its whole life */
    public const val ControlPointer: Int = 0x04

    public const val ManagerPointer: Int = 0x0C

    /** Seconds since the slot was started, never reset while it stays occupied */
    public const val Elapsed: Int = 0x14

    /** The eviction selector's first key; written as 1 on start */
    public const val Priority: Int = 0x18

    /** Set to '-1.0' each tick, then filled in by whichever state branch runs */
    public const val PendingWeight: Int = 0x28

    public const val State: Int = 0x34

    /** The control's local time as of last tick, which is how root motion is differenced */
    public const val PreviousLocalTime: Int = 0x3C

    /** The handle this slot is crossfading away from */
    public const val FadeFrom: Int = 0x40
    public const val FadeDuration: Int = 0x44
    public const val FadeRemaining: Int = 0x48
}

/** 'NS_FRPG::FrpgDefaultAnimationControl', FromSoft's subclass of 'hkaDefaultAnimationControl' */
public object AnimControl {
    public const val LocalTime: Int = 0x08
    public const val MasterWeight: Int = 0x0C
    public const val Binding: Int = 0x28

    /** What the tick copies into [MasterWeight] while a slot is playing */
    public const val WeightScale: Int = 0x3C

    public const val PlaybackSpeed: Int = 0x40
    public const val Cycles: Int = 0x44

    /** '-1' for a loop, '1' for a one-shot */
    public const val MaxCycles: Int = 0x4C

    public const val EaseRate: Int = 0x70
    public const val EaseT: Int = 0x74
    public const val EaseState: Int = 0x78
}

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
