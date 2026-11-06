package com.sappyoak.dsanalyzer.runtime.ptde

import com.sappyoak.dsanalyzer.native.memory.Signature
import com.sappyoak.dsanalyzer.native.memory.SignatureTarget
import com.sappyoak.dsanalyzer.runtime.inject.HookSite

public object PTDEHookSites {
    public val GameLoopStep: HookSite = HookSite(
        signature = PTDESignatures.GameLoopStep,
        offset = 0,
        displaced = 6
    )

    public val PadPolled: HookSite = HookSite(
        signature = PTDESignatures.PadPolled,
        offset = 24,
        displaced = 5
    )

    /** The pointer walk from [InputMan]'s value to the player's pad device */
    public val PlayerPad: List<Int> = listOf(0x08, 0x04, 0xFC)
    public val LoadBudgetImmediate: Int = 35
}

/** A 'FileMan' capsule ring and the capsule state that means "read requested" or not yet completed */
public object FileRing {
    public const val Count: Int = 5
    public const val Head: Int = 0x20
    public const val Tail: Int = 0x24
    public const val Capacity: Int = 0x28
    public const val Slots: Int = 0x2C

    /** A capsule's state byte: 0 none, 1 requested, 2 read done, 3 finishing, 4 loaded */
    public const val CapsuleState: Int = 0x20
    public const val Requested: Int = 1
}
public object PadDevice {
    /** The XInput user index, -1 for a DirectInput device; the stub drives user 0 only */
    public const val XInputUser: Int = 0x164
    public const val Buttons: Int = 0x160

    /** Sticks then triggers: LX, LY, RX, RY, LT, RT */
    public val Axes: List<Int> = listOf(0x16C, 0x170, 0x178, 0x17C, 0x18C, 0x190)
}
