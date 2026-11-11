package com.sappyoak.dsanalyzer.runtime.write

import java.nio.ByteBuffer
import java.nio.ByteOrder

import com.sappyoak.dsanalyzer.native.memory.Address
import com.sappyoak.dsanalyzer.native.process.CodeMemory
import com.sappyoak.dsanalyzer.native.process.CodePatch
import com.sappyoak.dsanalyzer.native.process.ProcessMemory
import com.sappyoak.dsanalyzer.native.process.patch
import com.sappyoak.dsanalyzer.runtime.pointers.Lifetime

public class GameWriter internal constructor(
    private val memory: ProcessMemory,
    private val code: CodeMemory?
) {
    private val journal = WriteJournal()

    public val active: Set<WriteKey> get() = journal.keys.toSet()
    public val canPlaceCode: Boolean get() = code != null

    internal fun writeBits(key: WriteKey, address: Address, lifetime: Lifetime, mask: Int, on: Boolean): Boolean {
        val word = dwordAt(address) ?: return false
        val original = (journal[key]?.original as? Original.Bits)?.bits ?: (word and mask)
        val next = if (on) word or mask else word and mask.inv()
        if (next != word && !memory.write(address, dword(next))) return false

        if (next and mask == original) journal.take(key)
        else journal.record(JournalEntry(key, address, lifetime, Original.Bits(mask, original)))
        return true
    }

    /** Replaces data at [address], journaled so the bytes first found there go back on [revert] */
    public fun writeBytes(key: WriteKey, address: Address, lifetime: Lifetime, bytes: ByteArray): Boolean {
        val original = journal[key]?.let { (it.original as? Original.Bytes)?.bytes }
            ?: memory.read(address, bytes.size)
            ?: return false
        if (!memory.write(address, bytes)) return false
        journal.record(JournalEntry(key, address, lifetime, Original.Bytes(original)))
        return true
    }

    /**
     * Applies every edit with the game's threads suspended, journaled so each goes back on [revert].
     * Refuses, changing nothing, if any site does not hold its expected bytes
     */
    @GameMutation
    public fun patchCode(edits: List<CodeEdit>): Boolean {
        val placer = code ?: return false
        if (edits.any { memory.read(it.address, it.expected.size)?.contentEquals(it.expected) != true }) return false
        if (!placer.patch(edits.map { CodePatch(it.address, it.bytes) })) return false
        edits.forEach { journal.record(JournalEntry(it.key, it.address, Lifetime.Process, Original.Code(it.expected))) }
        return true
    }

    @GameMutation
    public fun patchCode(edit: CodeEdit): Boolean = patchCode(listOf(edit))

    /**
     * Runs [steps]; if they report failure, every change they journaled is put back, newest first.
     * Memory they allocated is not journaled and stays the caller's to free
     */
    public fun allOrNothing(steps: GameWriter.() -> Boolean): Boolean {
        val before = journal.keys.toSet()
        if (steps()) return true
        journal.keys.filterNot { it in before }.asReversed().forEach(::revert)
        return false
    }

    /** Memory of the tool's own in the game, written with [fill] */
    @GameMutation
    public fun allocate(size: Int): Address? = code?.allocate(size)

    /** Writes memory the tool allocated; never journaled */
    @GameMutation
    public fun fill(address: Address, bytes: ByteArray): Boolean = memory.write(address, bytes)

    @GameMutation
    public fun fillDword(address: Address, value: Int): Boolean = fill(address, dword(value))

    @GameMutation
    public fun free(address: Address): Boolean = code?.free(address) ?: false

    /**
     * A one-shot action on the game, such as a warp: never journaled, because there is no state to
     * put back. Undoing a warp is another warp
     */
    @GameMutation
    public fun perform(address: Address, bytes: ByteArray): Boolean = memory.write(address, bytes)

    public fun revert(key: WriteKey): Boolean = journal.take(key)?.let(::restore) ?: false

    /** Undoes every change, newest first; the count that could be put back */
    public fun revertAll(): Int = journal.takeAll().count(::restore)

    /** Forgets, without reverting, the changes that died with [event] */
    internal fun discard(event: Lifetime): Unit = journal.discard(event)

    private fun restore(entry: JournalEntry): Boolean = when (val original = entry.original) {
        is Original.Bits -> {
            val word = dwordAt(entry.address)
            word != null && memory.write(entry.address, dword((word and original.mask.inv()) or original.bits))
        }
        is Original.Bytes -> memory.write(entry.address, original.bytes)
        is Original.Code -> code?.patch(entry.address, original.bytes) ?: false
    }

    private fun dwordAt(address: Address): Int? =
        memory.read(address, Int.SIZE_BYTES)?.let { ByteBuffer.wrap(it).order(ByteOrder.LITTLE_ENDIAN).int }

    private fun dword(value: Int): ByteArray =
        ByteBuffer.allocate(Int.SIZE_BYTES).order(ByteOrder.LITTLE_ENDIAN).putInt(value).array()
}
