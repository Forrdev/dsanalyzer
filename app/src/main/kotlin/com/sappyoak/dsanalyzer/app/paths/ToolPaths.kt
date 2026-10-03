package com.sappyoak.dsanalyzer.app.paths

import java.nio.file.Path
import kotlin.io.path.createDirectories


public class ToolPaths(public val root: Path = defaultToolRoot()) {
    public val logs = root.resolve("logs")
    public val workspaces = root.resolve("workspaces")

    public val settings = root.resolve("settings.json")
    public val verificationFile = root.resolve("verification.json")

    init {
        root.createDirectories()
        logs.createDirectories()
        workspaces.createDirectories()
    }
}