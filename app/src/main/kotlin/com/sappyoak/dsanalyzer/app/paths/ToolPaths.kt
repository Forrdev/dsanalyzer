package com.sappyoak.dsanalyzer.app.paths

import com.sappyoak.dsanalyzer.shared.platform.OS

import java.nio.file.Path
import kotlin.io.path.createDirectories


public class ToolPaths(public val root: Path = getDefaultRoot()) {
    public  val logs = root.resolve("logs")
    public val workspaces = root.resolve("workspaces")

    public val settings = root.resolve("settings.json")
    public val verificationFile = root.resolve("verification.json")

    init {
        root.createDirectories()
        logs.createDirectories()
        workspaces.createDirectories()
    }

    companion object {
        private const val DIRECTORY_NAME = "dsanalyzer"

        private fun getDefaultRoot(): Path {
            val home = Path.of(System.getProperty("user.home"))
            val os = OS.current

            return when {
                os.isWindows -> pathFromEnvironment("LOCALAPPDATA")?.resolve(DIRECTORY_NAME)
                    ?: home.resolve("AppData/Local/$DIRECTORY_NAME")
                os.isMac -> home.resolve("Library/Application Support/$DIRECTORY_NAME")
                else -> pathFromEnvironment("XDG_DATA_HOME")?.resolve(DIRECTORY_NAME)
                    ?: home.resolve(".local/share/$DIRECTORY_NAME")
            }
        }

        private fun pathFromEnvironment(name: String): Path? =
            System.getenv(name)?.takeIf { it.isNotBlank() }?.let { Path.of(it) }
    }
}