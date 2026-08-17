package com.sappyoak.dsanalyzer.app.config

import kotlinx.serialization.Serializable
import kotlinx.serialization.json.Json
import java.nio.file.Path

import com.sappyoak.dsanalyzer.shared.SInstant
import com.sappyoak.dsanalyzer.shared.io.appendString
import kotlin.io.path.deleteIfExists
import kotlin.io.path.exists
import kotlin.io.path.readLines

/**
 * An append-only log of workspace changes, compacted periodically
 *
 * Compaction folds the log back into settings once it grows past a threshold, so replay
 * stays short and the log does not grow without limit.
 *
 * The high-frequency changes are excluded instead of debounced. All of these changes
 * are essentially worth nothing on restore, so they are folded in at compaction rather than
 * journaled.
 */
class WorkspaceJournal(private val path: Path) {
    // internal json writer without pretty print
    private val json = Json {
        allowTrailingComma = true
        decodeEnumsCaseInsensitive = true
        encodeDefaults = true
        ignoreUnknownKeys = true
        isLenient = true
        prettyPrint = false
    }

    fun getEntryCount(): Int = runCatching {
        path.readLines().count { it.isNotBlank() }
    }.getOrDefault(0)

    fun record(change: WorkspaceChange) {
        path.appendString { json.encodeToString(change) }
    }

    /**
     * Every change since the last compaction, oldest first
     * Order matters, these are applied in sequence over the base state
     *
     * A malformed line is skipped rather than failing the replay. It means a possible
     * crash mid=write, so it is the *last* line and everything before it is intact
     */
    fun replay(): List<WorkspaceChange> {
        if (!path.exists()) return emptyList()

        return runCatching {
            path.readLines().mapNotNull { line ->
                if (line.isBlank()) {
                    null
                } else {
                    runCatching { json.decodeFromString<WorkspaceChange>(line) }.getOrNull()
                }
            }
        }.getOrDefault(emptyList())
    }

    /**
     * Whether the log has grown enough to fold back
     *
     * Bounded by entries rather than by size or time, because replay cost is per entry
     */
    fun needsCompaction(): Boolean = getEntryCount() >= COMPACTION_THRESHOLD

    fun compacted() {
        runCatching { path.deleteIfExists() }
    }

    private companion object {
        /**
         * Entries before folding back into settings. Low enough that replay is trivial
         * but high enough that compaction is not happening constantly
         */
        const val COMPACTION_THRESHOLD = 25
    }
}

/** One durable change to a workspace */
@Serializable
sealed class WorkspaceChange {
    abstract val workspaceId: String
    abstract val at: SInstant

    @Serializable
    data class Created(
        override val workspaceId: String,
        val installationKey: String,
        val title: String,
        override val at: SInstant
    ) : WorkspaceChange()

    @Serializable
    data class Closed(
        override val workspaceId: String,
        override val at: SInstant
    ) : WorkspaceChange()

    @Serializable
    data class Renamed(
        override val workspaceId: String,
        val title: String,
        override val at: SInstant
    ) : WorkspaceChange()
}

fun List<PersistedWorkspace>.applyChanges(
    changes: List<WorkspaceChange>
): List<PersistedWorkspace> {
    val byId = associateBy { it.id }.toMutableMap()

    for (change in changes) {
        when (change) {
            is WorkspaceChange.Created -> byId[change.workspaceId] = PersistedWorkspace(
                id = change.workspaceId,
                installationKey = change.installationKey,
                title = change.title
            )

            is WorkspaceChange.Closed -> byId.remove(change.workspaceId)

            is WorkspaceChange.Renamed ->
                byId[change.workspaceId] = byId[change.workspaceId]?.copy(title = change.title) ?: continue
        }
    }

    return byId.values.toList()
}