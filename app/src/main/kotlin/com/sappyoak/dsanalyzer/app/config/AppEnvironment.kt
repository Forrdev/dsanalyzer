package com.sappyoak.dsanalyzer.app.config

import java.nio.file.Path

import com.sappyoak.dsanalyzer.domain.GameIdentity
import com.sappyoak.dsanalyzer.domain.GameVersion

import com.sappyoak.dsanalyzer.app.data.JsonStore
import kotlinx.serialization.json.Json

class AppEnvironment private constructor(
    private val store: JsonStore<Settings>,
    initialSettings: Settings,
    val isFirstRun: Boolean,
    val loadProblem: String?
) {
    var settings: Settings = initialSettings
        private set

    var paths: ToolPaths = pathsFor(initialSettings)
        private set

    val installation: InstallationSettings? get() = settings.active

    val identity: GameIdentity? get() {
        val active = installation ?: return null
        val version = active.version ?: return null
        return GameIdentity(
            version = version,
            buildId = active.buildId ?: "unknown"
        )
    }

    val gameVersion: GameVersion
        get() = installation?.version ?: GameVersion.PTDE

    val extractedPath: Path? get() =
        installation?.extractedPath?.let { Path.of(it) }
            ?: identity?.let { paths.extracted(it) }

    val definitionsPath: Path? get() =
            installation?.version?.let { paths.definitions(it) }

    fun update(block: (Settings) -> Settings): Settings {
        val updated = block(settings)
        if (updated == settings) return settings

        settings = updated
        val newPaths = pathsFor(updated)
        if (newPaths.root != paths.root) {
            paths = newPaths
            paths.ensureExists(identity)
        }

        store.save(updated)
        return updated
    }

    fun selectInstallation(
        identity: GameIdentity,
        gamePath: String
    ) {
        update { settings ->
            settings
                .copy(activeInstallation = identity.pathSegment)
                .withInstallation(identity.pathSegment) {
                    it.copy(
                        gamePath = gamePath,
                        version = identity.version,
                        buildId = identity.buildId
                    )
                }
        }
        paths.ensureExists(identity)
    }

    fun updateInstallation(block: (InstallationSettings) -> InstallationSettings) {
        val key = settings.activeInstallation ?: return
        update { it.withInstallation(key, block) }
    }

    private fun pathsFor(settings: Settings): ToolPaths =
        settings.dataPath?.let { ToolPaths(Path.of(it)) } ?: ToolPaths.Default

    companion object {
        fun load(store: JsonStore<Settings>): AppEnvironment {
            val loaded = store.load()
            val environment = AppEnvironment(
                store = store,
                initialSettings = loaded.value,
                isFirstRun = loaded.isFirstRun,
                loadProblem = loaded.problem
            )

            environment.paths.ensureExists()
            if (loaded.isFirstRun) store.save(loaded.value)

            return environment
        }

        fun load(json: Json): AppEnvironment {
            val store = JsonStore<Settings>(
                location = ToolPaths.Default.settings,
                jsonSerializer = json,
                default = { Settings() }
            )

            return load(store)
        }
    }
}