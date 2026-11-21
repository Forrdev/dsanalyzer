package com.sappyoak.dsanalyzer.runtime.write.debugdraw.ptde

import com.sappyoak.dsanalyzer.native.memory.Address
import com.sappyoak.dsanalyzer.runtime.inject.HookSite
import com.sappyoak.dsanalyzer.runtime.inject.Mem.Absolute
import com.sappyoak.dsanalyzer.runtime.inject.Mem.Based
import com.sappyoak.dsanalyzer.runtime.inject.Reg.EAX
import com.sappyoak.dsanalyzer.runtime.inject.Reg.ESP
import com.sappyoak.dsanalyzer.runtime.inject.X86
import com.sappyoak.dsanalyzer.runtime.inject.Xmm
import com.sappyoak.dsanalyzer.runtime.inject.Xmm.XMM0
import com.sappyoak.dsanalyzer.runtime.inject.Xmm.XMM1
import com.sappyoak.dsanalyzer.runtime.inject.addss
import com.sappyoak.dsanalyzer.runtime.inject.X86Cave
import com.sappyoak.dsanalyzer.runtime.inject.comiss
import com.sappyoak.dsanalyzer.runtime.inject.jbe
import com.sappyoak.dsanalyzer.runtime.inject.movss
import com.sappyoak.dsanalyzer.runtime.inject.mulss
import com.sappyoak.dsanalyzer.runtime.inject.pop
import com.sappyoak.dsanalyzer.runtime.inject.subss
import com.sappyoak.dsanalyzer.runtime.ptde.AddTriangleArgs
import com.sappyoak.dsanalyzer.runtime.ptde.PTDEHookSites
import com.sappyoak.dsanalyzer.runtime.snapshot.WriteKey
import com.sappyoak.dsanalyzer.runtime.write.debugdraw.DrawControl

/** The cave's own `PUSH EAX` moves every argument down a slot */
private const val SAVED = Int.SIZE_BYTES

/**
 * Collapses a triangle whose first corner is farther from the player than the radius: v1 and v2
 * are pointed at v0, so its ring slot is still written but it has no area. A slot is never skipped,
 * because the flush counts on every index being filled
 */
internal class DistanceCull(private val control: Address) : X86Cave() {
    override val key: WriteKey = WriteKey("debugdraw:distance")
    override val site: HookSite = PTDEHookSites.AddTriangle

    override fun X86.emit(at: Address) {
        val keep = label()

        push(EAX)
        mov(EAX, Based(ESP, AddTriangleArgs.V0 + SAVED))
        axis(XMM0, 0)
        for (component in 1 until 3) {
            axis(XMM1, component)
            addss(XMM0, XMM1)
        }
        comiss(XMM0, field(DrawControl.RadiusSquared))
        jbe(keep)
        mov(Based(ESP, AddTriangleArgs.V1 + SAVED), EAX)
        mov(Based(ESP, AddTriangleArgs.V2 + SAVED), EAX)

        bind(keep)
        pop(EAX)
        raw(site.expected)
        jmp(at + site.displaced.toLong())
    }

    /** `(v0[component] - player[component])²` into [to] */
    private fun X86.axis(to: Xmm, component: Int) {
        movss(to, Based(EAX, component * Float.SIZE_BYTES))
        subss(to, field(DrawControl.Position + component * Float.SIZE_BYTES))
        mulss(to, to)
    }

    private fun field(offset: Int): Absolute = Absolute(control + offset.toLong())
}
