package com.sappyoak.dsanalyzer.game.files

import java.nio.file.Files
import java.nio.file.Path
import kotlin.io.path.invariantSeparatorsPathString
import kotlin.io.path.isRegularFile
import kotlin.streams.asSequence

/** A file on disk under the game path it is known by */
internal data class GameFile(val gamePath: String, val file: Path)

internal fun <T> walkGameFiles(root: Path, block: (Sequence<GameFile>) -> T): T =
    Files.walk(root).use { paths ->
        block(paths.asSequence()
            .filter { it.isRegularFile() }
            .map { GameFile("/" + root.relativize(it).invariantSeparatorsPathString, it) }
        )
    }