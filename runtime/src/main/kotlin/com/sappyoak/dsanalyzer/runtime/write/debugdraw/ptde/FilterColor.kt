package com.sappyoak.dsanalyzer.runtime.write.debugdraw.ptde

import com.sappyoak.dsanalyzer.native.memory.Address
import com.sappyoak.dsanalyzer.runtime.inject.HookSite
import com.sappyoak.dsanalyzer.runtime.inject.Mem.Based
import com.sappyoak.dsanalyzer.runtime.inject.Reg.EAX
import com.sappyoak.dsanalyzer.runtime.inject.Reg.EBX
import com.sappyoak.dsanalyzer.runtime.inject.Reg.EDI
import com.sappyoak.dsanalyzer.runtime.inject.Reg.ESI
import com.sappyoak.dsanalyzer.runtime.inject.Reg.ESP
import com.sappyoak.dsanalyzer.runtime.inject.X86
import com.sappyoak.dsanalyzer.runtime.inject.Xmm.XMM0
import com.sappyoak.dsanalyzer.runtime.inject.X86Cave
import com.sappyoak.dsanalyzer.runtime.inject.lea
import com.sappyoak.dsanalyzer.runtime.inject.movups
import com.sappyoak.dsanalyzer.runtime.inject.movzxByte
import com.sappyoak.dsanalyzer.runtime.inject.test
import com.sappyoak.dsanalyzer.runtime.ptde.PTDEHookSites
import com.sappyoak.dsanalyzer.runtime.ptde.HitResDraw
import com.sappyoak.dsanalyzer.runtime.ptde.HitIns
import com.sappyoak.dsanalyzer.runtime.snapshot.WriteKey
import com.sappyoak.dsanalyzer.runtime.write.debugdraw.DrawControl
import com.sappyoak.dsanalyzer.runtime.write.debugdraw.FILTER_BITS

/** log2 of [DrawControl.COLOR_STRIDE] */
private const val COLOR_SHIFT: Byte = 5

/**
 * Replaces a rigid draw's constant color build with the part's own pair from the control block's
 * table, picked by the filter in the part's tag. An untagged part, or no 'HitRes', takes entry 0
 */
internal class FilterColor(override val site: HookSite, name: String, private val control: Address) : X86Cave() {
    override val key: WriteKey = WriteKey("debugdraw:color-$name")

    override fun X86.emit(at: Address) {
        val pick = label()

        mov(EDI, Based(EBX, HitResDraw.StateBlock))
        mov(EAX, 0)
        test(ESI, ESI)
        jz(pick)
        mov(EAX, Based(ESI, HitResDraw.Owner))
        movzxByte(EAX, Based(EAX, HitIns.Tag))
        and(EAX, FILTER_BITS)
        shl(EAX, COLOR_SHIFT)

        bind(pick)
        add(EAX, (control + DrawControl.Colors.toLong()).value.toInt())
        movups(XMM0, Based(EAX, 0))
        movups(Based(ESP, HitResDraw.Wire), XMM0)
        movups(XMM0, Based(EAX, Float.SIZE_BYTES * 4))
        movups(Based(ESP, HitResDraw.Fill), XMM0)
        lea(EAX, Based(ESP, HitResDraw.Wire))
        push(EAX)
        jmp(at + PTDEHookSites.ColorRejoin.toLong())
    }
}
