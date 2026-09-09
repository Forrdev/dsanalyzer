package com.sappyoak.dsanalyzer.app.workspace

import io.github.oshai.kotlinlogging.KotlinLogging
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import kotlinx.serialization.json.Json
import java.nio.file.Path
import kotlin.io.path.*

import com.sappyoak.dsanalyzer.game.InstallationId

/** A representation of a [Workspace] on disk */
public class WorkspaceDirectory(
    private val location: Path,
    private val json: Json
) {
    private val logger = KotlinLogging.logger {  }

    public suspend fun list(): List<Workspace> = withContext(Dispatchers.IO) {
        if (!location.exists()) {
            return@withContext emptyList()
        }

        location
            .listDirectoryEntries()
            .filter { it.isDirectory() }
            .mapNotNull { read(it.resolve(DESCRIPTOR_NAME)) }
            .sortedBy { it.name.lowercase() }
    }

    public suspend fun create(name: String, installationId: InstallationId): Workspace = withContext(Dispatchers.IO) {
        val workspace = Workspace(
            id = WorkspaceId.random(),
            name = name,
            installationId = installationId
        )
        val directory = location.resolve(workspace.id.value).createDirectories()
        directory.resolve(DESCRIPTOR_NAME).writeText(json.encodeToString(workspace))
        workspace
    }

    private fun read(descriptor: Path): Workspace? = runCatching {
        json.decodeFromString<Workspace>(descriptor.readText())
    }
        .onFailure { logger.warn(it) { "Skipping unreadable workspace at $descriptor"} }
        .getOrNull()

    private companion object {
        private const val DESCRIPTOR_NAME = "workspace.json"
    }
}