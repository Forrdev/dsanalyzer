package com.sappyoak.dsanalyzer.runtime.write.mailbox

import com.sappyoak.dsanalyzer.native.memory.Address
import com.sappyoak.dsanalyzer.native.memory.AddressRange
import com.sappyoak.dsanalyzer.native.process.ProcessMemory
import com.sappyoak.dsanalyzer.runtime.inject.HookSite
import com.sappyoak.dsanalyzer.runtime.inject.SiteLookup
import com.sappyoak.dsanalyzer.runtime.inject.X86
import com.sappyoak.dsanalyzer.runtime.inject.locate
import com.sappyoak.dsanalyzer.runtime.write.CodeEdit
import com.sappyoak.dsanalyzer.runtime.write.GameMutation
import com.sappyoak.dsanalyzer.runtime.write.GameWriter
import com.sappyoak.dsanalyzer.shared.binary.littleEndian

private const val JMP_SIZE = 5

/** Jumps from [site] to a cave that runs the mailbox, the bytes the jump replaced, and jumps back */
@GameMutation
internal fun installCodeMailbox(
    writer: GameWriter,
    memory: ProcessMemory,
    module: AddressRange,
    site: HookSite
): InstalledMailbox? {
    val at = (memory.locate(site, module) as? SiteLookup.Found)?.address ?: return null
    val block = writer.allocate(BLOCK_SIZE, at) ?: return null
    val box = block + SLOT_AT
    val body = codeBody(block, box) + site.expected
    val code = body + jumpBack(block + body.size.toLong(), at + site.displaced.toLong())
    val installed = code.size <= SLOT_AT &&
            writer.fill(block, code) &&
            writer.fill(box, ByteArray(MailboxSlot.SIZE)) &&
            writer.patchCode(CodeEdit(MAILBOX_KEY, at, site.expected, X86.jumpPatch(at, block, site.displaced)))
    if (!installed) writer.free(block)
    // PTDE patches code that nothing puts back, so an installed cave stays entered
    return if (installed) InstalledMailbox(box, block) { true } else null
}

private fun jumpBack(from: Address, to: Address): ByteArray {
    val rel = to.value - (from.value + JMP_SIZE)
    require(rel in Int.MIN_VALUE..Int.MAX_VALUE) { "The mailbox cave is too far from its site for a rel32 jump" }
    return littleEndian(JMP_SIZE).put(0xE9.toByte()).putInt(rel.toInt()).array()
}
