package com.sappyoak.dsanalyzer.game.files
import com.sappyoak.dsanalyzer.game.Installation

public sealed interface FileListing {
    public data class Hashed(public val hashes: Set<UInt>) : FileListing
    public data class Named(public val paths: Set<String>) : FileListing

    /** No enumeration for this installation yet. Temporary */
    public data object Unsupported : FileListing
}


/**
 * Lists the contents of an installation. PTDE stores its files inside archives whose
 * headers carry the paths while Remastered ships them unpacked.
 */
public interface InstalledFileIndex {
    public suspend fun list(installation: Installation): FileListing
}