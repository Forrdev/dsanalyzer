package com.sappyoak.dsanalyzer.game

internal val PREPARE_TO_DIE_ARCHIVE_STEMS: List<String> =
    listOf("dvdbnd0", "dvdbnd1", "dvdbnd2", "dvdbnd3")

private const val ARCHIVE_HEADER_EXTENSION = "bhd5"
private const val ARCHIVE_DATA_EXTENSION = "bdt"

private val REMASTERED_DATA_DIRECTORIES = listOf("chr", "event", "map", "msg", "obj", "param", "script")

public fun archiveStems(edition: GameEdition): List<String> = when (edition) {
    GameEdition.PrepareToDie -> PREPARE_TO_DIE_ARCHIVE_STEMS
    GameEdition.Remastered -> emptyList()
}
/** The header half of each archive pair */
internal fun archiveHeaderNames(edition: GameEdition): List<String> =
    archiveStems(edition).map { "$it.$ARCHIVE_HEADER_EXTENSION" }

/**
 * Files and folders an installation must have besides its executable to be recognized as that
 * edition. This is deliberately not a completeness check. Inspection runs on every launch for every known
 * installation, so it only looks for the shape. Whether every file is present is what verification answers
 */
internal fun requiredPaths(edition: GameEdition): List<String> = when (edition) {
    GameEdition.PrepareToDie -> archiveStems(edition).flatMap { stem ->
        listOf("$stem.$ARCHIVE_HEADER_EXTENSION", "$stem.$ARCHIVE_DATA_EXTENSION")
    }

    GameEdition.Remastered -> REMASTERED_DATA_DIRECTORIES
}
