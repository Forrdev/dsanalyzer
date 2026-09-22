package com.sappyoak.dsanalyzer.game.files

import java.nio.file.Path
import kotlin.io.path.readBytes

import com.sappyoak.dsanalyzer.formats.compression.decompress
import com.sappyoak.dsanalyzer.shared.binary.BinaryReader

/**
 * Access to a game whose files are unpacked on disk under [root].
 *
 * Game paths are rooted at the installation, so '/chr/c0000.chrbnd.dcx' is 'root/chr/c0000.chrbnd.dcx'
 *
 * Files are found through an index built from one walk of the tree rather than by resolving paths because
 * [GamePath] is lowercase and the files on disk are not. Resolving this would only work on a case-insensitive
 * filesystem, and it cannot escape [root] by construction. The index is as old as this instance, which
 * [InstallationFiles] keeps short-lived.
 */
internal class LooseGameFiles(root: Path) : GameFiles {
    private val root = root.toAbsolutePath().normalize()
    private val index: Map<GamePath, Path> by lazy {
        walkGameFiles(root) { files -> files.associate { GamePath.of(it.gamePath) to it.file }}
    }

    override fun listing(): FileListing = FileListing.Named(index.keys)

    override fun exists(path: GamePath): Boolean = path in index

    override fun open(path: GamePath): BinaryReader? =
        index[path]?.let { BinaryReader.ofSegmentBytes(it.readBytes()).decompress() }

    override fun close() {}
}