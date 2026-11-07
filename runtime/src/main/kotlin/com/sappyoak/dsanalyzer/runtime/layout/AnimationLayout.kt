package com.sappyoak.dsanalyzer.runtime.layout

import com.sappyoak.dsanalyzer.runtime.pointers.GamePointer

public data class AnimationLayout(
    public val pointer: GamePointer,

    /** Where a character's controller holds its pool */
    public val fromController: Int,

    /** Slots in the pool, and so the cap on simultaneous blended animations */
    public val slotCapacity: Int,

    public val slotStride: Int,

    public val slotCountField: Int,

    /** Holds a pointer to the slot array, which is embedded further into this same object */
    public val slotsPointer: Int,

    /** → 'hkaAnimatedSkeleton' */
    public val skeletonPointer: Int,

    /**
     * Within the skeleton: an 'hkArray<FrpgDefaultAnimationControl*>' of [slotCapacity]
     *
     * The controls are reachable two ways — through here, and one per slot — and a slot keeps the
     * same control for its entire life, so the two agree
     */
    public val skeletonControls: Int,

    /** Scales how fast every animation on this character advances */
    public val playSpeed: Int,

    public val slot: AnimSlotLayout,
    public val control: AnimControlLayout
)

public data class AnimSlotLayout(
    public val handle: Int,
    public val serial: Int,

    /** → the control this slot owns until it is released */
    public val control: Int,

    /** Back to the owning pool. Remastered only; `null` in PTDE, which has no such field */
    public val manager: Int,

    /** Seconds since the slot was started, never reset while it stays occupied */
    public val elapsed: Int,

    /** The eviction selector's first key; written as 1 on start */
    public val priority: Int,

    /** Set to `-1.0f` each tick, then filled in by whichever state branch runs */
    public val pendingWeight: Int,

    /**
     * `0` free, `1` playing, `2` holding, `3` fading out, `4` finished instantly
     *
     * An orphaned slot is one parked at `1` that nothing holds a handle to. It is never reclaimed,
     * because the pool's steal pass skips any slot whose master weight is still above zero and the
     * tick keeps rewriting that weight to `1.0` every frame
     */
    public val state: Int,

    /** The control's local time as of last tick, which is how root motion is differenced */
    public val previousLocalTime: Int,

    /** The handle this slot is crossfading away from */
    public val fadeFrom: Int,
    public val fadeDuration: Int,
    public val fadeRemaining: Int
)

/** 'NS_FRPG::FrpgDefaultAnimationControl', FromSoft's subclass of 'hkaDefaultAnimationControl' */
public data class AnimControlLayout(
    public val localTime: Int,

    /**
    * What the blend actually weights by. The pool normalises these across live slots, so a slot
    * stuck at `1.0` dilutes every other animation on the character by `1/(1+stacks)`
    */
    public val masterWeight: Int,

    public val binding: Int,

    /** What the tick copies into [masterWeight] while a slot is playing */
    public val weightScale: Int,

    public val playbackSpeed: Int,
    public val cycles: Int,


    /** `-1` for a loop, `1` for a one-shot */
    public val maxCycles: Int,

    public val easeRate: Int,
    public val easeT: Int,
    public val easeState: Int
)
