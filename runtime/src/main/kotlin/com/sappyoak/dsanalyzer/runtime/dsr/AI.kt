package com.sappyoak.dsanalyzer.runtime.dsr

public object AIThink {
    public const val OwnerPointer: Int = 0x00
    public const val ScriptPointer: Int = 0x08
    public const val ChrInsPointer: Int = 0x28
    public const val TargetingSystemPointer: Int = 0x30

    /** 'AiScript_AddTopGoal' adds under this */
    public const val TopGoalPointer: Int = 0x58

    /** Four floats, 'AiScript_GetTimer' */
    public const val Timers: Int = 0x74

    /** Eight, 'AiScript_SetNumber' */
    public const val Numbers: Int = 0x84
    public const val ThinkId: Int = 0xA8
    
    public const val ThinkParamPointer: Int = 0xC0

    public const val SIZE: Int = 0xB0
    public const val TIMER_COUNT: Int = 4
    public const val NUMBER_COUNT: Int = 8
}

public object TargetingSystem {
    public const val Known: Int = 0x18
}

public object TargetNode {
    public const val Kind: Int = 0x02
    public const val Next: Int = 0x08
    public const val Shape: Int = 0x28
    public const val Weight: Int = 0x30
    public const val Handle: Int = 0x50
    public const val SIZE: Int = 0x58
}

public object Goal {
    public const val ThinkPointer: Int = 0x00
    public const val SelfPointer: Int = 0x18
    public const val Life: Int = 0x20
    public const val SubgoalMap: Int = 0x38
    public const val SubgoalMapSize: Int = 0x40
    public const val SubgoalOffset: Int = 0x48
    public const val SubgoalCount: Int = 0x50

    /** 16 bytes of 8-byte pointers */
    public const val DEQUE_BLOCK: Int = 2
    public const val Params: Int = 0x58
    public const val PARAM_COUNT: Int = 5

    public const val ExtraParamsBegin: Int = 0x78
    public const val ExtraParamsEnd: Int = 0x80
    public const val Id: Int = 0x90
    public const val TypePointer: Int = 0x98
    public const val SIZE: Int = 0xE8
}

public object GoalType {
    public const val Name: Int = 0x10
    public const val NameLength: Int = 0x20
    public const val NameCapacity: Int = 0x28
    public const val INLINE_CAPACITY: Int = 16

    public const val Id: Int = 0x150
    public const val SIZE: Int = 0x158
}

/** 'NS_FRPG::ActionCtrl', by RTTI at 'ChrCtrl+0x48' */
public object ActionCtrl {
    /** Two slots; each holds the machine at '+0x00' */
    public const val MachineSlots: Int = 0x30
    public const val MachineSlotStride: Int = 0x20
    public const val MachineSlotEnabled: Int = 0x08
    public const val MACHINE_SLOT_COUNT: Int = 2

    public const val MachineSlotState: Int = 0x10
    public const val PlayingAction: Int = 0x80
    public const val EsdFlags: Int = 0x1DC

    public const val LadderState: Int = 0x1E0
    public const val DamageAnimation: Int = 0x1E4

    public const val DamageLevel: Int = 0x1EC

    /** Seconds into [PlayingAction], back to zero when it changes */
    public const val PlayingTime: Int = 0x4B0
    public const val RequestedAction: Int = 0x528
}
