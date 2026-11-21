package com.sappyoak.dsanalyzer.runtime.write.debugdraw.ptde

import com.sappyoak.dsanalyzer.native.memory.Address
import com.sappyoak.dsanalyzer.runtime.inject.HookSite
import com.sappyoak.dsanalyzer.runtime.inject.Mem.Absolute
import com.sappyoak.dsanalyzer.runtime.inject.Mem.Based
import com.sappyoak.dsanalyzer.runtime.inject.Reg.EAX
import com.sappyoak.dsanalyzer.runtime.inject.Reg.EBX
import com.sappyoak.dsanalyzer.runtime.inject.Reg.EDX
import com.sappyoak.dsanalyzer.runtime.inject.Reg.ESP
import com.sappyoak.dsanalyzer.runtime.inject.X86
import com.sappyoak.dsanalyzer.runtime.inject.X86Cave
import com.sappyoak.dsanalyzer.runtime.inject.callTarget
import com.sappyoak.dsanalyzer.runtime.inject.cmp
import com.sappyoak.dsanalyzer.runtime.inject.lea
import com.sappyoak.dsanalyzer.runtime.inject.pop
import com.sappyoak.dsanalyzer.runtime.inject.push
import com.sappyoak.dsanalyzer.runtime.inject.pushDword
import com.sappyoak.dsanalyzer.runtime.ptde.DrawHandles
import com.sappyoak.dsanalyzer.runtime.ptde.PTDEHookSites
import com.sappyoak.dsanalyzer.runtime.ptde.Graphics
import com.sappyoak.dsanalyzer.runtime.ptde.PlanWalker
import com.sappyoak.dsanalyzer.runtime.snapshot.WriteKey

/**
 * Moves the retargeted plan to the end of the frame: the walker skips it where it would run, and
 * pushes it again right after the post chain, so it draws over the finished scene. The plan's
 * entity is looked up through the walker's own registry each time, as the game does.
 *
 * The post chain's handle is read from [POST_CHAIN] in the control block at [control] rather than built in:
 * a load screen remakes the chain under a new handle, and the backend rewrites the word after each load
 */
internal class PlanSplice(private val control: Address) : X86Cave() {
    override val key: WriteKey = WriteKey("debugdraw:plan-splice")
    override val site: HookSite = PTDEHookSites.PlanWalkerPush

    override fun X86.emit(at: Address) {
        val push = callTarget(at, site.expected)
        val skip = label()
        val done = label()

        mov(EDX, Based(ESP, 0))
        mov(EDX, Based(EDX, 0))
        cmp(EDX, DrawHandles.RetargetPlan)
        jz(skip)

        push(EDX)
        push(Based(ESP, 4))
        call(push)
        pop(EDX)
        add(ESP, 4)
        cmp(EDX, Absolute(control + POST_CHAIN.toLong()))
        jnz(done)

        push(0.toByte())
        mov(EAX, Based(EBX, PlanWalker.Registry))
        mov(EAX, Based(EAX, Graphics.Slots))
        mov(EAX, Based(EAX, Graphics.Table))
        mov(EAX, Based(EAX, (DrawHandles.RetargetPlan and DrawHandles.INDEX_MASK) * Int.SIZE_BYTES))
        push(EAX)
        pushDword(DrawHandles.RetargetPlan)
        push(ESP)
        lea(EAX, Based(EBX, PlanWalker.Stack))
        call(push)
        add(ESP, PlanWalker.ENTRY_SIZE)
        jmp(done)

        bind(skip)
        add(ESP, 4)

        bind(done)
        jmp(at + site.displaced.toLong())
    }
}
