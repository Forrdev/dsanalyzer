package com.sappyoak.dsanalyzer.game


import kotlinx.serialization.Serializable
import java.nio.file.attribute.BasicFileAttributes
import java.security.MessageDigest
import kotlin.io.path.*

import com.sappyoak.dsanalyzer.game.files.GameFile
import com.sappyoak.dsanalyzer.game.files.walkGameFiles

@Serializable
public data class FileStamp(
    public val size: Long,
    public val modifiedEpochMillis: Long
)

/**
 * A cheap answer to "has this installation changed since we last looked"
 *
 * It is a change signal, not an integrity check
 */
@Serializable
public data class InstallationFingerprint(
    public val fileCount: Int,
    public val digest: String
)


public fun Installation.fingerprint(): InstallationFingerprint = when (build.edition.storage) {
    GameStorage.Archived -> stampAll(
        (listOf(build.edition.executableName) + requiredPaths(build.edition))
            .asSequence()
            .map { GameFile(it, root.resolve(it)) }
            .filter { it.file.exists() }
    )

    GameStorage.Loose -> walkGameFiles(root, ::stampAll)
}
private fun stampAll(files: Sequence<GameFile>): InstallationFingerprint {
    val digest = MessageDigest.getInstance("SHA-256")
    var count = 0

    files.sortedBy { it.gamePath }.forEach { (gamePath, file) ->
        val attributes = file.readAttributes<BasicFileAttributes>()
        digest.update("$gamePath|${attributes.size()}|${attributes.lastModifiedTime().toMillis()}\n".toByteArray())
        count++
    }

    return InstallationFingerprint(count, digest.digest().toHexString())
}