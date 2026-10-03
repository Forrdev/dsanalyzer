package com.sappyoak.dsanalyzer.game.world.maps

import com.sappyoak.dsanalyzer.game.files.GameFiles
import com.sappyoak.dsanalyzer.game.verification.FileManifest

/**
 * The maps this installation has: every map the edition's [manifest] names whose MSB is present
 */
public fun GameFiles.availableMaps(manifest: FileManifest): List<MapId> = manifest.paths
    .asSequence()
    .map { it.value }
    .filter { it.startsWith(MAP_STUDIO_DIRECTORY) && it.endsWith(MSB_EXTENSION) }
    .mapNotNull { MapId.parse(it.removeSurrounding(MAP_STUDIO_DIRECTORY, MSB_EXTENSION)) }
    .map { it.canonical }
    .distinct()
    .filter { exists(it.msbPath) }
    .sortedBy { it.name }
    .toList()