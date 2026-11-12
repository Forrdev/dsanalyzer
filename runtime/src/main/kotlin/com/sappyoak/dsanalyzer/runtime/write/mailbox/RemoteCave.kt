package com.sappyoak.dsanalyzer.runtime.write.mailbox

import com.sappyoak.dsanalyzer.native.memory.Address
import com.sappyoak.dsanalyzer.runtime.inject.CaveTemplate
import com.sappyoak.dsanalyzer.runtime.layout.CallArch

private const val SLEEP = "sleep"

/**
 * x86 thread procedure, `stdcall (slot)`: saves EBX EBP ESI EDI, EBP = slot. Loops: on
 * [MailboxSlot.STOP] it answers [MailboxSlot.STOPPED] and returns; on REQUESTED it sets RUNNING, saves
 * ESP, pushes the stack arguments in reverse, loads EAX ECX EDX EBX ESI EDI, calls the target, restores
 * ESP, stores EAX and sets DONE; otherwise it calls `Sleep(1)` through the import slot
 */
private val X86_THREAD = CaveTemplate(
    CaveTemplate.hex(
        """
        53 55 56 57 8b 6c 24 14 83 7d 78 01 74 4c 83 7d 00 01 75 3c c7 45 00 02
        00 00 00 89 65 08 8b 4d 48 85 c9 74 07 ff 74 8d 4c 49 eb f5 8b 45 18 8b
        4d 20 8b 55 28 8b 5d 30 8b 75 38 8b 7d 40 ff 55 10 8b 65 08 89 45 70 c7
        45 00 03 00 00 00 eb b8 6a 01 ff 15 00 00 00 00 eb ae c7 45 78 02 00 00
        00 5f 5e 5d 5b c2 04 00
        """
    ),
    mapOf(SLEEP to CaveTemplate.Hole.Imm32(0x54))
)

/**
 * x64 thread procedure, `(slot)` in RCX: saves RBX RBP RSI RDI, keeps RSP aligned with shadow space,
 * RBX = slot. Loops as the x86 one does, making room for the shadow space and the stack arguments,
 * loading RCX RDX R8 R9 and storing RAX
 */
private val X64_THREAD = CaveTemplate(
    CaveTemplate.hex(
        """
        53 55 56 57 48 83 ec 28 48 89 cb 83 7b 78 01 74 67 83 3b 01 75 4f c7 03
        02 00 00 00 48 89 e5 8b 4b 38 48 8d 04 cd 2f 00 00 00 48 83 e0 f0 48 29
        c4 85 c9 74 0e 48 8b 44 cb 38 48 89 44 cc 18 ff c9 eb ee 48 8b 4b 18 48
        8b 53 20 4c 8b 43 28 4c 8b 4b 30 ff 53 10 48 89 ec 48 89 43 70 c7 03 03
        00 00 00 eb a6 b9 01 00 00 00 48 b8 00 00 00 00 00 00 00 00 ff 10 eb 93
        c7 43 78 02 00 00 00 48 83 c4 28 5f 5e 5d 5b 31 c0 c3
        """
    ),
    mapOf(SLEEP to CaveTemplate.Hole.Abs64(0x6C))
)

/** The thread procedure for [arch], which calls `Sleep` through the import slot at [sleep] */
internal fun threadBody(arch: CallArch, origin: Address, sleep: Address): ByteArray =
    (if (arch == CallArch.X86) X86_THREAD else X64_THREAD).build(origin, mapOf(SLEEP to sleep.value))
