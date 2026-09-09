package com.sappyoak.dsanalyzer.game

import java.nio.file.Path
import kotlin.io.path.exists
import kotlin.io.path.isRegularFile

private val PREPARE_TO_DIE_ARCHIVE_STEMS = listOf("dvdbnd0", "dvdbnd1", "dvdbnd2", "dvdbnd3")
private val ARCHIVE_EXTENSIONS = listOf("bhd5", "bdt")

public fun inspectInstallation(root: Path): InstallationCheck {
    val executable = GameEdition.entries
        .map { root.resolve(it.executableName) }
        .firstOrNull { it.isRegularFile() }
        ?: return InstallationCheck.Rejected(RejectionReason.NoExecutable)

    val edition = GameEdition.forExecutableName(executable.fileName.toString())
        ?: return InstallationCheck.Rejected(RejectionReason.UnrecognizedExecutable(executable.fileName.toString()))

    return when (edition) {
        GameEdition.PrepareToDie -> inspectPrepareToDie(root, executable)
        GameEdition.Remastered -> InstallationCheck.Rejected(RejectionReason.EditionNotSupportedYet(edition))
    }
}


private fun inspectPrepareToDie(root: Path, executable: Path): InstallationCheck {
    val missing = PREPARE_TO_DIE_ARCHIVE_STEMS
        .flatMap { stem -> ARCHIVE_EXTENSIONS.map { "$stem.$it"} }
        .filterNot { root.resolve(it).exists() }

    if (missing.isNotEmpty()) {
        return InstallationCheck.Rejected(RejectionReason.MissingArchives(missing))
    }

    return InstallationCheck.Valid(Installation(
        id = InstallationId.forRoot(root),
        root = root,
        executable = executable,
        build = GameBuild(GameEdition.PrepareToDie)
    ))
}