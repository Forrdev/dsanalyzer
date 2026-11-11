package com.sappyoak.dsanalyzer.runtime.ptde

public object InputMan {
    /** The pointer walk from [InputMan]'s value to the player's pad device */
    public val PlayerPad: List<Int> = listOf(0x08, 0x04, 0xFC)
}

public object PadDevice {
    /** The XInput user index, -1 for a DirectInput device, the stub drives user 0 only */
    public const val XInputUser: Int = 0x164
    public const val Buttons: Int = 0x160

    /** Sticks then triggers: LX, LY, RX, RY, LT, RT */
    public val Axes: List<Int> = listOf(0x16C, 0x170, 0x178, 0x17C, 0x18C, 0x190)
}
