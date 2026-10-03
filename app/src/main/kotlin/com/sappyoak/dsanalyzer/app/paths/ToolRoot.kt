package com.sappyoak.dsanalyzer.app.paths

import java.nio.file.Path

import com.sappyoak.dsanalyzer.shared.platform.OS

private const val DIRECTORY_NAME = "dsanalyzer"

/** Where this tool keeps everything, by the convention of whichever system it is running on */
public fun defaultToolRoot(): Path {
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
