package com.sappyoak.dsanalyzer.game

internal val PREPARE_TO_DIE_ARCHIVE_STEMS: List<String> =
    listOf("dvdbnd0", "dvdbnd1", "dvdbnd2", "dvdbnd3")

private val ARCHIVE_EXTENSIONS = listOf("bhd5", "bdt")

internal fun archiveFileNames(edition: GameEdition): List<String> = when (edition) {
    GameEdition.PrepareToDie -> PREPARE_TO_DIE_ARCHIVE_STEMS.flatMap { stem ->
        ARCHIVE_EXTENSIONS.map { "$stem.$it" }
    }

    GameEdition.Remastered -> emptyList()
}

internal fun watchedFileNames(edition: GameEdition): List<String> =
    listOf(edition.executableName) + archiveFileNames(edition)