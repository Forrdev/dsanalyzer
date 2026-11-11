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

    public val WorldHitMan: Signature = Signature.parse(
        name = "WorldHitMan",
        pattern = "48 8B 05 ?? ?? ?? ?? 89 48 18 4C 8D 45 80 49 8B D7 48 8B 0D",
        target = SignatureTarget.Relative(at = 3, instructionEnd = 7)
    )

    public val AttackMan: Signature = Signature.parse(
        name = "AttackMan",
        pattern = "48 8B 05 ?? ?? ?? ?? 0F 2F 48 1C 0F 86 A6 01 00 00 8B 7E 60",
        target = SignatureTarget.Relative(at = 3, instructionEnd = 7)
    )

    /** The renderer global the EzDraw layers hang off */
    public val GraphicsMan: Signature = Signature.parse(
        name = "GraphicsMan",
        pattern = "48 8B 05 ?? ?? ?? ?? 48 8B 40 08 C3 55 48 BD 05 1F 06 40 01",
        target = SignatureTarget.Relative(at = 3, instructionEnd = 7)
    )

    /** 'HitIns_DebugDraw': the lo mesh's display test */
    public val LoDisplay: Signature = Signature.parse(
        name = "LoDisplay",
        pattern = "0F B6 83 C0 00 00 00 A8 02 75 04 A8 10 74 10 E8"
    )

    /** 'HitIns_DebugDraw': the hi mesh's display test */
    public val HiDisplay: Signature = Signature.parse(
        name = "HiDisplay",
        pattern = "0F B6 83 C0 00 00 00 A8 04 75 04 A8 20 74 10 E8"
    )

    /**
     * 'HitIns_DebugDraw': the bounding box's color, the `.rdata` constant the rigid draws' colors
     * sit beside
     */
    public val HitColors: Signature = Signature.parse(
        name = "HitColors",
        pattern = "F6 81 C0 00 00 00 09 74 41 0F 28 05 ?? ?? ?? ??",
        target = SignatureTarget.Relative(at = 12, instructionEnd = 16)
    )

    /** 'EzDraw_FlushTriangles': the wire draw's rasterizer state, picked from the device's table */
    public val FlushWireRaster: Signature = Signature.parse(
        name = "FlushWireRaster",
        pattern = "C7 82 88 22 00 00 03 00 00 00 48 8B 88 80 00 00 00 48 8B 99 70 02 00 00"
    )

    /** 'EzDraw_FlushTriangles': the fill draw's rasterizer state */
    public val FlushFillRaster: Signature = Signature.parse(
        name = "FlushFillRaster",
        pattern = "C7 82 88 22 00 00 04 00 00 00 48 8B 88 80 00 00 00 48 8B 99 78 02 00 00"
    )

    /** 'EzDraw_FlushTriangles': just after the fill draw */
    public val FlushFillDrawn: Signature = Signature.parse(
        name = "FlushFillDrawn",
        pattern = "E8 ?? ?? ?? ?? 4C 8B 47 20 48 8B 57 10 48 8B 4F 40 4D 8D 48 30 49 83 C0 20 E8 ?? ?? ?? ?? 48 8B 74 24 40"
    )

    /** 'EzDrawFsHkPlusDc_AddTriangle' */
    public val AddTriangle: Signature = Signature.parse(
        name = "AddTriangle",
        pattern = "40 53 48 8B 59 18 44 8B D2 4C 8B D9 48 85 DB 0F 84"
    )

    /** In 'HgMan_BuildWorldDrawPlan', just before the first pass's `EndTargetCamera` is emitted */
    public val WorldPlanSceneEnd: Signature = Signature.parse(
        name = "WorldPlanSceneEnd",
        pattern = "8B CE E8 ?? ?? ?? ?? 44 8B C6 BA 16 00 00 00 48 8B CF E8 ?? ?? ?? ?? 48 8B 0F 48 83 C1 03 " +
                "48 83 E1 FC 48 8D 41 04 48 89 07 C7 01 04 00 02 00"
    )
}
