package com.sappyoak.dsanalyzer.app.settings

import io.github.oshai.kotlinlogging.KotlinLogging
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import kotlinx.coroutines.sync.Mutex
import kotlinx.coroutines.sync.withLock
import kotlinx.serialization.SerializationException
import kotlinx.serialization.json.Json
import java.nio.file.Path
import java.nio.file.StandardCopyOption
import kotlin.io.path.*


public data class SettingsReadResult(
    public val settings: Settings,
    public val recoveredFrom: Path? = null
)

public class SettingsFile(
    private val location: Path,
    private val json: Json
) {
    private val logger = KotlinLogging.logger { }
    private val writing = Mutex()

    /** Loads settings, writing the file with defaults it if does not exist yet */
    public suspend fun read(): SettingsReadResult = withContext(Dispatchers.IO) {
        if (!location.exists()) {
            return@withContext SettingsReadResult(materializeDefaults())
        }

        try {
            SettingsReadResult(json.decodeFromString<Settings>(location.readText()))
        } catch (err: SerializationException) {
            val backup = location.parent.resolve("settings.corrupt=${System.currentTimeMillis()}.json")
            location.moveTo(backup)
            logger.error(err) { "Settings.json could not be parsed: kept as $backup" }
            SettingsReadResult(materializeDefaults(), backup)
        }
    }

    public suspend fun write(settings: Settings): Unit = writing.withLock {
        withContext(Dispatchers.IO) { persist(settings) }
    }

    private fun materializeDefaults(): Settings {
        val defaults = Settings()
        runCatching { persist(defaults) }
            .onFailure { logger.warn(it) { "Could not write initial settings.json" } }
        return defaults
    }

    private fun persist(settings: Settings) {
        val temp = createTempFile(location.parent, "settings", ".json")
        temp.writeText(json.encodeToString(settings))
        temp.moveTo(
            location,
            StandardCopyOption.REPLACE_EXISTING,
            StandardCopyOption.ATOMIC_MOVE
        )
    }
}