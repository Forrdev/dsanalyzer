package com.sappyoak.dsanalyzer.formats.archive

/**
 * They key an archive header indexes an entry by
 *
 * Entries are stored under a hash of their path rather than the path itself,
 * so enumerating an archive yields hashes and a name list is needed to turn them
 * back into paths
 */
public fun archivePathHash(path: String): UInt {
    var hash = 0u
    for (c in normalizeArchivePath(path)) {
        hash = hash * 37u + c.code.toUInt()
    }
    return hash
}

public fun normalizeArchivePath(path: String): String {
    val normalized = path.trim().replace('\\', '/').lowercase()
    return if (normalized.startsWith("/")) normalized else "/$normalized"
}