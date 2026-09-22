package com.sappyoak.dsanalyzer.game.files

import com.sappyoak.dsanalyzer.formats.binder.OpenBinder
import com.sappyoak.dsanalyzer.formats.binder.openBinder

/** The two files of a split binder */
public data class SplitBinderPaths(public val header: GamePath, public val data: GamePath)

/** Opens a binder stored as a single file */
public fun GameFiles.openBinder(path: GamePath): OpenBinder? = open(path)?.let { openBinder(it) }

/** Opens a split-binder */
public fun GameFiles.openBinder(paths: SplitBinderPaths): OpenBinder? {
    val header = open(paths.header) ?: return null
    val data = open(paths.data) ?: return null
    return openBinder(header, data)
}