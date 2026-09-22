package com.sappyoak.dsanalyzer.game.files

import com.sappyoak.dsanalyzer.game.GameEdition
import com.sappyoak.dsanalyzer.game.Installation
import com.sappyoak.dsanalyzer.shared.binary.BinaryReader

/**
 * Reads game files by path, whichever way the edition happens to store them
 */
public interface GameFiles : AutoCloseable {
    public fun listing(): FileListing
    public fun exists(path: GamePath): Boolean

    /**
     * A reader over the file's contents, or null when absent. If the entry is compressed,
     * it will be decompressed in the reader.
     *
     * A file stored plain may be a view of mapped memory rather t han a copy, so the reader
     * is only valid until this closes. Parse inside the [InstallationFiles.use] lease and keep the
     * parsed result, never the reader
     */
    public fun open(path: GamePath): BinaryReader?
}

public fun GameFiles.exists(path: String): Boolean = exists(GamePath.of(path))
public fun GameFiles.open(path: String): BinaryReader? = open(GamePath.of(path))

/**
 * Opens an installation for reading
 */
public fun openGameFiles(installation: Installation): GameFiles =
    when (installation.build.edition) {
        GameEdition.PrepareToDie -> ArchiveGameFiles.open(installation)
        GameEdition.Remastered -> LooseGameFiles(installation.root)
    }

