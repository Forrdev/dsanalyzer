package com.sappyoak.dsanalyzer.app.config

import kotlinx.serialization.json.Json
import java.nio.file.Path
import kotlin.io.path.exists
import kotlin.io.path.isWritable

import com.sappyoak.dsanalyzer.game.identity.GameBuildId
import com.sappyoak.dsanalyzer.game.identity.GameIdentity

import com.sappyoak.dsanalyzer.app.data.JsonStore
import com.sappyoak.dsanalyzer.app.state.workspace.SetupState
import com.sappyoak.dsanalyzer.app.state.workspace.WorkspaceState



class AppEnvironment private constructor(
    private val store: JsonStore<Settings>,
    initialSettings: Settings,
    val isFirstRun: Boolean,
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
            buildId = active.buildId ?: GameBuildId.Unknown
        )
    }

    /**
     * Durable workspace changs, appended as they happen
     *
     * Separate from settings because the write pattern differs: settings are rewritten
     * rarely and wholly, and this is appended often and in fragments
     */
    var journal: WorkspaceJournal = WorkspaceJournal(paths.workspaceJournal)
        private set

    fun extractedPathFor(identity: GameIdentity): Path =
        settings.installations[identity.pathSegment]?.extractedPath ?: paths.extracted(identity)

    fun cacheSizeFor(identity: GameIdentity): Long = paths.cacheSize(identity)
    fun gamePathFor(identity: GameIdentity): Path? =
        settings.installations[identity.pathSegment]?.gamePath

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
        gamePath: Path
    ) {
        update { settings -> settings
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

    /** Makes an existing installation active */
    fun activeInstallation(key: String) {
        if (settings.installations[key] == null) return
        update { it.copy(activeInstallation = key) }
        identity?.let { paths.ensureExists(it) }
    }

    fun updateInstallationFor(
        identity: GameIdentity,
        block: (InstallationSettings) -> InstallationSettings
    ) {
        update { it.withInstallation(identity.pathSegment, block) }
    }

    fun updateActiveInstallation(block: (InstallationSettings) -> InstallationSettings) {
        val key = settings.activeInstallation ?: return
        update { it.withInstallation(key, block) }
    }

    fun forgetInstallation(key: String) {
        update { it.copy(
            installations = it.installations - key,
            activeInstallation = if (it.activeInstallation == key) null else it.activeInstallation
        )}
    }

    /**
     * Records a change and folds the log back when it has grown
     *
     * Compaction writes settings before clearing the log. The reverse order would
     * lose everything if the settings write then failed
     */
    fun recordWorkspaceChange(change: WorkspaceChange) {
        journal.record(change)

        if (journal.needsCompaction()) {
            val folded = settings.workspaces.applyChanges(journal.replay())
            store.save(settings.copy(workspaces = folded)).onSuccess {
                journal.compacted()
            }
        }
    }

    fun restoreWorkspace(persisted: PersistedWorkspace): WorkspaceState? {
        val install = settings.installations[persisted.installationKey] ?: return null
        val version = install.version ?: return null
        val identity = GameIdentity(version, buildId = install.buildId ?: GameBuildId.Unknown)

        return WorkspaceState(
            id = persisted.id,
            identity = identity,
            installationKey = persisted.installationKey,
            title = persisted.title,
            setup = SetupState(
                gamePath = install.gamePath,
                version = version,
                gamePathResolves = install.gamePath?.exists() ?: false,
                dataPathWritable = paths.root.isWritable()
            )
        )
    }

    /** Workspaces as of the last clean state, plus anything journaled after */
    fun persistedWorkspaces(): List<PersistedWorkspace> =
        settings.workspaces.applyChanges(journal.replay())


    fun persistWorkspaces(
        workspaces: Collection<WorkspaceState>,
        active: String?
    ) {
        update { settings ->
            settings.copy(
                workspaces = workspaces.map { workspace ->
                    PersistedWorkspace(
                        id = workspace.id,
                        installationKey = workspace.installationKey,
                        title = workspace.title
                    )
                },
                activeWorkspace = active
            )
        }
        journal.compacted()
    }

    private fun pathsFor(settings: Settings): ToolPaths =
        settings.dataPath?.let { ToolPaths(it) } ?: ToolPaths.Default

    companion object {
        fun load(store: JsonStore<Settings>): AppEnvironment {
            val loaded = store.load()
            val environment = AppEnvironment(
                store = store,
                initialSettings = loaded.value,
                isFirstRun = loaded.isFirstRun
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