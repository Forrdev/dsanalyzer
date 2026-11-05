package com.sappyoak.dsanalyzer.runtime.write

import java.nio.ByteBuffer
import java.nio.ByteOrder

import com.sappyoak.dsanalyzer.native.memory.Address
import com.sappyoak.dsanalyzer.native.process.CodeMemory
import com.sappyoak.dsanalyzer.native.process.ProcessMemory
import com.sappyoak.dsanalyzer.runtime.pointers.Lifetime

public class GameWriter internal constructor(
    private val memory: ProcessMemory,
    private val code: CodeMemory?
) {
    private val journal = WriteJournal()

    public val active: Set<WriteKey> get() = journal.keys
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

    /** Replaces code at [address], journaled so the original bytes go back on [revert] */
    @GameMutation
    internal fun patchCode(key: WriteKey, address: Address, bytes: ByteArray): Boolean {
        val placer = code ?: return false
        val original = memory.read(address, bytes.size) ?: return false
        if (!placer.patch(address, bytes)) return false
        journal.record(JournalEntry(key, address, Lifetime.Process, Original.Code(original)))
        return true
    }

    /** Memory of the tool's own in the game, written with [fill] */
    @GameMutation
    internal fun allocate(size: Int): Address? = code?.allocate(size)

    /** Writes memory the tool allocated; never journaled */
    @GameMutation
    internal fun fill(address: Address, bytes: ByteArray): Boolean = memory.write(address, bytes)

    @GameMutation
    internal fun fillDword(address: Address, value: Int): Boolean = fill(address, dword(value))

    @GameMutation
    internal fun free(address: Address): Boolean = code?.free(address) ?: false

    /**
     * A one-shot action on the game, such as a warp: never journaled, because there is no state to
     * put back. Undoing a warp is another warp
     */
    @GameMutation
    internal fun perform(address: Address, bytes: ByteArray): Boolean = memory.write(address, bytes)

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
        is Original.Code -> code?.patch(entry.address, original.bytes) ?: false
    }

    private fun dwordAt(address: Address): Int? =
        memory.read(address, Int.SIZE_BYTES)?.let { ByteBuffer.wrap(it).order(ByteOrder.LITTLE_ENDIAN).int }

    private fun dword(value: Int): ByteArray =
        ByteBuffer.allocate(Int.SIZE_BYTES).order(ByteOrder.LITTLE_ENDIAN).putInt(value).array()
}
