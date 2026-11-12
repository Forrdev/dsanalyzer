package com.sappyoak.dsanalyzer.runtime.write.mailbox

import com.sappyoak.dsanalyzer.native.memory.Address
import com.sappyoak.dsanalyzer.native.memory.AddressRange
import com.sappyoak.dsanalyzer.native.memory.PointerChain
import com.sappyoak.dsanalyzer.native.memory.Signature
import com.sappyoak.dsanalyzer.native.memory.SignatureScan
import com.sappyoak.dsanalyzer.native.memory.pointerAt
import com.sappyoak.dsanalyzer.native.memory.resolve
import com.sappyoak.dsanalyzer.native.process.ProcessMemory
import com.sappyoak.dsanalyzer.runtime.layout.MailboxLayout
import com.sappyoak.dsanalyzer.runtime.pointers.Lifetime
import com.sappyoak.dsanalyzer.runtime.write.GameMutation
import com.sappyoak.dsanalyzer.runtime.write.GameWriter
import com.sappyoak.dsanalyzer.shared.binary.littleEndian

/** The table copy sits between the cave and the slot */
private const val TABLE_AT = 0x600L
/** One short of the table room, which must also hold the terminating null */
private const val MAX_STEPS = 63
private const val ENTRY = 8L

/**
 * Points the step object at a copy of its table whose running step is a cave that runs the mailbox
 * and then jumps to the game's own step. One aligned 8-byte write, so the game never sees half of it;
 * reverting [MAILBOX_KEY] puts the game's table back
 */
@GameMutation
internal fun installStepTableMailbox(
    writer: GameWriter,
    memory: ProcessMemory,
    module: AddressRange,
    layout: MailboxLayout.StepTable
): InstalledMailbox? {
    val site = layout.site
    val gate = memory.static(layout.gate.owner, module) ?: return null
    val root = memory.static(site.root, module) ?: return null
    val step = PointerChain(root, site.path).resolve(memory).takeUnless { it.isNull } ?: return null
    val field = step + site.table.toLong()
    val table = memory.pointerAt(field)?.takeIf { it in module } ?: return null
    val steps = memory.steps(table) ?: return null
    val original = steps.getOrNull(site.slot)?.takeIf { it in module } ?: return null

    val block = writer.allocate(BLOCK_SIZE) ?: return null
    val box = block + SLOT_AT
    val copy = block + TABLE_AT
    val cave = gatedBody(block, box, gate, layout.gate.player) + jumpTo(original)
    val entries = steps.toMutableList().apply { set(site.slot, block) } + Address.Null
    val installed = cave.size <= TABLE_AT && entries.size * ENTRY <= SLOT_AT - TABLE_AT &&
            writer.fill(block, cave) &&
            writer.fill(box, ByteArray(MailboxSlot.SIZE)) &&
            writer.fill(copy, pointers(entries)) &&
            writer.writeBytes(MAILBOX_KEY, field, Lifetime.Process, pointers(listOf(copy)))
    if (!installed) writer.free(block)
    return if (installed) InstalledMailbox(box, block) { it.pointerAt(field) == copy } else null
}

private fun ProcessMemory.static(signature: Signature, module: AddressRange): Address? =
    (resolve(signature, module) as? SignatureScan.Resolved)?.address

/** The table's entries up to its terminating null */
private fun ProcessMemory.steps(table: Address): List<Address>? {
    val entries = mutableListOf<Address>()
    while (entries.size < MAX_STEPS) {
        val next = pointerAt(table + entries.size * ENTRY) ?: return null
        if (next.isNull) return entries
        entries += next
    }
    return null
}

/** `jmp [rip+0]` and the target */
private fun jumpTo(target: Address): ByteArray =
    littleEndian(14).put(0xFF.toByte()).put(0x25.toByte()).putInt(0).putLong(target.value).array()

private fun pointers(values: List<Address>): ByteArray {
    val bytes = littleEndian(values.size * ENTRY.toInt())
    values.forEach { bytes.putLong(it.value) }
    return bytes.array()
}
