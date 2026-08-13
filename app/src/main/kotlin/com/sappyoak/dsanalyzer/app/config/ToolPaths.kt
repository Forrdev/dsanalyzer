package com.sappyoak.dsanalyzer.app.config

import kotlin.io.path.createDirectories
import kotlin.io.path.exists
import kotlin.io.path.listDirectoryEntries
import java.nio.file.Files
import java.nio.file.Path

import com.sappyoak.dsanalyzer.domain.GameIdentity
import com.sappyoak.dsanalyzer.domain.GameVersion
import com.sappyoak.dsanalyzer.shared.OS

/**
 * Where the tool keeps its own files.
 */
class ToolPaths(val root: Path) {
    /**
     * Diagnostic reports, one per scan
     *
     * These are not scoped, because a report already carries its installation
     * identity inside, and someone comparing a PTDE scan against a Remastered one
     * would want them in the same place rather than two directories apart
     */
    val reports = root.resolve("reports")

    val settings = root.resolve("settings.json")

    /**
     * Definition files, per game kind
     */
    fun definitions(version: GameVersion): Path =
        root.resolve("definitions").resolve(version.shortName.lowercase())

    /**
     * Capture runtime sessions, per game kind
     */
    fun traces(version: GameVersion): Path =
        root.resolve("traces").resolve(version.shortName.lowercase())

    /**
     * Extracted game files, per build
     */
    fun extracted(identity: GameIdentity): Path =
        root.resolve("extracted").resolve(identity.pathSegment)

    fun ensureExists(identity: GameIdentity? = null): Result<Unit> = runCatching {
        root.createDirectories()
        reports.createDirectories()

        identity?.let {
            definitions(it.version).createDirectories()
            traces(it.version).createDirectories()
            extracted(it).createDirectories()
        }
    }

    companion object {
        const val APP_DIRECTORY = "dsanalyzer"

        val Default: ToolPaths by lazy {
            val home = System.getProperty("user.home")
            val os = OS.Current

            val base = when (os) {
                OS.Windows -> System.getenv("LOCALAPPDATA")?.let { Path.of(it) }
                    ?: Path.of(home, "AppData", "Local")

                OS.Mac -> Path.of(home, "Library", "Application Support")
                OS.Linux -> System.getenv("XDG_DATA_HOME")?.let { Path.of(it) }
                    ?: Path.of(home, ".local", "share")
            }

            ToolPaths(base.resolve(APP_DIRECTORY))
        }

        /**
         * Alternatives paths to offer alongside the default
         */
        fun suggestions(): List<Path> {
            val home = System.getProperty("user.home")
            val os = OS.Current

            val candidates = mutableListOf(
                Default.root,
                Path.of(home, APP_DIRECTORY),
                Path.of(home, "Documents", APP_DIRECTORY)
            )

            when (os) {
                OS.Windows -> {
                    for (letter in listOf("D", "E", "F")) {
                        val drive = Path.of("$letter:/")
                        if (drive.exists()) {
                            candidates.add(drive.resolve(APP_DIRECTORY))
                        }
                    }
                }

                OS.Mac -> {
                    val volumesDir = Path.of("/Volumes")
                    if (volumesDir.exists()) {
                        for (path in volumesDir.listDirectoryEntries()) {
                            candidates.add(path.resolve(APP_DIRECTORY))
                        }
                    }
                }
                OS.Linux -> {
                    for (mountName in listOf("/dev/sbd", "/dev/sdc", "/dev/nvme0n1")) {
                        val mount = Path.of("$mountName/")
                        if (Files.exists(mount)) {
                            candidates.add(mount.resolve(APP_DIRECTORY))
                        }
                    }
                }
            }

            return candidates.distinct()
        }
    }
}