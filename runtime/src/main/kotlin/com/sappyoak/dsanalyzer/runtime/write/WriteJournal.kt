package com.sappyoak.dsanalyzer.runtime.write

import com.sappyoak.dsanalyzer.native.memory.Address
import com.sappyoak.dsanalyzer.runtime.pointers.Lifetime

public data class WriteKey(public val name: String) {
    override fun toString(): String = name
}

internal sealed interface Original {
    /** The [mask] bits of a dword as they were */
    public data class Bits(val mask: Int, val bits: Int) : Original
    class Code(val bytes: ByteArray) : Original
}

internal class JournalEntry(
    val key: WriteKey,
    val address: Address,
    val lifetime: Lifetime,
    val original: Original
)

internal class WriteJournal {
    private val entries = LinkedHashMap<WriteKey, JournalEntry>()

    val keys: Set<WriteKey> get() = entries.keys.toSet()

    operator fun get(key: WriteKey): JournalEntry? = entries[key]

    fun record(entry: JournalEntry) {
        entries.putIfAbsent(entry.key, entry)
    }

    fun take(key: WriteKey): JournalEntry? = entries.remove(key)
    fun takeAll(): List<JournalEntry> = entries.values.reversed().also { entries.clear() }

    fun discard(event: Lifetime) {
        entries.values.removeAll { it.lifetime >= event }
    }
}
