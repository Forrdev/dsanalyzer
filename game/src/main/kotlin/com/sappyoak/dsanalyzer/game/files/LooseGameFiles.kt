package com.sappyoak.dsanalyzer.game.files

import java.nio.file.Files
import java.nio.file.Path
import kotlin.io.path.invariantSeparatorsPathString
import kotlin.io.path.isRegularFile
import kotlin.io.path.readBytes
import kotlin.streams.asSequence

import com.sappyoak.dsanalyzer.formats.compression.decompress

/**
 * Access to a game whose files are unpacked on disk under [root].
 *
 * Game paths are rooted at the installation, so '/chr/c0000.chrbnd.dcx' is 'root/chr/c0000.chrbnd.dcx'
 */
internal class LooseGameFiles(root: Path) : GameFiles {
    private val root = root.toAbsolutePath().normalize()

    override fun listing(): FileListing = walkGameFiles(root) { files ->
        FileListing.Named(files.mapTo(mutableSetOf()) { it.gamePath })
    }

    override fun read(path: String): ByteArray? = locate(path)?.readBytes()?.decompress()
    override fun exists(path: String): Boolean = locate(path) != null

    override fun close() {}

    private fun locate(path: String): Path? {
        val file = root.resolve(path.trimStart('/', '\\')).normalize()
        return file.takeIf { it.startsWith(root) && it.isRegularFile() }
    }
}