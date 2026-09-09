package com.sappyoak.dsanalyzer.app.ui

import java.nio.file.Path
import javax.swing.JFileChooser

public fun chooseDirectory(
    title: String = "",
    block: JFileChooser.() -> Unit = {}
): Path? {
    val chooser = JFileChooser().apply {
        fileSelectionMode = JFileChooser.DIRECTORIES_ONLY
        dialogTitle = title
        block()
    }

    return if (chooser.showOpenDialog(null) == JFileChooser.APPROVE_OPTION) {
        chooser.selectedFile?.toPath()
    } else null
}

public fun chooseInstallationDirectory() = chooseDirectory(
    title = "Select the Dark Souls Installation Directory"
)