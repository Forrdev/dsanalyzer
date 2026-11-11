package com.sappyoak.dsanalyzer.tas

import com.sappyoak.dsanalyzer.runtime.inject.Label
import com.sappyoak.dsanalyzer.runtime.inject.Mem
import com.sappyoak.dsanalyzer.runtime.inject.Reg
import com.sappyoak.dsanalyzer.runtime.inject.X86
import com.sappyoak.dsanalyzer.runtime.ptde.GameMan
import com.sappyoak.dsanalyzer.tas.Mailbox.Companion.BANK_SHIFT
import com.sappyoak.dsanalyzer.tas.Mailbox.Companion.LOG_MASK
import com.sappyoak.dsanalyzer.tas.Mailbox.Companion.LOG_SHIFT
import com.sappyoak.dsanalyzer.tas.Mailbox.Field
import com.sappyoak.dsanalyzer.tas.Mailbox.Slot


/** Milliseconds the held game sleeps between looks at the mailbox */
private const val HELD_POLL_MS: Byte = 1

internal fun X86.gate(mailbox: Mailbox, sleep: Mem, gameMan: Mem, loads: LoadSites?) {
    val evaluated = label()
    val equal = label()
    val match = label()
    val seed = label()
    val noMatch = label()
    val wait = label()
    val held = label()
    val sleeping = label()
    val run = label()
    val logged = label()
    val field = mailbox::operand
    val slot = { s: Slot -> Mem.Based(Reg.EBX, s.offset) }

    pushfd()
    pushad()
    mov(Reg.EAX, field(Field.TriggerArm))
    mov(Reg.EBX, Reg.EAX)
    and(Reg.EBX, 1)
    shl(Reg.EBX, BANK_SHIFT.toByte())
    add(Reg.EBX, mailbox.bank(0).value.toInt())

    mov(Reg.ECX, slot(Slot.Base))
    Slot.Offsets.forEachIndexed { depth, offset ->
        cmp(slot(Slot.Depth), (depth + 1).toByte())
        jb(evaluated)
        cmp(Reg.ECX, 0)
        jz(noMatch)
        add(Reg.ECX, slot(offset))
        mov(Reg.ECX, Mem.Based(Reg.ECX, 0))
    }
    bind(evaluated)
    and(Reg.ECX, slot(Slot.Mask))
    cmp(Reg.ECX, slot(Slot.Value))
    jz(equal)
    cmp(slot(Slot.Negate), 0)
    jnz(match)
    jmp(noMatch)
    bind(equal)
    cmp(slot(Slot.Negate), 0)
    jnz(noMatch)

    bind(match)
    cmp(Reg.EAX, field(Field.TriggerSeen))
    jnz(seed)
    cmp(field(Field.TriggerLast), 0)
    jnz(wait)
    mov(field(Field.TriggerLast), 1)
    mov(field(Field.TriggerFired), Reg.EAX)
    jmp(wait)
    bind(seed)
    mov(field(Field.TriggerSeen), Reg.EAX)
    mov(field(Field.TriggerLast), 1)
    jmp(wait)
    bind(noMatch)
    mov(field(Field.TriggerSeen), Reg.EAX)
    mov(field(Field.TriggerLast), 0)

    bind(wait)
    cmp(field(Field.Paused), 0)
    jnz(held)
    mov(Reg.EAX, field(Field.TriggerFired))
    cmp(Reg.EAX, field(Field.TriggerRelease))
    jz(run)
    bind(held)
    mov(Reg.EAX, field(Field.StepsGranted))
    cmp(Reg.EAX, field(Field.StepsTaken))
    jz(sleeping)
    inc(field(Field.StepsTaken))
    jmp(run)
    bind(sleeping)
    push(HELD_POLL_MS)
    call(sleep)
    jmp(wait)

    bind(run)
    if (loads != null) {
        val loaded = label()
        loadBarrier(mailbox, loads, sleep, loaded)
        bind(loaded)
    }
    inc(field(Field.Frame))
    logRng(mailbox, gameMan, logged)
    bind(logged)
    popad()
    popfd()
}

/** Copies the RNG words at frame entry into the log slot for this frame; skipped with no `GameMan` */
private fun X86.logRng(mailbox: Mailbox, gameMan: Mem, done: Label) {
    val entry = { offset: Int -> Mem.Based(Reg.ECX, offset) }
    val game = { offset: Int -> Mem.Based(Reg.EAX, offset) }

    mov(Reg.EAX, gameMan)
    cmp(Reg.EAX, 0)
    jz(done)
    mov(Reg.ECX, mailbox.operand(Field.Frame))
    mov(Reg.EDX, Reg.ECX)
    and(Reg.ECX, LOG_MASK)
    shl(Reg.ECX, LOG_SHIFT.toByte())
    add(Reg.ECX, mailbox.log.value.toInt())
    mov(entry(0), Reg.EDX)
    listOf(GameMan.RngState, GameMan.RngState + 4, GameMan.Twister + GameMan.TwisterLeft).forEachIndexed { i, offset ->
        mov(Reg.EDX, game(offset))
        mov(entry(Int.SIZE_BYTES * (i + 1)), Reg.EDX)
    }
}
