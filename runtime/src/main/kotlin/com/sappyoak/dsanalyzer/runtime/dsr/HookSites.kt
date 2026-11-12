package com.sappyoak.dsanalyzer.runtime.dsr

import com.sappyoak.dsanalyzer.native.memory.Signature
import com.sappyoak.dsanalyzer.runtime.inject.HookSite
import com.sappyoak.dsanalyzer.runtime.inject.PlayerGate
import com.sappyoak.dsanalyzer.runtime.inject.StepTableSite
import com.sappyoak.dsanalyzer.runtime.inject.code

public class DisplayTest(public val site: HookSite, public val bits: Int) {
    public val draw: Int get() = 0x0F
    public val skip: Int get() = 0x1F
}

public object DSRHookSites {
    /**
     * 'SysStep': FrpgSystem +0x8 is its task, whose +0x20 is the step. It lives for the whole
     * process and runs step 8 once per frame on the logic thread, menus and loads included. A heap
     * pointer rather than code.
     */
    public val Mailbox: StepTableSite = StepTableSite(
        root = DSRSignatures.FrpgSystem,
        path = listOf(0L, 0x8L, 0x20L),
        table = 0x20,
        slot = 8
    )

    public val MailboxGate: PlayerGate = PlayerGate(DSRSignatures.WorldChrMan, WorldChrMan.PlayerPointer)

    /**
     * `MOV R8D,ESI; MOV EDX,0x16` ahead of the first pass's `EndTargetCamera`. RDI is the plan
     * stream and ESI the plan's handle. The builder runs on every load screen
     */
    public val WorldPlanSceneEnd: HookSite = HookSite(
        DSRSignatures.WorldPlanSceneEnd,
        offset = 7,
        expected = code(0x44, 0x8B, 0xC6, 0xBA, 0x16, 0x00, 0x00, 0x00)
    )

    /**
     * From [WorldPlanSceneEnd] back to the builder's own `CALL emitScene(stream, plan, scene)`,
     * which writes `BeginTargetScene` and `EndTargetScene`; a cave reads its target from here
     */
    public const val SceneEmitCall: Int = -0x0C

    public val LoDisplay: DisplayTest = displayTest(DSRSignatures.LoDisplay, bits = 0x12)
    public val HiDisplay: DisplayTest = displayTest(DSRSignatures.HiDisplay, bits = 0x24)

    /** `MOV RBX,[RCX+0x270]`: the wire draw's rasterizer wrapper. RSI is the context wrapper */
    public val FlushWireRaster: HookSite = HookSite(
        DSRSignatures.FlushWireRaster,
        offset = 0x11,
        expected = code(0x48, 0x8B, 0x99, 0x70, 0x02, 0x00, 0x00)
    )

    /** `MOV RBX,[RCX+0x278]`: the fill draw's */
    public val FlushFillRaster: HookSite = HookSite(
        DSRSignatures.FlushFillRaster,
        offset = 0x11,
        expected = code(0x48, 0x8B, 0x99, 0x78, 0x02, 0x00, 0x00)
    )

    /** `MOV R8,[RDI+0x20]; MOV RDX,[RDI+0x10]` after the fill draw; RSI is still the context wrapper */
    public val FlushFillDrawn: HookSite = HookSite(
        DSRSignatures.FlushFillDrawn,
        offset = 5,
        expected = code(0x4C, 0x8B, 0x47, 0x20, 0x48, 0x8B, 0x57, 0x10)
    )

    /** `PUSH RBX; MOV RBX,[RCX+0x18]`: RCX visitor, EDX index, R8 and R9 v0 and v1, `[RSP+0x28]` v2 */
    public val AddTriangle: HookSite = HookSite(
        DSRSignatures.AddTriangle,
        offset = 0,
        expected = code(0x40, 0x53, 0x48, 0x8B, 0x59, 0x18)
    )

    private fun displayTest(signature: Signature, bits: Int): DisplayTest =
        DisplayTest(HookSite(signature, offset = 0, expected = code(0x0F, 0xB6, 0x83, HitIns.Display, 0x00, 0x00, 0x00)), bits)
}
