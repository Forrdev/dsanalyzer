package com.sappyoak.dsanalyzer.runtime.ptde

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
