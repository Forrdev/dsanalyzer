package com.sappyoak.dsanalyzer.runtime.ptde

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

    public val CharData2: Signature = Signature.parse(
        name = "CharData2",
        pattern = "A1 ?? ?? ?? ?? 8B 40 34 53 32 DB 85 C0",
        target = SignatureTarget.Embedded(1)
    )

    public val WorldState: Signature = Signature.parse(
        name = "WorldState",
        pattern = "8B 54 24 10 8B C8 F7 D9 39 8A B8 0E 00 00 B3 01 0F 95 C2 8B 0D ?? ?? ?? ?? 80 B9 A5 0B 00 00 00",
        target = SignatureTarget.Embedded(0x15)
    )

    public val WorldArea: Signature = Signature.parse(
        name = "WorldArea",
        pattern = "8B 48 04 8B 11 50 8B 42 34 FF D0 C7 46 2C 00 00 00 00 C3 8B 0D ?? ?? ?? ?? " +
                "8B 89 50 0B 00 00 32 C0 83 79 28 00",
        target = SignatureTarget.Embedded(0x15)
    )

    public val DeathCam: Signature = Signature.parse(
        name = "DeathCam",
        pattern = "88 5D 15 88 5D 16 88 5D 17 A1 ?? ?? ?? ?? 3B C3",
        target = SignatureTarget.Embedded(0xA)
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
}