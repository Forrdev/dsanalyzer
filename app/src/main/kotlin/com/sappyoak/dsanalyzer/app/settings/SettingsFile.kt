package com.sappyoak.dsanalyzer.app.settings

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import kotlinx.serialization.json.Json
import java.nio.file.StandardCopyOption
import kotlin.io.path.createTempFile
import kotlin.io.path.exists
import kotlin.io.path.moveTo
import kotlin.io.path.readText
import kotlin.io.path.writeText

import com.sappyoak.dsanalyzer.app.paths.ToolPaths

public class SettingsFile(
    private val paths: ToolPaths,
    private val json: Json
) {
    public suspend fun read(): Settings = withContext(Dispatchers.IO) {
        if (!paths.settings.exists()) {
            Settings()
            // should probably write these here as well
        } else {
            json.decodeFromString<Settings>(paths.settings.readText())
        }
    }

    public suspend fun write(settings: Settings): Unit = withContext(Dispatchers.IO) {
        val temp = createTempFile(paths.root, "settings", ".json")
        temp.writeText(json.encodeToString(settings))
        temp.moveTo(
            paths.settings,
            StandardCopyOption.REPLACE_EXISTING,
            StandardCopyOption.ATOMIC_MOVE
        )
    }
}