package com.sappyoak.dsanalyzer.game

import kotlinx.serialization.Serializable
import kotlin.io.path.*

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
    public val files: Map<String, FileStamp>
)

public fun Installation.fingerprint(): InstallationFingerprint =
    InstallationFingerprint(
        files = watchedFileNames(build.edition)
            .mapNotNull { name -> fileStampFor(this, name)?.let { name to it } }
            .toMap()
    )

private fun fileStampFor(installation: Installation, name: String): FileStamp? {
    val file = installation.root.resolve(name)
    if (!file.exists()) return null

    return FileStamp(
        size = file.fileSize(),
        modifiedEpochMillis = file.getLastModifiedTime().toMillis()
    )
}