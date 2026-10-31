package com.sappyoak.dsanalyzer.tools

import java.nio.file.Path

import com.sappyoak.dsanalyzer.game.GameBuild
import com.sappyoak.dsanalyzer.game.GameEdition
import com.sappyoak.dsanalyzer.game.Installation
import com.sappyoak.dsanalyzer.game.InstallationId

internal fun installationAt(root: Path, edition: GameEdition): Installation = Installation(
    id = InstallationId.forRoot(root),
    root = root,
    executable = root.resolve(edition.executableName),
    build = GameBuild(edition)
)
