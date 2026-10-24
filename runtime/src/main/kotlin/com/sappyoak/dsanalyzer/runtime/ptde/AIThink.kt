package com.sappyoak.dsanalyzer.runtime.ptde

/** 'NS_FRPG::ComManipulator */
public object ComManipulator {
    public const val AIThinkPointer: Int = 0x230

    /**
     * The embedded 'NS_FRPG::ComThinkOwner' that [AiThink.OwnerPointer] points back at
     */
    public const val ThinkOwner: Int = 0x234
}


public const val AI_THINK_TIMER_COUNT: Int = 4
public const val AI_THINK_NUMBER_COUNT: Int = 8
public const val AI_THINK_SIZE: Int = 0xA0

/**
 * 'NS_FRPG::AiThink
 */
public object AIThink {
    public const val OwnerPointer: Int = 0x00

    /** 'NS_FRPG::AiScript */
    public const val ScriptPointer: Int = 0x04
    public const val ChrInsPointer: Int = 0x14

    public const val TargetingSystemPointer: Int = 0x18

    public const val Timers: Int = 0x44
    public const val Numbers: Int = 0x54

    public const val ThinkId: Int = 0x78

    public const val ChosenAction: Int = 0x9C
}

/** 'NS_FRPG::ActionCtrl' */
public object ActionCtrl {
    public const val PlayingAction: Int = 0x60
    public const val RequestedAction: Int = 0x518
}

public object TargetingSystem {
    public const val Known: Int = 0x0C
}

public object TargetNode {
    public const val Kind: Int = 0x02
    public const val KIND_MASK: Int = 0x7F

    public const val HOSTILE: Int = 1
    public const val ALLY: Int = 2
    public const val OTHER: Int = 3
    public const val FORCED: Int = 4
    public const val SPECIAL: Int = 5

    public const val Next: Int = 0x04

    public const val Shape: Int = 0x18
    public const val SINGLE: Int = 1
    public const val DOUBLE: Int = 2

    public const val Score: Int = 0x20

    public const val SingleHandle: Int = 0x38
    public const val DoubleHandle: Int = 0x44

    public const val SIZE: Int = 0x48

}
