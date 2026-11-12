package com.sappyoak.dsanalyzer.runtime.write

import com.sappyoak.dsanalyzer.native.memory.Address
import com.sappyoak.dsanalyzer.native.process.CodeMemory
import com.sappyoak.dsanalyzer.native.process.CodePatch
import com.sappyoak.dsanalyzer.native.process.ProcessMemory
import com.sappyoak.dsanalyzer.native.process.patch
import com.sappyoak.dsanalyzer.runtime.pointers.Lifetime
import com.sappyoak.dsanalyzer.runtime.session.RevertPolicy
import com.sappyoak.dsanalyzer.runtime.session.WriteEntry
import com.sappyoak.dsanalyzer.runtime.write.task.WriteTask
import com.sappyoak.dsanalyzer.shared.binary.toLittleEndian
import com.sappyoak.dsanalyzer.native.memory.intAt

public class GameWriter internal constructor(
    private val memory: ProcessMemory,
    private val code: CodeMemory?
) {
    private val history = WriteHistory()

    public val active: Set<WriteKey> get() = history.keys.toSet()
    public val canPlaceCode: Boolean get() = code != null

    /** Every write this session, oldest first, for the history view */
    public val records: List<WriteEntry> get() = history.records.map { it.entry() }

    internal fun writeBits(key: WriteKey, address: Address, lifetime: Lifetime, mask: Int, on: Boolean): Boolean {
        val word = memory.intAt(address) ?: return false
        val original = (history[key]?.original as? Original.Bits)?.bits ?: (word and mask)
        val next = if (on) word or mask else word and mask.inv()
        if (next != word && !memory.write(address, next.toLittleEndian())) return false

        if (next and mask == original) history.take(key)
        else history.toggle(key, address, lifetime, Original.Bits(mask, original))
        return true
    }

    /** Replaces data at [address], journaled so the bytes first found there go back on [revert] */
    public fun writeBytes(key: WriteKey, address: Address, lifetime: Lifetime, bytes: ByteArray): Boolean {
        val original = history[key]?.let { (it.original as? Original.Bytes)?.bytes }
            ?: memory.read(address, bytes.size)
            ?: return false
        if (!memory.write(address, bytes)) return false
        history.toggle(key, address, lifetime, Original.Bytes(original))
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
        edits.forEach { history.toggle(it.key, it.address, Lifetime.Process, Original.Code(it.expected)) }
        return true
    }

    @GameMutation
    public fun patchCode(edit: CodeEdit): Boolean = patchCode(listOf(edit))

    /**
     * Runs [steps]; if they report failure, every change they journaled is put back, newest first.
     * Memory they allocated is not journaled and stays the caller's to free
     */
    public fun allOrNothing(steps: GameWriter.() -> Boolean): Boolean {
        val before = history.keys.toSet()
        if (steps()) return true
        history.keys.filterNot { it in before }.asReversed().forEach(::revert)
        return false
    }

    /** Memory of the tool's own in the game, written with [fill] */
    @GameMutation
    public fun allocate(size: Int): Address? = code?.allocate(size)

    /** As above, where a `rel32` in the module at [near] can reach it and it the module */
    @GameMutation
    public fun allocate(size: Int, near: Address): Address? = code?.allocate(size, near)

    /** Makes game memory writable to the game, for data a cave changes; never restored */
    @GameMutation
    public fun makeWritable(address: Address, size: Long): Boolean = code?.makeWritable(address, size) ?: false

    /** Writes memory the tool allocated; never journaled */
    @GameMutation
    public fun fill(address: Address, bytes: ByteArray): Boolean = memory.write(address, bytes)

    @GameMutation
    public fun fillDword(address: Address, value: Int): Boolean = fill(address, value.toLittleEndian())

    @GameMutation
    public fun free(address: Address): Boolean = code?.free(address) ?: false

    /** Starts a thread of the tool's own in the game at [entry] with [parameter]; never journaled */
    @GameMutation
    public fun startThread(entry: Address, parameter: Address): Boolean = code?.startThread(entry, parameter) ?: false

    /**
     * A one-shot action on the game, such as a warp: never journaled, because there is no state to
     * put back. Undoing a warp is another warp
     */
    @GameMutation
    public fun perform(address: Address, bytes: ByteArray): Boolean = memory.write(address, bytes)

    /**
     * Drops a record without writing anything back, for a target the game has already moved on from.
     * Putting the old bytes back there would write into whatever holds that address now
     */
    internal fun forget(key: WriteKey): Boolean = history.take(key) != null

    public fun revert(key: WriteKey): Boolean = history.take(key)?.let(::restore) ?: false

    /** Undoes every change, newest first; the count that could be put back */
    public fun revertAll(): Int = history.takeToggles().count(::restore)

    /** Forgets, without reverting, the changes that died with [event] */
    internal fun discard(event: Lifetime): Unit = history.discard(event)

    /**
     * Writes a value the user wants to keep. It stays through [revertAll] and is undone only by [undo].
     * Lifetime decides when the record is dropped, because the address is gone after that event
     */
    public fun edit(key: WriteKey, address: Address, lifetime: Lifetime, bytes: ByteArray, label: String = key.name): Boolean {
        val before = memory.read(address, bytes.size) ?: return false
        if (!memory.write(address, bytes)) return false
        history.edit(key, RevertPolicy.Manual, address, lifetime, Original.Bytes(before), bytes, label)
        return true
    }

    /**
     * Records a write that a task made, with a task as its inverse. Example: a position restore keeps
     * the position from before, and its undo is a restore to that position
     */
    public fun recordEdit(key: WriteKey, label: String, inverse: () -> WriteTask) {
        history.edit(key, RevertPolicy.Manual, Address.Null, Lifetime.Process, Original.Task(inverse), null, label)
    }

    /** Records an action that has no inverse, such as a game call, so the history shows it */
    public fun note(key: WriteKey, label: String = key.name) {
        history.edit(key, RevertPolicy.Never, Address.Null, Lifetime.Process, Original.None, null, label)
    }

    /**
     * Undoes one record. An edit is undone only while memory still holds what it wrote, unless [force];
     * the undo is itself recorded, so it can be undone too
     */
    public fun undo(id: Long, force: Boolean = false): UndoResult {
        val record = history.find(id) ?: return UndoResult.Missing
        return when (record.policy) {
            RevertPolicy.OnDetach -> if (revert(record.key)) UndoResult.Done else UndoResult.Failed
            RevertPolicy.Never -> UndoResult.NotUndoable
            RevertPolicy.Manual -> undoEdit(record, force)
        }
    }

    private fun undoEdit(record: WriteRecord, force: Boolean): UndoResult {
        (record.original as? Original.Task)?.let { return UndoResult.Started(it.inverse()) }
        val before = (record.original as? Original.Bytes)?.bytes ?: return UndoResult.NotUndoable
        val after = record.after ?: return UndoResult.NotUndoable
        val now = memory.read(record.address, after.size) ?: return UndoResult.Failed
        if (!force && !now.contentEquals(after)) return UndoResult.Stale
        if (!memory.write(record.address, before)) return UndoResult.Failed
        history.edit(record.key, RevertPolicy.Manual, record.address, record.lifetime, Original.Bytes(now), before, "Undo: ${record.label}")
        return UndoResult.Done
    }

    private fun restore(entry: WriteRecord): Boolean = when (val original = entry.original) {
        is Original.Bits -> {
            val word = memory.intAt(entry.address)
            word != null && memory.write(entry.address, ((word and original.mask.inv()) or original.bits).toLittleEndian())
        }
        is Original.Bytes -> memory.write(entry.address, original.bytes)
        is Original.Code -> code?.patch(entry.address, original.bytes) ?: false
        Original.None, is Original.Task -> false
    }
}
