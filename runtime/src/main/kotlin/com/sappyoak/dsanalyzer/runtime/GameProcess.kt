package com.sappyoak.dsanalyzer.runtime

import io.github.oshai.kotlinlogging.KotlinLogging
import java.nio.file.InvalidPathException
import java.nio.file.Path

import com.sappyoak.dsanalyzer.game.GameEdition
import com.sappyoak.dsanalyzer.game.Installation
import com.sappyoak.dsanalyzer.native.process.ProcessAccessException
import com.sappyoak.dsanalyzer.native.process.ProcessInfo
import com.sappyoak.dsanalyzer.native.process.Processes

private val logger = KotlinLogging.logger { }

/** A running process recognized as one edition of the game */
public data class GameProcess(
    public val process: ProcessInfo,
    public val edition: GameEdition
)

/**
 * The running processes that are [target]. It's edition's executable, loaded from [target]'s own path,
 * so another installation of the same edition is never attached to. A process whose modules cannot be read
 * is kept, and attaching to it decides
 */
public fun Processes.findGames(target: Installation): List<GameProcess> {
    val edition = target.build.edition
    val name = target.executable.fileName.toString()
    return list()
        .filter { it.executableName.equals(name, ignoreCase = true) }
        .filter { GameEdition.forExecutableName(it.executableName) == edition }
        .filter { runsFrom(it, target.executable) }
        .map { GameProcess(it, edition) }
}

private fun Processes.runsFrom(process: ProcessInfo, executable: Path): Boolean {
    val loaded = try {
        modules(process.pid).firstOrNull { it.name.equals(process.executableName, ignoreCase = true) }?.path
    } catch (failure: ProcessAccessException) {
        logger.debug { "Could not read where ${process.executableName} (${process.pid}) runs from: ${failure.message}" }
        null
    } ?: return true

    return samePath(loaded, executable).also { same ->
        if (!same) logger.debug { "${process.executableName} (${process.pid}) runs from $loaded, not $executable" }
    }
}

private fun samePath(loaded: String, executable: Path): Boolean = try {
    Path.of(loaded).toAbsolutePath().normalize() == executable.toAbsolutePath().normalize()
} catch (_: InvalidPathException) {
    false
}
