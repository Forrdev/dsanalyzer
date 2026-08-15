package com.sappyoak.dsanalyzer.app.ui.components

import java.nio.file.Path
import javax.swing.JFileChooser
import javax.swing.filechooser.FileNameExtensionFilter

object FilePickers {
    fun chooseDirectory(
        title: String,
        startAt: Path? = null
    ): Path? {
        val chooser = JFileChooser().apply {
            dialogTitle = title
            fileSelectionMode = JFileChooser.DIRECTORIES_ONLY
            isAcceptAllFileFilterUsed = false
            startAt?.let { currentDirectory = it.toFile() }
        }

        return chooser.selectedOrNull()
    }

    fun chooseFile(
        title: String,
        description: String,
        vararg extensions: String
    ): Path? {
        val chooser = JFileChooser().apply {
            dialogTitle = title
            fileSelectionMode = JFileChooser.FILES_ONLY
            fileFilter = FileNameExtensionFilter(description, *extensions)
        }

        return chooser.selectedOrNull()
    }
}

private fun JFileChooser.selectedOrNull(): Path? =
    if (showOpenDialog(null) == JFileChooser.APPROVE_OPTION) selectedFile?.toPath()
    else null
