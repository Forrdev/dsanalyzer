package com.sappyoak.dsanalyzer.runtime.write.mailbox

import com.sappyoak.dsanalyzer.native.memory.Address
import com.sappyoak.dsanalyzer.native.process.ProcessMemory
import com.sappyoak.dsanalyzer.runtime.inject.CaveTemplate

internal const val BLOCK_SIZE: Int = 0x1000
internal const val SLOT_AT: Long = 0x800

private const val SLOT = "slot"
private const val CHR_MAN = "chrMan"
private const val PLAYER = "player"

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
 * x64: saves RFLAGS, RAX RCX RDX R8-R11 RBX RBP and XMM0-XMM5, RBX = slot, and counts the frame.
 * When the state is REQUESTED it gates on the player: it reads the character manager and its player,
 * and leaves the request for a later frame while either is null, so a call never runs without a
 * player. Then it aligns RSP, makes room for the shadow space and the stack arguments, copies them,
 * loads RCX RDX R8 R9, calls the target, stores RAX and sets DONE.
 */
private val X64_TEMPLATE = CaveTemplate(
    CaveTemplate.hex(
        """
        9c 50 51 52 41 50 41 51 41 52 41 53 53 55 48 83 ec 60 f3 0f 7f 04 24 f3
        0f 7f 4c 24 10 f3 0f 7f 54 24 20 f3 0f 7f 5c 24 30 f3 0f 7f 64 24 40 f3
        0f 7f 6c 24 50 48 bb 00 00 00 00 00 00 00 00 f0 ff 43 04 83 3b 01 75 6f
        48 b8 00 00 00 00 00 00 00 00 48 8b 00 48 85 c0 74 5d 48 8b 80 00 00 00
        00 48 85 c0 74 51 c7 03 02 00 00 00 48 89 e5 48 83 e4 f0 8b 4b 38 48 8d
        04 cd 2f 00 00 00 48 83 e0 f0 48 29 c4 85 c9 74 0e 48 8b 44 cb 38 48 89
        44 cc 18 ff c9 eb ee 48 8b 4b 18 48 8b 53 20 4c 8b 43 28 4c 8b 4b 30 ff
        53 10 48 89 ec 48 89 43 70 c7 03 03 00 00 00 f3 0f 6f 04 24 f3 0f 6f 4c
        24 10 f3 0f 6f 54 24 20 f3 0f 6f 5c 24 30 f3 0f 6f 64 24 40 f3 0f 6f 6c
        24 50 48 83 c4 60 5d 5b 41 5b 41 5a 41 59 41 58 5a 59 58 9d
        """
    ),
    mapOf(
        SLOT to CaveTemplate.Hole.Abs64(0x37),
        CHR_MAN to CaveTemplate.Hole.Abs64(0x4A),
        PLAYER to CaveTemplate.Hole.Imm32(0x5D)
    )
)

/** The x86 body for a code hook, which falls through to the bytes the jump replaced */
internal fun codeBody(origin: Address, slot: Address): ByteArray = X86_TEMPLATE.build(origin, mapOf(SLOT to slot.value))

/**
 * The x64 body for a step table. [chrMan] is the character manager's static global and [player] the
 * field in it holding the player, which together gate the call. Falls through to the game's own step
 */
internal fun gatedBody(origin: Address, slot: Address, chrMan: Address, player: Int): ByteArray =
    X64_TEMPLATE.build(origin, mapOf(SLOT to slot.value, CHR_MAN to chrMan.value, PLAYER to player.toLong()))

/** A mailbox placed in the game: its slot, the block holding it, and whether the game still enters it */
internal class InstalledMailbox(
    val slot: Address,
    val block: Address,
    private val entered: (ProcessMemory) -> Boolean
) {
    fun intact(memory: ProcessMemory): Boolean = entered(memory)
}
