package com.sappyoak.dsanalyzer.runtime

import com.sappyoak.dsanalyzer.game.GameEdition
import com.sappyoak.dsanalyzer.native.process.ProcessInfo
import com.sappyoak.dsanalyzer.native.process.Processes

/** A running process recognized as one edition of the game */
public data class GameProcess(
    public val process: ProcessInfo,
    public val edition: GameEdition
)

/** The running processes that are one of [editions], matched by executable name */
public fun Processes.findGames(editions: Set<GameEdition>): List<GameProcess> =
    list().mapNotNull { process ->
        GameEdition.forExecutableName(process.executableName)
            ?.takeIf { it in editions }
            ?.let { GameProcess(process, it) }
    }