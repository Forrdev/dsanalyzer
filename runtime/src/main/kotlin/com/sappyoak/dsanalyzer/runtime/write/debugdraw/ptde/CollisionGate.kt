package com.sappyoak.dsanalyzer.runtime.write.debugdraw.ptde

import com.sappyoak.dsanalyzer.native.memory.Address
import com.sappyoak.dsanalyzer.runtime.inject.HookSite
import com.sappyoak.dsanalyzer.runtime.inject.Label
import com.sappyoak.dsanalyzer.runtime.inject.Mem.Absolute
import com.sappyoak.dsanalyzer.runtime.inject.Mem.Based
import com.sappyoak.dsanalyzer.runtime.inject.Reg.EAX
import com.sappyoak.dsanalyzer.runtime.inject.Reg.ECX
import com.sappyoak.dsanalyzer.runtime.inject.Reg.ESI
import com.sappyoak.dsanalyzer.runtime.inject.X86
import com.sappyoak.dsanalyzer.runtime.inject.bt
import com.sappyoak.dsanalyzer.runtime.inject.X86Cave
import com.sappyoak.dsanalyzer.runtime.inject.movzxByte
import com.sappyoak.dsanalyzer.runtime.inject.pop
import com.sappyoak.dsanalyzer.runtime.inject.test
import com.sappyoak.dsanalyzer.runtime.inject.testByte
import com.sappyoak.dsanalyzer.runtime.ptde.ChrCtrl
import com.sappyoak.dsanalyzer.runtime.ptde.ChrIns
import com.sappyoak.dsanalyzer.runtime.ptde.ChrPosData
import com.sappyoak.dsanalyzer.runtime.ptde.DisplayTest
import com.sappyoak.dsanalyzer.runtime.ptde.HitIns
import com.sappyoak.dsanalyzer.runtime.ptde.WorldChrMan
import com.sappyoak.dsanalyzer.runtime.snapshot.DrawMode
import com.sappyoak.dsanalyzer.runtime.snapshot.WriteKey
import com.sappyoak.dsanalyzer.runtime.write.debugdraw.DrawControl
import com.sappyoak.dsanalyzer.runtime.write.debugdraw.FILTER_BITS
import com.sappyoak.dsanalyzer.runtime.write.debugdraw.TAGGED
import com.sappyoak.dsanalyzer.runtime.write.debugdraw.bits


/**
 * Decides which parts draw at one mesh's [test]. The game's own bits still draw what it draws; on
 * top of that a part draws when it is tagged, its filter is on in the mask, and the mode includes
 * [mode]. A part that draws also refreshes the player position the distance cull measures from
 */
internal class CollisionGate(
    private val test: DisplayTest,
    private val mode: DrawMode,
    private val control: Address,
    /** The global holding 'WorldChrManImp' */
    private val characters: Address
) : X86Cave() {
    override val key: WriteKey = WriteKey("debugdraw:gate-${mode.name.lowercase()}")
    override val site: HookSite = test.site

    override fun X86.emit(at: Address) {
        val draw = label()
        val skip = label()

        testByte(Based(ESI, HitIns.Display), test.bits)
        jnz(draw)
        movzxByte(EAX, Based(ESI, HitIns.Tag))
        test(EAX, TAGGED)
        jz(skip)
        testByte(Absolute(control + DrawControl.Mode.toLong()), mode.bits)
        jz(skip)
        and(EAX, FILTER_BITS)
        bt(Absolute(control + DrawControl.Mask.toLong()), EAX)
        jae(skip)

        bind(draw)
        cachePlayerPosition()
        jmp(at + test.draw.toLong())

        bind(skip)
        jmp(at + test.skip.toLong())
    }

    /** `[[[[[characters]+player]+ChrCtrl]+position data]+position]`, three floats; any null leaves the last */
    private fun X86.cachePlayerPosition() {
        val absent = label()
        push(ECX)
        mov(ECX, Absolute(characters))
        for (offset in listOf(WorldChrMan.PlayerPointer, ChrIns.ChrCtrlPointer, ChrCtrl.PositionPointer)) {
            nonNull(absent)
            mov(ECX, Based(ECX, offset))
        }
        nonNull(absent)
        for (axis in 0 until 3) {
            mov(EAX, Based(ECX, ChrPosData.Position + axis * Float.SIZE_BYTES))
            mov(Absolute(control + (DrawControl.Position + axis * Float.SIZE_BYTES).toLong()), EAX)
        }
        bind(absent)
        pop(ECX)
    }

    private fun X86.nonNull(absent: Label) {
        test(ECX, ECX)
        jz(absent)
    }
}
