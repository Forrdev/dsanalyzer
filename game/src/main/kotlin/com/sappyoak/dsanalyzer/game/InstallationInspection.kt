package com.sappyoak.dsanalyzer.game

import java.nio.file.Path
import kotlin.io.path.exists
import kotlin.io.path.isRegularFile


public fun inspectInstallation(root: Path): InstallationCheck {
    val executable = GameEdition.entries
        .map { root.resolve(it.executableName) }
        .firstOrNull { it.isRegularFile() }
        ?: return InstallationCheck.Rejected(RejectionReason.NoExecutable)

    val edition = GameEdition.forExecutableName(executable.fileName.toString())
        ?: return InstallationCheck.Rejected(RejectionReason.UnrecognizedExecutable(executable.fileName.toString()))

    val missing = requiredPaths(edition).filterNot { root.resolve(it).exists() }
    if (missing.isNotEmpty()) {
        return InstallationCheck.Rejected(RejectionReason.MissingFiles(missing))
    }

    return InstallationCheck.Valid(Installation(
        id = InstallationId.forRoot(root),
        root = root,
        executable = executable,
        build = GameBuild(edition)
    ))
}
