package com.sappyoak.dsanalyzer.runtime.ptde

import com.sappyoak.dsanalyzer.runtime.pointers.GamePointer
import com.sappyoak.dsanalyzer.runtime.pointers.Lifetime

/** Slots in the pool, and so the cap on how many animations one character can blend at once */
internal const val ANIM_SLOT_COUNT: Int = 16

internal const val ANIM_SLOT_SIZE: Int = 0x60

/** The slot array is embedded here rather than allocated separately */
private const val ANIM_SLOTS_AT: Int = 0x160

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
