package com.sappyoak.dsanalyzer.runtime.ptde

import com.sappyoak.dsanalyzer.native.memory.Signature
import com.sappyoak.dsanalyzer.native.memory.SignatureTarget
import com.sappyoak.dsanalyzer.runtime.inject.HookSite

public object PTDEHookSites {
    public val GameLoopStep: HookSite = HookSite(
        signature = Signature.parse(
            name = "GameLoopStep",
            pattern = "51 A1 ?? ?? ?? ?? 56 57 85 C0 74 17 80 78 18 00 74 05 8B 40 14 EB 03 8B 40 10"
        ),
        offset = 0,
        displaced = 6
    )

    public val PadPolled: HookSite = HookSite(
        signature = Signature.parse(
            name = "PadPolled",
            pattern = "8B 07 8B 50 54 8B CF FF D2 84 C0 75 0B 8B 07 8B 50 2C 8B CF FF D2 EB 05 C6 44 24 13 01 83 C3 04"
        ),
        offset = 24,
        displaced = 5
    )

    public val InputMan: Signature = Signature.parse(
        name = "InputMan",
        pattern = "8B 4E 20 88 46 14 A1 ?? ?? ?? ?? F3 0F 10 40 34 57 8B 7E 04",
        target = SignatureTarget.Embedded(7)
    )

    /** The pointer walk from [InputMan]'s value to the player's pad device */
    public val PlayerPad: List<Int> = listOf(0x08, 0x04, 0xFC)

    /** A `Sleep(0)` wrapper; the operand is the import table slot for `kernel32!Sleep` */
    public val SleepImport: Signature = Signature.parse(
        name = "SleepImport",
        pattern = "6A 00 FF 15 ?? ?? ?? ?? C3 CC CC",
        target = SignatureTarget.Embedded(4)
    )
}

public object PadDevice {
    /** The XInput user index, -1 for a DirectInput device; the stub drives user 0 only */
    public const val XInputUser: Int = 0x164
    public const val Buttons: Int = 0x160

    /** Sticks then triggers: LX, LY, RX, RY, LT, RT */
    public val Axes: List<Int> = listOf(0x16C, 0x170, 0x178, 0x17C, 0x18C, 0x190)
}
