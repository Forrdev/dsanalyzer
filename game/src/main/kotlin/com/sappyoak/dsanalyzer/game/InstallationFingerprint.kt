package com.sappyoak.dsanalyzer.game


import kotlinx.serialization.Serializable
import java.nio.file.Path
import java.nio.file.attribute.BasicFileAttributes
import java.security.MessageDigest
import kotlin.io.path.*

import com.sappyoak.dsanalyzer.game.files.walkGameFiles

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


public fun Installation.fingerprint(): InstallationFingerprint {
    val digest = MessageDigest.getInstance("SHA-256")
    val fileCount = when (build.edition.storage) {
        GameStorage.Archived -> digest.stampArchives(root, build.edition)
        GameStorage.Loose -> walkGameFiles(root) { files ->
            digest.stamp(files.map { it.gamePath to it.file })
        }
    }

    return InstallationFingerprint(fileCount, digest.digest().toHexString())
}

private fun MessageDigest.stampArchives(root: Path, edition: GameEdition): Int {
    val named = (listOf(edition.executableName) + requiredPaths(edition))
        .map { it to root.resolve(it) }
        .filter { (_, file) -> file.exists() }

    val count = stamp(named.asSequence())
    archiveHeaderNames(edition)
        .map(root::resolve)
        .filter { it.exists() }
        .forEach { update(it.readBytes()) }

    return count
}

/**
 * Adds each file's path, size, and modification time returning how many there were. Sorted
 * first, so the result does not depend on file system order
 */
private fun MessageDigest.stamp(files: Sequence<Pair<String, Path>>): Int {
    var count = 0
    files.sortedBy { it.first }.forEach { (path, file) ->
        val attributes = file.readAttributes<BasicFileAttributes>()
        update("$path|${attributes.size()}|${attributes.lastModifiedTime().toMillis()}\n".toByteArray())
        count++
    }
    return count
}