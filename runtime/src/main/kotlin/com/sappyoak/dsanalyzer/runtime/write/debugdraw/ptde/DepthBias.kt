package com.sappyoak.dsanalyzer.runtime.write.debugdraw.ptde

import com.sappyoak.dsanalyzer.native.memory.Address
import com.sappyoak.dsanalyzer.runtime.inject.HookSite
import com.sappyoak.dsanalyzer.runtime.inject.Mem.Absolute
import com.sappyoak.dsanalyzer.runtime.inject.Mem.Based
import com.sappyoak.dsanalyzer.runtime.inject.Reg.EAX
import com.sappyoak.dsanalyzer.runtime.inject.Reg.EBP
import com.sappyoak.dsanalyzer.runtime.inject.Reg.EDI
import com.sappyoak.dsanalyzer.runtime.inject.X86
import com.sappyoak.dsanalyzer.runtime.inject.X86Cave
import com.sappyoak.dsanalyzer.runtime.inject.callTarget
import com.sappyoak.dsanalyzer.runtime.inject.push
import com.sappyoak.dsanalyzer.runtime.inject.pushDword
import com.sappyoak.dsanalyzer.runtime.ptde.D3D9Device
import com.sappyoak.dsanalyzer.runtime.ptde.PTDEHookSites
import com.sappyoak.dsanalyzer.runtime.ptde.EzDrawFlush
import com.sappyoak.dsanalyzer.runtime.snapshot.WriteKey
import com.sappyoak.dsanalyzer.runtime.write.debugdraw.DrawControl

/**
 * Render states set around EzDraw's flush, as (D3DRENDERSTATETYPE, value): the fill pulled toward
 * the camera so it stops z-fighting the map it was built from, then alpha blending
 */
private val STATES: List<Pair<Int, Int>> = listOf(
    195 to (-0.0001f).toRawBits(), // DEPTHBIAS
    175 to (-1.0f).toRawBits(),    // SLOPESCALEDEPTHBIAS
    27 to 1,                       // ALPHABLENDENABLE
    19 to 5,                       // SRCBLEND = SRCALPHA
    20 to 6                        // DESTBLEND = INVSRCALPHA
)

private fun X86.loadDevice() {
    mov(EDI, Based(EBP, EzDrawFlush.DevicePath.first()))
    EzDrawFlush.DevicePath.drop(1).forEach { mov(EDI, Based(EDI, it)) }
}

private fun X86.callDevice(slot: Int) {
    push(EDI)
    mov(EAX, Based(EDI, 0))
    call(Based(EAX, slot))
}

private fun saved(control: Address, index: Int): Address =
    control + (DrawControl.Scratch + index * Int.SIZE_BYTES).toLong()

/** Before the flush draws: save each state, then set it */
internal class DepthBiasOn(private val control: Address) : X86Cave() {
    override val key: WriteKey = WriteKey("debugdraw:bias-on")
    override val site: HookSite = PTDEHookSites.FlushBegin

    override fun X86.emit(at: Address) {
        pushad()
        loadDevice()
        STATES.forEachIndexed { i, (state, value) ->
            pushDword(saved(control, i).value.toInt())
            pushDword(state)
            callDevice(D3D9Device.GetRenderState)
            pushDword(value)
            pushDword(state)
            callDevice(D3D9Device.SetRenderState)
        }
        popad()
        raw(site.expected)
        jmp(at + site.displaced.toLong())
    }
}

/**
 * After the flush draws: put each state back, then make the displaced call as a tail call, so no
 * return address into the cave is ever left on the game's stack
 */
internal class DepthBiasOff(private val control: Address) : X86Cave() {
    override val key: WriteKey = WriteKey("debugdraw:bias-off")
    override val site: HookSite = PTDEHookSites.FlushEnd

    override fun X86.emit(at: Address) {
        pushad()
        loadDevice()
        STATES.forEachIndexed { i, (state, _) ->
            push(Absolute(saved(control, i)))
            pushDword(state)
            callDevice(D3D9Device.SetRenderState)
        }
        popad()
        pushDword((at + site.displaced.toLong()).value.toInt())
        jmp(callTarget(at, site.expected))
    }
}
