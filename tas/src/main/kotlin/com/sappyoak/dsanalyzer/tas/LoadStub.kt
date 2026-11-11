package com.sappyoak.dsanalyzer.tas

import com.sappyoak.dsanalyzer.native.memory.Address
import com.sappyoak.dsanalyzer.runtime.inject.Label
import com.sappyoak.dsanalyzer.runtime.inject.Mem
import com.sappyoak.dsanalyzer.runtime.inject.Reg
import com.sappyoak.dsanalyzer.runtime.inject.X86
import com.sappyoak.dsanalyzer.runtime.ptde.FileRing
import com.sappyoak.dsanalyzer.tas.Mailbox.Field

/** Where the barrier reads and calls: the `FileMan` global, its completion poll, the ring array */
internal class LoadSites(val fileMan: Address, val poll: Address, val rings: Address)

/**
 * The load barrier, run at the top of a frame that is about to run, borrowed from
 * the game's own 'FileMan_Shutdown_DrainAll
 */
internal fun X86.loadBarrier(mailbox: Mailbox, sites: LoadSites, sleep: Mem, done: Label) {
    val poll = label()
    val waiting = label()
    val ring = { offset: Int -> Mem.Based(Reg.ESI, offset) }

    cmp(mailbox.operand(Field.LoadBarrier), 0)
    jz(done)
    bind(poll)
    mov(Reg.EAX, Mem.Absolute(sites.fileMan))
    cmp(Reg.EAX, 0)
    jz(done)
    push(0)
    push(Reg.EAX)
    call(sites.poll)

    repeat(FileRing.Count) { index ->
        val scan = label()
        val next = label()
        val stepped = label()
        mov(Reg.ESI, Mem.Absolute(sites.rings + (index * Int.SIZE_BYTES).toLong()))
        cmp(Reg.ESI, 0)
        jz(next)
        mov(Reg.ECX, ring(FileRing.Head))
        bind(scan)
        cmp(Reg.ECX, ring(FileRing.Tail))
        jz(next)
        mov(Reg.EAX, Reg.ECX)
        shl(Reg.EAX, 2)
        add(Reg.EAX, ring(FileRing.Slots))
        mov(Reg.EAX, Mem.Based(Reg.EAX, 0))
        cmpByte(Mem.Based(Reg.EAX, FileRing.CapsuleState), FileRing.Requested.toByte())
        jz(waiting)
        inc(Reg.ECX)
        cmp(Reg.ECX, ring(FileRing.Capacity))
        jb(stepped)
        mov(Reg.ECX, 0)
        bind(stepped)
        jmp(scan)
        bind(next)
    }
    jmp(done)

    bind(waiting)
    push(1)
    call(sleep)
    jmp(poll)
}
