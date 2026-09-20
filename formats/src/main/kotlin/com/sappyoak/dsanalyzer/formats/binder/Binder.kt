package com.sappyoak.dsanalyzer.formats.binder

import kotlinx.serialization.Serializable

@Serializable
public class Binder(
    public val version: String,
    public val flags: BinderFormatFlags,
    public val entries: List<BinderEntry>
) {
    operator fun get(name: String): BinderEntry? =
        entries.firstOrNull { it.name?.equals(name, ignoreCase = true) == true }

    operator fun get(id: Int): BinderEntry? =
        entries.firstOrNull { it.id == id }

    operator fun contains(name: String): Boolean = this[name] != null
    operator fun contains(id: Int): Boolean = this[id] != null
}

@Serializable
public data class BinderEntry(
    /** id of the file or -1 */
    public val id: Int,
    public val name: String?,
    public val flags: BinderEntryFlags,
    /** Location of this file data within the Binder */
    public val dataOffset: Long,
    public val compressedSize: Long,
    public val uncompressedSize: Long
)