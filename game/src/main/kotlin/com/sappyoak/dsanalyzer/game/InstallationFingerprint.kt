package com.sappyoak.dsanalyzer.game


import kotlinx.serialization.Serializable
import java.nio.file.attribute.BasicFileAttributes
import java.security.MessageDigest
import kotlin.io.path.*

import com.sappyoak.dsanalyzer.game.files.GameFile
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
        GameStorage.Archived -> {
            val files = (listOf(build.edition.executableName) + requiredPaths(build.edition))
                .asSequence()
                .map { GameFile(it, root.resolve(it)) }
                .filter { it.file.exists() }

            digest.stamp(files).also {
                archiveHeaderNames(build.edition)
                    .map(root::resolve)
                    .filter { it.exists() }
                    .forEach { digest.update(it.readBytes()) }
            }
        }

        GameStorage.Loose -> walkGameFiles(root) { digest.stamp(it) }
    }

    return InstallationFingerprint(fileCount, digest.digest().toHexString())
}
/**
 * Adds each file's path, size, and modification time returning how many there were. Sorted
 * first, so the result does not depend on file system order
 */
private fun MessageDigest.stamp(files: Sequence<GameFile>): Int {
    var count = 0
    files.sortedBy { it.gamePath }.forEach { (gamePath, file) ->
        val attributes = file.readAttributes<BasicFileAttributes>()
        update("$gamePath|${attributes.size()}|${attributes.lastModifiedTime().toMillis()}\n".toByteArray())
        count++
    }
    return count
}