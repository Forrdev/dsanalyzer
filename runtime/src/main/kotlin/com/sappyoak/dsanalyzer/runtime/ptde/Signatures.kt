package com.sappyoak.dsanalyzer.runtime.ptde

import com.sappyoak.dsanalyzer.*
import com.sappyoak.dsanalyzer.native.memory.Signature
import com.sappyoak.dsanalyzer.native.memory.SignatureTarget


object PTDESignatures {
    public val ChrIns: Signature = Signature.parse(
        name = "ChrIns",
        pattern = "8B 15 ?? ?? ?? ?? F3 0F 10 44 24 30 52",
        target = SignatureTarget.Embedded(2)
    )

    public val EmevdMan: Signature = Signature.parse(
        name = "EmevdMan",
        pattern = "83 FF FF 73 06 03 F8 03 39 EB 02 33 FF A1 ?? ?? ?? ?? 83 C0 04",
        target = SignatureTarget.Embedded(14)
    )

    public val ChrFollowCam: Signature = Signature.parse(
        name = "ChrFollowCam",
        pattern = "D9 45 08 A1 ?? ?? ?? ?? 51 D9 1C 24 50",
        target = SignatureTarget.Embedded(4)
    )

    public val GameDataMan: Signature = Signature.parse(
        name = "GameDataMan",
        pattern = "8B 0D ?? ?? ?? ?? 8B 41 30 8B 4D 64",
        target = SignatureTarget.Embedded(2)
    )

    public val FrpgHavokMan: Signature = Signature.parse(
        name = "FrpgHavokMan",
        pattern = "51 A1 ?? ?? ?? ?? 8B 48 14 53 55 8B 6C 24 10 8B 5D 3C 56 57",
        target = SignatureTarget.Embedded(2)
    )

    public val MenuMan: Signature = Signature.parse(
        name = "MenuMan",
        pattern = "E8 ?? ?? ?? ?? 8B 35 ?? ?? ?? ?? A1 ?? ?? ?? ?? 3B C3 74 0D",
        target = SignatureTarget.Embedded(7)
    )

    public val GameMan: Signature = Signature.parse(
        name = "GameMan",
        pattern = "8B 54 24 10 8B C8 F7 D9 39 8A B8 0E 00 00 B3 01 0F 95 C2 8B 0D ?? ?? ?? ?? 80 B9 A5 0B 00 00 00",
        target = SignatureTarget.Embedded(0x15)
    )

    public val WorldArea: Signature = Signature.parse(
        name = "WorldArea",
        pattern = "8B 48 04 8B 11 50 8B 42 34 FF D0 C7 46 2C 00 00 00 00 C3 8B 0D ?? ?? ?? ?? " +
                "8B 89 50 0B 00 00 32 C0 83 79 28 00",
        target = SignatureTarget.Embedded(0x15)
    )

    public val WorldChrMan: Signature = Signature.parse(
        name = "WorldChrMan",
        pattern = "A1 ?? ?? ?? ?? 8B 40 3C 3B C8 75 02 33 C9 56",
        target = SignatureTarget.Embedded(1)
    )

    public val EventFlags: Signature = Signature.parse(
        name = "EventFlags",
        pattern = "56 8B F1 8B 46 1C 50 A1 ?? ?? ?? ?? 32 C9",
        target = SignatureTarget.Embedded(8)
    )

    public val AttackMan: Signature = Signature.parse(
        name = "AttackMan",
        pattern = "A1 ?? ?? ?? ?? 51 8B 4D 0C D9 1C 24 51 52 6A 00 56 50",
        target = SignatureTarget.Embedded(1)
    )

    public val BulletMan: Signature = Signature.parse(
        name = "BulletMan",
        pattern = "8B 57 04 83 FA FF 74 ?? A1 ?? ?? ?? ?? 56 E8",
        target = SignatureTarget.Embedded(9)
    )

    /** Entry of GameLoop_StepFrame */
    public val GameLoopStep: Signature = Signature.parse(
        name = "GameLoopStep",
        pattern = "51 A1 ?? ?? ?? ?? 56 57 85 C0 74 17 80 78 18 00 74 05 8B 40 14 EB 03 8B 40 10"
    )

    public val PadPolled: Signature = Signature.parse(
        name = "PadPolled",
        pattern = "8B 07 8B 50 54 8B CF FF D2 84 C0 75 0B 8B 07 8B 50 2C 8B CF FF D2 EB 05 C6 44 24 13 01 83 C3 04"
    )

    public val InputMan: Signature = Signature.parse(
        name = "InputMan",
        pattern = "8B 4E 20 88 46 14 A1 ?? ?? ?? ?? F3 0F 10 40 34 57 8B 7E 04",
        target = SignatureTarget.Embedded(7)
    )

    private const val FILE_MAN_UPDATE = "8B 3D ?? ?? ?? ?? 6A 00 57 89 7C 24 28 E8 ?? ?? ?? ?? FF 47 58"
    public val FileMan: Signature = Signature.parse(
        name = "FileMan",
        pattern = FILE_MAN_UPDATE,
        target = SignatureTarget.Embedded(2)
    )

    public val FileManPoll: Signature = Signature.parse(
        name = "FileManPoll",
        pattern = FILE_MAN_UPDATE,
        target = SignatureTarget.Relative(
            at = 0x0E,
            instructionEnd = 0x12
        )
    )

    public val FileRings: Signature = Signature.parse(
        name = "FileRings",
        pattern = "A1 ?? ?? ?? ?? 6A 01 50 C6 44 24 1B 01 E8 ?? ?? ?? ?? BD ?? ?? ?? ??",
        target = SignatureTarget.Embedded(0x13)
    )
    /**
     * 'ProcessMoveManStepFrame's. It turns on FileMan_UpdateFrame's wall-time
     * budget every in-game frame
     */
    public val LoadBudgetWrite: Signature = Signature.parse(
        name = "LoadBudgetWrite",
        pattern = "8B 15 ?? ?? ?? ?? 8B 0D ?? ?? ?? ?? F3 0F 10 15 ?? ?? ?? ?? 89 42 24 A1 ?? ?? ?? ?? C6 40 14 01 C6 41 68 01"
    )

    public val SleepImport: Signature = Signature.parse(
        name = "SleepImport",
        pattern = "6A 00 FF 15 ?? ?? ?? ?? C3 CC CC",
        target = SignatureTarget.Embedded(4)
    )
}
