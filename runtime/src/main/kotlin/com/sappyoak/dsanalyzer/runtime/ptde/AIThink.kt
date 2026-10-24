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

    public const val TopGoalPointer: Int = 0x24

    public const val Timers: Int = 0x44
    public const val Numbers: Int = 0x54

    public const val ThinkId: Int = 0x78

    public const val ChosenAction: Int = 0x9C
}

/** 'NS_FRPG::ActionCtrl' */
public object ActionCtrl {
    public const val PlayingAction: Int = 0x60
    public const val PlayingTime: Int = 0x48C
    public const val RequestedAction: Int = 0x518
}

public object TargetingSystem {
    public const val Known: Int = 0x0C
}

public object TargetNode {
    public const val Kind: Int = 0x02
    public const val KIND_MASK: Int = 0x7F

    /** The character being found, kept by 'TargetingSystemBase_Tick' through 'AiTargetEntryList_AddKind1' */
    public const val COMBAT: Int = 1
    /** A character known about, allies included */
    public const val KNOWN: Int = 2
    /** A sound of type 0, heard only while there is no [COMBAT] entry ('AiPerception_SoundPass') */
    public const val SOUND: Int = 3
    /** A sound of type 1 to 3, which wins over everything */
    public const val URGENT_SOUND: Int = 4
    /** A sound of type 4, second only to [URGENT_SOUND] */
    public const val ALERT_SOUND: Int = 5

    public const val Next: Int = 0x04

    /** Whether the entry points at a character or a place */
    public const val Shape: Int = 0x18
    public const val CHARACTER: Int = 1
    public const val PLACE: Int = 2

    /** What ranks [COMBAT] entries */
    public const val Weight: Int = 0x20

    /** The character's handle when [Shape] is [CHARACTER], otherwise -1 until the entry is filled */
    public const val Handle: Int = 0x38

    public const val SIZE: Int = 0x3C

}
