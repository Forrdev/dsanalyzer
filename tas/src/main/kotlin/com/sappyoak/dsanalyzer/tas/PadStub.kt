package com.sappyoak.dsanalyzer.tas

import com.sappyoak.dsanalyzer.runtime.inject.Mem
import com.sappyoak.dsanalyzer.runtime.inject.Reg
import com.sappyoak.dsanalyzer.runtime.inject.X86
import com.sappyoak.dsanalyzer.runtime.ptde.PadDevice
import com.sappyoak.dsanalyzer.tas.Mailbox.Companion.RECORD_SHIFT
import com.sappyoak.dsanalyzer.tas.Mailbox.Companion.RING_MASK
import com.sappyoak.dsanalyzer.tas.Mailbox.Field

/**
 * The pad stub, entered with EDI = the device just polled. The mode is read once, so a change from
 * the tool mid-frame applies whole on the next frame:
 * ```
 *   pushfd / pushad
 *   edx = PadMode ; if edx == Off goto done
 *   if EDI != PadDevice or device.XInputUser != 0 goto done
 *   if PadEpoch != PadSeen: PadFirstFrame = Frame ; PadCursor = 0 ; PadSeen = PadEpoch
 *   eax = PadCursor ; if eax >= PadLimit goto done
 *   eax = ring + (eax & mask) << shift
 *   if edx == Play: copy record -> device   else: copy device -> record
 *   PadCursor++
 * done:
 *   popad / popfd
 * ```
 * `PadSeen` is written last on a new epoch: once the tool reads it as current, the first frame and
 * the cursor beside it are this epoch's too
 */
internal fun X86.padOverride(mailbox: Mailbox) {
    val done = label()
    val current = label()
    val recording = label()
    val advance = label()
    val device = { offset: Int -> Mem.Based(Reg.EDI, offset) }
    val record = { offset: Int -> Mem.Based(Reg.EAX, offset) }

    pushfd()
    pushad()
    mov(Reg.EDX, mailbox.operand(Field.PadMode))
    cmp(Reg.EDX, PadMode.Off.code.toByte())
    jz(done)
    cmp(Reg.EDI, mailbox.operand(Field.PadDevice))
    jnz(done)
    cmp(device(PadDevice.XInputUser), 0)
    jnz(done)

    mov(Reg.EAX, mailbox.operand(Field.PadEpoch))
    cmp(Reg.EAX, mailbox.operand(Field.PadSeen))
    jz(current)
    mov(Reg.ECX, mailbox.operand(Field.Frame))
    mov(mailbox.operand(Field.PadFirstFrame), Reg.ECX)
    mov(mailbox.operand(Field.PadCursor), 0)
    mov(mailbox.operand(Field.PadSeen), Reg.EAX)
    bind(current)

    mov(Reg.EAX, mailbox.operand(Field.PadCursor))
    cmp(Reg.EAX, mailbox.operand(Field.PadLimit))
    jae(done)
    and(Reg.EAX, RING_MASK)
    shl(Reg.EAX, RECORD_SHIFT.toByte())
    add(Reg.EAX, mailbox.ring.value.toInt())

    cmp(Reg.EDX, PadMode.Play.code.toByte())
    jnz(recording)
    mov(Reg.ECX, record(0))
    movWord(device(PadDevice.Buttons), Reg.ECX)
    PadDevice.Axes.forEachIndexed { i, axis ->
        mov(Reg.ECX, record(axisOffset(i)))
        mov(device(axis), Reg.ECX)
    }
    jmp(advance)

    bind(recording)
    movzxWord(Reg.ECX, device(PadDevice.Buttons))
    mov(record(0), Reg.ECX)
    PadDevice.Axes.forEachIndexed { i, axis ->
        mov(Reg.ECX, device(axis))
        mov(record(axisOffset(i)), Reg.ECX)
    }

    bind(advance)
    inc(mailbox.operand(Field.PadCursor))
    bind(done)
    popad()
    popfd()
}

/** A record's axis [index], after its button dword */
private fun axisOffset(index: Int): Int = Int.SIZE_BYTES * (index + 1)
