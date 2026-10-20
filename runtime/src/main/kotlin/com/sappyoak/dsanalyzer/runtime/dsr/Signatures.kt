package com.sappyoak.dsanalyzer.runtime.dsr

import com.sappyoak.dsanalyzer.native.memory.Signature
import com.sappyoak.dsanalyzer.native.memory.SignatureTarget

public object DSRSignatures {
    public val WorldChrMan: Signature = Signature.parse(
        name = "WorldChrManImp",
        pattern = "48 8B 0D ?? ?? ?? ?? 83 CB FF 8B FB 48 85 C9",
        target = SignatureTarget.Relative(at = 3, instructionEnd = 7)
    )

    public val FieldArea: Signature = Signature.parse(
        name = "FieldArea",
        pattern = "48 8B 05 ?? ?? ?? ?? 48 85 C0 48 0F 44 C6 48 89 05 ?? ?? ?? ??",
        target = SignatureTarget.Relative(at = 3, instructionEnd = 7)
    )

    public val GameDataMan: Signature = Signature.parse(
        name = "GameDataMan",
        pattern = "48 8B 05 ?? ?? ?? ?? BB 0B 00 00 00 48 8B 48 10",
        target = SignatureTarget.Relative(at = 3, instructionEnd = 7)
    )

    public val MenuMan: Signature = Signature.parse(
        name = "MenuMan",
        pattern = "48 8B 05 ?? ?? ?? ?? 48 63 D1 8B 44 90 30 C3",
        target = SignatureTarget.Relative(at = 3, instructionEnd = 7)
    )

    public val FrpgHavokManImp: Signature = Signature.parse(
        name = "FrpgHavokManImp",
        pattern = "4C 8B 05 ?? ?? ?? ?? 49 8B 58 60 49 8B 78 68",
        target = SignatureTarget.Relative(at = 3, instructionEnd = 7)
    )
}