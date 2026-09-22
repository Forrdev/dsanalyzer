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

    override fun listing(): FileListing = Files.walk(root).use { paths ->
        FileListing.Named(paths.asSequence()
            .filter { it.isRegularFile() }
            .mapTo(mutableSetOf()) { "/" + root.relativize(it).invariantSeparatorsPathString }
        )
    }

    override fun read(path: String): ByteArray? = locate(path)?.readBytes()?.decompress()
    override fun exists(path: String): Boolean = locate(path) != null

    override fun close() {}

    private fun locate(path: String): Path? {
        val file = root.resolve(path.trimStart('/', '\\')).normalize()
        return file.takeIf { it.startsWith(root) && it.isRegularFile() }
    }
}