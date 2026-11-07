package com.sappyoak.dsanalyzer.runtime.ptde

/**
 * 'NS_FRPG::ActionCtrl' Owns a character's two ESD machines,
 * the playing action, and the flag words the ESD reads and writes
 */
public object ActionCtrl {
    public const val MachineSlots: Int = 0x20
    public const val MachineSlotStride: Int = 0x18
    public const val MachineSlotEnabled: Int = 0x04
    public const val MachineState: Int = 0x10

    public const val PlayingAction: Int = 0x60

    public const val EsdFlags: Int = 0x1BC
    public const val LadderState: Int = 0x1C0
    public const val DamageAnimation: Int = 0x1C4
    public const val DamageLevel: Int = 0x1CC

    public const val PlayingTime: Int = 0x48C
    public const val RequestedAction: Int = 0x518

    public const val NO_ANIMATION: Int = 99999
}


public object ChrEventAction {
    public const val Type: Int = 0x00
    public const val First: Int = 0x04
    public const val Second: Int = 0x08

    public const val SIZE: Int = 0x0C
}
