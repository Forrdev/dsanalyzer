package com.sappyoak.dsanalyzer.app.paths

import java.nio.file.Path
import java.util.Locale
import kotlin.io.path.createDirectories


public class ToolPaths(public val root: Path = getDefaultRoot()) {
    val logs = root.resolve("logs")
    val workspaces = root.resolve("workspaces")

    val settings = root.resolve("settings.json")

    init {
        root.createDirectories()
        logs.createDirectories()
        workspaces.createDirectories()
    }

    companion object {
        private const val DIRECTORY_NAME = "dsanalyzer"

        private fun getDefaultRoot(): Path {
            val home = Path.of(System.getProperty("user.home"))
            val os = System.getProperty("os.name").lowercase(Locale.ROOT)

            return when {
                os.startsWith("win") -> pathFromEnvironment("LOCALAPPDATA")?.resolve(DIRECTORY_NAME)
                    ?: home.resolve("AppData/Local/$DIRECTORY_NAME")
                os.startsWith("mac") -> home.resolve("Library/Application Support/$DIRECTORY_NAME")
                else -> pathFromEnvironment("XDG_DATA_HOME")?.resolve(DIRECTORY_NAME)
                    ?: home.resolve(".local/share/$DIRECTORY_NAME")
            }
        }

        private fun pathFromEnvironment(name: String): Path? =
            System.getenv(name)?.takeIf { it.isNotBlank() }?.let { Path.of(it) }
    }
}