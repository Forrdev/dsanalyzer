package com.sappyoak.dsanalyzer.runtime.write.mailbox

import com.sappyoak.dsanalyzer.native.memory.Address
import com.sappyoak.dsanalyzer.runtime.inject.CaveTemplate
import com.sappyoak.dsanalyzer.runtime.layout.CallArch
import com.sappyoak.dsanalyzer.runtime.layout.Displaced
import com.sappyoak.dsanalyzer.runtime.layout.MailboxLayout
import com.sappyoak.dsanalyzer.shared.binary.littleEndian

private const val SLOT = "slot"
private const val JMP_SIZE = 5
private const val RIP_LOAD_SIZE = 7

/**
 * x86: pushad/pushfd, EBP = slot. Counts the frame, and when the state is REQUESTED pushes the
 * stack arguments in reverse, loads EAX ECX EDX EBX ESI EDI, calls the target, restores ESP,
 * stores EAX and sets DONE.
 */
private val X86_TEMPLATE = CaveTemplate(
    CaveTemplate.hex(
        """
        60 9c bd 00 00 00 00 ff 45 04 83 7d 00 01 75 3a c7 45 00 02 00 00 00 89
        65 08 8b 4d 48 85 c9 74 07 ff 74 8d 4c 49 eb f5 8b 45 18 8b 4d 20 8b 55
        28 8b 5d 30 8b 75 38 8b 7d 40 ff 55 10 8b 65 08 89 45 70 c7 45 00 03 00
        00 00 9d 61
        """
    ),
    mapOf(SLOT to CaveTemplate.Hole.Imm32(0x03))
)

/**
 * x64: saves RFLAGS, RAX RCX RDX R8-R11 RBX RBP and XMM0-XMM5, RBX = slot. When the state is
 * REQUESTED it aligns RSP, makes room for the shadow space and the stack arguments, copies them,
 * loads RCX RDX R8 R9, calls the target, stores RAX and sets DONE
 */
private val X64_TEMPLATE = CaveTemplate(
    CaveTemplate.hex(
        """
        9c 50 51 52 41 50 41 51 41 52 41 53 53 55 48 83 ec 60 f3 0f 7f 04 24 f3
        0f 7f 4c 24 10 f3 0f 7f 54 24 20 f3 0f 7f 5c 24 30 f3 0f 7f 64 24 40 f3
        0f 7f 6c 24 50 48 bb 00 00 00 00 00 00 00 00 f0 ff 43 04 83 3b 01 75 51
        c7 03 02 00 00 00 48 89 e5 48 83 e4 f0 8b 4b 38 48 8d 04 cd 2f 00 00 00
        48 83 e0 f0 48 29 c4 85 c9 74 0e 48 8b 44 cb 38 48 89 44 cc 18 ff c9 eb
        ee 48 8b 4b 18 48 8b 53 20 4c 8b 43 28 4c 8b 4b 30 ff 53 10 48 89 ec 48
        89 43 70 c7 03 03 00 00 00 f3 0f 6f 04 24 f3 0f 6f 4c 24 10 f3 0f 6f 54
        24 20 f3 0f 6f 5c 24 30 f3 0f 6f 64 24 40 f3 0f 6f 6c 24 50 48 83 c4 60
        5d 5b 41 5b 41 5a 41 59 41 58 5a 59 58 9d
        """
    ),
    mapOf(SLOT to CaveTemplate.Hole.Abs64(0x37))
)

internal fun templateFor(arch: CallArch): CaveTemplate = if (arch == CallArch.X86) X86_TEMPLATE else X64_TEMPLATE

/** The instruction the jump replaced, and the jump back */
internal fun assembleMailbox(layout: MailboxLayout, origin: Address, slot: Address, site: Address, original: ByteArray): ByteArray {
    val body = templateFor(layout.arch).build(origin, mapOf(SLOT to slot.value))
    val moved = when (layout.displaced) {
        Displaced.Verbatim -> original
        Displaced.RipLoadRax -> ripLoadRax(site, original)
    }
    val jumpAt = origin.value + body.size + moved.size
    val back = site.value + original.size
    val rel = back - (jumpAt + JMP_SIZE)
    require(rel in Int.MIN_VALUE..Int.MAX_VALUE) { "The mailbox cave is too far from its site for a rel32 jump" }
    val jump = littleEndian(JMP_SIZE).put(0xE9.toByte()).putInt(rel.toInt()).array()
    return body + moved + jump
}

/** `48 8B 05 disp32` becomes `48 A1 abs64`, plus whatever followed it in the displaced bytes */
private fun ripLoadRax(site: Address, original: ByteArray): ByteArray {
    require(original.size >= RIP_LOAD_SIZE && original[0] == 0x48.toByte() && original[1] == 0x8B.toByte() && original[2] == 0x05.toByte()) {
        "The displaced bytes are not mov rax,[rip+disp32]"
    }
    val disp = original.littleEndian(offset = 3, length = 4).int
    val target = site.value + RIP_LOAD_SIZE + disp
    val load = littleEndian(10).put(0x48.toByte()).put(0xA1.toByte()).putLong(target).array()
    return load + original.copyOfRange(RIP_LOAD_SIZE, original.size)
}
