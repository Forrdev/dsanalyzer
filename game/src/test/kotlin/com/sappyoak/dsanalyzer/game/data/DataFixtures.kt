package com.sappyoak.dsanalyzer.game.data

import java.nio.file.Path
import kotlinx.coroutines.CoroutineScope

import com.sappyoak.dsanalyzer.game.GameBuild
import com.sappyoak.dsanalyzer.game.GameEdition
import com.sappyoak.dsanalyzer.game.Installation
import com.sappyoak.dsanalyzer.game.InstallationId
import com.sappyoak.dsanalyzer.game.files.FileListing
import com.sappyoak.dsanalyzer.game.files.GameFiles
import com.sappyoak.dsanalyzer.game.files.GamePath
import com.sappyoak.dsanalyzer.game.files.InstallationFiles
import com.sappyoak.dsanalyzer.shared.binary.BinaryReader

internal fun installation(root: String = "game") = Installation(
    id = InstallationId("ptde"),
    root = Path.of(root),
    executable = Path.of(root, "DARKSOULS.exe"),
    build = GameBuild(GameEdition.PrepareToDie)
)

private object NoFiles : GameFiles {
    override fun listing(): FileListing = FileListing.Named(emptySet())
    override fun exists(path: GamePath): Boolean = false
    override fun open(path: GamePath): BinaryReader? = null
    override fun close() = Unit
}

/** Files that hold nothing, opened straight away and kept until [scope] ends */
internal fun noFiles(scope: CoroutineScope) = InstallationFiles(scope, context = scope.coroutineContext) { NoFiles }
