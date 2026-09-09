package com.sappyoak.dsanalyzer.app.settings

import io.github.oshai.kotlinlogging.KotlinLogging
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import kotlinx.serialization.SerializationException
import kotlinx.serialization.json.Json
import java.nio.file.Path
import java.nio.file.StandardCopyOption
import kotlin.io.path.*

import com.sappyoak.dsanalyzer.app.paths.ToolPaths

public data class SettingsReadResult(
    public val settings: Settings,
    public val recoveredFrom: Path? = null
)

public class SettingsFile(
    private val paths: ToolPaths,
    private val json: Json
) {
    private val logger = KotlinLogging.logger { }

    /** Loads settings, writing the file with defaults it if does not exist yet */
    public suspend fun read(): SettingsReadResult = withContext(Dispatchers.IO) {
        if (!paths.settings.exists()) {
            return@withContext SettingsReadResult(materializeDefaults())
        }

        try {
            SettingsReadResult(json.decodeFromString<Settings>(paths.settings.readText()))
        } catch (err: SerializationException) {
            val backup = paths.root.resolve("settings.corrupt=${System.currentTimeMillis()}.json")
            paths.settings.moveTo(backup)
            logger.error(err) { "Settigns.json could not be parsed: kept as $backup" }
            SettingsReadResult(materializeDefaults(), backup)
        }
    }

    public suspend fun write(settings: Settings): Unit = withContext(Dispatchers.IO) {
        persist(settings)
    }

    private fun materializeDefaults(): Settings {
        val defaults = Settings()
        runCatching { persist(defaults) }
            .onFailure { logger.warn(it) { "Could not write initial settings.json" } }
        return defaults
    }

    private fun persist(settings: Settings) {
        val temp = createTempFile(paths.root, "settings", ".json")
        temp.writeText(json.encodeToString(settings))
        temp.moveTo(
            paths.settings,
            StandardCopyOption.REPLACE_EXISTING,
            StandardCopyOption.ATOMIC_MOVE
        )
    }
}