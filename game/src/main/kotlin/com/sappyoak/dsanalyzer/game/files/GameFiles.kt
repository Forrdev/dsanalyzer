package com.sappyoak.dsanalyzer.game.files

import com.sappyoak.dsanalyzer.game.GameEdition
import com.sappyoak.dsanalyzer.game.Installation

/**
 * Reads game files by path, whichever way the edition happens to store them
 */
public interface GameFiles : AutoCloseable {
    public fun hashes(): Set<UInt>

    public fun read(path: String): ByteArray?
    public fun exists(path: String): Boolean
}

/**
 * Opens an installation for reading
 */
public fun openGameFiles(installation: Installation): GameFiles =
    when (installation.build.edition) {
        GameEdition.PrepareToDie -> ArchiveGameFiles.open(installation)
        GameEdition.Remastered -> throw UnsupportedOperationException("Remastered installations are not readable yet")
    }

