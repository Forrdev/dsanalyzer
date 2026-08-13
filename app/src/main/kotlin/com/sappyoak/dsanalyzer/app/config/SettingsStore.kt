package com.sappyoak.dsanalyzer.app.config

import java.nio.file.Path

import com.sappyoak.dsanalyzer.shared.readFile
import com.sappyoak.dsanalyzer.shared.writeFile
import kotlin.io.path.exists

class SettingsStore(private val location: Path = defaultLocation()) {
    fun load(): LoadResult {
        if (!location.exists()) {
            return LoadResult(Settings(), wasPresent = false)
        }

        return location.readFile<Settings>(jsonSerializer).fold(
            onSuccess = { settings -> LoadResult(settings, wasPresent = true) },
            onFailure = { err -> LoadResult(
                settings = Settings(),
                wasPresent = true,
                problem = "Could not read settings from $location (${err.message}). Falling back to defaults"
            )}
        )
    }

    fun save(settings: Settings): Result<Path> =
        location.writeFile(jsonSerializer, settings)

    class LoadResult(
        val settings: Settings,
        val wasPresent: Boolean,
        val problem: String? = null
    ) {
        val isFirstRun: Boolean get() = !wasPresent
    }

    companion object {
        fun defaultLocation(): Path = ToolPaths.Default.settings
    }
}