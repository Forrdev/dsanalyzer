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

/**
 * One goal of an AI's plan. The tree hangs off [AiThink.TopGoalPointer]. Each goal keeps the goals it
 * has broken into as a dequeue, the first being worked on and the rest being queued behind it
 */
public object Goal {
    /** The [AiThink] this goal belongs to */
    public const val ThinkPointer: Int = 0x00

    /** The goal itself, which a freed and reused slot no longer agrees with */
    public const val SelfPointer: Int = 0x0C

    /** Seconds left, '-1' for a goal with no limit */
    public const val Life: Int = 0x10

    /** The subgoal deque: its block map, the map's length, the first element's index and the count */
    public const val SubgoalMap: Int = 0x28
    public const val SubgoalMapSize: Int = 0x2C
    public const val SubgoalOffset: Int = 0x30
    public const val SubgoalCount: Int = 0x34

    /** Subgoal pointers per deque block */
    public const val DEQUE_BLOCK: Int = 4

    /** The parameters the script passed, as floats: an action id, a target, a distance and the like */
    public const val Params: Int = 0x38
    public const val PARAM_COUNT: Int = 5

    /** The parameters beyond the fifth, a vector's begin and end */
    public const val ExtraParamsBegin: Int = 0x50
    public const val ExtraParamsEnd: Int = 0x54

    /** Its [GoalType] */
    public const val TypePointer: Int = 0x60

    public const val SIZE: Int = 0xB0
}

public object GoalType {
    /** A 'basic_string<char>': its inline buffer, then the length and the capacity */
    public const val Name: Int = 0x08
    public const val NameLength: Int = 0x18
    public const val NameCapacity: Int = 0x1C

    /** Below this capacity the name sits in the inline buffer, at and above it the buffer is a pointer */
    public const val INLINE_CAPACITY: Int = 16

    /** The 'GOAL_*' id the scripts know it by */
    public const val Id: Int = 0xA4

    /** What one read has to cover. The update rates and flags that follow are not read */
    public const val SIZE: Int = 0xA8
}
