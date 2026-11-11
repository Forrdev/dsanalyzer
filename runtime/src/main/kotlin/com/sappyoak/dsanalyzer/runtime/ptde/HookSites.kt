package com.sappyoak.dsanalyzer.runtime.ptde

import com.sappyoak.dsanalyzer.runtime.inject.HookSite
import com.sappyoak.dsanalyzer.runtime.inject.code

public class DisplayTest(public val site: HookSite, public val bits: Int) {
    public val draw: Int get() = site.displaced
    public val skip: Int get() = site.displaced + site.expected.last().toInt()
}

public object PTDEHookSites {
    public val GameLoopStep: HookSite = HookSite(
        signature = PTDESignatures.GameLoopStep,
        offset = 0,
        expected = code(0x51, 0xA1, 0x64, 0x55, 0x37, 0x01)
    )

    public val PadPolled: HookSite = HookSite(
        signature = PTDESignatures.PadPolled,
        offset = 24,
        expected = code(0xC6, 0x44, 0x24, 0x13, 0x01)
    )

    public val LoadBudgetImmediate: Int = 35

    /** The walker's `CALL push(entry)`: stdcall, the entry's address on the stack, the walker's stack in EAX */
    public val PlanWalkerPush: HookSite = HookSite(
        PTDESignatures.PlanWalkerPush,
        offset = 0x20,
        expected = code(0xE8, 0x57, 0x0C, 0x52, 0xFF)
    )

    /** `MOV ESI,[EBP+0x18]; MOV ECX,[ESI+0x18]`, just before the flush draws */
    public val FlushBegin: HookSite = HookSite(
        PTDESignatures.FlushBegin,
        offset = 0x0C,
        expected = code(0x8B, 0x75, 0x18, 0x8B, 0x4E, 0x18)
    )

    /** The `CALL` that resets EzDraw's state after the flush has drawn */
    public val FlushEnd: HookSite = HookSite(
        PTDESignatures.FlushEnd,
        offset = 0x0E,
        expected = code(0xE8, 0x7E, 0x2F, 0x4C, 0xFF)
    )

    public val LoDisplay: DisplayTest = displayTest(offset = 0xBE, bits = 0x12, skip = 0x16)
    public val HiDisplay: DisplayTest = displayTest(offset = 0x174, bits = 0x24, skip = 0x0B)

    /** The first color load of each rigid draw; the build ends at the wire color's `CALL` [ColorRejoin] bytes on */
    public val LoColor: HookSite = HookSite(
        PTDESignatures.LoColor,
        offset = 0,
        expected = COLOR_LOAD
    )
    public val HiColor: HookSite = HookSite(
        PTDESignatures.HiColor,
        offset = 0,
        expected = COLOR_LOAD
    )
    public const val ColorRejoin: Int = 0x73

    /** `PUSH EBX; MOV EBX,[ECX+0xC]; TEST EBX,EBX` */
    public val AddTriangle: HookSite = HookSite(
        PTDESignatures.AddTriangle,
        offset = 0,
        expected = code(0x53, 0x8B, 0x59, 0x0C, 0x85, 0xDB)
    )

    private fun displayTest(offset: Int, bits: Int, skip: Int): DisplayTest = DisplayTest(
        HookSite(PTDESignatures.HitInsDebugDraw, offset, code(0xF6, 0x86, HitIns.Display, 0x00, 0x00, 0x00, bits, 0x74, skip)),
        bits
    )
}


private val COLOR_LOAD = code(0xF3, 0x0F, 0x10, 0x0D, 0x2C, 0x50, 0x1E, 0x01)
