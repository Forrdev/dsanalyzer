package com.sappyoak.dsanalyzer.runtime.layout

public data class AILayout(
    public val think: ThinkLayout,
    public val targeting: TargetingLayout,
    public val goal: GoalLayout,
    public val goalType: GoalTypeLayout,
    public val pointerWidth: Int
)

public data class ThinkLayout(
    /** 'ComManipulator' object that points at the think object */
    public val fromManipulator: Int,
    /** 'ComManipulator' field the think object points back at, from [owner] */
    public val manipulatorOwner: Int,
    public val owner: Int,
    public val targeting: Int,
    public val topGoal: Int,
    public val timers: Int,
    public val timerCount: Int,
    public val numbers: Int,
    public val numberCount: Int,
    public val thinkId: Int,
    public val size: Int
)
public data class TargetingLayout(
    public val known: Int,
    public val nodeKind: Int,
    public val nodeNext: Int,
    public val nodeShape: Int,
    public val nodeWeight: Int,
    public val nodeHandle: Int,
    public val nodeSize: Int
)

public object TargetKinds {
    public const val KIND_MASK: Int = 0x7F
    public const val COMBAT: Int = 1
    public const val KNOWN: Int = 2
    public const val SOUND: Int = 3
    public const val URGENT_SOUND: Int = 4
    public const val ALERT_SOUND: Int = 5
    public const val CHARACTER: Int = 1
    public const val PLACE: Int = 2
}

public data class GoalLayout(
    public val think: Int,
    public val self: Int,
    public val life: Int,
    /** The subgaol 'std::deque' */
    public val subgoalMap: Int,
    public val subgoalMapSize: Int,
    public val subgoalOffset: Int,
    public val subgoalCount: Int,
    /** Elements per deque block, 16 bytes over the pointer width */
    public val dequeBlock: Int,
    public val params: Int,
    public val paramCount: Int,
    public val extraParamsBegin: Int,
    public val extraParamsEnd: Int,
    public val type: Int,
    public val size: Int
)
public data class GoalTypeLayout(
    public val name: Int,
    public val nameLength: Int,
    public val nameCapacity: Int,
    public val inlineCapacity: Int,
    public val id: Int,
    public val size: Int
)
