package com.sappyoak.dsanalyzer.formats.archive

public class Archive(private val byHash: Map<UInt, ArchiveEntry>) {
    public val entryCount: Int get() = byHash.size
    public val hashes: Set<UInt> get() = byHash.keys

    public val entries: Collection<ArchiveEntry> get() = byHash.values

    public operator fun get(path: String): ArchiveEntry? = byHash[archivePathHash(path)]
    public operator fun get(hash: UInt): ArchiveEntry? = byHash[hash]

    public operator fun contains(path: String): Boolean = this[path] != null
    public operator fun contains(hash: UInt): Boolean = this[hash] != null
}

/** Where a single file sits inside the companion .bdt */
public data class ArchiveEntry(
    public val hash: UInt,
    public val offset: Long,
    public val paddedSize: Int
)