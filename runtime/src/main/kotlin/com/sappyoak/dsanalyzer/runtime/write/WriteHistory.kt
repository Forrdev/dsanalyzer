package com.sappyoak.dsanalyzer.runtime.write

import com.sappyoak.dsanalyzer.native.memory.Address
import com.sappyoak.dsanalyzer.runtime.pointers.Lifetime
import com.sappyoak.dsanalyzer.runtime.snapshot.RevertPolicy
import com.sappyoak.dsanalyzer.runtime.snapshot.WriteEntry
import com.sappyoak.dsanalyzer.runtime.write.task.WriteTask
import com.sappyoak.dsanalyzer.runtime.snapshot.WriteKey

/** The most edits kept for undo; the oldest go first */
internal const val EDIT_LIMIT: Int = 100

public sealed interface UndoResult {
    public data object Done : UndoResult

    /** Memory no longer holds what the write put there; undo again with force to apply anyway */
    public data object Stale : UndoResult
    public data object Missing : UndoResult
    public data object NotUndoable : UndoResult
    public data object Failed : UndoResult

    /** The inverse is a task (for example a position restore); the session starts it */
    public data class Started(public val task: WriteTask) : UndoResult
}

internal sealed interface Original {
    data class Bits(val mask: Int, val bits: Int) : Original
    class Bytes(val bytes: ByteArray) : Original
    class Code(val bytes: ByteArray) : Original
    data object None : Original

    /** An inverse that is a whole write of its own, made when the undo runs */
    class Task(val inverse: () -> WriteTask) : Original
}

internal class WriteRecord(
    val id: Long,
    val key: WriteKey,
    val policy: RevertPolicy,
    val address: Address,
    val lifetime: Lifetime,
    val original: Original,
    /** What an edit wrote, which undo compares against to find a stale record */
    val after: ByteArray?,
    val label: String
) {
    fun entry(): WriteEntry = WriteEntry(id, key.name, policy, label, undoable = policy != RevertPolicy.Never)
}

/**
 * Every write of the session. Toggles keep one record per key, holding the bytes first found, so a
 * revert always restores the game's own state. Edits and actions append, up to [limit]
 */
internal class WriteHistory(private val limit: Int = EDIT_LIMIT) {
    private val toggles = LinkedHashMap<WriteKey, WriteRecord>()
    private val edits = ArrayDeque<WriteRecord>()
    private var next = 1L

    val keys: Set<WriteKey> get() = toggles.keys.toSet()
    val records: List<WriteRecord> get() = (toggles.values + edits).sortedBy { it.id }

    operator fun get(key: WriteKey): WriteRecord? = toggles[key]

    fun toggle(key: WriteKey, address: Address, lifetime: Lifetime, original: Original) {
        if (key !in toggles) toggles[key] = WriteRecord(next++, key, RevertPolicy.OnDetach, address, lifetime, original, null, key.name)
    }

    fun edit(
        key: WriteKey,
        policy: RevertPolicy,
        address: Address,
        lifetime: Lifetime,
        original: Original,
        after: ByteArray?,
        label: String
    ): WriteRecord {
        val record = WriteRecord(next++, key, policy, address, lifetime, original, after, label)
        edits.addLast(record)
        while (edits.size > limit) edits.removeFirst()
        return record
    }

    fun find(id: Long): WriteRecord? = edits.firstOrNull { it.id == id } ?: toggles.values.firstOrNull { it.id == id }

    fun take(key: WriteKey): WriteRecord? = toggles.remove(key)

    /** Toggles newest first, which is the order they must go back in. Edits stay */
    fun takeToggles(): List<WriteRecord> = toggles.values.reversed().also { toggles.clear() }

    fun discard(event: Lifetime) {
        toggles.values.removeAll { it.lifetime >= event }
        edits.removeAll { it.lifetime >= event }
    }
}
